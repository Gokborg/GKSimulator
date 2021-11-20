import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread
{
    private ServerSocket serverSocket;
    private ServerGrid serverGrid;
    private List<ClientHandler> clientHandlerList;
    private boolean running = false;
    private int port;

    public Server(int port)
    {
        this.port = port;
        clientHandlerList = new ArrayList<>();
        serverGrid = new ServerGrid();
    }

    @Override
    public void run()
    {
        try
        {
            running = true;
            serverSocket = new ServerSocket(port);
            System.out.println("Creating server socket on port: " + port);
            while(running)
            {
                System.out.println("Waiting for socket....");
                Socket socket = serverSocket.accept();
                System.out.println("Accepted socket: " + socket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(this, socket);
                clientHandler.start();
                clientHandlerList.add(clientHandler);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        end();
    }

    public synchronized ServerGrid getServerGrid()
    {
        return serverGrid;
    }

    public void broadcast(ClientHandler sender, Packet packet)
    {
        for(ClientHandler clientHandler : clientHandlerList)
        {
            if(clientHandler != sender)
            {
                clientHandler.send(packet);
            }
        }
    }

    public void removeClient(ClientHandler clientHandler)
    {
        clientHandlerList.remove(clientHandler);
    }

    public void end()
    {
        running = false;
        try
        {
            serverSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
