package core;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Drawer
{
    private Graphics2D graphics2D;

    public void setGraphics2D(Graphics2D graphics2D)
    {
        this.graphics2D = graphics2D;
    }

    public void drawImage(BufferedImage image, int x, int y)
    {
        graphics2D.drawImage(image, x, y, null);
    }

    public void drawText(String text, Color textColor, Font font, int x, int y)
    {
        graphics2D.setColor(textColor);
        graphics2D.setFont(font);
        graphics2D.drawString(text, x, y);
    }
}
