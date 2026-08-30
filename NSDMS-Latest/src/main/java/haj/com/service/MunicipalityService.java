package haj.com.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.MunicipalityDAO;
import haj.com.entity.Municipality;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class MunicipalityService.
 */
public class MunicipalityService extends AbstractService {

	/** The dao. */
	private MunicipalityDAO dao = new MunicipalityDAO();

	/**
	 * Get all Municipality.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Municipality}
	 * @throws Exception the exception
	 * @see    Municipality
	 */
	public List<Municipality> allMunicipality() throws Exception {
	  	return dao.allMunicipality();
	}

	/**
	 * Create or update  Municipality.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Municipality
	 */
	public void create(Municipality entity) throws Exception  {
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}

	/**
	 * Update  Municipality.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Municipality
	 */
	public void update(Municipality entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Municipality.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Municipality
	 */
	public void delete(Municipality entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a Municipality object
	 * @throws Exception the exception
	 * @see    Municipality
	 */
	public Municipality findByKey(Integer id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Municipality by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Municipality}
	 * @throws Exception the exception
	 * @see    Municipality
	 */
	public List<Municipality> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Municipality.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Municipality}
	 * @throws Exception the exception
	 */
	public List<Municipality> allMunicipality(int first, int pageSize) throws Exception {
		return dao.allMunicipality(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Municipality
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Municipality.class);
	}
	
    /**
     * All municipality.
     *
     * @author TechFinium
     * @param class1 Municipality class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return a list of {@link haj.com.entity.Municipality} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Municipality> allMunicipality(Class<Municipality> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Municipality o left join fetch o.district left join fetch o.province left join fetch o.municipalityType";
		return ( List<Municipality>)dao.sortAndFilter(first,pageSize,sortField,sortOrder,filters,hql);
	
	}
	
    /**
     * Count.
     *
     * @param entity Municipality class
     * @param filters the filters
     * @return Number of rows in the Municipality entity
     * @throws Exception the exception
     */	
	public int count(Class<Municipality> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the municipality
	 * @throws Exception the exception
	 */
	public Municipality findByCode(String code) throws Exception {
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(code.trim());
	}
}
