package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.server.GameServer;

public class ModelRangePowerUp extends ConnectionModelObject
{
	public ModelRangePowerUp(Vector2 position)
	{
		super(GameServer.assets.get("zasieg_up.bmp"), position);
	}

}
