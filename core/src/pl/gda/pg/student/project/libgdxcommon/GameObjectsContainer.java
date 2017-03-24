package pl.gda.pg.student.project.libgdxcommon;

import java.util.Map;

import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;

public interface GameObjectsContainer
{
    GameObject getObject(long id);
    
    void add(GameObject object);
    
    void remove(GameObject object);
    
    Map<Long, GameObject> getGameObjects();
}
