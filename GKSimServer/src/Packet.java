import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface Packet
{
    public static int NEW_PLAYER_PACKET_ID = 0;
    public static int MOVE_PLAYER_PACKET_ID = 1;
    public static final int BLOCK_ACTION_PACKET_ID = 2;

    public void read(DataInputStream in);
    public void write(DataOutputStream out);
}
