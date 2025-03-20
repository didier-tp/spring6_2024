package tp.appliSpring.bank.web.api.dto;

public class MyAuthResponse {
    private String username;
    private String message;
    private Boolean ok=false;
    private String token=null;

    public MyAuthResponse(String username, String message, Boolean ok, String token) {
        this.username = username;
        this.message = message;
        this.ok = ok;
        this.token = token;
    }

    public MyAuthResponse() {
        this(null,null,false,null);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
