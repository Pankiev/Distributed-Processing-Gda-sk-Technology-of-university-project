package pl.gda.pg.student.project.server.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;

public class Explosion extends GameObject
{
	private float livingTimeLeft = 0.5f;

	protected Explosion(Texture lookout, State linkedState)
	{
		super(lookout, linkedState);
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

}
