package hu.kniznertamas.adminsystem.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;

class DefaultDao<T extends PersistentEntity> implements GenericDao<T> {

    private final Class<T> CLASS;
    private final EntityManagerFactory EMF;

    @SuppressWarnings("FieldCanBeLocal")
    private final boolean ENV_DEV = true;

    DefaultDao(Class<T> CLASS) {
        this.CLASS = CLASS;
        //noinspection ConstantConditions
        this.EMF = ENV_DEV ? Persistence.createEntityManagerFactory("remotePersistenceUnit_DEV") : Persistence.createEntityManagerFactory("remotePersistenceUnit");
    }

    @Override
    public void create(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(T entity) {
        EntityManager entityManager = getEntityManager();
        entity = entityManager.merge(entity);
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<T> findAll() {
        return findEntities(true, -1, -1);
    }

    @Override
    public T findById(Integer id) {
        return getEntityManager().find(CLASS, id);
    }

    private EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List<T> findEntities(@SuppressWarnings("SameParameterValue") boolean all, int firstResult, int maxResult) {
        EntityManager entityManager = getEntityManager();
        CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(CLASS));
        Query query = entityManager.createQuery(criteriaQuery);
        if (!all) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResult);
        }
        return query.getResultList();
    }
}
