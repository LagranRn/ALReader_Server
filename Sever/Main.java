

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("init port...");
            ServerSocket socket = new ServerSocket(9999);
            while (true) {
                System.out.println("waiting for conn...");
                Socket socket1 = socket.accept();
                System.out.println("conn success!");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ConnUtil.readFromClient(socket1);

                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
