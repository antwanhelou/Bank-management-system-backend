package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.schedule.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {


    @Query("SELECT t FROM Task t " +
            "WHERE t.status = 'PENDING'")
    public Optional<List<Task>> findAllPendingTasks();


}
