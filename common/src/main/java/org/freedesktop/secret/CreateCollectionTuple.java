package org.freedesktop.secret;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.Position;

/**
 * Auto-generated class.
 */
public class CreateCollectionTuple extends Tuple {
    @Position(0)
    private DBusPath collection;
    @Position(1)
    private DBusPath prompt;

    public CreateCollectionTuple(DBusPath collection, DBusPath prompt) {
        this.collection = collection;
        this.prompt = prompt;
    }

    public DBusPath getCollection() {
        return collection;
    }

    public void setCollection(DBusPath arg) {
        collection = arg;
    }

    public DBusPath getPrompt() {
        return prompt;
    }

    public void setPrompt(DBusPath arg) {
        prompt = arg;
    }


}