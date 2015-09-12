package com.example.sergal.delivery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDBHelper extends SQLiteOpenHelper {
    private static final String SCRIPT_CREATE_DATABASE =
        "CREATE TABLE IF NOT EXISTS\n" +
        "streetcateg(\n" +
        "streetcategid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
        "streetcateg TEXT\t-- (вул, пл, б-р, пр-т тощо)\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS\n" +
        "clients(\n" +
        "clientid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
        "clientname TEXT,\n" +
        "FOREIGN KEY(streetcategid) REFERENCES streetcateg(streetcategid),\n" +
        "streetname TEXT,\n" +
        "house TEXT,\n" +
        "office INTEGER,\n" +
        "inserttimestamp TIMESTAMP,\n" +
        "updatetimestamp TIMESTAMP\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS\n" +
        "telephones(\n" +
        "telephoneid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
        "FOREIGN KEY(clientid) REFERENCES clients(clientid),\n" +
        "telephone TEXT\n" +
        "inserttimestamp TIMESTAMP,\n" +
        "updatetimestamp TIMESTAMP\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS\n" +
        "items(\n" +
        "itemid INTEGER,\n" +
        "itemname TEXT, --(вода, фільтри тощо)\n" +
        "itemquantityname TEXT --(л, бут, шт, кг тощо)\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS\n" +
        "orders(\n" +
        "orderid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
        "FOREIGN KEY(clientid) REFERENCES clients(clientid),\n" +
        "inserttimestamp TIMESTAMP,\n" +
        "fulfiltimestamp TIMESTAMP, --на коли привезти замовлення\n" +
        "updatetimestamp TIMESTAMP,\n" +
        "closetimestamp TIMESTAMP,\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS\n" +
        "sellhistory(\n" +
        "sellid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
        "FOREIGN KEY(orderid) REFERENCES orders(orderid),\n" +
        "FOREIGN KEY(itemid) REFERENCES items(itemid),\n" +
        "quantity DOUBLE,\n" +
        "price DOUBLE\n" +
        ");\n";
    private static final String SCRIPT_DROP_DATABASE =
        "DROP TABLE IF EXISTS streetcateg;\n"+
        "DROP TABLE IF EXISTS clients;\n"+
        "DROP TABLE IF EXISTS telephones;\n"+
        "DROP TABLE IF EXISTS items;\n"+
        "DROP TABLE IF EXISTS orders;\n"+
        "DROP TABLE IF EXISTS sellhistory;";

    public SQLDBHelper(Context context, String name, CursorFactory factory,
                       int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_CREATE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SCRIPT_DROP_DATABASE);
        onCreate(db);
    }

}