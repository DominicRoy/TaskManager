package manager.gui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import org.joda.time.DateTime;

import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskManagerController implements TimeConverter
{
    
    @FXML
    private ListView<String> taskList;
    
    @FXML
    private Button addEntryButton, deleteEntryButton;
    
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
        titleField.setPromptText("New Task");
        descriptionField.setPromptText("What to do?");
        
        ObservableList<String> priorityType = FXCollections.observableArrayList();
        for (TaskPriority priority : TaskPriority.values())
        {
            priorityType.add(priority.toString());
        }
        
        priorityTypeDropdown.setItems(priorityType);
        priorityTypeDropdown.getSelectionModel()
                            .select(0);
        
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        
        addEntryButton.setOnAction(action -> {
            
            
            if (titleField.getText()
                          .isEmpty() || descriptionField.getText()
                                                        .isEmpty() || titleField.getText().matches("(.*)[,*^<>?{}\\-|/](.*)")
                    || descriptionField.getText().matches("(.*)[,*^<>?{}\\-|/](.*)"))
            {
                action.consume();
            } else
            {
                try
                {
                    reader.addTaskEntry
                            ("C:\\Users\\Dico\\IdeaProjects\\TaskManager\\TaskStorage\\PrototypeStorageFile.csv",
                                        new Task.TaskBuilder(stringToTimestamp(DateTime.now().toString("yyyy-MM-dd HH:mm:ss")),
                                                             stringToTimestamp(DateTime.now().toString("yyyy-MM-dd HH:mm:ss")),
                                                             TaskPriority.valueOf(priorityTypeDropdown
                                                                                          .getSelectionModel()
                                                                                                      .getSelectedItem()),
                                                             titleField.getText(),
                                                             descriptionField.getText()).build());
                    
                    refreshTaskList();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            
        });
        
        deleteEntryButton.setOnAction(action -> {
            
            if (taskList.getSelectionModel()
                        .getSelectedIndex() == -1)
            {
                action.consume();
            } else
            {
                try
                {
                    String selectedTask = taskList.getSelectionModel()
                                                  .getSelectedItem();
                    
                    Pattern pattern = Pattern.compile("(Title: ([A-z0-9'\".$#@!%&();_=+]* )*([A-z0-9'\".$#@!%&();_=+]*))(,(.*))");
                    Matcher matcher = pattern.matcher(selectedTask);
                    
                    matcher.find();
                    String title = matcher.group(1)
                                          .substring(7, matcher.group(1)
                                                               .length());
                    
                    reader.deleteTaskEntry
                            ("C:\\Users\\Dico\\IdeaProjects\\TaskManager\\TaskStorage\\PrototypeStorageFile.csv",
                             title);
                    refreshTaskList();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        
        refreshTaskList();
    }
    
    @FXML
    private void refreshTaskList()
    {
        Platform.runLater(() -> {
            taskList.getItems()
                    .setAll(FXCollections.observableArrayList());
            
            ObservableList<String> observableStrings = FXCollections.observableArrayList();
            try
            {
                reader.readCsv("C:\\Users\\Dico\\IdeaProjects\\TaskManager\\TaskStorage\\PrototypeStorageFile.csv")
                      .forEach(task -> {
                          observableStrings.add(
                                  "Title: " + task.getTaskTitle() + ", Start Time: " + task.getCreationDate());
                      });
                taskList.getItems()
                        .setAll(observableStrings);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            
        });
    }
}
