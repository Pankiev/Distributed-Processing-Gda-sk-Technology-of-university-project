package pl.gda.pg.student.project.packets.creating;

import pl.gda.pg.student.project.kryonetcommon.Registerable;

@Registerable
public class CreateBoxPacket {
    public long id;
    public float xPosition;
    public float yPosition;
}
