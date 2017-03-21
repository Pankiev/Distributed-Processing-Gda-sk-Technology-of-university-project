package pl.gda.pg.student.project.libgdxcommon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.gda.pg.student.project.libgdxcommon.input.InputProcessorAdapter;

public abstract class State
{
    protected OrthographicCamera camera = new OrthographicCamera();

    public abstract void render(SpriteBatch batch);

    public abstract void update();
}
