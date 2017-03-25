package pl.gda.pg.student.project.server.objects;

import java.util.Collection;

import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.kryonetcommon.IdSupplier;
import pl.gda.pg.student.project.libgdxcommon.PacketsSender;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.libgdxcommon.objects.MovableGameObject;
import pl.gda.pg.student.project.packets.CreateObjectPacket;
import pl.gda.pg.student.project.server.GameServer;
import pl.gda.pg.student.project.server.helpers.BombLegalPositionFinder;
import pl.gda.pg.student.project.server.objects.powerUps.NumberOfBombsPowerUp;
import pl.gda.pg.student.project.server.objects.powerUps.PowerUp;
import pl.gda.pg.student.project.server.objects.powerUps.RangePowerUp;

public class ServerPlayer extends MovableGameObject
{
    private int maximumNumberOfPlacedBombs;
    private int numberOfPlacedBombs;
    private int rangeOfExplosion;

    public ServerPlayer(State linkedState)
    {
        super(GameServer.assets.get("pacman_dol.png"), linkedState);
        maximumNumberOfPlacedBombs = 1;
        numberOfPlacedBombs = 0;
        rangeOfExplosion = 1;
    }

	public boolean canPlaceBomb()
    {
		return numberOfPlacedBombs < maximumNumberOfPlacedBombs;
    }

	public void placeBomb()
	{
		numberOfPlacedBombs++;
		BombLegalPositionFinder bombLegalPositioninder = new BombLegalPositionFinder();
		Vector2 bombPosition = bombLegalPositioninder.countBombLegalPosition(new Vector2(getX(), getY()));
		Bomb bomb = new Bomb(linkedState, bombPosition, this);
		long id = IdSupplier.getId();
		bomb.setId(id);
		((GameObjectsContainer) linkedState).add(bomb);
		CreateObjectPacket createObjectPacket = new CreateObjectPacket();
		createObjectPacket.xPosition = bombPosition.x;
		createObjectPacket.yPosition = bombPosition.y;
		createObjectPacket.id = id;
		createObjectPacket.objectType = "Bomb";
		((PacketsSender) linkedState).send(createObjectPacket);
    }

	private void increaseRange()
	{
        rangeOfExplosion++;
    }

    private void increseMaximumNumberOfBombs(){
        maximumNumberOfPlacedBombs++;
    }

    private void handlePowerUpCollision(PowerUp powerUp){
        if(powerUp instanceof RangePowerUp){
            increaseRange();
        } else if(powerUp instanceof NumberOfBombsPowerUp){
            increseMaximumNumberOfBombs();
        }
        powerUp.deleteItself();
    }

    @Override
    public GameObject moveLeft(Collection<GameObject> possibleCollision)
    {
        GameObject object = super.moveLeft(possibleCollision);
        if(object instanceof PowerUp){
            handlePowerUpCollision((PowerUp) object);
        }
        return object;
    }

    @Override
    public GameObject moveRight(Collection<GameObject> possibleCollision)
    {
        GameObject object = super.moveRight(possibleCollision);
        if(object instanceof PowerUp){
            handlePowerUpCollision((PowerUp) object);
        }
        return object;
    }

    @Override
    public GameObject moveDown(Collection<GameObject> possibleCollision)
    {
        GameObject object = super.moveDown(possibleCollision);
        if(object instanceof PowerUp){
            handlePowerUpCollision((PowerUp) object);
        }
        return object;
    }

    @Override
    public GameObject moveUp(Collection<GameObject> possibleCollision)
    {
        GameObject object = super.moveUp(possibleCollision);
        if(object instanceof PowerUp){
            handlePowerUpCollision((PowerUp) object);
        }
        return object;
    }



	public int getRangeOfExplosion()
	{
		return rangeOfExplosion;
	}

	public void bombExploded()
	{
		numberOfPlacedBombs--;
	}

    @Override
    public void update()
    {

    }

    @Override
    public String getIdentifier() {
        return ObjectsIdentifier.getObjectIdentifier(ServerPlayer.class);
    }

}
