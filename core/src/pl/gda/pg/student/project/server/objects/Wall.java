package pl.gda.pg.student.project.server.objects;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.server.GameServer;

/**
 * Created by Bartek on 22.03.2017.
 * Gry Karciane
 */
public class Wall extends GameObject {


    public Wall(State linkedState, Vector2 position) {
        super(GameServer.assets.get("filar.bmp"), linkedState);
        super.setX(position.x);
        super.setY(position.y);
    }

    @Override
    public void update() {

    }
}
