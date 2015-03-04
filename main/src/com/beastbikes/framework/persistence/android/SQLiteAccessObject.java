package com.beastbikes.framework.persistence.android;

import java.io.Serializable;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.beastbikes.framework.persistence.DataAccessObject;
import com.beastbikes.framework.persistence.PersistenceException;
import com.beastbikes.framework.persistence.PersistentObject;

public abstract class SQLiteAccessObject<T extends PersistentObject> extends
		ContentProvider implements DataAccessObject<T> {

	private SQLitePersistenceSupport support;

	public SQLiteAccessObject(SQLitePersistenceSupport support) {
		this.support = support;
	}

	public abstract String getTableName();

	@Override
	public SQLitePersistenceManager getPersistenceManager() {
		return this.support;
	}

	@Override
	public long count() throws PersistenceException {
		final String sql = "SELECT COUNT(*) FROM " + getTableName();

		Cursor c = null;

		try {
			c = this.rawQuery(sql, null);
			if (null != c && c.moveToNext()) {
				return c.getLong(0);
			}

			return 0;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			if (null != c && !c.isClosed()) {
				c.close();
				c = null;
			}
		}
	}

	@Override
	public void delete(T po) throws PersistenceException {
		this.delete(po.getId());
	}

	@Override
	public void delete(Serializable id) throws PersistenceException {
		final String whereClause = BaseColumns._ID + "=?";
		final String[] whereArgs = { String.valueOf(id) };

		if (0 == this.delete(getTableName(), whereClause, whereArgs)) {
			throw new PersistenceException();
		}
	}

	@Override
	public boolean exists(T po) throws PersistenceException {
		return this.exists(po.getId());
	}

	@Override
	public boolean exists(Serializable id) throws PersistenceException {
		final String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE "
				+ BaseColumns._ID + "=?";
		final String[] args = { String.valueOf(id) };

		Cursor c = null;

		try {
			c = this.rawQuery(sql, args);
			if (null != c && c.moveToNext()) {
				return c.getLong(0) > 0;
			}

			return false;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			if (null != c && !c.isClosed()) {
				c.close();
				c = null;
			}
		}
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

	protected Cursor rawQuery(String sql, String[] selectionArgs) {
		final SQLitePersistenceManager pm = getPersistenceManager();
		final SQLiteDatabase db = pm.getReadableDatabase();
		return db.rawQuery(sql, selectionArgs);
	}

}
