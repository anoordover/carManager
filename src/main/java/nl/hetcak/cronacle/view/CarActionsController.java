package nl.hetcak.cronacle.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import nl.hetcak.cronacle.extraction.DefinitionExtractor;
import nl.hetcak.cronacle.extraction.Extractor;
import nl.hetcak.cronacle.extraction.MetaInfExtractor;
import nl.hetcak.cronacle.extraction.SupportingClassesExtractor;
import nl.hetcak.cronacle.logging.TextAreaAppender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by anoordover on 10-11-15.
 */
public class CarActionsController {

    private static final Set<Extractor> EXTRACTORS = new HashSet<>();
    private static final Logger LOGGER = LogManager.getLogger(CarActionsController.class);
    @FXML
    private TextArea textArea;

    private RootController rootController;

    static {
        EXTRACTORS.add(new SupportingClassesExtractor());
        EXTRACTORS.add(new MetaInfExtractor());
        EXTRACTORS.add(new DefinitionExtractor());
    }

    @FXML
    private void initialize() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        Layout layout = PatternLayout.createLayout("%m%n", null, config, null, null, true, true, null, null);
        Appender appender = TextAreaAppender.createAppender("textAreaAppender", null, layout, textArea);
        appender.start();
        config.addAppender(appender);
        AppenderRef ref = AppenderRef.createAppenderRef("File", null, null);
        AppenderRef[] refs = new AppenderRef[]{ref};
        LoggerConfig loggerConfig = LoggerConfig.createLogger("false", Level.ALL, "nl.hetcak",
                "true", refs, null, config, null);
        loggerConfig.addAppender(appender, null, null);
        config.addLogger("nl.hetcak", loggerConfig);
        ctx.updateLoggers();

    }

    @FXML
    private void uitpakkenSelectedCars() {
        for (File file : rootController.getSelectedFilesFromFileSelector()) {
            verwerkFile(file);
        }
    }

    private void verwerkFile(File file) {
        if (file.isFile() && file.getName().endsWith(".car")) {
            ZipInputStream zis = null;
            try {
                LOGGER.info("is File");
                zis = new ZipInputStream(new FileInputStream(file));
                ZipEntry ze = zis.getNextEntry();
                while (ze != null) {
                    if (!ze.isDirectory()) {
                        LOGGER.info(ze.getName());
                        for (Extractor extractor : EXTRACTORS) {
                            if (extractor.canExtract(ze)) {
                                extractor.extract(ze, zis, file);
                            }
                        }
                    }
                    ze = zis.getNextEntry();
                }
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

    public void init(RootController rootController) {
        this.rootController = rootController;
    }
}
