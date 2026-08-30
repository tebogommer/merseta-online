package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UpdateAuditTrailDAO;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.UpdateAuditTrail;
import haj.com.entity.UpdateAuditTrailChanges;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.CheckForChangesUtil;

public class UpdateAuditTrailService extends AbstractService {
	/** The dao. */
	private UpdateAuditTrailDAO dao = new UpdateAuditTrailDAO();

	private static UpdateAuditTrailService auditTrailService = null;

	/**
	 * Instance.
	 *
	 * @return the doc service
	 */
	public static synchronized UpdateAuditTrailService instance() {
		if (auditTrailService == null) {
			auditTrailService = new UpdateAuditTrailService();
		}
		return auditTrailService;
	}

	/**
	 * Get all UpdateAuditTrail
	 * 
	 * @author TechFinium
	 * @see UpdateAuditTrail
	 * @return a list of {@link haj.com.entity.UpdateAuditTrail}
	 * @throws Exception
	 *             the exception
	 */
	public List<UpdateAuditTrail> allUpdateAuditTrail() throws Exception {
		return dao.allUpdateAuditTrail();
	}

	/**
	 * Create or update UpdateAuditTrail.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see UpdateAuditTrail
	 */
	public void create(UpdateAuditTrail entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update UpdateAuditTrail.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see UpdateAuditTrail
	 */
	public void update(UpdateAuditTrail entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete UpdateAuditTrail.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see UpdateAuditTrail
	 */
	public void delete(UpdateAuditTrail entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.UpdateAuditTrail}
	 * @throws Exception
	 *             the exception
	 * @see UpdateAuditTrail
	 */
	public UpdateAuditTrail findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find UpdateAuditTrail by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.UpdateAuditTrail}
	 * @throws Exception
	 *             the exception
	 * @see UpdateAuditTrail
	 */
	public List<UpdateAuditTrail> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load UpdateAuditTrail
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.UpdateAuditTrail}
	 * @throws Exception
	 *             the exception
	 */
	public List<UpdateAuditTrail> allUpdateAuditTrail(int first, int pageSize) throws Exception {
		return dao.allUpdateAuditTrail(Long.valueOf(first), pageSize);
	}

	public void checkEntities(Users users, IDataEntity someObject, Long id, Company company, BankingDetails bankingDetails) throws Exception {
		if (id != null) {
			List<IDataEntity> dataEntities = new ArrayList<>();
			IDataEntity oldObject = findClass(someObject.getClass().getName(), id);

			Map<String, List<String>> changes = CheckForChangesUtil.instance().checkForChanges(someObject, oldObject);

			UpdateAuditTrail auditTrail = new UpdateAuditTrail(users, bankingDetails, company);
			dataEntities.add(auditTrail);
			for (java.util.Map.Entry<String, List<String>> entry : changes.entrySet()) {
				UpdateAuditTrailChanges auditTrailChanges = new UpdateAuditTrailChanges(auditTrail, entry.getKey(), entry.getValue().get(0), entry.getValue().get(1));
				dataEntities.add(auditTrailChanges);
			}
			dao.createBatch(dataEntities);

		}

	}

	public IDataEntity findClass(String className, Long id) throws Exception {
		return dao.findClass(className, id);
	}

	/**
	 * Get count of UpdateAuditTrail for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the UpdateAuditTrail
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(UpdateAuditTrail.class);
	}

	/**
	 * Lazy load UpdateAuditTrail with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            UpdateAuditTrail class
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
	 * @return a list of {@link haj.com.entity.UpdateAuditTrail} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<UpdateAuditTrail> allUpdateAuditTrail(Class<UpdateAuditTrail> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<UpdateAuditTrail>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of UpdateAuditTrail for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            UpdateAuditTrail class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the UpdateAuditTrail entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<UpdateAuditTrail> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
}
