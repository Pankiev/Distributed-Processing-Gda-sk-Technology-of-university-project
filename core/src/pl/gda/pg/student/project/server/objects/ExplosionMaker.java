package pl.gda.pg.student.project.server.objects;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.kryonetcommon.IdSupplier;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.server.GameServer;

public class ExplosionMaker
{
	private final static int TILE_SIZE = 27;
	private Collection<GameObject> collisionObjects;
	private Vector2 bombPosition;
	private State linkedState;

	public ExplosionMaker(Collection<GameObject> collisionObjects, Vector2 bombPosition, State linkedState)
	{
		this.collisionObjects = collisionObjects;
		this.bombPosition = bombPosition;
		this.linkedState = linkedState;
	}

	public List<Explosion> make(int explosionRange)
	{
		List<Explosion> explosionChunks = new LinkedList<>();
		Explosion centerExplosion = new Explosion(GameServer.assets.get("wyb_gdlp.png"), linkedState);
		centerExplosion.setId(IdSupplier.getId());
		centerExplosion.setPosition(bombPosition.x, bombPosition.y);
		explosionChunks.addAll(createExplosion(explosionRange, new Vector2(-TILE_SIZE, 0)));
		explosionChunks.addAll(createExplosion(explosionRange, new Vector2(TILE_SIZE, 0)));
		explosionChunks.addAll(createExplosion(explosionRange, new Vector2(0, -TILE_SIZE)));
		explosionChunks.addAll(createExplosion(explosionRange, new Vector2(0, TILE_SIZE)));
		return explosionChunks;
	}

	private List<Explosion> createExplosion(int explosionRange, Vector2 translationChunk)
	{
		List<Explosion> explosionChunks = new LinkedList<>();
		Vector2 translation = translationChunk.cpy();
		for (int i = 0; i < explosionRange; i++)
		{
			Vector2 explosionPosition = bombPosition.cpy().add(translation);
			if (!isColliding(explosionPosition))
			{
				Explosion explosion = new Explosion(GameServer.assets.get("wyb_gdlp.png"), linkedState);
				explosion.setId(IdSupplier.getId());
				explosion.setPosition(explosionPosition.x, explosionPosition.y);
				explosionChunks.add(explosion);
			} else
				return explosionChunks;
			translation.add(translationChunk);
		}
		return explosionChunks;
	}

	private boolean isColliding(Vector2 point)
	{
		Rectangle rectangle = new Rectangle(point.x, point.y, 1, 1);
		for (GameObject collisionObject : collisionObjects)
			if (collisionObject.isColliding(rectangle))
				return true;
		return false;
	}
}
