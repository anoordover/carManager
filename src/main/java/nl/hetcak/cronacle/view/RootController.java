package nl.hetcak.cronacle.view;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by anoordover on 13-11-15.
 */
public class RootController {

    @FXML
    CarActionsController carActionsController;
    @FXML
    FileSelectorController fileSelectorController;

    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private Stage primaryStage;

    @FXML
    private void initialize() {
        System.out.println("Application started");
        carActionsController.init(this);
        fileSelectorController.init(this);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public File showDirectoryDialog() {
        return directoryChooser.showDialog(primaryStage);
    }

    public Iterable<File> getSelectedFilesFromFileSelector() {
        return fileSelectorController.getSelectedFiles();
    }
}
