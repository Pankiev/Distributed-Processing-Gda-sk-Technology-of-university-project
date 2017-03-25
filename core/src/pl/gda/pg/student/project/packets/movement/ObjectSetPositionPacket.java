package pl.gda.pg.student.project.packets.movement;

import pl.gda.pg.student.project.kryonetcommon.Registerable;

@Registerable
public class ObjectSetPositionPacket
{
    public long id;
    public float x;
    public float y;
	public String direction;
}
