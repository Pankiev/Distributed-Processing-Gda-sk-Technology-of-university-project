package pl.gda.pg.student.project.client.states;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import pl.gda.pg.student.project.kryonetcommon.ConnectionSettings;
import pl.gda.pg.student.project.kryonetcommon.PacketsRegisterer;
import pl.gda.pg.student.project.libgdxcommon.State;

public class ConnectionState extends State implements TextInputListener
{
    private String messageForUser = "";
    private Client client = new Client();
    
    public ConnectionState()
    {
        Kryo kryo = client.getKryo();
        kryo = PacketsRegisterer.registerAllAnnotated(kryo);
        kryo = PacketsRegisterer.registerDefaults(kryo);
        askForIp();
    }

    private void askForIp()
    {
        Gdx.input.getTextInput(this, "Ip adress", "localhost", "");
    }
    
    @Override
    public void render(SpriteBatch batch)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void input(String ip)
    {
        tryConnecting(ip);
    }

    private void tryConnecting(String ip)
    {
        try
        {
            client.connect(1000, ip, ConnectionSettings.CONNECTION_PORT);
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @Override
    public void canceled()
    {
        askForIp();
    }
    
}
