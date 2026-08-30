package haj.com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jsoup.Jsoup;
import org.primefaces.model.SortOrder;

import haj.com.bean.ExtensionRequestReportBean;
import haj.com.bean.SmeQualReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.SmeQualificationsDAO;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.SitesSme;
import haj.com.entity.SmeQualifications;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RejectReasonsService;

public class SmeQualificationsService extends AbstractService {
	/** The dao. */
	private SmeQualificationsDAO dao = new SmeQualificationsDAO();
	
	/** The Service Level */
	private ConfigDocService configDocService = new ConfigDocService();
	private CompanyLearnersService companyLearnersService;
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();
	
	/** Instance of service level */
	private static SmeQualificationsService smeQualificationsService;
	public static synchronized SmeQualificationsService instance() {
		if (smeQualificationsService == null) {
			smeQualificationsService = new SmeQualificationsService();
		}
		return smeQualificationsService;
	}

	/**
	 * Get all SmeQualifications
 	 * @author TechFinium 
 	 * @see   SmeQualifications
 	 * @return a list of {@link haj.com.entity.SmeQualifications}
	 * @throws Exception the exception
 	 */
	public List<SmeQualifications> allSmeQualifications() throws Exception {
	  	return dao.allSmeQualifications();
	}

	/**
	 * Create or update SmeQualifications.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SmeQualifications
	 */
	public void create(SmeQualifications entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SmeQualifications.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SmeQualifications
	 */
	public void update(SmeQualifications entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SmeQualifications.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SmeQualifications
	 */
	public void delete(SmeQualifications entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SmeQualifications}
	 * @throws Exception the exception
	 * @see    SmeQualifications
	 */
	public SmeQualifications findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SmeQualifications by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SmeQualifications}
	 * @throws Exception the exception
	 * @see    SmeQualifications
	 */
	public List<SmeQualifications> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Locates SmeQualifications by SitesSme
	 * @see SitesSme
	 * @param sitesSme
	 * @return List<SmeQualifications>
	 * @throws Exception
	 */
	public List<SmeQualifications> findBySme(SitesSme sitesSme) throws Exception {
		return populateAdditionalInformationList(dao.findBySme(sitesSme.getId()));
	}
	
	public List<SmeQualifications> findBySme(SitesSme sitesSme, ApprovalEnum status) throws Exception {
		return populateAdditionalInformationList(dao.findBySme(sitesSme.getId(), status));
	}
	
	public List<SmeQualifications> locateCountOfCompanyLearnersAgainstQualificationList(List<SmeQualifications> SmeQualifications, Company company) throws Exception{
		for (SmeQualifications smeQualification : SmeQualifications) {
			locateCountOfCompanyLearnersAgainstQualification(smeQualification, company);
		}
		return SmeQualifications;
	}
	
	private SmeQualifications locateCountOfCompanyLearnersAgainstQualification(SmeQualifications smeQualification, Company company) throws Exception{
		if (smeQualification != null && smeQualification.getQualification() != null && company != null && company.getId() != null) {
			if (companyLearnersService == null) {
				companyLearnersService = new CompanyLearnersService();
			}
			smeQualification.setLearnerAssignedCount(companyLearnersService.countLearnersAssignedToCompanyWithQualificationId(company, smeQualification.getQualification()));
		}
		return smeQualification;
	}

	/**
	 * Counts SmeQualifications by SitesSme
	 * @see SitesSme
	 * @param sitesSme
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countBySme(SitesSme sitesSme) throws Exception {
		return dao.countBySme(sitesSme.getId());
	}
	
	/**
	 * Find SmeQualifications by Qualification
	 * @see Qualification
	 * @param qualification
	 * @return List<SmeQualifications>
	 * @throws Exception
	 */
	public List<SmeQualifications> findByQualification(Qualification qualification) throws Exception {
		return dao.findByQualification(qualification.getId());
	}
	
	/**
	 * Count SmeQualifications by Qualification
	 * @see Qualification
	 * @param qualification
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countByQualification(Qualification qualification) throws Exception {
		return dao.countByQualification(qualification.getId());
	}
	
	/**
	 * Find SmeQualifications by Qualification and SiteSme
	 * @see Qualification
	 * @see SitesSme
	 * @param qualification
	 * @param sitesSme
	 * @return List<SmeQualifications>
	 * @throws Exception
	 */
	public List<SmeQualifications> findByQualificationAndSme(Qualification qualification, SitesSme sitesSme) throws Exception {
		return dao.findByQualificationAndSme(qualification.getId(), sitesSme.getId());
	}
	
	public List<SmeQualifications> findByQualificationAndCompany(Qualification qualification, Company company, ApprovalEnum status) throws Exception {
		return dao.findByQualificationAndCompany(qualification.getId(), company.getId(), status);
	}
	
	public List<SmeQualifications> findByCompany(Company company, ApprovalEnum status) throws Exception {
		return dao.findByCompany(company.getId(), status);
	}
	
	/**
	 * Count SmeQualifications by Qualification and SiteSme
	 * @see Qualification
	 * @see SitesSme
	 * @param qualification
	 * @param sitesSme
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countByQualificationAndSme(Qualification qualification, SitesSme sitesSme) throws Exception {
		return dao.countByQualificationAndSme(qualification.getId(), sitesSme.getId());
	}
	
	/**
	 * Lazy load SmeQualifications
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SmeQualifications}
	 * @throws Exception the exception
	 */
	public List<SmeQualifications> allSmeQualifications(int first, int pageSize) throws Exception {
		return dao.allSmeQualifications(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SmeQualifications for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SmeQualifications
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SmeQualifications.class);
	}
	
    /**
     * Lazy load SmeQualifications with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SmeQualifications class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SmeQualifications} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> allSmeQualifications(Class<SmeQualifications> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SmeQualifications>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of SmeQualifications for lazy load with filters
     * @author TechFinium 
     * @param entity SmeQualifications class
     * @param filters the filters
     * @return Number of rows in the SmeQualifications entity
     * @throws Exception the exception     
     */	
	public int count(Class<SmeQualifications> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * All SmeQualifications by SiteSme 
	 * @see SiteSme
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param siteSme
	 * @return List<SitesSme>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> allSmeQualificationsBySiteSme(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, SitesSme siteSme) throws Exception {
		String hql = "select o from SmeQualifications o where o.sitesSme.id = :siteSmeId";
		filters.put("siteSmeId", siteSme.getId());
		return populateAdditionalInformationList((List<SmeQualifications>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	/**
	 * Count All SmeQualifications by SiteSme
	 * @see SiteSme
	 * @param filters
	 * @return int the count
	 * @throws Exception
	 */
	public int countAllSmeQualificationsBySiteSme(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SmeQualifications o where o.sitesSme.id = :siteSmeId";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> allSmeQualificationsNoAdditionalInformation(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SmeQualifications o where o.sitesSme.id <> null";
		return populateRejectionReasonsList((List<SmeQualifications>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllSmeQualificationsNoAdditionalInformation(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SmeQualifications o where o.sitesSme.id <> null";
		return dao.countWhere(filters, hql);
	}
	
	public List<SmeQualifications> populateRejectionReasonsList(List<SmeQualifications> smeQualificationsList){
		for (SmeQualifications smeQualifications : smeQualificationsList) {
			populateRejectReasons(smeQualifications);
		}
		return smeQualificationsList;
	}
	
	public SmeQualifications populateRejectReasons(SmeQualifications smeQualifications) {
		if (rejectReasonsService == null) {
			rejectReasonsService = new RejectReasonsService();
		}
		if (smeQualifications.getStatus() == ApprovalEnum.Rejected) {
			List<RejectReasons>	rejectReason = new ArrayList<>();
			try {
				rejectReason = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(smeQualifications.getSmeQualificationsParent().getId(), SitesSme.class.getName(), ConfigDocProcessEnum.SITE_SME_REGISTRATION);
				if(rejectReason.isEmpty()) {
					rejectReason = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(smeQualifications.getSmeQualificationsParent().getId(), SitesSme.class.getName(), ConfigDocProcessEnum.SITE_SME_UPDATE);
				}			
				if(rejectReason.isEmpty()) {
					rejectReason = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(smeQualifications.getSmeQualificationsParent().getId(), SitesSme.class.getName(), ConfigDocProcessEnum.SITE_SME_DELETE);
				}
				if(!rejectReason.isEmpty()) {
					smeQualifications.setReason(rejectReasonsService.convertToString(rejectReason));
				}
			} catch (Exception e) {
				logger.fatal(e);
				smeQualifications.setReason("");
			}
		}
		return smeQualifications;
	}
	
	/**
	 * Populates additional information against a list of SmeQualifications
	 * @param smeQualificationsList
	 * @return List<SmeQualifications>
	 * @throws Exception
	 */
	public List<SmeQualifications> populateAdditionalInformationList(List<SmeQualifications> smeQualificationsList) throws Exception {
		for (SmeQualifications smeQualification : smeQualificationsList) {
			populateAdditionalInformation(smeQualification);
		}
		return smeQualificationsList;
	}
	
	/**
	 * Populates additional information against SmeQualifications
	 * @param smeQualification
	 * @return smeQualification
	 * @throws Exception
	 */
	public SmeQualifications populateAdditionalInformation(SmeQualifications smeQualification) throws Exception {
		populateDocuments(smeQualification);
		return smeQualification;
	}

	/**
	 * Locates or populates docs against SmeQualifications
	 * @param smeQualification
	 * @return smeQualification
	 * @throws Exception
	 */
	private SmeQualifications populateDocuments(SmeQualifications smeQualification) throws Exception {	
		// Document requirements
		if (smeQualification.getId() != null) {
			smeQualification.setDocs(DocService.instance().searchByTargetKeyAndClass(smeQualification.getClass().getName(), smeQualification.getId()));
		}
		// if no documents found against smeQualification creates a new blank document
		if (smeQualification.getDocs() == null || smeQualification.getDocs().isEmpty()) {
			smeQualification.setDocs(new ArrayList<>());
			smeQualification.getDocs().add(new Doc());
		}
		return smeQualification;
	}
	
	/**
	 * Preps new instance of SmeQualifications
	 * Populates documents against and if siteSme or qualification not null
	 * will link
	 * 
	 * Note siteSme may have a new instance but id could be null
	 * @param siteSme
	 * @param qualification
	 * @return
	 * @throws Exception
	 */
	public SmeQualifications prepNewSmeQualifications(SitesSme siteSme, Qualification qualification) throws Exception{
		SmeQualifications smeQualifications = populateAdditionalInformation(new SmeQualifications());
		if (siteSme != null) {
			smeQualifications.setSitesSme(siteSme);
		}
		if (qualification != null) {
			smeQualifications.setQualification(qualification);
		}
		return smeQualifications;
	}
	
	/**
	 * The creation / update of the smeQualification
	 * @param smeQualification
	 * @throws Exception
	 */
	public void createUpdateSmeQualifications(SmeQualifications smeQualification, Users sessionUser) throws Exception{
		String error = validationSmeQualification(smeQualification);
		if (error.length() > 0) throw new ValidationException(error);
		// creates / updates qualification
		create(smeQualification);
		// uploads new documents, new version immediately uploaded 
		for (Doc doc : smeQualification.getDocs()) {
			if (doc.getId() == null && doc.getData() != null) {
				doc.setTargetClass(smeQualification.getClass().getName());
				doc.setTargetKey(smeQualification.getId());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
			}
		}
	}

	/**
	 * Validates SmeQualifications before create/update
	 * @param smeQualification
	 * @return error
	 * @throws Exception
	 */
	public String validationSmeQualification(SmeQualifications smeQualification) throws Exception {
		String error = "";
		// SitesSme validation
		if (smeQualification.getSitesSme() == null) {
			error += " Provide Sme ";
		}
		// Qualification validation
		if (smeQualification.getQualification() == null) {
			error += " Provide Qualification ";
		}
		// document validation
		if (smeQualification.getDocs() != null && smeQualification.getQualification() != null) {
			for (Doc doc : smeQualification.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc != null && doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc() != null && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument() != null) {
					error += "Upload The Document For Qualification " + smeQualification.getQualification().getDescription() + " Before Proceeding";
				}
			}
		}
		return error;
	}
	
	public List<SmeQualReportBean> populateSmeQualReport() throws Exception {
		return dao.populateSmeQualReport();
	}
	
	public void downloadSmeQualReport() throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Company Name", "Trading Name", "Entity ID", "Site Name", "First Name", "Last Name","Identity/Passport Number", "Application Status", "SAQA ID", "Qualification" , "NQF Level" , "Qualification Status", "Approval/Rejection Date", "Reject Reasons"});
		counter++;
		// data population
		populateDataForSmeQualReport(data, counter);
		counter = null;
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);;
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			String fileName = "Mentor Qualifications List.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private void populateDataForSmeQualReport(Map<String, Object[]> data, Integer counter) throws Exception{
		int counterForPopulation = counter;
		List<SmeQualReportBean> resultsList = populateSmeQualReport();
		// populate data found into report
		for (SmeQualReportBean entry : resultsList) {
			populateDataSmeQualReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}

	private void populateDataSmeQualReport(Map<String, Object[]> data, SmeQualReportBean entry, Integer counter) throws Exception{
		String approvalDate = "";
		if (entry.getApprovalDate() != null) {
			approvalDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getApprovalDate());
		}
		// new Object[] { "Company Name", "Trading Name", "Entity ID", "Site Name", "First Name", "Last Name","Identity/Passport Number", "Application Status", "SAQA ID", "Qualification" , "NQF Level" , "Qualification Status", "Approval/Rejection Date", "Reject Reasons"}
		data.put(counter.toString(), new Object[] { entry.getCompanyName(), entry.getTradingName(), entry.getEntityId(), entry.getSiteName(), entry.getFirstName(), entry.getLastName(), entry.getIdentityPassportNum(), entry.getApprovalStatus(), entry.getSaqaId(), entry.getQualDescr(), entry.getNqfDesc(), entry.getSmeStatus(), approvalDate, entry.getRejectionReasons() });
		
	}
}
