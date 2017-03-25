package pl.gda.pg.student.project.client.objects.poweUps;


import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.client.GameClient;

public class ModelNumberOfBombsPowerUp extends ModelPowerUp {

    public ModelNumberOfBombsPowerUp(Vector2 vector)
    {
        super(GameClient.assets.get("bomba_up.bmp"), vector);
    }

}
