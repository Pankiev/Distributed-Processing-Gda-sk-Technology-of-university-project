package pl.gda.pg.student.project.client.objects;

import java.util.Map;

public interface ConnectionModelObjectContainer
{
    void add(ConnectionModelObject object);

    void remove(ConnectionModelObject object);

    void remove(long id);

    Map<Long, ConnectionModelObject> getGameObjects();

    ConnectionModelObject getGameObjectById(long id);
}
