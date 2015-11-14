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
import nl.hetcak.cronacle.view.RootController;

import java.io.IOException;

/**
 * Created by anoordover on 5-11-15.
 */
public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CarManager");

        initRootLayout();

    }

    private void initRootLayout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("/nl/hetcak/cronacle/view/rootLayout.fxml"));
            BorderPane rootLayout = fxmlLoader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            RootController rootController = fxmlLoader.getController();
            rootController.setPrimaryStage(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
