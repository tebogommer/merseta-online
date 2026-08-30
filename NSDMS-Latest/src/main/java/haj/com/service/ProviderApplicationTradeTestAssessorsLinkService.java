package haj.com.service;

import java.util.List;

import haj.com.dao.ProviderApplicationTradeTestAssessorsLinkDAO;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.ProviderApplicationTradeTestAssessorsLink;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.AssessorModeratorTypeEnum;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class ProviderApplicationTradeTestAssessorsLinkService extends AbstractService {
	
	
	/** The dao. */
	private ProviderApplicationTradeTestAssessorsLinkDAO dao = new ProviderApplicationTradeTestAssessorsLinkDAO();
	
	/* Service Levels */
	private AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
	

	/**
	 * Get all ProviderApplicationTradeTestAssessorsLink
	 * 
	 * @author TechFinium
	 * @see ProviderApplicationTradeTestAssessorsLink
	 * @return a list of
	 *         {@link haj.com.entity.ProviderApplicationTradeTestAssessorsLink}
	 * @throws Exception
	 *             the exception
	 */
	public List<ProviderApplicationTradeTestAssessorsLink> allProviderApplicationTradeTestAssessorsLink() throws Exception {
		return dao.allProviderApplicationTradeTestAssessorsLink();
	}

	/**
	 * Create or update ProviderApplicationTradeTestAssessorsLink.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProviderApplicationTradeTestAssessorsLink
	 */
	public void create(ProviderApplicationTradeTestAssessorsLink entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update ProviderApplicationTradeTestAssessorsLink.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProviderApplicationTradeTestAssessorsLink
	 */
	public void update(ProviderApplicationTradeTestAssessorsLink entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ProviderApplicationTradeTestAssessorsLink.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProviderApplicationTradeTestAssessorsLink
	 */
	public void delete(ProviderApplicationTradeTestAssessorsLink entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a
	 *         {@link haj.com.entity.ProviderApplicationTradeTestAssessorsLink}
	 * @throws Exception
	 *             the exception
	 * @see ProviderApplicationTradeTestAssessorsLink
	 */
	public ProviderApplicationTradeTestAssessorsLink findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find ProviderApplicationTradeTestAssessorsLink by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of
	 *         {@link haj.com.entity.ProviderApplicationTradeTestAssessorsLink}
	 * @throws Exception
	 *             the exception
	 * @see ProviderApplicationTradeTestAssessorsLink
	 */
	public List<ProviderApplicationTradeTestAssessorsLink> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ProviderApplicationTradeTestAssessorsLink
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of
	 *         {@link haj.com.entity.ProviderApplicationTradeTestAssessorsLink}
	 * @throws Exception
	 *             the exception
	 */
	public List<ProviderApplicationTradeTestAssessorsLink> allProviderApplicationTradeTestAssessorsLink(int first,
			int pageSize) throws Exception {
		return dao.allProviderApplicationTradeTestAssessorsLink(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ProviderApplicationTradeTestAssessorsLink for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the ProviderApplicationTradeTestAssessorsLink
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(ProviderApplicationTradeTestAssessorsLink.class);
	}

	/**
	 * Lazy load ProviderApplicationTradeTestAssessorsLink with pagination,
	 * filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            ProviderApplicationTradeTestAssessorsLink class
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
	 * @return a list of
	 *         {@link haj.com.entity.ProviderApplicationTradeTestAssessorsLink}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationTradeTestAssessorsLink> allProviderApplicationTradeTestAssessorsLink(Class<ProviderApplicationTradeTestAssessorsLink> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<ProviderApplicationTradeTestAssessorsLink>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of ProviderApplicationTradeTestAssessorsLink for lazy load with
	 * filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            ProviderApplicationTradeTestAssessorsLink class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the ProviderApplicationTradeTestAssessorsLink
	 *         entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<ProviderApplicationTradeTestAssessorsLink> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public int countByProviderApplicationAndAssessorModApp(Long trainingProviderApplicationId, Long assessorModeratorApplicationId) throws Exception {
		return dao.countByProviderApplicationAndAssessorModApp(trainingProviderApplicationId, assessorModeratorApplicationId);
	}
	
	public ProviderApplicationTradeTestAssessorsLink findByProviderApplicationAndAssessorModApp(Long trainingProviderApplicationId, Long assessorModeratorApplicationId) throws Exception {
		return dao.findByProviderApplicationAndAssessorModApp(trainingProviderApplicationId, assessorModeratorApplicationId);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationTradeTestAssessorsLink> allByProviderApplicationResolveAssessorInformation(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long providerApplicationId) throws Exception {
		String hql = "select o from ProviderApplicationTradeTestAssessorsLink o where o.trainingProviderApplication.id = :providerApplicationId";
		filters.put("providerApplicationId", providerApplicationId);
		return resolveAssessorInformation((List<ProviderApplicationTradeTestAssessorsLink>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	private List<ProviderApplicationTradeTestAssessorsLink> resolveAssessorInformation(List<ProviderApplicationTradeTestAssessorsLink> list) throws Exception{
		for (ProviderApplicationTradeTestAssessorsLink entity : list) {
			if (entity.getAssessorModeratorApplication() != null && entity.getAssessorModeratorApplication().getId() != null) {
				entity.setAssessorModeratorApplication(assessorModeratorApplicationService.findByKey(entity.getAssessorModeratorApplication().getId()));
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationTradeTestAssessorsLink> allByProviderApplication(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long providerApplicationId) throws Exception {
		String hql = "select o from ProviderApplicationTradeTestAssessorsLink o where o.trainingProviderApplication.id = :providerApplicationId";
		filters.put("providerApplicationId", providerApplicationId);
		return (List<ProviderApplicationTradeTestAssessorsLink>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllByProviderApplication(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ProviderApplicationTradeTestAssessorsLink o where o.trainingProviderApplication.id = :providerApplicationId";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationTradeTestAssessorsLink> allByAssessorApplication(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long providerApplicationId) throws Exception {
		String hql = "select o from ProviderApplicationTradeTestAssessorsLink o where o.assessorModeratorApplication.id = :providerApplicationId";
		filters.put("providerApplicationId", providerApplicationId);
		return (List<ProviderApplicationTradeTestAssessorsLink>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllByAssessorApplication(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ProviderApplicationTradeTestAssessorsLink o where o.assessorModeratorApplication.id = :providerApplicationId";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findAssessorModeratorTtcByQualificationApprovedAndProviderApplication(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, 
				Long trainingProviderApplicationId, List<AssessorModeratorAppTypeEnum> typeList, Long qualificationId) throws Exception {
		
		String hql = "select o.assessorModeratorApplication from ProviderApplicationTradeTestAssessorsLink o "
				+ " where "
				+ " o.trainingProviderApplication.id = :trainingProviderApplicationId "
				+ " and o.assessorModeratorApplication.status = :status "
				+ " and o.assessorModeratorApplication.assessorModeratorType = :assessorModeratorType "
				+ " and o.assessorModeratorApplication.id in (select x.forAssessorModeratorApplication.id from UserQualifications x where x.qualification.id = :qualificationId and x.accept = true ) ";
		filters.put("trainingProviderApplicationId", trainingProviderApplicationId);
		filters.put("status", ApprovalEnum.Approved);
		filters.put("assessorModeratorType", AssessorModeratorTypeEnum.TTC_AM);
		filters.put("qualificationId", qualificationId);
		if (!typeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : typeList) {
				hql += " o.assessorModeratorApplication.applicationType = :applicationType"+counter;
				if (counter != typeList.size()) {
					hql += " or ";
				}
				filters.put("applicationType" + counter, assessorModeratorAppTypeEnum);
				counter++;
			}
			hql += ") ";
		}
		return (List<AssessorModeratorApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
		
	}
	
	public int countAssessorModeratorTTCByQualificationApprovedAndProviderApplication(Map<String, Object> filters, List<AssessorModeratorAppTypeEnum> typeList) throws Exception {
		
		String hql = "select count(o.assessorModeratorApplication.id) from ProviderApplicationTradeTestAssessorsLink o "
				+ " where "
				+ " o.trainingProviderApplication.id = :trainingProviderApplicationId "
				+ " and o.assessorModeratorApplication.status = :status "
				+ " and o.assessorModeratorApplication.assessorModeratorType = :assessorModeratorType "
				+ " and o.assessorModeratorApplication.id in (select x.forAssessorModeratorApplication.id from UserQualifications x where x.qualification.id = :qualificationId and x.accept = true ) ";
		if (!typeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : typeList) {
				hql += " o.assessorModeratorApplication.applicationType = :applicationType"+counter;
				if (counter != typeList.size()) {
					hql += " or ";
				}
				counter++;
			}
			hql += ") ";
		}
		
		return dao.countWhere(filters, hql);
	}

}