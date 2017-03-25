package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.client.GameClient;

public class ModelBomb extends ConnectionModelObject
{
	public ModelBomb(Vector2 position)
	{
		super(GameClient.assets.get("bomba.png"), position);
	}

}
