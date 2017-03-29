package pl.gda.pg.student.project.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import pl.gda.pg.student.project.client.GameClient;
import pl.gda.pg.student.project.libgdxcommon.State;

public class AskToReconnectState extends State implements TextInputListener
{
    private Client client;

    public AskToReconnectState(Client client)
    {
        this.client = client;
        askToReconnect();
    }

    private void askToReconnect()
    {
        Gdx.input.getTextInput(this, "Reconnect?", "y", "");
        
    }

    @Override
    public void render(SpriteBatch batch)
    {
    }

    @Override
    public void update()
    {
    }

    @Override
    public void input(String answer)
    {
        if(answer.equals("y"))
            GameClient.states.set(new ReconnectionState(client));
        else
            Gdx.app.exit();   
    }

    @Override
    public void canceled()
    {
        Gdx.app.exit();        
    }

}
