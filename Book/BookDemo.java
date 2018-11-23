
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDemo {

    // 将图书插入数据库
    public static void readImage2DB() {
        String path = "D:/kkk.txt";
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(path));
            conn = DBUtil.getConn();
            String sql = "insert into book(name,content) values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "kkk");
            ps.setBinaryStream(2, in, in.available());
            int count = ps.executeUpdate();
            if (count > 0) {
                System.out.println("插入成功！");
            } else {
                System.out.println("插入失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        readImage2DB();
    }
}