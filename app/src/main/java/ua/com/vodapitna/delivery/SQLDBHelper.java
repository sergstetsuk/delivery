package ua.com.vodapitna.delivery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import android.util.Log;

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
	+");\n"
	+"CREATE TABLE fuel("
		+"id INTEGER, "
		+"car TEXT, "
		+"driver TEXT, "
		+"starttimestamp TIMESTAMP, "
		+"odometerstart INTEGER, "
		+"finishtimestamp TIMESTAMP, "
		+"odometerend INTEGER, "
		+"fuelstart DECIMAL,"
		+"fueladd DECIMAL,"
		+"price DECIMAL,"
		+"changed TIMESTAMP, "
		+"PRIMARY KEY (id)"
	+");\n"
	+"CREATE TABLE login("
		+"id INTEGER, "
		+"login TEXT, "
		+"digest TEXT, "
		+"access TEXT, "
		+"phone TEXT, "
		+"contact TEXT, "
		+"changed TIMESTAMP, "
		+"PRIMARY KEY (id)"
	+");";

	private static SQLDBHelper sInstance = null;
	public static final String DATABASE_NAME = "DELIVERY_DATABASE";
	public static final int DATABASE_VERSION = 1;
	private static Context scontext = null;


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
		scontext = context;
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
/**
* Copies your database from your local assets-folder to the just created
* empty database in the system folder, from where it can be accessed and
* handled. This is done by transfering bytestream.
* */
	public void exportDB() /*throws IOException*/ {
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();
			//~ Log.d("vodapitna.export","exportDB " + Environment.getExternalStorageDirectory().getAbsolutePath());
			//~ Log.d("vodapitna.export","exportDB1 " + Environment.getDataDirectory().getAbsolutePath());

			if (sd.canWrite()) {
				//~ Log.d("vodapitna.currentDBPath",scontext.getDatabasePath(DATABASE_NAME).getAbsolutePath());
				String backupDBPath = "/vodapitna.sqlite";
				//~ Log.d("vodapitna.backupDBPath",backupDBPath);
				File currentDB = scontext.getDatabasePath(DATABASE_NAME);
				File backupDB = new File(sd, backupDBPath);

				if (currentDB.exists()) {
					FileChannel src = new FileInputStream(currentDB).getChannel();
					FileChannel dst = new FileOutputStream(backupDB).getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				}
			}
		} catch (Exception e) {
			Log.d("vodapitna:exception","IO error");
		}
	}

}
