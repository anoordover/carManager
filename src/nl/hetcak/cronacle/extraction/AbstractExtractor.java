package nl.hetcak.cronacle.extraction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;
/**
 * Created by anoordover on 11-11-2015.
 */
public class AbstractExtractor {
    private static final Logger LOGGER = LogManager.getLogger(AbstractExtractor.class);
    protected void executeExtractionIfNeeded(ZipInputStream zipInputStream, String filename) {
        File file = new File(filename);
        if (!(file.exists())) {
            executeExtraction(zipInputStream, filename);

        }
    }

    protected void executeExtraction(ZipInputStream zipInputStream, String filename) {
        File file = new File(filename);
        LOGGER.info("To be extracted: " + filename);
        new File(file.getParent()).mkdirs();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = zipInputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
