package sp.javasudokuprojects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sp.javasudokuprojects.buildLogic.SudokuBuildLogic;
import sp.javasudokuprojects.userInterface.IUserInterfaceContract;
import sp.javasudokuprojects.userInterface.UserInterfaceImpl;

import java.io.IOException;

public class SudokuApplication extends Application {
    private IUserInterfaceContract.View uiImpl;
    @Override
    public void start(Stage stage) throws IOException {
        uiImpl=new UserInterfaceImpl(stage);
        try{
            SudokuBuildLogic.build(uiImpl);
            //stage.show();
        }catch(IOException e){
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}