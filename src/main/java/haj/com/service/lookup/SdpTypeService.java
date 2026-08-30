package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.SdpTypeDAO;
import haj.com.entity.lookup.SdpType;
import haj.com.framework.AbstractService;
import haj.com.service.RejectReasonMultipleSelectionService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class SdpTypeService extends AbstractService {
	
	/** The dao. */
	private SdpTypeDAO dao = new SdpTypeDAO();
	
	/** The instance */
	private static SdpTypeService sdpTypeService;
	public static synchronized SdpTypeService instance() {
		if (sdpTypeService == null) {
			sdpTypeService = new SdpTypeService();
		}
		return sdpTypeService;
	}
	
	/**
	 * Get all SdpType
 	 * @author TechFinium 
 	 * @see   SdpType
 	 * @return a list of {@link haj.com.entity.SdpType}
	 * @throws Exception the exception
 	 */
	public List<SdpType> allSdpType() throws Exception {
	  	return dao.allSdpType();
	}
	
	public void validiateDesignationUsed(SdpType entity) throws Exception{
		if (entity.getDesignation() == null || entity.getDesignation().getId() == null) {
			throw new Exception("Select Designation Before Proceeding");
		}
		if (entity.getId() == null) {
			if (countByDesignationId(entity.getDesignation().getId()) > 0) {
				throw new Exception("Designation Already Assigned, Select Different Designation Before Proceeding");
			}
		} else {
			if (countByDesignationIdAndNotEqualsSdpTypeId(entity.getDesignation().getId(),entity.getId()) > 0) {
				throw new Exception("Designation Already Assigned, Select Different Designation Before Proceeding");
			}
		}
	}


	/**
	 * Create or update SdpType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SdpType
	 */
	public void create(SdpType entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SdpType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SdpType
	 */
	public void update(SdpType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SdpType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SdpType
	 */
	public void delete(SdpType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SdpType}
	 * @throws Exception the exception
	 * @see    SdpType
	 */
	public SdpType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SdpType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SdpType}
	 * @throws Exception the exception
	 * @see    SdpType
	 */
	public List<SdpType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SdpType
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SdpType}
	 * @throws Exception the exception
	 */
	public List<SdpType> allSdpType(int first, int pageSize) throws Exception {
		return dao.allSdpType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SdpType for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SdpType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SdpType.class);
	}
	
    /**
     * Lazy load SdpType with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SdpType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SdpType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SdpType> allSdpType(Class<SdpType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SdpType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SdpType for lazy load with filters
     * @author TechFinium 
     * @param entity SdpType class
     * @param filters the filters
     * @return Number of rows in the SdpType entity
     * @throws Exception the exception     
     */	
	public int count(Class<SdpType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public Integer countByDesignationId(Long designationId) throws Exception {
		return dao.countByDesignationId(designationId);
	}
	
	public Integer countByDesignationIdAndNotEqualsSdpTypeId(Long designationId, Long sdpTypeId) throws Exception {
		return dao.countByDesignationIdAndNotEqualsSdpTypeId(designationId, sdpTypeId);
	}
	
	public SdpType findByDesignationId(Long designationId) throws Exception {
		return dao.findByDesignationId(designationId);
	}
}
