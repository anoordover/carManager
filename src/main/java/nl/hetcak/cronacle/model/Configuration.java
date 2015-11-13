package nl.hetcak.cronacle.model;

/**
 * Created by anoordover on 10-11-15.
 */
public class Configuration {
    private static final Configuration INSTANCE = new Configuration();

    private String carsLocation = "car";
    private String xmlLocation = "extracted/definitions";
    private String loaderLocation = "extracted/loader";
    private String metaInfLocation = "extracted/META-INF";
    private String rootLocation;

    public String getRootLocation() {
        return rootLocation;
    }

    public void setRootLocation(String rootLocation) {
        this.rootLocation = rootLocation;
    }

    private Configuration() {
    }

    public static Configuration getInstance() {
        return INSTANCE;
    }

    public String getCarsLocation() {
        return carsLocation;
    }

    public String getXmlLocation() {
        return xmlLocation;
    }

    public String getLoaderLocation() {
        return loaderLocation;
    }

    public String getMetaInfLocation() {
        return metaInfLocation;
    }
}
