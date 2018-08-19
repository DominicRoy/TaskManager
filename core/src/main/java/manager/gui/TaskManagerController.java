package manager.gui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import manager.core.csv.CSVReader;
import manager.core.tasks.Task;
import manager.core.tasks.TaskPriority;
import manager.core.util.TimeConverter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TaskManagerController implements TimeConverter
{
    
    @FXML
    private ListView<String> taskList;
    
    @FXML
    private Button addEntryButton, displayTasksButton, deleteEntryButton;
    
    @FXML
    private JFXDatePicker startDatePicker, endDatePicker;
    
    @FXML
    private JFXComboBox<String> priorityTypeDropdown;
    
    @FXML
    private TextField titleField, descriptionField;
    
    private CSVReader reader = new CSVReader();
    
    @FXML
    private void initialize()
    {
        
        ObservableList<String> priorityType = FXCollections.observableArrayList();
        for(TaskPriority priority : TaskPriority.values())
        {
            priorityType.add(priority.toString());
        }
        priorityTypeDropdown.setItems(priorityType);
        priorityTypeDropdown.getSelectionModel().select(0);
        addEntryButton.setOnAction(action -> {
            try
            {
                reader.addTaskEntry("C:\\Users\\Dico\\IdeaProjects\\TaskManager\\TaskStorage\\PrototypeStorageFile.csv",
                                    new Task.TaskBuilder(stringToTimestamp(startDatePicker.getValue().toString()+" 00:00:00"),
                                                         stringToTimestamp(endDatePicker.getValue().toString()+" 00:00:00"),
                                                         TaskPriority.valueOf(priorityTypeDropdown.getSelectionModel().getSelectedItem()),
                                                         titleField.getText(),
                                                         descriptionField.getText()).build());
                
                refreshTaskList();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        
        deleteEntryButton.setOnAction(action -> {
            try{
                reader.deleteTaskEntry("C:\\Users\\Dico\\IdeaProjects\\TaskManager\\TaskStorage\\PrototypeStorageFile.csv", "New Title");
                refreshTaskList();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        });
        
        refreshTaskList();
    }
    
    @FXML
    private void refreshTaskList()
    {
        Platform.runLater(() -> {
            taskList.getItems().setAll(FXCollections.observableArrayList());
            
            ObservableList<String> observableStrings = FXCollections.observableArrayList();
            try
            {
                reader.readCsv("C:\\Users\\Dico\\IdeaProjects\\TaskManager\\TaskStorage\\PrototypeStorageFile.csv")
                      .forEach(task -> {
                          observableStrings.add(
                                  "Title: " + task.getTaskTitle() + "Start Time: " + task.getCreationDate());
                      });
                taskList.getItems().setAll(observableStrings);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            
        });
    }
}
