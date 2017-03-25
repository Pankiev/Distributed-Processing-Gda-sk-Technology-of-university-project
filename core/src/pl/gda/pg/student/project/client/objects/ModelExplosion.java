package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.client.GameClient;

public class ModelExplosion extends ConnectionModelObject
{
	public ModelExplosion(Vector2 position)
	{
		super(GameClient.assets.get("wyb_gdlp.png"), position);
	}

}
