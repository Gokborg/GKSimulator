import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerGrid
{
    List<BlockPosition> blockPositions = new CopyOnWriteArrayList<>();

    public synchronized boolean add(BlockPosition blockPosition)
    {
        for(BlockPosition bp : blockPositions)
        {
            if(bp == blockPosition)
            {
                return false;
            }
        }
        blockPositions.add(blockPosition);
        return true;
    }
}
