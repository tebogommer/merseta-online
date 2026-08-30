package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.AssessorModeratorCompanyDAO;
import haj.com.entity.AssessorModeratorCompany;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class AssessorModeratorCompanyService.
 */
public class AssessorModeratorCompanyService extends AbstractService {
	/** The dao. */
	private AssessorModeratorCompanyDAO dao = new AssessorModeratorCompanyDAO();
	
	/** The Service. */
	private DocService docService = new DocService();
	
	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/**
	 * Get all AssessorModeratorCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.AssessorModeratorCompany}
	 * @throws Exception the exception
	 * @see   AssessorModeratorCompany
	 */
	public List<AssessorModeratorCompany> allAssessorModeratorCompany() throws Exception {
	  	return dao.allAssessorModeratorCompany();
	}


	/**
	 * Create or update AssessorModeratorCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AssessorModeratorCompany
	 */
	public void create(AssessorModeratorCompany entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AssessorModeratorCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AssessorModeratorCompany
	 */
	public void update(AssessorModeratorCompany entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AssessorModeratorCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AssessorModeratorCompany
	 */
	public void delete(AssessorModeratorCompany entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AssessorModeratorCompany}
	 * @throws Exception the exception
	 * @see    AssessorModeratorCompany
	 */
	public AssessorModeratorCompany findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}
	
	/**
	 * Find By AssessorModerator user .
	 *
	 * @param user the user
	 * @return List<AssessorModeratorCompany>
	 * @throws Exception the exception
	 */
	public List<AssessorModeratorCompany> findByAssessorModerator(Users user) throws Exception {
		return resolveDocs(dao.findByAssessorModerator(user.getId()));
	}
	
	/**
	 * Find By AssessorModerator user and ForAMApplication .
	 *
	 * @param user the user
	 * @return List<AssessorModeratorCompany>
	 * @throws Exception the exception
	 */
	public List<AssessorModeratorCompany> findByAssessorModeratorAndForAMApplication(Long userID,Long amAppID) throws Exception {
		return resolveDocs(dao.findByAssessorModeratorAndForAMApplication(userID,amAppID));
	}

	/**
	 * Find AssessorModeratorCompany by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AssessorModeratorCompany}
	 * @throws Exception the exception
	 * @see    AssessorModeratorCompany
	 */
	public List<AssessorModeratorCompany> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AssessorModeratorCompany.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AssessorModeratorCompany}
	 * @throws Exception the exception
	 */
	public List<AssessorModeratorCompany> allAssessorModeratorCompany(int first, int pageSize) throws Exception {
		return dao.allAssessorModeratorCompany(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AssessorModeratorCompany for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the AssessorModeratorCompany
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AssessorModeratorCompany.class);
	}
	
    /**
     * Lazy load AssessorModeratorCompany with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 AssessorModeratorCompany class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AssessorModeratorCompany} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompany> allAssessorModeratorCompany(Class<AssessorModeratorCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AssessorModeratorCompany>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AssessorModeratorCompany for lazy load with filters.
     *
     * @author TechFinium
     * @param entity AssessorModeratorCompany class
     * @param filters the filters
     * @return Number of rows in the AssessorModeratorCompany entity
     * @throws Exception the exception
     */	
	public int count(Class<AssessorModeratorCompany> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Resolve docs.
	 *
	 * @param amc the amc
	 * @return the list
	 * @throws Exception the exception
	 */
	private List<AssessorModeratorCompany> resolveDocs(List<AssessorModeratorCompany> amc) throws Exception {
		amc.forEach(a -> {
			try {
				a.getCompany().setDocs(docService.searchByCompany(a.getCompany()));
				a.getCompany().setDocs(docService.searchByCompany(a.getCompany()));
				companyService.prepareDocs(ConfigDocProcessEnum.AM, a.getCompany(), CompanyUserTypeEnum.Company);
			} catch (Exception e) {
				logger.fatal(e);
			}
		});
		return amc;
	}
	
	
	
	/**
	 * Resolve docs not reg.
	 *
	 * @param amc the amc
	 * @return the list
	 * @throws Exception the exception
	 */
	private List<AssessorModeratorCompany> resolveDocsNotReg(List<AssessorModeratorCompany> amc) throws Exception {
		amc.forEach(a -> {
			try {
				a.getCompany().setDocs(docService.searchByCompany(a.getCompany()));
			} catch (Exception e) {
				logger.fatal(e);
			}
		});
		return amc;
	}
	
	/**
	 * Do checks user company.
	 *
	 * @param user the user
	 * @param amCompanies the am companies
	 * @param companyDocCheck the company doc check
	 * @param companyInfoCheck the company info check
	 * @throws Exception the exception
	 */
	public void doChecksUserCompany(Users user, List<AssessorModeratorCompany> amCompanies, Boolean companyDocCheck, Boolean companyInfoCheck) throws Exception {
		StringBuilder exceptions = new StringBuilder("");
		if (user != null) {
			for (Doc doc : user.getDocs()) {
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					exceptions.append("Upload Document: <i>" + doc.getConfigDoc().getName() + "</i> for user:<i>"
							+ user.getFirstName() + " " + user.getLastName() + "</i><br/>");
				}
			}
		}
		if (amCompanies != null) {
			if (companyInfoCheck) {
				for (AssessorModeratorCompany amCompany : amCompanies) {
					Company companyInfo = companyService.findByKey(amCompany.getCompany().getId());
					String ex = companyInfo.getValidCompany();
					if (ex.length() > 0) {
						exceptions.append("<h3>" + amCompany.getCompany().getCompanyName() + "</h3><ul>" + ex + "</ul>");
					}
				}
			}
			if (companyDocCheck) {
				for (AssessorModeratorCompany amCompany : amCompanies) {
					for (Doc doc : amCompany.getCompany().getDocs()) {
						if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
							exceptions.append("Upload Document:<i>" + doc.getConfigDoc().getName() + "</i> for <i>"
									+ amCompany.getCompany().getCompanyName() + "</i><br/>");
						}
					}
				}
			}
		}
		if (exceptions.length() > 0) {
			throw new Exception(exceptions.toString());
		}
	}
}
