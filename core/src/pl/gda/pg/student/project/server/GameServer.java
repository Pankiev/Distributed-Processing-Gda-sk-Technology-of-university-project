package pl.gda.pg.student.project.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.gda.pg.student.project.libgdxcommon.Assets;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.StateManager;
import pl.gda.pg.student.project.server.states.ObserveState;

public class GameServer extends ApplicationAdapter
{
    private SpriteBatch batch;
    public static Assets assets;
    private StateManager states;

    @Override
    public void create()
    {
        assets = new Assets();
        batch = new SpriteBatch();
        states = new StateManager();
        State observe = new ObserveState();
        states.push(observe);
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
}
