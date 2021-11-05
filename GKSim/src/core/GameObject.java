package core;

import java.awt.image.BufferedImage;
import java.util.UUID;

public abstract class GameObject
{
    protected Game game;
    protected int x, y;
    protected BufferedImage image;
    protected UUID uuid;

    public GameObject(Game game, UUID uuid, int x, int y)
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

    public void setX(int x)
    {
        this.x = x;
    }

    public int getX()
    {
        return x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getY()
    {
        return y;
    }

    public abstract void update();
    public abstract void render(Drawer drawer);
}
