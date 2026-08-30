package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.DgLegacyPayments;

public class DgLegacyPaymentsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DgLegacyPayments
 	 * @author TechFinium 
 	 * @see    DgLegacyPayments
 	 * @return a list of {@link haj.com.entity.DgLegacyPayments}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgLegacyPayments> allDgLegacyPayments() throws Exception {
		return (List<DgLegacyPayments>)super.getList("select o from DgLegacyPayments o");
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from DgLegacyPayments o")).intValue();
	}

	/**
	 * Get all DgLegacyPayments between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DgLegacyPayments
 	 * @return a list of {@link haj.com.entity.DgLegacyPayments}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgLegacyPayments> allDgLegacyPayments(Long from, int noRows) throws Exception {
	 	String hql = "select o from DgLegacyPayments o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DgLegacyPayments>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DgLegacyPayments
 	 * @return a {@link haj.com.entity.DgLegacyPayments}
 	 * @throws Exception global exception
 	 */
	public DgLegacyPayments findByKey(Long id) throws Exception {
	 	String hql = "select o from DgLegacyPayments o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DgLegacyPayments)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DgLegacyPayments by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DgLegacyPayments
  	 * @return a list of {@link haj.com.entity.DgLegacyPayments}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgLegacyPayments> findByName(String description) throws Exception {
	 	String hql = "select o from DgLegacyPayments o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DgLegacyPayments>)super.getList(hql, parameters);
	}
}

