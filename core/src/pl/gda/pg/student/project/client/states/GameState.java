package pl.gda.pg.student.project.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.gda.pg.student.project.client.objects.Box;
import pl.gda.pg.student.project.client.objects.Player;
import pl.gda.pg.student.project.libgdxcommon.State;

public class GameState extends State
{

    Player player = new Player(this);
    InputHandler inputHandler = new InputHandler(player);
    Box box = new Box(this);

    @Override
    public void render(SpriteBatch batch)
    {
        box.render(batch);
        player.render(batch);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void update()
    {
        inputHandler.process();
    }

}
