package pl.gda.pg.student.project.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import pl.gda.pg.student.project.client.objects.ConnectionModelObject;
import pl.gda.pg.student.project.client.objects.ConnectionModelObjectContainer;
import pl.gda.pg.student.project.client.objects.Player;
import pl.gda.pg.student.project.libgdxcommon.State;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class ClientPlayState extends State implements ConnectionModelObjectContainer
{
    private Map<Long, ConnectionModelObject> gameObjects = Collections.synchronizedMap(new TreeMap<>());
    private Player player = new Player(new Vector2(0,0));
    private PlayInputHandler inputHandler = new PlayInputHandler(player);
    private Client client;

    public ClientPlayState(Client client)
    {
        this.client = client;
        inputHandler = new PlayInputHandler(player, client);
        Gdx.input.setInputProcessor(inputHandler);
        player.setId(client.getID());
        add(player);
    }

    @Override
    public void render(SpriteBatch batch)
    {
        for(ConnectionModelObject object : gameObjects.values())
            object.render(batch);
    }

    @Override
    public void update()
    {
        inputHandler.process();
    }

    @Override
    public void add(ConnectionModelObject object) {

    }

    @Override
    public void remove(ConnectionModelObject object) {

    }

    @Override
    public Map<Long, ConnectionModelObject> getGameObjects()
    {
        return gameObjects;
    }

}
