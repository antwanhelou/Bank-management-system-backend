package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.loans.Loan;
import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.enums.AccountStatus;
import com.banksystem.BankSystem.exceptions.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.CloseAccountException;
import com.banksystem.BankSystem.exceptions.InsufficientFundsException;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.repository.LoanRepository;
import com.banksystem.BankSystem.repository.TransactionRepository;
import com.banksystem.BankSystem.utilities.Constants;
import com.banksystem.BankSystem.utilities.ResultHolder;
import org.springframework.beans.factory.annotation.Autowired;
import com.banksystem.BankSystem.repository.BankAccountRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service

public abstract class BankAccountService<T extends BankAccount> {

    @Autowired
    private  LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    protected CustomerService customerService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private BankAccountRepository<BankAccount> bankAccountRepository;


    public T findBankAccount(final UUID accountID) throws BankAccountNotFoundException {
        Optional<T> searchResult = this.getRepository().findById(accountID);
        if(searchResult.isEmpty()){
            throw new BankAccountNotFoundException("Cannot find Bank Account with ID: " + accountID);
        }
        return searchResult.get();
    }
    public ResponseEntity<Map<String, String>> suspendAccount(final UUID accountID) throws BankAccountNotFoundException {
        T account = this.findBankAccount(accountID);
        account.setAccountStatus(AccountStatus.SUSPENDED);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> activateAccount(final UUID accountID) throws BankAccountNotFoundException {
        T account = this.findBankAccount(accountID);
        account.setAccountStatus(AccountStatus.ACTIVE);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }

    public abstract ResponseEntity<Map<String, String>> closeAccount(final UUID accountID) throws BankAccountNotFoundException, CloseAccountException;

    public abstract BankAccountRepository<T> getRepository();
    public abstract ResponseEntity<BankAccountDTO> createBankAccount(final CreateBankAccountRequestDTO requestDTO) throws UserNotFoundException;

    protected void completeBankAccountDetails(T bankAccount, CreateBankAccountRequestDTO requestDTO) throws UserNotFoundException {
        bankAccount.init();
        Customer owner = customerService.getUser(requestDTO.getOwnerID());
        bankAccount.setOwner(owner);
        bankAccount.setMinimumBalance(requestDTO.getMinimumBalance());
        bankAccount.setAccountNumber(this.generateUniqueRandomBankAccountNumber());
        bankAccount.setAccountStatus(AccountStatus.ACTIVE);
        bankAccount.setBalance(new BigDecimal(Constants.INITIAL_BANK_ACCOUNT_BALANCE));
        bankAccount.setBankCode(Constants.BANK_CODE);
        bankAccount.setBranchCode(Constants.BRANCH_NUMBER);
        this.getRepository().save(bankAccount);
        customerService.saveCustomer(owner);
    }
    //public abstract ResponseEntity<TransactionDTO> transferMoney(TransferRequestDTO request);

//    public abstract ResponseEntity<Map<String, String>> closeBankAccount();


//    @Scheduled(cron = "0 0 0 1 * ?") // Runs at 00:00:00 on the first day of every month
//    public void processMonthlyLoanRepayments() {
//        LocalDate today = LocalDate.now();
//        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
//        List<Loan> loansToRepay = loanRepository.findLoansDueForRepaymentByDate(firstDayOfMonth);
//
//        for (Loan loan : loansToRepay) {
//            if (!loan.isPaid()) {
//                // Assuming you have a method to calculate the repayment amount
//                BigDecimal repaymentAmount = calculateRepaymentAmount(loan);
//                try {
//                    makeLoanRepayment((T) loan.getBankAccount(), loan.getId(), repaymentAmount);
//                    // Log success or additional actions as necessary
//                } catch (Exception e) {
//                    // Log failure for auditing and further investigation
//                }
//            }
//        }
//    }
    public BigDecimal calculateRepaymentAmount(Loan loan) {
        BigDecimal interest = loan.getAmount()
                .multiply(loan.getInterestRate())
                .divide(new BigDecimal(100)) // Convert percentage to decimal
                .multiply(new BigDecimal(loan.getTerm()))
                .divide(new BigDecimal(12), RoundingMode.HALF_UP); // Assuming interestRate is annual

        BigDecimal totalRepayable = loan.getAmount().add(interest);
        return totalRepayable.divide(new BigDecimal(loan.getTerm()), RoundingMode.HALF_UP);
    }

    public void makeLoanRepayment(T bankAccount, UUID loanId, BigDecimal repaymentAmount) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found for this id :: " + loanId));

        if (loan.isPaid()) {
            throw new RuntimeException("This loan is already paid off.");
        }

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

        loanRepository.save(loan);
        this.getRepository().save(bankAccount);
    }

