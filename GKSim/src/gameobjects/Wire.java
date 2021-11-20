package gameobjects;

import core.Asset;
import core.Game;
import core.rendering.Drawer;

import java.awt.*;
import java.util.UUID;

public class Wire extends Block
{
    public Wire(Game game, UUID uuid, int blockX, int blockY)
    {
        super(game, uuid, new BlockPosition(blockX, blockY), BlockType.WIRE);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void render(Drawer drawer)
    {
        drawer.drawBlock(Color.BLUE, x, y);
        drawer.drawText(blockPos.x + "," + blockPos.y, Color.LIGHT_GRAY, Asset.DEFAULT_FONT, x, y + (Block.PIXELS_PER_BLOCK / 2));
    }

    public String toString()
    {
        return "Wire(X: " + x + ", Y: " + y + ", BlockX: " + blockPos.x + ", BlockY: " + blockPos.y + ")";
    }
}
