package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LegacyAssessorLearnershipDAO;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.entity.lookup.LegacyAssessorQualification;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyAssessorLearnershipService extends AbstractService {
	
	/** The dao. */
	private LegacyAssessorLearnershipDAO dao = new LegacyAssessorLearnershipDAO();
	
	/* Service Levels */
	private LearnershipService learnershipService;

	/**
	 * Get all LegacyAssessorLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorLearnership
	 * @return a list of {@link haj.com.entity.LegacyAssessorLearnership}
	 * @throws Exception the exception
	 */
	public List<LegacyAssessorLearnership> allLegacyAssessorLearnership() throws Exception {
		return dao.allLegacyAssessorLearnership();
	}

	/**
	 * Create or update LegacyAssessorLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyAssessorLearnership
	 */
	public void create(LegacyAssessorLearnership entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyAssessorLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyAssessorLearnership
	 */
	public void update(LegacyAssessorLearnership entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyAssessorLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyAssessorLearnership
	 */
	public void delete(LegacyAssessorLearnership entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyAssessorLearnership}
	 * @throws Exception the exception
	 * @see LegacyAssessorLearnership
	 */
	public LegacyAssessorLearnership findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyAssessorLearnership by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyAssessorLearnership}
	 * @throws Exception the exception
	 * @see LegacyAssessorLearnership
	 */
	public List<LegacyAssessorLearnership> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyAssessorLearnership
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyAssessorLearnership}
	 * @throws Exception the exception
	 */
	public List<LegacyAssessorLearnership> allLegacyAssessorLearnership(int first, int pageSize) throws Exception {
		return dao.allLegacyAssessorLearnership(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyAssessorLearnership for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyAssessorLearnership
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyAssessorLearnership.class);
	}

	/**
	 * Lazy load LegacyAssessorLearnership with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyAssessorLearnership class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyAssessorLearnership} containing
	 *         data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorLearnership> allLegacyAssessorLearnership(Class<LegacyAssessorLearnership> class1,
			int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
			throws Exception {
		return (List<LegacyAssessorLearnership>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyAssessorLearnership for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyAssessorLearnership class
	 * @param filters the filters
	 * @return Number of rows in the LegacyAssessorLearnership entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyAssessorLearnership> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyAssessorLearnership> allEntries = allLegacyAssessorLearnership();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyAssessorLearnership> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public List<LegacyAssessorLearnership> findByAssessorIdNumber(String idNumber) throws Exception {
		return dao.findByAssessorIdNumber(idNumber);
	}
	
	public List<LegacyAssessorLearnership> findByAssessorRegNo(String assessorRegNo) throws Exception {
		return resolveExpiryDate(dao.findByAssessorRegNo(assessorRegNo));
	}
	
	public List<LegacyAssessorLearnership> resolveExpiryDate(List<LegacyAssessorLearnership> list)
	{
		if(list !=null && list.size()>0)
		{
			for(LegacyAssessorLearnership llp:list)
			{
				if(llp.getLearnership() !=null && llp.getLearnership().getQualification() !=null && llp.getLearnership().getQualification().getLastDateForEnrolment() !=null)
				{
					if(llp.getLearnership().getQualification().getLastDateForEnrolment().before(new Date()))
					{
						llp.setQualificationExpired(true);
					}
					else
					{
						llp.setQualificationExpired(false);
					}
				}
			}
		}
		
		return list;
	}
	
	public List<LegacyAssessorLearnership> findByAssessorIdNumberRegNo(String entry) throws Exception {
		return dao.findByAssessorIdNumberRegNo(entry);
	}

	public void applyValidiations() throws Exception {

		if (learnershipService == null) {
			learnershipService = new LearnershipService();
		}
		
		List<LegacyAssessorLearnership> entityList = allLegacyAssessorLearnership();
		List<IDataEntity> updateList = new ArrayList<>();
		for (LegacyAssessorLearnership entry : entityList) {
			
			if (entry.getAssessorId() != null && !entry.getAssessorId().trim().isEmpty()) {
				try {
					entry.setValidRsaIdNumber(GenericUtility.checkRsaId(entry.getAssessorId().trim()));
				} catch (Exception e) {
					entry.setValidRsaIdNumber(false);
				}
			}
			
			if (entry.getLearnershipCode() != null && !entry.getLearnershipCode().isEmpty()) {
				try {
					Learnership learnership = learnershipService.findByCode(entry.getLearnershipCode());
					if (learnership != null && learnership.getId() != null) {
						entry.setLearnership(learnership);
					}
				} catch (Exception e) {
				}
			}
			
			updateList.add(entry);
			
		}
		entityList = null;
		if (updateList.size() != 0) {
			dao.updateBatch(updateList, 1000);
		}
	}
}
