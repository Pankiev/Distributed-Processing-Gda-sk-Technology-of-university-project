package pl.gda.pg.student.project.client;

import java.util.LinkedList;
import java.util.List;

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
import pl.gda.pg.student.project.client.states.ClientPlayState;
import pl.gda.pg.student.project.client.states.ConnectionState;
import pl.gda.pg.student.project.kryonetcommon.PacketsRegisterer;
import pl.gda.pg.student.project.libgdxcommon.Assets;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.StateManager;
import pl.gda.pg.student.project.packets.CreateObjectPacket;
import pl.gda.pg.student.project.packets.RemoveObjectInfo;
import pl.gda.pg.student.project.packets.movement.ObjectSetPositionPacket;

public class GameClient extends ApplicationAdapter
{
	private SpriteBatch batch;
	public static Assets assets;
	public static StateManager states;
	private static ClientPlayState playState;
	private static List<Object> unhandledPackets = new LinkedList<>();
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

	private static void handlePacket(Object object)
	{
		if (object instanceof ObjectSetPositionPacket)
		{
			ObjectSetPositionPacket objectSetPositionPacket = (ObjectSetPositionPacket) object;
			ConnectionModelObject connectionModelObject = playState.getGameObjectById(objectSetPositionPacket.id);
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
			if (playState == null)
			{
				unhandledPackets.add(object);
				return;
			}
			handlePacket(object);
			System.out.println(
					"Client side: object reveived from server, client id: " + connection.getID() + " " + object);
		}

	}

	public static void setPlayState(ClientPlayState playState)
	{
		GameClient.playState = playState;
		for(Object packet : unhandledPackets)
			handlePacket(packet);
	}
}
