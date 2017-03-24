package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.libgdxcommon.exception.GameException;

public class ModelObjectsFactory
{
    public static ConnectionModelObject produce(String identifier, Vector2 position)
    {
        if(identifier.equals(ClientObjectIdentifier.getObjectIdentifier(ModelPlayer.class)))
            return new ModelPlayer(position);
        else if(identifier.equals(ClientObjectIdentifier.getObjectIdentifier(ModelWall.class)))
            return new ModelWall(position);
        
        throw new ObjectIdentifierNotFoundException();
    }
    
    
    private static class ObjectIdentifierNotFoundException extends GameException
    {  
    }
}