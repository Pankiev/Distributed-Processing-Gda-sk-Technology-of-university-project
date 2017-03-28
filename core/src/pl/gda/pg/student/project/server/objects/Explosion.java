package pl.gda.pg.student.project.server.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.server.GameServer;

public class Explosion extends GameObject
{
	private float livingTimeLeft = 0.5f;
	private String textureName;

	public Explosion(Texture lookout, State linkedState)
	{
		super(lookout, linkedState);
	}


	public Explosion(String textureName, State linkedState)
	{
		super(GameServer.assets.get(textureName), linkedState);
		this.textureName = textureName;
	}

	@Override
	public void update()
	{
		handleLivingTime();
	}

	private void handleLivingTime()
	{
		livingTimeLeft -= Gdx.graphics.getDeltaTime();
		if (livingTimeLeft <= 0.0f)
			deleteItself();
	}

	@Override
	public String getIdentifier()
	{
		return ObjectsIdentifier.getObjectIdentifier(Explosion.class);
	}

	public String getTextureName() {
		return textureName;
	}
}
