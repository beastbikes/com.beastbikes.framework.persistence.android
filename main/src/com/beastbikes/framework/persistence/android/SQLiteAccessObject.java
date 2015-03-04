package com.beastbikes.framework.persistence.android;

import java.sql.ResultSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.beastbikes.framework.persistence.DataAccessObject;
import com.beastbikes.framework.persistence.PersistenceException;
import com.beastbikes.framework.persistence.PersistentObject;

public abstract class SQLiteAccessObject<T extends PersistentObject> extends
		ContentProvider implements DataAccessObject<T> {

	private SQLitePersistenceManager persistenceManager;
	
	public SQLiteAccessObject(SQLitePersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}

	@Override
	public SQLitePersistenceManager getPersistenceManager() {
		return this.persistenceManager;
	}

	@Override
	public void execute(String sql, Object... args) throws PersistenceException {
		final SQLitePersistenceManager pm = getPersistenceManager();
		final SQLiteDatabase db = pm.getWritableDatabase();

		try {
			db.execSQL(sql, args);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public ResultSet query(String sql, String... args)
			throws PersistenceException {
		// TODO
		return null;
	}

	protected Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		final SQLitePersistenceManager pm = getPersistenceManager();
		final SQLiteDatabase db = pm.getReadableDatabase();
		return db.query(table, columns, selection, selectionArgs, groupBy,
				having, orderBy);
	}

	protected Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		final SQLitePersistenceManager pm = getPersistenceManager();
		final SQLiteDatabase db = pm.getReadableDatabase();
		return db.query(table, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);
	}

	protected long insert(String table, String nullColumnHack,
			ContentValues values) {
		final SQLitePersistenceManager pm = getPersistenceManager();
		final SQLiteDatabase db = pm.getWritableDatabase();
		return db.insert(table, nullColumnHack, values);
	}

	protected long insertOrThrow(String table, String nullColumnHack,
			ContentValues values) throws SQLException {
		final SQLitePersistenceManager pm = getPersistenceManager();
		final SQLiteDatabase db = pm.getWritableDatabase();
		return db.insertOrThrow(table, nullColumnHack, values);
	}

	protected long insertOrThrow(String table, String nullColumnHack,
			ContentValues values, int conflictAlgorithm) {
		final SQLitePersistenceManager pm = getPersistenceManager();
		final SQLiteDatabase db = pm.getWritableDatabase();
		return db.insertWithOnConflict(table, nullColumnHack, values,
				conflictAlgorithm);
	}

	protected int update(String table, ContentValues values,
			String whereClause, String[] whereArgs) {
		final SQLitePersistenceManager pm = getPersistenceManager();
		final SQLiteDatabase db = pm.getWritableDatabase();
		return db.update(table, values, whereClause, whereArgs);
	}

	protected int updateWithOnConflict(String table, ContentValues values,
			String whereClause, String[] whereArgs, int conflictAlgorithm) {
		final SQLitePersistenceManager pm = getPersistenceManager();
		final SQLiteDatabase db = pm.getWritableDatabase();
		return db.updateWithOnConflict(table, values, whereClause, whereArgs,
				conflictAlgorithm);
	}

	protected int delete(String table, String whereClause, String[] whereArgs) {
		final SQLitePersistenceManager pm = getPersistenceManager();
		final SQLiteDatabase db = pm.getWritableDatabase();
		return db.delete(table, whereClause, whereArgs);
	}

}
