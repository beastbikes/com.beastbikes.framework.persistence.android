package com.beastbikes.framework.persistence.android;

import java.util.Arrays;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * {@link SQLitePersistenceSupport} is an abstraction of interface
 * {@link SQLitePersistenceManager}
 * 
 * @author johnson
 * 
 */
public abstract class SQLitePersistenceSupport extends SQLiteOpenHelper
		implements SQLitePersistenceManager {

	/**
	 * Create an instance of {@link SQLitePersistenceSupport}
	 * 
	 * @param context
	 *            The android application context
	 * @param name
	 *            The SQLite database name
	 * @param factory
	 *            The cursor factory
	 * @param version
	 *            The SQLite database version
	 */
	public SQLitePersistenceSupport(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public int compare(SQLiteUpgradeHandler lhs, SQLiteUpgradeHandler rhs) {
		return lhs.compareTo(rhs);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		final SQLiteUpgradeHandler[] handlers = getUpgradeHandlers();
		if (null == handlers || handlers.length <= 0)
			return;

		Arrays.sort(handlers, this);

		for (int i = 0; i < handlers.length; i++) {
			final SQLiteUpgradeHandler handler = handlers[i];
			final int targetVersion = handler.getTargetVersion();

			if (oldVersion < targetVersion) {
				handler.upgrade(oldVersion, newVersion);
			}
		}
	}

}
