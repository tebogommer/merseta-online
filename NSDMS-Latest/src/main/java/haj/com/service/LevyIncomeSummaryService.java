package haj.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.LevyIncomeSummaryDAO;
import haj.com.entity.LevyIncomeSummary;
import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class LevyIncomeSummaryService.
 */
public class LevyIncomeSummaryService extends AbstractService {
	
	
	/** The dao. */
	private LevyIncomeSummaryDAO dao = new LevyIncomeSummaryDAO();

	/**
	 * Get all LevyIncomeSummary.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.LevyIncomeSummary}
	 * @throws Exception the exception
	 * @see LevyIncomeSummary
	 */
	public List<LevyIncomeSummary> allLevyIncomeSummary() throws Exception {
		return dao.allLevyIncomeSummary();
	}
	
	public List<LevyIncomeSummary> allLevyIncomeSummaryByType(ReportPropertiesEnum reportPropertiesEnum) throws Exception {
		return dao.allLevyIncomeSummaryByType(reportPropertiesEnum);
	}

	/**
	 * Create or update LevyIncomeSummary.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LevyIncomeSummary
	 */
	public void create(LevyIncomeSummary entity) throws Exception {
		if (entity.getId() == null) {
			entity.setCreateDate(new java.util.Date());
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LevyIncomeSummary.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LevyIncomeSummary
	 */
	public void update(LevyIncomeSummary entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LevyIncomeSummary.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LevyIncomeSummary
	 */
	public void delete(LevyIncomeSummary entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LevyIncomeSummary}
	 * @throws Exception the exception
	 * @see LevyIncomeSummary
	 */
	public LevyIncomeSummary findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LevyIncomeSummary by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LevyIncomeSummary}
	 * @throws Exception the exception
	 * @see LevyIncomeSummary
	 */
	public List<LevyIncomeSummary> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LevyIncomeSummary.
	 *
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LevyIncomeSummary}
	 * @throws Exception the exception
	 */
	public List<LevyIncomeSummary> allLevyIncomeSummary(int first, int pageSize) throws Exception {
		return dao.allLevyIncomeSummary(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LevyIncomeSummary for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the LevyIncomeSummary
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LevyIncomeSummary.class);
	}

	/**
	 * Lazy load LevyIncomeSummary with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1    LevyIncomeSummary class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LevyIncomeSummary} containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LevyIncomeSummary> allLevyIncomeSummary(Class<LevyIncomeSummary> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LevyIncomeSummary>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LevyIncomeSummary for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity  LevyIncomeSummary class
	 * @param filters the filters
	 * @return Number of rows in the LevyIncomeSummary entity
	 * @throws Exception the exception
	 */
	public int count(Class<LevyIncomeSummary> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public void lpmFM005Download() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		// Service Layers
		UsersService usersService= new UsersService();
		
		// Text Field Parameters
//		Users learnerUsers = UsersService.findByUsers();

		
		// TABLE LISTS

		
		// Jasper Parameters for Text Fields
			params.put("learner", "learnerUsers");

			params.put("employerContactPerson", "employerContactPersonUsers");

			params.put("skillsDevProviderContactPerson", "skillsDevProviderContactPersonUsers");

			params.put("new_employer", "newEmployerCompany");

			params.put("skillsDevProvider", "skillsDevProviderCompany");

			params.put("ofo_codes", "OfoCodes");

		params.put("applicationReason", "String");
		params.put("applicationReason", "String");
		params.put("contractAgreementNum", "String");
		params.put("skillsDevProviderAccreditationNum", "String");

		params.put("isMinor", "Boolean");

		// Jasper Parameters for Table Lists
//		if (!addressList.isEmpty()) {
//			params.put("AddressList", new JRBeanCollectionDataSource(addressList));
//		}

		JasperService.addFormTemplateParams(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-FM-005.jasper", params, "LPM-FM-005.pdf");
	}
	
	public void lpmFM018BDownload() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		// Service Layers
		UsersService usersService= new UsersService();
		
		// Text Field Parameters
//		Users learnerUsers = UsersService.findByUsers();

		
		// TABLE LISTS

		
		// Jasper Parameters for Text Fields
			params.put("learner", "learnerUsers");
		
			params.put("employerContactPerson", "employerContactPersonUsers");

			params.put("new_employer", "newEmployerCompany");
		
		params.put("applicationReason", "String");
		params.put("learnershipNum", "String");
		params.put("leanership", "String");
		
		params.put("isMinor", "Boolean");

		// Jasper Parameters for Table Lists
//		if (!addressList.isEmpty()) {
//			params.put("AddressList", new JRBeanCollectionDataSource(addressList));
//		}

		JasperService.addFormTemplateParams(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-FM-018-B.jasper", params, "LPM-FM-018-B.pdf");
	}
	
	public void lpmAD008Download() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		// Service Layers
		UsersService usersService= new UsersService();
		
		// Text Field Parameters
//		Users learnerUsers = UsersService.findByUsers();
		
		
		// TABLE LISTS
		
		
		// Jasper Parameters for Text Fields
		params.put("company_id", "Long");
		params.put("learners_id", "Long");
		
		// Jasper Parameters for Table Lists
//		if (!addressList.isEmpty()) {
//			params.put("AddressList", new JRBeanCollectionDataSource(addressList));
//		}
		
		JasperService.addFormTemplateParams(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-AD-008.jasper", params, "LPM-AD-008.pdf");
	}
	
	public void lpmAD002Download() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		// Service Layers
		UsersService usersService= new UsersService();
		
		// Text Field Parameters
//		Users learnerUsers = UsersService.findByUsers();
		
		
		// TABLE LISTS
		
		
		// Jasper Parameters for Text Fields
		params.put("company_id", "Long");
		params.put("learners_id", "Long");
		
		// Jasper Parameters for Table Lists
//		if (!addressList.isEmpty()) {
//			params.put("AddressList", new JRBeanCollectionDataSource(addressList));
//		}
		
		JasperService.addFormTemplateParams(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-AD-002.jasper", params, "LPM-AD-002.pdf");
	}
}