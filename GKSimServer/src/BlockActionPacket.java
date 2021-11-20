import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BlockActionPacket implements Packet
{
    private int blockId;
    private int blockX, blockY;
    private int action;

    //Actions
    public static final int ACTION_PLACE = 1;
    public static final int ACTION_REMOVE = 2;

    public BlockActionPacket(int blockId, int action, int blockX, int blockY)
    {
        this.blockId = blockId;
        this.action = action;
        this.blockX = blockX;
        this.blockY = blockY;
    }

    public BlockActionPacket(DataInputStream in)
    {
        read(in);
    }

    @Override
    public void read(DataInputStream in)
    {
        try
        {
            this.blockId = in.readInt();
            this.action = in.readInt();
            this.blockX = in.readInt();
            this.blockY = in.readInt();
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
            out.writeInt(Packet.BLOCK_ACTION_PACKET_ID);
            out.writeInt(blockId);
            out.writeInt(action);
            out.writeInt(blockX);
            out.writeInt(blockY);
            out.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getAction()
    {
        return action;
    }

    public int getBlockId()
    {
        return blockId;
    }

    public int getBlockX()
    {
        return blockX;
    }

    public int getBlockY()
    {
        return blockY;
    }
    public String toString()
    {
        return "BlockActionPacket(blockid: " + blockId + ", action: " + action + ", blockx:" + blockX + ", blocky:" + blockY + ")";
    }

}