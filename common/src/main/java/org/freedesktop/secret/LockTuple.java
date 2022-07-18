package org.freedesktop.secret;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.Position;

import java.util.List;

/**
 * Auto-generated class.
 */
public class LockTuple extends Tuple {
    @Position(0)
    private List<DBusPath> locked;
    @Position(1)
    private DBusPath Prompt;

    public LockTuple(List<DBusPath> locked, DBusPath Prompt) {
        this.locked = locked;
        this.Prompt = Prompt;
    }

    public List<DBusPath> getLocked() {
        return locked;
    }

    public void setLocked(List<DBusPath> arg) {
        locked = arg;
    }

    public DBusPath getPrompt() {
        return Prompt;
    }

    public void setPrompt(DBusPath arg) {
        Prompt = arg;
    }


}