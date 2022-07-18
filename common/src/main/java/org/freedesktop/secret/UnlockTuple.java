package org.freedesktop.secret;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.Position;

import java.util.List;

/**
 * Auto-generated class.
 */
public class UnlockTuple extends Tuple {
    @Position(0)
    private List<DBusPath> unlocked;
    @Position(1)
    private DBusPath prompt;

    public UnlockTuple(List<DBusPath> unlocked, DBusPath prompt) {
        this.unlocked = unlocked;
        this.prompt = prompt;
    }

    public List<DBusPath> getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(List<DBusPath> arg) {
        unlocked = arg;
    }

    public DBusPath getPrompt() {
        return prompt;
    }

    public void setPrompt(DBusPath arg) {
        prompt = arg;
    }


}