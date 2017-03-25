package pl.gda.pg.student.project.server.objects;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.server.GameServer;

public class Bomb extends GameObject
{
    private ServerPlayer player;

    public Bomb(State linkedState, Vector2 position, ServerPlayer serverPlayer) {
        super(GameServer.assets.get("skrzynka.bmp"), linkedState);
        super.setX(position.x);
        super.setY(position.y);
        this.player = serverPlayer;
    }

    @Override
    public void update() {

    }

    @Override
    public String getIdentifier() {
        return ObjectsIdentifier.getObjectIdentifier(Bomb.class);
    }
}
