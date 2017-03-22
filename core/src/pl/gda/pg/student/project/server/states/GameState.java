package pl.gda.pg.student.project.server.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.gda.pg.student.project.libgdxcommon.GameObjectsContainer;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 22.03.2017.
 * Gry Karciane
 */
public class GameState extends State implements GameObjectsContainer {

    private HashMap<Long, GameObject> objects;


    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void update() {

    }

    @Override
    public void add(GameObject object) {
        objects.put(object.getId(), object);
    }

    @Override
    public void remove(GameObject object) {
        objects.remove(object.getId());
    }

    @Override
    public Map<Long, GameObject> getGameObjects() {
        return objects;
    }
}
