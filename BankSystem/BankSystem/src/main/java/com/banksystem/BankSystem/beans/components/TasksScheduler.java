package com.banksystem.BankSystem.beans.components;


import com.banksystem.BankSystem.entities.schedule.Task;
import com.banksystem.BankSystem.entities.users.Administrator;
import com.banksystem.BankSystem.enums.RequestStatus;
import com.banksystem.BankSystem.enums.TaskStatus;
import com.banksystem.BankSystem.repository.AdministratorRepository;
import com.banksystem.BankSystem.repository.TaskRepository;
import com.banksystem.BankSystem.services.CustomerRequestService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TasksScheduler {

    @Autowired
    private TaskRepository taskRepository;


    @Autowired
    private AdministratorRepository administratorRepository;
    private Queue<Task> pendingTasks = new PriorityQueue<>();

    // Other methods and fields...

    @PostConstruct
    public void init() {
        Optional<List<Task>> searchResult = taskRepository.findAllPendingTasks();
        if(searchResult.isPresent()){
            List<Task> tasks = searchResult.get();
            pendingTasks.addAll(tasks);
        }
    }

    public Task getMostUrgentTask(){
        if(pendingTasks.peek() != null){
            return pendingTasks.poll();
        }
        return null;
    }

    public void scheduleTask(Task task){
        pendingTasks.add(task);
    }


    @Transactional
    @Scheduled(fixedRate = 4 * 60 * 60 * 1000) // Run every four hours
    public void assignTasks() {
        System.out.println("TaskScheduler is Running!");
        Optional<Administrator> searchResult = administratorRepository.findMostAvailableAdministrator();
        while(!pendingTasks.isEmpty() && searchResult.isPresent()){
            Task task = pendingTasks.poll();
            Administrator administrator = searchResult.get();
            task.setStatus(TaskStatus.IN_PROGRESS);
            task.setAssignedAdministrator(administrator);
            administrator.addTask(task);
            administratorRepository.save(administrator);
            task.getCustomerRequest().setStatus(RequestStatus.IN_PROGRESS);
            taskRepository.save(task);
            searchResult = administratorRepository.findMostAvailableAdministrator();
        }

    }
}
