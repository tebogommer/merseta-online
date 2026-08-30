package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ToolListDAO;
import haj.com.entity.lookup.Tool;
import haj.com.entity.lookup.ToolList;
import haj.com.entity.lookup.ToolListCategory;
import haj.com.entity.lookup.ToolListTools;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class ToolListService extends AbstractService {
	/** The dao. */
	private ToolListDAO dao = new ToolListDAO();

	/**
	 * Get all ToolList
 	 * @author TechFinium 
 	 * @see   ToolList
 	 * @return a list of {@link haj.com.entity.ToolList}
	 * @throws Exception the exception
 	 */
	public List<ToolList> allToolList() throws Exception {
	  	return dao.allToolList();
	}


	/**
	 * Create or update ToolList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ToolList
	 */
	public void create(ToolList entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	

	public void create(ToolList entity, List<Tool> unitStandards) throws Exception {
		if (entity.getId() == null) {
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			dataEntities.add(entity);
			for (Tool unitStandard : unitStandards) {
				dataEntities.add(new ToolListTools(entity, unitStandard));
			}
			this.dao.createBatch(dataEntities);
		} else {
			this.dao.update(entity);

			List<ToolListTools> list = allToolListTools(entity);

			dao.deleteBatch((List<IDataEntity>) (List<?>) list);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			for (Tool unitStandard : unitStandards) {
				dataEntities.add(new ToolListTools(entity, unitStandard));
			}
			this.dao.createBatch(dataEntities);
		}
	}

	public List<ToolListCategory> allToolListCategory(ToolList toolList) throws Exception {
		return dao.allToolListCategory(toolList);
	}
	
	public List<ToolListTools> allToolListTools(ToolList toolList) throws Exception {
		return dao.allToolListTools(toolList);
	}

	/**
	 * Update  ToolList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ToolList
	 */
	public void update(ToolList entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ToolList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ToolList
	 */
	public void delete(ToolList entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ToolList}
	 * @throws Exception the exception
	 * @see    ToolList
	 */
	public ToolList findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ToolList by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ToolList}
	 * @throws Exception the exception
	 * @see    ToolList
	 */
	public List<ToolList> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ToolList
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ToolList}
	 * @throws Exception the exception
	 */
	public List<ToolList> allToolList(int first, int pageSize) throws Exception {
		return dao.allToolList(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ToolList for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ToolList
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ToolList.class);
	}
	
    /**
     * Lazy load ToolList with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ToolList class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ToolList} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ToolList> allToolList(Class<ToolList> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ToolList>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ToolList for lazy load with filters
     * @author TechFinium 
     * @param entity ToolList class
     * @param filters the filters
     * @return Number of rows in the ToolList entity
     * @throws Exception the exception     
     */	
	public int count(Class<ToolList> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<ToolList> findByToolList(ToolList toolList) {
		return dao.findByToolList(toolList.getId());
	}
}
