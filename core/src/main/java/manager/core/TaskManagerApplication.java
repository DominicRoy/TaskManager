package manager.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.core.csv.CSVReader;
import manager.core.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TaskManagerApplication extends Application
{
    
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/view/TaskManagerMain.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop() throws Exception
    {
        System.exit(0);
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
