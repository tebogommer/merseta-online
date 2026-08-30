package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.UserQualifications;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class UserQualificationsDAO.
 */
public class UserQualificationsDAO extends AbstractDAO  {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " + "left join fetch o.user left join fetch o.qualification x left join fetch x.nqfLevel left join fetch o.qualificationType" + " ";
	
	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UserQualifications.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UserQualifications}
	 * @throws Exception global exception
	 * @see    UserQualifications
	 */
	@SuppressWarnings("unchecked")
	public List<UserQualifications> allUserQualifications() throws Exception {
		return (List<UserQualifications>)super.getList("select o from UserQualifications o");
	}

	/**
	 * Get all UserQualifications between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.UserQualifications}
	 * @throws Exception global exception
	 * @see    UserQualifications
	 */
	@SuppressWarnings("unchecked")
	public List<UserQualifications> allUserQualifications(Long from, int noRows) throws Exception {
	 	String hql = "select o from UserQualifications o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UserQualifications>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserQualifications}
	 * @throws Exception global exception
	 * @see    UserQualifications
	 */
	public UserQualifications findByKey(Long id) throws Exception {
	 	String hql = "select o from UserQualifications o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UserQualifications)super.getUniqueResult(hql, parameters);
	}
	
	public UserQualifications findByKeyWithJoin(Long id) throws Exception {
	 	String hql = "select o from UserQualifications o left join fetch o.qualification where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (UserQualifications)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UserQualifications by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.UserQualifications}
	 * @throws Exception global exception
	 * @see    UserQualifications
	 */
	@SuppressWarnings("unchecked")
	public List<UserQualifications> findByName(String description) throws Exception {
	 	String hql = "select o from UserQualifications o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UserQualifications>)super.getList(hql, parameters);
	}
	
	/**
	 * Find UserQualifications by user.
	 *
	 * @author TechFinium
	 * @param userId the user id
	 * @return a list of {@link haj.com.entity.UserQualifications}
	 * @throws Exception global exception
	 * @see    UserQualifications
	 */
	@SuppressWarnings("unchecked")
	public List<UserQualifications> findByUser(Long userId) throws Exception {
	 	String hql = "select o from UserQualifications o left join fetch o.qualification where o.user.id = :userId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
		return (List<UserQualifications>)super.getList(hql, parameters);
	}
	
	
	/**
	 * Find UserQualifications by user and forAssessorModeratorApplication.
	 *
	 * @author TechFinium
	 * @param userId the user id
	 * @return a list of {@link haj.com.entity.UserQualifications}
	 * @throws Exception global exception
	 * @see    UserQualifications
	 */
	@SuppressWarnings("unchecked")
	public List<UserQualifications> findByUserAMApplication(Long userId,Long amApID) throws Exception {
	 	String hql = "select o from UserQualifications o left join fetch o.qualification where o.user.id = :userId and o.forAssessorModeratorApplication.id =:amApID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
	    parameters.put("amApID", amApID);
		return (List<UserQualifications>)super.getList(hql, parameters);
	} 
	
	@SuppressWarnings("unchecked")
	public List<UserQualifications> findByUserAMApplicationAndAccept(Long userId,Long amApID,Boolean accept) throws Exception {
	 	String hql = "select o from UserQualifications o left join fetch o.qualification where o.user.id = :userId and o.forAssessorModeratorApplication.id =:amApID and o.accept = :accept" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
	    parameters.put("amApID", amApID);
	    parameters.put("accept", accept);
		return (List<UserQualifications>)super.getList(hql, parameters);
	} 
	
	
	/**
	 * Finds UserQualifications by user and qualification ID.
	 *
	 * @param uid the uid
	 * @param qualificationId the qualification id
	 * @return UserQualifications
	 * @throws Exception the exception
	 */
	public UserQualifications findByUserAndQualification(Long uid, Long qualificationId) throws Exception {
	 	String hql = "select o from UserQualifications o left join fetch o.qualification where o.user.id = :uid and o.qualification.id = :qualificationId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("uid", uid);
	    parameters.put("qualificationId", qualificationId);
	    return (UserQualifications)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Finds AssessorModeratorApplication by user and qualification ID.
	 *
	 * @param uid the uid
	 * @param qualificationId the qualification id
	 * @param assessorModeratorAppTypeEnum 
	 * @return AssessorModeratorApplication
	 * @throws Exception the exception
	 */
	public List<AssessorModeratorApplication> findAMApplicationByUserAndQual(Long uid, Long qualificationId, AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum) throws Exception {
	 	String hql = "select distinct o.forAssessorModeratorApplication from UserQualifications o where o.user.id = :uid and o.qualification.id = :qualificationId and o.forAssessorModeratorApplication.status <>:status and  o.forAssessorModeratorApplication.applicationType =:applicationType " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("uid", uid);
	    parameters.put("status", ApprovalEnum.Rejected);
	    parameters.put("qualificationId", qualificationId);
	    parameters.put("applicationType", assessorModeratorAppTypeEnum);
	    return (List<AssessorModeratorApplication>) super.getList(hql, parameters);
	}
	
	public List<AssessorModeratorApplication> findAMApplicationByUserQualAppTypeAndAccept(Long uid, Long qualificationId, AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum,Boolean accept) throws Exception {
	 	String hql = "select distinct o.forAssessorModeratorApplication from UserQualifications o where o.user.id = :uid and o.qualification.id = :qualificationId and o.forAssessorModeratorApplication.status <>:status and  o.forAssessorModeratorApplication.applicationType =:applicationType and o.accept = :accept " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("uid", uid);
	    parameters.put("status", ApprovalEnum.Rejected);
	    parameters.put("qualificationId", qualificationId);
	    parameters.put("applicationType", assessorModeratorAppTypeEnum);
	    parameters.put("accept", accept);
	    return (List<AssessorModeratorApplication>) super.getList(hql, parameters);
	}
	
	
	
	
	
	/**
	 * Find by user id left join.
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<UserQualifications> findByUserIdLeftJoin(Long userId) throws Exception {
		String hql = "select o from UserQualifications o" + leftJoins + "where o.user.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		return (List<UserQualifications>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationByAMApplication(Long amApID) throws Exception {
	 	String hql = "select distinct o.qualification from UserQualifications o where o.forAssessorModeratorApplication.id =:amApID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("amApID", amApID);
		return (List<Qualification>)super.getList(hql, parameters);
	}

//	/**  target key for task. */
//	@Column(name = "target_key", nullable = true)
//	private Long targetKey;
//
//	/**  target class for task. */
//	@Column(name = "target_class", nullable = true)
//	private String targetClass;
	
	
	public List<UserQualifications> findByTargetKeyAndTargetClas(Long id, String targetClass) {
		String hql = "select o from UserQualifications o left join fetch o.qualification where o.targetKey =:targetKey and o.targetClass =:targetClass " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", id);
	    parameters.put("targetClass", targetClass);
		return (List<UserQualifications>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserQualifications> findUserQualificationByAssessModeratorAppllicationAndQualification(Long assessorModeratorApplicationId, Long qualificationId) throws Exception {
		String hql = "select o from UserQualifications o left join fetch o.qualification where o.forAssessorModeratorApplication.id = :assessorModeratorApplicationId "
				+ " and o.qualification.id = :qualificationId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("assessorModeratorApplicationId", assessorModeratorApplicationId);
	    parameters.put("qualificationId", qualificationId);
		return (List<UserQualifications>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserQualifications> findUserQualificationByAssessModeratorAppllication(Long assessorModeratorApplicationId) throws Exception {
		String hql = "select o from UserQualifications o left join fetch o.qualification where o.forAssessorModeratorApplication.id = :assessorModeratorApplicationId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("assessorModeratorApplicationId", assessorModeratorApplicationId);
		return (List<UserQualifications>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationByAssessModeratorAppllicationWhereApproved(Long assessorModeratorApplicationId, Boolean accept) throws Exception {
	 	String hql = "select distinct o.qualification from UserQualifications o where o.forAssessorModeratorApplication.id = :assessorModeratorApplicationId and o.accept = :accept" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("assessorModeratorApplicationId", assessorModeratorApplicationId);
	    parameters.put("accept", accept);
		return (List<Qualification>)super.getList(hql, parameters);
	}
	
}