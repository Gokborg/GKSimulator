package core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Asset
{
    public static BufferedImage MISSING_IMAGE;
    public static BufferedImage[] PLAYER_IDLE, PLAYER_RUN_RIGHT, PLAYER_RUN_LEFT;
    public static Font DEFAULT_FONT;

    public Asset()
    {
        try
        {
            DEFAULT_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("res/slkscr.ttf")).deriveFont(Font.PLAIN, 12);
            MISSING_IMAGE = ImageIO.read(new File("res/missing_sprite.png"));
            PLAYER_IDLE = new BufferedImage[7];
            PLAYER_RUN_RIGHT = new BufferedImage[8];
            PLAYER_RUN_LEFT = new BufferedImage[8];

            for(int i = 0; i < 7; i++)
            {
                PLAYER_IDLE[i] = ImageIO.read(new File("res/pixel_platformer_player/idle/" + (i+1) + ".png"));
            }
            for(int i = 0; i < 8; i++)
            {
                PLAYER_RUN_RIGHT[i] = ImageIO.read(new File("res/pixel_platformer_player/run/" + (i+1) + ".png"));
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-PLAYER_RUN_RIGHT[i].getWidth(null), 0);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                PLAYER_RUN_LEFT[i] = op.filter(PLAYER_RUN_RIGHT[i], null);
            }
        }
        catch(IOException | FontFormatException e)
        {
            e.printStackTrace();
        }
    }
}
