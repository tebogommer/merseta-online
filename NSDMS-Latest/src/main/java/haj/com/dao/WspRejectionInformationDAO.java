package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.DgVerificationRejectionInformation;
import haj.com.entity.WspRejectionInformation;

public class WspRejectionInformationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspRejectionInformation
 	 * @author TechFinium 
 	 * @see    WspRejectionInformation
 	 * @return a list of {@link haj.com.entity.WspRejectionInformation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspRejectionInformation> allWspRejectionInformation() throws Exception {
		return (List<WspRejectionInformation>)super.getList("select o from WspRejectionInformation o");
	}

	/**
	 * Get all WspRejectionInformation between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WspRejectionInformation
 	 * @return a list of {@link haj.com.entity.WspRejectionInformation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspRejectionInformation> allWspRejectionInformation(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspRejectionInformation o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspRejectionInformation>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WspRejectionInformation
 	 * @return a {@link haj.com.entity.WspRejectionInformation}
 	 * @throws Exception global exception
 	 */
	public WspRejectionInformation findByKey(Long id) throws Exception {
	 	String hql = "select o from WspRejectionInformation o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspRejectionInformation)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspRejectionInformation by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WspRejectionInformation
  	 * @return a list of {@link haj.com.entity.WspRejectionInformation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspRejectionInformation> findByName(String description) throws Exception {
	 	String hql = "select o from WspRejectionInformation o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspRejectionInformation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspRejectionInformation> findByWspRoleIdAndLastestEntry(Long wspId, Long roleID, Boolean lastestEntry) throws Exception {
	 	String hql = "select o from WspRejectionInformation o where o.wsp.id = :wspId and o.role.id = :roleID and o.latestEntry = :lastestEntry" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("wspId", wspId);
	    parameters.put("roleID", roleID);
	    parameters.put("lastestEntry", lastestEntry);
		return (List<WspRejectionInformation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspRejectionInformation> findByWspUserIdAndLastestEntry(Long wspId, Long userId, Boolean lastestEntry) throws Exception {
	 	String hql = "select o from WspRejectionInformation o where o.wsp.id = :wspId and o.user.id = :userId and o.latestEntry = :lastestEntry" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("wspId", wspId);
	    parameters.put("userId", userId);
	    parameters.put("lastestEntry", lastestEntry);
		return (List<WspRejectionInformation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspRejectionInformation> findByWspAndLastestEntry(Long wspId, Boolean lastestEntry) throws Exception {
	 	String hql = "select o from WspRejectionInformation o where o.wsp.id = :wspId and o.latestEntry = :lastestEntry" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("wspId", wspId);
	    parameters.put("lastestEntry", lastestEntry);
		return (List<WspRejectionInformation>)super.getList(hql, parameters);
	}
}

