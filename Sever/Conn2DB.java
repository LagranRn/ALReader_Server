import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

public class Conn2DB {

    public static String getBookList() {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConn();
            String sql = "SELECT id,name FROM book";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            StringBuffer sb = new StringBuffer();
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            StringBuffer res = new StringBuffer();
            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                String s = rs.getString("id");
                String s1 = rs.getString("name");
                String temp = s + "," + s1;
                res.append(temp + "\n");
            }

            return res.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return "-1";
    }


    public static String readDB2Book(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConn();
            String sql = "select * from book where id =?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            StringBuffer sb = new StringBuffer();
            while (rs.next()) {
                InputStream in = rs.getBinaryStream("content"); //输入流
                InputStreamReader isr = new InputStreamReader(in,"Unicode");
                BufferedReader br = new BufferedReader(isr);
                String temp = "";
                while ((temp = br.readLine())!= null){
                    System.out.println(temp);
                    sb.append(temp);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
       }
        return "-1";
    }

    public static  String login(String account,String password){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConn();
            String sql = "select * from user where account =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, account);
            rs = ps.executeQuery();
            StringBuffer sb = new StringBuffer();
            System.out.println("querying...");
            while (rs.next()) {
                System.out.println("has account ");
                if (rs.getString("password").equals(password)){
                    System.out.println("password is correct");
                    return "1";//success

                }
                System.out.println("incorrect");
                return "2";//password incorrect;
            }
            System.out.println("no user");
            return "3";//no user
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("sth just happened");
        } finally {
            System.out.println("close everything");
            DBUtil.closeConn(conn);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("-1");
        return "-1";//bug
    }

}
