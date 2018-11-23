import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class BookDemo {

    // 将图书插入数据库
    public static void readImage2DB(String path) {
        File file = new File("");
        String now = file.getAbsolutePath();
        System.out.println(file.getAbsoluteFile());
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(now + "/" + path + ".txt"));
            conn = DBUtil.getConn();
            String sql = "insert into book(name,content) values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, path);
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
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入文件名称（不加后缀！）:");
            String path = sc.nextLine();
            readImage2DB(path);
        }
    }
}
