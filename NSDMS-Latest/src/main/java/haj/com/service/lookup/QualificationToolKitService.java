package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.QualificationToolKitDAO;
import haj.com.entity.lookup.QualificationToolKit;
import haj.com.framework.AbstractService;

public class QualificationToolKitService extends AbstractService {

	/** The dao. */
	private QualificationToolKitDAO dao = new QualificationToolKitDAO();
	private static QualificationToolKitService qualificationToolKitService = null;

	public static synchronized QualificationToolKitService instance() {
		if (qualificationToolKitService == null) {
			qualificationToolKitService = new QualificationToolKitService();
		}
		return qualificationToolKitService;
	}

	/**
	 * Get all QualificationToolKit
	 * 
	 * @author TechFinium
	 * @see QualificationToolKit
	 * @return a list of {@link haj.com.entity.QualificationToolKit}
	 * @throws Exception
	 *             the exception
	 */
	public List<QualificationToolKit> allQualificationToolKit() throws Exception {
		return dao.allQualificationToolKit();
	}

	/**
	 * Create or update QualificationToolKit with check to esnure no duplicate
	 * qualifications added
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see QualificationToolKit
	 */
	public void createWithCheck(QualificationToolKit entity) throws Exception {
		if (entity.getId() == null) {
			if (countByQualificationId(entity.getQualification().getId()) == 0) {
				this.dao.create(entity);
			}else {
				throw new Exception("Qualification already assigned. Please select a different qualification.");
			}
		} else {
			if (countByQualificationIdIgnoreQualificationToolKitId(entity.getQualification().getId(), entity.getId()) == 0) {
				this.dao.update(entity);
			}else {
				throw new Exception("Qualification already assigned. Please select a different qualification.");
			}
		}
	}

	/**
	 * Create or update QualificationToolKit.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see QualificationToolKit
	 */
	public void create(QualificationToolKit entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update QualificationToolKit.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see QualificationToolKit
	 */
	public void update(QualificationToolKit entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete QualificationToolKit.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see QualificationToolKit
	 */
	public void delete(QualificationToolKit entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.QualificationToolKit}
	 * @throws Exception
	 *             the exception
	 * @see QualificationToolKit
	 */
	public QualificationToolKit findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find QualificationToolKit by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.QualificationToolKit}
	 * @throws Exception
	 *             the exception
	 * @see QualificationToolKit
	 */
	public List<QualificationToolKit> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load QualificationToolKit
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.QualificationToolKit}
	 * @throws Exception
	 *             the exception
	 */
	public List<QualificationToolKit> allQualificationToolKit(int first, int pageSize) throws Exception {
		return dao.allQualificationToolKit(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of QualificationToolKit for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the QualificationToolKit
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(QualificationToolKit.class);
	}

	/**
	 * Lazy load QualificationToolKit with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            QualificationToolKit class
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
	 * @return a list of {@link haj.com.entity.QualificationToolKit} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationToolKit> allQualificationToolKit(Class<QualificationToolKit> class1, int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<QualificationToolKit>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of QualificationToolKit for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            QualificationToolKit class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the QualificationToolKit entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<QualificationToolKit> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public int countByQualificationId(Long qualificationId) throws Exception {
		return dao.countByQualificationId(qualificationId);
	}
	
	public int countByQualificationIdIgnoreQualificationToolKitId(Long qualificationId, Long qualificationToolKitId) throws Exception {
		return dao.countByQualificationIdIgnoreQualificationToolKitId(qualificationId, qualificationToolKitId);
	}
}
