package nl.hetcak.cronacle.extraction;

import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by anoordover on 10-11-15.
 */
public interface Extractor {

    void extract(ZipEntry zipEntry, ZipInputStream zipInputStream, File file);

    boolean canExtract(ZipEntry zipEntry);
}
