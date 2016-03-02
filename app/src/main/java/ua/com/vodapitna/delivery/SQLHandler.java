package ua.com.vodapitna.delivery;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLHandler {

    //private static DatabaseHelper mInstance = null;
    Context context;
    SQLiteDatabase sqlDatabase;
    SQLDBHelper dbHelper;

    public SQLHandler(Context context) {

        dbHelper = SQLDBHelper.getInstance(context);
        sqlDatabase = dbHelper.getWritableDatabase();
    }

    public void executeQuery(String query) {
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();
            }

            sqlDatabase = dbHelper.getWritableDatabase();
            sqlDatabase.execSQL(query);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }

    }

    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();

            }
            sqlDatabase = dbHelper.getWritableDatabase();
            c1 = sqlDatabase.rawQuery(query, null);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);

        }
        return c1;

    }

    public void exportDataBase() {
	dbHelper.exportDB();
    }

}
