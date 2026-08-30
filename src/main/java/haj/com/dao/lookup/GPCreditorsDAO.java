package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.GPCreditors;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class GPCreditorsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all GPCreditors
 	 * @author TechFinium 
 	 * @see    GPCreditors
 	 * @return a list of {@link haj.com.entity.lookup.GPCreditors}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GPCreditors> allGPCreditors() throws Exception {
		return (List<GPCreditors>)super.getList("select o from GPCreditors o");
	}

	/**
	 * Get all GPCreditors between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    GPCreditors
 	 * @return a list of {@link haj.com.entity.lookup.GPCreditors}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GPCreditors> allGPCreditors(Long from, int noRows) throws Exception {
	 	String hql = "select o from GPCreditors o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<GPCreditors>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    GPCreditors
 	 * @return a {@link haj.com.entity.lookup.GPCreditors}
 	 * @throws Exception global exception
 	 */
	public GPCreditors findByKey(Long id) throws Exception {
	 	String hql = "select o from GPCreditors o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (GPCreditors)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find GPCreditors by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    GPCreditors
  	 * @return a list of {@link haj.com.entity.lookup.GPCreditors}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GPCreditors> findByName(String description) throws Exception {
	 	String hql = "select o from GPCreditors o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<GPCreditors>)super.getList(hql, parameters);
	}
	
	
	public GPCreditors findByRefNo(String refNo) throws Exception {
	 	String hql = "select o from GPCreditors o where o.vendorId = :refNo " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNo", refNo);
		return (GPCreditors)super.getUniqueResult(hql, parameters);
	}
}

