package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.loans.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    @Query("SELECT l FROM Loan l WHERE l.dueDate <= :date")
    List<Loan> findLoansDueForRepaymentByDate(LocalDate date);



}
