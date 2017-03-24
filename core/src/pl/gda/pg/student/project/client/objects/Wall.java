package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.client.GameClient;

public class Wall extends ConnectionModelObject {


    public Wall(Vector2 position) {
        super(GameClient.assets.get("filar.bmp"), position);
    }

}
