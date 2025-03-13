package xy.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

// dans appliction.properties
// mysecurity.area.permit-list=/rest/api-bank/...

@ConfigurationProperties(prefix = "mysecurity")
public class SecurityProperties {
    private String mode ; // "oauth2" ou "..."
    private AreaProperties area;

    @Override
    public String toString() {
        return "SecurityProperties{" +
                "mode='" + mode + '\'' +
                ", area=" + area +
                '}';
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public AreaProperties getArea() {
        return area;
    }

    public void setArea(AreaProperties area) {
        this.area = area;
    }
}
