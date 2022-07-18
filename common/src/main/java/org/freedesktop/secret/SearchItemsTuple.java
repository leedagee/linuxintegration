package org.freedesktop.secret;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.Position;

import java.util.List;

/**
 * Auto-generated class.
 */
public class SearchItemsTuple extends Tuple {
    @Position(0)
    private List<DBusPath> unlocked;
    @Position(1)
    private List<DBusPath> locked;

    public SearchItemsTuple(List<DBusPath> unlocked, List<DBusPath> locked) {
        this.unlocked = unlocked;
        this.locked = locked;
    }

    public List<DBusPath> getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(List<DBusPath> arg) {
        unlocked = arg;
    }

    public List<DBusPath> getLocked() {
        return locked;
    }

    public void setLocked(List<DBusPath> arg) {
        locked = arg;
    }


}