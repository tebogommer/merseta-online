package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile506ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile506Bean;
import haj.com.entity.SetmisFile506Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.setmis.SETMISFile506Service;
import haj.com.utils.CSVUtil;

public class SetmisFile506ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile506ExtractedDAO dao = new SetmisFile506ExtractedDAO();

	/**
	 * Get all SetmisFile506Extracted
 	 * @author TechFinium 
 	 * @see   SetmisFile506Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile506Extracted}
	 * @throws Exception the exception
 	 */
	public List<SetmisFile506Extracted> allSetmisFile506Extracted() throws Exception {
	  	return dao.allSetmisFile506Extracted();
	}


	/**
	 * Create or update SetmisFile506Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SetmisFile506Extracted
	 */
	public void create(SetmisFile506Extracted entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SetmisFile506Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile506Extracted
	 */
	public void update(SetmisFile506Extracted entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SetmisFile506Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile506Extracted
	 */
	public void delete(SetmisFile506Extracted entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SetmisFile506Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile506Extracted
	 */
	public SetmisFile506Extracted findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile506Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SetmisFile506Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile506Extracted
	 */
	public List<SetmisFile506Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SetmisFile506Extracted
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile506Extracted}
	 * @throws Exception the exception
	 */
	public List<SetmisFile506Extracted> allSetmisFile506Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile506Extracted(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SetmisFile506Extracted for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SetmisFile506Extracted
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SetmisFile506Extracted.class);
	}
	
    /**
     * Lazy load SetmisFile506Extracted with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SetmisFile506Extracted class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SetmisFile506Extracted} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SetmisFile506Extracted> allSetmisFile506Extracted(Class<SetmisFile506Extracted> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SetmisFile506Extracted>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SetmisFile506Extracted for lazy load with filters
     * @author TechFinium 
     * @param entity SetmisFile506Extracted class
     * @param filters the filters
     * @return Number of rows in the SetmisFile506Extracted entity
     * @throws Exception the exception     
     */	
	public int count(Class<SetmisFile506Extracted> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	// service level
		private SETMISFile506Service setmisFile506Service = new SETMISFile506Service(); 
		
		public void deleteAllEntries() throws Exception {
			List<IDataEntity> deleteList = new ArrayList<>();
			deleteList.addAll(allSetmisFile506Extracted());
			if (deleteList.size() > 0) {
				dao.deleteBatch(deleteList);
			}
		}
		
		public void populateEntity() throws Exception {
			deleteAllEntries(); 
			List<SETMISFile506Bean> beanResultList = setmisFile506Service.extractSETMISFile506Bean();
			List<IDataEntity> createList = new ArrayList<IDataEntity>();
			if (beanResultList.size() > 0) {
				int lineNumber = 1;
				for (SETMISFile506Bean beanEntry : beanResultList) {
					SetmisFile506Extracted newEntry = new SetmisFile506Extracted();
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

		private void populateAdditionalInfoImport(SetmisFile506Extracted extractedEntry, SETMISFile506Bean bean, Integer lineNumber) throws Exception{
			// sets lineNumber
			extractedEntry.setLineNumber(lineNumber.toString().trim());
			
			// sets format
			SimpleDateFormat sdf = new SimpleDateFormat(DataExtractService.QCTO_DATE_FORMAT);
			
			//private Date qualificationAchievementDate;
			if (bean.getQualificationAchievementDate() != null) {
				extractedEntry.setQualificationAchievementDateString(sdf.format(bean.getQualificationAchievementDate()));
			}
			//private Date startDate;
			if (bean.getStartDate() != null) {
				extractedEntry.setStartDateString(sdf.format(bean.getStartDate()));
			}
			//private Date endDate;
			if (bean.getEndDate() != null) {
				extractedEntry.setEndDateString(sdf.format(bean.getEndDate()));
			}
			
			//private Date dateStamp;
			if (bean.getDateStamp() != null) {
				extractedEntry.setDateStampString(sdf.format(bean.getDateStamp()));
			}
			
		}
		
		public void downloadData() throws Exception{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String currentDate = sdf.format(new Date());
			List<SetmisFile506Extracted> allData = allSetmisFile506Extracted();
			String csv = CSVUtil.writeFixedLength(allData);
			String fileName =  "MERS_0006_506_v001_" + currentDate + ".dat";
			JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName , "text/plain");
		}
}
