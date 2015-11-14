package nl.hetcak.cronacle.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import nl.hetcak.cronacle.model.Configuration;
import nl.hetcak.cronacle.model.ShortNameFile;

import java.io.File;
import java.util.Iterator;

/**
 * Created by anoordover on 5-11-15.
 */
public class FileSelectorController {

    private RootController rootController;

    @FXML
    private Label rootLocation;

    @FXML
    private TreeView<ShortNameFile> treeView = new TreeView<>();

    @FXML
    private void initialize() {
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

            @Override
            public ObservableList<TreeItem<ShortNameFile>> getChildren() {
                if (isFirstTimeChildren) {
                    isFirstTimeChildren = false;

                    // First getChildren() call, so we actually go off and
                    // determine the children of the File contained in this TreeItem.
                    super.getChildren().setAll(buildChildren(this));
                }
                return super.getChildren();
            }

            @Override
            public boolean isLeaf() {
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
        File file = rootController.showDirectoryDialog();
        if (file != null) {
            rootLocation.setText(file.getPath());
            Configuration.getInstance().setRootLocation(file.getPath());
            buildFileSystemBrowser(file.getPath());
            treeView.setCellFactory(CheckBoxTreeCell.<ShortNameFile>forTreeView());
        }
    }

    public TreeView<ShortNameFile> getTreeView() {
        return treeView;
    }

    public void init(RootController rootController) {
        this.rootController = rootController;
    }

    public Iterable<File> getSelectedFiles() {
        return new TreeViewFlatterner(treeView);
    }

    private class TreeViewFlatterner implements Iterable<File> {

        private final TreeView<ShortNameFile> treeView;

        public TreeViewFlatterner(TreeView<ShortNameFile> treeView) {
            this.treeView = treeView;
        }

        @Override
        public Iterator<File> iterator() {
            return new TreeViewValueIterator(treeView);
        }
    }


    private class TreeViewIterator implements Iterator<CheckBoxTreeItem<ShortNameFile>> {
        private final TreeView<ShortNameFile> treeView;
        private CheckBoxTreeItem<ShortNameFile> currentItem;


        public TreeViewIterator(TreeView<ShortNameFile> treeView) {
            this.treeView = treeView;
            navigateToFirstItem();
        }

        private void navigateToFirstItem() {
            currentItem = (CheckBoxTreeItem<ShortNameFile>) treeView.getRoot();
            if (currentItem != null && !currentItem.isSelected()) {
                navigateToNextItem();
            }
        }

        private void navigateToNextItem() {
            CheckBoxTreeItem<ShortNameFile> newCurrentItem = getFirstChild(currentItem);
            if (newCurrentItem != null) {
                currentItem = newCurrentItem;
            } else {
                newCurrentItem = (CheckBoxTreeItem<ShortNameFile>) currentItem.nextSibling();
                if (newCurrentItem != null) {
                    currentItem = newCurrentItem;
                } else {
                    currentItem = nextSiblingOfAParent(currentItem);
                }
            }
            if (currentItem != null && !currentItem.isSelected()) {
                navigateToNextItem();
            }
        }

        private CheckBoxTreeItem<ShortNameFile> nextSiblingOfAParent(CheckBoxTreeItem<ShortNameFile> currentItem) {
            if (currentItem.getParent() == null) {
                return null;
            }
            if (currentItem.getParent().nextSibling() != null) {
                return (CheckBoxTreeItem<ShortNameFile>) currentItem.getParent().nextSibling();
            } else {
                return nextSiblingOfAParent((CheckBoxTreeItem<ShortNameFile>) currentItem.getParent());
            }
        }

        @Override
        public boolean hasNext() {
            return currentItem != null;
        }

        @Override
        public CheckBoxTreeItem<ShortNameFile> next() {
            CheckBoxTreeItem<ShortNameFile> result = currentItem;
            navigateToNextItem();
            return result;
        }

        private CheckBoxTreeItem<ShortNameFile> getFirstChild(CheckBoxTreeItem<ShortNameFile> item) {
            if (item.getChildren() == null || item.getChildren().size() == 0) {
                return null;
            }
            return (CheckBoxTreeItem<ShortNameFile>) item.getChildren().get(0);
        }

    }

    private class TreeViewValueIterator implements Iterator<File> {
        private final TreeViewIterator iterator;

        public TreeViewValueIterator(TreeView<ShortNameFile> treeView) {
            iterator = new TreeViewIterator(treeView);
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public File next() {
            return iterator.next().getValue().getFile();
        }
    }
}
