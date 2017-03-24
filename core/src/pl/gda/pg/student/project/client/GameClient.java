package pl.gda.pg.student.project.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.gda.pg.student.project.client.states.ConnectionState;
import pl.gda.pg.student.project.kryonetcommon.PacketsRegisterer;
import pl.gda.pg.student.project.libgdxcommon.Assets;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.StateManager;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameClient extends ApplicationAdapter
{
    private SpriteBatch batch;
    public static Assets assets;
    public static StateManager states;
    private Client client;

    @Override
    public void create()
    {
        client = initializeClient();
        assets = new Assets();
        batch = new SpriteBatch();
        states = new StateManager();
        State connectionState = new ConnectionState(client);
        states.push(connectionState);
    }

    private Client initializeClient()
    {
        Client client = new Client();
        Kryo kryo = client.getKryo();
        kryo = PacketsRegisterer.registerAllAnnotated(kryo);
        kryo = PacketsRegisterer.registerDefaults(kryo);
        client.addListener(new ClientListener());
        return client;
    }

    @Override
    public void render()
    {
        update();
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        states.render(batch);
        batch.end();
    }

    private void update()
    {   
        states.update();
    }

    @Override
    public void dispose()
    {
        assets.dispose();
    }
    
    private class ClientListener extends Listener
    {

        @Override
        public void connected(Connection connection)
        {
            System.out.println("Connected, message from client, id: " + connection.getID());
        }

        @Override
        public void disconnected(Connection connection)
        {
            System.out.println("Disconnected, message from client, id: " + connection.getID());
        }

        @Override
        public void received(Connection connection, Object object)
        {


            System.out.println("Client side: object reveived from server, client id: " + connection.getID() + " " + object);
        }
        
    }
}
