package core.rendering;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import core.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JOGLDisplay extends Display
{
    private GLCanvas glcanvas;
    private boolean draw;

    public JOGLDisplay(Game game)
    {
        super(game);
        setTitle(game.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        this.glcanvas = new GLCanvas( glcapabilities );
        glcanvas.addMouseListener(game.getInput());
        glcanvas.addMouseMotionListener(game.getInput());
        glcanvas.addGLEventListener(new JOGLDrawer(game));
        glcanvas.setFocusable(false);

        this.addWindowListener( new WindowAdapter()
        {
            public void windowClosing( WindowEvent windowevent )
            {
                dispose();
                System.exit( 0 );
            }
        });

        this.getContentPane().add( glcanvas, BorderLayout.CENTER );
        setSize(new Dimension(game.WIDTH, game.HEIGHT));
        setVisible(true);
    }

    public void render()
    {
        glcanvas.display();
    }
}
