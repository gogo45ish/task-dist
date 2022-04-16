import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader in;
    private BufferedReader keyboard;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;

    public ClientHandler (Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
        keyboard = new BufferedReader(new InputStreamReader(System.in));

    }
    @Override
    public void run() {
        try {
            while (true) {
                String str = in.readLine();

                if (str.contains("name")){
                    out.println("Some random name");
                } else if(str.startsWith("say")) {
                    int firstSpace = str.indexOf(" ");
                    if (firstSpace != -1){
                        outToAll(str.substring((firstSpace+1)));
                    }
                } else if (str.contains("go")){
                    String[] numbers = str.replaceAll("^\\D+","").split("\\D+");
                    int start = Integer.parseInt(numbers[0]);
                    int end = Integer.parseInt(numbers[1]);
                    outToAll2(start, end);
                }else if (str.contains("work")){
                    out.println("I am working!");
                }
                else{
//                    out.println("Type 'tell me a name' to get random name");
                    System.out.println(str);
                    clients.get(0).out.println(str);
                }

//                System.out.println("Client: " + str);
//
//                System.out.print("> ");
//                String message = keyboard.readLine();
//                out.println(message);
            }
        } catch (IOException e){
            System.err.println("Client disconnected");
//            System.err.println(e.getStackTrace());
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void outToAll(String msg) {
//        for (ClientHandler aClient : clients) {
//            aClient.out.println(msg);
//        }
        for (int i = 0; i < clients.size(); i++){
            if (i != 0) {
                clients.get(i).out.println(msg);
            }
        }
    }
    private void outToAll1(int start, int end) {
        System.out.println(start + " " + end);
        int n = clients.size();
        int total_length = (end - start) + 1;
        double subrange_length = total_length/n;

        System.out.println("Total length: "+ total_length);
        System.out.println("N : "+ n);
        System.out.println("Subrange length: "+ subrange_length);

        int current_start = start;
        for (ClientHandler aClient : clients) {
            double current_end = current_start + subrange_length;
            aClient.out.println("work " + current_start + " " + current_end);
            System.out.println("work " + current_start + " " + current_end);
            current_start += subrange_length;
//            current_start++;

//            System.out.println("work " + current_start + " " + current_end);
        }
    }

    private void outToAll2(int start, int end) {
        System.out.println(start + " " + end);
        int n = clients.size() - 1;
        int total_length = (end - start) + 1;
        double subrange_length = total_length/n;

        System.out.println("Total length: "+ total_length);
        System.out.println("N : "+ n);
        System.out.println("Subrange length: "+ subrange_length);

        int current_start = start;
        for (int i = 0; i < clients.size(); i++) {
            if (i != 0) {
                double current_end = current_start + subrange_length;
                clients.get(i).out.println("work " + current_start + " " + current_end);
                System.out.println("work " + current_start + " " + current_end);
                current_start += subrange_length;
            }

//            current_start++;

//            System.out.println("work " + current_start + " " + current_end);
        }
    }

}
