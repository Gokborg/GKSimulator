package core;

import gameobjects.Block;
import gameobjects.BlockPosition;

import java.util.HashMap;
import java.util.Map;

//TODO: What a disgrace. Find a better way of doing this
public class Grid
{
    private Game game;
    private Map<BlockPosition, Block> gridMap;

    public Grid(Game game)
    {
        this.game = game;
        gridMap = new HashMap<>();
    }

    public Block getBlock(int x, int y)
    {
        return gridMap.get(new BlockPosition(x, y)); //TODO: no way new object everytime wtf
    }

    public void add(Block block)
    {
        BlockPosition blockPosition = block.getBlockPosition();
        Block gridBlock = gridMap.get(blockPosition);
        if (gridBlock == null)
        {
            gridMap.put(blockPosition, block);
            game.getScene().add(block);
        }
    }

    public void remove(Block block)
    {
        BlockPosition blockPosition = block.getBlockPosition();
        Block gridBlock = gridMap.get(blockPosition);
        if (gridBlock != null)
        {
            gridMap.remove(blockPosition);
            game.getScene().remove(block);
        }
    }
}
