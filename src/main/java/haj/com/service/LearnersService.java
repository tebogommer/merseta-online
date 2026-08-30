package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.LearnersDAO;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Learners;
import haj.com.entity.NonSetaQualificationsCompletion;
import haj.com.entity.Users;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.InterventionTypeService;

public class LearnersService extends AbstractService {
	/** The dao. */
	private LearnersDAO dao = new LearnersDAO();
	
	/* Service Levels */
	private TrainingProviderApplicationService trainingProviderApplicationService;

	private static final String leftJoin = " " + "left join fetch o.user.residentialAddress ra left join fetch o.user.postalAddress pa left join fetch o.user.disabled left join fetch o.user.gender left join fetch o.user.disabilityStatus left join fetch ra.municipality left join fetch pa.municipality" + " ";
	private static final String leftJoinAddress = " left join fetch o.employer e " + "left join fetch o.company c " + "left join fetch o.user u " + "left join fetch u.postalAddress upa " + "left join fetch c.postalAddress cpa " + "left join fetch e.postalAddress epa ";
	/**
	 * Get all Learners
	 * 
	 * @author TechFinium
	 * @see Learners
	 * @return a list of {@link haj.com.entity.Learners}
	 * @throws Exception the exception
	 */
	public List<Learners> allLearners() throws Exception {
		return dao.allLearners();
	}

