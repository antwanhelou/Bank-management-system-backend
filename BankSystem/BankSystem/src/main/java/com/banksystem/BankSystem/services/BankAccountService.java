package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.loans.Loan;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.enums.LoanStatus;
import com.banksystem.BankSystem.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.banksystem.BankSystem.repository.BankAccountRepository;

import com.banksystem.BankSystem.repository.CustomerRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private EmailService emailService;

    public BankAccount createAccountForCustomer(BankAccount bankAccount, UUID customerId) {
        // Fetch the customer by ID
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + customerId));

        // Set the relationship
        bankAccount.getCustomers().add(customer); // Make sure Account's getCustomers() is initialized properly
        customer.getBankAccounts().add(bankAccount); // Ensure bi-directional consistency

        // Save the account, which should cascade the relationship update if cascading is configured
        return bankAccountRepository.save(bankAccount);
    }
    @Scheduled(cron = "0 0 0 1 * ?") // Runs at 00:00:00 on the first day of every month
    public void processMonthlyLoanRepayments() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        List<Loan> loansToRepay = loanRepository.findLoansDueForRepaymentByDate(firstDayOfMonth);

        for (Loan loan : loansToRepay) {
            if (!loan.isPaid()) {
                // Assuming you have a method to calculate the repayment amount
                BigDecimal repaymentAmount = calculateRepaymentAmount(loan);
                try {
                    makeLoanRepayment(loan.getBankAccount().getId(), loan.getId(), repaymentAmount);
                    // Log success or additional actions as necessary
                } catch (Exception e) {
                    // Log failure for auditing and further investigation
                }
            }
        }
    }
    /**
     * Calculates the monthly repayment amount for a loan using simple interest.
     * @param loan the loan to calculate the repayment for
     * @return the monthly repayment amount
     */
    public BigDecimal calculateRepaymentAmount(Loan loan) {
        BigDecimal interest = loan.getAmount()
                .multiply(loan.getInterestRate())
                .divide(new BigDecimal(100)) // Convert percentage to decimal
                .multiply(new BigDecimal(loan.getTerm()))
                .divide(new BigDecimal(12), RoundingMode.HALF_UP); // Assuming interestRate is annual

        BigDecimal totalRepayable = loan.getAmount().add(interest);
        return totalRepayable.divide(new BigDecimal(loan.getTerm()), RoundingMode.HALF_UP);
    }
    public BankAccount makeLoanRepayment(UUID accountId, UUID loanId, BigDecimal repaymentAmount) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found for this id :: " + loanId));

        if (loan.isPaid()) {
            throw new RuntimeException("This loan is already paid off.");
        }

        BankAccount bankAccount = loan.getBankAccount();

        // Calculate the actual repayment amount to ensure it does not exceed the remaining loan amount
        BigDecimal actualRepaymentAmount = repaymentAmount.compareTo(loan.getAmount()) > 0 ? loan.getAmount() : repaymentAmount;

        // Subtract the actual repayment amount from the loan amount
        BigDecimal newLoanAmount = loan.getAmount().subtract(actualRepaymentAmount);

        if (newLoanAmount.compareTo(BigDecimal.ZERO) <= 0) {
            // If the new loan amount is less than or equal to zero, mark the loan as paid and set the amount to zero
            loan.setPaid(true);
            loan.setAmount(BigDecimal.ZERO); // Ensure the loan balance is set to zero
        } else {
            // If there is still a remaining balance, update the loan amount
            loan.setAmount(newLoanAmount);
        }

        loanRepository.save(loan); // Save the updated loan state

        // Update the bank account's balance if necessary
        // Assuming you want to adjust the bank account balance based on the repayment, add your logic here

        return bankAccountRepository.save(bankAccount); // Save the updated bank account state
    }






    public Loan provideLoanToCustomer(UUID accountId, BigDecimal amount, BigDecimal interestRate, LocalDate dueDate) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("BankAccount not found for this id :: " + accountId));

        Loan loan = new Loan();
        loan.setAmount(amount);
        loan.setInterestRate(interestRate);
        loan.setDueDate(dueDate);
        loan.setBankAccount(bankAccount);

        bankAccount.getLoans().add(loan);
        bankAccountRepository.save(bankAccount);

        return loan; // Assuming you have a Loan repository or manage it through BankAccount
    }
    public String generateUniqueRandomBankAccountNumber(){
        String characters = "0123456789";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        Optional<BankAccount> bankAccounts = bankAccountRepository.findByBankAccountNumber("aa");
        // Generate 12 random characters and append them to the StringBuilder
        for (int i = 0; i < 13; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();

    }




}
