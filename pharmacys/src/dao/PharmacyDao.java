package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Pharmacy;
import util.HibernateUtil;

public class PharmacyDao {
	
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected Pharmacy getPharmacyByCIF(String cif){
		Pharmacy pharmacy = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			pharmacy = (Pharmacy) session
					.createQuery("from Pharmacy p where p.cif = :CIF")
					.setParameter("CIF", cif)
					.uniqueResult();
			session.getTransaction().commit();
		}
		catch(Exception e){
			if(session != null)
				session.getTransaction().rollback();
		}
		finally {
			if(session != null)
				session.close();
		}
		
		return pharmacy;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Pharmacy> getAllPharmacies(){
		List<Pharmacy> pharmacies = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			pharmacies = session.createQuery("from Pharmacy p").list();
			session.getTransaction().commit();
		}
		catch(Exception e){
			if(session != null)
				session.getTransaction().rollback();
		}
		finally {
			if(session != null)
				session.close();
		}
		
		return pharmacies;
	}
	
	protected boolean insertPharmacy(Pharmacy p){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(p);
			session.getTransaction().commit();
		}
		catch (Exception e){
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
	
	protected boolean updatePharmacy(Pharmacy p){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(p);
			session.getTransaction().commit();
		}
		catch (Exception e){
			if(session != null)
				session.getTransaction().rollback();
			
			hasErrors = true;
		}
		finally {
			if(session != null)
				session.close();
		}
		
		return hasErrors;
	}
	
	protected boolean deletePharmacy(Pharmacy p){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(p);
			session.getTransaction().commit();
		}
		catch (Exception e){
			if(session != null)
				session.getTransaction().rollback();
			
			hasErrors = true;
		}
		finally {
			if(session != null)
				session.close();
		}
		
		return hasErrors;
		
	}
}
