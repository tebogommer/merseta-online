package haj.com.service;

import java.util.List;

import haj.com.dao.DocumentUpdateDAO;
import haj.com.entity.DocumentUpdate;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class DocumentUpdateService extends AbstractService {
	/** The dao. */
	private DocumentUpdateDAO dao = new DocumentUpdateDAO();

	/**
	 * Get all DocumentUpdate
 	 * @author TechFinium 
 	 * @see   DocumentUpdate
 	 * @return a list of {@link haj.com.entity.DocumentUpdate}
	 * @throws Exception the exception
 	 */
	public List<DocumentUpdate> allDocumentUpdate() throws Exception {
	  	return dao.allDocumentUpdate();
	}


	/**
	 * Create or update DocumentUpdate.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DocumentUpdate
	 */
	public void create(DocumentUpdate entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DocumentUpdate.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DocumentUpdate
	 */
	public void update(DocumentUpdate entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DocumentUpdate.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DocumentUpdate
	 */
	public void delete(DocumentUpdate entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DocumentUpdate}
	 * @throws Exception the exception
	 * @see    DocumentUpdate
	 */
	public DocumentUpdate findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DocumentUpdate by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DocumentUpdate}
	 * @throws Exception the exception
	 * @see    DocumentUpdate
	 */
	public List<DocumentUpdate> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DocumentUpdate
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DocumentUpdate}
	 * @throws Exception the exception
	 */
	public List<DocumentUpdate> allDocumentUpdate(int first, int pageSize) throws Exception {
		return dao.allDocumentUpdate(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DocumentUpdate for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DocumentUpdate
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DocumentUpdate.class);
	}
	
    /**
     * Lazy load DocumentUpdate with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DocumentUpdate class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DocumentUpdate} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DocumentUpdate> allDocumentUpdate(Class<DocumentUpdate> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DocumentUpdate>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DocumentUpdate for lazy load with filters
     * @author TechFinium 
     * @param entity DocumentUpdate class
     * @param filters the filters
     * @return Number of rows in the DocumentUpdate entity
     * @throws Exception the exception     
     */	
	public int count(Class<DocumentUpdate> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
