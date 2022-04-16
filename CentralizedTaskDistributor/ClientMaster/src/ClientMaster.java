import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMaster {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4999);

        ServerConnectionMaster serverConn = new ServerConnectionMaster(socket);

//        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(serverConn).start();

        while(true) {
            System.out.print("> ");
            String message = keyboard.readLine();

            if (message.equals("quit")) break;
            out.println(message);
//        pr.println("Hey server docker!");

        }

        socket.close();
    }
}
