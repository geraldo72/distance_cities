package com.br.distanceCities.db;

import com.br.distanceCities.exception.DatabaseException;

public interface CrudDAO<T> {

	public abstract T insert(T request) throws DatabaseException;

	public abstract void delete(T request) throws DatabaseException;

	public abstract void update(T request) throws DatabaseException;

	public abstract Iterable<T> listAll() throws DatabaseException;

	public abstract Iterable<T> findBy(T request) throws DatabaseException;
	
	public abstract T findFirst(T request) throws DatabaseException;

}