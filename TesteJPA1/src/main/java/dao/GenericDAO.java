package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.Table;

import model.PersistentEntity;

public abstract class GenericDAO<E extends PersistentEntity<PK>, PK extends Serializable> {

	@PersistenceContext
	protected EntityManager entityManager;

	private Class<E> entityClass = null;

	@SuppressWarnings("unchecked")
	private Class<E> getEntityClass() {
		if (entityClass == null) {
			ParameterizedType paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
			entityClass = (Class<E>) paramType.getActualTypeArguments()[0];
		}
		return entityClass;
	}
	
	public String getTableName() {
		String tableName = null;
		if (getEntityClass().getAnnotation(Table.class) != null) {
			Table table = getEntityClass().getAnnotation(Table.class);
			tableName = table.name();
		} else {
			tableName = getEntityClass().getSimpleName();
		}
		return tableName;
	}

	
	public int getTotal() {
		Query q = entityManager.createQuery("SELECT COUNT(o) FROM " + getTableName() + " o");
		return ((Long)q.getSingleResult()).intValue();
		
	}
	
	public void save(E entity) {
		try {
			if (entity.isNew()) {
				entityManager.persist(entity);
			} else {
				entityManager.merge(entity);
			}
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}

    public E findByPrimaryKey(PK pk) {
        if (pk == null) {
            throw new DAOException("A chave primaria não pode ser nula");
        }
        try {
            return entityManager.find(getEntityClass(), pk);
        } catch (javax.persistence.PersistenceException e) {
            throw new DAOException(e);
        }
    }
    
    public void delete(E entity) {
        try {
            if (entityManager.contains(entity)) {
                entityManager.remove(entity);
            } else if (!entity.isNew()) {
                entityManager.remove(entityManager.merge(entity));
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }
    
    public E findByPk(PK pk) {
    	return entityManager.find(getEntityClass(), pk);
    }
    
    public Collection<E> findAll(){
    	return findAll(0, 100);
    }

    public Collection<E> findAll(int page, int maxResult){
    	return findByQuery("SELECT o FROM " + getTableName() + " o",page,maxResult);
    }
    
    @SuppressWarnings("unchecked")
	protected Collection<E> findByQuery(String query, int firstPage, int maxResults) {
        try {
            Query q = entityManager.createQuery(query);
            q.setMaxResults(maxResults);
            q.setFirstResult(firstPage);
            return q.getResultList();
        } catch (javax.persistence.PersistenceException e) {
            throw new DAOException(e);
        }
    }
    
	@SuppressWarnings("unchecked")
	protected Collection<E> findByQuery(String query, int firstPage, int maxResults, Object... parameters) {
        try {
            Query q = entityManager.createQuery(query);
            q.setMaxResults(maxResults);
            q.setFirstResult(firstPage);
            fillIndexedParameters(q, parameters);
            return q.getResultList();
        } catch (javax.persistence.PersistenceException e) {
            throw new DAOException(e);
        }
    }
    
	@SuppressWarnings("unchecked")
	protected E findSingleByQuery(String query) {
        try {
            Query q = entityManager.createQuery(query);
            return (E) q.getSingleResult();
        } catch (javax.persistence.PersistenceException e) {
            throw new DAOException(e);
        }
    }

	@SuppressWarnings("unchecked")
	protected E findSingleByQuery(String query, Object... parameters) {
        try {
            Query q = entityManager.createQuery(query);
            fillIndexedParameters(q, parameters);
            return (E) q.getSingleResult();
        } catch (javax.persistence.PersistenceException e) {
            throw new DAOException(e);
        }
    }

    
	@SuppressWarnings("unchecked")
	protected Collection<E> findByQuery(String query, int firstPage, int maxResults, Map<String, Object> parameters) {
        try {
            Query q = entityManager.createQuery(query);
            q.setMaxResults(maxResults);
            q.setFirstResult(firstPage);
            fillNamedParameters(q, parameters);
            return q.getResultList();
        } catch (javax.persistence.PersistenceException e) {
            throw new DAOException(e);
        }
    }
    
	@SuppressWarnings("unchecked")
	protected E findSingleByQuery(String query, Map<String, Object> parameters) {
        try {
            Query q = entityManager.createQuery(query);
            fillNamedParameters(q, parameters);
            return (E) q.getSingleResult();
        } catch (javax.persistence.PersistenceException e) {
            throw new DAOException(e);
        }
    }
    
    
    private void fillNamedParameters(Query q, Map<String, Object> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }
    
    private void fillIndexedParameters(Query q, Object... parameters) {
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                q.setParameter(i + 1, parameters[i]);
            }
        }
    }
    
}
