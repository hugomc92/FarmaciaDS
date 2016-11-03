package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.UserAbstraction;
import model.UserAdmin;
import model.UserNormal;
import model.UserRefinedAbstraction;
import util.HibernateUtil;

public class UserDao {
	
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	protected UserAbstraction getUserFuncById(String email){
		UserRefinedAbstraction user = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			user = (UserRefinedAbstraction) session
					.createQuery("from UserRefinedAbstraction u where u.email = :EMAIL")
					.setParameter("EMAIL", email)
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
		
		UserAbstraction uAbstraction = null;
		if(user.getActive() == 1) {
			switch(user.getRole()){
				case 1: // is Admin
					uAbstraction = new UserRefinedAbstraction(new UserAdmin());
					break;
					
				case 2: // is Normal user
					uAbstraction = new UserRefinedAbstraction(new UserNormal());
					break;
			}
		}
		
		return uAbstraction;
	}
	
	protected UserRefinedAbstraction getUserByResetHash(String hash){
		UserRefinedAbstraction user = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			user = (UserRefinedAbstraction) session
					.createQuery("from UserRefinedAbstraction u where u.resetHash = :HASH")
					.setParameter("HASH", hash)
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
		
		return user;
	}
	
	protected UserRefinedAbstraction getUserById(String email){
		UserRefinedAbstraction user = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			user = (UserRefinedAbstraction) session
					.createQuery("from UserRefinedAbstraction u where u.email = :EMAIL")
					.setParameter("EMAIL", email)
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
		
		return user;
	}
	
	protected UserRefinedAbstraction getUserByEmailPassword(String email, String password){
		UserRefinedAbstraction user = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			user = (UserRefinedAbstraction) session
					.createQuery("from UserRefinedAbstraction u where u.email = :EMAIL and u.password = :PASSWORD")
					.setParameter("EMAIL", email)
					.setParameter("PASSWORD", password)
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
		
		return user;
	}
	
	@SuppressWarnings("unchecked")
	protected List<UserRefinedAbstraction> getAllUsers(){
		List<UserRefinedAbstraction> users = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			users = session.createQuery("from UserRefinedAbstraction u").list();
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
		
		return users;
	}
	
	protected boolean insertUser(UserRefinedAbstraction user){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(user);
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
	
	protected boolean updateUser(UserRefinedAbstraction user){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(user);
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
