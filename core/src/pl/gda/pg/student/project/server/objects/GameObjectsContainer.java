package pl.gda.pg.student.project.server.objects;

import java.util.Map;

import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;

public interface GameObjectsContainer
{
    GameObject getObject(long id);
    
    void add(GameObject object);
    
    void remove(GameObject object);
    
	void remove(long id);

    Map<Long, GameObject> getGameObjects();
}
