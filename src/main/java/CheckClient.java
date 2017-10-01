import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CheckClient {

    static Socket socket;
    static InetAddress inetAddress;
    static int port = 9090;
    static String ip = "127.0.0.1";

    public static void main(String[] args) throws IOException {

        inetAddress = InetAddress.getByName(ip);
        socket = new Socket(inetAddress, port);

        if (socket.isConnected()){
            System.out.print("dkjfildjgldkf");
        }
    }
}
