package nl.hetcak.cronacle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import nl.hetcak.cronacle.view.FileSelectorController;

import java.io.IOException;

/**
 * Created by anoordover on 5-11-15.
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CarManager");

        initRootLayout();

        showFileSelector();
    }

    private void showFileSelector() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/FileSelector.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            rootLayout.setLeft(anchorPane);
            FileSelectorController controler = fxmlLoader.getController();
            controler.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
            rootLayout = fxmlLoader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
