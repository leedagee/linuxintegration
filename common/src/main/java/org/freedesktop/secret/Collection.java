package org.freedesktop.secret;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.TypeRef;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusProperty;
import org.freedesktop.dbus.annotations.DBusProperty.Access;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.UInt64;
import org.freedesktop.dbus.types.Variant;

import java.util.List;
import java.util.Map;

/**
 * Auto-generated class.
 */
@DBusInterfaceName("org.freedesktop.Secret.Collection")
@DBusProperty(name = "Items", type = Collection.PropertyItemsType.class, access = Access.READ)
@DBusProperty(name = "Label", type = String.class, access = Access.READ_WRITE)
@DBusProperty(name = "Locked", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "Created", type = UInt64.class, access = Access.READ)
@DBusProperty(name = "Modified", type = UInt64.class, access = Access.READ)
public interface Collection extends DBusInterface {


    DBusPath Delete();

    List<DBusPath> SearchItems(Map<String, String> attributes);

    CreateItemTuple CreateItem(Map<String, Variant<?>> properties, Secret secret, boolean replace);


    interface PropertyItemsType extends TypeRef<List<DBusPath>> {


    }

    class ItemCreated extends DBusSignal {

        private final DBusPath item;

        public ItemCreated(String _path, DBusPath _item) throws DBusException {
            super(_path, _item);
            this.item = _item;
        }


        public DBusPath getItem() {
            return item;
        }


    }

    class ItemDeleted extends DBusSignal {

        private final DBusPath item;

        public ItemDeleted(String _path, DBusPath _item) throws DBusException {
            super(_path, _item);
            this.item = _item;
        }


        public DBusPath getItem() {
            return item;
        }


    }

    class ItemChanged extends DBusSignal {

        private final DBusPath item;

        public ItemChanged(String _path, DBusPath _item) throws DBusException {
            super(_path, _item);
            this.item = _item;
        }


        public DBusPath getItem() {
            return item;
        }


    }
}