package manager.core;

import manager.core.csv.CSVReader;
import manager.core.tasks.Task;

import java.io.IOException;
import java.util.List;

public class TaskManagerApplication
{
    
    public static void main(String[] args)
    {
        CSVReader reader = new CSVReader();
        try
        {
            List<Task> tasks = reader.readCsv(
                    "C:\\Users\\Dico\\IdeaProjects\\TaskManager\\TaskStorage\\PrototypeStorageFile.csv");
            tasks.forEach(task -> {
                System.out.println(
                        task.getTaskTitle() + task.getPriority() + task.getDescription() + task.getCreationDate() +
                        task.getDoBeforeDate());
            });
        } catch (IOException e)
        {
            System.out.println("Try again");
            e.printStackTrace();
        }
    }
    
}
