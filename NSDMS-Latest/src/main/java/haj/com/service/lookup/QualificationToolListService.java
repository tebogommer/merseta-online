package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.QualificationToolListDAO;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.QualificationToolList;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class QualificationToolListService extends AbstractService {
	/** The dao. */
	private QualificationToolListDAO dao = new QualificationToolListDAO();

	/**
	 * Get all QualificationToolList
 	 * @author TechFinium 
 	 * @see   QualificationToolList
 	 * @return a list of {@link haj.com.entity.QualificationToolList}
	 * @throws Exception the exception
 	 */
	public List<QualificationToolList> allQualificationToolList() throws Exception {
	  	return dao.allQualificationToolList();
	}


	/**
	 * Create or update QualificationToolList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     QualificationToolList
	 */
	public void create(QualificationToolList entity) throws Exception  {
	
		if(coutQualification(entity.getQualification()) > 0) {
			throw new Exception("Qualification Already Linked To The Tool");
		}
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	private int coutQualification(Qualification qualification) {
		return dao.coutQualification(qualification.getId());
	}


	/**
	 * Update  QualificationToolList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationToolList
	 */
	public void update(QualificationToolList entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  QualificationToolList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationToolList
	 */
	public void delete(QualificationToolList entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.QualificationToolList}
	 * @throws Exception the exception
	 * @see    QualificationToolList
	 */
	public QualificationToolList findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find QualificationToolList by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.QualificationToolList}
	 * @throws Exception the exception
	 * @see    QualificationToolList
	 */
	public List<QualificationToolList> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load QualificationToolList
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.QualificationToolList}
	 * @throws Exception the exception
	 */
	public List<QualificationToolList> allQualificationToolList(int first, int pageSize) throws Exception {
		return dao.allQualificationToolList(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of QualificationToolList for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the QualificationToolList
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(QualificationToolList.class);
	}
	
    /**
     * Lazy load QualificationToolList with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 QualificationToolList class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.QualificationToolList} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<QualificationToolList> allQualificationToolList(Class<QualificationToolList> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<QualificationToolList>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of QualificationToolList for lazy load with filters
     * @author TechFinium 
     * @param entity QualificationToolList class
     * @param filters the filters
     * @return Number of rows in the QualificationToolList entity
     * @throws Exception the exception     
     */	
	public int count(Class<QualificationToolList> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public QualificationToolList findByQualification(Qualification qualification) {
		return dao.findByQualification(qualification.getId());
	}
}
