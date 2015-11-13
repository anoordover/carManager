package nl.hetcak.cronacle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.hetcak.cronacle.view.CarActionsController;
import nl.hetcak.cronacle.view.FileSelectorController;

import java.io.IOException;

/**
 * Created by anoordover on 5-11-15.
 */
public class MainApp extends Application {
    private FileSelectorController fileSelectorController;
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CarManager");

        initRootLayout();

        showFileSelector();

        showCarActions();
    }

    private void showCarActions() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/CarActions.fxml"));
            VBox vBox = fxmlLoader.load();
            rootLayout.setCenter(vBox);
            CarActionsController carActionsController = fxmlLoader.getController();
            carActionsController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showFileSelector() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/FileSelector.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            rootLayout.setLeft(anchorPane);
            fileSelectorController = fxmlLoader.getController();
            fileSelectorController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("./view/rootLayout.fxml"));
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public FileSelectorController getFileSelectorController() {
        return fileSelectorController;
    }
}
