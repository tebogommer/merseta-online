package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.OfoCodesDAO;
import haj.com.entity.OfoCodes;
import haj.com.entity.lookup.OccupationCategory;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.OccupationCategoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class OfoCodesService.
 */
public class OfoCodesService extends AbstractService {

	/** The dao. */
	private OfoCodesDAO dao = new OfoCodesDAO();
	
	/** Service Levels */
	private OccupationCategoryService occupationCategoryService;

	/**
	 * Get all OfoCodes.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.OfoCodes}
	 * @throws Exception
	 *             the exception
	 * @see OfoCodes
	 */
	public List<OfoCodes> allOfoCodes() throws Exception {
		return dao.allOfoCodes();
	}
	
	public List<OfoCodes> allOfoCodesActiveWithOfoCode() throws Exception {
		return dao.allOfoCodesActiveWithOfoCode();
	}
	public List<OfoCodes> allSpecialOfoCodesActiveWithOfoCode() throws Exception {
		return dao.allSpecialOfoCodesActiveWithOfoCode();
	}

	/**
	 * Create or update OfoCodes.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see OfoCodes
	 */
	public void create(OfoCodes entity) throws Exception {

		if (entity.getId() == null) {
			entity.setCreateDate(new java.util.Date());
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update OfoCodes.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see OfoCodes
	 */
	public void update(OfoCodes entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete OfoCodes.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see OfoCodes
	 */
	public void delete(OfoCodes entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a OfoCodes object
	 * @throws Exception
	 *             the exception
	 * @see OfoCodes
	 */
	public OfoCodes findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find OfoCodes by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.OfoCodes}
	 * @throws Exception
	 *             the exception
	 * @see OfoCodes
	 */
	public List<OfoCodes> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	// for wsp grant applications: WSP
	public List<OfoCodes> findByNameAndYear(String description, Integer year) throws Exception {	
		return dao.findByNameAndYear(description, year);
	}
	
	public List<OfoCodes> findByNameAtr(String desc) throws Exception {
		return dao.findByNameAtr(desc);
	}
	
	// for wsp grant applications: ATR
	public List<OfoCodes> findByNameAtrAndYear(String description, Integer year) throws Exception {
		return dao.findByNameAtrAndYear(description, year);
	}
	
	public List<OfoCodes> findByTrade(String desc, Boolean trade ) throws Exception {
		return dao.findByTrade(desc, trade);
	}
	
	public List<OfoCodes> findByTradeWpaVersion(String description,  boolean trade) throws Exception {
		return dao.findByTradeWpaVersion(description, trade);
	}
	
	public List<OfoCodes> findByNameNoSpes(String description) throws Exception {
		return dao.findByNameNoSpes(description);
	}

	/**
	 * Lazy load OfoCodes.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.OfoCodes}
	 * @throws Exception
	 *             the exception
	 */
	public List<OfoCodes> allOfoCodes(int first, int pageSize) throws Exception {
		return dao.allOfoCodes(Long.valueOf(first), pageSize);
	}

	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the OfoCodes
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(OfoCodes.class);
	}

	/**
	 * All ofo codes.
	 *
	 * @author TechFinium
	 * @param class1
	 *            OfoCodes class
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
	 * @return a list of {@link haj.com.entity.OfoCodes} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<OfoCodes> allOfoCodes(Class<OfoCodes> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<OfoCodes>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Count.
	 *
	 * @param entity
	 *            OfoCodes class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the OfoCodes entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<OfoCodes> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by code.
	 *
	 * @param ofoCode
	 *            the ofo code
	 * @return the ofo codes
	 * @throws Exception
	 *             the exception
	 */
	public OfoCodes findByCode(String ofoCode) throws Exception {
		if (StringUtils.isBlank(ofoCode)) return null;
		else return dao.findByCode(ofoCode.trim());
	}

	public OfoCodes findBySpecialisation(String ofoCode) throws Exception {
		return dao.findBySpecialisation(ofoCode);
	}
	
	public void populateOfoCodeWithGroups() throws Exception{
		List<IDataEntity> updateList = new ArrayList<>();
		
		// locates all ofo codes
		List<OfoCodes> list = allOfoCodesActiveWithOfoCode();
		for (OfoCodes ofoCodes : list) {
			String ofoCode = ofoCodes.getOfoCode().trim();
			
			String majorGroup = ofoCode.substring(5, 6); // Major group would be 2
			String minorGroup = ofoCode.substring(5, 8); // Minor Group: 232
			String subMinorGroup = ofoCode.substring(5, 7);// Sub Minor group: 23
			String unitGroup = ofoCode.substring(5, 9); // unitGroup: 2321
			
			ofoCodes.setMajorGroup(majorGroup);
			ofoCodes.setMinorGroup(minorGroup);
			ofoCodes.setSubMajorGroup(subMinorGroup);
			ofoCodes.setUnitGroup(unitGroup);
			
			if (occupationCategoryService == null) {
				occupationCategoryService = new OccupationCategoryService();
			}
			OccupationCategory oc = occupationCategoryService.findByCode("OFO_"+majorGroup.trim());
			if (oc != null) {
				ofoCodes.setOccupationCategory(oc);
			}
			updateList.add(ofoCodes);
		}
		occupationCategoryService = null;
		if (updateList.size() != 0) {
			System.out.println("List to update: " + updateList.size());
			dao.updateBatch(updateList);
		}
	}
	
	public void populateOfoCodeSpecials() throws Exception{
		List<OfoCodes> listSpecial = allSpecialOfoCodesActiveWithOfoCode();
		for (OfoCodes ofoCodeSpecial : listSpecial) {
			OfoCodes parent = findByOfoCode(ofoCodeSpecial.getSpecialisationCode());
			if (parent != null) {
				int count = findByLinkedOfoCode(parent.getId());
				int usedAmount = count +1;
				ofoCodeSpecial.setSpecialisationCode(ofoCodeSpecial.getSpecialisationCode().trim() + "-" + (usedAmount));
				ofoCodeSpecial.setTrade(parent.getTrade());
				ofoCodeSpecial.setOfoCodes(parent);
//				System.out.println("parent : " + parent.getOfoCode() + "   -    Child: " + ofoCodeSpecial.getSpecialisationCode());
				update(ofoCodeSpecial);
			} else {
//				System.out.println(ofoCodeSpecial.getSpecialisationCode() + " No Parent ");
			}
		}
	}
	
	public OfoCodes findByOfoCode(String ofoCode) throws Exception {
		return dao.findByOfoCode(ofoCode);
	}
	
	public int findByLinkedOfoCode(Long ofoCodeParentId) throws Exception {
		return dao.findByLinkedOfoCode(ofoCodeParentId);
	}
}
