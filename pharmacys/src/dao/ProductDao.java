package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Product;
import util.HibernateUtil;

public class ProductDao {
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	protected Product getLastInserted(){
		Product product = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			product = (Product) session
					.createQuery("from Product p order by p.id desc")
					.setMaxResults(1).list().get(0);
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
		
		return product;
	}
	
	protected Product getProductById(int id){
		Product product = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			product = (Product) session
					.createQuery("from Product p where p.id = :ID")
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
		
		return product;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Product> getAllProducts(){
		List<Product> products = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			products = session.createQuery("from Product p").list();
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
		
		return products;
	}
	
	protected boolean insertProduct(Product p){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(p);
			session.getTransaction().commit();
		}
		catch(HibernateException e){
			if(session != null)
				session.getTransaction().rollback();
			
			e.printStackTrace();
			hasErrors = true;
		}
		finally {
			if(session != null)
				session.close();
		}
		
		return hasErrors;
	}
	
	protected boolean updateProduct(Product p){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(p);
			session.getTransaction().commit();
		}
		catch (HibernateException e){
			if(session != null)
				session.getTransaction().rollback();
			
			e.printStackTrace();
			hasErrors = true;
		}
		finally {
			if(session != null)
				session.close();
		}
		
		return hasErrors;
	}
	
	protected boolean deleteProduct(Product p){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(p);
			session.getTransaction().commit();
		}
		catch (HibernateException e){
			if(session != null)
				session.getTransaction().rollback();
			
			e.printStackTrace();
			hasErrors = true;
		}
		finally {
			if(session != null)
				session.close();
		}
		
		return hasErrors;
		
	}
}
