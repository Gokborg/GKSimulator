package core;

import gameobjects.Player;
import net.Client;

import java.util.UUID;

public class Game
{
    public static final String TITLE = "GKSim v0.0.1";
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private Display display;
    private Scene scene;
    private GameLoop gameLoop;
    private Input input;
    private Asset asset;
    private Client client;

    public Game()
    {
        asset = new Asset();
        input = new Input();
        display = new Display(this);
        display.addKeyListener(input);
        client = new Client(this);

        scene = new Scene(new Player(this, UUID.randomUUID(), UUID.randomUUID().toString(), 0, 0, true));

        gameLoop = new GameLoop(this);
        gameLoop.start();

        client.connect("localhost", 24454);
    }

    public void update()
    {
        client.update();
        scene.update();
    }

    public void render()
    {
        display.render();
    }

    public Display getDisplay()
    {
        return display;
    }

    public Scene getScene()
    {
        return scene;
    }

    public Input getInput()
    {
        return input;
    }

    public Client getClient()
    {
        return client;
    }
}