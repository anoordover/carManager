package nl.hetcak.cronacle.extraction;

import nl.hetcak.cronacle.model.Configuration;

import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by anoordover on 10-11-15.
 */
public class DefinitionExtractor extends AbstractExtractor implements Extractor {
    @Override
    public void extract(ZipEntry zipEntry, ZipInputStream zipInputStream, File file) {
        String relativePath = bepaalRelatieveDirectory(file);
        String filename = Configuration.getInstance().getRootLocation() + File.separator +
                Configuration.getInstance().getXmlLocation() + File.separator +
                relativePath + File.separator +
                zipEntry.getName();
        executeExtraction(zipInputStream, filename);
    }

    private String bepaalRelatieveDirectory(File file) {
        String root = Configuration.getInstance().getRootLocation() + File.separator + Configuration.getInstance().getCarsLocation();
        String result = file.getParent();
        if (result.startsWith(root)) {
            result = result.substring(root.length());
        } else {
            throw new IllegalStateException("File should be in root!");
        }
        return result;
    }

    @Override
    public boolean canExtract(ZipEntry zipEntry) {
        return zipEntry.getName().endsWith(".xml") && !zipEntry.getName().startsWith("META-INF");
    }
}
