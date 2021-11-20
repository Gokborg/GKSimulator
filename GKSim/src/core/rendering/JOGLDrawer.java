package core.rendering;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import core.Asset;
import core.Game;
import core.GameObject;
import gameobjects.Block;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

public class JOGLDrawer implements Drawer, GLEventListener
{
    private Game game;
    private TextRenderer textRenderer;
    private GL2 gl;

    public JOGLDrawer(Game game)
    {
        this.game = game;
        textRenderer = new TextRenderer(Asset.DEFAULT_FONT);
    }

    public void setGl(GL2 gl)
    {
        this.gl = gl;
    }

    public GL2 getGl()
    {
        return gl;
    }

    public void clearGl()
    {
        gl.glClear( GL.GL_COLOR_BUFFER_BIT );
    }

    @Override
    public void drawImage(BufferedImage image, double x, double y)
    {
        Texture texture = AWTTextureIO.newTexture(gl.getGLProfile(), image, true);
        if (texture != null)
        {
            gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject());
        }
        float nx = (float)x;
        float ny = (float)y;
        gl.glColor4f(1, 1, 1, 1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(nx, ny+image.getHeight());
        gl.glVertex2f(0, image.getHeight());

        gl.glTexCoord2f(nx+image.getWidth(), ny+image.getHeight());
        gl.glVertex2f(image.getWidth(), image.getHeight());

        gl.glTexCoord2f(nx+image.getWidth(), ny);
        gl.glVertex2f(image.getWidth(), 0);

        gl.glTexCoord2f(nx, ny);
        gl.glVertex2f(0, 0);
        gl.glEnd();
        gl.glFlush();

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
    }
    @Override
    public void drawBlock(Color color, double x, double y)
    {
        int newX = (int)(x - game.getCamera().getX());
        int newY = (int)(y - game.getCamera().getY());

        gl.glBegin(GL2.GL_QUADS);

        gl.glColor3f(color.getRed()/255, color.getGreen()/255, color.getBlue()/255);
        gl.glVertex2f(newX, newY);
        gl.glVertex2f(Block.PIXELS_PER_BLOCK + newX,newY);
        gl.glVertex2f(Block.PIXELS_PER_BLOCK + newX,Block.PIXELS_PER_BLOCK + newY);
        gl.glVertex2f(newX,Block.PIXELS_PER_BLOCK + newY);

        gl.glEnd();
    }

    @Override
    public void drawRect(Color color, double x, double y)
    {
        int newX = (int)(x - game.getCamera().getX());
        int newY = (int)(y - game.getCamera().getY());

        gl.glBegin(GL2.GL_QUADS);

        gl.glColor3f(color.getRed()/255, color.getGreen()/255, color.getBlue()/255);
        gl.glVertex2f(newX, newY);
        gl.glVertex2f(Block.PIXELS_PER_BLOCK + newX,newY);
        gl.glVertex2f(Block.PIXELS_PER_BLOCK + newX,Block.PIXELS_PER_BLOCK + newY);
        gl.glVertex2f(newX,Block.PIXELS_PER_BLOCK + newY);

        gl.glEnd();
        gl.glFlush();
    }

    @Override
    public void drawText(String text, Color textColor, Font font, double x, double y)
    {
//        int newX = (int)(x - game.getCamera().getX());
//        int newY = (int)(y - game.getCamera().getY());
//        textRenderer.begin3DRendering();
//        textRenderer.setColor(textColor);
//        textRenderer.setSmoothing(false);
//        textRenderer.draw(text, newX, newY);
//        textRenderer.end3DRendering();
    }

    @Override
    public void drawRawText(String text, Color textColor, Font font, double x, double y)
    {
        //textRenderer.begin3DRendering();
        //textRenderer.setColor(textColor);
        //textRenderer.setSmoothing(true);
        //textRenderer.draw(text, 50, 50);
        //textRenderer.end3DRendering();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable)
    {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(0f, 0f, 0f, 0f);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable)
    {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        for(GameObject gameObject : game.getScene().getGameObjects())
        {
            gameObject.render(this);
        }
        game.getScene().getPlayer().render(this);
        game.getScene().getCursor().render(this);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3)
    {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, game.getDisplay().getWidth(),  game.getDisplay().getHeight(), 0, -1, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
