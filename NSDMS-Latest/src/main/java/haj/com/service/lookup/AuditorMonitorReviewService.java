package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.AuditorMonitorReviewDAO;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AreaForImprovement;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.DocService;

public class AuditorMonitorReviewService extends AbstractService {
	/** The dao. */
	private AuditorMonitorReviewDAO dao = new AuditorMonitorReviewDAO();

	/**
	 * Get all AuditorMonitorReview
	 * 
	 * @author TechFinium
	 * @see AuditorMonitorReview
	 * @return a list of {@link haj.com.entity.AuditorMonitorReview}
	 * @throws Exception
	 *             the exception
	 */
	public List<AuditorMonitorReview> allAuditorMonitorReview() throws Exception {
		return dao.allAuditorMonitorReview();
	}

	/**
	 * Create or update AuditorMonitorReview.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AuditorMonitorReview
	 */
	public void create(AuditorMonitorReview entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update AuditorMonitorReview.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AuditorMonitorReview
	 */
	public void update(AuditorMonitorReview entity) throws Exception {
		this.dao.update(entity);
	}
	
	public void update(List<IDataEntity> entity) throws Exception {
		this.dao.updateBatch(entity);
	}


	/**
	 * Delete AuditorMonitorReview.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AuditorMonitorReview
	 */
	public void delete(AuditorMonitorReview entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.AuditorMonitorReview}
	 * @throws Exception
	 *             the exception
	 * @see AuditorMonitorReview
	 */
	public AuditorMonitorReview findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find AuditorMonitorReview by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.AuditorMonitorReview}
	 * @throws Exception
	 *             the exception
	 * @see AuditorMonitorReview
	 */
	public List<AuditorMonitorReview> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load AuditorMonitorReview
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.AuditorMonitorReview}
	 * @throws Exception
	 *             the exception
	 */
	public List<AuditorMonitorReview> allAuditorMonitorReview(int first, int pageSize) throws Exception {
		return dao.allAuditorMonitorReview(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of AuditorMonitorReview for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the AuditorMonitorReview
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(AuditorMonitorReview.class);
	}

	/**
	 * Lazy load AuditorMonitorReview with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            AuditorMonitorReview class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.AuditorMonitorReview} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> allAuditorMonitorReview(Class<AuditorMonitorReview> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.trainingProviderMonitoring is null and o.targetClass is null";
		return (List<AuditorMonitorReview>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWhere(Class<AuditorMonitorReview> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AuditorMonitorReview o where o.trainingProviderMonitoring is null and o.targetClass is null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> allAuditorMonitorReviewNoFilter(Class<AuditorMonitorReview> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<AuditorMonitorReview>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> allAuditorMonitorReviewTrainingMonitor(Class<AuditorMonitorReview> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.trainingProviderMonitoring.id = :trainingProviderMonitoringID";
		return (List<AuditorMonitorReview>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countTrainingMonitor(Class<AuditorMonitorReview> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AuditorMonitorReview o where o.trainingProviderMonitoring.id = :trainingProviderMonitoringID";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> allAuditorMonitorReviewByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return populateAdditionalInformationList((List<AuditorMonitorReview>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> allAuditorMonitorReviewByTargetClassAndKeyNoDocResolve(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return (List<AuditorMonitorReview>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllAuditorMonitorReviewByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	public List<AuditorMonitorReview> populateAdditionalInformationList(List<AuditorMonitorReview> auditorMonitorReviewList) throws Exception{
		for (AuditorMonitorReview auditorMonitorReview : auditorMonitorReviewList) {
			populateAdditionalInformation(auditorMonitorReview);
		}
		return auditorMonitorReviewList;
	}

	public AuditorMonitorReview populateAdditionalInformation(AuditorMonitorReview auditorMonitorReview) throws Exception{
		if (auditorMonitorReview.getId() != null) {
			auditorMonitorReview.setDocs(DocService.instance().searchByTargetKeyAndClass(auditorMonitorReview.getClass().getName(), auditorMonitorReview.getId()));
		}
		return auditorMonitorReview;
	}

	/**
	 * Get count of AuditorMonitorReview for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            AuditorMonitorReview class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the AuditorMonitorReview entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<AuditorMonitorReview> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void prepNewReview(TrainingProviderMonitoring trainingProviderMonitoring) throws Exception {
		List<AuditorMonitorReview> amr = new ArrayList<>();
		if (trainingProviderMonitoring.getAudit()) {
			amr = findByforProcess(ConfigDocProcessEnum.AuditMonitoring);
		} else {
			amr = findByforProcess(ConfigDocProcessEnum.ProviderMonitoring);
		}
		List<IDataEntity> dataEntities = new ArrayList<>();
		for (AuditorMonitorReview auditorMonitorReview : amr) {
			AuditorMonitorReview amrNew = (AuditorMonitorReview) auditorMonitorReview.clone();
			amrNew.setId(null);
			amrNew.setCreateDate(null);
			amrNew.setTrainingProviderMonitoring(trainingProviderMonitoring);
			// added to target class and key
			amrNew.setTargetClass(trainingProviderMonitoring.getClass().getName());
			amrNew.setTargetKey(trainingProviderMonitoring.getId());
			dataEntities.add(amrNew);
		}
		this.dao.createBatch(dataEntities);
	}

	public void prepNewReview(String targetClass, Long targetKey, ConfigDocProcessEnum forProcess) throws Exception {
		List<AuditorMonitorReview> amr = findByforProcess(forProcess);
		List<IDataEntity> dataEntities = new ArrayList<>();
		for (AuditorMonitorReview auditorMonitorReview : amr) {
			AuditorMonitorReview amrNew = (AuditorMonitorReview) auditorMonitorReview.clone();
			amrNew.setId(null);
			amrNew.setCreateDate(null);
			amrNew.setTargetClass(targetClass);
			amrNew.setTargetKey(targetKey);
			dataEntities.add(amrNew);
		}
		this.dao.createBatch(dataEntities);
	}
	
	
	public void prepNewTPApplicationReview(String targetClass, Long targetKey, ConfigDocProcessEnum forProcess,TrainingProviderApplication tpApplication) throws Exception {
		if(forProcess !=ConfigDocProcessEnum.TP){forProcess=ConfigDocProcessEnum.TP;}
		List<AuditorMonitorReview> amr = findByforProcessAndTPApplicationType(forProcess,tpApplication.getAccreditationApplicationTypeEnum());
		List<IDataEntity> dataEntities = new ArrayList<>();
		for (AuditorMonitorReview auditorMonitorReview : amr) {
			AuditorMonitorReview amrNew = (AuditorMonitorReview) auditorMonitorReview.clone();
			amrNew.setId(null);
			amrNew.setCreateDate(null);
			amrNew.setTargetClass(targetClass);
			amrNew.setTargetKey(targetKey);
			dataEntities.add(amrNew);
		}
		this.dao.createBatch(dataEntities);
	}
	/**
	 * Business Rule - if merSETA Primary Accreditation, 
	 * then send Self-evaluation template ETP-TP-001ii.	
	 * Business rule: if application is Extension of Scope, 
	 * Re-accreditation/Re-approval or Learning Programme 
	 * Approval then send Self-evaluation template ETP-TP-010
	 * */
	public void prepSelfEvaluation(String targetClass, Long targetKey, ConfigDocProcessEnum forProcess,TrainingProviderApplication tpApplication) throws Exception {
		if(forProcess !=ConfigDocProcessEnum.TP){forProcess=ConfigDocProcessEnum.TP;}
		List<AuditorMonitorReview> amr=new ArrayList<>();
		if(tpApplication.getAccreditationApplicationTypeEnum()==AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL)
		{
			/**
			If you applying to be accredited as an Training and Assessment Site you need to complete Section 1 to 12
			If you apply to be accredited as an Assessment Only Site the following Section must not be completed:
			Section 2.2
			Section 3.1.3 
			Section 3.1.9
			Section 4
			Section 6.1.3
			Section 6.1.4
			*/
			AccreditationApplicationTypeEnum appType=AccreditationApplicationTypeEnum.Training_and_Assessment_Site;
			List<AuditorMonitorReview> list = findByforProcessAndTPApplicationType(forProcess,appType);
		    if(tpApplication.getTrainingAssessment() !=null && !tpApplication.getTrainingAssessment())
			{
				for(AuditorMonitorReview amrObj:list)
				{
					//Section 4
					if(amrObj.getSection().equals(4))
					{
						amrObj.setIsNotRelevant(true);
					}
					//Section 2.2
					else if(amrObj.getSection().equals(2) && amrObj.getEvidenceRequirements().contains("2.2"))
					{
						amrObj.setIsNotRelevant(true);
					}
					else if(amrObj.getEvidenceRequirements().contains("3.1.3")|| amrObj.getEvidenceRequirements().contains("3.1.9")
				    || amrObj.getEvidenceRequirements().contains("6.1.3") || amrObj.getEvidenceRequirements().contains("6.1.4"))
					{
						amrObj.setIsNotRelevant(true);
					}
				}
				amr=list;
			}
		    else
		    {
		    	amr=list;
		    }
		}
		else
		{
			  amr = findByforProcessAndTPApplicationType(forProcess,AccreditationApplicationTypeEnum.EOS_RA_LP);
		}
		
		List<IDataEntity> dataEntities = new ArrayList<>();
		for (AuditorMonitorReview auditorMonitorReview : amr) {
			AuditorMonitorReview amrNew = (AuditorMonitorReview) auditorMonitorReview.clone();
			amrNew.setId(null);
			amrNew.setCreateDate(null);
			amrNew.setTargetClass(targetClass);
			amrNew.setTargetKey(targetKey);
			dataEntities.add(amrNew);
		}
		this.dao.createBatch(dataEntities);
	}
	
	public void prepSelfEvaluationExtensionOfScope(String targetClass, Long targetKey, ConfigDocProcessEnum forProcess,TrainingProviderApplication tpApplication) throws Exception {
		List<AuditorMonitorReview> amr = new ArrayList<>();
		amr = findByforProcessTP(forProcess);
		List<IDataEntity> dataEntities = new ArrayList<>();
		for (AuditorMonitorReview auditorMonitorReview : amr) {
			AuditorMonitorReview amrNew = (AuditorMonitorReview) auditorMonitorReview.clone();
			amrNew.setId(null);
			amrNew.setCreateDate(null);
			amrNew.setTargetClass(targetClass);
			amrNew.setTargetKey(targetKey);
			dataEntities.add(amrNew);
		}
		this.dao.createBatch(dataEntities);
	}
	
	public List<AuditorMonitorReview> findByforProcessAndTPApplicationType(ConfigDocProcessEnum forProcess,AccreditationApplicationTypeEnum appType) throws Exception {
		return dao.findByforProcessAndTPApplicationType(forProcess,appType);
	}
	
	public List<AuditorMonitorReview> findByforProcessTP(ConfigDocProcessEnum forProcess) throws Exception {
		return dao.findByforProcessTP(forProcess);
	}

	public List<AuditorMonitorReview> findByMonitor(TrainingProviderMonitoring trainingProviderMonitoring) throws Exception {
		List<AuditorMonitorReview> amr = dao.findByMonitor(trainingProviderMonitoring);
		for (AuditorMonitorReview auditorMonitorReview : amr) {
			auditorMonitorReview.setDocs(DocService.instance().searchByTargetKeyAndClass(auditorMonitorReview.getClass().getName(), auditorMonitorReview.getId()));
		}
		return amr;
	}

	public List<AuditorMonitorReview> findByTargetKeyAndClass(String targetClass, Long targetKey) throws Exception {
		List<AuditorMonitorReview> amr = dao.findByTargetKeyAndClass(targetClass, targetKey);
		for (AuditorMonitorReview auditorMonitorReview : amr) {
			auditorMonitorReview.setDocs(DocService.instance().searchByTargetKeyAndClass(auditorMonitorReview.getClass().getName(), auditorMonitorReview.getId()));
		}
		return amr;
	}
	
	public List<AuditorMonitorReview> findByTargetKeyAndClassNoDoc(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetKeyAndClass(targetClass, targetKey);
	}
	
	public List<AuditorMonitorReview> findByTargetKeyAndClassNoDoc(String targetClass, Long targetKey, Integer section) throws Exception {
		return dao.findByTargetKeyAndClass(targetClass, targetKey, section);
	}
	
	@SuppressWarnings("unchecked")
	public List<AreaForImprovement> findAreaForImprovement(String targetClass, Long targetKey) throws Exception {
		return dao.findAreaForImprovement(targetClass, targetKey);
	}

	public List<AuditorMonitorReview> findByforProcess(ConfigDocProcessEnum forProcess) throws Exception {
		return dao.findByforProcess(forProcess);
	}
	
	public int countByTargetKeyAndClassWhereEvidanceIsRequired(String targetClass, Long targetKey) throws Exception {
		return dao.countByTargetKeyAndClassWhereEvidanceIsRequired(targetClass, targetKey);
	}
	
	public Integer countByTargetClassKeyWhereEvidanceAvalaibleNotPorvided(String targetClass, Long targetKey) throws Exception{
		return dao.countByTargetClassKeyWhereEvidanceAvalaibleNotPorvided(targetClass, targetKey);
	}
	
	public Integer countByTargetClassKeyWhereEvidanceAvalaibleNotPorvidedWithRelevent(String targetClass, Long targetKey) throws Exception{
		return dao.countByTargetClassKeyWhereEvidanceAvalaibleNotPorvidedWithRelevent(targetClass, targetKey);
	}
	
	public Integer countByTargetClassKeyWhereOutcomeCommentNotProvided(String targetClass, Long targetKey) throws Exception {
		return dao.countByTargetClassKeyWhereOutcomeCommentNotProvided(targetClass, targetKey);
	}
	
	public Integer countByTargetClassKeyWhereOutcomeIsTypeAndNoDocProvided(String targetClass, Long targetKey, YesNoEnum evidenceRequiredEvaluatorOutcome) throws Exception{
		return dao.countByTargetClassKeyWhereOutcomeIsTypeAndNoDocProvided(targetClass, targetKey, evidenceRequiredEvaluatorOutcome);
	}
	
	public Integer countByTargetClassKeyWhereOutcomeIsType(String targetClass, Long targetKey, YesNoEnum evidenceRequiredEvaluatorOutcome) throws Exception {
		return dao.countByTargetClassKeyWhereOutcomeIsType(targetClass, targetKey, evidenceRequiredEvaluatorOutcome);
	}
	
}