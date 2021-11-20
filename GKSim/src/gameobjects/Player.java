package gameobjects;

import core.Animation;
import core.Asset;
import core.Game;
import core.GameObject;
import core.rendering.Drawer;
import net.packets.BlockActionPacket;
import net.packets.MovePlayerPacket;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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

    public Player(Game game, UUID uuid, String name, double x, double y, boolean isLocalPlayer)
    {
        super(game, uuid, x, y);
        this.isLocalPlayer = isLocalPlayer;
        this.name = name;
        animationMap = new HashMap<>();
        animationMap.put("Idle", new Animation(200, Asset.PLAYER_IDLE));
        animationMap.put("Left", new Animation(50, Asset.PLAYER_RUN_LEFT));
        animationMap.put("Right", new Animation(50, Asset.PLAYER_RUN_RIGHT));
        currentAnimation = animationMap.get("Idle");
        image = currentAnimation.getFrame();
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
            if(game.getInput().isMouseDown(MouseEvent.BUTTON1))
            {
                Wire wire = new Wire(game, UUID.randomUUID(), game.getScene().getCursor().getBlockX(), game.getScene().getCursor().getBlockY());
                game.getGrid().add(wire);
                game.getClient().sendBlockActionPacket(wire, BlockActionPacket.ACTION_PLACE);
            }
            updateMovement();
        }
        currentAnimation.update();
    }

    private void updateMovement()
    {
        if (
                !game.getInput().isKeyPressed(KeyEvent.VK_D) &&
                !game.getInput().isKeyPressed(KeyEvent.VK_A) &&
                !game.getInput().isKeyPressed(KeyEvent.VK_W) &&
                !game.getInput().isKeyPressed(KeyEvent.VK_S) &&
                sendIdlePacket)
        {
            currentAnimation = animationMap.get("Idle");
            game.getClient().sendPlayerMovePacket(this, MovePlayerPacket.IDLE);
            sendIdlePacket = false;
        }
        if(game.getInput().isKeyPressed(KeyEvent.VK_D))
        {
            x += 5;
            currentAnimation = animationMap.get("Right");
            game.getClient().sendPlayerMovePacket(this, MovePlayerPacket.MOVING_RIGHT);
            sendIdlePacket = true;
        }
        if(game.getInput().isKeyPressed(KeyEvent.VK_A))
        {
            x -= 5;
            currentAnimation = animationMap.get("Left");
            game.getClient().sendPlayerMovePacket(this, MovePlayerPacket.MOVING_LEFT);
            sendIdlePacket = true;
        }
        if(game.getInput().isKeyPressed(KeyEvent.VK_W))
        {
            y -= 5;
            game.getClient().sendPlayerMovePacket(this, MovePlayerPacket.MOVING_UP);
            sendIdlePacket = true;
        }
        if(game.getInput().isKeyPressed(KeyEvent.VK_S))
        {
            y += 5;
            game.getClient().sendPlayerMovePacket(this, MovePlayerPacket.MOVING_DOWN);
            sendIdlePacket = true;
        }
        image = currentAnimation.getFrame();
    }

    public void render(Drawer drawer)
    {
        drawer.drawText(name, Color.WHITE, Asset.DEFAULT_FONT, x, y-10);
        drawer.drawText("(" + x + "," + y + ")", Color.RED, Asset.DEFAULT_FONT, x, y);
        drawer.drawImage(image, x, y);
    }
}
