package com.beastbikes.framework.persistence.android;

import com.beastbikes.framework.persistence.AbstractDataAccessObject;

public abstract class SQLiteAccessObject extends AbstractDataAccessObject {

	public SQLiteAccessObject(SQLitePersistenceManager persistenceManager) {
		super(persistenceManager);
	}

	@Override
	public SQLitePersistenceManager getPersistenceManager() {
		return (SQLitePersistenceManager) super.getPersistenceManager();
	}

}
