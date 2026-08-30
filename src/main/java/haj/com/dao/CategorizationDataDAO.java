package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.CategorizationData;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class CategorizationDataDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CategorizationData
 	 * @author TechFinium 
 	 * @see    CategorizationData
 	 * @return a list of {@link haj.com.entity.CategorizationData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CategorizationData> allCategorizationData() throws Exception {
		return (List<CategorizationData>)super.getList("select o from CategorizationData o");
	}

	/**
	 * Get all CategorizationData between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CategorizationData
 	 * @return a list of {@link haj.com.entity.CategorizationData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CategorizationData> allCategorizationData(Long from, int noRows) throws Exception {
	 	String hql = "select o from CategorizationData o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CategorizationData>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CategorizationData
 	 * @return a {@link haj.com.entity.CategorizationData}
 	 * @throws Exception global exception
 	 */
	public CategorizationData findByKey(Long id) throws Exception {
	 	String hql = "select o from CategorizationData o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CategorizationData)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CategorizationData by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CategorizationData
  	 * @return a list of {@link haj.com.entity.CategorizationData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CategorizationData> findByName(String description) throws Exception {
	 	String hql = "select o from CategorizationData o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CategorizationData>)super.getList(hql, parameters);
	}
	@SuppressWarnings("unchecked")
	public CategorizationData findByCompany(Long companyID) throws Exception {
	 	String hql = "select o from CategorizationData o where o.company.id = :companyID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyID", companyID);
		return (CategorizationData)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CategorizationData> findByLNumber(String lNumber) throws Exception {
	 	String hql = "select o from CategorizationData o where o.levyNumber like  :lNumber" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("lNumber", lNumber.trim());
		return (List<CategorizationData>)super.getList(hql, parameters);
	}
}

