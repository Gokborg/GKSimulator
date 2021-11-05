import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class ClientHandler extends Thread
{
    private Server server;
    private boolean running;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientHandler(Server server, Socket socket)
    {
        this.server = server;
        this.socket = socket;
        try
        {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        running = true;
        try
        {
            //We sent the ok we are ready to recieve
            out.writeChar('a');
            out.flush();

            while(running)
            {
                //Prepare for a player packet
                int packetType = in.readInt();
                Packet packet = null;
                if(packetType == Packet.NEW_PLAYER_PACKET_ID)
                {
                    packet = new NewPlayerPacket(in);
                    server.broadcast(this, packet);
                }
                else if(packetType == Packet.MOVE_PLAYER_PACKET_ID)
                {
                    packet = new MovePlayerPacket(in);
                    server.broadcast(this, packet);
                }
                if(packet == null)
                {
                    System.out.println(socket.getInetAddress() + " has sent a faulty packet, disconnecting the user!");
                    end();
                }
                else
                {
                    System.out.println(socket.getInetAddress() + "> " + packet.toString());
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(socket.getInetAddress() + " has disconnected!");
        }
        end();
    }

    public void send(Packet packet)
    {
        if(running)
        {
            packet.write(out);
        }
    }

    public void end()
    {
        running = false;
        try
        {
            socket.close();
            in.close();
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        server.removeClient(this);
    }
}
