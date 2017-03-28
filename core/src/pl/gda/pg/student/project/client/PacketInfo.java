package pl.gda.pg.student.project.client;

import java.util.Map;

final class PacketInfo
{
    private final Long id;
    private Object packet;
    private boolean isHandled = false;

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
