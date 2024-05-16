import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    int clientNo;
    Database database;

    public ClientHandler (Socket socket, int clientId, Database db) {
        clientSocket = socket;
        clientNo = clientId;
        database = db;
    }

    public void run() {
        System.out.println("ClientHandler started...");
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            String clientMessage;
            int titlesNum = 0;
            while(!(clientMessage = inFromClient.readLine()).equals("stop")) {
                System.out.println("Client sent the artist name " + clientMessage);
                titlesNum = database.getTitles(clientMessage);
                outToClient.println("Number of titles: " + titlesNum + " records found");
            }
            System.out.println("Client " + clientNo + " has disconnected");
            outToClient.println("Connection closed, Goodbye!");
            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
