package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.CompanyLearnersTransferDAO;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.framework.AbstractService;

public class CompanyLearnersTransferService extends AbstractService {
	
	/** The dao. */
	private CompanyLearnersTransferDAO dao = new CompanyLearnersTransferDAO();
	
	/** Instance of service level */
	private static CompanyLearnersTransferService companyLearnersTransferService;
	public static synchronized CompanyLearnersTransferService instance() {
		if (companyLearnersTransferService == null) {
			companyLearnersTransferService = new CompanyLearnersTransferService();
		}
		return companyLearnersTransferService;
	}
	
	/** Service levels */
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private CompanyService companyService = new CompanyService();
	private UsersService usersService = new UsersService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();

	/**
	 * Get all CompanyLearnersTransfer
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTransfer
	 * @return a list of {@link haj.com.entity.CompanyLearnersTransfer}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersTransfer> allCompanyLearnersTransfer() throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersTransfer());
	}

	/**
	 * Create or update CompanyLearnersTransfer.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTransfer
	 */
	public void create(CompanyLearnersTransfer entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update CompanyLearnersTransfer.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTransfer
	 */
	public void update(CompanyLearnersTransfer entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyLearnersTransfer.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTransfer
	 */
	public void delete(CompanyLearnersTransfer entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyLearnersTransfer}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTransfer
	 */
	public CompanyLearnersTransfer findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}

	/**
	 * Find object by company learner id
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see CompanyLearnersTransfer
	 * @return a {@link haj.com.entity.CompanyLearnersTransfer}
	 * @throws Exception
	 *             global exception
	 */
	public List<CompanyLearnersTransfer> findByCompanyLearnerID(Long id) throws Exception {
		return populateAdditionalInformationList(dao.findByCompanyLearnerID(id));
	}

	/**
	 * Find CompanyLearnersTransfer by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyLearnersTransfer}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTransfer
	 */
	public List<CompanyLearnersTransfer> findByName(String desc) throws Exception {
		return populateAdditionalInformationList(dao.findByName(desc));
	}

	/**
	 * Lazy load CompanyLearnersTransfer
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearnersTransfer}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersTransfer> allCompanyLearnersTransfer(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersTransfer(Long.valueOf(first), pageSize));
	}

	/**
	 * Get count of CompanyLearnersTransfer for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CompanyLearnersTransfer
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyLearnersTransfer.class);
	}

	/**
	 * Lazy load CompanyLearnersTransfer with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CompanyLearnersTransfer class
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
	 * @return a list of {@link haj.com.entity.CompanyLearnersTransfer} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTransfer> allCompanyLearnersTransfer(Class<CompanyLearnersTransfer> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<CompanyLearnersTransfer>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));

	}

	/**
	 * Get count of CompanyLearnersTransfer for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CompanyLearnersTransfer class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyLearnersTransfer entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyLearnersTransfer> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	/**
	 * Populates additional information against CompanyLearnersTransfer refer to method: populateAdditionalInformation
	 * @param companyLearnersTransferList a list of CompanyLearnersTransfer
	 * @return List<CompanyLearnersTransfer>
	 * @throws Exception
	 */
	public List<CompanyLearnersTransfer> populateAdditionalInformationList(List<CompanyLearnersTransfer> companyLearnersTransferList) throws Exception{
		for (CompanyLearnersTransfer companyLearnersTransfer : companyLearnersTransferList) {
			populateAdditionalInformation(companyLearnersTransfer);
		}
		return companyLearnersTransferList;
	}
	
	/**
	 * Populates additional information against CompanyLearnersTransfer
	 * 
	 * @param companyLearnersTransfer
	 * @return companyLearnersTransfer
	 * @see CompanyLearnersTransfer
	 * @see Company
	 * @see Users
	 * @see TrainingProviderApplication
	 * @throws Exception
	 */
	private CompanyLearnersTransfer populateAdditionalInformation(CompanyLearnersTransfer companyLearnersTransfer) throws Exception{
		// populate company learners link
		if (companyLearnersTransfer.getCompanyLearners() != null && companyLearnersTransfer.getCompanyLearners().getId() != null) {
			companyLearnersTransfer.setCompanyLearners(companyLearnersService.findByKey(companyLearnersTransfer.getCompanyLearners().getId()));
		}
		// current company assigned to for both provider or work place transfer
		if (companyLearnersTransfer.getCurrentCompanyAssigned() != null && companyLearnersTransfer.getCurrentCompanyAssigned().getId() != null) {
			companyLearnersTransfer.setCurrentCompanyAssigned(companyService.findByKey(companyLearnersTransfer.getCurrentCompanyAssigned().getId()));
		}
		// original provider application assigned (only for provider transfer)
		if (companyLearnersTransfer.getOrginalTrainingProviderApplication() != null && companyLearnersTransfer.getOrginalTrainingProviderApplication().getId() != null) {
			companyLearnersTransfer.setOrginalTrainingProviderApplication(trainingProviderApplicationService.findByKey(companyLearnersTransfer.getOrginalTrainingProviderApplication().getId()));
		}
		// company learner being transfered to for both provider or work place transfer
		if (companyLearnersTransfer.getTransferToCompany() != null && companyLearnersTransfer.getTransferToCompany().getId() != null) {
			companyLearnersTransfer.setTransferToCompany(companyService.findByKey(companyLearnersTransfer.getTransferToCompany().getId()));		
		}
		// provider application learner being transfered to  (only for provider transfer)
		if (companyLearnersTransfer.getTransferTrainingProviderApplication() != null && companyLearnersTransfer.getTransferTrainingProviderApplication().getId() != null) {
			companyLearnersTransfer.setTransferTrainingProviderApplication(trainingProviderApplicationService.findByKey(companyLearnersTransfer.getTransferTrainingProviderApplication().getId()));
		}
		// learner information
		if (companyLearnersTransfer.getLearnerUser() != null && companyLearnersTransfer.getLearnerUser().getId() != null) {
			companyLearnersTransfer.setLearnerUser(usersService.findByKey(companyLearnersTransfer.getLearnerUser().getId()));
		}
		// transfer representative information
		if (companyLearnersTransfer.getTransferCompanyRepresentativeUser() != null && companyLearnersTransfer.getTransferCompanyRepresentativeUser().getId() != null) {
			companyLearnersTransfer.setTransferCompanyRepresentativeUser(usersService.findByKey(companyLearnersTransfer.getTransferCompanyRepresentativeUser().getId()));
		}
		return companyLearnersTransfer;
	}
}
