package haj.com.service;

import java.util.ArrayList;
import java.util.List;

import haj.com.dao.TrainingProviderSkillsSetDAO;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.SkillsSet;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class TrainingProviderSkillsSetService extends AbstractService {
	/** The dao. */
	private TrainingProviderSkillsSetDAO dao = new TrainingProviderSkillsSetDAO();
	
	private static TrainingProviderSkillsSetService trainingProviderSkillsSetService = null;
	public static synchronized TrainingProviderSkillsSetService instance() {
		if (trainingProviderSkillsSetService == null) {
			trainingProviderSkillsSetService = new TrainingProviderSkillsSetService();
		}
		return trainingProviderSkillsSetService;
	}

	/**
	 * Get all TrainingProviderSkillsSet
 	 * @author TechFinium 
 	 * @see   TrainingProviderSkillsSet
 	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsSet}
	 * @throws Exception the exception
 	 */
	public List<TrainingProviderSkillsSet> allTrainingProviderSkillsSet() throws Exception {
	  	return dao.allTrainingProviderSkillsSet();
	}


	/**
	 * Create or update TrainingProviderSkillsSet.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TrainingProviderSkillsSet
	 */
	public void create(TrainingProviderSkillsSet entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TrainingProviderSkillsSet.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingProviderSkillsSet
	 */
	public void update(TrainingProviderSkillsSet entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TrainingProviderSkillsSet.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingProviderSkillsSet
	 */
	public void delete(TrainingProviderSkillsSet entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TrainingProviderSkillsSet}
	 * @throws Exception the exception
	 * @see    TrainingProviderSkillsSet
	 */
	public TrainingProviderSkillsSet findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TrainingProviderSkillsSet by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsSet}
	 * @throws Exception the exception
	 * @see    TrainingProviderSkillsSet
	 */
	public List<TrainingProviderSkillsSet> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TrainingProviderSkillsSet
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingProviderSkillsSet}
	 * @throws Exception the exception
	 */
	public List<TrainingProviderSkillsSet> allTrainingProviderSkillsSet(int first, int pageSize) throws Exception {
		return dao.allTrainingProviderSkillsSet(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TrainingProviderSkillsSet for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the TrainingProviderSkillsSet
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TrainingProviderSkillsSet.class);
	}
	
    /**
     * Lazy load TrainingProviderSkillsSet with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 TrainingProviderSkillsSet class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TrainingProviderSkillsSet} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> allTrainingProviderSkillsSet(Class<TrainingProviderSkillsSet> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TrainingProviderSkillsSet>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TrainingProviderSkillsSet for lazy load with filters
     * @author TechFinium 
     * @param entity TrainingProviderSkillsSet class
     * @param filters the filters
     * @return Number of rows in the TrainingProviderSkillsSet entity
     * @throws Exception the exception     
     */	
	public int count(Class<TrainingProviderSkillsSet> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<TrainingProviderSkillsSet> findByTrainingProvider(Long id) throws Exception {
		// TODO Auto-generated method stub
		return populateAdditionalInformationList(dao.findByTrainingProvider(id));
	}
	
	public List<TrainingProviderSkillsSet> findByTrainingProviderAndAccept(Long id,Boolean accept) throws Exception {
		// TODO Auto-generated method stub
		return populateAdditionalInformationList(dao.findByTrainingProviderAndAccept(id,accept));
	}
	
	public List<TrainingProviderSkillsSet> findByTargetClassAndTargetKey(String targetClass,Long targetKey) throws Exception{
		return populateAdditionalInformationList(dao.findByTargetClassAndTargetKey(targetClass, targetKey));
	}
	
	public List<TrainingProviderSkillsSet> findByTargetClassAndTargetKeyAndAccept(String targetClass,Long targetKey,Boolean accept) throws Exception{
		return dao.findByTargetClassAndTargetKeyAndAccept(targetClass, targetKey,accept);
	}
	
	public List<TrainingProviderSkillsSet> findByCompany(Long id) throws Exception {
		// TODO Auto-generated method stub
		return dao.findByCompany(id);
	}
	
	public Integer countfindByCompany(TrainingProviderApplication trainingProviderApplication, SkillsSet skillsSet, Boolean accept) throws Exception {
		return dao.countfindByCompany(trainingProviderApplication, skillsSet, accept);
	}
	
	public Integer countfindByCompany(Company company, SkillsSet skillsSet, Boolean accept) throws Exception {
		return dao.countfindByCompany(company, skillsSet, accept);
	}
	
	public List<TrainingProviderSkillsSet> populateAdditionalInformationList(List<TrainingProviderSkillsSet> trainingProviderSkillsSetList) throws Exception{
		for (TrainingProviderSkillsSet trainingProviderSkillsSet : trainingProviderSkillsSetList) {
			populateAdditionalInformation(trainingProviderSkillsSet);
		}
		return trainingProviderSkillsSetList;
	}
	
	public TrainingProviderSkillsSet populateAdditionalInformation(TrainingProviderSkillsSet trainingProviderSkillsSet) throws Exception{
		if (trainingProviderSkillsSet.getId() != null) {
			// will need to change
			trainingProviderSkillsSet.setDocList(new ArrayList<Doc>());
			if (trainingProviderSkillsSet.getTrainingProviderDocParent() != null && trainingProviderSkillsSet.getTrainingProviderDocParent().getDoc() != null && trainingProviderSkillsSet.getTrainingProviderDocParent().getDoc().getId() != null) {
				trainingProviderSkillsSet.getDocList().add(DocService.instance().findByKeyWithJoins(trainingProviderSkillsSet.getTrainingProviderDocParent().getDoc().getId()));
			}
			
		}
		return trainingProviderSkillsSet;
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplication(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long skillsSetID,  ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o.trainingProviderApplication from TrainingProviderSkillsSet o where o.SkillsSet.id = :skillsSetID and o.accept = true and o.trainingProviderApplication.approvalStatus = :approvalStatus";
		filters.put("skillsSetID", skillsSetID);
		filters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhereSP(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplication(Map<String, Object> filters) throws Exception {
		String hql = "select count(o.trainingProviderApplication) from TrainingProviderSkillsSet o where o.SkillsSet.id = :skillsSetID and o.accept = true and o.trainingProviderApplication.approvalStatus = :approvalStatus";
		return dao.countWhereSP(filters, hql);
	}
	
	public List<TrainingProviderApplication> allTrainingProviderApplicationBySS(Long skillsSetID, Boolean accept) throws Exception {		
		return dao.allTrainingProviderApplicationBySS(skillsSetID,  accept);
	}
	
	public List<TrainingProviderApplication> allTrainingProviderApplicationBySS(Long skillsSetID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {		
		return dao.allTrainingProviderApplicationBySS(skillsSetID,  accept, approvalStatus);
	}
	
	public int countfindBySS(Long skillsSetID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {	
		return dao.countfindBySS(skillsSetID, accept, approvalStatus);
	}
	
	public int countfindBySS(Long companyID,Long skillsSetID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {	
		return dao.countfindBySS(companyID,skillsSetID, accept, approvalStatus);
	}
	
	public int countByTrainingProviderApplication(Long trainingProviderApplicationID,Long skillsSetID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {	
		return dao.countByTrainingProviderApplication(trainingProviderApplicationID,skillsSetID, accept, approvalStatus);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> allTrainingProviderSkillsSetByProviderApplicationId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long providerApplicationId) throws Exception {
		String hql="select o from TrainingProviderSkillsSet o where o.trainingProviderApplication.id = :providerApplicationId  ";
		filters.put("providerApplicationId", providerApplicationId);
		return (List<TrainingProviderSkillsSet>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllTrainingProviderSkillsSetByProviderApplicationId(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from TrainingProviderSkillsSet o where o.trainingProviderApplication.id = :providerApplicationId ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderSkillsSet> allTrainingProviderSkillsSetByProviderApplicationIdAndManuallyEntered(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long providerApplicationId, Boolean manuallyAdded) throws Exception {
		String hql="select o from TrainingProviderSkillsSet o where o.trainingProviderApplication.id = :providerApplicationId and o.manuallyAdded = :manuallyAdded";
		filters.put("providerApplicationId", providerApplicationId);
		filters.put("manuallyAdded", manuallyAdded);
		return (List<TrainingProviderSkillsSet>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllTrainingProviderSkillsSetByProviderApplicationIdAndManuallyEntered(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from TrainingProviderSkillsSet o where o.trainingProviderApplication.id = :providerApplicationId and o.manuallyAdded = :manuallyAdded";
		return dao.countWhere(filters, hql);
	}
	
	public Integer countByApplicationAndSkillsSet(TrainingProviderApplication trainingProviderApplication, SkillsSet skillsSet) throws Exception {
		return dao.countByApplicationAndSkillsSet(trainingProviderApplication, skillsSet);
	}
	
}
