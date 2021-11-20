package core;

import gameobjects.Cursor;
import gameobjects.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scene
{
    private List<GameObject> gameObjects;
    private Map<UUID, GameObject> gameObjectMap;
    private Player player;
    private Cursor cursor;

    public Scene(Player player, Cursor cursor)
    {
        gameObjects = new CopyOnWriteArrayList<>();
        gameObjectMap = new HashMap<>();
        this.player = player;
        this.cursor = cursor;
    }

    public Cursor getCursor()
    {
        return cursor;
    }

    public Player getPlayer()
    {
        return player;
    }

    public List<GameObject> getGameObjects()
    {
        return gameObjects;
    }

    public GameObject getGameObject(UUID uuid)
    {
        return gameObjectMap.get(uuid);
    }

    public void add(GameObject gameObject)
    {
        gameObjects.add(gameObject);
        gameObjectMap.put(gameObject.getUUID(), gameObject);
    }

    public void remove(GameObject gameObject)
    {
        gameObjects.remove(gameObject);
        gameObjectMap.remove(gameObject.getUUID());
    }

    public void update()
    {
        player.update();
        cursor.update();
        gameObjects.forEach(gameObject -> gameObject.update());
    }
}
