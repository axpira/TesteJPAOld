package model;

import java.io.Serializable;

public interface PersistentEntity<T extends Serializable> extends Serializable{
	
	public T getId();
	
	public boolean isNew();
}
