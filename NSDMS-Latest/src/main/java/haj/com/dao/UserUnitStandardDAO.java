package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class UserUnitStandardDAO.
 */
public class UserUnitStandardDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UserUnitStandard.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception global exception
	 * @see    UserUnitStandard
	 */
	@SuppressWarnings("unchecked")
	public List<UserUnitStandard> allUserUnitStandard() throws Exception {
		return (List<UserUnitStandard>)super.getList("select o from UserUnitStandard o");
	}

	/**
	 * Get all UserUnitStandard between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception global exception
	 * @see    UserUnitStandard
	 */
	@SuppressWarnings("unchecked")
	public List<UserUnitStandard> allUserUnitStandard(Long from, int noRows) throws Exception {
	 	String hql = "select o from UserUnitStandard o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UserUnitStandard>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception global exception
	 * @see    UserUnitStandard
	 */
	public UserUnitStandard findByKey(Long id) throws Exception {
	 	String hql = "select o from UserUnitStandard o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UserUnitStandard)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UserUnitStandard by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception global exception
	 * @see    UserUnitStandard
	 */
	@SuppressWarnings("unchecked")
	public List<UserUnitStandard> findByName(String description) throws Exception {
	 	String hql = "select o from UserUnitStandard o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UserUnitStandard>)super.getList(hql, parameters);
	}
	
	/**
	 * Find UserUnitStandard by user.
	 *
	 * @author TechFinium
	 * @param uid the uid
	 * @return a list of {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception global exception
	 * @see    UserUnitStandard
	 */
	@SuppressWarnings("unchecked")
	public List<UserUnitStandard> findByUser(Long uid) throws Exception {
	 	String hql = "select o from UserUnitStandard o left join fetch o.unitStandard where o.user.id = :uid" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("uid", uid);
		return (List<UserUnitStandard>)super.getList(hql, parameters);
	}
	
	/**
	 * Find UserUnitStandard by user and forAssessorModeratorApplication.
	 *
	 * @author TechFinium
	 * @param uid the uid
	 * @return a list of {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception global exception
	 * @see    UserUnitStandard
	 */
	@SuppressWarnings("unchecked")
	public List<UserUnitStandard> findByUserAndAPApplication(Long uid,Long amAppID ) throws Exception {
	 	String hql = "select o from UserUnitStandard o left join fetch o.unitStandard where o.user.id = :uid and o.forAssessorModeratorApplication.id =:amAppID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("uid", uid);
	    parameters.put("amAppID", amAppID);
		return (List<UserUnitStandard>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserUnitStandard> findByUnitStandardAndAPApplication(Long unitStandardID,Long assessorModeratorApplicationID ) throws Exception {
	 	String hql = "select o from UserUnitStandard o where o.unitStandard.id = :unitStandardID and o.forAssessorModeratorApplication.id =:assessorModeratorApplicationID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("unitStandardID", unitStandardID);
	    parameters.put("assessorModeratorApplicationID", assessorModeratorApplicationID);
		return (List<UserUnitStandard>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserUnitStandard> findByUserAndAPApplicationAndAccept(Long uid,Long amAppID,Boolean accept ) throws Exception {
	 	String hql = "select o from UserUnitStandard o left join fetch o.unitStandard where o.user.id = :uid and o.forAssessorModeratorApplication.id =:amAppID and o.accept = :accept" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("uid", uid);
	    parameters.put("amAppID", amAppID);
	    parameters.put("accept", accept);
		return (List<UserUnitStandard>)super.getList(hql, parameters);
	}
	
	
	
	/**
	 *  
	 * Locates UserUnitStandard by userId and unitStandardId.
	 *
	 * @param userId the user id
	 * @param unitStandardId the unit standard id
	 * @return UserUnitStandard
	 * @throws Exception the exception
	 */
	public UserUnitStandard findByUserAndUnitStandard(Long userId, Long unitStandardId) throws Exception {
	 	String hql = "select o from UserUnitStandard o left join fetch o.unitStandard where o.user.id = :userId and o.unitStandard.id = :unitStandardId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
	    parameters.put("unitStandardId", unitStandardId);
	    return (UserUnitStandard)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Finds AssessorModeratorApplication by user and unitStandard ID.
	 *
	 * @param uid the uid
	 * @param appType 
	 * @param qualificationId the qualification id
	 * @return AssessorModeratorApplication
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findAMApplicationByUserAndUS(Long uid, Long unitStandardId, AssessorModeratorAppTypeEnum appType) throws Exception {
	 	String hql = "select distinct o.forAssessorModeratorApplication from UserUnitStandard o where o.user.id = :uid and o.unitStandard.id = :qualificationId and o.forAssessorModeratorApplication.status <>:status and o.forAssessorModeratorApplication.applicationType =:applicationType" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("uid", uid);
	    parameters.put("status", ApprovalEnum.Rejected);
	    parameters.put("qualificationId", unitStandardId);
	    parameters.put("applicationType", appType);
	    return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findAMApplicationByUserUSAndAccept(Long uid, Long unitStandardId, AssessorModeratorAppTypeEnum appType,Boolean accept) throws Exception {
	 	String hql = "select distinct o.forAssessorModeratorApplication from UserUnitStandard o where o.user.id = :uid and o.unitStandard.id = :qualificationId and o.forAssessorModeratorApplication.status <>:status and o.forAssessorModeratorApplication.applicationType =:applicationType and o.accept = :accept" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("uid", uid);
	    parameters.put("status", ApprovalEnum.Rejected);
	    parameters.put("qualificationId", unitStandardId);
	    parameters.put("applicationType", appType);
	    parameters.put("accept", accept);
	    return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findAMApplicationByUserUSAppTypeAndAccept(Long uid, Long unitStandardId, AssessorModeratorAppTypeEnum appType,Boolean accept) throws Exception {
	 	String hql = "select distinct o.forAssessorModeratorApplication from UserUnitStandard o where o.user.id = :uid and o.unitStandard.id = :qualificationId and o.forAssessorModeratorApplication.status <>:status and o.forAssessorModeratorApplication.applicationType =:applicationType and o.accept = :accept" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("uid", uid);
	    parameters.put("status", ApprovalEnum.Rejected);
	    parameters.put("qualificationId", unitStandardId);
	    parameters.put("applicationType", appType);
	    parameters.put("accept", accept);
	    return (List<AssessorModeratorApplication>)super.getList(hql, parameters);
	}
	
	
	
	public List<UnitStandards> findUnitStandardByAMApplication(Long id) throws Exception {
	 	String hql = "select distinct o.unitStandard from UserUnitStandard o where  o.forAssessorModeratorApplication.id = :id" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
	    return (List<UnitStandards>)super.getList(hql, parameters);
	}

	public List<UserUnitStandard> findByTargetKeyAndTargetClas(Long id, String targetClass) {
		String hql = "select o from UserUnitStandard o left join fetch o.unitStandard where o.targetKey = :targetKey and o.targetClass = :targetClass " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", id);
	    parameters.put("targetClass", targetClass);
		return (List<UserUnitStandard>)super.getList(hql, parameters);
	}
}

