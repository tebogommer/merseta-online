package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.Wsp;
import haj.com.entity.lookup.WspHistoricData;

public class WspHistoricDataDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspHistoricData
 	 * @author TechFinium 
 	 * @see    WspHistoricData
 	 * @return a list of {@link haj.com.entity.WspHistoricData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspHistoricData> allWspHistoricData() throws Exception {
		return (List<WspHistoricData>)super.getList("select o from WspHistoricData o");
	}

	/**
	 * Get all WspHistoricData between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WspHistoricData
 	 * @return a list of {@link haj.com.entity.WspHistoricData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspHistoricData> allWspHistoricData(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspHistoricData o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspHistoricData>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WspHistoricData
 	 * @return a {@link haj.com.entity.WspHistoricData}
 	 * @throws Exception global exception
 	 */
	public WspHistoricData findByKey(Long id) throws Exception {
	 	String hql = "select o from WspHistoricData o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspHistoricData)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspHistoricData by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WspHistoricData
  	 * @return a list of {@link haj.com.entity.WspHistoricData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspHistoricData> findByName(String description) throws Exception {
	 	String hql = "select o from WspHistoricData o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspHistoricData>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspHistoricData> findByLevyNumber(String referenceNo) throws Exception {
	 	String hql = "select o from WspHistoricData o where o.referenceNo = :referenceNo " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("referenceNo", referenceNo);
		return (List<WspHistoricData>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspHistoricData> findByFinancialYear(Integer year) throws Exception {
	 	String hql = "select o from WspHistoricData o where o.levyYear = :year " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
		return (List<WspHistoricData>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspHistoricData> findByYearRange(Integer fromYear,Integer toYear) throws Exception {
		String hql = "select o from WspHistoricData o where o.levyYear >= :fromYear and o.levyYear <= :toYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("fromYear", fromYear);
		parameters.put("toYear", toYear);
		return (List<WspHistoricData>) super.getList(hql, parameters);
	}
	
	
}

