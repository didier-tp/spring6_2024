package xy.properties;
/*
permit-get-list for .requestMatchers(HttpMethod.GET, "...").permitAll()
permit-all-list for .requestMatchers( "...").permitAll()
auth-list for .requestMatchers( "...").authenticated()
NB: uri/path of list must be separated by ";"
 */
public class AreaProperties {
    private String permitGetList ; //"/rest/api-bank/v1/comptes/**;/rest/..."
    private String permitAllList;
    private String authList ;

    @Override
    public String toString() {
        return "AreaProperties{" +
                "permitGetList='" + permitGetList + '\'' +
                ", permitAllList='" + permitAllList + '\'' +
                ", authList='" + authList + '\'' +
                '}';
    }

    public String getPermitGetList() {
        return permitGetList;
    }

    public void setPermitGetList(String permitGetList) {
        this.permitGetList = permitGetList;
    }

    public String getPermitAllList() {
        return permitAllList;
    }

    public void setPermitAllList(String permitAllList) {
        this.permitAllList = permitAllList;
    }

    public String getAuthList() {
        return authList;
    }

    public void setAuthList(String authList) {
        this.authList = authList;
    }
}

