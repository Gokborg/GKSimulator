package net.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class MovePlayerPacket implements Packet
{
    private UUID uuid;
    private int x, y;

    public MovePlayerPacket(UUID uuid, int x, int y)
    {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
    }

    public MovePlayerPacket(DataInputStream in)
    {
        read(in);
    }

    public UUID getUUID()
    {
        return uuid;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public void read(DataInputStream in)
    {
        try
        {
            this.uuid = UUID.fromString(in.readUTF());
            this.x = in.readInt();
            this.y = in.readInt();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream out)
    {
        try
        {
            out.writeInt(Packet.MOVE_PLAYER_PACKET_ID);
            out.writeUTF(uuid.toString());
            out.writeInt(x);
            out.writeInt(y);
            out.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String toString()
    {
        return "MovePlayerPacket(uuid: " + uuid  + ", x:" + x + ", y:" + y + ")";
    }
}
