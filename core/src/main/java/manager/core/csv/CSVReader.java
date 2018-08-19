package manager.core.csv;

import manager.core.tasks.Task;
import manager.core.tasks.TaskPriority;
import manager.core.util.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements TimeConverter
{
    private static final Logger LOG = LoggerFactory.getLogger(CSVReader.class);
    private BufferedReader reader;
    private PrintWriter writer;
    
    public void addTaskEntry(String fileName, Task newTask) throws IOException
    {
        writer = new PrintWriter(new FileWriter(fileName, true));
        
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append(newTask.getCreationDate()
                                    .toLocalDateTime() + ",");
        stringBuilder.append(newTask.getDoBeforeDate()
                                    .toLocalDateTime() + ",");
        stringBuilder.append(newTask.getPriority() + ",");
        stringBuilder.append(newTask.getTaskTitle() + ",");
        stringBuilder.append(newTask.getDescription() + "\n");
        
        writer.write(stringBuilder.toString());
        writer.close();
    }
    
    public void deleteTaskEntry(String fileName, String taskTitle) throws IOException
    {
        List<Task> tasks = readCsv(fileName);
        List<Task> modifiedList = new ArrayList<>();
        for (Task task : tasks)
        {
            if (!task.getTaskTitle()
                     .equals(taskTitle))
            {
                modifiedList.add(task);
            }
        }
        StringBuilder builder = new StringBuilder();
        
        modifiedList.forEach(task -> {
            builder.append(task.getCsvFormattedTask() + "\n");
        });
        
        writer = new PrintWriter(new FileWriter(fileName));
        writer.write(builder.toString());
        writer.close();
    }
    
    public List<Task> readCsv(String fileName) throws IOException
    {
        reader = new BufferedReader(new FileReader(fileName));
        
        String line = "";
        
        List<Task> taskList = new ArrayList<>();
        
        while ((line = reader.readLine()) != null)
        {
            String[] taskAttributes = line.split(",");
            
            
            // TODO: fix this method so that misentries or a lack of entries won't break the application
            
            if (taskAttributes.length > 0)
            {
                String description = "";
                try
                {
                    description = taskAttributes[4];
                }
                catch(ArrayIndexOutOfBoundsException e)
                {
                    LOG.debug("Description field is empty for the task under analysis");
                }
                
                taskList.add(
                        new Task.TaskBuilder(stringToTimestamp(taskAttributes[0]), stringToTimestamp(taskAttributes[1]),
                                             TaskPriority.valueOf(taskAttributes[2]), taskAttributes[3],
                                             description).build());
            }
        }
        reader.close();
        return taskList;
    }
    
}
