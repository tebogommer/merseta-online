package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LearnershipDataUploadDAO;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LearnershipDataUpload;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class LearnershipDataUploadService extends AbstractService {
	/** The dao. */
	private LearnershipDataUploadDAO dao = new LearnershipDataUploadDAO();
	
	/* Service Levels */
	private LearnershipService learnershipService;
	private QualificationService qualificationService;
	private EtqaService etqaService;

	/**
	 * Get all LearnershipDataUpload
 	 * @author TechFinium 
 	 * @see   LearnershipDataUpload
 	 * @return a list of {@link haj.com.entity.LearnershipDataUpload}
	 * @throws Exception the exception
 	 */
	public List<LearnershipDataUpload> allLearnershipDataUpload() throws Exception {
	  	return dao.allLearnershipDataUpload();
	}


	/**
	 * Create or update LearnershipDataUpload.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LearnershipDataUpload
	 */
	public void create(LearnershipDataUpload entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LearnershipDataUpload.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnershipDataUpload
	 */
	public void update(LearnershipDataUpload entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LearnershipDataUpload.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnershipDataUpload
	 */
	public void delete(LearnershipDataUpload entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LearnershipDataUpload}
	 * @throws Exception the exception
	 * @see    LearnershipDataUpload
	 */
	public LearnershipDataUpload findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LearnershipDataUpload by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LearnershipDataUpload}
	 * @throws Exception the exception
	 * @see    LearnershipDataUpload
	 */
	public List<LearnershipDataUpload> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LearnershipDataUpload
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LearnershipDataUpload}
	 * @throws Exception the exception
	 */
	public List<LearnershipDataUpload> allLearnershipDataUpload(int first, int pageSize) throws Exception {
		return dao.allLearnershipDataUpload(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LearnershipDataUpload for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LearnershipDataUpload
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LearnershipDataUpload.class);
	}
	
    /**
     * Lazy load LearnershipDataUpload with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LearnershipDataUpload class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LearnershipDataUpload} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LearnershipDataUpload> allLearnershipDataUpload(Class<LearnershipDataUpload> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LearnershipDataUpload>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LearnershipDataUpload for lazy load with filters
     * @author TechFinium 
     * @param entity LearnershipDataUpload class
     * @param filters the filters
     * @return Number of rows in the LearnershipDataUpload entity
     * @throws Exception the exception     
     */	
	public int count(Class<LearnershipDataUpload> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LearnershipDataUpload> allEntries = allLearnershipDataUpload();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LearnershipDataUpload> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public void syncLearnershipInformation() throws Exception{
		
		if (learnershipService == null) {
			learnershipService = new LearnershipService();
		}
		if (qualificationService ==  null) {
			qualificationService = new QualificationService();
		}
		if (etqaService == null) {
			etqaService = new EtqaService();
		}
		
		List<LearnershipDataUpload> list = allLearnershipDataUpload();
		List<IDataEntity> createList = new ArrayList<>();
		
		for (LearnershipDataUpload learnershipUpload : list) {
			if (learnershipUpload.getLearnershipCode() != null && !learnershipUpload.getLearnershipCode().trim().isEmpty()) {
				if (learnershipUpload.getEtqaRegisteringLship() != null && !learnershipUpload.getEtqaRegisteringLship().trim().isEmpty()) {
					Etqa etqa = etqaService.findByCode(learnershipUpload.getEtqaRegisteringLship().trim());
					if (etqa != null) {
						Learnership learnership = learnershipService.findByCode(learnershipUpload.getLearnershipCode().trim());
						if (learnership == null) {
							
							// new instance 
							learnership = new Learnership();
		
							// learnership code
							learnership.setCode(learnershipUpload.getLearnershipCode().trim());
							
							// description
							if (learnershipUpload.getLearnershipTitle() != null) {
								learnership.setDescription(learnershipUpload.getLearnershipTitle().trim());
							}
							
							// qualification
							if (learnershipUpload.getQualId() != null) {
								try {
									Qualification q = qualificationService.findByCodeString(learnershipUpload.getQualId().trim());
									if (q != null) {
										learnership.setQualification(q);
									}
								} catch (Exception e) {
								}
							}
							
							// etqa 
							learnership.setEtqa(etqa);
							
							if (learnership.getQualification() != null) {
								createList.add(learnership);
							}
						}
						etqa = null;
					}
				}
			}
		}
		
		list = null;
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
	}
}
