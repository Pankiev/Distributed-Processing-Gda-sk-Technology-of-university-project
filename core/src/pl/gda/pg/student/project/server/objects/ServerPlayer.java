package pl.gda.pg.student.project.server.objects;

import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.MovableGameObject;
import pl.gda.pg.student.project.server.GameServer;

public class ServerPlayer extends MovableGameObject
{

    public ServerPlayer(State linkedState)
    {
        super(GameServer.assets.get("pacman_dol.bmp"), linkedState);
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
