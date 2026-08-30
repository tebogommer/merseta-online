package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.DesignatedTradeLevelDAO;
import haj.com.entity.CompanyLearners;
import haj.com.entity.lookup.DesignatedTrade;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;

public class DesignatedTradeLevelService extends AbstractService {

	/** The dao. */
	private DesignatedTradeLevelDAO dao = new DesignatedTradeLevelDAO();

	private static DesignatedTradeLevelService designatedTradeLevelService = null;
	public static synchronized DesignatedTradeLevelService instance() {
		if (designatedTradeLevelService == null) {
			designatedTradeLevelService = new DesignatedTradeLevelService();
		}
		return designatedTradeLevelService;
	}
	
	/* Service Levels */
	private DesignatedTradeService designatedTradeService;

	/**
	 * Get all DesignatedTradeLevel
	 * 
	 * @author TechFinium
	 * @see DesignatedTradeLevel
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevel}
	 * @throws Exception
	 *             the exception
	 */
	public List<DesignatedTradeLevel> allDesignatedTradeLevel() throws Exception {
		return populateAdditionalInformationList(dao.allDesignatedTradeLevel());
	}

	/**
	 * Create or update DesignatedTradeLevel.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DesignatedTradeLevel
	 */
	public void create(DesignatedTradeLevel entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update DesignatedTradeLevel.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DesignatedTradeLevel
	 */
	public void update(DesignatedTradeLevel entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete DesignatedTradeLevel.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DesignatedTradeLevel
	 */
	public void delete(DesignatedTradeLevel entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.DesignatedTradeLevel}
	 * @throws Exception
	 *             the exception
	 * @see DesignatedTradeLevel
	 */
	public DesignatedTradeLevel findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}

	/**
	 * Find DesignatedTradeLevel by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevel}
	 * @throws Exception
	 *             the exception
	 * @see DesignatedTradeLevel
	 */
	public List<DesignatedTradeLevel> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load DesignatedTradeLevel
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevel}
	 * @throws Exception
	 *             the exception
	 */
	public List<DesignatedTradeLevel> allDesignatedTradeLevel(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allDesignatedTradeLevel(Long.valueOf(first), pageSize));
	}

	/**
	 * Get count of DesignatedTradeLevel for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the DesignatedTradeLevel
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(DesignatedTradeLevel.class);
	}

	/**
	 * Lazy load DesignatedTradeLevel with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            DesignatedTradeLevel class
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
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevel} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> allDesignatedTradeLevel(Class<DesignatedTradeLevel> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<DesignatedTradeLevel>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));

	}

	/**
	 * Get count of DesignatedTradeLevel for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            DesignatedTradeLevel class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the DesignatedTradeLevel entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<DesignatedTradeLevel> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public List<DesignatedTradeLevel> populateAdditionalInformationList(List<DesignatedTradeLevel> designatedTradeLevelList) throws Exception{
		for (DesignatedTradeLevel designatedTradeLevel : designatedTradeLevelList) {
			populateAdditionalInformation(designatedTradeLevel);
		}
		return designatedTradeLevelList;
	}

	public DesignatedTradeLevel populateAdditionalInformation(DesignatedTradeLevel designatedTradeLevel) throws Exception{
		if (designatedTradeLevel.getId() != null) {
			designatedTradeLevel.setDesignatedTradeLevelItemsList(DesignatedTradeLevelItemsService.instance().findByDesignatedTradeLevel(designatedTradeLevel.getId()));
		}
		return designatedTradeLevel;
	}

	public List<DesignatedTradeLevel> allDesignatedTradeLevelByDesignatedTradeType(DesignatedTradeType designatedTradeType) {
		if (designatedTradeType != null && designatedTradeType.getId() != null) {
			return dao.allDesignatedTradeLevelByDesignatedTradeType(designatedTradeType.getId());
		} else {
			return new ArrayList<DesignatedTradeLevel>();
		}
	}

	public List<DesignatedTradeLevel> findByQualificationIdOrderByLevel(Qualification qualification) throws Exception {
		return populateAdditionalInformationList(dao.findByQualificationIdOrderByLevel(qualification.getId()));
	}
	
	public Integer countByQualificationIdOrderByLevel(Long qualificationId) throws Exception {
		return dao.countByQualificationIdOrderByLevel(qualificationId);
	}
	
	public DesignatedTrade locateDesignatedTradeByQualification(Qualification qualification) throws Exception{
		if (qualification == null || qualification.getId() == null) {
			return null;
		}
		List<DesignatedTradeLevel> designatedTradeLevelList = findByQualificationIdOrderByLevel(qualification);
		if (designatedTradeLevelList.isEmpty()) {
			return null;
		}else {
			if (designatedTradeLevelList.get(0).getDesignatedTrade() != null && designatedTradeLevelList.get(0).getDesignatedTrade().getId() != null) {
				if (designatedTradeService == null) {
					designatedTradeService = new DesignatedTradeService();
				}
				return designatedTradeService.findByKey(designatedTradeLevelList.get(0).getDesignatedTrade().getId());
			} else {
				return null;
			}
		}
	}

	public List<DesignatedTradeLevel> findByQualificationIdNotRecorded(Qualification qualification, CompanyLearners companyLearners) throws Exception {
		return populateAdditionalInformationList(dao.findByQualificationIdNotRecorded(qualification.getId(), companyLearners.getId()));
	}

	public List<DesignatedTradeLevel> findByQualificationIdNextLevel(Qualification qualification, Long level) throws Exception {
		return populateAdditionalInformationList(dao.findByQualificationIdNextLevel(qualification.getId(), level));
	}

	public List<DesignatedTradeLevel> findByQualificationIdNotRecordedSingle(Qualification qualification, CompanyLearners companyLearners) throws Exception {
		return populateAdditionalInformationList(dao.findByQualificationIdNotRecordedSingle(qualification.getId(), companyLearners.getId()));
	}
	
	public List<DesignatedTradeLevel> findBydesignatedTradeIdNotRecordedSingle(DesignatedTrade designatedTrade, CompanyLearners companyLearners) throws Exception {
		return populateAdditionalInformationList(dao.findBydesignatedTradeIdNotRecordedSingle(designatedTrade.getId(), companyLearners.getId()));
	}

	public Long findByQualificationIdNotRecordedCount(Qualification qualification, CompanyLearners companyLearners) throws Exception {
		return dao.findByQualificationIdNotRecordedCount(qualification.getId(), companyLearners.getId());
	}

	public int countEntiresByQualificationId(Qualification qualification) throws Exception {
		return dao.countEntiresByQualificationId(qualification.getId());
	}
	
	public Long findBydesignatedTradeIdNotRecordedSingleCount(DesignatedTrade designatedTrade, CompanyLearners companyLearners) throws Exception {
		return dao.findBydesignatedTradeIdNotRecordedSingleCount(designatedTrade.getId(), companyLearners.getId());
	}
	
	public int countEntiresByDesignatedTradeId(DesignatedTrade designatedTrade) throws Exception {
		return dao.countEntiresByDesignatedTradeId(designatedTrade.getId());
	}
	
	public int countDesignatedTradeLevelEntriesByQualificationId(Long qualificationId) throws Exception {
		return dao.countDesignatedTradeLevelEntriesByQualificationId(qualificationId);
	}
	
	public int countDesignatedTradeLevelEntriesByQualificationIdAndNoCompletionOrder(Long qualificationId, Long level) throws Exception {
		return dao.countDesignatedTradeLevelEntriesByQualificationIdAndNoCompletionOrder(qualificationId, level);
	}
	
	public List<DesignatedTradeLevel> findDesignatedTradeLevelAutocomplete(String description) throws Exception {
		return dao.findDesignatedTradeLevelAutocomplete(description);
	}
	
	public boolean determainQualificationDesignatedTrade(Qualification qualification) throws Exception{
		if (countDesignatedTradeLevelEntriesByQualificationId(qualification.getId()) == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean determainQualificationDesignatedTradeAndNoCompletionOrder(Qualification qualification, Long level) throws Exception{
		if (countDesignatedTradeLevelEntriesByQualificationIdAndNoCompletionOrder(qualification.getId(), level) == 0) {
			return false;
		} else {
			return true;
		}
	}
}
