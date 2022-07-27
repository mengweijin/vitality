package com.github.mengweijin.flowable.service;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/6/21
 */
@Service
public class FlowableService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;
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

    /**
     * HashMap variables = new HashMap<String, Object>();
     * variables.put("approved", approved);
     * @param taskId
     * @param variables
     */
    public void handleTask(String taskId, HashMap variables) {
        taskService.complete(taskId, variables);
    }

    public List<Task> getTaskListByAssignee(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public List<Task> getTaskListByCandidateGroup(String candidateGroup) {
        return taskService.createTaskQuery().taskCandidateGroup(candidateGroup).list();
    }

    public List<HistoricActivityInstance> getHistoryByProcessInstanceId(String processInstanceId) {
        return historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                // 加上这个就是查询已经完成的流程实例
                //.finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
    }

    public List<ProcessDefinition> listProcessDefinition() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.orderByProcessDefinitionId().orderByProcessDefinitionVersion().desc();
        return processDefinitionQuery.list();
    }
}
