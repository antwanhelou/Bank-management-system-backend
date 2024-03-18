package com.banksystem.BankSystem.services;


import com.banksystem.BankSystem.entities.schedule.CustomerRequest;
import com.banksystem.BankSystem.entities.schedule.Task;
import com.banksystem.BankSystem.enums.TaskPriority;
import com.banksystem.BankSystem.enums.TaskStatus;
import com.banksystem.BankSystem.exceptions.object_not_found.TaskNotFoundException;
import com.banksystem.BankSystem.repository.TaskRepository;
import com.banksystem.BankSystem.utilities.ResultHolder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskSchedulerService taskSchedulerService;
    @Transactional
    public ResponseEntity<Map<String, String>> createTask(CustomerRequest request){
        Task task = Task.builder().build();
        task.setTaskName(request.getTitle());
        task.setCreationTime(Instant.now());
        task.setStatus(TaskStatus.PENDING);
        task.setDeadline(Instant.now().plus(Duration.ofDays(7)));
        task.setCustomerRequest(request);
        task.setDescription("Please Process Request Before: " + task.getDeadline());
        switch (request.getRequestType()){
            case ACCOUNT_CLOSURE, ACCOUNT_ACTIVATION -> task.setTaskPriority(TaskPriority.URGENT);
            case ACCOUNT_CREATION -> task.setTaskPriority(TaskPriority.HIGH);
            case DEBIT_CARD, LOAN -> task.setTaskPriority(TaskPriority.NORMAL);
        }
        taskRepository.save(task);
        taskSchedulerService.scheduleTask(task);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);

    }

    public ResponseEntity<Map<String, String>> taskDone(Task task) throws TaskNotFoundException {
        task.setStatus(TaskStatus.DONE);
        taskRepository.save(task);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }

}
