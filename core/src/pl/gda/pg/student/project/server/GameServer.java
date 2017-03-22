package pl.gda.pg.student.project.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import pl.gda.pg.student.project.kryonetcommon.ConnectionSettings;
import pl.gda.pg.student.project.libgdxcommon.Assets;
import pl.gda.pg.student.project.libgdxcommon.StateManager;
import pl.gda.pg.student.project.libgdxcommon.exception.GameException;
import pl.gda.pg.student.project.server.states.GameState;

import java.io.IOException;

public class GameServer extends ApplicationAdapter
{
    private SpriteBatch batch;
    public static Assets assets;
    private StateManager states;
    private Server server;
    private GameState gameState;

    @Override
    public void create()
    {
        server = initializeServer();
        assets = new Assets();
        batch = new SpriteBatch();
        states = new StateManager();
        gameState = new GameState();
        states.push(gameState);
    }

    private Server initializeServer()
    {
        Server server = new Server();
        tryBindingServer(server, ConnectionSettings.TCP_PORT, ConnectionSettings.UDP_PORT);
        server.addListener(new ServerListener());
        server.start();
        return server;
    }

    private void tryBindingServer(Server server, int tcpPort, int udpPort)
    {
        try
        {
            server.bind(tcpPort, udpPort);
        } catch (IOException e)
        {
            throw new CannotBindServerException(e.getMessage());
        }
    }

    @Override
    public void render()
    {
        update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
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
    
    private static class CannotBindServerException extends GameException
    {
        public CannotBindServerException(String message)
        {
            super(message);
        }
    }
    
    
    private class ServerListener extends Listener
    {

        @Override
        public void connected(Connection connection)
        {
            System.out.println("Client connected server side, id: " + connection.getID());
        }

        @Override
        public void disconnected(Connection connection)
        {
            System.out.println("Client connected server side, id: " + connection.getID());
        }

        @Override
        public void received(Connection connection, Object object)
        {
            System.out.println("Server side: object reveived from client id: " + connection.getID() + " " + object);
        }
        
    }
}
