package org.utilities.orm;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HibernateRepository<T extends HibernateEntity> extends HibernateDaoSupport {

	private static final Logger LOGGER = Logger.getLogger(HibernateRepository.class);

	private Class<T> entityType;

	public HibernateRepository(SessionFactory sessionFactory, Class<T> entityType) {
		this.entityType = entityType;
		setSessionFactory(sessionFactory);
		LOGGER.info(getClass().getSimpleName() + " initialized");
	}

	public List<T> list(String sql) {
		return this.currentSession()
				.createNativeQuery(sql, entityType)
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> list(Criteria critera) {
		return critera.list();
	}

	public Criteria createCriteria(Criterion... criterion) {
		Criteria criteria = currentSession().createCriteria(entityType);
		for (int i = 0; i < criterion.length; i++) {
			criteria.add(criterion[i]);
		}
		return criteria;
	}

	public List<T> list(Criterion... criterion) {
		Criteria criteria = createCriteria(criterion);
		return list(criteria);
	}

	public List<T> findAll() {
		return list();
	}

	public void save(T entity) {
		currentSession().save(entity);
	}

	public void saveOrUpdate(T entity) {
		currentSession().saveOrUpdate(entity);
	}

	public void saveAll(Collection<T> entities) {
		OrmUtils.saveAll(getSessionFactory(), entities);
	}

}
