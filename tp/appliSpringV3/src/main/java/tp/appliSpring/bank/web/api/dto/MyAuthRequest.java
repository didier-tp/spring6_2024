package tp.appliSpring.bank.web.api.dto;


public class MyAuthRequest {
    private String username;
    private String password;

    public MyAuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public MyAuthRequest() {
        this(null,null);
    }

    @Override
    public String toString() {
        return "MyAuthRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
