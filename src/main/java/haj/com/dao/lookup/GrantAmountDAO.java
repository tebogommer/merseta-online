package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.enums.GrantTypeEnum;
import haj.com.entity.lookup.GrantAmount;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class GrantAmountDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all GrantAmount
 	 * @author TechFinium 
 	 * @see    GrantAmount
 	 * @return a list of {@link haj.com.entity.GrantAmount}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GrantAmount> allGrantAmount() throws Exception {
		return (List<GrantAmount>)super.getList("select o from GrantAmount o");
	}
	
	@SuppressWarnings("unchecked")
	public List<GrantAmount> allGrantAmount(GrantTypeEnum grantType) throws Exception {
	 	String hql = "select o from GrantAmount o where o.grantType = :grantType" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("grantType", grantType);
		return (List<GrantAmount>)super.getList(hql, parameters);
	}

	/**
	 * Get all GrantAmount between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    GrantAmount
 	 * @return a list of {@link haj.com.entity.GrantAmount}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GrantAmount> allGrantAmount(Long from, int noRows) throws Exception {
	 	String hql = "select o from GrantAmount o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<GrantAmount>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    GrantAmount
 	 * @return a {@link haj.com.entity.GrantAmount}
 	 * @throws Exception global exception
 	 */
	public GrantAmount findByKey(Long id) throws Exception {
	 	String hql = "select o from GrantAmount o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (GrantAmount)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find GrantAmount by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    GrantAmount
  	 * @return a list of {@link haj.com.entity.GrantAmount}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GrantAmount> findByName(String description) throws Exception {
	 	String hql = "select o from GrantAmount o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<GrantAmount>)super.getList(hql, parameters);
	}
}

