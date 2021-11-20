package core;

import gameobjects.Block;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Drawer
{
    private Graphics2D graphics2D;
    private Game game;

    public Drawer(Game game)
    {
        this.game = game;
    }

    public void setGraphics2D(Graphics2D graphics2D)
    {
        this.graphics2D = graphics2D;
    }

    public void drawImage(BufferedImage image, double x, double y)
    {
        int newX = (int)(x - game.getCamera().getX());
        int newY = (int)(y - game.getCamera().getY());
//        if(newX+image.getWidth() < 0 || newX > game.getDisplay().getWidth() || newY+image.getHeight()>game.getDisplay().getHeight() || newY < 0)
//        {
//            return;
//        }
        graphics2D.drawImage(image, newX, newY, null);
    }

    public void drawBlock(Color color, double x, double y)
    {
        graphics2D.setColor(color);
        graphics2D.fillRect((int)(x - game.getCamera().getX()), (int)(y - game.getCamera().getY()), Block.PIXELS_PER_BLOCK, Block.PIXELS_PER_BLOCK);
    }

    public void drawRect(Color color, double x, double y)
    {
        graphics2D.setColor(color);
        graphics2D.drawRect((int)(x - game.getCamera().getX()), (int)(y - game.getCamera().getY()), Block.PIXELS_PER_BLOCK, Block.PIXELS_PER_BLOCK);
    }

    public void drawText(String text, Color textColor, Font font, double x, double y)
    {
        graphics2D.setColor(textColor);
        graphics2D.setFont(font);
        graphics2D.drawString(text, (int)(x - game.getCamera().getX()), (int)(y - game.getCamera().getY()));
    }
    //Text placed will not be effected by the camera
    public void drawRawText(String text, Color textColor, Font font, double x, double y)
    {
        graphics2D.setColor(textColor);
        graphics2D.setFont(Asset.DEFAULT_FONT);
        graphics2D.drawString(text, (int) x, (int) y);
    }
}
