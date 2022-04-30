package com.github.mengweijin.flowable.controller;

import com.github.mengweijin.flowable.service.HolidayService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/4/10
 */
@RequestMapping("/holiday")
@RestController
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @PostMapping(value="/process")
    public void startProcessInstance() {
        holidayService.startProcess();
    }

    @GetMapping(value="/managerTask")
    public List<Task> getManagerTasks() {
        return holidayService.getManagerTasks();
    }

    @GetMapping(value="/approved")
    public void approved() {
        holidayService.approved();
    }

    @GetMapping(value="/history")
    public List<HistoricActivityInstance> history() {
        return holidayService.finishedHistory();
    }

    @GetMapping(value="/listProcessDefinition")
    public List<ProcessDefinition> listProcessDefinition() {
        return holidayService.listProcessDefinition();
    }
}
