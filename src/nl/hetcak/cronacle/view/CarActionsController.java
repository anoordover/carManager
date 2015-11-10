package nl.hetcak.cronacle.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import nl.hetcak.cronacle.MainApp;
import nl.hetcak.cronacle.extraction.Extract;
import nl.hetcak.cronacle.extraction.ExtractSupportingClasses;
import nl.hetcak.cronacle.model.ShortNameFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by anoordover on 10-11-15.
 */
public class CarActionsController {

    private MainApp mainApp;
    private static final Set<Extract> EXTRACTORS = new HashSet<>();

    static {
        EXTRACTORS.add(new ExtractSupportingClasses());
    }

    @FXML
    private void initialize() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void uitpakkenSelectedCars() {
        TreeView<ShortNameFile> tree = mainApp.getFileSelectorController().getTreeView();
        CheckBoxTreeItem<ShortNameFile> root = (CheckBoxTreeItem<ShortNameFile>) tree.getRoot();
        if (root != null) {
            verwerkItem(root);
            verwerkChildren(root);
        }
    }

    private void verwerkChildren(CheckBoxTreeItem<ShortNameFile> root) {
        if (root.getValue().getFile().isDirectory()) {
            for (TreeItem<ShortNameFile> child : root.getChildren()) {
                verwerkItem((CheckBoxTreeItem<ShortNameFile>) child);
                verwerkChildren((CheckBoxTreeItem<ShortNameFile>) child);
            }
        }
    }

    private void verwerkItem(CheckBoxTreeItem<ShortNameFile> root) {
        if (root.getValue().getFile().isFile() && root.isSelected()) {
            ZipInputStream zis = null;
            try {
                System.out.println("is File");
                zis = new ZipInputStream(new FileInputStream(root.getValue().getFile()));
                ZipEntry ze = zis.getNextEntry();
                while (ze != null) {
                    if (!ze.isDirectory()) {
                        System.out.println(ze.getName());
                        for (Extract extractor : EXTRACTORS) {
                            if (extractor.canExtract(ze)) {
                                extractor.extract(ze, zis);
                            }
                        }
                    }
                    ze = zis.getNextEntry();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (zis != null) {
                        zis.closeEntry();
                        zis.close();
                    }
                } catch (IOException ignore) {
                }
            }
        }
    }
}
