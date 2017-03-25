package pl.gda.pg.student.project.client.objects.powerUps;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.client.GameClient;

public class ModelRangePowerUp extends ModelPowerUp {

    public ModelRangePowerUp(Vector2 vector)
    {
        super(GameClient.assets.get("zasieg_up.bmp"), vector);
    }
}
