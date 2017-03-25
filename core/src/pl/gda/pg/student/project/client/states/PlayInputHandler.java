package pl.gda.pg.student.project.client.states;

import com.esotericsoftware.kryonet.Client;

import pl.gda.pg.student.project.client.objects.ModelPlayer;
import pl.gda.pg.student.project.libgdxcommon.input.InputProcessorAdapter;
import pl.gda.pg.student.project.libgdxcommon.input.KeyHandler;
import pl.gda.pg.student.project.packets.PlayerPutBombPacket;
import pl.gda.pg.student.project.packets.movement.ObjectMoveDownPacket;
import pl.gda.pg.student.project.packets.movement.ObjectMoveLeftPacket;
import pl.gda.pg.student.project.packets.movement.ObjectMoveRightPacket;
import pl.gda.pg.student.project.packets.movement.ObjectMoveUpPacket;


public class PlayInputHandler extends InputProcessorAdapter
{
    private ModelPlayer playerObject;
    private Client connection;

    public PlayInputHandler(ModelPlayer playerObject, Client connectedClient)
    {
        this.connection = connectedClient;
        this.playerObject = playerObject;
    }

    public class WKeyHandler implements KeyHandler
    {
        @Override
        public void handle()
        {
            ObjectMoveUpPacket moveUpPacket = new ObjectMoveUpPacket();
            moveUpPacket.id = playerObject.getId();
            connection.sendTCP(moveUpPacket);
			playerObject.lookUp();
        }
    }

    public class SKeyHandler implements KeyHandler
    {
        @Override
        public void handle()
        {
            ObjectMoveDownPacket moveDownPacket = new ObjectMoveDownPacket();
            moveDownPacket.id = playerObject.getId();
            connection.sendTCP(moveDownPacket);
			playerObject.lookDown();
        }
    }

    public class AKeyHandler implements KeyHandler
    {
        @Override
        public void handle()
        {
            ObjectMoveLeftPacket moveLeftPacket = new ObjectMoveLeftPacket();
            moveLeftPacket.id = playerObject.getId();
            connection.sendTCP(moveLeftPacket);
			playerObject.lookLeft();
        }
    }

    public class DKeyHandler implements KeyHandler
    {
        @Override
        public void handle()
        {
            ObjectMoveRightPacket moveRightPacket = new ObjectMoveRightPacket();
            moveRightPacket.id = playerObject.getId();
            connection.sendTCP(moveRightPacket);
			playerObject.lookRight();
        }
    }

	public class SpaceKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			PlayerPutBombPacket putBombPacket = new PlayerPutBombPacket();
			putBombPacket.id = playerObject.getId();
			connection.sendTCP(putBombPacket);
		}
	}

}
