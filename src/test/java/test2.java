import dbConnection.gettingConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class test2 {
    public static void main(String[] args) throws Exception {
        String str = "select * from test1 where var1 = ?;";
        Connection con = gettingConnection.gettingCon("test");
        PreparedStatement ps = con.prepareStatement(str, ResultSet.TYPE_FORWARD_ONLY);
        ps.setString(1, "2");
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData data = rs.getMetaData();
        ArrayList <String> list = new ArrayList<>();
        for (int i = 0; i < data.getColumnCount(); i++) {
            list.add(data.getColumnName(i + 1));
        }
        System.out.println(list);
    }
}
