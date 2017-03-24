package pl.gda.pg.student.project.client.objects;

import java.util.Map;
import java.util.TreeMap;


public class ClientObjectsIdentifier
{
    private static Map<Class<?>, String> identifiers = new TreeMap<>();

    static
    {
        identifiers.put(ModelPlayer.class, "Player");
        identifiers.put(ModelWall.class, "Wall");
        identifiers.put(ModelBox.class, "Box");
    }

    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
