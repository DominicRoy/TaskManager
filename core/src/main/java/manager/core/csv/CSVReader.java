package manager.core.csv;

import manager.core.tasks.Task;
import manager.core.tasks.TaskPriority;
import manager.core.util.TimeConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements TimeConverter
{
    
    private BufferedReader reader;
    
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
        return taskList;
    }
    
}
