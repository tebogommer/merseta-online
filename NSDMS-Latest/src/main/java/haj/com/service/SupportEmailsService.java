package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SupportEmailsDAO;
import haj.com.entity.SupportEmails;
import haj.com.entity.enums.SupportTypeEnum;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

public class SupportEmailsService extends AbstractService {
	/** The dao. */
	private SupportEmailsDAO dao = new SupportEmailsDAO();

	/**
	 * Get all SupportEmails
 	 * @author TechFinium 
 	 * @see   SupportEmails
 	 * @return a list of {@link haj.com.entity.SupportEmails}
	 * @throws Exception the exception
 	 */
	public List<SupportEmails> allSupportEmails() throws Exception {
	  	return dao.allSupportEmails();
	}


	/**
	 * Create or update SupportEmails.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SupportEmails
	 */
	public void create(SupportEmails entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SupportEmails.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SupportEmails
	 */
	public void update(SupportEmails entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SupportEmails.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SupportEmails
	 */
	public void delete(SupportEmails entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SupportEmails}
	 * @throws Exception the exception
	 * @see    SupportEmails
	 */
	public SupportEmails findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SupportEmails by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SupportEmails}
	 * @throws Exception the exception
	 * @see    SupportEmails
	 */
	public List<SupportEmails> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SupportEmails
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SupportEmails}
	 * @throws Exception the exception
	 */
	public List<SupportEmails> allSupportEmails(int first, int pageSize) throws Exception {
		return dao.allSupportEmails(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SupportEmails for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SupportEmails
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SupportEmails.class);
	}
	
    /**
     * Lazy load SupportEmails with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SupportEmails class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SupportEmails} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SupportEmails> allSupportEmails(Class<SupportEmails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SupportEmails>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SupportEmails for lazy load with filters
     * @author TechFinium 
     * @param entity SupportEmails class
     * @param filters the filters
     * @return Number of rows in the SupportEmails entity
     * @throws Exception the exception     
     */	
	public int count(Class<SupportEmails> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<SupportEmails> findByRole(SupportTypeEnum supportType) throws Exception { 
		return dao.findByRole(supportType);
	}
	
	public List<SupportEmails> applicationSupport() throws Exception {  
		return findByRole(SupportTypeEnum.ApplicationSupport);
	}
	
	public void reportError(String subject, String body, Exception exception) {
		try {
			String err = "<h4>"+exception.getMessage()+"</h4>";
			StackTraceElement[] s = exception.getStackTrace();
			for (StackTraceElement se : s) {
				err += "<b>Class:</b> " + se.getClassName() + " <b>Method:</b> " +se.getMethodName() + " <b>LineNumber:</b> "+ se.getLineNumber() + "<br/>";
			}
			body = body + err;
			List<SupportEmails> l = applicationSupport();
			for (SupportEmails supportEmails : l) {
				GenericUtility.sendMail(supportEmails.getEmail(), subject, body, null);
			}
					
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
}
