package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.DhetReporting;
import haj.com.entity.enums.DhetFileNumberEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class DhetReportingDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DhetReporting
 	 * @author TechFinium 
 	 * @see    DhetReporting
 	 * @return a list of {@link haj.com.entity.DhetReporting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DhetReporting> allDhetReporting() throws Exception {
		return (List<DhetReporting>)super.getList("select o from DhetReporting o");
	}

	/**
	 * Get all DhetReporting between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DhetReporting
 	 * @return a list of {@link haj.com.entity.DhetReporting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DhetReporting> allDhetReporting(Long from, int noRows) throws Exception {
	 	String hql = "select o from DhetReporting o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<DhetReporting>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DhetReporting
 	 * @return a {@link haj.com.entity.DhetReporting}
 	 * @throws Exception global exception
 	 */
	public DhetReporting findByKey(Long id) throws Exception {
	 	String hql = "select o from DhetReporting o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (DhetReporting)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DhetReporting by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DhetReporting
  	 * @return a list of {@link haj.com.entity.DhetReporting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DhetReporting> findByName(String description) throws Exception {
	 	String hql = "select o from DhetReporting o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DhetReporting>)super.getList(hql, parameters);
	}
	
	public DhetReporting findByDhetFileNumberEnum(DhetFileNumberEnum dhetFileNumberEnum) throws Exception {
	 	String hql = "select o from DhetReporting o where o.dhetFileNumberEnum = :dhetFileNumberEnum " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("dhetFileNumberEnum", dhetFileNumberEnum);
		return (DhetReporting)super.getUniqueResult(hql, parameters);
	}
	
}