package org.freedesktop.secret;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.TypeRef;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusProperty;
import org.freedesktop.dbus.annotations.DBusProperty.Access;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.UInt64;

import java.util.Map;

/**
 * Auto-generated class.
 */
@DBusInterfaceName("org.freedesktop.Secret.Item")
@DBusProperty(name = "Locked", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "Attributes", type = Item.PropertyAttributesType.class, access = Access.READ_WRITE)
@DBusProperty(name = "Label", type = String.class, access = Access.READ_WRITE)
@DBusProperty(name = "Created", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "Modified", type = UInt64.class, access = Access.READ)
public interface Item extends DBusInterface {


    DBusPath Delete();

    Secret GetSecret(DBusPath session);

    void SetSecret(Secret secret);


    interface PropertyAttributesType extends TypeRef<Map<String, String>> {


    }
}