package pl.gda.pg.student.project.server.objects;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.kryonetcommon.IdSupplier;
import pl.gda.pg.student.project.libgdxcommon.PacketsSender;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.packets.CreateObjectPacket;
import pl.gda.pg.student.project.packets.RemoveObjectInfo;
import pl.gda.pg.student.project.server.GameServer;

public class Bomb extends GameObject
{
	private ServerPlayer player;
	private boolean playerSteppedOutOfBomb = false;
	private float timeToExplosion = 2.0f;
	private List<Explosion> explosionChunks;

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
		handlePlayerCollision();
		handleTimer();
	}

	private void handlePlayerCollision()
	{
		if (!this.isColliding(player))
			playerSteppedOutOfBomb = true;
	}

	private void handleTimer()
	{
		timeToExplosion -= Gdx.graphics.getDeltaTime();
		if (timeToExplosion <= 0.0f)
			explode();
	}

	private void explode()
	{
		Map<Long, GameObject> collisionObjects = ((GameObjectsContainer) linkedState).getGameObjects();
		ExplosionMaker explosionMaker = new ExplosionMaker(
				collisionObjects.values(), 
				new Vector2(getX(), getY()),
				linkedState);

		explosionChunks = explosionMaker.make(player.getRangeOfExplosion());
		for (Explosion explosion : explosionChunks)
		{
			((GameObjectsContainer) linkedState).add(explosion);
			sendExplosionCreationInfo(explosion);
		}
		deleteItself();
		player.bombExploded();
	}

	private void deleteItself()
	{
		RemoveObjectInfo deleteItselfPacket = new RemoveObjectInfo();
		deleteItselfPacket.id = this.getId();
		((PacketsSender) linkedState).send(deleteItselfPacket);
		((GameObjectsContainer) linkedState).remove(this.getId());
	}

	private void sendExplosionCreationInfo(Explosion explosion)
	{
		CreateObjectPacket createExplosion = new CreateObjectPacket();
		createExplosion.objectType = explosion.getIdentifier();
		createExplosion.id = IdSupplier.getId();
		createExplosion.xPosition = explosion.getX();
		createExplosion.yPosition = explosion.getY();
		((PacketsSender) linkedState).send(createExplosion);
	}

	@Override
	public String getIdentifier()
	{
		return ObjectsIdentifier.getObjectIdentifier(Bomb.class);
	}

	@Override
	protected boolean isCollideable()
	{
		return playerSteppedOutOfBomb;
	}
}
