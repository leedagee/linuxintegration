package org.freedesktop.secret;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.annotations.Position;

import java.util.List;

/**
 * Auto-generated class.
 */
public class Secret extends Struct {
    @Position(0)
    private final DBusPath member0;
    @Position(1)
    private final List<Byte> member1;
    @Position(2)
    private final List<Byte> member2;
    @Position(3)
    private final String member3;

    public Secret(DBusPath member0, List<Byte> member1, List<Byte> member2, String member3) {
        this.member0 = member0;
        this.member1 = member1;
        this.member2 = member2;
        this.member3 = member3;
    }


    public DBusPath getMember0() {
        return member0;
    }

    public List<Byte> getMember1() {
        return member1;
    }

    public List<Byte> getMember2() {
        return member2;
    }

    public String getMember3() {
        return member3;
    }


}