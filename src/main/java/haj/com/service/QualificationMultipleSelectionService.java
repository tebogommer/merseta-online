package haj.com.service;

import java.util.ArrayList;
import java.util.List;

import haj.com.dao.QualificationMultipleSelectionDAO;
import haj.com.entity.DateChangeMultipleSelection;
import haj.com.entity.QualificationMultipleSelection;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class QualificationMultipleSelectionService extends AbstractService {
	/** The dao. */
	private QualificationMultipleSelectionDAO dao = new QualificationMultipleSelectionDAO();
	
	/** Instance of service level */
	private static QualificationMultipleSelectionService qualificationMultipleSelectionService;
	public static synchronized QualificationMultipleSelectionService instance() {
		if (qualificationMultipleSelectionService == null) {
			qualificationMultipleSelectionService = new QualificationMultipleSelectionService();
		}
		return qualificationMultipleSelectionService;
	}

	/**
	 * Get all QualificationMultipleSelection
 	 * @author TechFinium 
 	 * @see   QualificationMultipleSelection
 	 * @return a list of {@link haj.com.entity.QualificationMultipleSelection}
	 * @throws Exception the exception
 	 */
	public List<QualificationMultipleSelection> allQualificationMultipleSelection() throws Exception {
	  	return dao.allQualificationMultipleSelection();
	}


	/**
	 * Create or update QualificationMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     QualificationMultipleSelection
	 */
	public void create(QualificationMultipleSelection entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  QualificationMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationMultipleSelection
	 */
	public void update(QualificationMultipleSelection entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  QualificationMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationMultipleSelection
	 */
	public void delete(QualificationMultipleSelection entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.QualificationMultipleSelection}
	 * @throws Exception the exception
	 * @see    QualificationMultipleSelection
	 */
	public QualificationMultipleSelection findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find QualificationMultipleSelection by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.QualificationMultipleSelection}
	 * @throws Exception the exception
	 * @see    QualificationMultipleSelection
	 */
	public List<QualificationMultipleSelection> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load QualificationMultipleSelection
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.QualificationMultipleSelection}
	 * @throws Exception the exception
	 */
	public List<QualificationMultipleSelection> allQualificationMultipleSelection(int first, int pageSize) throws Exception {
		return dao.allQualificationMultipleSelection(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of QualificationMultipleSelection for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the QualificationMultipleSelection
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(QualificationMultipleSelection.class);
	}
	
    /**
     * Lazy load QualificationMultipleSelection with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 QualificationMultipleSelection class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.QualificationMultipleSelection} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<QualificationMultipleSelection> allQualificationMultipleSelection(Class<QualificationMultipleSelection> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<QualificationMultipleSelection>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of QualificationMultipleSelection for lazy load with filters
     * @author TechFinium 
     * @param entity QualificationMultipleSelection class
     * @param filters the filters
     * @return Number of rows in the QualificationMultipleSelection entity
     * @throws Exception the exception     
     */	
	public int count(Class<QualificationMultipleSelection> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<QualificationMultipleSelection> findByTargetKeyAndClassName(Long targetKey, String className) throws Exception {
		return dao.findByTargetKeyAndClassName(targetKey, className);
	}
	
	public List<QualificationMultipleSelection> findByTargetKeyClassNameAndProcess(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		return dao.findByTargetKeyClassNameAndProcess(targetKey, className, process);
	}

	/**
	 * Removes old links and creates new links for QualificationMultipleSelection
	 * @param id
	 * @param name
	 * @param qualificationList
	 * @return entries
	 */
	public List<QualificationMultipleSelection> removeCreateLinks(Long targetKey, String className, List<Qualification> qualificationList, Users sessionUser, ConfigDocProcessEnum process) throws Exception{
		// delete all old entries by target key and class name
		clearEntries(targetKey, className, process);
		List<QualificationMultipleSelection> entries = new ArrayList<QualificationMultipleSelection>();
		for (Qualification qualification : qualificationList) {
			QualificationMultipleSelection rms = new QualificationMultipleSelection(targetKey, className, qualification, sessionUser, process);
			entries.add(rms);
		}
		return entries;
	}

	public void clearEntries(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		entityList.addAll(findByTargetKeyClassNameAndProcess(targetKey, className, process));
		if (entityList.size() != 0) {
			dao.deleteBatch(entityList);
		}
		entityList = null;
	}
}
