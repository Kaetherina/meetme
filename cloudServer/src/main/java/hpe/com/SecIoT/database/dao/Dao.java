package hpe.com.SecIoT.database.dao;

import java.util.Collection;

/**
 *
 */
public interface Dao<ID, TYPE> {

  void persist(TYPE entity);

  void delete(ID id);

  TYPE get(ID id);

  Collection<TYPE> list();
}
