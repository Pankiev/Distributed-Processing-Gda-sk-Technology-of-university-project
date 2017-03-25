package pl.gda.pg.student.project.server.objects;

import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.MovableGameObject;
import pl.gda.pg.student.project.server.GameServer;

public class ServerPlayer extends MovableGameObject
{

    private int maximumNumberOfPlacedBombs;
    private int numberOfPlacedBombs;

    public ServerPlayer(State linkedState)
    {
        super(GameServer.assets.get("pacman_dol.bmp"), linkedState);
        maximumNumberOfPlacedBombs = 1;
        numberOfPlacedBombs = 0;
    }

    public boolean placeBomb(){
        //TODO
        return false;
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
