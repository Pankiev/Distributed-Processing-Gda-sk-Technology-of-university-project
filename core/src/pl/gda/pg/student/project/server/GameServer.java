package pl.gda.pg.student.project.server;

import java.io.IOException;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import pl.gda.pg.student.project.kryonetcommon.ConnectionSettings;
import pl.gda.pg.student.project.kryonetcommon.IdSupplier;
import pl.gda.pg.student.project.kryonetcommon.PacketsRegisterer;
import pl.gda.pg.student.project.libgdxcommon.Assets;
import pl.gda.pg.student.project.libgdxcommon.StateManager;
import pl.gda.pg.student.project.libgdxcommon.exception.GameException;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.libgdxcommon.objects.MovableGameObject;
import pl.gda.pg.student.project.packets.CreateObjectPacket;
import pl.gda.pg.student.project.packets.DisconnectPacket;
import pl.gda.pg.student.project.packets.PlayerPutBombPacket;
import pl.gda.pg.student.project.packets.RemoveObjectInfo;
import pl.gda.pg.student.project.packets.movement.ObjectMoveDownPacket;
import pl.gda.pg.student.project.packets.movement.ObjectMoveLeftPacket;
import pl.gda.pg.student.project.packets.movement.ObjectMoveRightPacket;
import pl.gda.pg.student.project.packets.movement.ObjectMoveUpPacket;
import pl.gda.pg.student.project.packets.movement.ObjectSetPositionPacket;
import pl.gda.pg.student.project.server.helpers.PlayerPositioner;
import pl.gda.pg.student.project.server.objects.Bomb;
import pl.gda.pg.student.project.server.objects.ObjectsIdentifier;
import pl.gda.pg.student.project.server.objects.ServerPlayer;
import pl.gda.pg.student.project.server.states.ServerPlayState;

public class GameServer extends ApplicationAdapter
{
    private PlayerPositioner positioner;
    private SpriteBatch batch;
    public static Assets assets;
    private StateManager states;
    private Server server;
    private ServerPlayState gameState;

    @Override
    public void create()
    {
        positioner  = new PlayerPositioner();
        server = initializeServer();
        assets = new Assets();
        batch = new SpriteBatch();
        states = new StateManager();
		gameState = new ServerPlayState(server);
        states.push(gameState);
    }

    private Server initializeServer()
    {
        Server server = new Server();
		Kryo serverKryo = server.getKryo();
		serverKryo = PacketsRegisterer.registerAllAnnotated(serverKryo);
		serverKryo = PacketsRegisterer.registerDefaults(serverKryo);
        server.addListener(new ServerListener());
        server.start();
        tryBindingServer(server, ConnectionSettings.TCP_PORT, ConnectionSettings.UDP_PORT);
        return server;
    }

    private void tryBindingServer(Server server, int tcpPort, int udpPort)
    {
        try
        {
            server.bind(tcpPort, udpPort);
        } catch (IOException e)
        {
            throw new CannotBindServerException(e.getMessage());
        }
    }

