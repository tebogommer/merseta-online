package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.FormTypeDAO;
import haj.com.entity.enums.SectionEnum;
import haj.com.entity.formconfig.FormType;
import haj.com.entity.formconfig.FormTypeSections;
import haj.com.framework.AbstractService;

public class FormTypeService extends AbstractService {
	/** The dao. */
	private FormTypeDAO dao = new FormTypeDAO();

	/**
	 * Get all FormType
 	 * @author TechFinium 
 	 * @see   FormType
 	 * @return a list of {@link haj.com.entity.FormType}
	 * @throws Exception the exception
 	 */
	public List<FormType> allFormType() throws Exception {
	  	return dao.allFormType();
	}


	/**
	 * Create or update FormType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     FormType
	 */
	public void create(FormType entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
		 FormTypeSections formTypeSections = new FormTypeSections();
		 formTypeSections.setParentTemplate(entity);
		 formTypeSections.setSectionType(SectionEnum.Questions);
		 this.dao.create(formTypeSections);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  FormType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FormType
	 */
	public void update(FormType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  FormType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FormType
	 */
	public void delete(FormType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.FormType}
	 * @throws Exception the exception
	 * @see    FormType
	 */
	public FormType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find FormType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.FormType}
	 * @throws Exception the exception
	 * @see    FormType
	 */
	public List<FormType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load FormType
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.FormType}
	 * @throws Exception the exception
	 */
	public List<FormType> allFormType(int first, int pageSize) throws Exception {
		return dao.allFormType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of FormType for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the FormType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(FormType.class);
	}
	
    /**
     * Lazy load FormType with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 FormType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.FormType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<FormType> allFormType(Class<FormType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		
		return ( List<FormType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of FormType for lazy load with filters
     * @author TechFinium 
     * @param entity FormType class
     * @param filters the filters
     * @return Number of rows in the FormType entity
     * @throws Exception the exception     
     */	
	public int count(Class<FormType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
