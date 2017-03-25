package pl.gda.pg.student.project.server.objects;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.libgdxcommon.PacketsSender;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.packets.CreateObjectPacket;
import pl.gda.pg.student.project.packets.RemoveObjectInfo;
import pl.gda.pg.student.project.server.GameServer;

public class Box extends GameObject
{
	private final static Random random = new Random();

	public Box(State linkedState, Vector2 position)
	{
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
		if (random.nextInt() % 2 == 0)
			addRandomPowerUp();

		deleteItself();
	}

	private void addRandomPowerUp()
	{
		GameObject powerUp = randomPowerUp();
		CreateObjectPacket powerUpCreationPacket = new CreateObjectPacket();
		powerUpCreationPacket.id = powerUp.getId();
		powerUpCreationPacket.objectType = powerUp.getIdentifier();
		powerUpCreationPacket.xPosition = powerUp.getX();
		powerUpCreationPacket.yPosition = powerUp.getY();
		((PacketsSender)linkedState).send(powerUpCreationPacket); 
		((GameObjectsContainer)linkedState).add(powerUp);
	}

	private GameObject randomPowerUp()
	{
		// TODO Auto-generated method stub
		return null;
	}

	private void deleteItself()
	{
		RemoveObjectInfo deleteItselfPacket = new RemoveObjectInfo();
		deleteItselfPacket.id = this.getId();
		((PacketsSender) linkedState).send(deleteItselfPacket);
		((GameObjectsContainer) linkedState).remove(this.getId());
	}
}
