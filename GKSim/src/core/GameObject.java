package core;

import core.rendering.Drawer;
import core.rendering.SwingDrawer;

import java.awt.image.BufferedImage;
import java.util.UUID;

public abstract class GameObject
{
    protected Game game;
    protected double x, y;
    protected BufferedImage image;
    protected UUID uuid;

    public GameObject(Game game, UUID uuid, double x, double y)
    {
        this.game = game;
        this.x = x;
        this.y = y;
        this.uuid = uuid;
        this.image = Asset.MISSING_IMAGE;
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public void setUUID(UUID uuid)
    {
        this.uuid = uuid;
    }

    public UUID getUUID()
    {
        return uuid;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getX()
    {
        return x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getY()
    {
        return y;
    }

    public abstract void update();
    public abstract void render(Drawer drawer);
}
