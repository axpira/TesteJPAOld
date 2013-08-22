package test;

import java.io.PrintWriter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hsqldb.Server;

import dao.DAO;
import dao.DAOGeneric;
import model.Contact;

public class Teste {
	public static void main(String[] args) {
		DAOGeneric<String> dao = new DAOGeneric<String>();
		if (true) return;
		
		Server hsqlServer = null;
		try {
			hsqlServer = new Server();
//			hsqlServer.setLogWriter(new PrintWriter(System.out));
			hsqlServer.setLogWriter(null);
			hsqlServer.setSilent(true);

			hsqlServer.setDatabaseName(0, "xdb");
			hsqlServer.setDatabasePath(0, "file:testdb");

			hsqlServer.start();

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("TesteHSQLDB");
			EntityManager em = emf.createEntityManager();

			
//			Contact contact = new Contact();
//			contact.setContactId(2);
//			contact.setName("Shirey");
//			em.getTransaction().begin();
//			em.persist(contact);
//			em.getTransaction().commit();
//			em.close();
			
			
			em = emf.createEntityManager();
			System.out.println("The customer names are:");
			final List<Contact> customers = em.createQuery("select c from Contact c").getResultList();
			for (Contact c : customers) {
				System.out.println(c.getContactId() + " : " + c.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (hsqlServer != null) {
				hsqlServer.shutdown();
			}

		}
	}
}
