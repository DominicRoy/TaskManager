package manager.core.tasks;

import org.joda.time.DateTime;

public class Task
{
    private DateTime creationDate;
    private DateTime doBeforeDate;
    private TaskPriority priority;
    private String taskTitle;
    private String description;
    
    private Task(TaskBuilder builder)
    {
        this.doBeforeDate = builder.doBeforeDate;
        this.creationDate = builder.creationDate;
        this.description = builder.description;
        this.taskTitle = builder.taskTitle;
        this.priority = builder.priority;
    }
    
    public DateTime getCreationDate()
    {
        return creationDate;
    }
    
    public DateTime getDoBeforeDate()
    {
        return doBeforeDate;
    }
    
    public TaskPriority getPriority()
    {
        return priority;
    }
    
    public String getTaskTitle()
    {
        return taskTitle;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public static class TaskBuilder
    {
        private DateTime creationDate;
        private DateTime doBeforeDate;
        private TaskPriority priority;
        private String taskTitle;
        private String description;
        
        public TaskBuilder(DateTime creationDate, DateTime doBeforeDate, TaskPriority priority, String taskTitle, String description)
        {
            this.creationDate = creationDate;
            this.doBeforeDate = doBeforeDate;
            this.priority = priority;
            this.taskTitle = taskTitle;
            this.description = description;
        }
        
        public Task build()
        {
            return new Task(this);
        }
    }
    
}
