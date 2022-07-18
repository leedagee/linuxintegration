package org.freedesktop.secret;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.TypeRef;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusProperty;
import org.freedesktop.dbus.annotations.DBusProperty.Access;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.Variant;

import java.util.List;
import java.util.Map;

/**
 * Auto-generated class.
 */
@DBusInterfaceName("org.freedesktop.Secret.Service")
@DBusProperty(name = "Collections", type = Service.PropertyCollectionsType.class, access = Access.READ)
public interface Service extends DBusInterface {


    OpenSessionTuple OpenSession(String algorithm, Variant<?> input);

    CreateCollectionTuple CreateCollection(Map<String, Variant<?>> properties, String alias);

    SearchItemsTuple SearchItems(Map<String, String> attributes);

    UnlockTuple Unlock(List<DBusPath> objects);

    LockTuple Lock(List<DBusPath> objects);

    Map<DBusPath, Secret> GetSecrets(List<DBusPath> items, DBusPath session);

    DBusPath ReadAlias(String name);

    void SetAlias(String name, DBusPath collection);


    interface PropertyCollectionsType extends TypeRef<List<DBusPath>> {


    }

    class CollectionCreated extends DBusSignal {

        private final DBusPath collection;

        public CollectionCreated(String _path, DBusPath _collection) throws DBusException {
            super(_path, _collection);
            this.collection = _collection;
        }


        public DBusPath getCollection() {
            return collection;
        }


    }

    class CollectionDeleted extends DBusSignal {

        private final DBusPath collection;

        public CollectionDeleted(String _path, DBusPath _collection) throws DBusException {
            super(_path, _collection);
            this.collection = _collection;
        }


        public DBusPath getCollection() {
            return collection;
        }


    }

    class CollectionChanged extends DBusSignal {

        private final DBusPath collection;

        public CollectionChanged(String _path, DBusPath _collection) throws DBusException {
            super(_path, _collection);
            this.collection = _collection;
        }


        public DBusPath getCollection() {
            return collection;
        }


    }
}