package net.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface Packet
{
    public static final int NEW_PLAYER_PACKET_ID = 0;
    public static final int MOVE_PLAYER_PACKET_ID = 1;

    public void read(DataInputStream in);
    public void write(DataOutputStream out);
    public String toString();
}
