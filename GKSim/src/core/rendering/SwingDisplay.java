package core.rendering;

import core.Game;
import core.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class SwingDisplay extends Display
{
    private Canvas canvas;
    private SwingDrawer drawer;

    public SwingDisplay(Game game)
    {
        super(game);
        setTitle(game.TITLE);
        setPreferredSize(new Dimension(game.WIDTH, game.HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(game.WIDTH, game.HEIGHT));
        canvas.setFocusable(false);
        canvas.addMouseListener(game.getInput());
        canvas.addMouseMotionListener(game.getInput());
        add(canvas);

        pack();
        canvas.createBufferStrategy(3);
        drawer = new SwingDrawer(game);
        setVisible(true);
    }

    public void render()
    {
        BufferStrategy bs = canvas.getBufferStrategy();
        Graphics2D graphics2D = (Graphics2D) bs.getDrawGraphics();
        drawer.setGraphics2D(graphics2D);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for(GameObject gameObject : game.getScene().getGameObjects())
        {
            gameObject.render(drawer);

            //Debug rectangle around game object
            //graphics2D.setColor(Color.GREEN);
            //graphics2D.drawRect(gameObject.getX(), gameObject.getY(), gameObject.getImage().getWidth(), gameObject.getImage().getHeight());
        }

        game.getScene().getPlayer().render(drawer);
        game.getScene().getCursor().render(drawer);

        graphics2D.dispose();
        bs.show();
    }
}
