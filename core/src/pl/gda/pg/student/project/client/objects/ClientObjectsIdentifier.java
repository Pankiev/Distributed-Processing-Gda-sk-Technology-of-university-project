package pl.gda.pg.student.project.client.objects;

import pl.gda.pg.student.project.client.objects.poweUps.ModelNumberOfBombsPowerUp;
import pl.gda.pg.student.project.client.objects.poweUps.ModelRangePowerUp;

import java.util.HashMap;
import java.util.Map;


public class ClientObjectsIdentifier
{
	private static Map<Class<?>, String> identifiers = new HashMap<>();

    static
    {
        identifiers.put(ModelPlayer.class, "Player");
        identifiers.put(ModelWall.class, "Wall");
        identifiers.put(ModelBox.class, "PowerUp");
		identifiers.put(ModelBomb.class, "Bomb");
		identifiers.put(ModelExplosion.class, "Explosion");
        identifiers.put(ModelNumberOfBombsPowerUp.class, "NumberOfBombsPowerUp");
        identifiers.put(ModelRangePowerUp.class, "RangePowerUp");
    }

    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
