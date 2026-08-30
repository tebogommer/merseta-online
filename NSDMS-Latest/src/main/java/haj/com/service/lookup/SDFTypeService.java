package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SDFTypeDAO;
import haj.com.entity.Company;
import haj.com.entity.lookup.SDFType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SDFTypeService.
 */
public class SDFTypeService extends AbstractService {
	/** The dao. */
	private SDFTypeDAO dao = new SDFTypeDAO();

	/**
	 * Get all SDFType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SDFType}
	 * @throws Exception the exception
	 * @see   SDFType
	 */
	public List<SDFType> allSDFType() throws Exception {
	  	return dao.allSDFType();
	}
	
	/**
	 * All SDF type not used.
	 *
	 * @param company the company
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<SDFType> allSDFTypeNotUsed(Company company) throws Exception {
		return dao.allSDFTypeNotUsed(company);
	}
	
	
	public List<SDFType> allSDFTypeNotUsedAndNoReplacementSDF(Company company) throws Exception {
		return dao.allSDFTypeNotUsedAndNoReplacementSDF(company);
	}
	
	/**
	 * All SDF type not used count.
	 *
	 * @param company the company
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long allSDFTypeUsedCount(Company company) throws Exception {
		if (company.getRecognitionAgreement() == null) {
			return 0l;
		}
		return dao.allSDFTypeUsedCount(company);
	}

	public Long allSDFTypeUsedCount(Company company, long yesNoLookup) throws Exception {
		return dao.allSDFTypeUsedCount(company, yesNoLookup);
	}


	/**
	 * Create or update SDFType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SDFType
	 */
	public void create(SDFType entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SDFType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SDFType
	 */
	public void update(SDFType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SDFType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SDFType
	 */
	public void delete(SDFType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SDFType}
	 * @throws Exception the exception
	 * @see    SDFType
	 */
	public SDFType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SDFType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SDFType}
	 * @throws Exception the exception
	 * @see    SDFType
	 */
	public List<SDFType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SDFType.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SDFType}
	 * @throws Exception the exception
	 */
	public List<SDFType> allSDFType(int first, int pageSize) throws Exception {
		return dao.allSDFType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SDFType for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SDFType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SDFType.class);
	}
	
    /**
     * Lazy load SDFType with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SDFType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SDFType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SDFType> allSDFType(Class<SDFType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SDFType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SDFType for lazy load with filters.
     *
     * @author TechFinium
     * @param entity SDFType class
     * @param filters the filters
     * @return Number of rows in the SDFType entity
     * @throws Exception the exception
     */	
	public int count(Class<SDFType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
