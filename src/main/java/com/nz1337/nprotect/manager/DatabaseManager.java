package com.nz1337.nprotect.manager;

import com.nz1337.easysql.EasySQL;
import com.nz1337.easysql.manager.Column;
import com.nz1337.easysql.manager.Table;
import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.configs.Settings;

public class DatabaseManager {

    private final Settings settings;
    private EasySQL easySQL;
    private Column column;

    public DatabaseManager(Protect protect) {
        this.settings = protect.getSettings();
        this.initialize();
    }

    private void initialize() {
        EasySQL easySQL = new EasySQL().setDatabase(this.settings.getDatabase()).setHost(this.settings.getHost()).setPassword(this.settings.getPassword()).setPort(this.settings.getPort()).setUser(this.settings.getUser());
        Table table = new Table("players");
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
