package pl.gda.pg.student.project.client.objects.powerUps;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.client.GameClient;

public class ModelSpeedUpPowerUp extends ModelPowerUp {

    public ModelSpeedUpPowerUp(Vector2 vector)
    {
        super(GameClient.assets.get("speed_up.bmp"), vector);
    }
}
