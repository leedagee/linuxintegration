package org.freedesktop.secret;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.types.Variant;

/**
 * Auto-generated class.
 */
public class OpenSessionTuple extends Tuple {
    @Position(0)
    private Variant<?> output;
    @Position(1)
    private DBusPath result;

    public OpenSessionTuple(Variant<?> output, DBusPath result) {
        this.output = output;
        this.result = result;
    }

    public Variant<?> getOutput() {
        return output;
    }

    public void setOutput(Variant<?> arg) {
        output = arg;
    }

    public DBusPath getResult() {
        return result;
    }

    public void setResult(DBusPath arg) {
        result = arg;
    }


}