package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile501ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile501Bean;
import haj.com.entity.SetmisFile501Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.setmis.SETMISFile501Service;
import haj.com.utils.CSVUtil;

public class SetmisFile501ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile501ExtractedDAO dao = new SetmisFile501ExtractedDAO();

	/**
	 * Get all SetmisFile501Extracted
 	 * @author TechFinium 
 	 * @see   SetmisFile501Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile501Extracted}
	 * @throws Exception the exception
 	 */
	public List<SetmisFile501Extracted> allSetmisFile501Extracted() throws Exception {
	  	return dao.allSetmisFile501Extracted();
	}


	/**
	 * Create or update SetmisFile501Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SetmisFile501Extracted
	 */
	public void create(SetmisFile501Extracted entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SetmisFile501Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile501Extracted
	 */
	public void update(SetmisFile501Extracted entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SetmisFile501Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile501Extracted
	 */
	public void delete(SetmisFile501Extracted entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SetmisFile501Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile501Extracted
	 */
	public SetmisFile501Extracted findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile501Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SetmisFile501Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile501Extracted
	 */
	public List<SetmisFile501Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SetmisFile501Extracted
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile501Extracted}
	 * @throws Exception the exception
	 */
	public List<SetmisFile501Extracted> allSetmisFile501Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile501Extracted(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SetmisFile501Extracted for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SetmisFile501Extracted
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SetmisFile501Extracted.class);
	}
	
    /**
     * Lazy load SetmisFile501Extracted with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SetmisFile501Extracted class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SetmisFile501Extracted} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SetmisFile501Extracted> allSetmisFile501Extracted(Class<SetmisFile501Extracted> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SetmisFile501Extracted>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SetmisFile501Extracted for lazy load with filters
     * @author TechFinium 
     * @param entity SetmisFile501Extracted class
     * @param filters the filters
     * @return Number of rows in the SetmisFile501Extracted entity
     * @throws Exception the exception     
     */	
	public int count(Class<SetmisFile501Extracted> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	// service level
		private SETMISFile501Service setmisFile501Service = new SETMISFile501Service(); 
		
		public void deleteAllEntries() throws Exception {
			List<IDataEntity> deleteList = new ArrayList<>();
			deleteList.addAll(allSetmisFile501Extracted());
			if (deleteList.size() > 0) {
				dao.deleteBatch(deleteList);
			}
		}
		
		public void populateEntity() throws Exception {
			deleteAllEntries(); 
			List<SETMISFile501Bean> beanResultList = setmisFile501Service.extractSETMISFile501Bean();
			List<IDataEntity> createList = new ArrayList<IDataEntity>();
			if (beanResultList.size() > 0) {
				int lineNumber = 1;
				for (SETMISFile501Bean beanEntry : beanResultList) {
					SetmisFile501Extracted newEntry = new SetmisFile501Extracted();
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

		private void populateAdditionalInfoImport(SetmisFile501Extracted extractedEntry, SETMISFile501Bean bean, Integer lineNumber) throws Exception{
			// sets lineNumber
			extractedEntry.setLineNumber(lineNumber.toString().trim());
			
			// sets format
			SimpleDateFormat sdf = new SimpleDateFormat(DataExtractService.QCTO_DATE_FORMAT);
			
			//private Date enrolmentStatusDate;
			if (bean.getEnrolmentStatusDate() != null) {
				extractedEntry.setEnrolmentStatusDateString(sdf.format(bean.getEnrolmentStatusDate()));
			}
			//private Date enrolmentDate;
			if (bean.getEnrolmentDate() != null) {
				extractedEntry.setEnrolmentDateString(sdf.format(bean.getEnrolmentDate()));
			}
			//private Date mostRecentRegistrationDate;
			if (bean.getMostRecentRegistrationDate() != null) {
				extractedEntry.setMostRecentRegistrationDateString(sdf.format(bean.getMostRecentRegistrationDate()));
			}
			
			if (bean.getDateStamp() != null) {
				extractedEntry.setDateStampString(sdf.format(bean.getDateStamp()));
			}
		}
		
		public void downloadData() throws Exception{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String currentDate = sdf.format(new Date());
			List<SetmisFile501Extracted> allData = allSetmisFile501Extracted();
			String csv = CSVUtil.writeFixedLength(allData);
			String fileName =  "MERS_0006_501_v001_" + currentDate + ".dat";
			JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName , "text/plain");
		}
}
