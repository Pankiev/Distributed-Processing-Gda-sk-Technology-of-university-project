package pl.gda.pg.student.project.server.objects;

import java.util.HashMap;
import java.util.Map;


public class ObjectsIdentifier
{
    private static Map<Class<?>, String> identifiers = new HashMap<>();
    
    static
    {
        identifiers.put(ServerPlayer.class, "Player");
        identifiers.put(Wall.class, "Wall");
		identifiers.put(Box.class, "Box");
    }
    
    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
