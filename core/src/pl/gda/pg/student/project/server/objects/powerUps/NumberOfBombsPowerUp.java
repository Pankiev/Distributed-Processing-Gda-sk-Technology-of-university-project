package pl.gda.pg.student.project.server.objects.powerUps;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.server.GameServer;
import pl.gda.pg.student.project.server.objects.ObjectsIdentifier;

public class NumberOfBombsPowerUp extends PowerUp {

    public NumberOfBombsPowerUp(State linkedState, Vector2 position) {
        super(GameServer.assets.get("bomba_up.bmp"), linkedState);
        super.setX(position.x);
        super.setY(position.y);
    }

    @Override
    public void update() {

    }

    @Override
    public String getIdentifier() {
        return ObjectsIdentifier.getObjectIdentifier(NumberOfBombsPowerUp.class);
    }
}
