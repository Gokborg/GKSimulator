package core;

import gameobjects.Player;

import java.util.*;

public class Scene
{
    private List<GameObject> gameObjects;
    private Map<UUID, GameObject> gameObjectMap;
    private Player player;

    public Scene(Player player)
    {
        gameObjects = new ArrayList<>();
        gameObjectMap = new HashMap<>();
        this.player = player;
        add(player);
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
        gameObjects.forEach(gameObject -> gameObject.update());
    }
}
