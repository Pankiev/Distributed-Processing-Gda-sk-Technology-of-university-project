package pl.gda.pg.student.project.server.objects;

import com.badlogic.gdx.math.Rectangle;
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
import pl.gda.pg.student.project.server.objects.powerUps.SpeedUpPowerUp;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ServerPlayer extends MovableGameObject
{
    private int maximumNumberOfPlacedBombs;
    private int rangeOfExplosion;
    private Map<Long, Bomb> placedBombs = new TreeMap<>();

    public ServerPlayer(State linkedState)
    {
        super(GameServer.assets.get("pacman_dol.png"), linkedState);
        maximumNumberOfPlacedBombs = 1;
        rangeOfExplosion = 1;
    }

    public boolean canPlaceBomb()
    {

        System.out.println(placedBombs.size());
        if(placedBombs.size() >= maximumNumberOfPlacedBombs)
            return false;
        BombLegalPositionFinder bombLegalPositioninder = new BombLegalPositionFinder();
        Vector2 bombPosition = bombLegalPositioninder.countBombLegalPosition(new Vector2(getX(), getY()));
        for(Bomb bomb : placedBombs.values())
            if(bomb.isColliding(new Rectangle(bombPosition.x-1, bombPosition.y-1, 3, 3)))
                return false;
                
        return true;
    }

    public void placeBomb()
    {
        BombLegalPositionFinder bombLegalPositioninder = new BombLegalPositionFinder();
        Vector2 bombPosition = bombLegalPositioninder.countBombLegalPosition(new Vector2(getX(), getY()));
        Bomb bomb = new Bomb(linkedState, bombPosition, this);
        long id = IdSupplier.getId();
        bomb.setId(id);
        placedBombs.put(bomb.getId(), bomb);
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

    private void increseMaximumNumberOfBombs()
    {
        maximumNumberOfPlacedBombs++;
    }

    private void handlePowerUpCollision(PowerUp powerUp)
    {
        if (powerUp instanceof RangePowerUp)
        {
            increaseRange();
        } else if (powerUp instanceof NumberOfBombsPowerUp)
        {
            increseMaximumNumberOfBombs();
        } else if (powerUp instanceof SpeedUpPowerUp)
        {
            setMoveSpeed(5 + getMoveSpeed());
        }
        powerUp.deleteItself();
    }

    private void handleCollision(GameObject object)
    {
        if (object instanceof PowerUp)
        {
            handlePowerUpCollision((PowerUp) object);
        } else if (object instanceof Explosion)
        {
            deleteItself();
        }
    }

    @Override
    public GameObject moveLeft(Collection<GameObject> possibleCollision)
    {
        GameObject object = super.moveLeft(possibleCollision);
        handleCollision(object);
        return object;
    }

    @Override
    public GameObject moveRight(Collection<GameObject> possibleCollision)
    {
        GameObject object = super.moveRight(possibleCollision);
        handleCollision(object);
        return object;
    }

    @Override
    public GameObject moveDown(Collection<GameObject> possibleCollision)
    {
        GameObject object = super.moveDown(possibleCollision);
        handleCollision(object);
        return object;
    }

    @Override
    public GameObject moveUp(Collection<GameObject> possibleCollision)
    {
        GameObject object = super.moveUp(possibleCollision);
        handleCollision(object);
        return object;
    }

    public int getRangeOfExplosion()
    {
        return rangeOfExplosion;
    }

    public void bombExploded(Bomb explodedBomb)
    {
        placedBombs.remove(explodedBomb.getId());
    }

    private boolean areEqual(Bomb explodedBomb, Bomb bomb)
    {
        return (int)bomb.getX() == (int)explodedBomb.getY() && (int)bomb.getY() == (int)explodedBomb.getY();
    }

    @Override
    public void update()
    {

    }

    @Override
    public String getIdentifier()
    {
        return ObjectsIdentifier.getObjectIdentifier(ServerPlayer.class);
    }

}
