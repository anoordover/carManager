package nl.hetcak.cronacle.extraction;

import nl.hetcak.cronacle.model.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by anoordover on 10-11-15.
 */
public class ExtractSupportingClasses implements Extract {
    @Override
    public void extract(ZipEntry zipEntry, ZipInputStream zipInputStream) {
        String filename = Configuration.getInstance().getRootLocation() + File.separator +
                Configuration.getInstance().getLoaderLocation() + File.separator + zipEntry.getName();
        File file = new File(filename);
        if (!(file.exists())) {
            System.out.println("To be extracted: " + filename);
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

    @Override
    public boolean canExtract(ZipEntry zipEntry) {
        return zipEntry.getName().startsWith("com/") && zipEntry.getName().endsWith(".class");
    }
}
