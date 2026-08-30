package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.BlankDAO;
import haj.com.entity.Address;
import haj.com.entity.Blank;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Signoff;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankService.
 */
public class BlankService extends AbstractService {
	/** The dao. */
	private BlankDAO dao = new BlankDAO();

	/**
	 * Get all Blank.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Blank}
	 * @throws Exception the exception
	 * @see Blank
	 */
	public List<Blank> allBlank() throws Exception {
		return dao.allBlank();
	}

	/**
	 * Create or update Blank.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see Blank
	 */
	public void create(Blank entity) throws Exception {
		if (entity.getId() == null) {
			entity.setCreateDate(new java.util.Date());
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update Blank.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see Blank
	 */
	public void update(Blank entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Blank.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see Blank
	 */
	public void delete(Blank entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Blank}
	 * @throws Exception the exception
	 * @see Blank
	 */
	public Blank findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find Blank by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Blank}
	 * @throws Exception the exception
	 * @see Blank
	 */
	public List<Blank> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Blank.
	 *
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Blank}
	 * @throws Exception the exception
	 */
	public List<Blank> allBlank(int first, int pageSize) throws Exception {
		return dao.allBlank(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Blank for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Blank
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(Blank.class);
	}

	/**
	 * Lazy load Blank with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1    Blank class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.Blank} containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Blank> allBlank(Class<Blank> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Blank>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of Blank for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity  Blank class
	 * @param filters the filters
	 * @return Number of rows in the Blank entity
	 * @throws Exception the exception
	 */
	public int count(Class<Blank> entity, Map<String, Object> filters) throws Exception {
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