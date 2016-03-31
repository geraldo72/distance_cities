package com.br.distanceCities.db;

import java.util.List;

import com.br.distanceCities.exception.DatabaseException;

public interface CrudDAO<T> {

	public abstract T insert(T request) throws DatabaseException;

	public abstract void delete(T request) throws DatabaseException;

	public abstract void update(T request) throws DatabaseException;

	public abstract List<T> listAll() throws DatabaseException;

	public abstract List<T> findBy(T request) throws DatabaseException;
	
	public abstract T findFirst(T request) throws DatabaseException;

}