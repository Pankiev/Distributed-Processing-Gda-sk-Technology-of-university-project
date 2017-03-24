package pl.gda.pg.student.project.server.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class ObjectsIdentifier
{
    private static Map<Class<?>, String> identifiers = new HashMap<>();
    
    static
    {
        identifiers.put(ServerPlayer.class, "Player");
        identifiers.put(Wall.class, "Wall");
    }
    
    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
