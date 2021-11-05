package core;

import java.awt.image.BufferedImage;

public class Animation
{
    private int frame;
    private long msPerFrame;
    private BufferedImage[] images;
    private int frameNumber;

    private long timePassed, lastTime;

    public Animation(long msPerFrame, BufferedImage[] images)
    {
        this.msPerFrame = msPerFrame;
        this.images = images;
        this.lastTime = System.currentTimeMillis();
    }

    public void update()
    {
        timePassed = System.currentTimeMillis() - lastTime;
        if(timePassed >= msPerFrame)
        {
            frameNumber++;
            if(frameNumber >= images.length)
            {
                frameNumber = 0;
            }
            lastTime = System.currentTimeMillis();
        }
    }

    public int getLength()
    {
        return images.length;
    }

    public void setFrame(int frameNumber)
    {
        this.frameNumber = frameNumber;
    }

    public BufferedImage getFrame()
    {
        return images[frameNumber];
    }
}
