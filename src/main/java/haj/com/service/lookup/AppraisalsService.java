package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.AppraisalsDAO;
import haj.com.entity.OfoCodes;
import haj.com.entity.lookup.AppraisalCategories;
import haj.com.entity.lookup.AppraisalCategoryCode;
import haj.com.entity.lookup.AppraisalChecklist;
import haj.com.entity.lookup.Appraisals;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Tool;
import haj.com.entity.lookup.ToolListTools;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class AppraisalsService extends AbstractService {
	/** The dao. */
	private AppraisalsDAO dao = new AppraisalsDAO();

	/**
	 * Get all Appraisals
 	 * @author TechFinium 
 	 * @see   Appraisals
 	 * @return a list of {@link haj.com.entity.Appraisals}
	 * @throws Exception the exception
 	 */
	public List<Appraisals> allAppraisals() throws Exception {
	  	return dao.allAppraisals();
	}


	/**
	 * Create or update Appraisals.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Appraisals
	 */
	public void create(Appraisals entity, List<AppraisalCategories> selectedCategories) throws Exception  {
		if (entity.getId() == null) {
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			dataEntities.add(entity);
			for (AppraisalCategories selectedCategory : selectedCategories) {
				dataEntities.add(new AppraisalChecklist(entity, selectedCategory));
			}
			this.dao.createBatch(dataEntities);
		} else {
			this.dao.update(entity);

			List<AppraisalChecklist> list = allAppraisalChecklist(entity);

			dao.deleteBatch((List<IDataEntity>) (List<?>) list);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			for (AppraisalCategories selectedCategory : selectedCategories) {
				dataEntities.add(new AppraisalChecklist(entity, selectedCategory));
			}
			this.dao.createBatch(dataEntities);
		}	
	}
	
	public void createList(Appraisals entity,AppraisalCategories appraisalCategories, List<AppraisalCategoryCode> appraisalCategoryCodes) throws Exception  {
		List<Appraisals> appraisalslist =  this.dao.findByTrade(entity.getQualification());
		if(appraisalslist.size() == 0)
		{
			if (entity.getId() == null) {
				List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
				dataEntities.add(entity);
				for (AppraisalCategoryCode appraisalCategoryCode : appraisalCategoryCodes) {
					dataEntities.add(new AppraisalChecklist(entity, appraisalCategories, appraisalCategoryCode));
				}
				this.dao.createBatch(dataEntities);
			} else {
				this.dao.update(entity);
	
				List<AppraisalChecklist> list = allAppraisalChecklist(entity);
	
				dao.deleteBatch((List<IDataEntity>) (List<?>) list);
	
				List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
				for (AppraisalCategoryCode appraisalCategoryCode : appraisalCategoryCodes)  {
					dataEntities.add(new AppraisalChecklist(entity, appraisalCategories, appraisalCategoryCode));
				}
				this.dao.createBatch(dataEntities);
			}	
		}
		else if (entity.getId() != null ) 
		{
			this.dao.update(entity);
			
			List<AppraisalChecklist> list = allAppraisalChecklist(entity);

			dao.deleteBatch((List<IDataEntity>) (List<?>) list);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			for (AppraisalCategoryCode appraisalCategoryCode : appraisalCategoryCodes)  {
				dataEntities.add(new AppraisalChecklist(entity, appraisalCategories, appraisalCategoryCode));
			}
			this.dao.createBatch(dataEntities);
		}
		else
		{
			throw new Exception("Trade already exist, please update");
		}
	}
	
	


	public List<AppraisalChecklist> allAppraisalChecklist(Appraisals entity) throws Exception {
		return dao.allAppraisalChecklist(entity);
	}


	private List<Appraisals> allAppraisals(Appraisals entity) throws Exception {
		return dao.allAppraisals(entity);
	}


	/**
	 * Update  Appraisals.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Appraisals
	 */
	public void update(Appraisals entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Appraisals.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Appraisals
	 */
	public void delete(Appraisals entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Appraisals}
	 * @throws Exception the exception
	 * @see    Appraisals
	 */
	public Appraisals findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Appraisals by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Appraisals}
	 * @throws Exception the exception
	 * @see    Appraisals
	 */
	public List<Appraisals> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Appraisals
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Appraisals}
	 * @throws Exception the exception
	 */
	public List<Appraisals> allAppraisals(int first, int pageSize) throws Exception {
		return dao.allAppraisals(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Appraisals for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Appraisals
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Appraisals.class);
	}
	
    /**
     * Lazy load Appraisals with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Appraisals class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Appraisals} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Appraisals> allAppraisals(Class<Appraisals> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Appraisals>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Appraisals for lazy load with filters
     * @author TechFinium 
     * @param entity Appraisals class
     * @param filters the filters
     * @return Number of rows in the Appraisals entity
     * @throws Exception the exception     
     */	
	public int count(Class<Appraisals> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public Appraisals findByOfocodes(OfoCodes ofoCodes) {
		// TODO Auto-generated method stub
		return dao.findByOfocodes(ofoCodes);
	}
	
	public Appraisals findByQualification(Qualification ualification) {
		// TODO Auto-generated method stub
		return dao.findByQualification(ualification);
	}

}
