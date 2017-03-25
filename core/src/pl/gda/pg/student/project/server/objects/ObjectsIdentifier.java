package pl.gda.pg.student.project.server.objects;

import pl.gda.pg.student.project.server.objects.powerUps.NumberOfBombsPowerUp;
import pl.gda.pg.student.project.server.objects.powerUps.RangePowerUp;
import pl.gda.pg.student.project.server.objects.powerUps.SpeedUpPowerUp;

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
		identifiers.put(Bomb.class, "Bomb");
		identifiers.put(Explosion.class, "Explosion");
        identifiers.put(NumberOfBombsPowerUp.class, "NumberOfBombsPowerUp");
        identifiers.put(RangePowerUp.class, "RangePowerUp");
        identifiers.put(SpeedUpPowerUp.class, "SpeedUpPowerUp");
    }
    
    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
