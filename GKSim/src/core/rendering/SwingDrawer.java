package core.rendering;

import core.Asset;
import core.Game;
import gameobjects.Block;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SwingDrawer implements Drawer
{
    private Graphics2D graphics2D;
    private Game game;

    public SwingDrawer(Game game)
    {
        this.game = game;
    }

    public void setGraphics2D(Graphics2D graphics2D)
    {
        this.graphics2D = graphics2D;
    }
    @Override
    public void drawImage(BufferedImage image, double x, double y)
    {
        int newX = (int)(x - game.getCamera().getX());
        int newY = (int)(y - game.getCamera().getY());
//        if(newX+image.getWidth() < 0 || newX > game.getDisplay().getWidth() || newY+image.getHeight()>game.getDisplay().getHeight() || newY < 0)
//        {
//            return;

        graphics2D.drawImage(image, newX, newY, null);
    }
    @Override
    public void drawBlock(Color color, double x, double y)
    {
        graphics2D.setColor(color);
        graphics2D.fillRect((int)(x - game.getCamera().getX()), (int)(y - game.getCamera().getY()), Block.PIXELS_PER_BLOCK, Block.PIXELS_PER_BLOCK);
    }
    @Override
    public void drawRect(Color color, double x, double y)
    {
        graphics2D.setColor(color);
        graphics2D.drawRect((int)(x - game.getCamera().getX()), (int)(y - game.getCamera().getY()), Block.PIXELS_PER_BLOCK, Block.PIXELS_PER_BLOCK);
    }
    @Override
    public void drawText(String text, Color textColor, Font font, double x, double y)
    {
        graphics2D.setColor(textColor);
        graphics2D.setFont(font);
        graphics2D.drawString(text, (int)(x - game.getCamera().getX()), (int)(y - game.getCamera().getY()));
    }
    @Override
    public void drawRawText(String text, Color textColor, Font font, double x, double y)
    {
        graphics2D.setColor(textColor);
        graphics2D.setFont(Asset.DEFAULT_FONT);
        graphics2D.drawString(text, (int) x, (int) y);
    }
}
