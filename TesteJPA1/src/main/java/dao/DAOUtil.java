package dao;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Table;

public class DAOUtil<Entiy> {
	private DAOUtil() {}
	
//	EntityManagerFactory
	static EntityManagerFactory e;

	/*
	public static Class getEntityClass(Class entityClass) {
		if (entityClass == null) {
			ParameterizedType paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
			entityClass = paramType.getActualTypeArguments()[0];
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
	
	*/
	public static void fillNamedParameters(Query q, Map<String, Object> parameters) {
//        e.getPersistenceUnitUtil().
    	if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public static void fillIndexedParameters(Query q, Object... parameters) {
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                q.setParameter(i + 1, parameters[i]);
            }
        }
    }

}
