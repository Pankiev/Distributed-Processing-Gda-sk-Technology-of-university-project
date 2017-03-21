package pl.gda.pg.student.project.server.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.server.objects.ShowdownObject;

public class ObserveState extends State
{
    private ShowdownObject object = new ShowdownObject(this);
    private ShowdownInputHandler inputHandler = new ShowdownInputHandler(object);
    
    @Override
    public void render(SpriteBatch batch)
    {
        object.render(batch);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void update()
    {
        inputHandler.process();
        
    }

}
