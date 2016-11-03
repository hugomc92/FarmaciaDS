package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Reservation;
import util.HibernateUtil;

public class ReservationDao {
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected Reservation getReservation(String cif, int productId, String email){
		Reservation reservation = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			reservation = (Reservation) session.createQuery("select r from Reservation r where r.reservationPK.cif = :CIF and r.reservationPK.productId = :ID and r.reservationPK.email = :EMAIL")
					.setParameter("CIF", cif)
					.setParameter("ID", productId)
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
		
		return reservation;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Reservation> getAllReservationByCIF(String cif){
		List<Reservation> reservationList = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			reservationList = session.createQuery("select r from Reservation r where r.reservationPK.cif = :CIF")
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
		
		return reservationList;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Reservation> getAllReservationByCIFProductId(String cif, int productId){
		List<Reservation> reservationList = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			reservationList = session.createQuery("select r from Reservation r where r.reservationPK.cif = :CIF and r.reservationPK.productId = :ID")
					.setParameter("CIF", cif)
					.setParameter("ID", productId)
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
		
		return reservationList;
	}
	
	protected boolean deleteReservation(Reservation r){
		Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(r);
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
