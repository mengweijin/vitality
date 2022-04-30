package com.github.mengweijin.flowable.service;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/4/10
 */
@Service
public class HolidayService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Transactional(rollbackFor = Exception.class)
    public void startProcess() {
        String employee = "Jack";
        Integer nrOfHolidays = 2;
        String description = "Personal things.";

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("holidayRequest", variables);
    }

    public List<Task> getManagerTasks() {
        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("managers").list();

        System.out.println("You have " + taskList.size() + " tasks:");
        for (int i=0; i < taskList.size(); i++) {
            System.out.println((i+1) + ") " + taskList.get(i).getName());

            Map<String, Object> processVariables = taskService.getVariables(taskList.get(i).getId());
            System.out.println(processVariables.get("employee") + " wants " +
                    processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");
        }

        return taskList;
    }

    public void approved() {
        // 前端传过来
        String taskId = getManagerTasks().get(0).getId();
        // 前端传过来
        boolean approved = true;

        HashMap  variables = new HashMap<String, Object>();
        variables.put("approved", approved);
        taskService.complete(taskId, variables);
    }

  public List<HistoricActivityInstance> finishedHistory() {
      String processInstanceId = getManagerTasks().get(0).getProcessInstanceId();
      List<HistoricActivityInstance> activities =
              historyService.createHistoricActivityInstanceQuery()
                      .processInstanceId(processInstanceId)
                      .finished()
                      .orderByHistoricActivityInstanceEndTime().asc()
                      .list();

      for (HistoricActivityInstance activity : activities) {
          System.out.println(activity.getActivityId() + " took " + activity.getDurationInMillis() + " milliseconds");
      }

      return activities;
    }


    public List<ProcessDefinition> listProcessDefinition() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.orderByProcessDefinitionId().orderByProcessDefinitionVersion().desc();
        List<ProcessDefinition> list = processDefinitionQuery.list();

        list.forEach( processDefinition -> {
            Deployment deployment = repositoryService.createDeploymentQuery()
                    .deploymentId(processDefinition.getDeploymentId())
                    .singleResult();
            deployment.getDeploymentTime();
        });

        return list;
    }
}
