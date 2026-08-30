package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.DocByteDAO;
import haj.com.entity.DocByte;
import haj.com.framework.AbstractService;

public class DocByteService extends AbstractService {
	/** The dao. */
	private DocByteDAO dao = new DocByteDAO();

	/**
	 * Get all DocByte
 	 * @author TechFinium 
 	 * @see   DocByte
 	 * @return a list of {@link haj.com.entity.DocByte}
	 * @throws Exception the exception
 	 */
	public List<DocByte> allDocByte() throws Exception {
	  	return dao.allDocByte();
	}


	/**
	 * Create or update DocByte.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DocByte
	 */
	public void create(DocByte entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DocByte.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DocByte
	 */
	public void update(DocByte entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DocByte.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DocByte
	 */
	public void delete(DocByte entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DocByte}
	 * @throws Exception the exception
	 * @see    DocByte
	 */
	public DocByte findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DocByte by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DocByte}
	 * @throws Exception the exception
	 * @see    DocByte
	 */
	public List<DocByte> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DocByte
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DocByte}
	 * @throws Exception the exception
	 */
	public List<DocByte> allDocByte(int first, int pageSize) throws Exception {
		return dao.allDocByte(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DocByte for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DocByte
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DocByte.class);
	}
	
    /**
     * Lazy load DocByte with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DocByte class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DocByte} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DocByte> allDocByte(Class<DocByte> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DocByte>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DocByte for lazy load with filters
     * @author TechFinium 
     * @param entity DocByte class
     * @param filters the filters
     * @return Number of rows in the DocByte entity
     * @throws Exception the exception     
     */	
	public int count(Class<DocByte> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
