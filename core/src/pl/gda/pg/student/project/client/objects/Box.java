package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.client.GameClient;

public class Box extends ConnectionModelObject {

    public Box(Vector2 vector) {
        super(GameClient.assets.get("skrzynka.bmp"), vector);
    }

}
