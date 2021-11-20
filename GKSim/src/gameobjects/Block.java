package gameobjects;

import core.BlockType;
import core.Drawer;
import core.Game;
import core.GameObject;

import java.util.UUID;

public abstract class Block extends GameObject
{
    public static int PIXELS_PER_BLOCK = 32;
    protected BlockPosition blockPos;
    protected BlockType blockType;

    public Block(Game game, UUID uuid, BlockPosition blockPos, BlockType blockType)
    {
        super(game, uuid, blockPos.x * PIXELS_PER_BLOCK, blockPos.y * PIXELS_PER_BLOCK);
        this.blockPos = blockPos;
        this.blockType = blockType;
    }

    public BlockType getBlockType() {return blockType;}

    public BlockPosition getBlockPosition()
    {
        return blockPos;
    }

    public int getBlockX()
    {
        return blockPos.x;
    }

    public int getBlockY()
    {
        return blockPos.y;
    }
}
