package pl.gda.pg.student.project.server.objects;


import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.server.GameServer;

public class ServerBox extends GameObject {

    public ServerBox(State linkedState, Vector2 position) {
        super(GameServer.assets.get("skrzynka.bmp"), linkedState);
        super.setX(position.x);
        super.setY(position.y);
    }

    @Override
    public void update() {

    }
}
