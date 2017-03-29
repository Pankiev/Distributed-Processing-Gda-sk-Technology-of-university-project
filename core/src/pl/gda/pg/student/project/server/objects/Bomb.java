package pl.gda.pg.student.project.server.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.libgdxcommon.PacketsSender;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.packets.ExplosionCreatePacket;
import pl.gda.pg.student.project.server.GameServer;
import pl.gda.pg.student.project.server.objects.powerUps.PowerUp;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Bomb extends GameObject
{
	private ServerPlayer player;
	private boolean playerSteppedOutOfBomb = false;
	private float timeToExplosion = 10.0f;
	private List<Explosion> explosionChunks;
	private boolean isAfterExplosion = false;

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

	public void explode()
	{
		isAfterExplosion = true;
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

		Collection<GameObject> explosionColliders = explosionMaker.getExplosionColliders();
		for (GameObject explosionCollider : explosionColliders)
			handle(explosionCollider);
		deleteItself();
		player.bombExploded();
	}

	private void handle(GameObject explosionCollider) {
		if (explosionCollider instanceof Box)
			((Box) explosionCollider).destroyedByBomb();
		else if (explosionCollider instanceof PowerUp)
			explosionCollider.deleteItself();
		else if (explosionCollider instanceof Bomb) {
			Bomb bomb = ((Bomb) explosionCollider);
			if (!bomb.isAfterExplosion())
				bomb.explode();
		} else if (explosionCollider instanceof ServerPlayer) {
			explosionCollider.deleteItself();
		}
	}


	private void sendExplosionCreationInfo(Explosion explosion)
	{
		ExplosionCreatePacket createExplosion = new ExplosionCreatePacket();
		createExplosion.textureName = explosion.getTextureName();
		createExplosion.objectType = explosion.getIdentifier();
		createExplosion.id = explosion.getId();
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

	public boolean isAfterExplosion() {
		return isAfterExplosion;
	}

}
