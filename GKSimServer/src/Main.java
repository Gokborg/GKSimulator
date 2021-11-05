public class Main
{
    /*
    Packet Design
    --------------

    PacketID
    0x00 PING
    0x01 PONG


     */

    public static void main(String[] args)
    {
        Server server = new Server(24454);
        server.start();
    }
}
