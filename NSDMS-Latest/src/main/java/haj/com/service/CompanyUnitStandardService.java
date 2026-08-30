package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.CompanyUnitStandardDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.Doc;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUnitStandardService.
 */
public class CompanyUnitStandardService extends AbstractService {
	
	/** The dao. */
	private CompanyUnitStandardDAO dao = new CompanyUnitStandardDAO();
	private static CompanyUnitStandardService companyUnitStandardService = null;
	public static synchronized CompanyUnitStandardService instance() {
		if (companyUnitStandardService == null) {
			companyUnitStandardService = new CompanyUnitStandardService();
		}
		return companyUnitStandardService;
	}

	/**
	 * Get all CompanyUnitStandard.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUnitStandard}
	 * @throws Exception             the exception
	 * @see CompanyUnitStandard
	 */
	public List<CompanyUnitStandard> allCompanyUnitStandard() throws Exception {
		return dao.allCompanyUnitStandard();
	}

	/**
	 * Create or update CompanyUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyUnitStandard
	 */
	public void create(CompanyUnitStandard entity) throws Exception {
		/*CompanyUnitStandard cu = findByCompany(entity.getCompany(), entity.getUnitStandard());
		if (entity.getId() == null && cu != null) {
			throw new ValidationException("unit.standard.already.exists", entity.getUnitStandard().getTitle());			
		}*/
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update CompanyUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyUnitStandard
	 */
	public void update(CompanyUnitStandard entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyUnitStandard
	 */
	public void delete(CompanyUnitStandard entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyUnitStandard}
	 * @throws Exception
	 *             the exception
	 * @see CompanyUnitStandard
	 */
	public CompanyUnitStandard findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find CompanyUnitStandard by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyUnitStandard}
	 * @throws Exception
	 *             the exception
	 * @see CompanyUnitStandard
	 */
	public List<CompanyUnitStandard> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load CompanyUnitStandard.
	 *
	 * @param first            from key
	 * @param pageSize            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyUnitStandard}
	 * @throws Exception             the exception
	 */
	public List<CompanyUnitStandard> allCompanyUnitStandard(int first, int pageSize) throws Exception {
		return dao.allCompanyUnitStandard(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CompanyUnitStandard for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the CompanyUnitStandard
	 * @throws Exception             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyUnitStandard.class);
	}

	/**
	 * Lazy load CompanyUnitStandard with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1            CompanyUnitStandard class
	 * @param first            the first
	 * @param pageSize            the page size
	 * @param sortField            the sort field
	 * @param sortOrder            the sort order
	 * @param filters            the filters
	 * @return a list of {@link haj.com.entity.CompanyUnitStandard} containing data
	 * @throws Exception             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUnitStandard> allCompanyUnitStandard(Class<CompanyUnitStandard> class1, int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyUnitStandard>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of CompanyUnitStandard for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity            CompanyUnitStandard class
	 * @param filters            the filters
	 * @return Number of rows in the CompanyUnitStandard entity
	 * @throws Exception             the exception
	 */
	public int count(Class<CompanyUnitStandard> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by company.
	 *
	 * @param company the company
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<CompanyUnitStandard> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company);
	}

	/**
	 * Find by company.
	 *
	 * @param company the company
	 * @param unitStandards the unit standards
	 * @return the company unit standard
	 * @throws Exception the exception
	 */
	public CompanyUnitStandard findByCompany(Company company, UnitStandards unitStandards) throws Exception {
		return dao.findByCompany(company, unitStandards);
	}
	
	public List<CompanyUnitStandard> findByTargetClassAndTargetKey(String targetClass,Long targetKey)  throws Exception {
		return populateAdditionalInformationList(dao.findByTargetClassAndTargetKey(targetClass, targetKey));
	}
	
	public List<CompanyUnitStandard> findByTargetClassAndTargetKeyAndAccept(String targetClass,Long targetKey,Boolean accept)  throws Exception {
		return dao.findByTargetClassAndTargetKeyAndAccept(targetClass, targetKey,accept);
	}
	
	public Integer countfindByCompany(Company company, UnitStandards unitStandards, Boolean accept) throws Exception {
		return dao.countfindByCompany(company, unitStandards, accept);
	}
	
	public Integer countCompanyUnitStandard(Company company, Boolean accept) throws Exception {
		return dao.countCompanyUnitStandard(company,  accept);
	}
	
	public List<CompanyUnitStandard> populateAdditionalInformationList(List<CompanyUnitStandard> companyUnitStandardList) throws Exception{
		for (CompanyUnitStandard companyUnitStandard : companyUnitStandardList) {
			populateAdditionalInformation(companyUnitStandard);
		}
		return companyUnitStandardList;
	}
	
	public CompanyUnitStandard populateAdditionalInformation(CompanyUnitStandard companyUnitStandard) throws Exception{
		if (companyUnitStandard.getId() != null) {
			// will need to change
			companyUnitStandard.setDocList(new ArrayList<Doc>());
			if (companyUnitStandard.getTrainingProviderDocParent() != null && companyUnitStandard.getTrainingProviderDocParent().getDoc() != null && companyUnitStandard.getTrainingProviderDocParent().getDoc().getId() != null) {
				companyUnitStandard.getDocList().add(DocService.instance().findByKeyWithJoins(companyUnitStandard.getTrainingProviderDocParent().getDoc().getId()));
			}
			
		}
		return companyUnitStandard;
	}
	
	public Integer countCompanyUnitStandardAccptedByTargetClassAndKey(String targetClass, Long targetKey, UnitStandards unitStandards, Boolean accept) throws Exception {
		return dao.countCompanyUnitStandardAccptedByTargetClassAndKey(targetClass, targetKey, unitStandards, accept);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUnitStandard> allCompanyUnitStandardByProviderApplicationId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql="select o from CompanyUnitStandard o left join fetch o.company c where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return (List<CompanyUnitStandard>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllCompanyUnitStandardByProviderApplicationId(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from CompanyUnitStandard o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUnitStandard> allCompanyUnitStandardByProviderApplicationIdAndManuallyEntered(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey, Boolean manuallyAdded) throws Exception {
		String hql="select o from CompanyUnitStandard o left join fetch o.company c where o.targetClass = :targetClass  and o.targetKey = :targetKey and o.manuallyAdded = :manuallyAdded";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		filters.put("manuallyAdded", manuallyAdded);
		return (List<CompanyUnitStandard>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllCompanyUnitStandardByProviderApplicationIdAndManuallyEntered(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from CompanyUnitStandard o where o.targetClass = :targetClass  and o.targetKey = :targetKey and o.manuallyAdded = :manuallyAdded";
		return dao.countWhere(filters, hql);
	}
	
	public Integer countUnitStandardTargetClassAndKey(String targetClass, Long targetKey, UnitStandards unitStandards) throws Exception {
		return dao.countUnitStandardTargetClassAndKey(targetClass, targetKey, unitStandards);
	}
}
