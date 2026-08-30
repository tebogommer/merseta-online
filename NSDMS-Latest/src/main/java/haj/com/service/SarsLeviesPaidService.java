package haj.com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.SarsFilesDAO;
import haj.com.dao.SarsLeviesPaidDAO;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.Users;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.sars.SARSConstants;
import haj.com.sars.SarsFiles;
import haj.com.sars.SarsLeviesPaid;
import haj.com.service.lookup.MailTemplatesService;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsLeviesPaidService.
 */
public class SarsLeviesPaidService extends AbstractService {
	/** The dao. */
	private SarsLeviesPaidDAO dao = new SarsLeviesPaidDAO();
	private DocService docService = new DocService();
	private UsersService usersService = new UsersService();
	private SarsFilesDAO sarsFilesDAO = new SarsFilesDAO();
	/**
	 * Get all SarsLeviesPaid.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.sars.SarsLeviesPaid}
	 * @throws Exception the exception
	 * @see   SarsLeviesPaid
	 */
	public List<SarsLeviesPaid> allSarsLeviesPaid() throws Exception {
	  	return dao.allSarsLeviesPaid();
	}


	/**
	 * Create or update SarsLeviesPaid.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @param users 
	 * @param doc 
	 * @throws Exception the exception
	 * @see     SarsLeviesPaid
	 */
	public void create(SarsLeviesPaid entity, Users users, Doc doc) throws Exception  {
	  if (entity.getLevyFileD()!=null ) {
			entity.setLevyFile(SARSConstants.sdf4.format(entity.getLevyFileD()));
			entity.setSarsFiles(findByYearMonth(entity.getLevyFile()));
			if (entity.getSarsFiles()==null) {
				throw new Exception("The levy file does not exist on the system");
			}
		}
	   if (entity.getId() ==null) {
		 documentUpload(entity, users);
		 notifyParties(entity);
	   }
	  else
	  {
		 this.dao.update(entity);
		 if (doc.getData()!=null && doc.getId()!=null) {
		    uploadNewVersion(doc, users);
		 }
		 else if (doc.getData()!=null && doc.getId()==null) {
			 documentUpload(entity, users);
		 }
	  }
	}


