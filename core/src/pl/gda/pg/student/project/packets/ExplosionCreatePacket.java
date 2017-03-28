package pl.gda.pg.student.project.packets;

import pl.gda.pg.student.project.kryonetcommon.Registerable;

@Registerable
public class ExplosionCreatePacket {
    public String textureName;
    public String objectType;
    public long id;
    public float xPosition;
    public float yPosition;
}
