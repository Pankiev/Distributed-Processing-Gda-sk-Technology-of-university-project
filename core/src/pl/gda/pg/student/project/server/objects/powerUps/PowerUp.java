package pl.gda.pg.student.project.server.objects.powerUps;


import com.badlogic.gdx.graphics.Texture;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;

public abstract class PowerUp extends GameObject
{

    protected PowerUp(Texture lookout, State linkedState) {
        super(lookout, linkedState);
    }

}