	/**
	 * Create or update Learners.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see Learners
	 */
	public void create(Learners entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update Learners.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see Learners
	 */
	public void update(Learners entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Learners.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see Learners
	 */
	public void delete(Learners entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Learners}
	 * @throws Exception the exception
	 * @see Learners
	 */
	public Learners findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find Learners by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Learners}
	 * @throws Exception the exception
	 * @see Learners
	 */
	public List<Learners> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Learners
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Learners}
	 * @throws Exception the exception
	 */
	public List<Learners> allLearners(int first, int pageSize) throws Exception {
		return dao.allLearners(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Learners for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the Learners
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(Learners.class);
	}

	/**
	 * Lazy load Learners with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    Learners class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.Learners} containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allLearners(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		//String hql = "select o from CompanyLearners o left join fetch o.employer where o.company.id = :companyID and o.learnerStatus <> :terminated and o.interventionType <> null";
		String hql = "select o from CompanyLearners o left join fetch o.employer where (o.company.id = :companyID or  o.employer.id = :companyID) and o.learnerStatus <> :terminated and o.interventionType <> null";
		
		// return populateAdditionalInformationList((List<CompanyLearners>)
		// dao.hqlAndParamOnly(CompanyLearners.class, first, pageSize, sortField,
		// sortOrder, filters, hql));
		return populateAdditionalInformationList((List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allLearnersNew(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o "+leftJoinAddress+" where (c.id = :companyID or e.id = :companyID) and o.interventionType <> null";
		return populateAdditionalInformationList((List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allLearnersByCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer where (o.company.id = :companyID or  o.employer.id = :companyID) and o.interventionType <> null";
		return populateAdditionalInformationList((List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countByCompany(Class<Learners> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where (o.company.id = :companyID or  o.employer.id = :companyID) and o.interventionType <> null";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allLearnersOld(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o "+leftJoinAddress+" where (o.company.id = :companyID or  o.employer.id = :companyID) and o.interventionType <> null";
		return populateAdditionalInformationList((List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countNew(Class<Learners> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o.user) from CompanyLearners o where (o.company.id = :companyID or  o.employer.id = :companyID) and o.interventionType <> null";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allCompanyLearners(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer where (o.company.id = :companyID or  o.employer.id = :companyID) and o.learnerStatus <> :terminated and o.interventionType <> null";
		// return populateAdditionalInformationList((List<CompanyLearners>)
		// dao.hqlAndParamOnly(CompanyLearners.class, first, pageSize, sortField,
		// sortOrder, filters, hql));
		return populateAdditionalInformationList((List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allLearnersAssignedToEmployerWithoutStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer where o.employer.id = :companyId and o.interventionType <> null";
		filters.put("companyId", companyId);
		return populateAdditionalInformationList((List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllLearnersAssignedToEmployerWithoutStatus(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.employer.id = :companyId and o.interventionType <> null";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allLearnersAssignedToEmployerWithoutStatusWithoutAdditionalLoad(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer where o.employer.id = :companyId and o.interventionType <> null";
		filters.put("companyId", companyId);
		return (List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allArplTradeTestLearners(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer where o.learnerStatus <> :terminated and o.interventionType <> null and o.interventionType.forTradeTest = :forTradeTest and o.legacyId is not null and o.legacyTargetClass is not null ";
		return populateAdditionalInformationList((List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllArplTradeTestLearners(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.learnerStatus <> :terminated and o.interventionType <> null and o.interventionType.forTradeTest = :forTradeTest and o.legacyId is not null and o.legacyTargetClass is not null ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId, List<InterventionType> interventionTypeList) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer where o.employer.id = :companyId and o.interventionType <> null ";
		filters.put("companyId", companyId);
		if (interventionTypeList != null && !interventionTypeList.isEmpty()) {
			hql += " and ( ";
			int counter = 1;
			for (InterventionType interventionType : interventionTypeList) {
				if (counter == interventionTypeList.size()) {
					hql += " o.interventionType.id = :interventionType" + counter;
				} else {
					hql += " o.interventionType.id = :interventionType" + counter + " or ";
				}
				filters.put("interventionType" + counter, interventionType.getId());
				counter++;
			}
			hql += " ) ";
		}
		return populateAdditionalInformationList((List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeList(Map<String, Object> filters, List<InterventionType> interventionTypeList) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.employer.id = :companyId and o.interventionType <> null ";
		if (interventionTypeList != null && !interventionTypeList.isEmpty()) {
			hql += " and ( ";
			int counter = 1;
			for (InterventionType interventionType : interventionTypeList) {
				if (counter == interventionTypeList.size()) {
					hql += " o.interventionType.id = :interventionType" + counter;
				} else {
					hql += " o.interventionType.id = :interventionType" + counter + " or ";
				}
				counter++;
			}
			hql += " ) ";
		}
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeAndDesignatedTrade(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId, Long interventionTypeId, Long designatedTradeId) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer where o.employer.id = :companyId and o.interventionType.id = :interventionTypeId and o.qualification.id in (select distinct(x.qualification.id) from DesignatedTradeLevel x where x.designatedTrade.id = :designatedTradeId)  ";
		filters.put("companyId", companyId);
		filters.put("interventionTypeId", interventionTypeId);
		filters.put("designatedTradeId", designatedTradeId);
		return populateAdditionalInformationList((List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeAndDesignatedTrade(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.employer.id = :companyId and o.interventionType.id = :interventionTypeId and o.qualification.id in (select distinct(x.qualification.id) from DesignatedTradeLevel x where x.designatedTrade.id = :designatedTradeId)  ";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Get count of Learners for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  Learners class
	 * @param filters the filters
	 * @return Number of rows in the Learners entity
	 * @throws Exception the exception
	 */
	public int countCompanyLearners(Class<Learners> entity, Map<String, Object> filters) throws Exception {
		//String hql = "select count(o.user) from CompanyLearners o where o.company.id = :companyID and o.learnerStatus <> :terminated and o.interventionType <> null";
		String hql = "select count(o.user) from CompanyLearners o where (o.company.id = :companyID or  o.employer.id = :companyID) and o.learnerStatus <> :terminated and o.interventionType <> null";
		return dao.countWhere(filters, hql);
	}
	
	public int count(Class<Learners> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o.user) from CompanyLearners o where (o.company.id = :companyID or  o.employer.id = :companyID) and o.learnerStatus <> :terminated and o.interventionType <> null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allLearnersNonSectaCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer left join fetch o.nonSetaCompany where o.nonSetaCompany.id = :nonSetaCompanyId and o.learnerStatus <> :terminated and o.interventionType <> null";
		return (List<CompanyLearners>) dao.hqlAndParamOnly(CompanyLearners.class, first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllLearnersNonSectaCompany(Class<Learners> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.nonSetaCompany.id = :nonSetaCompanyId and o.learnerStatus <> :terminated and o.interventionType <> null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allLearnersByUser(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o left join fetch o.employer left join fetch o.company where o.user.id = :userID and o.interventionType <> null";
		return populateAdditionalInformationList((List<CompanyLearners>) dao.hqlAndParamOnly(CompanyLearners.class, first, pageSize, sortField, sortOrder, filters, hql));
		// return (
		// List<Learners>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}

	public int countByUser(Class<Learners> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.user.id = :userID and o.interventionType <> null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Users> allLearnersAsUsers(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select distinct o.user from CompanyLearners o";
		return (List<Users>) dao.hqlAndParamOnly(CompanyLearners.class, first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> allUsersFilter(Class<Users> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		return (List<Users>) dao.sortAndFilterUsers(class1, first, pageSize, sortField, sortOrder, filters);
	}
	
	public int countUsersFilter(Class<Users> entity, Map<String, Object> filters) {
		return dao.countUsersFilter(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> allUsers(Class<Users> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		return (List<Users>) dao.sortAndFilterUsers(class1, first, pageSize, sortField, sortOrder, filters);
	}

	public int countUsers(Class<Learners> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(distinct o.user) from CompanyLearners o";
		return dao.countWhere(filters, hql);
	}

	public List<CompanyLearners> populateAdditionalInformationList(List<CompanyLearners> companyLearnersList) throws Exception {
		for (CompanyLearners companyLearners : companyLearnersList) {
			populateAdditionalInformation(companyLearners);
		}
		return companyLearnersList;
	}

	public CompanyLearners populateAdditionalInformation(CompanyLearners companyLearners) throws Exception {
		if (companyLearners.getId() != null) {

			companyLearners.setCanAction(true);
			// validation to ensure no lost time is open
			if (CompanyLearnersLostTimeService.instance().countOpenLostTimeByCompanyLearnersId(companyLearners) != 0) {
				companyLearners.setCanAction(false);
			}

			CompanyLearnersService.instance().locateDocsByTargetClassAndKey(companyLearners);

			InterventionTypeService interventionTypeService = new InterventionTypeService();
			List<InterventionType> l = new ArrayList<>();
			l.add(interventionTypeService.findByKey(24L));
			l.add(interventionTypeService.findByKey(30L));
			l.add(interventionTypeService.findByKey(31L));
			l.add(interventionTypeService.findByKey(32L));
			l.add(interventionTypeService.findByKey(33L));
			l.add(interventionTypeService.findByKey(37L));
			l.add(interventionTypeService.findByKey(38L));
			for (InterventionType interventionType : l) {
				if (companyLearners.getInterventionType().getId() == interventionType.getId()) {
					companyLearners.setExtentionCheck(true);
					break;
				}
			}
			//companyLearners
			if(companyLearners.getLearnerStatus() == LearnerStatusEnum.QualificationObtained) {
				NonSetaQualificationsCompletionService nqs = new NonSetaQualificationsCompletionService();
				List<NonSetaQualificationsCompletion>list = nqs.findByKeyCompanyLearner(companyLearners);
				if(list.size()>0) {
					//NonSetaQualificationsCompletion nonSetaQualificationsCompletion = nqs.findByKeyCompanyLearners(companyLearners);
					companyLearners.setNonSetaQualificationsCompletion(list.get(0));
				}
			}
			if(companyLearners.getQualification()==null) {
				companyLearners.setQualification(CompanyLearnersService.instance().getCompanyLearnerQualification(companyLearners));
			}
			// training provider application
			if (trainingProviderApplicationService == null) {
				trainingProviderApplicationService = new TrainingProviderApplicationService();
			}
			if (companyLearners.getTrainingProviderApplication() != null && companyLearners.getTrainingProviderApplication().getId() != null) {
				companyLearners.setTrainingProviderApplication(trainingProviderApplicationService.findByKey(companyLearners.getTrainingProviderApplication().getId()));
				if (companyLearners.getTrainingProviderApplication() != null && companyLearners.getTrainingProviderApplication().getTrainingSite() != null && companyLearners.getTrainingProviderApplication().getTrainingSite().getId() != null ) {
					companyLearners.setTrainingSite(companyLearners.getTrainingProviderApplication().getTrainingSite());
				}
			}
		}
		return companyLearners;
	}

}
