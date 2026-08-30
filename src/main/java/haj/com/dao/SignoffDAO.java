package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.DgVerification;
import haj.com.entity.MgVerification;
import haj.com.entity.Signoff;
import haj.com.entity.WspDispute;
import haj.com.entity.enums.SignoffByEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SignoffDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Signoff
 	 * @author TechFinium 
 	 * @see    Signoff
 	 * @return a list of {@link haj.com.entity.Signoff}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Signoff> allSignoff() throws Exception {
		return (List<Signoff>)super.getList("select o from Signoff o");
	}

	/**
	 * Get all Signoff between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Signoff
 	 * @return a list of {@link haj.com.entity.Signoff}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Signoff> allSignoff(Long from, int noRows) throws Exception {
	 	String hql = "select o from Signoff o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<Signoff>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Signoff
 	 * @return a {@link haj.com.entity.Signoff}
 	 * @throws Exception global exception
 	 */
	public Signoff findByKey(Long id) throws Exception {
	 	String hql = "select o from Signoff o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (Signoff)super.getUniqueResult(hql, parameters);
	}
	
	public Signoff findByGUID(String guid) throws Exception {
	 	String hql = "select o from Signoff o where o.guid = :guid " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("guid", guid);
		return (Signoff)super.getUniqueResult(hql, parameters);
	}
	
	public Integer countByGUID(String guid) throws Exception {
	 	String hql = "select count(o) from Signoff o where o.guid = :guid " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("guid", guid);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Signoff findByGUIDnotAccepted(String guid) throws Exception {
	 	String hql = "select o from Signoff o where o.guid = :guid and (o.accept is null or o.accept = false)" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("guid", guid);
		return (Signoff)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Signoff by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Signoff
  	 * @return a list of {@link haj.com.entity.Signoff}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Signoff> findByName(String description) throws Exception {
	 	String hql = "select o from Signoff o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByVerifivcation(MgVerification mgVerification) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.mgVerification.id = :mgVerificationId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("mgVerificationId", mgVerification.getId());
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByDGVerifivcation(DgVerification dgVerification) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.dgVerification.id = :dgVerificationId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("dgVerificationId", dgVerification.getId());
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByWspDispute(WspDispute wspDispute) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.wspDispute.id = :wspDisputeId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("wspDisputeId", wspDispute.getId());
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByTargetKeyAndClass(Long targetKey, String targetClass) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByTargetKeyAndClassAndLastest(Long targetKey, String targetClass, Boolean lastestSignOff) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.lastestSignoff = :lastestSignOff" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("lastestSignOff", lastestSignOff);
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByTargetKeyAndClassAndLastest(Long targetKey, String targetClass, SignoffByEnum signoffByEnum) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.signoffByEnum = :signoffByEnum" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("signoffByEnum", signoffByEnum);
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	public Signoff findByTargetKeyAndClassAndSignoffByEnum(Long targetKey, String targetClass, SignoffByEnum signoffByEnum) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.signoffByEnum = :signoffByEnum" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("signoffByEnum", signoffByEnum);
	    return (Signoff)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByTargetKeyAndClassAndLastest(Long targetKey, String targetClass, Boolean lastestSignOff, SignoffByEnum signoffByEnum) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.lastestSignoff = :lastestSignOff and o.signoffByEnum = :signoffByEnum" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("lastestSignOff", lastestSignOff);
	    parameters.put("signoffByEnum", signoffByEnum);
		return (List<Signoff>)super.getList(hql, parameters);
	}

	public Signoff findSingleByTargetKeyAndClassAndLastest(Long targetKey, String targetClass, Boolean lastestSignOff, SignoffByEnum signoffByEnum) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.lastestSignoff = :lastestSignOff and o.signoffByEnum = :signoffByEnum" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("lastestSignOff", lastestSignOff);
	    parameters.put("signoffByEnum", signoffByEnum);
	    return (Signoff)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByTargetKeyAndClassTempUsers(Long targetKey, String targetClass) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.tempSignoff is not null" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByTargetKeyAndClassTempUsersLastest(Long targetKey, String targetClass, Boolean lastestSignOff) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.tempSignoff is not null and o.lastestSignoff = :lastestSignOff" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("lastestSignOff", lastestSignOff);
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByTargetKeyAndClassNsdmsUsers(Long targetKey, String targetClass) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.user is not null" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByTargetKeyAndClassNsdmsUsersLastest(Long targetKey, String targetClass, Boolean lastestSignOff) throws Exception {
	 	String hql = "select o from Signoff o join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.user is not null and o.lastestSignoff = :lastestSignOff" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("lastestSignOff", lastestSignOff);
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByTargetKeyAndClassAndLastest(Long targetKey, String targetClass, boolean lastestSignoff) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.lastestSignoff = :lastestSignoff" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("lastestSignoff", lastestSignoff);
		return (List<Signoff>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Signoff> findByTargetKeyAndClassAndLastestAndRole(Long targetKey, String targetClass, boolean lastestSignoff, Long roleId) throws Exception {
	 	String hql = "select o from Signoff o left join fetch o.user u where o.targetKey = :targetKey and o.targetClass = :targetClass and o.lastestSignoff = :lastestSignoff and o.role.id = :roleId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("lastestSignoff", lastestSignoff);
	    parameters.put("roleId", roleId);
		return (List<Signoff>)super.getList(hql, parameters);
	}

}