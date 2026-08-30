package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.entity.EmployeesHistory;
import haj.com.entity.Wsp;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesHistoryDAO.
 */
public class EmployeesHistoryDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all EmployeesHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EmployeesHistory}
	 * @throws Exception
	 *             global exception
	 * @see EmployeesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesHistory> allEmployeesHistory() throws Exception {
		return (List<EmployeesHistory>) super.getList("select o from EmployeesHistory o");
	}

	/**
	 * Get all EmployeesHistory between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.EmployeesHistory}
	 * @throws Exception
	 *             global exception
	 * @see EmployeesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesHistory> allEmployeesHistory(Long from, int noRows) throws Exception {
		String hql = "select o from EmployeesHistory o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<EmployeesHistory>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	

	/**
	 * All EmployeesHistory employed count.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allEmployeesHistoryEmployedCount(Wsp wsp) throws Exception {
		String hql = "select count(o) from EmployeesHistory o where o.wsp.id = :wspID and o.employedUnEmployed = :employed";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("employed", EmployedUnEmployedEnum.Employed);
		return (long) super.getUniqueResult(hql, parameters);
	}



	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.EmployeesHistory}
	 * @throws Exception
	 *             global exception
	 * @see EmployeesHistory
	 */
	public EmployeesHistory findByKey(Long id) throws Exception {
		String hql = "select o from EmployeesHistory o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (EmployeesHistory) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find EmployeesHistory by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.EmployeesHistory}
	 * @throws Exception
	 *             global exception
	 * @see EmployeesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesHistory> findByName(String description) throws Exception {
		String hql = "select o from EmployeesHistory o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<EmployeesHistory>) super.getList(hql, parameters);
	}
	
	/**
	 * Find EmployeesHistory by forEmployee.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.EmployeesHistory}
	 * @throws Exception
	 *             global exception
	 * @see EmployeesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesHistory> findByForEmployee(Long id) throws Exception {
		String hql = "select o from EmployeesHistory o where o.forEmployees.id =:id order by o.createDate ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id",  id);
		return (List<EmployeesHistory>) super.getList(hql, parameters);
	}

	/**
	 * Sort and filter.
	 *
	 * @param entity the entity
	 * @param startingAt the starting at
	 * @param pageSize the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters the filters
	 * @param wspId the wsp id
	 * @return the list
	 */
	public List<?> sortAndFilter(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long id, boolean wsp) {
		String hql = "select o from " + entity.getName() + " o left join fetch o.site s left join fetch ";
		if (wsp)
			hql += "o.wsp w  where o.wsp.id = :id and o.employedUnEmployed = :employed";
		else
			hql += "o.company c where c.id = :id";
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("id", id);
		if (wsp)
			parms.put("employed", EmployedUnEmployedEnum.Employed);
		if (filters != null) {
			boolean ft = true;

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
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
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
				hql += " order by o." + sortField + " asc  ";
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
	 * Count.
	 *
	 * @param entity
	 *            the entity
	 * @param filters
	 *            the filters
	 * @param wspId
	 *            the wsp id
	 * @return the int
	 */
	public int count(Class<?> entity, Map<String, Object> filters, Long wspId, boolean wsp) {
		String hql = "select count(o) from " + entity.getName() + " o ";
		if (wsp) {
			hql += "where o.wsp.id = :wspId and o.employedUnEmployed = :employed";			
		}else {
			hql += "where o.company.id = :wspId";					
		}
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("wspId", wspId);
		if (wsp) {
			parms.put("employed", EmployedUnEmployedEnum.Employed);			
		}
		if (filters != null) {
			boolean ft = true;
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
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}


	/** The Constant leftJoins. */
	private static final String leftJoins = " "
			+ "left join fetch o.gender left join fetch o.equity left join fetch o.municipality left join fetch o.ofoCode left join fetch o.disability left join fetch o.wsp left join fetch o.highestQualTitle "
			+ "left join fetch o.occupationCategory left join fetch o.highestQualType left join fetch o.employmentStatus left join fetch o.interventionType left join fetch o.nqfLevel left join fetch o.interventionLevel left join fetch o.sourceOfFunding"
			+ "left join fetch o.nationality" + " ";

}
