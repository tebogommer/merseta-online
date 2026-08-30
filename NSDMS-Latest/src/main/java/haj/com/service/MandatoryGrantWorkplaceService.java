package haj.com.service;

import java.util.List;

import haj.com.dao.MandatoryGrantWorkplaceDAO;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantWorkplace;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class MandatoryGrantWorkplaceService extends AbstractService {
	/** The dao. */
	private MandatoryGrantWorkplaceDAO dao = new MandatoryGrantWorkplaceDAO();

	/**
	 * Get all MandatoryGrantWorkplace
	 * @author TechFinium
	 * @see MandatoryGrantWorkplace
	 * @return a list of {@link haj.com.entity.MandatoryGrantWorkplace}
	 * @throws Exception
	 * the exception
	 */
	public List<MandatoryGrantWorkplace> allMandatoryGrantWorkplace() throws Exception {
		return dao.allMandatoryGrantWorkplace();
	}

	public List<MandatoryGrantWorkplace> findByMandatoryGrant(MandatoryGrant mandatoryGrant) throws Exception {
		return dao.findByMandatoryGrant(mandatoryGrant);
	}

	/**
	 * Create or update MandatoryGrantWorkplace.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see MandatoryGrantWorkplace
	 */
	public void create(MandatoryGrantWorkplace entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update MandatoryGrantWorkplace.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see MandatoryGrantWorkplace
	 */
	public void update(MandatoryGrantWorkplace entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete MandatoryGrantWorkplace.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see MandatoryGrantWorkplace
	 */
	public void delete(MandatoryGrantWorkplace entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.MandatoryGrantWorkplace}
	 * @throws Exception
	 * the exception
	 * @see MandatoryGrantWorkplace
	 */
	public MandatoryGrantWorkplace findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find MandatoryGrantWorkplace by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.MandatoryGrantWorkplace}
	 * @throws Exception
	 * the exception
	 * @see MandatoryGrantWorkplace
	 */
	public List<MandatoryGrantWorkplace> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load MandatoryGrantWorkplace
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.MandatoryGrantWorkplace}
	 * @throws Exception
	 * the exception
	 */
	public List<MandatoryGrantWorkplace> allMandatoryGrantWorkplace(int first, int pageSize) throws Exception {
		return dao.allMandatoryGrantWorkplace(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of MandatoryGrantWorkplace for lazy load
	 * @author TechFinium
	 * @return Number of rows in the MandatoryGrantWorkplace
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(MandatoryGrantWorkplace.class);
	}

	/**
	 * Lazy load MandatoryGrantWorkplace with pagination, filter, sorting
	 * @author TechFinium
	 * @param class1
	 * MandatoryGrantWorkplace class
	 * @param first
	 * the first
	 * @param pageSize
	 * the page size
	 * @param sortField
	 * the sort field
	 * @param sortOrder
	 * the sort order
	 * @param filters
	 * the filters
	 * @return a list of {@link haj.com.entity.MandatoryGrantWorkplace} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantWorkplace> allMandatoryGrantWorkplace(Class<MandatoryGrantWorkplace> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<MandatoryGrantWorkplace>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of MandatoryGrantWorkplace for lazy load with filters
	 * @author TechFinium
	 * @param entity
	 * MandatoryGrantWorkplace class
	 * @param filters
	 * the filters
	 * @return Number of rows in the MandatoryGrantWorkplace entity
	 * @throws Exception
	 * the exception
	 */
	public int count(Class<MandatoryGrantWorkplace> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
}
