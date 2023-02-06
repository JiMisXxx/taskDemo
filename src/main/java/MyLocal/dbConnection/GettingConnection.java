package MyLocal.dbConnection;

import MyLocal.exception.ThrowableUtil;

import java.sql.Connection;
import java.sql.DriverManager;

public class GettingConnection {
    public static final String CLASS_ALIAS = "GettingConnection";

    static String classMysqlName = "com.mysql.cj.jdbc.Driver";
    static SqlLinkDTO sqlLinkDTO = new SqlLinkDTO();

    public static void main(String[] args) throws Exception {
        int i = 2;
        Throwable ex = null;
        throw new Exception("数据库错误：请勿插入重复的主键！" + ThrowableUtil.getErrorNo(CLASS_ALIAS));
    }
    public static Connection gettingCon(String dbSource) throws Exception {
        conInformation(dbSource);
//        System.out.println("url:"+sqlLinkDTO.getUrl()+";user:"+sqlLinkDTO.getUser()+";password:"+sqlLinkDTO.getPassword());
        return DriverManager.getConnection(sqlLinkDTO.getUrl(), sqlLinkDTO.getUser(), sqlLinkDTO.getPassword());
    }

    public static void conInformation(String dbSource) throws ClassNotFoundException {
        StringBuilder sbuf = new StringBuilder();
        if (dbSource.startsWith("w")) {
            dbSource = dbSource.substring(2);
            sqlLinkDTO.setUrl("jdbc:mysql://19.15.76.173:15009/");
            sqlLinkDTO.setUrl(sbuf.append(sqlLinkDTO.getUrl()).append(dbSource).toString());
            sqlLinkDTO.setUser("cep_ro_app");
            sqlLinkDTO.setPassword("8B{M<@vH");
            Class.forName(classMysqlName);
        } else {
            sqlLinkDTO.setUrl("jdbc:mysql://localhost:3306/");
            sqlLinkDTO.setUrl(sbuf.append(sqlLinkDTO.getUrl()).append(dbSource).toString());
            sqlLinkDTO.setUser("root");
            sqlLinkDTO.setPassword("");
            Class.forName(classMysqlName);
        }
    }
}
