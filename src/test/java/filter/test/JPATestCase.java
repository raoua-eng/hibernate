package filter.test;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import filterBug.AccountPO;

public class JPATestCase {
	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() throws IOException {
		entityManagerFactory = Persistence.createEntityManagerFactory("testCasePU");
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Test when we specify the table name in the @SqlFragmentAlias
	@Test
	public void whenfilterWithTableNameQueryIsInvalid() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Session session = (Session) entityManager.unwrap(Session.class);
		try {
			entityManager.getTransaction().begin();
			// Do stuff...
			AccountPO account = new AccountPO(1L, 147.89, 5.1, true, false);
			AccountPO account1 = new AccountPO(2L, 100.89, 8.1, false, true);

			entityManager.persist(account);
			entityManager.persist(account1);

			Filter filter = session.enableFilter("activeAccountV1");
			filter.setParameter("active", true);

			List<AccountPO> accounts = entityManager.createQuery("from Account", AccountPO.class).getResultList();
			accounts.forEach(a -> System.out.println(a.toString()));

			entityManager.getTransaction().commit();

		} catch (RuntimeException e) {
			if (session.getTransaction() != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			throw e;
		}
	}
	
	// Test when we specify the entity name in the @SqlFragmentAlias
		@Test
		public void whenfilterWithEntityNameQueryIsInvalid() throws Exception {
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			Session session = (Session) entityManager.unwrap(Session.class);
			try {
				entityManager.getTransaction().begin();
				// Do stuff...
				AccountPO account = new AccountPO(3L, 951.85, 9.1, true, false);
				AccountPO account1 = new AccountPO(4L, 753.74, 8.1, false, true);

				entityManager.persist(account);
				entityManager.persist(account1);

				Filter filter = session.enableFilter("activeAccountV2");
				filter.setParameter("active", true);

				List<AccountPO> accounts = entityManager.createQuery("from Account", AccountPO.class).getResultList();
				accounts.forEach(a -> System.out.println(a.toString()));

				entityManager.getTransaction().commit();

			} catch (RuntimeException e) {
				if (session.getTransaction() != null && session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}
				throw e;
			}
		}
}
