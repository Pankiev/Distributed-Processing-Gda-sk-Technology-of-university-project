package pl.gda.pg.student.project.client.objects.poweUps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.client.objects.ConnectionModelObject;


public abstract class ModelPowerUp extends ConnectionModelObject {

    public ModelPowerUp(Texture lookout, Vector2 position) {
        super(lookout, position);
    }
}
