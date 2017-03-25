package pl.gda.pg.student.project.server.objects.powerUps;


import com.badlogic.gdx.graphics.Texture;

import pl.gda.pg.student.project.libgdxcommon.PacketsSender;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.packets.RemoveObjectInfo;
import pl.gda.pg.student.project.server.objects.GameObjectsContainer;

public abstract class PowerUp extends GameObject
{

    protected PowerUp(Texture lookout, State linkedState) {
        super(lookout, linkedState);
    }

    public void deleteItself()
    {
        RemoveObjectInfo deleteItselfPacket = new RemoveObjectInfo();
        deleteItselfPacket.id = this.getId();
        ((PacketsSender) linkedState).send(deleteItselfPacket);
        ((GameObjectsContainer) linkedState).remove(this.getId());
    }

}
