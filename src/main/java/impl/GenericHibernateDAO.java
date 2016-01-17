package impl;

import inf.GenericDAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.proxy.HibernateProxy;


public abstract class GenericHibernateDAO<T extends Serializable, ID extends Serializable> implements GenericDAO<T, ID> {

    private Class<T> m_persistentClass;

    private SessionFactory m_sessionFactory;
    
    @SuppressWarnings("unchecked")
    public GenericHibernateDAO(SessionFactory sessionFactory) {
        m_sessionFactory = sessionFactory;
        Class klass = getClass();
        while (klass != null && ! (klass.getGenericSuperclass() instanceof ParameterizedType)) {
            klass = klass.getSuperclass();
        }
        m_persistentClass = (Class<T>) ((ParameterizedType) klass.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Session getSession() {
        return m_sessionFactory.getCurrentSession();
    }
    
    protected StatelessSession getStatelessSession() {
        return m_sessionFactory.openStatelessSession();
    }

    public Class<T> getPersistentClass() {
        return m_persistentClass;
    }
    
    public void refresh(Object value, LockMode mode) {
        getSession().refresh(value, mode);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T findByAttribute(String attribute, Object value) {
        return (T) createCriteria().add(Restrictions.eq(attribute, value)).uniqueResult();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T findByAttribute(String attribute, Object value, LockMode mode) {
        return (T) createCriteria().add(Restrictions.eq(attribute, value)).setLockMode(mode).uniqueResult();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAllByAttribute(String attribute, Object value) {
        return (List<T>)createCriteria()
                .add(Restrictions.eq(attribute, value))
                .list();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAllByAttribute(String attribute, Object value, LockMode mode) {
        return (List<T>)createCriteria().add(Restrictions.eq(attribute, value)).setLockMode(mode).list();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T findById(ID id, boolean lock) {
        Object entity;
        if (lock) {
            entity = getSession().load(getPersistentClass(), id, LockMode.UPGRADE);
        } else {
            entity = getSession().load(getPersistentClass(), id);
        }
        if (entity == null) {
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = ((HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return (T) entity;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T getById(ID id, boolean lock) {
        T entity;
        if (lock) {
            entity = (T) getSession().get(getPersistentClass(), id, LockMode.UPGRADE);
        } else {
            entity = (T) getSession().get(getPersistentClass(), id);
        }
        return entity;
    }
    
    @Override
    public List<T> findAll() {
        return list();
    }
    
    @Override
    public long countAll() {
        Criteria crit = getSession().createCriteria(getPersistentClass()).setProjection(Projections.rowCount());
        Object obj = crit.uniqueResult();
        if (obj instanceof Integer) {
            return ((Integer)obj).longValue();
        }
        return (Long) crit.uniqueResult();
    }

    /**
     * Warning: bulk DELETE queries do not cause cascade-deletes. See the
     * comments to the Hibernate 3 migration guide:
     * http://hibernate.org/250.html
     * 
     * @return The number of objects that were deleted.
     */ 
    @Override
    public long deleteAll() {
        return getSession().createQuery("delete from " + getPersistentClass().getName()).executeUpdate();
    }
    
    @Override
    public void deleteAllCascade() {
        List<T> all = findAll();
        makeTransient(all);
    }
    
    @Override
    public void lockTable() {
        getSession().doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                Statement stmt = connection.createStatement();
                try {
                    stmt.execute("SET AUTOCOMMIT=0;");
                    stmt.execute("LOCK TABLES " + getTableName() + " WRITE;");
                    stmt.execute("SET AUTOCOMMIT=1;");
                } finally {
                    stmt.close();
                }
            }
        });            
    }
    
    @Override
    public String getTableName() {
        String tableName = null;
        ClassMetadata hibernateMetadata = m_sessionFactory.getClassMetadata(getPersistentClass());
        if (hibernateMetadata != null && hibernateMetadata instanceof AbstractEntityPersister) {
            AbstractEntityPersister persister = (AbstractEntityPersister)hibernateMetadata;
            tableName = persister.getTableName();
        }
        return tableName;
    }
    
    @Override
    public void unlockAllTables() {
        getSession().doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                Statement stmt = connection.createStatement();
                try {
                    stmt.execute("UNLOCK TABLES;");
                } finally {
                    stmt.close();
                }
            }
        });
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String... excludeProperty) {
        Criteria crit = createCriteria();
        Example example = Example.create(exampleInstance);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }
    
    @Override
    public T makePersistent(T entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }
    
    @Override
    public T makePersistentBySave(T entity) {
        getSession().save(entity);
        return entity;
    }
    
    @Override
    public void makePersistent(Collection<T> entities) {
        Session session = getSession();
        for (T entity : entities) {
            session.saveOrUpdate(entity);
        }
    }
    
    @Override
    public void makePersistentByMerge(Collection<T> entities) {
        Session session = getSession();
        for (T entity : entities) {
            session.merge(entity);
        }
    }
    
    @Override
    public void makePersistentBySave(Collection<T> entities) {
        Session session = getSession();
        for (T entity : entities) {
            session.save(entity);
        }
    }
    
    @Override
    public void makeTransient(T entity) {
        getSession().delete(entity);
    }
    
    @Override
    public void makeTransient(Collection<T> entities) {
        Session session = getSession();
        for (T entity : entities) {
            session.delete(entity);
        }
    }
    
    
    @Override
    @SuppressWarnings("unchecked")
    public T merge(T detachedEntity) {
        return (T) getSession().merge(detachedEntity);
    }
    
    @Override
    public void evict(T entity) {
        getSession().evict(entity);
    }
    
    @Override
    public void evictId(ID id) {
        m_sessionFactory.getCache().evictEntity(getPersistentClass(), id);
    }
    
    @Override
    public void flush() {
        getSession().flush();
    }
    
    @Override
    public void clear() {
        getSession().clear();
    }

    /**
     * Use this inside subclasses as a convenience method.
     * 
     * @param criteria
     *            The criteria that could match multiple objects.
     * @return All the objects that pass the query.
     */
    @SuppressWarnings("unchecked")
    protected List<T> list(Criterion... criteria) {
        Criteria crit = createCriteria();
        for (Criterion c : criteria) {
            crit.add(c);
        }
        return crit.list();
    }

    /**
     * Use this inside subclasses as a convenience method.
     * 
     * @param criteria
     *            The criteria that should identify a unique object.
     * @return The unique result, null if nothing is found.
     */
    protected T uniqueResult(Criterion... criteria) {
        return uniqueResult(false, criteria);
    }
    
    
    @SuppressWarnings("unchecked")
    protected T uniqueResult(boolean cached, Criterion... criteria) {
        Criteria crit = createCriteria();
        for (Criterion c : criteria) {
            crit.add(c);
        }

        return (T) crit.uniqueResult();
    }

    /**
     * Use this inside subclasses as a convenience method.
     * 
     * @param criterions
     *            The criteria that the objects should match
     * @return The number of objects that match the criteria
     */
    protected Long countByCriteria(Criterion... criterions) {
        Criteria crit = getSession().createCriteria(getPersistentClass()).setProjection(Projections.rowCount());
        for (Criterion c : criterions) {
            crit.add(c);
        }
        return (Long) crit.uniqueResult();
    }

    protected Iterable<T> makeIterable(final ScrollableResults results) {

        return new Iterable<T>() {

            public Iterator<T> iterator() {
                return new Iterator<T>() {

                    public boolean hasNext() {
                        return results.next();
                    }

                    @SuppressWarnings("unchecked")
                    public T next() {
                        return (T) results.get(0);
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

        };
    }

    protected static <T> Property findProperty(Class<T> clazz, String propertyName) {
        try {
            clazz.getMethod("get" + ucFirst(propertyName));
        } catch (NoSuchMethodException e) {
           // m_log.debug(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return Property.forName(propertyName);
    }

    private static String ucFirst(String propertyName) {
        return Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
    }

    protected Criteria createCriteria() {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    @SuppressWarnings("unchecked")
    protected List<T> list(Criteria criteria) {
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    protected List<T> list(org.hibernate.Query query) {
        return query.list();
    }

    @SuppressWarnings("unchecked")
    protected List<Object[]> listReport(org.hibernate.Query query) {
        return query.list();
    }
    
    protected String parseDateValue(Date value, SimpleDateFormat dateFormat) {
        StringBuilder string = new StringBuilder();
        if (value != null) {
            string.append('\'').append(dateFormat.format(value)).append('\'');
        } else {
            string.append("NULL");
        }
        return string.toString();
    }
    
    protected String parseStringValue(String value) {
        StringBuilder string = new StringBuilder();
        if (value != null) {
            value = makeValid(value);
            string.append('\'').append(value).append('\'');
        } else {
            string.append("NULL");
        }
        return string.toString();
    }

    protected String parseNumberValue(Number value) {
        if (value == null) {
            return "NULL";
        }
        return value.toString();
    }
    
    protected String makeValid(String input) {
        String result = input;
        if (result != null && !result.equals("")) {
            if (result.contains("\\")) {
                result = result.replace("\\", "\\\\");
            }
            if (result.contains("'")) {
                result = result.replace("'", "\\'");
            }
        }
        return result;
    }
    
}
