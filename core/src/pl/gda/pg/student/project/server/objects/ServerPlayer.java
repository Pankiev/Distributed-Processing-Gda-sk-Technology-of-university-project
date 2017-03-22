package pl.gda.pg.student.project.server.objects;

import pl.gda.pg.student.project.client.GameClient;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.MovableGameObject;

/**
 * Created by Bartek on 22.03.2017. Gry Karciane
 */
public class ServerPlayer extends MovableGameObject
{

    public ServerPlayer(State linkedState)
    {
        super(GameClient.assets.get("pacman_dol.bmp"), linkedState);
    }

    @Override
    public void update()
    {

    }

}
