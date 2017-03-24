package pl.gda.pg.student.project.server.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.server.helpers.FieldInitializer;
import pl.gda.pg.student.project.server.objects.GameObjectsContainer;

import java.util.Map;

public class ServerPlayState extends State implements GameObjectsContainer
{
    private Map<Long, GameObject> objects;

    public ServerPlayState()
    {
        objects = FieldInitializer.initializeField(this);

    }

    @Override
    public void render(SpriteBatch batch)
    {
        for (GameObject object : objects.values())
        {
            object.render(batch);
        }
    }

    @Override
    public void update()
    {

    }

    @Override
    public void add(GameObject object)
    {
        objects.put(object.getId(), object);
    }

    @Override
    public void remove(GameObject object)
    {
        objects.remove(object.getId());
    }

    @Override
    public Map<Long, GameObject> getGameObjects()
    {
        return objects;
    }
    
    @Override
    public GameObject getObject(long id)
    {
        return objects.get(id);
    }
    
    public void updateObjectPosition(long id, Vector2 position)
    {
        GameObject operationTarget = objects.get(id);
        operationTarget.setPosition(position.x, position.y);
    }
}
