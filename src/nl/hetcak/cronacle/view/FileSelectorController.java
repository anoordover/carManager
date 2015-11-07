package nl.hetcak.cronacle.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import nl.hetcak.cronacle.MainApp;
import nl.hetcak.cronacle.model.ShortNameFile;

import java.io.File;

/**
 * Created by anoordover on 5-11-15.
 */
public class FileSelectorController {

    @FXML
    private Label rootLocation;

    @FXML
    private TreeView<ShortNameFile> treeView = new TreeView<>();

    private MainApp mainApp;

    @FXML
    private void initialize() {
        rootLocation.setText("/home/anoordover");
    }


    private TreeView buildFileSystemBrowser(String rootDirectory) {
        CheckBoxTreeItem<ShortNameFile> root = createNode(new ShortNameFile(new File(rootDirectory)));

        treeView.setRoot(root);
        return treeView;
    }

    // This method creates a TreeItem to represent the given File. It does this
    // by overriding the TreeItem.getChildren() and TreeItem.isLeaf() methods
    // anonymously, but this could be better abstracted by creating a
    // 'FileTreeItem' subclass of TreeItem. However, this is left as an exercise
    // for the reader.
    private CheckBoxTreeItem<ShortNameFile> createNode(final ShortNameFile f) {
        return new CheckBoxTreeItem<ShortNameFile>(f) {
            // We cache whether the File is a leaf or not. A File is a leaf if
            // it is not a directory and does not have any files contained within
            // it. We cache this as isLeaf() is called often, and doing the
            // actual check on File is expensive.
            private boolean isLeaf;

            // We do the children and leaf testing only once, and then set these
            // booleans to false so that we do not check again during this
            // run. A more complete implementation may need to handle more
            // dynamic file system situations (such as where a folder has files
            // added after the TreeView is shown). Again, this is left as an
            // exercise for the reader.
            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override public ObservableList<TreeItem<ShortNameFile>> getChildren() {
                if (isFirstTimeChildren) {
                    isFirstTimeChildren = false;

                    // First getChildren() call, so we actually go off and
                    // determine the children of the File contained in this TreeItem.
                    super.getChildren().setAll(buildChildren(this));
                }
                return super.getChildren();
            }

            @Override public boolean isLeaf() {
                if (isFirstTimeLeaf) {
                    isFirstTimeLeaf = false;
                    File f = getValue().getFile();
                    isLeaf = f.isFile();
                }

                return isLeaf;
            }

            private ObservableList<CheckBoxTreeItem<ShortNameFile>> buildChildren(CheckBoxTreeItem<ShortNameFile> treeItem) {
                File f = treeItem.getValue().getFile();
                if (f != null && f.isDirectory()) {
                    File[] files = f.listFiles();
                    if (files != null) {
                        ObservableList<CheckBoxTreeItem<ShortNameFile>> children = FXCollections.observableArrayList();

                        for (File childFile : files) {
                            children.add(createNode(new ShortNameFile(childFile)));
                        }

                        return children;
                    }
                }

                return FXCollections.emptyObservableList();
            }
        };
    }


    @FXML
    private void handleDirectorySelectButton() {
        String root = rootLocation.getText();
        buildFileSystemBrowser(root);
        treeView.setCellFactory(CheckBoxTreeCell.<ShortNameFile>forTreeView());

        /*

        CheckBoxTreeItem<String> jonathanGiles = new CheckBoxTreeItem<>("Jonathan");
        CheckBoxTreeItem<String> juliaGiles = new CheckBoxTreeItem<>("Julia");
        CheckBoxTreeItem<String> mattGiles = new CheckBoxTreeItem<>("Matt");
        CheckBoxTreeItem<String> sueGiles = new CheckBoxTreeItem<>("Sue");
        CheckBoxTreeItem<String> ianGiles = new CheckBoxTreeItem<>("Ian");

        CheckBoxTreeItem<String> gilesFamily = new CheckBoxTreeItem<>("Giles Family");
        gilesFamily.setExpanded(true);
        gilesFamily.getChildren().addAll(jonathanGiles, juliaGiles, mattGiles, sueGiles, ianGiles
        );

        // create the treeView
        treeView.setRoot(gilesFamily);

        // set the cell factory
        treeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
         */
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
