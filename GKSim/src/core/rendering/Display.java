package core.rendering;

import core.Game;

import javax.swing.*;

public abstract class Display extends JFrame
{
    protected Game game;

    public Display(Game game)
    {
        this.game = game;
    }

    public abstract void render();
}
