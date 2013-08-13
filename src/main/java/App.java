import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.mockupcode.example.hibernate.xmlmapping.model.Test;

public class App {
	private static final SessionFactory ourSessionFactory;
	private static final ServiceRegistry serviceRegistry;

	static {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();

			serviceRegistry = new ServiceRegistryBuilder().applySettings(
					configuration.getProperties()).buildServiceRegistry();
			ourSessionFactory = configuration
					.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getSession() throws HibernateException {
		return ourSessionFactory.openSession();
	}

	public static void main(String[] args) {
		Transaction tx = null;
		try {
			tx = getSession().beginTransaction();
			Test test = new Test();
			test.setName("my test");
			getSession().save(test);
			tx.commit();
			System.out.println("get ID from detatch bean : "+test.getId());
		} catch (Exception ex) {
			if(tx!=null)tx.rollback();
		}finally{
			getSession().close();
		}
	}
}
