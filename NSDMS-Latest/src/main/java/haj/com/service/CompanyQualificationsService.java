package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.CompanyQualificationsDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.Doc;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyQualificationsService.
 */
public class CompanyQualificationsService extends AbstractService {
	/** The dao. */
	private CompanyQualificationsDAO dao = new CompanyQualificationsDAO();
	
	private static CompanyQualificationsService companyQualificationsService = null;
	public static synchronized CompanyQualificationsService instance() {
		if (companyQualificationsService == null) {
			companyQualificationsService = new CompanyQualificationsService();
		}
		return companyQualificationsService;
	}

	/**
	 * Get all CompanyQualifications.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyQualifications}
	 * @throws Exception
	 *             the exception
	 * @see CompanyQualifications
	 */
	public List<CompanyQualifications> allCompanyQualifications() throws Exception {
		return dao.allCompanyQualifications();
	}
	
	

	/**
	 * Create or update CompanyQualifications.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyQualifications
	 */
	public void create(CompanyQualifications entity) throws Exception {
		/*CompanyQualifications cq = findByCompany(entity.getCompany(), entity.getQualification());
		if (entity.getId() == null && cq != null) {
			throw new ValidationException("qualification.already.exists", entity.getQualification().getDescription());
		}*/
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update CompanyQualifications.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyQualifications
	 */
	public void update(CompanyQualifications entity) throws Exception {
		this.dao.update(entity);
	}

	public void update(List<IDataEntity> entity) throws Exception {
		this.dao.updateBatch(entity);
	}

	/**
	 * Delete CompanyQualifications.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyQualifications
	 */
	public void delete(CompanyQualifications entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyQualifications}
	 * @throws Exception
	 *             the exception
	 * @see CompanyQualifications
	 */
	public CompanyQualifications findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find CompanyQualifications by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyQualifications}
	 * @throws Exception
	 *             the exception
	 * @see CompanyQualifications
	 */
	public List<CompanyQualifications> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load CompanyQualifications.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyQualifications}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyQualifications> allCompanyQualifications(int first, int pageSize) throws Exception {
		return dao.allCompanyQualifications(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CompanyQualifications for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the CompanyQualifications
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyQualifications.class);
	}

	/**
	 * Lazy load CompanyQualifications with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            CompanyQualifications class
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
	 * @return a list of {@link haj.com.entity.CompanyQualifications} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> allCompanyQualifications(Class<CompanyQualifications> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyQualifications>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> allAcceptedCompanyQualifications(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql="select o from CompanyQualifications o left join fetch o.company c where o.accept = true and o.qualification.id =:qualID";
		return (List<CompanyQualifications>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> allAcceptedCompanyQualificationsTest(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql="select o from CompanyQualifications o left join fetch o.company c where o.accept = true";
		return (List<CompanyQualifications>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

	}
	
	public int countAcceptedCompanies(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from CompanyQualifications o where o.accept = true and o.qualification.id =:qualID";
		return dao.countWhere(filters, hql);
	}
	
	public int countAcceptedCompaniesTest(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from CompanyQualifications o where o.accept = true";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Get count of CompanyQualifications for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            CompanyQualifications class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyQualifications entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyQualifications> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}


	/**
	 * Find by company.
	 *
	 * @param company
	 *            the company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyQualifications> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company);
	}

	/**
	 * Find by company.
	 *
	 * @param company
	 *            the company
	 * @param qualification
	 *            the qualification
	 * @return the company qualifications
	 * @throws Exception
	 *             the exception
	 */
	public CompanyQualifications findByCompany(Company company, Qualification qualification) throws Exception {
		return dao.findByCompany(company, qualification);
	}
	public CompanyQualifications findByCompany(Company company, Qualification qualification, Boolean accept) throws Exception {
		return dao.findByCompany(company, qualification, accept);
	}
	
	public List<Qualification> findQualificationAutocomplete(Company company,  Boolean accept) throws Exception {		
		return dao.findQualificationAutocomplete(company, accept);
	}
	
	public List<CompanyQualifications> findByTargetClassAndTargetKey(String targetClass,Long targetKey) throws Exception {
	 	return populateAdditionalInformationList(dao.findByTargetClassAndTargetKey(targetClass, targetKey));
	}
	
	
	public List<CompanyQualifications> findByTargetClassAndTargetKeyAndAccept(String targetClass,Long targetKey,Boolean accept) throws Exception {
	 	return dao.findByTargetClassAndTargetKeyAndAccept(targetClass, targetKey,accept);
	}
	
	public Integer countfindByCompany(Company company, Qualification qualification, Boolean accept) throws Exception {
		return dao.countfindByCompany(company, qualification, accept);
	}
	
	public Integer countfindByTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication, Qualification qualification, Boolean accept) throws Exception {
		return dao.countfindByTrainingProviderApplication(trainingProviderApplication, qualification, accept);
	}
	
	public List<CompanyQualifications> populateAdditionalInformationList(List<CompanyQualifications> companyQualificationsList) throws Exception{
		for (CompanyQualifications companyQualifications : companyQualificationsList) {
			populateAdditionalInformation(companyQualifications);
		}
		return companyQualificationsList;
	}
	
	public CompanyQualifications populateAdditionalInformation(CompanyQualifications companyQualifications) throws Exception{
		if (companyQualifications.getId() != null) {
			// will need to change
			companyQualifications.setDocList(new ArrayList<Doc>());
			if (companyQualifications.getTrainingProviderDocParent() != null && companyQualifications.getTrainingProviderDocParent().getDoc() != null && companyQualifications.getTrainingProviderDocParent().getDoc().getId() != null) {
				companyQualifications.getDocList().add(DocService.instance().findByKeyWithJoins(companyQualifications.getTrainingProviderDocParent().getDoc().getId()));
			}
			
		}
		return companyQualifications;
	}
	
	public CompanyQualifications findByQualification(Qualification qualification, Boolean accept) throws Exception {
		return dao.findByQualification(qualification, accept);
	}
	public Integer countfindByQualification(Qualification qualification, Boolean accept) throws Exception {
		return dao.countfindByQualification(qualification, accept);
	}
	
	public List<Company> findCompaniesAssignedToQualificationAndAccept(String desc, Qualification qualification, Boolean accept) throws Exception {
		return dao.findCompaniesAssignedToQualificationAndAccept(desc, qualification, accept);
	}
	public Integer countCompaniesAssignedToQualificationAndAccept(Qualification qualification, Boolean accept) throws Exception {
		return dao.countCompaniesAssignedToQualificationAndAccept(qualification, accept);
	}
	
	public List<Company> findCompaniesAssignedToQualificationAndTpApplicationIsApproved(String desc, Qualification qualification, Boolean accept) throws Exception {
		return dao.findCompaniesAssignedToQualificationAndTpApplicationIsApproved(desc, qualification, accept);
	}
	public Integer countCompaniesAssignedToQualificationAndTpApplicationIsApproved(Qualification qualification, Boolean accept) throws Exception {
		return dao.countCompaniesAssignedToQualificationAndTpApplicationIsApproved(qualification, accept);
	}
	
	public List<Company> findCompaniesAssignedToQualificationAndTpApplicationIsApprovedAndBeforeExpiryDate(String desc, Qualification qualification, Boolean accept, Date date) throws Exception {
		return dao.findCompaniesAssignedToQualificationAndTpApplicationIsApprovedAndBeforeExpiryDate(desc, qualification, accept, date);
	}
	public Integer countCompaniesAssignedToQualificationAndTpApplicationIsApprovedAndBeforeExpiryDate(Qualification qualification, Boolean accept, Date date) throws Exception {
		return dao.countCompaniesAssignedToQualificationAndTpApplicationIsApprovedAndBeforeExpiryDate(qualification, accept, date);
	}
	
	public List<Company> findCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(String desc, Qualification qualification, Boolean accept, Date date, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
		return dao.findCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(desc, qualification, accept, date, accreditationApplicationTypeEnum);
	}
	
	public List<TrainingProviderApplication> findTrainingProviderApllicationsByCompanyIdAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(Long companyId, Qualification qualification, Boolean accept, Date date, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
		return dao.findTrainingProviderApllicationsByCompanyIdAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(companyId, qualification, accept, date, accreditationApplicationTypeEnum);
	}
	
	public Integer countCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(Qualification qualification, Boolean accept, Date date, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
		return dao.countCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(qualification, accept, date, accreditationApplicationTypeEnum);
	}
	
	public List<TrainingProviderApplication> findTrainingProviderApllicationsByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(String desc, Qualification qualification, Boolean accept, Date date, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
		return dao.findTrainingProviderApllicationsByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(desc, qualification, accept, date, accreditationApplicationTypeEnum);
	}
	
	public Integer countQualificationAccptedByTargetClassAndKey(String targetClass, Long targetKey, Qualification qualification, Boolean accept) throws Exception {
		return dao.countQualificationAccptedByTargetClassAndKey(targetClass, targetKey, qualification, accept);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> allCompanyQualificationsByProviderApplicationId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql="select o from CompanyQualifications o left join fetch o.company c where o.targetClass = :targetClass  and o.targetKey = :targetKey";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return (List<CompanyQualifications>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

	}
	
	public int countAllCompanyQualificationsByProviderApplicationId(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from CompanyQualifications o where o.targetClass = :targetClass  and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyQualifications> allCompanyQualificationsByProviderApplicationIdAndManuallyEntered(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey, Boolean manuallyAdded) throws Exception {
		String hql="select o from CompanyQualifications o left join fetch o.company c where o.targetClass = :targetClass  and o.targetKey = :targetKey and o.manuallyAdded = :manuallyAdded";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		filters.put("manuallyAdded", manuallyAdded);
		return (List<CompanyQualifications>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

	}
	
	public int countAllCompanyQualificationsByProviderApplicationIdAndManuallyEntered(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from CompanyQualifications o where o.targetClass = :targetClass  and o.targetKey = :targetKey and o.manuallyAdded = :manuallyAdded";
		return dao.countWhere(filters, hql);
	}
	
	public Integer countByApplicationAndQualification(String targetClass, Long targetKey, Long qualificationId, Long companyId) throws Exception {
		return dao.countByApplicationAndQualification(targetClass, targetKey, qualificationId, companyId);
	}
}
