package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.client.objects.powerUps.ModelNumberOfBombsPowerUp;
import pl.gda.pg.student.project.client.objects.powerUps.ModelRangePowerUp;
import pl.gda.pg.student.project.libgdxcommon.exception.GameException;

public class ModelObjectsFactory
{
    public static ConnectionModelObject produce(String identifier, Vector2 position)
    {
        if(identifier.equals(ClientObjectsIdentifier.getObjectIdentifier(ModelPlayer.class)))
            return new ModelPlayer(position);
        else if(identifier.equals(ClientObjectsIdentifier.getObjectIdentifier(ModelWall.class)))
            return new ModelWall(position);
		else if (identifier.equals(ClientObjectsIdentifier.getObjectIdentifier(ModelBox.class)))
			return new ModelBox(position);
		else if (identifier.equals(ClientObjectsIdentifier.getObjectIdentifier(ModelBomb.class)))
			return new ModelBomb(position);
		else if (identifier.equals(ClientObjectsIdentifier.getObjectIdentifier(ModelExplosion.class)))
			return new ModelExplosion(position);
		else if (identifier.equals(ClientObjectsIdentifier.getObjectIdentifier(ModelRangePowerUp.class)))
			return new ModelRangePowerUp(position);
		else if (identifier.equals(ClientObjectsIdentifier.getObjectIdentifier(ModelNumberOfBombsPowerUp.class)))
			return new ModelNumberOfBombsPowerUp(position);
        
		throw new ObjectIdentifierNotFoundException(identifier);
    }
    
    private static class ObjectIdentifierNotFoundException extends GameException
    {  
		public ObjectIdentifierNotFoundException(String identifier)
		{
			super("Identifier: " + identifier);
		}
    }
}
