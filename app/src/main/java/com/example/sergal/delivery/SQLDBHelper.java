package com.example.sergal.delivery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDBHelper extends SQLiteOpenHelper {
    private static final String SCRIPT_DROP_DATABASE =
            "DROP TABLE /*IF EXISTS*/ streetcateg;\n" +
            "DROP TABLE /*IF EXISTS*/ clients;\n" +
            "DROP TABLE /*IF EXISTS*/ phones;\n" +
            "DROP TABLE /*IF EXISTS*/ items;\n" +
            "DROP TABLE /*IF EXISTS*/ orders;\n" +
            "DROP TABLE /*IF EXISTS*/ sellhistory;\n";
    private static final String SCRIPT_CREATE_DATABASE =
            "CREATE TABLE " +
            "streetcateg( " +
            "streetcategid INTEGER, " +
            "streetcateg TEXT, " +
            "PRIMARY KEY (streetcategid) " +
            ");\n" +
            "CREATE TABLE " +
            "clients( " +
            "clientid INTEGER, " +
            "clientname TEXT, " +
            "streetcategid INTEGER, " +
            "streetname TEXT, " +
            "house TEXT, " +
            "office INTEGER, " +
            "inserttimestamp TIMESTAMP, " +
            "updatetimestamp TIMESTAMP, " +
            "PRIMARY KEY (clientid), " +
            "FOREIGN KEY (streetcategid) REFERENCES streetcateg(streetcategid) " +
            ");\n" +
            "CREATE TABLE " +
            "phones( " +
            "phonenumb TEXT, " +
            "clientid INTEGER, " +
            "inserttimestamp TIMESTAMP, " +
            "updatetimestamp TIMESTAMP, " +
            "PRIMARY KEY (phonenumb), " +
            "FOREIGN KEY (clientid) REFERENCES clients(clientid) " +
            ");\n" +
            "CREATE TABLE " +
            "items( " +
            "itemid INTEGER, " +
            "itemname TEXT, " +
            "itemquantityname TEXT, " +
            "PRIMARY KEY (itemid) " +
            ");\n" +
            "CREATE TABLE\n" +
            "orders(\n" +
            "orderid INTEGER,\n" +
            "clientid INTEGER,\n" +
            "inserttimestamp TIMESTAMP,\n" +
            "fulfiltimestamp TIMESTAMP, --на коли привезти замовлення\n" +
            "updatetimestamp TIMESTAMP,\n" +
            "closetimestamp TIMESTAMP,\n" +
            "PRIMARY KEY (orderid)\n" +
            "FOREIGN KEY (clientid) REFERENCES clients(clientid)\n" +
            ");\n" +
            "CREATE TABLE --IF NOT EXISTS\n" +
            "sellhistory(\n" +
            "sellid INTEGER,\n" +
            "orderid INTEGER,\n" +
            "itemid INTEGER,\n" +
            "quantity DOUBLE,\n" +
            "price DOUBLE,\n" +
            "PRIMARY KEY (sellid),\n" +
            "FOREIGN KEY(orderid) REFERENCES orders(orderid),\n" +
            "FOREIGN KEY(itemid) REFERENCES items(itemid)\n" +
            ");\n" +
            "INSERT INTO streetcateg (streetcategid, streetcateg) VALUES (1, \"вул.\");\n" +
            "INSERT INTO streetcateg (streetcategid, streetcateg) VALUES (2, \"просп.\");\n" +
            "INSERT INTO streetcateg (streetcategid, streetcateg) VALUES (3, \"пл.\");\n" +
            "INSERT INTO streetcateg (streetcategid, streetcateg) VALUES (4, \"бульв.\");\n" +
            "INSERT INTO streetcateg (streetcategid, streetcateg) VALUES (5, \"перев.\");\n" +
            "INSERT INTO streetcateg (streetcategid, streetcateg) VALUES (6, \"набер.\");\n" +
            "INSERT INTO clients (clientid, clientname, streetcategid, streetname)\n" +
            "VALUES (1,\"ПП Іванов\", 1, \"Шевченка\");\n" +
            "INSERT INTO clients (clientid, clientname, streetcategid, streetname)\n" +
            "VALUES (2,\"ПП Петров\", 3, \"Галицька\");\n" +
            "INSERT INTO clients (clientid, clientname, streetcategid, streetname)\n" +
            "VALUES (3,\"Сидоров Сидор Сидорович\", 2, \"Оболонський\");\n" +
            "INSERT INTO clients (clientid, clientname, streetcategid, streetname)\n" +
            "VALUES (4,\"С Сидорович\", 2, \"Оболонський\");\n" +

            "INSERT INTO phones (phonenumb,clientid) VALUES (\"+380505152000\",1);\n" +
            "INSERT INTO phones (phonenumb,clientid) VALUES (\"+380975152000\",1);\n" +
            "INSERT INTO phones (phonenumb,clientid) VALUES (\"+380322343300\",2);\n";

    public SQLDBHelper(Context context, String name, CursorFactory factory,
                       int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SCRIPT_CREATE_DATABASE);
        MultiExecSQL(db, SCRIPT_CREATE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(SCRIPT_DROP_DATABASE);
        MultiExecSQL(db,SCRIPT_DROP_DATABASE);
        onCreate(db);
    }

    public void MultiExecSQL(SQLiteDatabase db, String multilinescript) {
        String separated[] = multilinescript.split(";\n");
        try {
            for (String query: separated) {
                db.execSQL(query);
            }
        } catch (Exception e) {

        }
    }

}