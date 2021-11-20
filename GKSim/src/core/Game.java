package core;

import gameobjects.Cursor;
import gameobjects.Player;
import gameobjects.Wire;
import net.Client;

import java.util.UUID;

public class Game
{
    public static final String TITLE = "GKSim v0.0.1";
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int UPS = 60;
    public static final int FPS = 60;

    private Display display;
    private Grid grid;
    private Scene scene;
    private Camera camera;
    private GameLoop gameLoop;
    private Input input;
    private Asset asset;
    private Client client;

    public Game()
    {
        asset = new Asset();
        input = new Input(this);
        camera = new Camera(0, 0);
        display = new Display(this);
        display.addKeyListener(input);
        client = new Client(this);
        grid = new Grid(this);

        scene = new Scene(
                new Player(this, UUID.randomUUID(), UUID.randomUUID().toString().substring(0,5), 0, 0, true),
                new Cursor(this, UUID.randomUUID(), 0, 0)
        );

        grid.add(new Wire(this, UUID.randomUUID(), 5, 0));
        gameLoop = new GameLoop(this);
        gameLoop.start();

        client.connect("localhost", 24454);
    }

    public void update()
    {
        //Center the camera onto the player
        Player player = scene.getPlayer();
        camera.setX(player.getX() - display.getWidth()/2.0 + player.getImage().getWidth()/2);
        camera.setY(player.getY() - display.getHeight()/2.0 + player.getImage().getHeight()/2);

        client.update();
        scene.update();
        input.update();
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

    public Camera getCamera()
    {
        return camera;
    }

    public Grid getGrid()
    {
        return grid;
    }
}
