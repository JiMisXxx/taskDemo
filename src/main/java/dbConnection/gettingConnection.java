package dbConnection;

import java.sql.*;
import java.sql.DriverManager;

public class gettingConnection {
    static String url;
    static String user;
    static String password;
    static String classMysqlName = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws Exception {
    }

    public static Connection gettingCon(String dbsource) throws Exception {
        conInformation(dbsource);
        return DriverManager.getConnection(url, user, password);
    }

    public static void conInformation(String dbsource) throws ClassNotFoundException {
        StringBuilder sbuf = new StringBuilder();
        switch (dbsource) {
            default:
                if (dbsource.startsWith("w")) {
                    dbsource = dbsource.substring(2);
                    url = "jdbc:mysql://19.15.76.173:15009/";
                    url = sbuf.append(url).append(dbsource).toString();
                    user = "cep_ro_app";
                    password = "8B{M<@vH";
                    Class.forName(classMysqlName);
                } else {
                    url = "jdbc:mysql://localhost:3306/";
                    url = sbuf.append(url).append(dbsource).toString();
                    user = "root";
                    password = "";
                    Class.forName(classMysqlName);
                }
        }
    }
}
