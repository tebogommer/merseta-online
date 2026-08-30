package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.FinancialYears;

public class FinancialYearsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all FinancialYears
 	 * @author TechFinium 
 	 * @see    FinancialYears
 	 * @return a list of {@link haj.com.entity.FinancialYears}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FinancialYears> allFinancialYears() throws Exception {
		return (List<FinancialYears>)super.getList("select o from FinancialYears o");
	}

	/**
	 * Get all FinancialYears between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    FinancialYears
 	 * @return a list of {@link haj.com.entity.FinancialYears}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FinancialYears> allFinancialYears(Long from, int noRows) throws Exception {
	 	String hql = "select o from FinancialYears o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<FinancialYears>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    FinancialYears
 	 * @return a {@link haj.com.entity.FinancialYears}
 	 * @throws Exception global exception
 	 */
	public FinancialYears findByKey(Long id) throws Exception {
	 	String hql = "select o from FinancialYears o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (FinancialYears)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find FinancialYears by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    FinancialYears
  	 * @return a list of {@link haj.com.entity.FinancialYears}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FinancialYears> findByName(String description) throws Exception {
	 	String hql = "select o from FinancialYears o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<FinancialYears>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<FinancialYears> findByStartYear(Integer finYear) throws Exception {
	 	String hql = "select o from FinancialYears o where o.startYear = :finYear " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("finYear", finYear);
		return (List<FinancialYears>)super.getList(hql, parameters);
	}
}

