package nl.hetcak.cronacle.extraction;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by anoordover on 10-11-15.
 */
public interface Extract {

    void extract(ZipEntry zipEntry, ZipInputStream zipInputStream);

    boolean canExtract(ZipEntry zipEntry);
}
