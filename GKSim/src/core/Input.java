package core;

import java.awt.*;
import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener
{
    private Game game;
    private boolean[] pressed;
    private int screenMouseX, screenMouseY;
    private boolean[] mouseButtons;
    private boolean[] lastMouseButtons;

    public Input(Game game)
    {
        this.game = game;
        pressed = new boolean[255];
        mouseButtons = new boolean[MouseInfo.getNumberOfButtons()];
        lastMouseButtons = new boolean[MouseInfo.getNumberOfButtons()];
    }

    public int getScreenMouseX()
    {
        return screenMouseX;
    }

    public int getScreenMouseY()
    {
        return screenMouseY;
    }

    //Returns where in the world we clicked
    public double getCameraMouseX()
    {
        return screenMouseX + game.getCamera().getX();
    }

    public double getCameraMouseY()
    {
        return screenMouseY + game.getCamera().getY();
    }

    public boolean isKeyPressed(int keyCode)
    {
        return pressed[keyCode];
    }

    public boolean isMouseUp(int button)
    {
        return !mouseButtons[button] && lastMouseButtons[button];
    }

    public boolean isMouseDown(int button)
    {
        return mouseButtons[button] && !lastMouseButtons[button];
    }

    public void update()
    {
        System.arraycopy(mouseButtons, 0, lastMouseButtons, 0, mouseButtons.length);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        pressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        pressed[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        setMousePos(e);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseButtons[e.getButton()] = true;
        setMousePos(e);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        mouseButtons[e.getButton()] = false;
        setMousePos(e);
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        setMousePos(e);
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        setMousePos(e);
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        setMousePos(e);
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        setMousePos(e);
    }

    private void setMousePos(MouseEvent e)
    {
        screenMouseX = e.getX();
        screenMouseY = e.getY();
    }
}
