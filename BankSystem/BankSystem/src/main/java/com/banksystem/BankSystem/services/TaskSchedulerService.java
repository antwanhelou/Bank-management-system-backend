package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.beans.components.TasksScheduler;
import com.banksystem.BankSystem.entities.schedule.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class TaskSchedulerService {

    @Autowired
    private TasksScheduler tasksScheduler;
    public void scheduleTask(Task task){
        tasksScheduler.scheduleTask(task);
    }

}
