package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Order;
import util.HibernateUtil;

public class OrderDao {
	
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	@SuppressWarnings("unchecked")
	protected List<Order> getAllOrdersByCIF(String cif){
		List<Order> orderList = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			orderList = session.createQuery("from Order o where o.cif = :CIF")
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
		
		return orderList;
	}
}
