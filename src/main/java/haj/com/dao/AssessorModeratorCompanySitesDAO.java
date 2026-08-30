package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.entity.enums.AssessorModType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class AssessorModeratorCompanySitesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AssessorModeratorCompanySites
 	 * @author TechFinium 
 	 * @see    AssessorModeratorCompanySites
 	 * @return a list of {@link haj.com.entity.AssessorModeratorCompanySites}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> allAssessorModeratorCompanySites() throws Exception {
		return (List<AssessorModeratorCompanySites>)super.getList("select o from AssessorModeratorCompanySites o");
	}

	/**
	 * Get all AssessorModeratorCompanySites between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AssessorModeratorCompanySites
 	 * @return a list of {@link haj.com.entity.AssessorModeratorCompanySites}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> allAssessorModeratorCompanySites(Long from, int noRows) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AssessorModeratorCompanySites>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AssessorModeratorCompanySites
 	 * @return a {@link haj.com.entity.AssessorModeratorCompanySites}
 	 * @throws Exception global exception
 	 */
	public AssessorModeratorCompanySites findByKey(Long id) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AssessorModeratorCompanySites)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AssessorModeratorCompanySites by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AssessorModeratorCompanySites
  	 * @return a list of {@link haj.com.entity.AssessorModeratorCompanySites}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByName(String description) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AssessorModeratorCompanySites>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByCompany(Long companyId) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o where o.company.id = :companyId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
		return (List<AssessorModeratorCompanySites>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByCompanyUserType(Long companyId, Long userId, AssessorModType assessorModType) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o where o.company.id = :companyId and o.assessorModerator.id = :userId and o.assessorModType = :assessorModType" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("userId", userId);
	    parameters.put("assessorModType", assessorModType);
		return (List<AssessorModeratorCompanySites>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByHoldingCompany(Long companyId) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o where o.company.id = :companyId and o.trainingSite is null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
		return (List<AssessorModeratorCompanySites>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByUserHoldingCompany(Long userId, Long companyId) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o where o.assessorModerator.id = :userId and o.company.id = :companyId and o.trainingSite is null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("userId", userId);
		return (List<AssessorModeratorCompanySites>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByUserHoldingCompanyAssModType(Long userId, Long companyId, AssessorModType assessorModType) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o where o.assessorModType = :assessorModType and o.assessorModerator.id = :userId and o.company.id = :companyId and o.trainingSite is null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("userId", userId);
	    parameters.put("assessorModType", assessorModType);
		return (List<AssessorModeratorCompanySites>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByUserHoldingCompanyAssModTypeList(Long userId, Long companyId, List<AssessorModType> assessorModTypeList) throws Exception {
		StringBuilder hql = new StringBuilder();
	 	hql.append("select o from AssessorModeratorCompanySites o where o.assessorModerator.id = :userId and o.company.id = :companyId and o.trainingSite is null ");
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("userId", userId);
	    if (assessorModTypeList == null || assessorModTypeList.isEmpty()) {
			throw new Exception("System Error. One entry of assessor / moderator type must be provided. Contact support!");
		} else {
			hql.append(" and o.assessorModType in ( ");
			int counter = 1;
			for (AssessorModType assessorModType : assessorModTypeList) {
				hql.append(" :assessorModType" + counter);
				 parameters.put("assessorModType" +counter, assessorModType);
				if (counter != assessorModTypeList.size()) {
					hql.append(" , ");
				}
				counter++;
			}
			hql.append(" ) ");
		}
		return (List<AssessorModeratorCompanySites>)super.getList(hql.toString(), parameters);
	}
	
	public Integer countByUserHoldingCompanyAssModType(Long userId, Long companyId, AssessorModType assessorModType) throws Exception {
	 	String hql = "select count(o) from AssessorModeratorCompanySites o where o.assessorModType = :assessorModType and o.assessorModerator.id = :userId and o.company.id = :companyId and o.trainingSite is null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("userId", userId);
	    parameters.put("assessorModType", assessorModType);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByUserHoldingCompanyAssModTypeList(Long userId, Long companyId, List<AssessorModType> assessorModTypeList) throws Exception {
		StringBuilder hql = new StringBuilder();
	 	hql.append("select count(o) from AssessorModeratorCompanySites o where o.assessorModerator.id = :userId and o.company.id = :companyId and o.trainingSite is null ");
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("userId", userId);
	    if (assessorModTypeList == null || assessorModTypeList.isEmpty()) {
			throw new Exception("System Error. One entry of assessor / moderator type must be provided. Contact support!");
		} else {
			hql.append(" and o.assessorModType in ( ");
			int counter = 1;
			for (AssessorModType type : assessorModTypeList) {
				hql.append(" :assessorModType" + counter);
				 parameters.put("assessorModType" +counter, type);
				if (counter != assessorModTypeList.size()) {
					hql.append(" , ");
				}
				counter++;
			}
			hql.append(" ) ");
		}
		return ((Long) super.getUniqueResult(hql.toString(), parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByTrainingSite(Long trainingSiteId) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o where o.trainingSite.id = :trainingSiteId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
		return (List<AssessorModeratorCompanySites>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByUserTrainingSite(Long userId, Long trainingSiteId) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o where o.assessorModerator.id = :userId and o.trainingSite.id = :trainingSiteId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
	    parameters.put("userId", userId);
		return (List<AssessorModeratorCompanySites>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByUserTrainingSiteAssModType(Long userId, Long trainingSiteId, AssessorModType assessorModType) throws Exception {
	 	String hql = "select o from AssessorModeratorCompanySites o where o.assessorModType = :assessorModType and o.assessorModerator.id = :userId and o.trainingSite.id = :trainingSiteId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
	    parameters.put("userId", userId);
	    parameters.put("assessorModType", assessorModType);
		return (List<AssessorModeratorCompanySites>)super.getList(hql, parameters);
	}
	
	public Integer countByUserTrainingSiteAssModType(Long userId, Long trainingSiteId, AssessorModType assessorModType) throws Exception {
	 	String hql = "select count(o) from AssessorModeratorCompanySites o where o.assessorModType = :assessorModType and o.assessorModerator.id = :userId and o.trainingSite.id = :trainingSiteId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
	    parameters.put("userId", userId);
	    parameters.put("assessorModType", assessorModType);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> findByUserTrainingSiteAssModTypeList(Long userId, Long trainingSiteId, List<AssessorModType> assessorModTypeList) throws Exception {
		StringBuilder hql = new StringBuilder();
	 	hql.append("select o from AssessorModeratorCompanySites o where o.assessorModerator.id = :userId and o.trainingSite.id = :trainingSiteId ");
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
	    parameters.put("userId", userId);
	    if (assessorModTypeList == null || assessorModTypeList.isEmpty()) {
			throw new Exception("System Error. One entry of assessor / moderator type must be provided. Contact support!");
		} else {
			hql.append(" and o.assessorModType in ( ");
			int counter = 1;
			for (AssessorModType type : assessorModTypeList) {
				hql.append(" :assessorModType" + counter);
				parameters.put("assessorModType" +counter, type);
				if (counter != assessorModTypeList.size()) {
					hql.append(" , ");
				}
				counter++;
			}
			hql.append(" ) ");
		}
		return (List<AssessorModeratorCompanySites>)super.getList(hql.toString(), parameters);
	}
	
	public Integer countByUserTrainingSiteAssModTypeList(Long userId, Long trainingSiteId, List<AssessorModType> assessorModTypeList) throws Exception {
		StringBuilder hql = new StringBuilder();
	 	hql.append("select count(o) from AssessorModeratorCompanySites o where o.assessorModerator.id = :userId and o.trainingSite.id = :trainingSiteId ");
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingSiteId", trainingSiteId);
	    parameters.put("userId", userId);
	    if (assessorModTypeList == null || assessorModTypeList.isEmpty()) {
			throw new Exception("System Error. One entry of assessor / moderator type must be provided. Contact support!");
		} else {
			hql.append(" and o.assessorModType in ( ");
			int counter = 1;
			for (AssessorModType type : assessorModTypeList) {
				hql.append(" :assessorModType" + counter);
				parameters.put("assessorModType" +counter, type);
				if (counter != assessorModTypeList.size()) {
					hql.append(" , ");
				}
				counter++;
			}
			hql.append(" ) ");
		}
	    return ((Long) super.getUniqueResult(hql.toString(), parameters)).intValue();
	}
	
}