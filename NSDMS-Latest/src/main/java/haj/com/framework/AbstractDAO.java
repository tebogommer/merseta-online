package haj.com.framework;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.activation.UnsupportedDataTypeException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.primefaces.model.SortOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDAO.
 */
public abstract class AbstractDAO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Return implementation of AbstractDataProvider which will link the DAO
	 * implementations to a data source.
	 *
	 * @return the data provider
	 */
	public abstract AbstractDataProvider getDataProvider();

	/**
	 * Return a Session for data access.
	 *
	 * @return the session
	 */
	public Session getSession() {
		return getDataProvider().getSession();
	}

	/**
	 * Insert new db object.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void create(IDataEntity entity) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			create(entity, session);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public long createAndReturnLastInsertedId(IDataEntity entity) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		BigInteger result = null;
		try {
			transaction = session.beginTransaction();
			session.save(entity);
			result = (BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return result.longValue();
	}

	/**
	 * Insert a batch of new db objects within same transaction.
	 *
	 * @param entityList
	 *            the entity list
	 * @throws Exception
	 *             the exception
	 */
	public void createBatch(List<IDataEntity> entityList) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			for (IDataEntity entity : entityList)
				create(entity, session);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public void createBatch(List<IDataEntity> entityList, int batchSize) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int count = 0;
			for (IDataEntity entity : entityList) {
				create(entity, session);
				if (++count % batchSize == 0) {
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public void createAndUpdateBatch(List<IDataEntity> entityList, List<IDataEntity> updateEntityList) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			for (IDataEntity entity : entityList)
				create(entity, session);

			for (IDataEntity entity : updateEntityList)
				update(entity, session);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * Update batch.
	 *
	 * @param entityList
	 *            the entity list
	 * @throws Exception
	 *             the exception
	 */
	public void updateBatch(List<IDataEntity> entityList) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			for (IDataEntity entity : entityList)
				update(entity, session);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * Update existing db object.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void update(IDataEntity entity) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			update(entity, session);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * Delete an existing db object.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void delete(IDataEntity entity) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			delete(entity, session);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * Delete batch.
	 *
	 * @param entityList
	 *            the entity list
	 * @throws Exception
	 *             the exception
	 */
	public void deleteBatch(List<IDataEntity> entityList) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			for (IDataEntity entity : entityList)
				delete(entity, session);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	

	/**
	 * Delete batch.
	 *
	 * @param entityList
	 *            the entity list
	 * @throws Exception
	 *             the exception
	 */
	public void deleteBatch(List<IDataEntity> entityList, int batchSize) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int count = 0;
			for (IDataEntity entity : entityList) {
				delete(entity, session);
				if (++count % batchSize == 0) {
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * Creates the.
	 *
	 * @param entity
	 *            the entity
	 * @param session
	 *            the session
	 * @throws Exception
	 *             the exception
	 */
	public void create(IDataEntity entity, Session session) throws Exception {
		session.save(entity);
	}

	/**
	 * Update.
	 *
	 * @param entity
	 *            the entity
	 * @param session
	 *            the session
	 * @throws Exception
	 *             the exception
	 */
	public void update(IDataEntity entity, Session session) throws Exception {
		session.update(entity);
	}

	/**
	 * Delete.
	 *
	 * @param entity
	 *            the entity
	 * @param session
	 *            the session
	 * @throws Exception
	 *             the exception
	 */
	public void delete(IDataEntity entity, Session session) throws Exception {
		session.delete(entity);
	}

	/**
	 * Adds the parameter.
	 *
	 * @param query
	 *            the query
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	private void addParameter(Query query, String key, Object value) {
		query.setParameter(key, value);
		/*
		 * if (value == null) query.setParameter(key, value); else if (value instanceof
		 * Timestamp) query.setTimestamp(key, (Timestamp) value); else if (value
		 * instanceof BigDecimal) query.setBigDecimal(key, (BigDecimal) value); else if
		 * (value instanceof BigInteger) query.setBigInteger(key, (BigInteger) value);
		 * else if (value instanceof byte[]) query.setBinary(key, (byte[]) value); else
		 * if (value instanceof Boolean) query.setBoolean(key, (Boolean) value); else if
		 * (value instanceof Byte) query.setByte(key, (Byte) value); else if (value
		 * instanceof Calendar) query.setCalendarDate(key, (Calendar) value); else if
		 * (value instanceof Character) query.setCharacter(key, (Character) value); else
		 * if (value instanceof Date) query.setDate(key, (Date) value); else if (value
		 * instanceof Double) query.setDouble(key, (Double) value); else if (value
		 * instanceof Float) query.setFloat(key, (Float) value); else if (value
		 * instanceof Integer) query.setInteger(key, (Integer) value); else if (value
		 * instanceof Locale) query.setLocale(key, (Locale) value); else if (value
		 * instanceof Long) query.setLong(key, (Long) value); else if (value instanceof
		 * Short) query.setShort(key, (Short) value); else if (value instanceof String)
		 * query.setString(key, (String) value);
		 *
		 * else query.setParameter(key, value);
		 */
	}

	/**
	 * Return a list of db objects based on specified hql query.
	 *
	 * @param hql
	 *            the hql
	 * @return the list
	 */
	protected List<?> getList(String hql) {
		return getList(hql, null);
	}

	/**
	 * Gets the list.
	 *
	 * @param hql
	 *            the hql
	 * @param noRows
	 *            the no rows
	 * @return the list
	 */
	protected List<?> getList(String hql, int noRows) {
		return getList(hql, null, noRows);
	}

	/**
	 * Return a list of db objects based on specified hql query with filter
	 * parameters.
	 *
	 * @param hql
	 *            the hql
	 * @param parameters
	 *            the parameters
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	protected List<?> getList(String hql, Map<String, Object> parameters) {
		List<Object> result = null;
		Session session = getSession();
		try {
			Query query = session.createQuery(hql);
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(query, key, parameters.get(key));
			result = query.getResultList();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Gets the list.
	 *
	 * @param hql
	 *            the hql
	 * @param parameters
	 *            the parameters
	 * @param noRows
	 *            the no rows
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	protected List<?> getList(String hql, Map<String, Object> parameters, int noRows) {
		List<Object> result = null;
		Session session = getSession();
		try {
			Query query = session.createQuery(hql);
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(query, key, parameters.get(key));
			query.setMaxResults(noRows);
			result = query.getResultList();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Gets the list.
	 *
	 * @param hql
	 *            the hql
	 * @param parameters
	 *            the parameters
	 * @param startingAt
	 *            the starting at
	 * @param maxPerPage
	 *            the max per page
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	protected List<?> getList(String hql, Map<String, Object> parameters, int startingAt, int maxPerPage) {
		List<Object> result = null;
		Session session = getSession();
		try {
			Query query = session.createQuery(hql);
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(query, key, parameters.get(key));
			query.setFirstResult(startingAt);
			query.setMaxResults(maxPerPage);
			result = query.getResultList();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Return a single db object based on specified hql query.
	 *
	 * @param hql
	 *            the hql
	 * @return the unique result
	 */
	protected Object getUniqueResult(String hql) {
		return getUniqueResult(hql, null);
	}

	/**
	 * Return a single db object based on specified hql query with filter
	 * parameters.
	 *
	 * @param hql
	 *            the hql
	 * @param parameters
	 *            the parameters
	 * @return the unique result
	 */
	protected Object getUniqueResult(String hql, Map<String, Object> parameters) {
		Object result = null;
		Session session = getSession();
		try {
			Query query = session.createQuery(hql);
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(query, key, parameters.get(key));
			result = query.getSingleResult();// uniqueResult();
		} catch (javax.persistence.NoResultException ne) {
			// logger.info("No result exception!");
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Return the next value for specified sequence.
	 *
	 * @param sequence
	 *            the sequence
	 * @return the next from sequence
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	protected long getNextFromSequence(String sequence) throws Exception {
		Session session = getSession();
		try {
			String sql = "select (nextval for " + sequence + ") from users fetch first 1 rows only";
			List<Object> l = session.createSQLQuery(sql).list();
			for (Object o : l) {
				if (o instanceof BigInteger) return ((BigInteger) o).longValue();
				if (o instanceof BigDecimal) return ((BigDecimal) o).longValue();
				else if (o instanceof Long) return ((Long) o).longValue();
				else if (o instanceof Integer) return ((Integer) o).longValue();
				else if (o instanceof Short) return ((Short) o).longValue();
				else throw new UnsupportedDataTypeException("No sequence cast defined for " + o.getClass().toString());
			}
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return 0l;
	}

	/**
	 * Gets the last seq nr.
	 *
	 * @param hql
	 *            the hql
	 * @return the last seq nr
	 *
	 */
	@Deprecated
	@SuppressWarnings("rawtypes")
	protected long getLastSeqNr(String hql) {
		long nr = 0;
		try {
			List array = (List) getList(hql);
			for (Object o : array) {
				if (o instanceof Integer) try {
					nr = ((Integer) o).longValue();
				} catch (java.lang.NullPointerException np) {
				}
				else if (o instanceof Short) try {
					nr = ((Short) o).longValue();
				} catch (java.lang.NullPointerException np) {
				}
				else if (o instanceof Long) try {
					nr = ((Long) o).longValue();
				} catch (java.lang.NullPointerException np) {
				}
			}
			nr++;
		} catch (Exception e) {
			logger.fatal(e);
		}
		return nr;
	}

	/**
	 * Return only first db object from specified hql query.
	 *
	 * @param hql
	 *            the hql
	 * @return the object
	 */
	protected Object oneRow(String hql) {
		Object result = null;
		Session session = getSession();
		try {
			Query query = session.createQuery(hql);
			query.setMaxResults(1);
			result = query.getSingleResult();
		} catch (javax.persistence.NoResultException ne) {
			logger.info("No result exception!");
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Non select sql.
	 *
	 * @param sql
	 *            the sql
	 * @throws Exception
	 *             the exception
	 */
	public void nonSelectSql(String sql) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.createSQLQuery(sql).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public <T> List<T> nativeSelectSqlList(String sql, Class<T> entityType) throws Exception {
		List<T> list = null;
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			NativeQuery nq = session.createNativeQuery(sql);
			nq.setResultTransformer(Transformers.aliasToBean(entityType));
			list = nq.getResultList();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public <T> List<T> nativeSelectSqlList(String sql, Class<T> entityType, Map<String, Object> parameters) throws Exception {
		List<T> list = null;
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			NativeQuery nq = session.createNativeQuery(sql);
			nq.setResultTransformer(Transformers.aliasToBean(entityType));
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(nq, key, parameters.get(key));
			list = nq.getResultList();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public <T> T nativeSelectSqlUniqueResult(String sql, Class<T> entityType) throws Exception {
		Object result = null;
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			NativeQuery nq = session.createNativeQuery(sql);
			nq.setResultTransformer(Transformers.aliasToBean(entityType));
			result = nq.getSingleResult();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
		return (T) result;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public <T> T nativeSelectSqlUniqueResult(String sql, Map<String, Object> parameters) throws Exception {
		Object result = null;
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			NativeQuery nq = session.createNativeQuery(sql);
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(nq, key, parameters.get(key));
			result = nq.getSingleResult();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
		return (T) result;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public <T> T nativeSelectSqlUniqueResult(String sql, Class<T> entityType, Map<String, Object> parameters) throws Exception {
		Object result = null;
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			NativeQuery nq = session.createNativeQuery(sql);
			nq.setResultTransformer(Transformers.aliasToBean(entityType));
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(nq, key, parameters.get(key));
			result = nq.getSingleResult();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
		return (T) result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T nativeSelectSqlUniqueResultNoTransform(String sql, Class<T> entityType, Map<String, Object> parameters) throws Exception {
		Object result = null;
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			NativeQuery nq = session.createNativeQuery(sql);
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(nq, key, parameters.get(key));
			result = nq.getSingleResult();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
		return (T) result;
	}

	/**
	 * Count.
	 *
	 * @param entity
	 *            the entity
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long count(Class<?> entity) throws Exception {
		return (Long) getUniqueResult("select count(o) from " + entity.getName() + " o ");
	}

	/**
	 * Count.
	 *
	 * @param entity
	 *            the entity
	 * @param filters
	 *            the filters
	 * @return the int
	 */
	public int count(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from " + entity.getName() + " o ";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " where o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}

	/**
	 * Count.
	 * 
	 * @param filters
	 *            the filters
	 *
	 * @return the int
	 */
	public int countWhere(Map<String, Object> filters, String hql) {
		if (filters != null) {
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}

	/**
	 * Sort and filter.
	 *
	 * @param entity
	 *            the entity
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @param hql
	 *            the hql
	 * @return the list
	 */
	public List<?> sortAndFilter(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " where o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o." + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	/**
	 * Sort and filter.
	 * 
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @param hql
	 *            the hql
	 *
	 * @return the list
	 */
	public List<?> sortAndFilterWhere(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
		if (filters != null) {
			// boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o." + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	public List<?> hqlAndParamOnly(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters, String hql) {
		return getList(hql, parameters, startingAt, pageSize);
	}

	/**
	 * Sort and filter.
	 *
	 * @param entity
	 *            the entity
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return the list
	 */
	public List<?> sortAndFilter(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from " + entity.getName() + " o ";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " where o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o." + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	/**
	 * get a unique result of type class with a key.
	 *
	 * @param entity
	 *            the entity
	 * @param key
	 *            the id
	 */
	public IDataEntity getByClassAndKey(Class<?> entity, Long key) {
		return getByClassAndKey(entity.getName(), key);
	}

	/**
	 * get a unique result of type class from class name and key.
	 *
	 * @param className
	 *            the class name
	 * @param key
	 *            the id
	 */
	public IDataEntity getByClassAndKey(String className, Long key) {
		String hql = "select o from " + className + " o " + "where o.id = " + key;
		IDataEntity toReturn = (IDataEntity) getUniqueResult(hql);
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	protected List<?> getNQList(String namedQuery, Map<String, Object> parameters) {
		List<Object> result = null;
		Session session = getSession();
		try {
			Query query = session.getNamedQuery(namedQuery);
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(query, key, parameters.get(key));
			result = query.getResultList();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List<?> getNQList(String namedQuery, Map<String, Object> parameters, int startingAt, int maxPerPage) {
		List<Object> result = null;
		Session session = getSession();
		try {
			Query query = session.getNamedQuery(namedQuery);
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(query, key, parameters.get(key));
			query.setFirstResult(startingAt);
			query.setMaxResults(maxPerPage);
			result = query.getResultList();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	protected Object getUniqueNQResult(String namedQuery, Map<String, Object> parameters) {
		Object result = null;
		Session session = getSession();
		try {
			Query query = session.getNamedQuery(namedQuery);
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(query, key, parameters.get(key));
			result = query.getSingleResult();// uniqueResult();
		} catch (javax.persistence.NoResultException ne) {
			logger.info("No result exception!");
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Update batch.
	 *
	 * @param entityList
	 *            the entity list
	 * @throws Exception
	 *             the exception
	 */
	public void updateBatch(List<IDataEntity> entityList, int batchSize) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int count = 0;
			for (IDataEntity entity : entityList) {
				update(entity, session);
				if (++count % batchSize == 0) {
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * This is purely for example purposes and custom code should be in the specific
	 * DAO
	 * 
	 * @param namedQuery
	 * @param batchSize
	 * @throws Exception
	 */
	@Deprecated
	public void updateBatch(String namedQuery, int batchSize) throws Exception {
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			ScrollableResults scrollableResults = session.getNamedQuery(namedQuery).setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
			int count = 0;
			while (scrollableResults.next()) {
				IDataEntity entity = (IDataEntity) scrollableResults.get(0);
				update(entity, session);
				if (++count % batchSize == 0) {
					session.flush();
					session.clear();
				}
			}

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public <T> List<T> nativeSelectSqlList(String sql, Class<T> entityType, Map<String, Object> parameters, int startingAt, int maxPerPage) throws Exception {
		List<T> list = null;
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query nq = session.createNativeQuery(sql);
			nq.setFirstResult(startingAt);
			nq.setMaxResults(maxPerPage);
			((NativeQuery)nq).setResultTransformer(Transformers.aliasToBean(entityType));
			if (parameters != null) for (String key : parameters.keySet())
				addParameter(nq, key, parameters.get(key));
			list = nq.getResultList();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return list;
	}
	
	public int nativeSQLInsert(String sql) throws Exception {
		int list = 0;
		Session session = getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			NativeQuery nq = session.createNativeQuery(sql);
			list = nq.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
		return list;
	}
	
	
	

	/*
	 * DML-style operations ====================
	 * 
	 * 
	 * Sample 1 -------- Session session = sessionFactory.openSession(); Transaction
	 * tx = session.beginTransaction();
	 * 
	 * String hqlUpdate =
	 * "update Customer c set c.name = :newName where c.name = :oldName"; // or
	 * String hqlUpdate =
	 * "update Customer set name = :newName where name = :oldName"; int
	 * updatedEntities = s.createQuery( hqlUpdate ) .setString( "newName", newName )
	 * .setString( "oldName", oldName ) .executeUpdate(); tx.commit();
	 * session.close();
	 * 
	 * 
	 * Sample 2 -------- Session session = sessionFactory.openSession(); Transaction
	 * tx = session.beginTransaction(); String hqlVersionedUpdate =
	 * "update versioned Customer set name = :newName where name = :oldName"; int
	 * updatedEntities = s.createQuery( hqlUpdate ) .setString( "newName", newName )
	 * .setString( "oldName", oldName ) .executeUpdate(); tx.commit();
	 * session.close();
	 * 
	 * 
	 * Sample 3 -------- Session session = sessionFactory.openSession(); Transaction
	 * tx = session.beginTransaction();
	 * 
	 * String hqlDelete = "delete Customer c where c.name = :oldName"; // or String
	 * hqlDelete = "delete Customer where name = :oldName"; int deletedEntities =
	 * s.createQuery( hqlDelete ) .setString( "oldName", oldName ) .executeUpdate();
	 * tx.commit(); session.close();
	 * 
	 * 
	 * 
	 * Sample 4 -------- Session session = sessionFactory.openSession(); Transaction
	 * tx = session.beginTransaction();
	 * 
	 * String hqlInsert =
	 * "insert into DelinquentAccount (id, name) select c.id, c.name from Customer c where ..."
	 * ; int createdEntities = s.createQuery( hqlInsert ) .executeUpdate();
	 * tx.commit(); session.close();
	 */
}
