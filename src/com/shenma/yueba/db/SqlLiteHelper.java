package com.shenma.yueba.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shenma.yueba.R;
import com.shenma.yueba.constants.Constants;
/**
 * 数据库创建的类
 * @author Administrator
 *
 */
public class SqlLiteHelper extends SQLiteOpenHelper {
	private final DBHelper dbHelper;
	private static final int DB_VERSION = 1;

	public SqlLiteHelper(DBHelper dbHelper, Context context, String userid) {
		super(context, Constants.PROJECT_NAME + userid, null, DB_VERSION);
		this.dbHelper = dbHelper;
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		return super.getReadableDatabase();
	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		return super.getWritableDatabase();
	}

	@Override
	public synchronized void close() {
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/** Contancts表 */
		String sqlContactsUSer = "CREATE TABLE  "
				+ Constants.CONTACT
				+ " ( "
				+ "userId"
				+ " nvarchar(50) PRIMARY KEY, "
				+"username nvarchar(100))";
   
		db.execSQL(sqlContactsUSer);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Constants.CONTACT);
        onCreate(db);
	}
}