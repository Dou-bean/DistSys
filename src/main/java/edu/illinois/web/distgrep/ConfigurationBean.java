package edu.illinois.web.distgrep;

/**
 * @author Yichen
 */
public class ConfigurationBean {

    private final String addr;
    private final String port;
    private final String path;

    private static final int HASHCODE_INITIALIZE = 17;
    private static final int HASHCODE_MULTIPLY_FACTOR = 31;

    public ConfigurationBean(String addr, String port, String path) {
        this.addr = addr;
        this.port = port;
        this.path = path;
    }

    public String getAddr() {
        return addr;
    }

    public String getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object that) {
        if (that == this) return true;
        if (that == null) return false;
        if (that.getClass() != this.getClass()) return false;

        ConfigurationBean thatConfig = (ConfigurationBean) that;
        if (!this.addr.equals(thatConfig.addr)) return false;
        if (!this.port.equals(thatConfig.port)) return false;
        if (!this.path.equals(thatConfig.path)) return false;
        return true;
    }


    @Override
    public int hashCode() {
        int hash = HASHCODE_INITIALIZE;
        hash = HASHCODE_MULTIPLY_FACTOR * hash + addr.hashCode();
        hash = HASHCODE_MULTIPLY_FACTOR * hash + port.hashCode();
        hash = HASHCODE_MULTIPLY_FACTOR * hash + path.hashCode();
        return hash;
    }
}
