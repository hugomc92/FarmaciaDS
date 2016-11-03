package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import util.HibernateUtil;

public class SizeTableUtil {
	
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	protected float getSizeinBytes(String tableName){
		float size = 0.0f;
		Session session = null;
				
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			
			String sql = "SELECT table_name AS `Table`, round(((data_length + index_length)), 2) "
					+ "`Bytes` FROM information_schema.TABLES WHERE table_schema = :DB_NAME "
					+ "AND table_name = :TABLE_NAME";
			SQLQuery query = (SQLQuery) session.createSQLQuery(sql)
					.setParameter("DB_NAME", "pharmacys")
					.setParameter("TABLE_NAME", tableName);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<?> data = query.list();			
			for(Object object : data){
				Map<?, ?> row = (Map<?, ?>) object;
	            String result = row.get("Bytes").toString();
	            size = Float.parseFloat(result);
	        }
			
			session.getTransaction().commit();
		}
		catch(HibernateException e){
			if(session != null)
				session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			if(session != null)
				session.close();
		}
		
		return size;
	}
	
	protected float getSizeinKB(String tableName){
		float size = 0.0f;
		Session session = null;
				
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			
			String sql = "SELECT table_name AS `Table`, round(((data_length + index_length) / 1024), 2) "
					+ "`Bytes` FROM information_schema.TABLES WHERE table_schema = :DB_NAME "
					+ "AND table_name = :TABLE_NAME";
			SQLQuery query = (SQLQuery) session.createSQLQuery(sql)
					.setParameter("DB_NAME", "pharmacys")
					.setParameter("TABLE_NAME", tableName);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<?> data = query.list();			
			for(Object object : data){
				Map<?, ?> row = (Map<?, ?>) object;
	            String result = row.get("Bytes").toString();
	            size = Float.parseFloat(result);
	        }
			
			session.getTransaction().commit();
		}
		catch(HibernateException e){
			if(session != null)
				session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			if(session != null)
				session.close();
		}
		
		return size;
	}
	
	protected float getSizeinMB(String tableName){
		float size = 0.0f;
		Session session = null;
				
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			
			String sql = "SELECT table_name AS `Table`, round(((data_length + index_length) / 1024 / 1024), 2) "
					+ "`Bytes` FROM information_schema.TABLES WHERE table_schema = :DB_NAME "
					+ "AND table_name = :TABLE_NAME";
			SQLQuery query = (SQLQuery) session.createSQLQuery(sql)
					.setParameter("DB_NAME", "pharmacys")
					.setParameter("TABLE_NAME", tableName);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<?> data = query.list();			
			for(Object object : data){
				Map<?, ?> row = (Map<?, ?>) object;
	            String result = row.get("Bytes").toString();
	            size = Float.parseFloat(result);
	        }
			
			session.getTransaction().commit();
		}
		catch(HibernateException e){
			if(session != null)
				session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			if(session != null)
				session.close();
		}
		
		return size;
	}
}
