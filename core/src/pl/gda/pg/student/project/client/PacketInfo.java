package pl.gda.pg.student.project.client;

final class PacketInfo
{
    private final Long id;
    private Object packet;

    public PacketInfo(Long key, Object value) {
        this.id = key;
        this.packet = value;
    }

    public Long getId()
    {
        return id;
    }

    public Object getPacket()
    {
        return packet;
    }
}