    public Loan provideLoanToCustomer(T bankAccount, BigDecimal amount, BigDecimal interestRate, LocalDate dueDate) {

        Loan loan = new Loan();
        loan.setAmount(amount);
        loan.setInterestRate(interestRate);
        loan.setDueDate(dueDate);
        loan.setBankAccount(bankAccount);

        bankAccount.getLoans().add(loan);
        this.getRepository().save(bankAccount);

        return loan; // Assuming you have a Loan repository or manage it through BankAccount
    }
    private String generateUniqueRandomBankAccountNumber(){
        String characters = "0123456789";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        Optional<BankAccount> bankAccounts = this.getRepository().findByAccountNumber("aa");
        // Generate 12 random characters and append them to the StringBuilder
        for (int i = 0; i < 13; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();

    }




    public ResponseEntity<TransactionDTO> depositMoney(DepositDTO depositDTO) throws Throwable {
        BankAccount account = bankAccountRepository.findById(depositDTO.getBankAccountID())
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        BigDecimal amountInNIS;

        // Check if the currency is already ILS to skip conversion
        if ("ILS".equals(depositDTO.getCurrency())) {
            amountInNIS = depositDTO.getAmount();
        } else {
            // Convert deposit amount to NIS if not already in ILS
            amountInNIS = currencyService.convertCurrency(depositDTO.getAmount(), depositDTO.getCurrency(), "ILS");
        }

        // Update account balance
        account.setBalance(account.getBalance().add(amountInNIS));

        // Create and save the transaction
        Transaction transaction = new Transaction();
        transaction.setBankAccount(account);
        transaction.setAmount(amountInNIS); // Record the transaction amount
      // Assuming you have a type field to distinguish between transactions
        transactionRepository.save(transaction);
        bankAccountRepository.save(account);

        // Convert the saved transaction to a TransactionDTO and return it
         // Implement this method to map your Transaction entity to TransactionDTO
        return new ResponseEntity<TransactionDTO>( HttpStatus.OK);
    }

    public BigDecimal viewBalance(UUID accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found for this id :: " + accountId));
        return bankAccount.getBalance();
    }

    public void transferMoney(TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, InsufficientFundsException {
        BankAccount fromAccount = bankAccountRepository.findById(transferRequestDTO.getFromBankAccount())
                .orElseThrow(() -> new BankAccountNotFoundException("Source bank account not found"));

        BankAccount toAccount = bankAccountRepository.findById(transferRequestDTO.getToBankAccount())
                .orElseThrow(() -> new BankAccountNotFoundException("Destination bank account not found"));

        BigDecimal transferAmount = transferRequestDTO.getAmount();

        // Optional: Convert transfer amount if you're handling multiple currencies and if necessary
        // transferAmount = currencyService.convertCurrency(transferAmount, fromCurrency, toCurrency);

        // Check for sufficient funds in the fromAccount
        if (fromAccount.getBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }

        // Deduct the amount from the fromAccount
        fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));

        // Add the amount to the toAccount
        toAccount.setBalance(toAccount.getBalance().add(transferAmount));

        // Save the updated account states
        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);

        // Record the transaction in both accounts
        recordTransaction(fromAccount, transferAmount.negate(), "Transfer Out");
        recordTransaction(toAccount, transferAmount, "Transfer In");
    }

    private void recordTransaction(BankAccount account, BigDecimal amount, String type) {
        Transaction transaction = new Transaction();
        transaction.setBankAccount(account);
        transaction.setAmount(amount); // Negative for withdrawals
        // This could be "Deposit", "Withdrawal", or "Transfer"
        transactionRepository.save(transaction);
    }

    public ResponseEntity<TransactionDTO> withdrawMoney(DepositDTO withdrawDTO) throws BankAccountNotFoundException {
        BankAccount account = bankAccountRepository.findById(withdrawDTO.getBankAccountID())
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));

        BigDecimal withdrawalAmount = withdrawDTO.getAmount();

        // Optional: Convert withdrawal amount if in a foreign currency
        if (!"ILS".equals(withdrawDTO.getCurrency())) { // Assuming account balance is in NIS
            withdrawalAmount = currencyService.convertCurrency(withdrawalAmount, withdrawDTO.getCurrency(), "ILS");
        }

        // Check for sufficient funds
        if (account.getBalance().compareTo(BigDecimal.valueOf(withdrawalAmount.doubleValue())) < 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }

        // Update the account balance
        account.setBalance(account.getBalance().subtract(withdrawalAmount));
        bankAccountRepository.save(account);

        // Record the transaction
        Transaction transaction = new Transaction();
        transaction.setBankAccount(account);
        transaction.setAmount(withdrawalAmount.negate()); // Use negative amount for withdrawal
         // Assume you have a type or similar property
        transactionRepository.save(transaction);
        return new ResponseEntity<TransactionDTO>( HttpStatus.OK);
    }

}
