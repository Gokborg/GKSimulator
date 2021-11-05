package core;

public class GameLoop extends Thread
{
    private Game game;
    private boolean running;

    public GameLoop(Game game)
    {
        this.game = game;
    }

    //Fuck you, i will never take the time to learn game loops
    //https://stackoverflow.com/questions/18283199/java-main-game-loop
    @Override
    public void run()
    {
        running = true;
        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / Game.UPS;
        final double timeF = 1000000000 / Game.FPS;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();

        while (running)
        {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1)
            {
                game.update();
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1)
            {
                game.render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000)
            {
                game.getDisplay().setTitle(game.TITLE + "  UPS: " + ticks + " FPS: " + frames);
                frames = 0;
                ticks = 0;
                timer += 1000;
            }
        }
    }
}
