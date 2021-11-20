package core.rendering;

import gameobjects.Block;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Drawer
{
    public void drawImage(BufferedImage image, double x, double y);
    public void drawBlock(Color color, double x, double y);
    public void drawRect(Color color, double x, double y);
    public void drawText(String text, Color textColor, Font font, double x, double y);
    //Text placed will not be effected by the camera
    public void drawRawText(String text, Color textColor, Font font, double x, double y);
}
