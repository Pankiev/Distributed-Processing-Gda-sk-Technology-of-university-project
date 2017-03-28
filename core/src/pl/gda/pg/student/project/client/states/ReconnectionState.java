package pl.gda.pg.student.project.client.states;

import java.io.IOException;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import pl.gda.pg.student.project.client.GameClient;
import pl.gda.pg.student.project.libgdxcommon.State;

public class ReconnectionState extends State
{
    private Client client;

    public ReconnectionState(Client client)
    {
        this.client = client;
        tryReconnecting();
    }

    private void tryReconnecting()
    {
        try
        {
            client.reconnect();
        } catch (IOException e)
        {
            GameClient.states.set(new AskToReconnectState(client));
        }
        
    }

    @Override
    public void render(SpriteBatch batch)
    {
    }

    @Override
    public void update()
    {
        if(client.isConnected())
            GameClient.states.set(new ClientPlayState(client));
    }

}