    @Override
    public void render()
    {
        update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
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
    
    private void userConnected(int clientId)
    {
        sendGameStateInfo(clientId);
        Vector2 playerPosition = positioner.getPosition();
        addPlayerObjectOnServer(clientId, playerPosition);
        informOthersAboutNewPlayer(clientId, playerPosition);
        sendPositionUpdateInfoToNewClient(clientId, playerPosition);
    }

    private void sendGameStateInfo(int clientId)
    {
        Map<Long, GameObject> gameObjects = gameState.getGameObjects();
        for(GameObject object : gameObjects.values())
            sendObjectCreationInfo(object, clientId);
    }
    
    private void sendObjectCreationInfo(GameObject object, int targetClientId)
    {
        CreateObjectPacket createObjectPacket = new CreateObjectPacket();
        createObjectPacket.id = object.getId();
        createObjectPacket.xPosition = object.getX();
        createObjectPacket.yPosition = object.getY();
        createObjectPacket.objectType = ObjectsIdentifier.getObjectIdentifier(object.getClass());
		server.sendToTCP(targetClientId, createObjectPacket);
    }

	private void addPlayerObjectOnServer(int clientId, Vector2 playerPosition)
	{
		ServerPlayer newPlayer = new ServerPlayer(gameState);
		newPlayer.setId(clientId);
		newPlayer.setPosition(playerPosition.x, playerPosition.y);
		gameState.add(newPlayer);
	}

    private void informOthersAboutNewPlayer(int id, Vector2 playerPosition)
    {
        CreateObjectPacket createNewPlayer = new CreateObjectPacket();
        createNewPlayer.id = id;
        createNewPlayer.objectType = ObjectsIdentifier.getObjectIdentifier(ServerPlayer.class);
        createNewPlayer.xPosition = playerPosition.x;
        createNewPlayer.yPosition = playerPosition.y;
        server.sendToAllExceptTCP(id, createNewPlayer);
    }
    
    private void sendPositionUpdateInfoToNewClient(int clientId, Vector2 playerPosition)
    {
        ObjectSetPositionPacket setPositionPacket = new ObjectSetPositionPacket();
        setPositionPacket.id = clientId; 
        setPositionPacket.x = playerPosition.x;
        setPositionPacket.y = playerPosition.y;
		server.sendToTCP(clientId, setPositionPacket);
    }

    private void userDisconnected(long id)
    {
        RemoveObjectInfo removeObjectInfo = new RemoveObjectInfo();
        removeObjectInfo.id = id;
		server.sendToAllTCP(removeObjectInfo);
		gameState.remove(id);
    }

    private static class CannotBindServerException extends GameException
    {
        public CannotBindServerException(String message)
        {
            super(message);
        }
    }
    
    
    private class ServerListener extends Listener
    {
        @Override
        public void connected(Connection connection)
        {
            userConnected(connection.getID());
            System.out.println("Client connected server side, id: " + connection.getID());
        }

        @Override
        public void disconnected(Connection connection)
        {
            userDisconnected(connection.getID());
            System.out.println("Client connected server side, id: " + connection.getID());
        }

        @Override
        public void received(Connection connection, Object object)
        {
            if(object instanceof ObjectSetPositionPacket)
            {
                ObjectSetPositionPacket setPositionPacket = (ObjectSetPositionPacket)object;
                MovableGameObject operationTarget = (MovableGameObject)gameState.getObject(setPositionPacket.id);
                operationTarget.setPosition(setPositionPacket.x, setPositionPacket.y);
                server.sendToAllTCP(setPositionPacket);
            }
            else if(object instanceof ObjectMoveLeftPacket)
            {
                ObjectMoveLeftPacket moveLeftPacket = (ObjectMoveLeftPacket)object;
                MovableGameObject operationTarget = (MovableGameObject)gameState.getObject(moveLeftPacket.id);
                operationTarget.moveLeft(gameState.getGameObjects().values());
                ObjectSetPositionPacket updatePositionPacket = createSetPositionPacketByObject(operationTarget);
                server.sendToAllTCP(updatePositionPacket);
            }
            else if(object instanceof ObjectMoveRightPacket)
            {
                ObjectMoveRightPacket moveRightPacket = (ObjectMoveRightPacket)object;
                MovableGameObject operationTarget = (MovableGameObject)gameState.getObject(moveRightPacket.id);
                operationTarget.moveRight(gameState.getGameObjects().values());
                ObjectSetPositionPacket updatePositionPacket = createSetPositionPacketByObject(operationTarget);
                server.sendToAllTCP(updatePositionPacket);
            }
            else if (object instanceof ObjectMoveUpPacket)
            {
                ObjectMoveUpPacket moveRightPacket = (ObjectMoveUpPacket)object;
                MovableGameObject operationTarget = (MovableGameObject)gameState.getObject(moveRightPacket.id);
                operationTarget.moveUp(gameState.getGameObjects().values());
                ObjectSetPositionPacket updatePositionPacket = createSetPositionPacketByObject(operationTarget);
                server.sendToAllTCP(updatePositionPacket);
            }
            else if(object instanceof ObjectMoveDownPacket)
            {
                ObjectMoveDownPacket moveRightPacket = (ObjectMoveDownPacket)object;
                MovableGameObject operationTarget = (MovableGameObject)gameState.getObject(moveRightPacket.id);
                operationTarget.moveDown(gameState.getGameObjects().values());
                ObjectSetPositionPacket updatePositionPacket = createSetPositionPacketByObject(operationTarget);
                server.sendToAllTCP(updatePositionPacket);
            }
            else if(object instanceof DisconnectPacket)
                connection.close();
			else if (object instanceof PlayerPutBombPacket)
			{
				PlayerPutBombPacket putBombPacket = (PlayerPutBombPacket) object;
				ServerPlayer player = (ServerPlayer) gameState.getObject(putBombPacket.id);
				if(player.canPlaceBomb()){
                    Bomb bomb = new Bomb(gameState, new Vector2(player.getX(), player.getY()), player);
                    long id = IdSupplier.getId();
                    bomb.setId(id);
                    gameState.add(bomb);
                    CreateObjectPacket createObjectPacket = new CreateObjectPacket();
                    createObjectPacket.xPosition = player.getX();
                    createObjectPacket.yPosition = player.getY();
                    createObjectPacket.id = id;
                    createObjectPacket.objectType = "Bomb";
                    server.sendToAllTCP(createObjectPacket);
                }
			}
            System.out.println("Server side: object reveived from client id: " + connection.getID() + " " + object);
        }

        private ObjectSetPositionPacket createSetPositionPacketByObject(GameObject object)
        {
            ObjectSetPositionPacket packet = new ObjectSetPositionPacket();
            packet.id = object.getId();
            packet.x = object.getX();
            packet.y = object.getY();
            return packet;
        }
        
    }
}
