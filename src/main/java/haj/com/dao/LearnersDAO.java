package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.entity.Learners;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class LearnersDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Learners
	 * 
	 * @author TechFinium
	 * @see Learners
	 * @return a list of {@link haj.com.entity.Learners}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<Learners> allLearners() throws Exception {
		return (List<Learners>) super.getList("select o from Learners o");
	}

	/**
	 * Get all Learners between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see Learners
	 * @return a list of {@link haj.com.entity.Learners}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<Learners> allLearners(Long from, int noRows) throws Exception {
		String hql = "select o from Learners o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Learners>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see Learners
	 * @return a {@link haj.com.entity.Learners}
	 * @throws Exception
	 *             global exception
	 */
	public Learners findByKey(Long id) throws Exception {
		String hql = "select o from Learners o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Learners) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Learners by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see Learners
	 * @return a list of {@link haj.com.entity.Learners}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<Learners> findByName(String description) throws Exception {
		String hql = "select o from Learners o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Learners>) super.getList(hql, parameters);
	}

	public List<?> sortAndFilterUsers(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select distinct o.user from CompanyLearners o ";
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
					hql += " where o.user. " + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o.user. " + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o.user" + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o.user" + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	public int countUsersFilter(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(distinct o.user) from CompanyLearners o ";
		//String hql = "select distinct o.user from CompanyLearners o ";
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
					hql += " where o.user." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o.user." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
}
