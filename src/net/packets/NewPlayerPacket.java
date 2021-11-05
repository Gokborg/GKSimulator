package net.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class NewPlayerPacket implements Packet
{
    private UUID uuid;
    private String name;
    private int x, y;

    public NewPlayerPacket(UUID uuid, String name, int x, int y)
    {
        this.uuid = uuid;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public NewPlayerPacket(DataInputStream in)
    {
        read(in);
    }

    @Override
    public void read(DataInputStream in)
    {
        try
        {
            this.uuid = UUID.fromString(in.readUTF());
            this.name = in.readUTF();
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
            out.writeInt(Packet.NEW_PLAYER_PACKET_ID);
            out.writeUTF(uuid.toString());
            out.writeUTF(name);
            out.writeInt(x);
            out.writeInt(y);
            out.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public UUID getUUID()
    {
        return uuid;
    }

    public String getName()
    {
        return name;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public String toString()
    {
        return "NewPlayerPacket(uuid: " + uuid + ", name: " + name + ", x:" + x + ", y:" + y + ")";
    }
}
