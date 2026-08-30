package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile502ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile502Bean;
import haj.com.entity.SetmisFile502Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.setmis.SETMISFile502Service;
import haj.com.utils.CSVUtil;

public class SetmisFile502ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile502ExtractedDAO dao = new SetmisFile502ExtractedDAO();

	/**
	 * Get all SetmisFile502Extracted
 	 * @author TechFinium 
 	 * @see   SetmisFile502Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile502Extracted}
	 * @throws Exception the exception
 	 */
	public List<SetmisFile502Extracted> allSetmisFile502Extracted() throws Exception {
	  	return dao.allSetmisFile502Extracted();
	}


	/**
	 * Create or update SetmisFile502Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SetmisFile502Extracted
	 */
	public void create(SetmisFile502Extracted entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SetmisFile502Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile502Extracted
	 */
	public void update(SetmisFile502Extracted entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SetmisFile502Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile502Extracted
	 */
	public void delete(SetmisFile502Extracted entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SetmisFile502Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile502Extracted
	 */
	public SetmisFile502Extracted findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile502Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SetmisFile502Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile502Extracted
	 */
	public List<SetmisFile502Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SetmisFile502Extracted
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile502Extracted}
	 * @throws Exception the exception
	 */
	public List<SetmisFile502Extracted> allSetmisFile502Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile502Extracted(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SetmisFile502Extracted for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SetmisFile502Extracted
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SetmisFile502Extracted.class);
	}
	
    /**
     * Lazy load SetmisFile502Extracted with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SetmisFile502Extracted class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SetmisFile502Extracted} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SetmisFile502Extracted> allSetmisFile502Extracted(Class<SetmisFile502Extracted> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SetmisFile502Extracted>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SetmisFile502Extracted for lazy load with filters
     * @author TechFinium 
     * @param entity SetmisFile502Extracted class
     * @param filters the filters
     * @return Number of rows in the SetmisFile502Extracted entity
     * @throws Exception the exception     
     */	
	public int count(Class<SetmisFile502Extracted> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	// service level
		private SETMISFile502Service setmisFile502Service = new SETMISFile502Service(); 
		
		public void deleteAllEntries() throws Exception {
			List<IDataEntity> deleteList = new ArrayList<>();
			deleteList.addAll(allSetmisFile502Extracted());
			if (deleteList.size() > 0) {
				dao.deleteBatch(deleteList);
			}
		}
		
		public void populateEntity() throws Exception {
			deleteAllEntries(); 
			List<SETMISFile502Bean> beanResultList = setmisFile502Service.extractSETMISFile502Bean();
			List<IDataEntity> createList = new ArrayList<IDataEntity>();
			if (beanResultList.size() > 0) {
				int lineNumber = 1;
				for (SETMISFile502Bean beanEntry : beanResultList) {
					SetmisFile502Extracted newEntry = new SetmisFile502Extracted();
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

		private void populateAdditionalInfoImport(SetmisFile502Extracted extractedEntry, SETMISFile502Bean bean, Integer lineNumber) throws Exception{
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
			//private Date dateStamp;
			if (bean.getDateStamp() != null) {
				extractedEntry.setDateStampString(sdf.format(bean.getDateStamp()));
			}
			
		}
		
		public void downloadData() throws Exception{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String currentDate = sdf.format(new Date());
			List<SetmisFile502Extracted> allData = allSetmisFile502Extracted();
			String csv = CSVUtil.writeFixedLength(allData);
			String fileName =  "MERS_0006_502_v001_" + currentDate + ".dat";
			JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName , "text/plain");
		}
}
