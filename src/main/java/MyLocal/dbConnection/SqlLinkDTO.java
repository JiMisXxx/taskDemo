package MyLocal.dbConnection;

import java.io.Serializable;

public class SqlLinkDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    static String url;

    static String user;

    static String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        SqlLinkDTO.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        SqlLinkDTO.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        SqlLinkDTO.password = password;
    }

}
