package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile503ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile503Bean;
import haj.com.entity.SetmisFile503Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.setmis.SETMISFile503Service;
import haj.com.utils.CSVUtil;

public class SetmisFile503ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile503ExtractedDAO dao = new SetmisFile503ExtractedDAO();

	/**
	 * Get all SetmisFile503Extracted
 	 * @author TechFinium 
 	 * @see   SetmisFile503Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile503Extracted}
	 * @throws Exception the exception
 	 */
	public List<SetmisFile503Extracted> allSetmisFile503Extracted() throws Exception {
	  	return dao.allSetmisFile503Extracted();
	}


	/**
	 * Create or update SetmisFile503Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SetmisFile503Extracted
	 */
	public void create(SetmisFile503Extracted entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SetmisFile503Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile503Extracted
	 */
	public void update(SetmisFile503Extracted entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SetmisFile503Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile503Extracted
	 */
	public void delete(SetmisFile503Extracted entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SetmisFile503Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile503Extracted
	 */
	public SetmisFile503Extracted findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile503Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SetmisFile503Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile503Extracted
	 */
	public List<SetmisFile503Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SetmisFile503Extracted
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile503Extracted}
	 * @throws Exception the exception
	 */
	public List<SetmisFile503Extracted> allSetmisFile503Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile503Extracted(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SetmisFile503Extracted for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SetmisFile503Extracted
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SetmisFile503Extracted.class);
	}
	
    /**
     * Lazy load SetmisFile503Extracted with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SetmisFile503Extracted class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SetmisFile503Extracted} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SetmisFile503Extracted> allSetmisFile503Extracted(Class<SetmisFile503Extracted> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SetmisFile503Extracted>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SetmisFile503Extracted for lazy load with filters
     * @author TechFinium 
     * @param entity SetmisFile503Extracted class
     * @param filters the filters
     * @return Number of rows in the SetmisFile503Extracted entity
     * @throws Exception the exception     
     */	
	public int count(Class<SetmisFile503Extracted> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	// service level
		private SETMISFile503Service setmisFile503Service = new SETMISFile503Service(); 
		
		public void deleteAllEntries() throws Exception {
			List<IDataEntity> deleteList = new ArrayList<>();
			deleteList.addAll(allSetmisFile503Extracted());
			if (deleteList.size() > 0) {
				dao.deleteBatch(deleteList);
			}
		}
		
		public void populateEntity() throws Exception {
			deleteAllEntries(); 
			List<SETMISFile503Bean> beanResultList = setmisFile503Service.extractSETMISFile503Bean();
			List<IDataEntity> createList = new ArrayList<IDataEntity>();
			if (beanResultList.size() > 0) {
				int lineNumber = 1;
				for (SETMISFile503Bean beanEntry : beanResultList) {
					SetmisFile503Extracted newEntry = new SetmisFile503Extracted();
					org.apache.commons.beanutils.BeanUtils.copyProperties(newEntry, beanEntry);
					populateAdditionalInfoImport(newEntry, beanEntry, lineNumber);
					createList.add(newEntry);
					lineNumber++;
				}
			}
			if (createList.size() > 0) {
				dao.createBatch(createList, 1000);
			}
		}

		private void populateAdditionalInfoImport(SetmisFile503Extracted extractedEntry, SETMISFile503Bean bean, Integer lineNumber) throws Exception{
			// sets lineNumber
			extractedEntry.setLineNumber(lineNumber.toString().trim());
			
			// sets format
			SimpleDateFormat sdf = new SimpleDateFormat(DataExtractService.QCTO_DATE_FORMAT);
			
			//private Integer unitStandardId;
			if (bean.getUnitStandardId() != null) {
				extractedEntry.setUnitStandardIdString(bean.getUnitStandardId().toString());
			}
			//private Date enrolmentStatusDate;
			if (bean.getEnrolmentStatusDate() != null) {
				extractedEntry.setEnrolmentStatusDateString(sdf.format(bean.getEnrolmentStatusDate()));
			}
			//private Date enrolmentDate;
			if (bean.getEnrolmentDate() != null) {
				extractedEntry.setEnrolmentDateString(sdf.format(bean.getEnrolmentDate()));
			}
			//private Integer qualificationId;
			if (bean.getQualificationId() != null) {
				extractedEntry.setQualificationIdString(bean.getQualificationId().toString());
			}
			//private Date mostRecentRegistrationDate;
			if (bean.getMostRecentRegistrationDate() != null) {
				extractedEntry.setMostRecentRegistrationDateString(sdf.format(bean.getMostRecentRegistrationDate()));
			}
			//private Date dateStamp;
			if (bean.getDateStamp() != null) {
				extractedEntry.setDateStampString(sdf.format(bean.getDateStamp()));
			}
		}
		
		public void downloadData() throws Exception{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String currentDate = sdf.format(new Date());
			List<SetmisFile503Extracted> allData = allSetmisFile503Extracted();
			String csv = CSVUtil.writeFixedLength(allData);
			String fileName =  "MERS_0006_503_v001_" + currentDate + ".dat";
			JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName , "text/plain");
		}
	
}
