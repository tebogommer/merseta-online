package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.DgVerificationRejectionInformation;

public class DgVerificationRejectionInformationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DgVerificationRejectionInformation
 	 * @author TechFinium 
 	 * @see    DgVerificationRejectionInformation
 	 * @return a list of {@link haj.com.entity.DgVerificationRejectionInformation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgVerificationRejectionInformation> allDgVerificationRejectionInformation() throws Exception {
		return (List<DgVerificationRejectionInformation>)super.getList("select o from DgVerificationRejectionInformation o");
	}

	/**
	 * Get all DgVerificationRejectionInformation between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DgVerificationRejectionInformation
 	 * @return a list of {@link haj.com.entity.DgVerificationRejectionInformation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgVerificationRejectionInformation> allDgVerificationRejectionInformation(Long from, int noRows) throws Exception {
	 	String hql = "select o from DgVerificationRejectionInformation o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<DgVerificationRejectionInformation>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DgVerificationRejectionInformation
 	 * @return a {@link haj.com.entity.DgVerificationRejectionInformation}
 	 * @throws Exception global exception
 	 */
	public DgVerificationRejectionInformation findByKey(Long id) throws Exception {
	 	String hql = "select o from DgVerificationRejectionInformation o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (DgVerificationRejectionInformation)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DgVerificationRejectionInformation by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DgVerificationRejectionInformation
  	 * @return a list of {@link haj.com.entity.DgVerificationRejectionInformation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgVerificationRejectionInformation> findByName(String description) throws Exception {
	 	String hql = "select o from DgVerificationRejectionInformation o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DgVerificationRejectionInformation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DgVerificationRejectionInformation> findByDgVerificationRoleIdAndLastestEntry(Long dgVerificationId, Long roleID, Boolean lastestEntry) throws Exception {
	 	String hql = "select o from DgVerificationRejectionInformation o where o.dgVerification.id = :dgVerificationId and o.role.id = :roleID and o.latestEntry = :lastestEntry" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("dgVerificationId", dgVerificationId);
	    parameters.put("roleID", roleID);
	    parameters.put("lastestEntry", lastestEntry);
		return (List<DgVerificationRejectionInformation>)super.getList(hql, parameters);
	}
	
}