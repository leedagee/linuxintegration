package org.freedesktop.secret;

import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusMemberName;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.Variant;

/**
 * Auto-generated class.
 */
@DBusInterfaceName("org.freedesktop.Secret.Prompt")
public interface Prompt extends DBusInterface {

    @DBusMemberName("Prompt")
    void doPrompt(String window_id);

    void Dismiss();


    class Completed extends DBusSignal {

        private final boolean dismissed;
        private final Variant<?> result;

        public Completed(String _path, boolean _dismissed, Variant<?> _result) throws DBusException {
            super(_path, _dismissed, _result);
            this.dismissed = _dismissed;
            this.result = _result;
        }


        public boolean getDismissed() {
            return dismissed;
        }

        public Variant<?> getResult() {
            return result;
        }


    }
}