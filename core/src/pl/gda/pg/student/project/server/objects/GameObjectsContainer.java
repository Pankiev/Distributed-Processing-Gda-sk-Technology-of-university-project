package pl.gda.pg.student.project.server.objects;

import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;

import java.util.Map;

public interface GameObjectsContainer
{
    GameObject getObject(long id);
    
    void add(GameObject object);
    
    void remove(GameObject object);
    
    Map<Long, GameObject> getGameObjects();
}
