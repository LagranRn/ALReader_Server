
import java.io.*;
import java.net.Socket;

/**
 * (00001,) 返回所有图书id，name
 * (00002,id) 返回改id对应的图书的内容
 *
 */

public class ConnUtil {
    public static void readFromClient(Socket socket){
        try {
            InputStream is = socket.getInputStream();
            System.out.println("read msg...");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            System.out.println("out...");
            String res = "-1";
            String temp = "";
            while ((temp = br.readLine()) != null){
//                String temp = br.readLine();
                System.out.println(temp);
                String[] src = temp.split(",");
                switch (src[0]){
                    //获取所有图书的信息
                    case "00001":
                        res = Conn2DB.getBookList();
                        System.out.println(res);
                        break;
                    case "00002":
                        //src[1]: 具体要查的的id，返回书的内容！
                        int id = Integer.valueOf(src[1]);
                        System.out.println("00002中的id:" + id);
                        res = Conn2DB.readDB2Book(id);
                        break;
                    case "00003":
                        String account = String.valueOf(src[1]);
                        String password = String.valueOf(src[2]);
                        System.out.println("00003:"+account+","+password);
                        res = Conn2DB.login(account,password);
                        System.out.println("00003: res -- : " + res);
                        break;
                }
                break;
            }
            System.out.println("receive done.");

            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write(res);
            pw.flush();
            System.out.println("close conn...");
            br.close();
            isr.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
