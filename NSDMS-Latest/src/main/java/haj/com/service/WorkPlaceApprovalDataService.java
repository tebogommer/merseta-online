package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WorkPlaceApprovalDataDAO;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.WorkPlaceApprovalData;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WorkPlaceApprovalDataService extends AbstractService {
	/** The dao. */
	private WorkPlaceApprovalDataDAO dao = new WorkPlaceApprovalDataDAO();

	private ResolveByCodeLookupDAO lookupDAO = new ResolveByCodeLookupDAO();

	/**
	 * Get all WorkPlaceApprovalData
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApprovalData
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalData}
	 * @throws Exception
	 *             the exception
	 */
	public List<WorkPlaceApprovalData> allWorkPlaceApprovalData() throws Exception {
		return dao.allWorkPlaceApprovalData();
	}

	/**
	 * Create or update WorkPlaceApprovalData.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApprovalData
	 */
	public void create(WorkPlaceApprovalData entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update WorkPlaceApprovalData.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApprovalData
	 */
	public void update(WorkPlaceApprovalData entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WorkPlaceApprovalData.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApprovalData
	 */
	public void delete(WorkPlaceApprovalData entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WorkPlaceApprovalData}
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApprovalData
	 */
	public WorkPlaceApprovalData findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find WorkPlaceApprovalData by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalData}
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApprovalData
	 */
	public List<WorkPlaceApprovalData> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WorkPlaceApprovalData
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalData}
	 * @throws Exception
	 *             the exception
	 */
	public List<WorkPlaceApprovalData> allWorkPlaceApprovalData(int first, int pageSize) throws Exception {
		return dao.allWorkPlaceApprovalData(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WorkPlaceApprovalData for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the WorkPlaceApprovalData
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WorkPlaceApprovalData.class);
	}

	/**
	 * Lazy load WorkPlaceApprovalData with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            WorkPlaceApprovalData class
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
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalData} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalData> allWorkPlaceApprovalData(Class<WorkPlaceApprovalData> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WorkPlaceApprovalData>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of WorkPlaceApprovalData for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            WorkPlaceApprovalData class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WorkPlaceApprovalData entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WorkPlaceApprovalData> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void loadLookups(List<WorkPlaceApprovalData> workPlaceApprovalDatas) throws Exception {
		for (WorkPlaceApprovalData workPlaceApprovalData : workPlaceApprovalDatas) {
			try {
				workPlaceApprovalData.setCompany(lookupDAO.findCompany(workPlaceApprovalData.getSdlNumber()));
				if (workPlaceApprovalData.getQualificationCode() != null) 
					workPlaceApprovalData.setQualification(lookupDAO.findQualification(Integer.parseInt(workPlaceApprovalData.getQualificationCode())));
				workPlaceApprovalData.setOfoCodes(lookupDAO.findOfoCode(workPlaceApprovalData.getOfoCode()));
				workPlaceApprovalData.setSkillsProgram(lookupDAO.findSkillsProgram(workPlaceApprovalData.getsProgrammeCode()));
				workPlaceApprovalData.setLearnership(lookupDAO.findLearnership(workPlaceApprovalData.getLearnershipCode()));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(workPlaceApprovalData.getSdlNumber());
			}
		}
		dao.createBatch((List<IDataEntity>) (List<?>) workPlaceApprovalDatas);
	}
}
