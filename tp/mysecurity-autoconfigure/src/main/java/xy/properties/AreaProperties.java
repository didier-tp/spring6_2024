package xy.properties;

public class AreaProperties {
    private String permitList ; //"/rest/api-bank/v1/comptes/**;/rest/..."
    private String authList ;

    @Override
    public String toString() {
        return "AeraProperties{" +
                "permitList='" + permitList + '\'' +
                ", authList='" + authList + '\'' +
                '}';
    }

    public String getPermitList() {
        return permitList;
    }

    public void setPermitList(String permitList) {
        this.permitList = permitList;
    }

    public String getAuthList() {
        return authList;
    }

    public void setAuthList(String authList) {
        this.authList = authList;
    }
}

