package pl.gda.pg.student.project.server.objects;

import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.server.GameServer;

public class Bomb extends GameObject
{
    private ServerPlayer player;
	private boolean playerSteppedOutOfBomb = false;

	public Bomb(State linkedState, Vector2 position, ServerPlayer serverPlayer)
	{
		super(GameServer.assets.get("bomba.png"), linkedState);
        super.setX(position.x);
        super.setY(position.y);
        this.player = serverPlayer;
    }

    @Override
    public void update() 
    {
		if (!this.isColliding(player))
			playerSteppedOutOfBomb = true;
    }

    @Override
    public String getIdentifier() {
        return ObjectsIdentifier.getObjectIdentifier(Bomb.class);
    }

	@Override
	protected boolean isCollideable()
	{
		return playerSteppedOutOfBomb;
	}
}
