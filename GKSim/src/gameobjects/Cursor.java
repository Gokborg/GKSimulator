package gameobjects;

import core.Asset;
import core.Game;
import core.rendering.Drawer;

import java.awt.*;
import java.util.UUID;

public class Cursor extends Block
{
    private Color cursorColor = new Color(1, 0, 0, 1f);
    public Cursor(Game game, UUID uuid, int blockX, int blockY)
    {
        super(game, uuid, new BlockPosition(blockX, blockY), BlockType.CURSOR);
    }

    @Override
    public void update()
    {
        x = game.getInput().getScreenMouseX();
        y = game.getInput().getScreenMouseY();
        blockPos.x = (int) Math.floor(game.getInput().getCameraMouseX() / PIXELS_PER_BLOCK);
        blockPos.y = (int) Math.floor(game.getInput().getCameraMouseY() / PIXELS_PER_BLOCK);
    }

    @Override
    public void render(Drawer drawer)
    {
        drawer.drawRect(cursorColor, blockPos.x * Block.PIXELS_PER_BLOCK, blockPos.y * Block.PIXELS_PER_BLOCK);
        drawer.drawRawText("(" + game.getInput().getCameraMouseX() + "," + game.getInput().getCameraMouseY() + ")", Color.RED, Asset.DEFAULT_FONT, x, y);
        drawer.drawRawText("(" + blockPos.x + "," + blockPos.y + ")", Color.RED, Asset.DEFAULT_FONT, x, y+10);

    }
}
