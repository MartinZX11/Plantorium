package com.computalimpo.plantorium.POJO;

public class TaskPOJO {
    private String taskText;
    private String priority;

    public TaskPOJO(String taskText, String priority) {
        this.taskText = taskText;
        this.priority = priority;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
