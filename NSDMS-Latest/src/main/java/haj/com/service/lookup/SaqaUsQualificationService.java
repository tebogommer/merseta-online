package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SaqaUsQualificationDAO;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SaqaQualification;
import haj.com.entity.lookup.SaqaUsQualification;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;

public class SaqaUsQualificationService extends AbstractService {
	/** The dao. */
	private SaqaUsQualificationDAO dao = new SaqaUsQualificationDAO();

	/**
	 * Get all SaqaUsQualification
 	 * @author TechFinium 
 	 * @see   SaqaUsQualification
 	 * @return a list of {@link haj.com.entity.SaqaUsQualification}
	 * @throws Exception the exception
 	 */
	public List<SaqaUsQualification> allSaqaUsQualification() throws Exception {
	  	return dao.allSaqaUsQualification();
	}


	/**
	 * Create or update SaqaUsQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SaqaUsQualification
	 */
	public void create(SaqaUsQualification entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SaqaUsQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SaqaUsQualification
	 */
	public void update(SaqaUsQualification entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SaqaUsQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SaqaUsQualification
	 */
	public void delete(SaqaUsQualification entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SaqaUsQualification}
	 * @throws Exception the exception
	 * @see    SaqaUsQualification
	 */
	public SaqaUsQualification findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SaqaUsQualification by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SaqaUsQualification}
	 * @throws Exception the exception
	 * @see    SaqaUsQualification
	 */
	public List<SaqaUsQualification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SaqaUsQualification
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SaqaUsQualification}
	 * @throws Exception the exception
	 */
	public List<SaqaUsQualification> allSaqaUsQualification(int first, int pageSize) throws Exception {
		return dao.allSaqaUsQualification(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SaqaUsQualification for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SaqaUsQualification
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SaqaUsQualification.class);
	}
	
    /**
     * Lazy load SaqaUsQualification with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SaqaUsQualification class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SaqaUsQualification} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> allSaqaUsQualification(Class<SaqaUsQualification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SaqaUsQualification>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SaqaUsQualification for lazy load with filters
     * @author TechFinium 
     * @param entity SaqaUsQualification class
     * @param filters the filters
     * @return Number of rows in the SaqaUsQualification entity
     * @throws Exception the exception     
     */	
	public int count(Class<SaqaUsQualification> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	
	/**
	 * Find SaqaUsQualification qualification ID and US is
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SaqaUsQualification
  	 * @return a list of {@link haj.com.entity.SaqaUsQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> findByQualAndUS(Long qualID,Integer unitstandardid) throws Exception {
	    return dao.findByQualAndUS(qualID, unitstandardid);
	}
	
	@SuppressWarnings("unchecked")
	public SaqaUsQualification  findByUS(Integer unitstandardid) throws Exception {
	    return dao.findByUS(unitstandardid);
	}
	
	@SuppressWarnings("unchecked")
	public Qualification  findQualificationByUS(Integer unitstandardid) throws Exception {
		SaqaQualification saqaQualification = dao.findQualificationByUS(unitstandardid);
		if(saqaQualification!=null) {
			return QualificationService.instance().findByKey(saqaQualification.getId());
		}else {
			return null;
		}
	}
	
	
	
	/**
	 * Find SaqaUsQualification qualification ID 
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SaqaUsQualification
  	 * @return a list of {@link haj.com.entity.SaqaUsQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findByQualification(Long qualID) throws Exception {
	 	
		return  dao.findByQualification(qualID);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Qualification>  findSaqaUsQualificationByUnitStandardId(Integer unitstandardid) throws Exception {
		List<SaqaUsQualification>saqaQualifications = dao.findSaqaUsQualificationByUnitStandardId(unitstandardid);		
		List<Qualification> list = new ArrayList<>();
		if(saqaQualifications!=null && saqaQualifications.size()>0) {
			for(SaqaUsQualification saqaQualification: saqaQualifications) {
				list.add(QualificationService.instance().findByKey(saqaQualification.getSaqaQualification().getId()));
				//list.add(QualificationService.instance().findByKey(saqaQualification.getId()));
			}			 
		}		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification>  findSaqaUsQualificationByUnitStandardId(Integer unitstandardid, Date qualregistrationendDate) throws Exception {
		List<SaqaUsQualification>saqaQualifications = dao.findSaqaUsQualificationByUnitStandardId(unitstandardid, qualregistrationendDate);		
		List<Qualification> list = new ArrayList<>();
		if(saqaQualifications!=null && saqaQualifications.size()>0) {
			for(SaqaUsQualification saqaQualification: saqaQualifications) {
				list.add(QualificationService.instance().findByKey(saqaQualification.getSaqaQualification().getId()));
				//list.add(QualificationService.instance().findByKey(saqaQualification.getId()));
			}			 
		}		
		return list;
	}
	
	public List<Qualification>  findSaqaUsQualificationByLearningProgrammeQual(String codeString, Date qualregistrationendDate) throws Exception {
		List<Qualification>list = dao.findSaqaUsQualificationByLearningProgrammeQual(codeString, qualregistrationendDate);					
		return list;
	}
	
	public int countLearningProgrammes(String codeString, Date qualregistrationendDate) throws Exception {
		return  dao.countLearningProgrammes(codeString, qualregistrationendDate);
	}
}
