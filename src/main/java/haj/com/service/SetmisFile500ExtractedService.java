package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile500ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile500Bean;
import haj.com.entity.SetmisFile500Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.setmis.SETMISFile500Service;
import haj.com.utils.CSVUtil;

public class SetmisFile500ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile500ExtractedDAO dao = new SetmisFile500ExtractedDAO();

	/**
	 * Get all SetmisFile500Extracted
 	 * @author TechFinium 
 	 * @see   SetmisFile500Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile500Extracted}
	 * @throws Exception the exception
 	 */
	public List<SetmisFile500Extracted> allSetmisFile500Extracted() throws Exception {
	  	return dao.allSetmisFile500Extracted();
	}


	/**
	 * Create or update SetmisFile500Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SetmisFile500Extracted
	 */
	public void create(SetmisFile500Extracted entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SetmisFile500Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile500Extracted
	 */
	public void update(SetmisFile500Extracted entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SetmisFile500Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile500Extracted
	 */
	public void delete(SetmisFile500Extracted entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SetmisFile500Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile500Extracted
	 */
	public SetmisFile500Extracted findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile500Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SetmisFile500Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile500Extracted
	 */
	public List<SetmisFile500Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SetmisFile500Extracted
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile500Extracted}
	 * @throws Exception the exception
	 */
	public List<SetmisFile500Extracted> allSetmisFile500Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile500Extracted(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SetmisFile500Extracted for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SetmisFile500Extracted
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SetmisFile500Extracted.class);
	}
	
    /**
     * Lazy load SetmisFile500Extracted with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SetmisFile500Extracted class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SetmisFile500Extracted} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SetmisFile500Extracted> allSetmisFile500Extracted(Class<SetmisFile500Extracted> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SetmisFile500Extracted>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SetmisFile500Extracted for lazy load with filters
     * @author TechFinium 
     * @param entity SetmisFile500Extracted class
     * @param filters the filters
     * @return Number of rows in the SetmisFile500Extracted entity
     * @throws Exception the exception     
     */	
	public int count(Class<SetmisFile500Extracted> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	// service level
		private SETMISFile500Service setmisFile500Service = new SETMISFile500Service(); 
		
		public void deleteAllEntries() throws Exception {
			List<IDataEntity> deleteList = new ArrayList<>();
			deleteList.addAll(allSetmisFile500Extracted());
			if (deleteList.size() > 0) {
				dao.deleteBatch(deleteList);
			}
		}
		
		public void populateEntity() throws Exception {
			deleteAllEntries(); 
			List<SETMISFile500Bean> beanResultList = setmisFile500Service.extractSETMISFile500Bean();
			List<IDataEntity> createList = new ArrayList<IDataEntity>();
			if (beanResultList.size() > 0) {
				int lineNumber = 1;
				for (SETMISFile500Bean beanEntry : beanResultList) {
					SetmisFile500Extracted newEntry = new SetmisFile500Extracted();
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

		private void populateAdditionalInfoImport(SetmisFile500Extracted extractedEntry, SETMISFile500Bean bean, Integer lineNumber) throws Exception{
			// sets lineNumber
			extractedEntry.setLineNumber(lineNumber.toString().trim());
			
			// sets format
			SimpleDateFormat sdf = new SimpleDateFormat(DataExtractService.QCTO_DATE_FORMAT);
			
			// //private Date enrolmentStatusDate;
			if (bean.getEnrolmentStatusDate() != null) {
				extractedEntry.setEnrolmentStatusDateString(sdf.format(bean.getEnrolmentStatusDate()));
			}
			//private Date enrolmentDate;
			if (bean.getEnrolmentDate() != null) {
				extractedEntry.setEnrolmentDate(sdf.format(bean.getEnrolmentDate()));
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
			List<SetmisFile500Extracted> allData = allSetmisFile500Extracted();
			String csv = CSVUtil.writeFixedLength(allData);
			String fileName =  "MERS_0006_500_v001_" + currentDate + ".dat";
			JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName , "text/plain");
		}
}
