package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.SkillsSet;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class TrainingProviderSkillsSetDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingProviderSkillsSet
 	 * @author TechFinium 
 	 * @see    TrainingProviderSkillsSet
 	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsSet}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> allTrainingProviderSkillsSet() throws Exception {
		return (List<TrainingProviderSkillsSet>)super.getList("select o from TrainingProviderSkillsSet o");
	}

	/**
	 * Get all TrainingProviderSkillsSet between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TrainingProviderSkillsSet
 	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsSet}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> allTrainingProviderSkillsSet(Long from, int noRows) throws Exception {
	 	String hql = "select o from TrainingProviderSkillsSet o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<TrainingProviderSkillsSet>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TrainingProviderSkillsSet
 	 * @return a {@link haj.com.entity.TrainingProviderSkillsSet}
 	 * @throws Exception global exception
 	 */
	public TrainingProviderSkillsSet findByKey(Long id) throws Exception {
	 	String hql = "select o from TrainingProviderSkillsSet o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TrainingProviderSkillsSet)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingProviderSkillsSet by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TrainingProviderSkillsSet
  	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsSet}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> findByName(String description) throws Exception {
	 	String hql = "select o from TrainingProviderSkillsSet o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TrainingProviderSkillsSet>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> findByTrainingProvider(Long id) throws Exception {
		String hql = "select o from TrainingProviderSkillsSet o where o.trainingProviderApplication.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<TrainingProviderSkillsSet>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> findByTrainingProviderAndAccept(Long id,Boolean accept) throws Exception {
		String hql = "select o from TrainingProviderSkillsSet o where o.trainingProviderApplication.id = :id and o.accept =:accept " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
	    parameters.put("accept", accept);
		return (List<TrainingProviderSkillsSet>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> findByTargetClassAndTargetKey(String targetClass,Long targetKey) throws Exception {
		String hql = "select o from TrainingProviderSkillsSet o where o.targetKey = :targetKey and o.targetClass = :targetClass " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return (List<TrainingProviderSkillsSet>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> findByTargetClassAndTargetKeyAndAccept(String targetClass,Long targetKey,Boolean accept) throws Exception {
		String hql = "select o from TrainingProviderSkillsSet o where o.targetKey = :targetKey and o.targetClass = :targetClass and o.accept =:accept " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("accept", accept);
		return (List<TrainingProviderSkillsSet>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> findByCompany(Long id) throws Exception {
		String hql = "select o from TrainingProviderSkillsSet o where o.trainingProviderApplication.company.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<TrainingProviderSkillsSet>)super.getList(hql, parameters);
	}
	
	public Integer countfindByCompany(TrainingProviderApplication trainingProviderApplication, SkillsSet skillsSet, Boolean accept) throws Exception {
	 	String hql = "select count(o) from TrainingProviderSkillsSet o where o.trainingProviderApplication.id = :trainingProviderApplicationId and o.SkillsSet.id =:skillsSetID and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingProviderApplicationId", trainingProviderApplication.getId());
	    parameters.put("skillsSetID", skillsSet.getId());
	    parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countfindByCompany(Company company, SkillsSet skillsSet, Boolean accept) throws Exception {
	 	String hql = "select count(o) from TrainingProviderSkillsSet o where o.trainingProviderApplication.company.id = :companyID and o.SkillsSet.id =:skillsSetID and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyID", company.getId());
	    parameters.put("skillsSetID", skillsSet.getId());
	    parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationBySS(Long skillsSetID, Boolean accept) {
		String hql = "select o.trainingProviderApplication from TrainingProviderSkillsSet o where o.SkillsSet.id = :skillsSetID and o.accept = :accept ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsSetID", skillsSetID);
	    parameters.put("accept", accept);
	    return (List<TrainingProviderApplication>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationBySS(Long skillsSetID, Boolean accept, ApprovalEnum approvalStatus) {
		String hql = "select o.trainingProviderApplication from TrainingProviderSkillsSet o where o.SkillsSet.id = :skillsSetID and o.accept = :accept and o.trainingProviderApplication.approvalStatus = :approvalStatus ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsSetID", skillsSetID);
	    parameters.put("accept", accept);
	    parameters.put("approvalStatus", approvalStatus);
	    return (List<TrainingProviderApplication>)super.getList(hql, parameters);
	}
	
	public Integer countfindBySS(Long skillsSetID ,Boolean accept, ApprovalEnum approvalStatus) throws Exception {
	 	String hql = "select count(o.trainingProviderApplication) from TrainingProviderSkillsSet o where o.SkillsSet.id = :skillsSetID and o.accept = :accept and o.trainingProviderApplication.approvalStatus = :approvalStatus";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsSetID", skillsSetID);
	    parameters.put("accept", accept);
	    parameters.put("approvalStatus", approvalStatus);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countfindBySS(Long companyID,Long skillsSetID ,Boolean accept, ApprovalEnum approvalStatus) throws Exception {
	 	String hql = "select count(o.trainingProviderApplication) from TrainingProviderSkillsSet o where o.trainingProviderApplication.company.id = :companyID and o.SkillsSet.id = :skillsSetID and o.accept = :accept and o.trainingProviderApplication.approvalStatus = :approvalStatus";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsSetID", skillsSetID);
	    parameters.put("accept", accept);
	    parameters.put("approvalStatus", approvalStatus);
	    parameters.put("companyID", companyID);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByTrainingProviderApplication(Long trainingProviderApplicationID,Long skillsSetID ,Boolean accept, ApprovalEnum approvalStatus) throws Exception {
	 	String hql = "select count(o.trainingProviderApplication) from TrainingProviderSkillsSet o where o.trainingProviderApplication.id = :trainingProviderApplicationID and o.SkillsSet.id = :skillsSetID and o.accept = :accept and o.trainingProviderApplication.approvalStatus = :approvalStatus";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsSetID", skillsSetID);
	    parameters.put("accept", accept);
	    parameters.put("approvalStatus", approvalStatus);
	    parameters.put("trainingProviderApplicationID", trainingProviderApplicationID);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByApplicationAndSkillsSet(TrainingProviderApplication trainingProviderApplication, SkillsSet skillsSet) throws Exception {
	 	String hql = "select count(o) from TrainingProviderSkillsSet o where o.trainingProviderApplication.id = :trainingProviderApplicationId and o.SkillsSet.id =:skillsSetID";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingProviderApplicationId", trainingProviderApplication.getId());
	    parameters.put("skillsSetID", skillsSet.getId());
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

	public List<?> sortAndFilterWhereSP(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
		if (filters != null) {
			// boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					hql += " and o.trainingProviderApplication." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o." + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}
	
	public int countWhereSP(Map<String, Object> filters, String hql) {
		if (filters != null) {
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					hql += " and o.trainingProviderApplication." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
}

