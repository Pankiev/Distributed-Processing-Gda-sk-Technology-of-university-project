package pl.gda.pg.student.project.server.objects;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.libgdxcommon.PacketsSender;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.packets.CreateObjectPacket;
import pl.gda.pg.student.project.packets.RemoveObjectInfo;
import pl.gda.pg.student.project.server.GameServer;
import pl.gda.pg.student.project.server.helpers.RandomPowerUp;

public class Box extends GameObject {

    public Box(State linkedState, Vector2 position) {
        super(GameServer.assets.get("skrzynka.bmp"), linkedState);
        super.setX(position.x);
        super.setY(position.y);
    }

    @Override
	public void update()
	{

    }

    @Override
	public String getIdentifier()
	{
        return ObjectsIdentifier.getObjectIdentifier(Box.class);
    }

	public void destroyedByBomb()
	{
		GameObject powerUp = randomPowerUp();
		CreateObjectPacket powerUpCreationPacket = new CreateObjectPacket();
		powerUpCreationPacket.id = powerUp.getId();
		powerUpCreationPacket.objectType = powerUp.getIdentifier();
		powerUpCreationPacket.xPosition = powerUp.getX();
		powerUpCreationPacket.yPosition = powerUp.getY();
		((PacketsSender) linkedState).send(powerUpCreationPacket);
		((GameObjectsContainer) linkedState).add((powerUp));
		deleteItself();
	}

	private GameObject randomPowerUp()
	{
	    RandomPowerUp random = new RandomPowerUp();
	    return random.getRandomPowerUp(linkedState, new Vector2(getX(), getY()));
	}

	private void deleteItself()
	{
		RemoveObjectInfo deleteItselfPacket = new RemoveObjectInfo();
		deleteItselfPacket.id = this.getId();
		((PacketsSender) linkedState).send(deleteItselfPacket);
		((GameObjectsContainer) linkedState).remove(this.getId());
	}
}
