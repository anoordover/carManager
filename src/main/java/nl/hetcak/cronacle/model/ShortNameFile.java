package nl.hetcak.cronacle.model;

import java.io.File;

/**
 * Created by anoordover on 7-11-15.
 */
public class ShortNameFile {
    private final File file;

    public ShortNameFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return file.getName();
    }

    public File getFile() {
        return file;
    }
}
