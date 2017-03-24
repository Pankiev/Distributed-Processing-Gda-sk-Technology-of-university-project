package pl.gda.pg.student.project.client.states;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import pl.gda.pg.student.project.client.objects.ConnectionModelObject;
import pl.gda.pg.student.project.client.objects.ConnectionModelObjectContainer;
import pl.gda.pg.student.project.client.objects.Player;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;

public class ClientPlayState extends State implements ConnectionModelObjectContainer
{
    private Map<Long, GameObject> gameObjects = Collections.synchronizedMap(new TreeMap<>());
    private Player player;
    private PlayInputHandler inputHandler;
    private Client client;

    public ClientPlayState(Client client)
    {
        this.client = client;
        player = new Player(this);
        inputHandler = new PlayInputHandler(player, client);
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

    @Override
    public void add(ConnectionModelObject object)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove(ConnectionModelObject object)
    {
        // TODO Auto-generated method stub
        
    }

}
