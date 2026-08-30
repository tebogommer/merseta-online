package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;

import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class TrainingProviderSkillsProgrammeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingProviderSkillsProgramme
 	 * @author TechFinium 
 	 * @see    TrainingProviderSkillsProgramme
 	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> allTrainingProviderSkillsProgramme() throws Exception {
		return (List<TrainingProviderSkillsProgramme>)super.getList("select o from TrainingProviderSkillsProgramme o");
	}

	/**
	 * Get all TrainingProviderSkillsProgramme between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TrainingProviderSkillsProgramme
 	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> allTrainingProviderSkillsProgramme(Long from, int noRows) throws Exception {
	 	String hql = "select o from TrainingProviderSkillsProgramme o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TrainingProviderSkillsProgramme>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TrainingProviderSkillsProgramme
 	 * @return a {@link haj.com.entity.TrainingProviderSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	public TrainingProviderSkillsProgramme findByKey(Long id) throws Exception {
	 	String hql = "select o from TrainingProviderSkillsProgramme o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TrainingProviderSkillsProgramme)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingProviderSkillsProgramme by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TrainingProviderSkillsProgramme
  	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> findByName(String description) throws Exception {
	 	String hql = "select o from TrainingProviderSkillsProgramme o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TrainingProviderSkillsProgramme>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> findByTrainingProvider(Long id) throws Exception{
		String hql = "select o from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<TrainingProviderSkillsProgramme>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> findByTrainingProviderAndAccept(Long id,boolean accept) throws Exception{
		String hql = "select o from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.id = :id and  o.accept =:accept " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
	    parameters.put("accept", accept);
		return (List<TrainingProviderSkillsProgramme>)super.getList(hql, parameters);
	}
	

	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> findByTargetClassAndTargetKey(String targetClass,Long targetKey) throws Exception{
		String hql = "select o from TrainingProviderSkillsProgramme o where o.targetKey = :targetKey and o.targetClass = :targetClass " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return (List<TrainingProviderSkillsProgramme>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> findByTrainingProviderAndSkillsProgAndAccept(Long tpId,Long spId,boolean accept) throws Exception{
		String hql = "select o from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.id = :id and  o.accept =:accept and o.skillsProgram.id =:spId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", spId);
	    parameters.put("spId", spId);
	    parameters.put("accept", accept);
		return (List<TrainingProviderSkillsProgramme>)super.getList(hql, parameters);
	}
	
	

	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> findByTargetClassAndTargetKeyAndAccept(String targetClass,Long targetKey,Boolean accept) throws Exception{
		String hql = "select o from TrainingProviderSkillsProgramme o where o.targetKey = :targetKey and o.targetClass = :targetClass and o.accept =:accept" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("accept", accept);
		return (List<TrainingProviderSkillsProgramme>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> findByCompany(Long id) {
		String hql = "select o from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.company.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<TrainingProviderSkillsProgramme>)super.getList(hql, parameters);
	}
	
	public Integer countfindByCompany(TrainingProviderApplication trainingProviderApplication, SkillsProgram skillsProgram, Boolean accept) throws Exception {
	 	String hql = "select count(o) from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.id = :trainingProviderApplicationId and o.skillsProgram.id =:skillsProgramID and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingProviderApplicationId", trainingProviderApplication.getId());
	    parameters.put("skillsProgramID", skillsProgram.getId());
	    parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countfindByCompany(Company company, SkillsProgram skillsProgram, Boolean accept) throws Exception {
	 	String hql = "select count(o) from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.company.id = :companyID and o.skillsProgram.id =:skillsProgramID and o.accept =:accept";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyID", company.getId());
	    parameters.put("skillsProgramID", skillsProgram.getId());
	    parameters.put("accept", accept);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationBySP(Long skillsProgramID, Boolean accept) {
		String hql = "select o.trainingProviderApplication from TrainingProviderSkillsProgramme o where o.skillsProgram.id = :skillsProgramID and o.accept = :accept ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("skillsProgramID", skillsProgramID);
	    parameters.put("accept", accept);
		return (List<TrainingProviderApplication>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationBySP(Long skillsProgramID, Boolean accept, ApprovalEnum approvalStatus) {
		String hql = "select o.trainingProviderApplication from TrainingProviderSkillsProgramme o where o.skillsProgram.id = :skillsProgramID and o.accept = :accept and o.trainingProviderApplication.approvalStatus = :approvalStatus";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("skillsProgramID", skillsProgramID);
	    parameters.put("accept", accept);
	    parameters.put("accept", accept);
	    parameters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>)super.getList(hql, parameters);
	}
	
	public Integer countfindBySP(Long skillsProgramID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {
	 	String hql = "select count(o) from TrainingProviderSkillsProgramme o where o.skillsProgram.id = :skillsProgramID and o.accept = :accept and o.trainingProviderApplication.approvalStatus = :approvalStatus";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsProgramID", skillsProgramID);
	    parameters.put("accept", accept);
	    parameters.put("approvalStatus", approvalStatus);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countfindBySP(Long companyID,Long skillsProgramID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {
	 	String hql = "select count(o) from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.company.id = :companyID and o.skillsProgram.id = :skillsProgramID and o.accept = :accept and o.trainingProviderApplication.approvalStatus = :approvalStatus";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsProgramID", skillsProgramID);
	    parameters.put("accept", accept);
	    parameters.put("approvalStatus", approvalStatus);
	    parameters.put("companyID", companyID);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByTrainingProviderApplication(Long trainingProviderApplicationID,Long skillsProgramID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {
	 	String hql = "select count(o) from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.id = :trainingProviderApplicationID and o.skillsProgram.id = :skillsProgramID and o.accept = :accept and o.trainingProviderApplication.approvalStatus = :approvalStatus";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsProgramID", skillsProgramID);
	    parameters.put("accept", accept);
	    parameters.put("approvalStatus", approvalStatus);
	    parameters.put("trainingProviderApplicationID", trainingProviderApplicationID);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByApplicationAndSkillsProgramme(TrainingProviderApplication trainingProviderApplication, SkillsProgram skillsProgram) throws Exception {
		String hql = "select count(o) from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.id = :trainingProviderApplicationId and o.skillsProgram.id =:skillsProgramID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("trainingProviderApplicationId", trainingProviderApplication.getId());
		parameters.put("skillsProgramID", skillsProgram.getId());
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public List<?> sortAndFilterWhereTP(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
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
					hql += " and o.trainingProviderApplication. " + entry.getKey() + " like " + " :" + hvar;
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
	
	public int countWhereTP(Map<String, Object> filters, String hql) {
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
					hql += " and o.trainingProviderApplication. " + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
}

