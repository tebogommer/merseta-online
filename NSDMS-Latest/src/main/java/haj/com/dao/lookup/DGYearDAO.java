package haj.com.dao.lookup;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.enums.DGWindowTypeEnum;
import haj.com.entity.lookup.DGYear;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class DGYearDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DGYear
	 * 
	 * @author TechFinium
	 * @see DGYear
	 * @return a list of {@link haj.com.entity.DGYear}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DGYear> allDGYear() throws Exception {
		return (List<DGYear>) super.getList("select o from DGYear o");
	}

	/**
	 * Get all DGYear between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see DGYear
	 * @return a list of {@link haj.com.entity.DGYear}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DGYear> allDGYear(Long from, int noRows) throws Exception {
		String              hql        = "select o from DGYear o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<DGYear>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see DGYear
	 * @return a {@link haj.com.entity.DGYear}
	 * @throws Exception
	 *             global exception
	 */
	public DGYear findByKey(Long id) throws Exception {
		String              hql        = "select o from DGYear o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (DGYear) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DGYear by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see DGYear
	 * @return a list of {@link haj.com.entity.DGYear}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DGYear> findByName(String description) throws Exception {
		String              hql        = "select o from DGYear o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<DGYear>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DGYear> findByDatePivitol(Date searchDate) throws Exception {
		String              hql        = "select o from DGYear o where date(:searchDate) between date(o.startDate) and date(o.endDate) and o.dgWindowType = :dgWindowType";
		Map<String, Object> parameters = new Hashtable<String, Object>();		
		parameters.put("searchDate", searchDate);
		parameters.put("dgWindowType", DGWindowTypeEnum.Pivitol);
		return (List<DGYear>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DGYear> findByDate(Date searchDate) throws Exception {
		String              hql        = "select o from DGYear o where date(:searchDate) between date(o.startDate) and date(o.endDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();		
		parameters.put("searchDate", searchDate);
		return (List<DGYear>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DGYear>  findLatestDGYear() throws Exception {
		String              hql        = "select o from DGYear o order by id desc";
		return (List<DGYear> ) super.getList(hql, 1);
	}
	
	public Long findTotalDGYearByFinYear(Integer finYear) throws Exception {
		String              hql        = "select count(o) from DGYear o where o.finYear = :finYear order by id desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();		
		parameters.put("finYear", finYear);
		return (Long) super.getUniqueResult(hql, parameters);
	}
}

