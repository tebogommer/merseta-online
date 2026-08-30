package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.ConfigDocDAO;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgVerification;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.HostingCompany;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerDocRequirements;
import haj.com.entity.enums.WspDocRequirements;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigDocService.
 */
public class ConfigDocService extends AbstractService {
	/** The dao. */
	private ConfigDocDAO dao = new ConfigDocDAO();

	/** The doc service. */
	private static ConfigDocService docService = null;

	/**
	 * Instance.
	 *
	 * @return the doc service
	 */
	public static synchronized ConfigDocService instance() {
		if (docService == null) {
			docService = new ConfigDocService();
		}
		return docService;
	}

	public List<ConfigDocProcessEnum> usedConfigDocProcessEnumInTasks() throws Exception {
		return dao.usedConfigDocProcessEnumInTasks();
	}

	/**
	 * Get all ConfigDoc.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ConfigDoc}
	 * @throws Exception
	 *             the exception
	 * @see ConfigDoc
	 */
	public List<ConfigDoc> allConfigDoc() throws Exception {
		return dao.allConfigDoc();
	}

	/**
	 * All root config doc.
	 *
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ConfigDoc> allRootConfigDoc() throws Exception {
		return resolveChildren(dao.allRootConfigDoc());
	}

	/**
	 * Resolve children.
	 *
	 * @param list
	 *            the list
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	private List<ConfigDoc> resolveChildren(List<ConfigDoc> list) throws Exception {
		list.forEach(a -> {
			try {
				a.setDependants(dao.findChildren(a.getId()));
			} catch (Exception e) {
				logger.fatal(e);
			}
		});
		return list;
	}

	/**
	 * All root config doc.
	 *
	 * @param hostingCompany
	 *            the hosting company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ConfigDoc> allRootConfigDoc(HostingCompany hostingCompany) throws Exception {
		return resolveChildren(dao.allRootConfigDoc(hostingCompany.getId()));
	}

	/**
	 * Create or update ConfigDoc.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ConfigDoc
	 */
	public void create(ConfigDoc entity) throws Exception {
		if (entity.getConfigDocProcess() != null) checkIfRootAlreadyExist(entity);
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Check if root already exist.
	 *
	 * @param cd
	 *            the cd
	 * @throws Exception
	 *             the exception
	 */
	private void checkIfRootAlreadyExist(ConfigDoc cd) throws Exception {
		ConfigDoc tmp = null;
		if (cd.getId() == null) {
			if (cd.getHostingCompany() == null) tmp = dao.findByProcess(cd.getConfigDocProcess());
			else tmp = dao.findByProcessHC(cd.getConfigDocProcess(), cd.getHostingCompany().getId());
		} else {
			if (cd.getHostingCompany() == null) tmp = dao.findByProcess(cd.getId(), cd.getConfigDocProcess());
			else tmp = dao.findByProcess(cd.getId(), cd.getConfigDocProcess(), cd.getHostingCompany().getId());
		}

		if (tmp != null) {
			throw new Exception("This business process has already been configured");
		}

	}

	/**
	 * Update ConfigDoc.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ConfigDoc
	 */
	public void update(ConfigDoc entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ConfigDoc.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ConfigDoc
	 */
	public void delete(ConfigDoc entity) throws Exception {
		if (entity.getParent() != null) {
			this.dao.delete(entity);
		} else {
			List<IDataEntity> entityList = new ArrayList<IDataEntity>();
			for (ConfigDoc c : dao.findChildren(entity.getId())) {
				entityList.add(c);
			}
			entityList.add(entity);
			dao.deleteBatch(entityList);
		}
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.ConfigDoc}
	 * @throws Exception
	 *             the exception
	 * @see ConfigDoc
	 */
	public ConfigDoc findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find ConfigDoc by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.ConfigDoc}
	 * @throws Exception
	 *             the exception
	 * @see ConfigDoc
	 */
	public List<ConfigDoc> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ConfigDoc.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.ConfigDoc}
	 * @throws Exception
	 *             the exception
	 */
	public List<ConfigDoc> allConfigDoc(int first, int pageSize) throws Exception {
		return dao.allConfigDoc(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ConfigDoc for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the ConfigDoc
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(ConfigDoc.class);
	}

	/**
	 * Lazy load ConfigDoc with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            ConfigDoc class
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
	 * @return a list of {@link haj.com.entity.ConfigDoc} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigDoc> allConfigDoc(Class<ConfigDoc> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<ConfigDoc>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of ConfigDoc for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            ConfigDoc class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the ConfigDoc entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<ConfigDoc> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by process for reg.
	 *
	 * @param configDocProcess
	 *            the config doc process
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ConfigDoc> findByProcessForReg(ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessForReg(configDocProcess);
	}

	/**
	 * Find by process for reg by company user type enum.
	 *
	 * @param configDocProcess
	 *            the config doc process
	 * @param companyUserType
	 *            the company user type
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ConfigDoc> findByProcessForRegByCompanyUserTypeEnum(ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcessForRegByCompanyUserTypeEnum(configDocProcess, companyUserType);
	}

	/**
	 * Find by process not for reg.
	 *
	 * @param company
	 *            the company
	 * @param configDocProcess
	 *            the config doc process
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ConfigDoc> findByProcessNotForReg(Company company, ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessNotForReg(company, configDocProcess);
	}

	/**
	 * Find by process not uploaded.
	 *
	 * @param company
	 *            the company
	 * @param configDocProcess
	 *            the config doc process
	 * @param companyUserType
	 *            the company user type
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ConfigDoc> findByProcessNotUploaded(Company company, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcessNotUploaded(company, configDocProcess, companyUserType);
	}

	public List<ConfigDoc> findByProcessNotUploaded(BankingDetails bankingDetails, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcessNotUploaded(bankingDetails, configDocProcess, companyUserType);
	}

	public List<ConfigDoc> findByProcessNotUploaded(ExtensionRequest extensionRequest, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcessNotUploaded(extensionRequest, configDocProcess, companyUserType);
	}

	public List<ConfigDoc> findByProcessNotUploaded(DgVerification extensionRequest, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcessNotUploaded(extensionRequest, configDocProcess, companyUserType);
	}

	public List<ConfigDoc> findByProcessNotUploaded(String targetClass, Long targetKey, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcessNotUploaded(targetClass, targetKey, configDocProcess, companyUserType);
	}

	public List<ConfigDoc> findByProcessNotUploaded(String targetClass, Long targetKey, ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessNotUploaded(targetClass, targetKey, configDocProcess);
	}

	public List<ConfigDoc> findByProcessNotUploadedForReg(String targetClass, Long targetKey, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcessNotUploadedForReg(targetClass, targetKey, configDocProcess, companyUserType);
	}

	public List<ConfigDoc> findByProcessNotUploadedNotForReg(String targetClass, Long targetKey, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcessNotUploadedNotForReg(targetClass, targetKey, configDocProcess, companyUserType);
	}
	
	public List<ConfigDoc> findByProcessNotForUpload(String targetClass, Long targetKey,  ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessNotForUpload(targetClass, targetKey, configDocProcess);
	}

	public List<ConfigDoc> findByProcess(ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcess(configDocProcess, companyUserType);
	}
	
	public List<ConfigDoc> findByProcessLearners(ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcessLearners(configDocProcess, companyUserType);
	}
	
	public List<ConfigDoc> findByProcessList(ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessList(configDocProcess);
	}

	public List<ConfigDoc> findByProcessA(ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessA(configDocProcess);
	}
	
	public List<ConfigDoc> findByProcessABursary(ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessABursary(configDocProcess);
	}
	
	public List<ConfigDoc> findByProcessELearnerBursary(ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessELearnerBursary(configDocProcess);
	}
	
	public List<ConfigDoc> findByProcessADisabledBursary(ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessADisabledBursary(configDocProcess);
	}
	
	public List<ConfigDoc> findByProcessABursary(ConfigDocProcessEnum configDocProcess, LearnerDocRequirements learnerDocRequirements) throws Exception {
		return dao.findByProcessABursary(configDocProcess, learnerDocRequirements);
	}
	public List<ConfigDoc> findByProcessAELearner(ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessAELearner(configDocProcess);
	}
	
	public List<ConfigDoc> findByProcessAELearnerDisabled(ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessAELearnerDisabled(configDocProcess);
	}
	
	public List<ConfigDoc> findByProcessRoles(ProcessRoles processRoles) throws Exception {
		return dao.findByProcessRoles(processRoles.getId());
	}

	public List<ConfigDoc> findByProcessRolesNotUploaded(String targetClass, Long targetKey, ProcessRoles processRoles) throws Exception {
		return dao.findByProcessRolesNotUploaded(targetClass, targetKey, processRoles.getId());
	}
	public List<ConfigDoc> findByProcessRolesNotUploadedNotBusary(String targetClass, Long targetKey, ProcessRoles processRoles) throws Exception {
		return dao.findByProcessRolesNotUploadedNotBusary(targetClass, targetKey, processRoles.getId());
	}
	
	public ConfigDoc findByProcessByProcessRolesAndName(Long processRolesId, String name) throws Exception {
		return dao.findByProcessByProcessRolesAndName(processRolesId, name);
	}

	/**
	 * Find by process not uploaded WSP.
	 *
	 * @param wsp
	 *            the wsp
	 * @param configDocProcess
	 *            the config doc process
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ConfigDoc> findByProcessNotUploadedWSP(Wsp wsp, ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessNotUploadedWSP(wsp, configDocProcess);
	}
	
	public List<ConfigDoc> findByProcessNotUploadedNewWSP(ConfigDocProcessEnum configDocProcess) throws Exception {
		return dao.findByProcessNotUploadedNewWSP(configDocProcess);
	}
	
	public List<ConfigDoc> findByProcessWSPDocRequirements(ConfigDocProcessEnum configDocProcess, List<WspDocRequirements> wspDocRequirements) throws Exception {
		return dao.findByProcessWSPDocRequirements(configDocProcess, wspDocRequirements);
	}

	/**
	 * Find by process not uploaded user.
	 *
	 * @param user
	 *            the user
	 * @param configDocProcess
	 *            the config doc process
	 * @param companyUserType
	 *            the company user type
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ConfigDoc> findByProcessNotUploadedUser(Users user, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		return dao.findByProcessNotUploadedUser(user, configDocProcess, companyUserType);
	}
	
	public ConfigDoc findByProcessNotUploadedLearnerDocRequirements(ConfigDocProcessEnum configDocProcess, LearnerDocRequirements learnerDocRequirements, String targetClass, Long targetKey) throws Exception {
		return dao.findByProcessNotUploadedLearnerDocRequirements(configDocProcess, learnerDocRequirements, targetClass, targetKey);
	}
	
	public ConfigDoc findByProcessNotUploadedLearnerDocRequirements(ConfigDocProcessEnum configDocProcess, LearnerDocRequirements learnerDocRequirements) throws Exception {
		return dao.findByProcessNotUploadedLearnerDocRequirements(configDocProcess, learnerDocRequirements);
	}
	
	public ConfigDoc findByProcessUploadedLearnerDocRequirements(ConfigDocProcessEnum configDocProcess, LearnerDocRequirements learnerDocRequirements, String targetClass, Long targetKey) throws Exception {
		return dao.findByProcessUploadedLearnerDocRequirements(configDocProcess, learnerDocRequirements, targetClass, targetKey);
	}
}
