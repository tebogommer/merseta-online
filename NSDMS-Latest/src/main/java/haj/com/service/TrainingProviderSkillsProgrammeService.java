package haj.com.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import haj.com.dao.TrainingProviderSkillsProgrammeDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.Doc;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.framework.AbstractService;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.primefaces.model.SortOrder;

public class TrainingProviderSkillsProgrammeService extends AbstractService {
	/** The dao. */
	private TrainingProviderSkillsProgrammeDAO dao = new TrainingProviderSkillsProgrammeDAO();
	
	private static TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService = null;
	public static synchronized TrainingProviderSkillsProgrammeService instance() {
		if (trainingProviderSkillsProgrammeService == null) {
			trainingProviderSkillsProgrammeService = new TrainingProviderSkillsProgrammeService();
		}
		return trainingProviderSkillsProgrammeService;
	}

	/**
	 * Get all TrainingProviderSkillsProgramme
 	 * @author TechFinium 
 	 * @see   TrainingProviderSkillsProgramme
 	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsProgramme}
	 * @throws Exception the exception
 	 */
	public List<TrainingProviderSkillsProgramme> allTrainingProviderSkillsProgramme() throws Exception {
	  	return dao.allTrainingProviderSkillsProgramme();
	}


	/**
	 * Create or update TrainingProviderSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TrainingProviderSkillsProgramme
	 */
	public void create(TrainingProviderSkillsProgramme entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TrainingProviderSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingProviderSkillsProgramme
	 */
	public void update(TrainingProviderSkillsProgramme entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TrainingProviderSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingProviderSkillsProgramme
	 */
	public void delete(TrainingProviderSkillsProgramme entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TrainingProviderSkillsProgramme}
	 * @throws Exception the exception
	 * @see    TrainingProviderSkillsProgramme
	 */
	public TrainingProviderSkillsProgramme findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TrainingProviderSkillsProgramme by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsProgramme}
	 * @throws Exception the exception
	 * @see    TrainingProviderSkillsProgramme
	 */
	public List<TrainingProviderSkillsProgramme> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TrainingProviderSkillsProgramme
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsProgramme}
	 * @throws Exception the exception
	 */
	public List<TrainingProviderSkillsProgramme> allTrainingProviderSkillsProgramme(int first, int pageSize) throws Exception {
		return dao.allTrainingProviderSkillsProgramme(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TrainingProviderSkillsProgramme for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the TrainingProviderSkillsProgramme
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TrainingProviderSkillsProgramme.class);
	}
	
