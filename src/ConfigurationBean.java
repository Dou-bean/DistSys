/**
 * @author Yichen
 */
public class ConfigurationBean {

    private final String addr;
    private final String port;
    private final String path;

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
}
