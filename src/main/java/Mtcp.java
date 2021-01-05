import client.Client;
import server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Mtcp{
    public static Server server = new Server();

    static ServerSocket _sSocket = null;
    static final int _port = 10001;
    static List<String> __messagesSaved = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("srv: Starting server...");

        _sSocket = new ServerSocket(_port);
        System.out.println("srv: Server is running in port " + _port);

        // repeatedly wait for connections, and process
        // noinspection InfiniteLoopStatement
        while (true) {
            // connect to client
            Socket clientSocket = _sSocket.accept();
            System.out.println("srv: New client");
            server = new Server(clientSocket);
            server.readRequest();
            //close the client socket
            clientSocket.close();

        }
    }
}
