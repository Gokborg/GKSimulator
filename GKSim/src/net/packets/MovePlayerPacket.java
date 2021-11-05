package net.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class MovePlayerPacket implements Packet
{
    public static final int IDLE = 0;
    public static final int MOVING_RIGHT = 1;
    public static final int MOVING_LEFT = 2;
    public static final int MOVING_UP = 3;
    public static final int MOVING_DOWN = 4;

    private UUID uuid;
    private int x, y;
    private int movementType;

    public MovePlayerPacket(UUID uuid, int movementType, int x, int y)
    {
        this.uuid = uuid;
        this.movementType = movementType;
        this.x = x;
        this.y = y;
    }

    public MovePlayerPacket(DataInputStream in)
    {
        read(in);
    }

    public int getMovementType()
    {
        return movementType;
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
            this.movementType = in.readInt();
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
            out.writeInt(movementType);
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
        return "MovePlayerPacket(uuid: " + uuid + ", movement_type: " + movementType + ", x:" + x + ", y:" + y + ")";
    }
}
