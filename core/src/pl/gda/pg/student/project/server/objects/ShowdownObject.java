package pl.gda.pg.student.project.server.objects;

import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.MovableGameObject;
import pl.gda.pg.student.project.server.GameServer;

public class ShowdownObject extends MovableGameObject
{
    public ShowdownObject(State linkedState)
    {
        super(GameServer.assets.get("pacman_dol.bmp"), linkedState);
    }

    @Override
    public void update()
    {
        
    }

}
