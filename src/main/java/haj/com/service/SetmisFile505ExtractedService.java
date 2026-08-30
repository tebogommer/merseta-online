package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile505ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile505Bean;
import haj.com.entity.SetmisFile505Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.setmis.SETMISFile505Service;
import haj.com.utils.CSVUtil;

public class SetmisFile505ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile505ExtractedDAO dao = new SetmisFile505ExtractedDAO();

	/**
	 * Get all SetmisFile505Extracted
 	 * @author TechFinium 
 	 * @see   SetmisFile505Extracted
 	 * @return a list of {@link haj.com.entity.SetmisFile505Extracted}
	 * @throws Exception the exception
 	 */
	public List<SetmisFile505Extracted> allSetmisFile505Extracted() throws Exception {
	  	return dao.allSetmisFile505Extracted();
	}


	/**
	 * Create or update SetmisFile505Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SetmisFile505Extracted
	 */
	public void create(SetmisFile505Extracted entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SetmisFile505Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile505Extracted
	 */
	public void update(SetmisFile505Extracted entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SetmisFile505Extracted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SetmisFile505Extracted
	 */
	public void delete(SetmisFile505Extracted entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SetmisFile505Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile505Extracted
	 */
	public SetmisFile505Extracted findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile505Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SetmisFile505Extracted}
	 * @throws Exception the exception
	 * @see    SetmisFile505Extracted
	 */
	public List<SetmisFile505Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SetmisFile505Extracted
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile505Extracted}
	 * @throws Exception the exception
	 */
	public List<SetmisFile505Extracted> allSetmisFile505Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile505Extracted(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SetmisFile505Extracted for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SetmisFile505Extracted
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SetmisFile505Extracted.class);
	}
	
    /**
     * Lazy load SetmisFile505Extracted with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SetmisFile505Extracted class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SetmisFile505Extracted} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SetmisFile505Extracted> allSetmisFile505Extracted(Class<SetmisFile505Extracted> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SetmisFile505Extracted>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SetmisFile505Extracted for lazy load with filters
     * @author TechFinium 
     * @param entity SetmisFile505Extracted class
     * @param filters the filters
     * @return Number of rows in the SetmisFile505Extracted entity
     * @throws Exception the exception     
     */	
	public int count(Class<SetmisFile505Extracted> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	// service level
		private SETMISFile505Service setmisFile505Service = new SETMISFile505Service(); 
		
		public void deleteAllEntries() throws Exception {
			List<IDataEntity> deleteList = new ArrayList<>();
			deleteList.addAll(allSetmisFile505Extracted());
			if (deleteList.size() > 0) {
				dao.deleteBatch(deleteList);
			}
		}
		
		public void populateEntity() throws Exception {
			deleteAllEntries(); 
			List<SETMISFile505Bean> beanResultList = setmisFile505Service.extractSETMISFile505Bean();
			List<IDataEntity> createList = new ArrayList<IDataEntity>();
			if (beanResultList.size() > 0) {
				int lineNumber = 1;
				for (SETMISFile505Bean beanEntry : beanResultList) {
					SetmisFile505Extracted newEntry = new SetmisFile505Extracted();
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

		private void populateAdditionalInfoImport(SetmisFile505Extracted extractedEntry, SETMISFile505Bean bean, Integer lineNumber) throws Exception{
			// sets lineNumber
			extractedEntry.setLineNumber(lineNumber.toString().trim());
			
			// sets format
			SimpleDateFormat sdf = new SimpleDateFormat(DataExtractService.QCTO_DATE_FORMAT);
			
			
			
			if (bean.getDateStamp() != null) {
				extractedEntry.setDateStampString(sdf.format(bean.getDateStamp()));
			}
		}
		
		public void downloadData() throws Exception{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String currentDate = sdf.format(new Date());
			List<SetmisFile505Extracted> allData = allSetmisFile505Extracted();
			String csv = CSVUtil.writeFixedLength(allData);
			String fileName =  "MERS_0006_505_v001_" + currentDate + ".dat";
			JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName , "text/plain");
		}
}
