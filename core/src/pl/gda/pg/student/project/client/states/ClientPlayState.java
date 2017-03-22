package pl.gda.pg.student.project.client.states;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import pl.gda.pg.student.project.client.objects.Box;
import pl.gda.pg.student.project.client.objects.Player;
import pl.gda.pg.student.project.libgdxcommon.GameObjectsContainer;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;

public class ClientPlayState extends State implements GameObjectsContainer
{
    private Map<Long, GameObject> gameObjects = Collections.synchronizedMap(new TreeMap<>());
    private Player player = new Player(this);
    private PlayInputHandler inputHandler = new PlayInputHandler(player);
    private Client client;

    public ClientPlayState(Client client)
    {
        this.client = client;
        Gdx.input.setInputProcessor(inputHandler);
        player.setId(client.getID());
        add(player);
    }
    
    @Override
    public void render(SpriteBatch batch)
    {
        for(GameObject object : gameObjects.values())
            object.render(batch);
    }

    @Override
    public void update()
    {
        for(GameObject object : gameObjects.values())
            object.update();
        inputHandler.process();
    }

    @Override
    public void add(GameObject object)
    {
        gameObjects.put(object.getId(), object);
    }

    @Override
    public void remove(GameObject object)
    {
        gameObjects.remove(object.getId());
    }

    @Override
    public Map<Long, GameObject> getGameObjects()
    {
        return gameObjects;
    }

}
