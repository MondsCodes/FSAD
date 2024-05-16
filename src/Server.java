import java.io.*;
import java.net.*;

public class Server {

    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Credentials.PORT);
        System.out.println("Server is running" );
        Database database = new Database();
        if (database.establishDBConnection()) {
            System.out.println("Server is now connected to DB");
        } else
            System.out.println("DB connection fail, stopping.");
        int clientId = 0;
        while (true) {
            Socket clientSocket = serverSocket.accept();
            clientId ++;
            System.out.println("Client " + clientId + " connected with IP " + clientSocket.getInetAddress().getHostAddress());
            ClientHandler clientHandler = new ClientHandler(clientSocket, clientId, database);
            new Thread(clientHandler).start();
        }
    }
}

