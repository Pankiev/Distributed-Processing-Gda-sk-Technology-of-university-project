package pl.gda.pg.student.project.client.states;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import pl.gda.pg.student.project.client.objects.ConnectionModelObject;
import pl.gda.pg.student.project.client.objects.ConnectionModelObjectContainer;
import pl.gda.pg.student.project.client.objects.ModelPlayer;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.packets.DisconnectPacket;

public class ClientPlayState extends State implements ConnectionModelObjectContainer
{
	private Map<Long, ConnectionModelObject> gameObjects = Collections.synchronizedMap(new HashMap<>());
    private ModelPlayer player;
    private PlayInputHandler inputHandler;
    private Client client;

    public ClientPlayState(Client client)
    {
        this.client = client;
        player = new ModelPlayer();
        inputHandler = new PlayInputHandler(player, client);
        Gdx.input.setInputProcessor(inputHandler);
        player.setId(client.getID());
        add(player);
    }
    
    @Override
    public void render(SpriteBatch batch)
    {
		synchronized (gameObjects)
		{
			for (Entry<Long, ConnectionModelObject> object : gameObjects.entrySet())
				object.getValue().render(batch);
		}
    }

    @Override
    public void update()
    {
        inputHandler.process();
    }

    @Override
    public void add(ConnectionModelObject object)
    {
        gameObjects.put(object.getId(), object);
    }

    @Override
    public void remove(ConnectionModelObject object)
    {
        gameObjects.remove(object.getId());
    }

    @Override
    public void remove(long id) {
        if (id == player.getId()) {
            client.sendTCP(new DisconnectPacket());
            Gdx.app.exit();
        } else {
            gameObjects.remove(id);
        }
    }

    @Override
    public Map<Long, ConnectionModelObject> getGameObjects()
    {
        return gameObjects;
    }

    @Override
    public ConnectionModelObject getGameObjectById(long id){
        return gameObjects.get(id);
    }

}
