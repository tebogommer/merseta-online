package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.entity.enums.FinYearQuartersEnum;
import haj.com.entity.lookup.FinYearQuartersLookUp;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class FinYearQuartersLookUpDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all FinYearQuartersLookUp
 	 * @author TechFinium 
 	 * @see    FinYearQuartersLookUp
 	 * @return a list of {@link haj.com.entity.FinYearQuartersLookUp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FinYearQuartersLookUp> allFinYearQuartersLookUp() throws Exception {
		return (List<FinYearQuartersLookUp>)super.getList("select o from FinYearQuartersLookUp o");
	}
	
	@SuppressWarnings("unchecked")
	public List<FinYearQuartersLookUp> allFinYearQuartersLookUpOrderedByQuarters() throws Exception {
		return (List<FinYearQuartersLookUp>)super.getList("select o from FinYearQuartersLookUp o order by o.finYearQuarters asc");
	}

	/**
	 * Get all FinYearQuartersLookUp between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    FinYearQuartersLookUp
 	 * @return a list of {@link haj.com.entity.FinYearQuartersLookUp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FinYearQuartersLookUp> allFinYearQuartersLookUp(Long from, int noRows) throws Exception {
	 	String hql = "select o from FinYearQuartersLookUp o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<FinYearQuartersLookUp>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    FinYearQuartersLookUp
 	 * @return a {@link haj.com.entity.FinYearQuartersLookUp}
 	 * @throws Exception global exception
 	 */
	public FinYearQuartersLookUp findByKey(Long id) throws Exception {
	 	String hql = "select o from FinYearQuartersLookUp o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (FinYearQuartersLookUp)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find FinYearQuartersLookUp by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    FinYearQuartersLookUp
  	 * @return a list of {@link haj.com.entity.FinYearQuartersLookUp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FinYearQuartersLookUp> findByName(String description) throws Exception {
	 	String hql = "select o from FinYearQuartersLookUp o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<FinYearQuartersLookUp>)super.getList(hql, parameters);
	}
	
	public FinYearQuartersLookUp findByQuarterAssigned(FinYearQuartersEnum quarter) throws Exception {
	 	String hql = "select o from FinYearQuartersLookUp o where o.finYearQuarters = :quarter " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("quarter", quarter);
		return (FinYearQuartersLookUp)super.getUniqueResult(hql, parameters);
	}
	
}