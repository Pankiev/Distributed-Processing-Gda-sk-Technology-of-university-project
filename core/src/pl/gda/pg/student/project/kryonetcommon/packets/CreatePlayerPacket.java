package pl.gda.pg.student.project.kryonetcommon.packets;

import pl.gda.pg.student.project.kryonetcommon.Registerable;

@Registerable
public class CreatePlayerPacket
{
    public long id;
    public float xPosition;
    public float yPosition;
}