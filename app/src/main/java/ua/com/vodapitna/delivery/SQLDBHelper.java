package ua.com.vodapitna.delivery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDBHelper extends SQLiteOpenHelper {
private static final String SCRIPT_DROP_DATABASE = ""
	+"DROP TABLE /*IF EXISTS*/ clients;\n"
	+"DROP TABLE /*IF EXISTS*/ orders;";
private static final String SCRIPT_CREATE_DATABASE = ""
	+"CREATE TABLE clients("
		+"id INTEGER, "
		+"name TEXT, "
		+"cat TEXT, "
		+"addr TEXT, "
		+"house TEXT, "
		+"office INTEGER,"
		+"floor INTEGER,"
		+"quantity INTEGER,"
		+"price DECIMAL,"
		+"bottletype TEXT,"
		+"cooler TEXT,"
		+"phone TEXT, "
		+"contact TEXT,"
		+"phone1 TEXT,"
		+"contact1 TEXT,"
		+"comment TEXT,"
		+"firstorderdate DATE,"
		+"lastorderdate DATE,"
		+"type TEXT,"
		+"customid INTEGER,"
		+"changed TIMESTAMP, "
		+"PRIMARY KEY (id) "
	+");\n"
	+"CREATE TABLE orders("
		+"id INTEGER, "
		+"name TEXT, "
		+"cat TEXT, "
		+"addr TEXT, "
		+"house TEXT, "
		+"office INTEGER,"
		+"floor INTEGER,"
		+"quantity INTEGER,"
		+"price DECIMAL,"
		+"bottletype TEXT,"
		+"cooler TEXT,"
		+"phone TEXT, "
		+"contact TEXT,"
		+"phone1 TEXT,"
		+"contact1 TEXT,"
		+"comment TEXT,"
		+"ordertimestamp TIMESTAMP,"
		+"result TEXT,"
		+"completetimestamp TIMESTAMP,"
		+"type TEXT,"
		+"customid INTEGER,"
		+"changed TIMESTAMP, "
		+"PRIMARY KEY (id)"
	+");";

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
			System.out.println("DATABASE ERROR " + e);
		}
	}
}