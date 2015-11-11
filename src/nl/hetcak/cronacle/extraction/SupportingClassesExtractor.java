package nl.hetcak.cronacle.extraction;

import nl.hetcak.cronacle.model.Configuration;

import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by anoordover on 10-11-15.
 */
public class SupportingClassesExtractor extends AbstractExtractor implements Extractor {
    @Override
    public void extract(ZipEntry zipEntry, ZipInputStream zipInputStream, File file) {
        String filename = Configuration.getInstance().getRootLocation() + File.separator +
                Configuration.getInstance().getLoaderLocation() + File.separator + zipEntry.getName();
        executeExtractionIfNeeded(zipInputStream, filename);
    }

    @Override
    public boolean canExtract(ZipEntry zipEntry) {
        return zipEntry.getName().startsWith("com/") && zipEntry.getName().endsWith(".class");
    }
}
