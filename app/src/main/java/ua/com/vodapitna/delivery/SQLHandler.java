package ua.com.vodapitna.delivery;

import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
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

        public long insert(String table, String nullString, ContentValues values) {
		long id = -1;
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();
            }

            sqlDatabase = dbHelper.getWritableDatabase();
            id = sqlDatabase.insert(table, nullString, values);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }
		return id;
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
