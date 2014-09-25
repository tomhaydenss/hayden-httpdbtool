package hayden.httpdbtool.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JPADAOImpl {

	private String persistenceUnit;

	public JPADAOImpl(String persistenceUnit) {
		super();
		this.persistenceUnit = persistenceUnit;
	}

	@SuppressWarnings("rawtypes")
	public List read(String hql) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery(hql);
			List result = query.getResultList();
			return result;
		} finally {
			em.close();
		}

	}

}
