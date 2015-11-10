package nl.hetcak.cronacle.model;

/**
 * Created by anoordover on 10-11-15.
 */
public class Configuration {
    private static final Configuration INSTANCE = new Configuration();

    private String carsLocation = "car";
    private String xmlLocation = "definitions";
    private String loaderLocation = "loader";
    private String metaInfLocation = "META-INF";

    public String getRootLocation() {
        return rootLocation;
    }

    public void setRootLocation(String rootLocation) {
        this.rootLocation = rootLocation;
    }

    private String rootLocation;

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
