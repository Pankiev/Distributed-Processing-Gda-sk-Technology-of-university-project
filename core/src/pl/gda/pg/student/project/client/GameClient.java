package pl.gda.pg.student.project.client;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import pl.gda.pg.student.project.client.objects.ConnectionModelObject;
import pl.gda.pg.student.project.client.objects.ModelObjectsFactory;
import pl.gda.pg.student.project.client.objects.ModelPlayer;
import pl.gda.pg.student.project.client.states.ClientPlayState;
import pl.gda.pg.student.project.client.states.ConnectionState;
import pl.gda.pg.student.project.kryonetcommon.PacketsRegisterer;
import pl.gda.pg.student.project.libgdxcommon.Assets;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.StateManager;
import pl.gda.pg.student.project.libgdxcommon.exception.GameException;
import pl.gda.pg.student.project.packets.CreateObjectPacket;
import pl.gda.pg.student.project.packets.RemoveObjectInfo;
import pl.gda.pg.student.project.packets.movement.Direction;
import pl.gda.pg.student.project.packets.movement.ObjectSetPositionPacket;

public class GameClient extends ApplicationAdapter
{
    private SpriteBatch batch;
    public static Assets assets;
    public static StateManager states;
    private static ClientPlayState playState;
    private static List<PacketInfo> unhandledPackets = Collections.synchronizedList(new LinkedList<>());
    private Client client;

    @Override
    public void create()
    {
        client = initializeClient();
        assets = new Assets();
        batch = new SpriteBatch();
        states = new StateManager();
        State connectionState = new ConnectionState(client);
        states.push(connectionState);
    }

    private Client initializeClient()
    {
        Client client = new Client();
        Kryo kryo = client.getKryo();
        kryo = PacketsRegisterer.registerAllAnnotated(kryo);
        kryo = PacketsRegisterer.registerDefaults(kryo);
        client.addListener(new ClientListener());
        return client;
    }

    @Override
    public void render()
    {
        update();
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        states.render(batch);
        batch.end();
    }

    private void update()
    {
        states.update();
    }

    @Override
    public void dispose()
    {
        assets.dispose();
    }

    private static void handlePacket(Object object, long id)
    {
        if(playState == null)
            throw new PacketCannotBeHandledYetException();
        if (playState.getClientId() != id)
            throw new PacketCannotBeHandledYetException();

        if (object instanceof ObjectSetPositionPacket)
        {
            ObjectSetPositionPacket objectSetPositionPacket = (ObjectSetPositionPacket) object;
            ConnectionModelObject connectionModelObject = playState.getGameObjectById(objectSetPositionPacket.id);
            if (connectionModelObject == null)
                throw new PacketCannotBeHandledYetException();
            if (connectionModelObject instanceof ModelPlayer)
                adjustTexture((ModelPlayer) connectionModelObject, objectSetPositionPacket.direction);
            connectionModelObject.positionUpdate(new Vector2(objectSetPositionPacket.x, objectSetPositionPacket.y));
        } else if (object instanceof CreateObjectPacket)
        {
            CreateObjectPacket createObjectPacket = (CreateObjectPacket) object;
            ConnectionModelObject newObject = ModelObjectsFactory.produce(createObjectPacket.objectType,
                    new Vector2(createObjectPacket.xPosition, createObjectPacket.yPosition));
            newObject.setId(createObjectPacket.id);
            playState.add(newObject);
        } else if (object instanceof RemoveObjectInfo)
        {
            RemoveObjectInfo removeObjectInfo = (RemoveObjectInfo) object;
            playState.remove(removeObjectInfo.id);
        }

    }

    private static void adjustTexture(ModelPlayer player, String direction)
    {
        if (direction != null)
        {
            if (direction.equals(Direction.DOWN))
                player.lookDown();
            else if (direction.equals(Direction.UP))
                player.lookUp();
            else if (direction.equals(Direction.LEFT))
                player.lookLeft();
            else if (direction.equals(Direction.RIGHT))
                player.lookRight();
        }
    }

    private class ClientListener extends Listener
    {

        @Override
        public void connected(Connection connection)
        {
            System.out.println("Connected, message from client, id: " + connection.getID());
        }

        @Override
        public void disconnected(Connection connection)
        {
            System.out.println("Disconnected, message from client, id: " + connection.getID());
        }

        @Override
        public void received(Connection connection, Object object)
        {
            try
            {
                handlePacket(object, connection.getID());
            }
            catch(PacketCannotBeHandledYetException e)
            {
                synchronized(unhandledPackets)
                {
                    unhandledPackets.add(new PacketInfo((long)connection.getID(), object));
                }
            }
            System.out.println(
                    "Client side: object reveived from server, client id: " + connection.getID() + " " + object);
        }

    }

    public static void setPlayState(ClientPlayState playState)
    {
        GameClient.playState = playState;
        tryHandlingUnhandledPackets();
    }

    public static void tryHandlingUnhandledPackets()
    {
        synchronized(unhandledPackets)
        {
            Iterator<PacketInfo> iterator = unhandledPackets.iterator();
            
            while(iterator.hasNext())
            {
                PacketInfo packetInfo = iterator.next();
                try
                {
                    handlePacket(packetInfo.getPacket(), packetInfo.getId());
                    iterator.remove();;
                } catch (PacketCannotBeHandledYetException e)
                {
                }
            }
        }
    }

    public static void playStateRemoved()
    {
        playState = null;
    }

    private static class PacketCannotBeHandledYetException extends GameException
    {
    }
}
