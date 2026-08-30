package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.QualificationUnitStandardsDAO;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.enums.UnitStandardTypeEnum;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LearnershipUnitStandards;
import haj.com.entity.lookup.PostCodeLink;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.QualificationUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.UnitStandardsService;

public class QualificationUnitStandardsService extends AbstractService {
	/** The dao. */
	private QualificationUnitStandardsDAO dao = new QualificationUnitStandardsDAO();
	
	/* Additaional Service Levels */
	private UnitStandardsService unitStandardsService;
	private QualificationService qualificationService;

	/**
	 * Get all QualificationUnitStandards
 	 * @author TechFinium 
 	 * @see   QualificationUnitStandards
 	 * @return a list of {@link haj.com.entity.LearnerQualificationUnitStandards}
	 * @throws Exception the exception
 	 */
	public List<QualificationUnitStandards> allQualificationUnitStandards() throws Exception {
	  	return dao.allQualificationUnitStandards();
	}


	/**
	 * Create or update QualificationUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     QualificationUnitStandards
	 */
	public void create(QualificationUnitStandards entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  QualificationUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationUnitStandards
	 */
	public void update(QualificationUnitStandards entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  QualificationUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationUnitStandards
	 */
	public void delete(QualificationUnitStandards entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LearnerQualificationUnitStandards}
	 * @throws Exception the exception
	 * @see    QualificationUnitStandards
	 */
	public QualificationUnitStandards findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find QualificationUnitStandards by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LearnerQualificationUnitStandards}
	 * @throws Exception the exception
	 * @see    QualificationUnitStandards
	 */
	public List<QualificationUnitStandards> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load QualificationUnitStandards
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LearnerQualificationUnitStandards}
	 * @throws Exception the exception
	 */
	public List<QualificationUnitStandards> allQualificationUnitStandards(int first, int pageSize) throws Exception {
		return dao.allQualificationUnitStandards(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of QualificationUnitStandards for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the QualificationUnitStandards
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	   return dao.count(QualificationUnitStandards.class);
	}
	
    /**
     * Lazy load QualificationUnitStandards with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 QualificationUnitStandards class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LearnerQualificationUnitStandards} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<QualificationUnitStandards> allQualificationUnitStandards(Class<QualificationUnitStandards> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<QualificationUnitStandards>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of QualificationUnitStandards for lazy load with filters
     * @author TechFinium 
     * @param entity QualificationUnitStandards class
     * @param filters the filters
     * @return Number of rows in the QualificationUnitStandards entity
     * @throws Exception the exception     
     */	
	public int count(Class<QualificationUnitStandards> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<QualificationUnitStandards> findByQualificationKey(Qualification qualification) throws Exception{
		return dao.findByQualificationKey(qualification.getId());
	}
	
	public List<QualificationUnitStandards> findByQualificationKey(Qualification qualification, SummativeAssessmentReport summativeAssessmentReport) throws Exception{
		return dao.findByQualificationKey(qualification.getId(), summativeAssessmentReport.getId());
	}
	
	public List<UnitStandards> findUnitStandardByQualificationKey(Long qualificationId) throws Exception{
		return dao.findUnitStandardByQualificationKey(qualificationId);
	}
	
	public void createLink(UnitStandards unitStandard, Qualification qualification, UnitStandardTypeEnum unitStandardTypeEnum) throws Exception{
		QualificationUnitStandards qualificationUnitStandards = new QualificationUnitStandards();
		qualificationUnitStandards.setQualification(qualification);
		qualificationUnitStandards.setUnitStandards(unitStandard);
		qualificationUnitStandards.setUnitStandardTypeEnum(unitStandardTypeEnum);
		create(qualificationUnitStandards);
	}
	
	public void createLink(QualificationUnitStandards qualificationUnitStandards, Qualification qualification) throws Exception{
		qualificationUnitStandards.setQualification(qualification);
		create(qualificationUnitStandards);
	}
	
	public Integer countByUnitStandardAndQualification(UnitStandards unitStandard, Qualification qualification) throws Exception {
		return dao.countByUnitStandardAndLearnership(unitStandard.getId(), qualification.getId());
	}
	
	public void processCsvData(List<QualificationUnitStandards> csvData) throws Exception {
		if (!csvData.isEmpty()) {
			if (unitStandardsService == null) {
				unitStandardsService = new UnitStandardsService();
			}
			if (qualificationService == null) {
				qualificationService = new QualificationService();
			}
			for (QualificationUnitStandards entry : csvData) {
				processImportEntry(entry);
			}
			saveCsvUploadData(csvData);
			csvData.clear();
			qualificationService = null;
			unitStandardsService = null;
		}
	}

	/**
	 * @param entry
	 */
	public void processImportEntry(QualificationUnitStandards entry) throws Exception {
		boolean importSuccessful = true;
		if (entry.getQualificationIdEntry() != null && !entry.getQualificationIdEntry().trim().isEmpty()) {
			try {
				Qualification qual = qualificationService.findByCode(entry.getQualificationIdEntry().trim());
				if (qual != null && qual.getId() != null) {
					entry.setQualification(qual);
				} else {
					importSuccessful = false;
				}
				qual = null;
			} catch (Exception e) {
				importSuccessful = false;
				logger.fatal(e);
			}
		} else {
			importSuccessful = false;
		}
		
		if (entry.getUnitStandardIdEntry() != null && !entry.getUnitStandardIdEntry().trim().isEmpty()) {
			try {
				UnitStandards us = unitStandardsService.findByUnitStandartId(Integer.valueOf(entry.getUnitStandardIdEntry().trim()));
				if (us != null && us.getId() != null) {
					entry.setUnitStandards(us);
				} else {
					importSuccessful = false;
				}
				us = null;
			} catch (Exception e) {
				importSuccessful = false;
				logger.fatal(e);
			}
		} else {
			importSuccessful = false;
		}
		
		if (entry.getTypeEntry() != null && !entry.getTypeEntry().trim().isEmpty()) {
			switch (entry.getTypeEntry().trim().toUpperCase()) {
			case "C":
				entry.setUnitStandardTypeEnum(UnitStandardTypeEnum.Core);
				break;
			case "F":
				entry.setUnitStandardTypeEnum(UnitStandardTypeEnum.Fundamental);
				break;
			case "E":
				entry.setUnitStandardTypeEnum(UnitStandardTypeEnum.Elective);
				break;
			default:
				importSuccessful = false;
				break;
			}
		} else {
			importSuccessful = false;
		}
		
		entry.setImportEntry(true);
		entry.setImportSuccessful(importSuccessful);
	}
	
	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<QualificationUnitStandards> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
}
