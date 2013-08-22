package dao;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

public class DAOGeneric<E> implements DAO<E>{
	@PersistenceContext
	protected EntityManager entityManager;

	protected EntityManagerFactory emf;
	private Class<E> entityClass = null;
	
//	private Class<E> persistentClass = (Class<E>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	private Class<E> typeOfT;
	  private  Type type;
	  
	public DAOGeneric() {
//		System.out.println(emf.getPersistenceUnitUtil().getIdentifier(this));
		System.out.println(this.getClass().getGenericSuperclass().toString());
		System.out.println(this.getClass().getSuperclass().toString());
		System.out.println(Arrays.toString(this.getClass().getGenericInterfaces()));
//		System.out.println(this.getClass().getGenericInterfaces()[0].getClass().getSimpleName());
		Class<E> clazz = (Class<E>) getClass();
		System.out.println(clazz.getGenericSuperclass());
//		System.out.println(((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
//		System.out.println(persistentClass.toString());
//		this.typeOfT = (Class<E>)
//                ((ParameterizedType)getClass()
//                .getGenericSuperclass())
//                .getActualTypeArguments()[0];

//		System.out.println( ((ParameterizedType) getClass().getGenericInterfaces()[0].getClass().getGenericSuperclass()));
//		System.out.println(getClass(getClass()).);
//		final ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();  
//		Class<E> theType = (Class<E>) (type).getActualTypeArguments()[0];  
	
		 Type superclass = getClass().getGenericSuperclass();
//	        if (superclass instanceof Class) {
//	            throw new RuntimeException("Missing type parameter.");
//	        }
//	        this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
	        System.out.println(Arrays.toString(getClass().getClasses()) + "");
//	        Class<E> dataClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	        
	        System.out.println(((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0]);
	        Type teste = ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
	        System.out.println(teste.toString());
	        
	        
			ParameterizedType paramType = (ParameterizedType) this.getClass().getGenericInterfaces()[0];
//			entityClass = (Class<E>) paramType.getActualTypeArguments()[0];
			System.out.println(paramType.getActualTypeArguments()[0].getClass());

	}
	
	static void inspect(Object o) {
	    Type type = o.getClass();
	    while (type != null) {
	        System.out.print(type + " implements");
	        Class<?> rawType =
	                (type instanceof ParameterizedType)
	                ? (Class<?>)((ParameterizedType)type).getRawType()
	                : (Class<?>)type;
	        Type[] interfaceTypes = rawType.getGenericInterfaces();
	        if (interfaceTypes.length > 0) {
	            System.out.println(":");
	            for (Type interfaceType : interfaceTypes) {
	                if (interfaceType instanceof ParameterizedType) {
	                    ParameterizedType parameterizedType = (ParameterizedType)interfaceType;
	                    System.out.print("  " + parameterizedType.getRawType() + " with type args: ");
	                    Type[] actualTypeArgs = parameterizedType.getActualTypeArguments();
	                    System.out.println(Arrays.toString(actualTypeArgs));
	                }
	                else {
	                    System.out.println("  " + interfaceType);
	                }
	            }
	        }
	        else {
	            System.out.println(" nothing");
	        }
	        type = rawType.getGenericSuperclass();
	    }
	}		
	public static Class<?> getClass(Type type) {
	    if (type instanceof Class) {
	      return (Class) type;
	    }
	    else if (type instanceof ParameterizedType) {
	      return getClass(((ParameterizedType) type).getRawType());
	    }
	    else if (type instanceof GenericArrayType) {
	      Type componentType = ((GenericArrayType) type).getGenericComponentType();
	      Class<?> componentClass = getClass(componentType);
	      if (componentClass != null ) {
	        return Array.newInstance(componentClass, 0).getClass();
	      }
	      else {
	        return null;
	      }
	    }
	    else {
	      return null;
	    }
	  }
//    public Class returnedClass() {
//        return E.class; //not remotely legal in Java 5 or 6.
//      }
}
