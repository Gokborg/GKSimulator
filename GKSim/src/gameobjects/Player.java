package gameobjects;

import core.*;
import net.packets.MovePlayerPacket;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Player extends GameObject
{
    private String name;
    private boolean isLocalPlayer;
    private boolean sendIdlePacket;

    private Map<String, Animation> animationMap;
    private Animation currentAnimation;

    public Player(Game game, UUID uuid, String name, int x, int y, boolean isLocalPlayer)
    {
        super(game, uuid, x, y);
        this.isLocalPlayer = isLocalPlayer;
        this.name = name;
        animationMap = new HashMap<>();
        animationMap.put("Idle", new Animation(200, Asset.PLAYER_IDLE));
        animationMap.put("Left", new Animation(50, Asset.PLAYER_RUN_LEFT));
        animationMap.put("Right", new Animation(50, Asset.PLAYER_RUN_RIGHT));
        currentAnimation = animationMap.get("Idle");
    }

    public String getName()
    {
        return name;
    }

    public void setAnimation(String animation)
    {
        currentAnimation = animationMap.get(animation);
    }

    @Override
    public void update()
    {
        if(isLocalPlayer)
        {
            updateMovement();
        }
        currentAnimation.update();
    }

    public void updateMovement()
    {
        if (
                !game.getInput().isPressed(KeyEvent.VK_D) &&
                !game.getInput().isPressed(KeyEvent.VK_A) &&
                !game.getInput().isPressed(KeyEvent.VK_W) &&
                !game.getInput().isPressed(KeyEvent.VK_S) &&
                sendIdlePacket)
        {
            currentAnimation = animationMap.get("Idle");
            game.getClient().sendPlayerMovePacket(this, MovePlayerPacket.IDLE);
            sendIdlePacket = false;
        }
        if(game.getInput().isPressed(KeyEvent.VK_D))
        {
            x += 5;
            currentAnimation = animationMap.get("Right");
            game.getClient().sendPlayerMovePacket(this, MovePlayerPacket.MOVING_RIGHT);
            sendIdlePacket = true;
        }
        if(game.getInput().isPressed(KeyEvent.VK_A))
        {
            x -= 5;
            currentAnimation = animationMap.get("Left");
            game.getClient().sendPlayerMovePacket(this, MovePlayerPacket.MOVING_LEFT);
            sendIdlePacket = true;
        }
        if(game.getInput().isPressed(KeyEvent.VK_W))
        {
            y -= 5;
            currentAnimation = animationMap.get("Right");
            game.getClient().sendPlayerMovePacket(this, MovePlayerPacket.MOVING_UP);
            sendIdlePacket = true;
        }
        if(game.getInput().isPressed(KeyEvent.VK_S))
        {
            y += 5;
            game.getClient().sendPlayerMovePacket(this, MovePlayerPacket.MOVING_DOWN);
            sendIdlePacket = true;
        }
    }

    public void render(Drawer drawer)
    {
        drawer.drawText(name, Color.WHITE, Asset.DEFAULT_FONT, x+20, y+35);
        drawer.drawImage(currentAnimation.getFrame(), x, y);
    }
}
