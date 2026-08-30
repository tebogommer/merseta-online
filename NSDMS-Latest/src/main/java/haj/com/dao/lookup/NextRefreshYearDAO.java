package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.NextRefreshYear;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class NextRefreshYearDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NextRefreshYear
	 * 
	 * @author TechFinium
	 * @see NextRefreshYear
	 * @return a list of {@link haj.com.entity.NextRefreshYear}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<NextRefreshYear> allNextRefreshYear() throws Exception {
		return (List<NextRefreshYear>) super.getList("select o from NextRefreshYear o");
	}

	/**
	 * Get all NextRefreshYear between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see NextRefreshYear
	 * @return a list of {@link haj.com.entity.NextRefreshYear}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<NextRefreshYear> allNextRefreshYear(Long from, int noRows) throws Exception {
		String hql = "select o from NextRefreshYear o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<NextRefreshYear>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see NextRefreshYear
	 * @return a {@link haj.com.entity.NextRefreshYear}
	 * @throws Exception
	 *             global exception
	 */
	public NextRefreshYear findByKey(Long id) throws Exception {
		String hql = "select o from NextRefreshYear o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (NextRefreshYear) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NextRefreshYear by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see NextRefreshYear
	 * @return a list of {@link haj.com.entity.NextRefreshYear}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<NextRefreshYear> findByName(String description) throws Exception {
		String hql = "select o from NextRefreshYear o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<NextRefreshYear>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<NextRefreshYear> findLast() throws Exception {
		return (List<NextRefreshYear>) super.getList("select o from NextRefreshYear o order by o.nextRefresh desc", 1);
	}
}
