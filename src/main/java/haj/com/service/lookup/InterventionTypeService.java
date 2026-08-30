package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.InterventionTypeDAO;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionTypeService.
 */
public class InterventionTypeService extends AbstractService {
	/** The dao. */
	private InterventionTypeDAO dao = new InterventionTypeDAO();

	/**
	 * Get all InterventionType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InterventionType}
	 * @throws Exception
	 *             the exception
	 * @see InterventionType
	 */
	public List<InterventionType> allInterventionType() throws Exception {
		return dao.allInterventionType();
	}
	
	public List<InterventionType> allInterventionTypeNoARPL() throws Exception {
		return dao.allInterventionTypeNoARPL();
	}
	
	public List<InterventionType> allInterventionTypeWithARPL() throws Exception {
		return dao.allInterventionTypeWithARPL();
	}
	
	public List<InterventionType> allInterventionTypeByNonMerseta(Boolean registrationByNonMerseta) throws Exception {
		return dao.allInterventionTypeByNonMerseta(registrationByNonMerseta);
	}
	
	public List<InterventionType> allInterventionTypeWithNoAccreditaionNoARPL(Boolean forSdpAccreditaion) throws Exception {
		return dao.allInterventionTypeWithNoAccreditaionNoARPL(forSdpAccreditaion);
	}
	
	public List<InterventionType> allInterventionTypeWithNoAccreditaion(Boolean forSdpAccreditaion) throws Exception {
		return dao.allInterventionTypeWithNoAccreditaion(forSdpAccreditaion);
	}
	
	public List<InterventionType> allInterventionTypeWithNoAccreditaionWithARPL(Boolean forSdpAccreditaion) throws Exception {
		return dao.allInterventionTypeWithNoAccreditaionWithARPL(forSdpAccreditaion);
	}
	
	public List<InterventionType> allInterventionTypePivotal() throws Exception {
		return dao.allInterventionTypePivotal();
	}
	
	public List<InterventionType> allInterventionTypeNonPivotal() throws Exception {
		return dao.allInterventionTypeNonPivotal();
	}
	
	public List<InterventionType> allInterventionTypeWspSelection() throws Exception {
		return dao.allInterventionTypeWspSelection();
	}
	
	public List<InterventionType> allInterventionTypeAtrSelection() throws Exception {
		return dao.allInterventionTypeAtrSelection();
	}

	/**
	 * Create or update InterventionType.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see InterventionType
	 */
	public void create(InterventionType entity) throws Exception {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update InterventionType.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see InterventionType
	 */
	public void update(InterventionType entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete InterventionType.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see InterventionType
	 */
	public void delete(InterventionType entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.InterventionType}
	 * @throws Exception
	 *             the exception
	 * @see InterventionType
	 */
	public InterventionType findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	public InterventionType findByKey(List<Long> id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find InterventionType by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.InterventionType}
	 * @throws Exception
	 *             the exception
	 * @see InterventionType
	 */
	public List<InterventionType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<InterventionType> findByInterventionType(InterventionTypeEnum interventionType) throws Exception {
		return dao.findByInterventionType(interventionType);
	}

	/**
	 * Lazy load InterventionType.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.InterventionType}
	 * @throws Exception
	 *             the exception
	 */
	public List<InterventionType> allInterventionType(int first, int pageSize) throws Exception {
		return dao.allInterventionType(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of InterventionType for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the InterventionType
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(InterventionType.class);
	}

	/**
	 * Lazy load InterventionType with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            InterventionType class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.InterventionType} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionType> allInterventionType(Class<InterventionType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<InterventionType>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of InterventionType for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            InterventionType class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the InterventionType entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<InterventionType> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by code.
	 *
	 * @param code
	 *            the code
	 * @return the intervention type
	 * @throws Exception
	 *             the exception
	 */
	public InterventionType findByCode(String code) throws Exception {
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(code.trim());
	}

	/**
	 * Locates intervention Type By Enum
	 * 
	 * @param type
	 * @return List<InterventionType>
	 * @throws Exception
	 */
	public List<InterventionType> findByInterventionTypeEnum(InterventionTypeEnum type) throws Exception {
		return dao.findByInterventionTypeEnum(type);
	}
	
	public List<InterventionType> findByDescription(String description) throws Exception {
		return dao.findByDescription(description);
	}

}
