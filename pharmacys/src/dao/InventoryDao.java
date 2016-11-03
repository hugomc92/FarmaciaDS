package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Inventory;
import util.HibernateUtil;

public class InventoryDao {
	
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@SuppressWarnings("unchecked")
	protected List<Integer> getAllProductsIdsByCif(String cif){
		List<Integer> productIds = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			productIds = session.createQuery("select i.pharmacyProduct.productId from Inventory i where i.pharmacyProduct.pharmacyId = :CIF")
					.setParameter("CIF", cif)
					.list();
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
		
		return productIds;
	}
		
	protected Inventory getInventoryById(String cif, Integer productId){
		Inventory inventory = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			inventory = (Inventory) session.createQuery("from Inventory i where i.pharmacyProduct.pharmacyId = :CIF and i.pharmacyProduct.productId = :PRODUCTID")
					.setParameter("CIF", cif)
					.setParameter("PRODUCTID", productId)
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
		
		return inventory;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Inventory> getTopProducts(int n, String cif){
		List<Inventory> productList = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			productList = session.createQuery("from Inventory i where i.pharmacyProduct.pharmacyId = :CIF order by i.queryCount desc")
					.setParameter("CIF", cif)
					.setMaxResults(n)
					.list();
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
		
		return productList;
	}
	
	protected boolean insertInventory(Inventory i){
		Session session = null;
		boolean hasErrors = false;				
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(i);
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
	
	protected boolean updateInventory(Inventory i){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(i);
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
	
	protected boolean deleteInventory(Inventory i){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(i);
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
