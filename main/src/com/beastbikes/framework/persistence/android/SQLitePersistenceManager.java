package com.beastbikes.framework.persistence.android;

import java.util.Comparator;

import android.database.sqlite.SQLiteDatabase;

import com.beastbikes.framework.persistence.PersistenceManager;

/**
 * A sub interface of interface {@link PersistenceManager} for SQLite
 * 
 * @author johnson
 * 
 */
public interface SQLitePersistenceManager extends PersistenceManager,
		Comparator<SQLiteUpgradeHandler> {

	/**
	 * Returns the upgrade handlers
	 * 
	 * @return the upgrade handlers
	 */
	public SQLiteUpgradeHandler[] getUpgradeHandlers();

	/**
	 * Returns a writable {@link SQLiteDatabase}
	 * 
	 * @return a writable {@link SQLiteDatabase}
	 */
	public SQLiteDatabase getReadableDatabase();

	/**
	 * Returns a readable {@link SQLiteDatabase}
	 * 
	 * @return a readable {@link SQLiteDatabase}
	 */
	public abstract SQLiteDatabase getWritableDatabase();

}
