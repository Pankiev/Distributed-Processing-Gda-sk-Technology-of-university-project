package pl.gda.pg.student.project.client.states;

import pl.gda.pg.student.project.client.objects.Player;
import pl.gda.pg.student.project.libgdxcommon.input.InputProcessorAdapter;
import pl.gda.pg.student.project.libgdxcommon.input.KeyHandler;

/**
 * Created by Bartek on 22.03.2017. Gry Karciane
 */
public class PlayInputHandler extends InputProcessorAdapter
{

    private Player playerObject;

    public PlayInputHandler(Player playerObject)
    {
        this.playerObject = playerObject;
    }

    public class WKeyHandler implements KeyHandler
    {

        @Override
        public void handle()
        {
            playerObject.moveUp(); 

        }

    }

    public class SKeyHandler implements KeyHandler
    {

        @Override
        public void handle()
        {
            playerObject.moveDown();

        }

    }

    public class AKeyHandler implements KeyHandler
    {

        @Override
        public void handle()
        {
            playerObject.moveLeft();

        }

    }

    public class DKeyHandler implements KeyHandler
    {

        @Override
        public void handle()
        {
            playerObject.moveRight();

        }

    }

}
