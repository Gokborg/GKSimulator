package gameobjects;

import core.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.UUID;

public class Player extends GameObject
{
    private String name;
    private boolean isLocalPlayer;

    private Animation idleAnimation;
    private Animation runLeftAnimation;
    private Animation runRightAnimation;
    private Animation current;

    public Player(Game game, UUID uuid, String name, int x, int y, boolean isLocalPlayer)
    {
        super(game, uuid, x, y);
        this.isLocalPlayer = isLocalPlayer;
        this.name = name;
        idleAnimation = new Animation(200, Asset.PLAYER_IDLE);
        runLeftAnimation = new Animation(50, Asset.PLAYER_RUN_LEFT);
        runRightAnimation = new Animation(50, Asset.PLAYER_RUN_RIGHT);
        current = idleAnimation;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public void update()
    {
        if(isLocalPlayer)
        {
            updateMovement();
        }
    }

    public void updateMovement()
    {
        current = idleAnimation;
        if(game.getInput().isPressed(KeyEvent.VK_D))
        {
            x += 5;
            current = runRightAnimation;
            game.getClient().sendPlayerMovePacket(this);
        }
        if(game.getInput().isPressed(KeyEvent.VK_A))
        {
            x -= 5;
            current = runLeftAnimation;
            game.getClient().sendPlayerMovePacket(this);
        }
        if(game.getInput().isPressed(KeyEvent.VK_W))
        {
            y -= 5;
            game.getClient().sendPlayerMovePacket(this);
        }
        if(game.getInput().isPressed(KeyEvent.VK_S))
        {
            y += 5;
            game.getClient().sendPlayerMovePacket(this);
        }
        current.update();
    }

    public void render(Drawer drawer)
    {
        drawer.drawText(name, Color.WHITE, Asset.DEFAULT_FONT, x+20, y+35);
        drawer.drawImage(current.getFrame(), x, y);
    }
}