	private void notifyParties(SarsLeviesPaid sarsLeviesPaid) {
		try {
			List<Users> ul = usersService.findByRole(HAJConstants.SENIOR_MANAGER);
			if (ul !=null && ul.size() > 0) {
				MailDef mailDef = MailDef.instance(MailEnum.LevyFileUploadNotification, MailTagsEnum.Date, sarsLeviesPaid.getLevyFile().trim());
					for (Users user : ul) {
					MailTemplatesService.instance().sendMail(user, "Bank Statement for levy file has been uploaded",mailDef,"Bank Statement for levy file has been uploaded for "+ sarsLeviesPaid.getLevyFile().trim());
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		
	}


	/**
	 * Update  SarsLeviesPaid.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsLeviesPaid
	 */
	public void update(SarsLeviesPaid entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SarsLeviesPaid.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsLeviesPaid
	 */
	public void delete(SarsLeviesPaid entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.sars.SarsLeviesPaid}
	 * @throws Exception the exception
	 * @see    SarsLeviesPaid
	 */
	public SarsLeviesPaid findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SarsLeviesPaid by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.sars.SarsLeviesPaid}
	 * @throws Exception the exception
	 * @see    SarsLeviesPaid
	 */
	public List<SarsLeviesPaid> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SarsLeviesPaid.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.sars.SarsLeviesPaid}
	 * @throws Exception the exception
	 */
	public List<SarsLeviesPaid> allSarsLeviesPaid(int first, int pageSize) throws Exception {
		return dao.allSarsLeviesPaid(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SarsLeviesPaid for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SarsLeviesPaid
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SarsLeviesPaid.class);
	}
	
    /**
     * Lazy load SarsLeviesPaid with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SarsLeviesPaid class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.sars.SarsLeviesPaid} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SarsLeviesPaid> allSarsLeviesPaid(Class<SarsLeviesPaid> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return  resolveDocs( ( List<SarsLeviesPaid>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	
	}
	
	private List<SarsLeviesPaid> resolveDocs(List<SarsLeviesPaid> list) throws Exception {
		for (SarsLeviesPaid sarsLeviesPaid : list) {
			resolveDocs(sarsLeviesPaid);
		}
		return list;
	}


	private void resolveDocs(SarsLeviesPaid sarsLeviesPaid)  {
		try {
			sarsLeviesPaid.setDocs(docService.searchBySarsLeviesPaid(sarsLeviesPaid));
			
		} catch (Exception e) {
			logger.fatal(e);
		}

		
	}
	
    /**
     * Get count of SarsLeviesPaid for lazy load with filters.
     *
     * @author TechFinium
     * @param entity SarsLeviesPaid class
     * @param filters the filters
     * @return Number of rows in the SarsLeviesPaid entity
     * @throws Exception the exception
     */	
	public int count(Class<SarsLeviesPaid> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	
	/**
	 * Find by sars levy file.
	 *
	 * @param levyFileDate the levy file date
	 * @return the sars levies paid
	 * @throws Exception the exception
	 */
	public SarsLeviesPaid findBySarsLevyFile(Date levyFileDate) throws Exception { 
		List<SarsLeviesPaid> l =  dao.findBySarsLevyFile(SARSConstants.sdf4.format(levyFileDate));
		if (l.size()>1) {
			SarsLeviesPaid s = new SarsLeviesPaid();
			double total = 0.0;
			for (SarsLeviesPaid sarsLeviesPaid : l) {
				total += sarsLeviesPaid.getCheckbookAmount().doubleValue();
			}
			s.setGlPostingDate( l.get(0).getGlPostingDate());
			s.setLevyFile(l.get(0).getLevyFile());
		
			s.setCheckbookAmount(BigDecimal.valueOf(total));
			chequeBookAmount(s);
			return s;
		}
		else if (l.size() ==1)   {
			chequeBookAmount(l.get(0));
			return l.get(0);
		}
		else return null;
	}


	private void chequeBookAmount(SarsLeviesPaid sarsLeviesPaid) {
		if (sarsLeviesPaid.getCheckbookAmount()!=null && sarsLeviesPaid.getCheckbookAmount().doubleValue() < 0.0) {
			sarsLeviesPaid.setCheckbookAmount(BigDecimal.valueOf(
					 sarsLeviesPaid.getCheckbookAmount().doubleValue() * -1.0
					));
		}
		
	}
	
	public void documentUpload(SarsLeviesPaid entity, Users users) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		if ( entity.getId()==null) dataEntities.add(entity);
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() == null && doc.getData() != null) {
					doc.setBankStatement(entity);
					doc.setVersionNo(1);
					doc.setUsers(users);
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, users, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
		}
		
		this.dao.createBatch(dataEntities);
	}
	
	public void uploadNewVersion(Doc doc, Users users) throws Exception {
		List<Doc> docs = null;
		if (doc.getDocVerions() == null || doc.getDocVerions().isEmpty()) {
			docs = new ArrayList<>();
			docs.add(DocService.instance().findByKey(doc.getId()));
			doc.setId(null);
		} else {
			docs = doc.getDocVerions();
			Collections.reverse(docs);
			docs.add(0, doc);
		}
		if (doc.getData() != null) {
			DocService.instance().save(doc, users, docs);
		}
	}
	
	
	
	

	public SarsFiles findByYearMonth(Integer year, Integer month) throws Exception { 
		return sarsFilesDAO.findByYearMonth(year, month);
	}
	
	public SarsFiles findByYearMonth(String levyFileDate)  throws Exception { 
		Date d = SARSConstants.sdf4.parse(levyFileDate);
		return findByYearMonth(Integer.valueOf(SARSConstants.sdf5.format(d)), Integer.valueOf(SARSConstants.sdf6.format(d)));
	}


	public void sarsLeviesPaidWithNoLevyFileLink() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<SarsLeviesPaid> l =  dao.allSarsLeviesPaidWithNoLevyFileLink();
					List<IDataEntity> entityList = new ArrayList<IDataEntity>();
					for (SarsLeviesPaid sarsLeviesPaid : l) {
						if (sarsLeviesPaid.getLevyFile()!=null) {
						  sarsLeviesPaid.setSarsFiles(findByYearMonth(sarsLeviesPaid.getLevyFile()));
						  entityList.add(sarsLeviesPaid);
						}
					}
					if (entityList.size() >0) {
						dao.updateBatch(entityList);
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();

	} 
	
	public SarsLeviesPaid findBySarsFile(Long id) throws Exception { 
		return dao.findBySarsFile(id);
	}
	
	public SarsLeviesPaid findBySarsFile(SarsFiles sarsFiles) throws Exception {  
		return findBySarsFile(sarsFiles.getId());
	}
	
	public Date findpostingDateBySarsFile(SarsFiles sarsFiles) throws Exception {  
		Date d = sarsFiles.getForMonth();
		SarsLeviesPaid s = findBySarsFile(sarsFiles);
		if (s!=null && s.getGlPostingDate()!=null) d = s.getGlPostingDate();
		return d;
		
	}
}