    /**
     * Lazy load TrainingProviderSkillsProgramme with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 TrainingProviderSkillsProgramme class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TrainingProviderSkillsProgramme} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> allTrainingProviderSkillsProgramme(Class<TrainingProviderSkillsProgramme> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TrainingProviderSkillsProgramme>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TrainingProviderSkillsProgramme for lazy load with filters
     * @author TechFinium 
     * @param entity TrainingProviderSkillsProgramme class
     * @param filters the filters
     * @return Number of rows in the TrainingProviderSkillsProgramme entity
     * @throws Exception the exception     
     */	
	public int count(Class<TrainingProviderSkillsProgramme> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<TrainingProviderSkillsProgramme> findByTrainingProvider(Long id) throws Exception {
		// TODO Auto-generated method stub
		return populateAdditionalInformationList(dao.findByTrainingProvider(id));
	}
	
	public List<TrainingProviderSkillsProgramme> findByTrainingProviderAndAccept(Long id,Boolean accept) throws Exception {
		// TODO Auto-generated method stub
		return populateAdditionalInformationList(dao.findByTrainingProviderAndAccept(id,accept));
	}
	
	public List<TrainingProviderSkillsProgramme> findByTrainingProviderAndSkillsProgAndAccept(Long tpId,Long ssId,Boolean accept) throws Exception {
		// TODO Auto-generated method stub
		return dao.findByTrainingProviderAndSkillsProgAndAccept(tpId,ssId,accept);
	}
	
	public List<TrainingProviderSkillsProgramme> findByTargetClassAndTargetKey(String targetClass,Long targetKey) throws Exception{
		return populateAdditionalInformationList(dao.findByTargetClassAndTargetKey(targetClass, targetKey));
	}
	
	
	public List<TrainingProviderSkillsProgramme> findByTargetClassAndTargetKeyAndAccept(String targetClass,Long targetKey,Boolean accept) throws Exception{
		return dao.findByTargetClassAndTargetKeyAndAccept(targetClass, targetKey,accept);
	}
	
	public List<TrainingProviderSkillsProgramme> findByCompany(Long id) throws Exception {
		// TODO Auto-generated method stub
		return dao.findByCompany(id);
	}
	
	public Integer countfindByCompany(TrainingProviderApplication trainingProviderApplication, SkillsProgram skillsProgram, Boolean accept) throws Exception {
		return dao.countfindByCompany(trainingProviderApplication, skillsProgram, accept);
	}
	
	public Integer countfindByCompany(Company company, SkillsProgram skillsProgram, Boolean accept) throws Exception {
		return dao.countfindByCompany(company, skillsProgram, accept);
	}
	
	public List<TrainingProviderSkillsProgramme> populateAdditionalInformationList(List<TrainingProviderSkillsProgramme> trainingProviderSkillsProgrammeList) throws Exception{
		for (TrainingProviderSkillsProgramme trainingProviderSkillsProgramme : trainingProviderSkillsProgrammeList) {
			populateAdditionalInformation(trainingProviderSkillsProgramme);
		}
		return trainingProviderSkillsProgrammeList;
	}
	
	public TrainingProviderSkillsProgramme populateAdditionalInformation(TrainingProviderSkillsProgramme trainingProviderSkillsProgramme) throws Exception{
		if (trainingProviderSkillsProgramme.getId() != null) {
			// will need to change
			trainingProviderSkillsProgramme.setDocList(new ArrayList<Doc>());
			if (trainingProviderSkillsProgramme.getTrainingProviderDocParent() != null && trainingProviderSkillsProgramme.getTrainingProviderDocParent().getDoc() != null && trainingProviderSkillsProgramme.getTrainingProviderDocParent().getDoc().getId() != null) {
				trainingProviderSkillsProgramme.getDocList().add(DocService.instance().findByKeyWithJoins(trainingProviderSkillsProgramme.getTrainingProviderDocParent().getDoc().getId()));
			}
			
		}
		return trainingProviderSkillsProgramme;
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplication(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long skillsProgramID, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o.trainingProviderApplication from TrainingProviderSkillsProgramme o where o.skillsProgram.id = :skillsProgramID and o.accept = true and o.trainingProviderApplication.approvalStatus = :approvalStatus ";
		filters.put("skillsProgramID", skillsProgramID);
		filters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhereTP(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplication(Map<String, Object> filters) throws Exception {
		String hql = "select count(o.trainingProviderApplication) from TrainingProviderSkillsProgramme o where o.skillsProgram.id = :skillsProgramID and o.accept = true and o.trainingProviderApplication.approvalStatus = :approvalStatus";
		return dao.countWhereTP(filters, hql);
	}
	
	public List<TrainingProviderApplication> allTrainingProviderApplicationBySP(Long skillsProgramID, Boolean accept) throws Exception {		
		return dao.allTrainingProviderApplicationBySP(skillsProgramID, accept);
	}
	
	public List<TrainingProviderApplication> allTrainingProviderApplicationBySP(Long skillsProgramID, Boolean accept, ApprovalEnum approvalEnum) throws Exception {		
		return dao.allTrainingProviderApplicationBySP(skillsProgramID, accept, approvalEnum);
	}
	
	public Integer countfindBySP(Long skillsProgramID, Boolean accept, ApprovalEnum approvalEnum) throws Exception {
		return dao.countfindBySP(skillsProgramID, accept, approvalEnum);
	}
	public Integer countfindBySP(Long companyID,Long skillsProgramID, Boolean accept, ApprovalEnum approvalEnum) throws Exception {
		return dao.countfindBySP(companyID,skillsProgramID, accept, approvalEnum);
	}
	
	public Integer countByTrainingProviderApplication(Long trainingProviderApplicationID,Long skillsProgramID, Boolean accept, ApprovalEnum approvalEnum) throws Exception {
		return dao.countByTrainingProviderApplication(trainingProviderApplicationID,skillsProgramID, accept, approvalEnum);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> allTrainingProviderSkillsProgrammeByProviderApplicationId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long providerApplicationId) throws Exception {
		String hql="select o from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.id = :providerApplicationId  ";
		filters.put("providerApplicationId", providerApplicationId);
		return (List<TrainingProviderSkillsProgramme>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllTrainingProviderSkillsProgrammeByProviderApplicationId(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.id = :providerApplicationId ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsProgramme> allTrainingProviderSkillsProgrammeByProviderApplicationIdAndManuallyEntered(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long providerApplicationId, Boolean manuallyAdded) throws Exception {
		String hql="select o from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.id = :providerApplicationId and o.manuallyAdded = :manuallyAdded";
		filters.put("providerApplicationId", providerApplicationId);
		filters.put("manuallyAdded", manuallyAdded);
		return (List<TrainingProviderSkillsProgramme>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllTrainingProviderSkillsProgrammeByProviderApplicationIdAndManuallyEntered(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from TrainingProviderSkillsProgramme o where o.trainingProviderApplication.id = :providerApplicationId and o.manuallyAdded = :manuallyAdded";
		return dao.countWhere(filters, hql);
	}
	
	public Integer countByApplicationAndSkillsProgramme(TrainingProviderApplication trainingProviderApplication, SkillsProgram skillsProgram) throws Exception {
		return dao.countByApplicationAndSkillsProgramme(trainingProviderApplication, skillsProgram);
	}
}
