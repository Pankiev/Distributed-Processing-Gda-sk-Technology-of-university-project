package pl.gda.pg.student.project.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import pl.gda.pg.student.project.kryonetcommon.ConnectionSettings;
import pl.gda.pg.student.project.libgdxcommon.Assets;
import pl.gda.pg.student.project.libgdxcommon.StateManager;
import pl.gda.pg.student.project.libgdxcommon.exception.GameException;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.libgdxcommon.objects.MovableGameObject;
import pl.gda.pg.student.project.packets.CreateObjectPacket;
import pl.gda.pg.student.project.packets.RemoveObjectInfo;
import pl.gda.pg.student.project.packets.movement.ObjectMoveDownPacket;
import pl.gda.pg.student.project.packets.movement.ObjectMoveLeftPacket;
import pl.gda.pg.student.project.packets.movement.ObjectMoveRightPacket;
import pl.gda.pg.student.project.packets.movement.ObjectMoveUpPacket;
import pl.gda.pg.student.project.packets.movement.ObjectSetPositionPacket;
import pl.gda.pg.student.project.server.objects.ObjectsIdentifier;
import pl.gda.pg.student.project.server.states.ServerPlayState;

import java.io.IOException;
import java.util.Map;

public class GameServer extends ApplicationAdapter
{
    private SpriteBatch batch;
    public static Assets assets;
    private StateManager states;
    private Server server;
    private ServerPlayState gameState;

    @Override
    public void create()
    {
        server = initializeServer();
        assets = new Assets();
        batch = new SpriteBatch();
        states = new StateManager();
        gameState = new ServerPlayState();
        states.push(gameState);
    }

    private Server initializeServer()
    {
        Server server = new Server();
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
    
    private void userConnected(int id)
    {
        Map<Long, GameObject> gameObjects = gameState.getGameObjects();
        for(GameObject object : gameObjects.values())
            sendObjectCreationInfo(object);
        
        CreateObjectPacket createNewPlayer = new CreateObjectPacket();
        createNewPlayer.id = id;
        
    }
    
    private void sendObjectCreationInfo(GameObject object)
    {
        CreateObjectPacket createObjectPacket = new CreateObjectPacket();
        createObjectPacket.id = object.getId();
        createObjectPacket.xPosition = object.getX();
        createObjectPacket.yPosition = object.getY();
        createObjectPacket.objectType = ObjectsIdentifier.getObjectIdentifier(object.getClass());
        server.sendToAllTCP(createObjectPacket);
    }

    private void userDisconnected(long id){
        RemoveObjectInfo removeObjectInfo = new RemoveObjectInfo();
        removeObjectInfo.id = id;
        server.sendToAllTCP(removeObjectInfo);
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
