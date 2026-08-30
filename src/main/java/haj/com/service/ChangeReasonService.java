package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.ChangeReasonDAO;
import haj.com.entity.ChangeReason;
import haj.com.entity.Doc;
import haj.com.framework.AbstractService;

public class ChangeReasonService extends AbstractService {
	/** The dao. */
	private ChangeReasonDAO dao = new ChangeReasonDAO();

	private static ChangeReasonService changeReasonService = null;

	/**
	 * Instance.
	 *
	 * @return the SitesService
	 */
	public static synchronized ChangeReasonService instance() {
		if (changeReasonService == null) {
			changeReasonService = new ChangeReasonService();
		}
		return changeReasonService;
	}

	/**
	 * Get all ChangeReason
	 * 
	 * @author TechFinium
	 * @see ChangeReason
	 * @return a list of {@link haj.com.entity.ChangeReason}
	 * @throws Exception
	 *             the exception
	 */
	public List<ChangeReason> allChangeReason() throws Exception {
		return dao.allChangeReason();
	}

	/**
	 * Create or update ChangeReason.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ChangeReason
	 */
	public void create(ChangeReason entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update ChangeReason.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ChangeReason
	 */
	public void update(ChangeReason entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ChangeReason.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ChangeReason
	 */
	public void delete(ChangeReason entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.ChangeReason}
	 * @throws Exception
	 *             the exception
	 * @see ChangeReason
	 */
	public ChangeReason findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find ChangeReason by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.ChangeReason}
	 * @throws Exception
	 *             the exception
	 * @see ChangeReason
	 */
	public List<ChangeReason> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ChangeReason
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.ChangeReason}
	 * @throws Exception
	 *             the exception
	 */
	public List<ChangeReason> allChangeReason(int first, int pageSize) throws Exception {
		return dao.allChangeReason(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ChangeReason for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the ChangeReason
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(ChangeReason.class);
	}

	/**
	 * Lazy load ChangeReason with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            ChangeReason class
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
	 * @return a list of {@link haj.com.entity.ChangeReason} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ChangeReason> allChangeReason(Class<ChangeReason> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<ChangeReason>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of ChangeReason for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            ChangeReason class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the ChangeReason entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<ChangeReason> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void createChangeReason(Long targetKey, String targetClass, ChangeReason changeReason) throws Exception {
		if (changeReason.getDoc() != null) {
			createChangeReason(targetKey, targetClass, changeReason, changeReason.getDoc());
		} else {
			changeReason.setTargetKey(targetKey);
			changeReason.setTargetClass(targetClass);
			ChangeReasonService.instance().create(changeReason);
		}
	}

	public void createChangeReason(Long targetKey, String targetClass, ChangeReason changeReason, Doc doc) throws Exception {
		doc.setChangeReason(changeReason);
		changeReason.setTargetKey(targetKey);
		changeReason.setTargetClass(targetClass);
		ChangeReasonService.instance().create(changeReason);
		DocService.instance().save(doc);
	}

	public List<ChangeReason> findByTargetClassAndTargetKey(String targetClass, Long targetKey) throws Exception {
		return resolveDocs(dao.findByTargetClassAndTargetKey(targetClass, targetKey));
	}

	private List<ChangeReason> resolveDocs(List<ChangeReason> reasons) throws Exception {
		for (ChangeReason changeReason : reasons) {
			changeReason.setDocs(DocService.instance().searchByCangeReason(changeReason));
		}
		return reasons;

	}
}