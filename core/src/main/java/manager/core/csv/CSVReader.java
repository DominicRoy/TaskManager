package manager.core.csv;

import manager.core.tasks.Task;
import manager.core.tasks.TaskPriority;
import manager.core.util.TimeConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements TimeConverter
{
    
    private BufferedReader reader;
    private PrintWriter writer;
    
    public void addTaskEntry(String fileName, Task newTask) throws IOException
    {
        writer = new PrintWriter(new FileWriter(fileName, true));
        
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append(newTask.getCreationDate().toLocalDateTime()+",");
        stringBuilder.append(newTask.getDoBeforeDate().toLocalDateTime()+",");
        stringBuilder.append(newTask.getPriority()+",");
        stringBuilder.append(newTask.getTaskTitle()+",");
        stringBuilder.append(newTask.getDescription()+"\n");
        
        writer.write(stringBuilder.toString());
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
            
            if (taskAttributes.length > 0)
            {
                taskList.add(
                        new Task.TaskBuilder(stringToTimestamp(taskAttributes[0]), stringToTimestamp(taskAttributes[1]),
                                             TaskPriority.valueOf(taskAttributes[2]), taskAttributes[3],
                                             taskAttributes[4]).build());
            }
        }
        reader.close();
        return taskList;
    }
    
}
