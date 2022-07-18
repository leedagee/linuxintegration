package org.freedesktop.secret;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.Position;

/**
 * Auto-generated class.
 */
public class CreateItemTuple extends Tuple {
    @Position(0)
    private DBusPath item;
    @Position(1)
    private DBusPath prompt;

    public CreateItemTuple(DBusPath item, DBusPath prompt) {
        this.item = item;
        this.prompt = prompt;
    }

    public DBusPath getItem() {
        return item;
    }

    public void setItem(DBusPath arg) {
        item = arg;
    }

    public DBusPath getPrompt() {
        return prompt;
    }

    public void setPrompt(DBusPath arg) {
        prompt = arg;
    }


}