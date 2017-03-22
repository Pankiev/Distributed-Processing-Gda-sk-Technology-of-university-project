package pl.gda.pg.student.project.client.objects;

import pl.gda.pg.student.project.client.GameClient;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;

/**
 * Created by Bartek on 22.03.2017.
 * Gry Karciane
 */
public class Box extends GameObject {

    public Box(State linkedState) {
        super(GameClient.assets.get("skrzynka.bmp"), linkedState);
    }

    @Override
    public void update() {

    }
}
