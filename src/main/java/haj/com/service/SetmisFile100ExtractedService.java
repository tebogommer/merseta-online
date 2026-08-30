package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile100ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile100Bean;
import haj.com.entity.SetmisFile100Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.setmis.SETMISFile100Service;
import haj.com.utils.CSVUtil;

public class SetmisFile100ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile100ExtractedDAO dao = new SetmisFile100ExtractedDAO();

	/**
	 * Get all SetmisFile100Extracted
 	 * @author TechFinium 
 	 * @see   SetmisFile100Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile100Extracted}
	 * @throws Exception the exception
 	 */
	public List<SetmisFile100Extracted> allSetmisFile100Extracted() throws Exception {
	  	return dao.allSetmisFile100Extracted();
	}


	/**
	 * Create or update SetmisFile100Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SetmisFile100Extracted
	 */
	public void create(SetmisFile100Extracted entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SetmisFile100Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile100Extracted
	 */
	public void update(SetmisFile100Extracted entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SetmisFile100Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile100Extracted
	 */
	public void delete(SetmisFile100Extracted entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SetmisFile100Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile100Extracted
	 */
	public SetmisFile100Extracted findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile100Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SetmisFile100Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile100Extracted
	 */
	public List<SetmisFile100Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SetmisFile100Extracted
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile100Extracted}
	 * @throws Exception the exception
	 */
	public List<SetmisFile100Extracted> allSetmisFile100Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile100Extracted(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SetmisFile100Extracted for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SetmisFile100Extracted
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SetmisFile100Extracted.class);
	}
	
    /**
     * Lazy load SetmisFile100Extracted with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SetmisFile100Extracted class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SetmisFile100Extracted} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SetmisFile100Extracted> allSetmisFile100Extracted(Class<SetmisFile100Extracted> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SetmisFile100Extracted>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SetmisFile100Extracted for lazy load with filters
     * @author TechFinium 
     * @param entity SetmisFile100Extracted class
     * @param filters the filters
     * @return Number of rows in the SetmisFile100Extracted entity
     * @throws Exception the exception     
     */	
	public int count(Class<SetmisFile100Extracted> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	// service level
	private SETMISFile100Service setmisFile100Service = new SETMISFile100Service(); 
	
	public void deleteAllEntries() throws Exception {
		List<IDataEntity> deleteList = new ArrayList<>();
		deleteList.addAll(allSetmisFile100Extracted());
		if (deleteList.size() > 0) {
			dao.deleteBatch(deleteList);
		}
	}
	
	public void populateEntity() throws Exception {
		deleteAllEntries(); 
		List<SETMISFile100Bean> beanResultList = setmisFile100Service.extractSETMISFile100Bean();
		List<IDataEntity> createList = new ArrayList<IDataEntity>();
		if (beanResultList.size() > 0) {
			int lineNumber = 1;
			for (SETMISFile100Bean beanEntry : beanResultList) {
				SetmisFile100Extracted newEntry = new SetmisFile100Extracted();
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

	private void populateAdditionalInfoImport(SetmisFile100Extracted extractedEntry, SETMISFile100Bean bean, Integer lineNumber) throws Exception{
		// sets lineNumber
		extractedEntry.setLineNumber(lineNumber.toString().trim());
		
		// sets format
		SimpleDateFormat sdf = new SimpleDateFormat(DataExtractService.QCTO_DATE_FORMAT);
		
		if (bean.getProviderStartDate() != null) {
			extractedEntry.setProviderStartDateString(sdf.format(bean.getProviderStartDate()));
		}
		
		if (bean.getProviderEndDate() != null) {
			extractedEntry.setProviderEndDateString(sdf.format(bean.getProviderEndDate()));
		}
		
		if (bean.getLatitudeDegree() != null) {
			extractedEntry.setLatitudeDegreeString(bean.getLatitudeDegree().toString());
		}
		 
		if (bean.getLatitudeDegree() != null) {
			extractedEntry.setLatitudeDegreeString(bean.getLatitudeDegree().toString());
		}
		
		if (bean.getLatitudeMinutes() != null) {
			extractedEntry.setLatitudeMinutesString(bean.getLatitudeMinutes().toString());
		}
		
		if (bean.getLatitudeSeconds() != null) {
			extractedEntry.setLatitudeSecondsString(bean.getLatitudeSeconds().toString());
		}
		
		if (bean.getLongitudeDegree() != null) {
			extractedEntry.setLongitudeDegreeString(bean.getLongitudeDegree().toString());
		}
		
		if (bean.getLongitudeMinutes() != null) {
			extractedEntry.setLongitudeMinutesString(bean.getLongitudeMinutes().toString());
		}
		
		if (bean.getLongitudeSeconds() != null) {
			extractedEntry.setLongitudeSecondsString(bean.getLongitudeSeconds().toString());
		}
		
		if (bean.getDateStamp() != null) {
			extractedEntry.setDateStampString(sdf.format(bean.getDateStamp()));
		}
	}
	
	public void downloadData() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(new Date());
		List<SetmisFile100Extracted> allData = allSetmisFile100Extracted();
		String csv = CSVUtil.writeFixedLength(allData);
		String fileName =  "MERS_0006_100_v001_" + currentDate + ".dat";
		JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName , "text/plain");
	}
	
}
