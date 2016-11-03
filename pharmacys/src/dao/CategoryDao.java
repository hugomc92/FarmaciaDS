package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Category;
import util.HibernateUtil;

public class CategoryDao {
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	@SuppressWarnings("unchecked")
	protected List<Category> getAllCategories(){
		List<Category> categories = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			categories = session.createQuery("from Category c").list();
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
		
		return categories;
	}
	
	protected Category getCategoryById(int id){
		Category category = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			category = (Category) session
					.createQuery("from Category c where c.id = :ID")
					.setParameter("ID", id)
					.uniqueResult();
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
		
		return category;
	}
}
