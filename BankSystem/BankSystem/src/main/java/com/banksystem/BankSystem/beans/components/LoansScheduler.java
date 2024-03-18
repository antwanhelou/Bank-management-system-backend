package com.banksystem.BankSystem.beans.components;

import com.banksystem.BankSystem.entities.bankaccounts.IndividualBankAccount;
import com.banksystem.BankSystem.entities.loans.Loan;
import com.banksystem.BankSystem.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class LoansScheduler {



    @Autowired
    private LoanRepository loanRepository;


    @Scheduled(cron = "0 0 0 1 * ?") // Runs at 00:00:00 on the first day of every month
    public void processMonthlyLoanRepayments() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        List<Loan> loansToRepay = loanRepository.findLoansDueForRepaymentByDate(firstDayOfMonth);

        for (Loan loan : loansToRepay) {
            BigDecimal repaymentAmount = calculateRepaymentAmount(loan);
            makeLoanRepayment(loan, repaymentAmount);

        }
    }
    private BigDecimal calculateRepaymentAmount(Loan loan) {
        BigDecimal interest = loan.getAmount()
                .multiply(loan.getInterestRate())
                .divide(new BigDecimal(100)) // Convert percentage to decimal
                .multiply(new BigDecimal(loan.getTerm()))
                .divide(new BigDecimal(12), RoundingMode.HALF_UP); // Assuming interestRate is annual

        BigDecimal totalRepayable = loan.getAmount().add(interest);
        return totalRepayable.divide(new BigDecimal(loan.getTerm()), RoundingMode.HALF_UP);
    }

    private void makeLoanRepayment(Loan loan, BigDecimal repaymentAmount) {

        BigDecimal actualRepaymentAmount = repaymentAmount.compareTo(loan.getAmount()) > 0 ? loan.getAmount() : repaymentAmount;

        BigDecimal newLoanAmount = loan.getAmount().subtract(actualRepaymentAmount);

        if (newLoanAmount.compareTo(BigDecimal.ZERO) <= 0) {
            loan.setPaid(true);
            loan.setAmount(BigDecimal.ZERO);
        } else {
            loan.setAmount(newLoanAmount);
        }
        loanRepository.save(loan);
    }

}
