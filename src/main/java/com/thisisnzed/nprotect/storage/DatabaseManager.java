package com.thisisnzed.nprotect.storage;

import com.nz1337.easysql.EasySQL;
import com.nz1337.easysql.manager.Column;
import com.nz1337.easysql.manager.Table;
import com.thisisnzed.nprotect.Protect;
import com.thisisnzed.nprotect.config.impl.Settings;

public class DatabaseManager {

    private final Settings settings;
    private EasySQL easySQL;
    private Column column;

    public DatabaseManager(final Protect protect) {
        this.settings = protect.getSettings();
    }

    public void initialize() {
        final EasySQL easySQL = new EasySQL().setDatabase(this.settings.getDatabase()).setHost(this.settings.getHost()).setPassword(this.settings.getPassword()).setPort(this.settings.getPort()).setUser(this.settings.getUser());
        final Table table = new Table("players");
        table.addColumn("uuid", "uuid");
        table.addColumn("pin", "string");
        table.setPrimaryKey("uuid");
        easySQL.createDefaultTables(table);
        easySQL.connect();
        this.easySQL = easySQL;
        this.column = table.getColumns();
    }

    public void close() {
        this.easySQL.close();
    }

    public Column getColumn() {
        return this.column;
    }
}
