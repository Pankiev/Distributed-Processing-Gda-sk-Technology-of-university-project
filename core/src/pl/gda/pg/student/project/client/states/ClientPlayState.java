package pl.gda.pg.student.project.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.gda.pg.student.project.client.objects.Box;
import pl.gda.pg.student.project.client.objects.Player;
import pl.gda.pg.student.project.libgdxcommon.State;


public class ClientPlayState extends State {

    private Player player = new Player(this);
    private InputHandler inputHandler = new InputHandler(player);
    private Box box = new Box(this);


    @Override
    public void render(SpriteBatch batch) {
        box.render(batch);
        player.render(batch);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void update() {
        inputHandler.process();
    }


}
