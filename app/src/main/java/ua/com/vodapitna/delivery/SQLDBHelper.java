package ua.com.vodapitna.delivery;

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
            "phone TEXT, " +
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
            "fulfiltimestamp TIMESTAMP,\n" +
            "updatetimestamp TIMESTAMP,\n" +
            "closetimestamp TIMESTAMP,\n" +
            "PRIMARY KEY (orderid)\n" +
            "FOREIGN KEY (clientid) REFERENCES clients(clientid)\n" +
            ");\n" +
            "CREATE TABLE\n" +
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
            "INSERT INTO streetcateg (streetcategid, streetcateg) VALUES (6, \"набер.\");\n";

    private static SQLDBHelper sInstance = null;
    public static final String DATABASE_NAME = "DELIVERY_DATABASE";
    public static final int DATABASE_VERSION = 1;


    public static synchronized SQLDBHelper getInstance(Context context) {
    // Use the application context, which will ensure that you 
    // don't accidentally leak an Activity's context.
    // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
          sInstance = new SQLDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private SQLDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SCRIPT_CREATE_DATABASE);
        MultiExecSQL(db, SCRIPT_CREATE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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