package haj.com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import haj.com.bean.CompanyRegionReportBean;
import haj.com.bean.ExtensionRequestReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.ExtensionRequestDAO;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;


public class ExtensionRequestService extends AbstractService {
	/** The dao. */
	private ExtensionRequestDAO dao = new ExtensionRequestDAO();
	private ConfigDocService configDocService = new ConfigDocService();

	private static ExtensionRequestService extensionRequestService;

	public static synchronized ExtensionRequestService instance() {
		if (extensionRequestService == null) {
			extensionRequestService = new ExtensionRequestService();
		}
		return extensionRequestService;
	}

	/**
	 * Get all ExtensionRequest
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 * @return a list of {@link haj.com.entity.ExtensionRequest}
	 * @throws Exception
	 *             the exception
	 */
	public List<ExtensionRequest> allExtensionRequest() throws Exception {
		return dao.allExtensionRequest();
	}

	public List<ExtensionRequest> findByWSP(Wsp wsp) throws Exception {
		return dao.findByWSP(wsp.getId());
	}

	/**
	 * Get all ExtensionRequest by Company
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 * @return a list of {@link haj.com.entity.ExtensionRequest}
	 * @throws Exception
	 *             the exception
	 */
	public List<ExtensionRequest> findByCompany(Company company) throws Exception {
		return populateInformationList(dao.findByCompany(company.getId()));
	}

	public List<ExtensionRequest> findByCompanyNoWSP(Company company) throws Exception {
		return dao.findByCompanyNoWSP(company.getId());
	}
	
	public List<ExtensionRequest> findByCompanyNoWSPWithFinYear(Company company, Integer finYear) throws Exception {
		return dao.findByCompanyNoWSPWithFinYear(company.getId( ), finYear);
	}
	
	public List<ExtensionRequest> findByCompanyNoWSPAssigned(Company company) throws Exception {
		return dao.findByCompanyNoWSPAssigned(company.getId());
	}
	
	

	/**
	 * Create or update ExtensionRequest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ExtensionRequest
	 */
	public void create(ExtensionRequest entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void create(ExtensionRequest entity, Users users, boolean createWorkflow, Users user) throws Exception {
		for (Doc doc : entity.getDocs()) {
			if (doc.getId() != null) {
				DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
				if (docByte != null) {
					doc.setData(docByte.getData());
				}				
			}
			if (doc.getData() == null) throw new Exception("Please ensure all documents are uploaded");
		}
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);

		if (createWorkflow) {
			String desc = "Extension request has been submitted.";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.EXTENSION_REQUEST, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		}

		for (Doc doc : entity.getDocs()) {
			if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
		}
	}
	
	/**
	 * The Super Admin creation of extension requests
	 * 
	 * @param entity
	 * @param users
	 * @param createWorkflow
	 * @param user
	 * @throws Exception
	 */
	public void adminCreate(ExtensionRequest entity, boolean createWorkflow, Users user) throws Exception {
		for (Doc doc : entity.getDocs()) {
			if (doc.getId() != null) {
				DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
				if (docByte != null) {
					doc.setData(docByte.getData());
				}				
			}
			if (doc.getData() == null) throw new Exception("Please ensure all documents are uploaded");
		}
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovedUser(user);
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
		for (Doc doc : entity.getDocs()) {
			if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
		}
		
		
	}

	/**
	 * Update ExtensionRequest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ExtensionRequest
	 */
	public void update(ExtensionRequest entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ExtensionRequest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ExtensionRequest
	 */
	public void delete(ExtensionRequest entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.ExtensionRequest}
	 * @throws Exception
	 *             the exception
	 * @see ExtensionRequest
	 */
	public ExtensionRequest findByKey(long id) throws Exception {
		return prepareNewDocs(dao.findByKey(id), ConfigDocProcessEnum.EXTENSION_REQUEST, CompanyUserTypeEnum.Company);
	}

	public ExtensionRequest prepareNewDocs(ExtensionRequest extensionRequest, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		extensionRequest.setDocs(DocService.instance().searchByExtensionRequest(extensionRequest));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(extensionRequest, configDocProcess, companyUserType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				extensionRequest.getDocs().add(new Doc(a, extensionRequest));
			});
		}
		return extensionRequest;
	}

	public void prepareNewDocs(ExtensionRequest extensionRequest) throws Exception {
		extensionRequest.setDocs(new ArrayList<Doc>());
		List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.EXTENSION_REQUEST, CompanyUserTypeEnum.Company);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				extensionRequest.getDocs().add(new Doc(a, extensionRequest));
			});
		}
	}

	/**
	 * Find ExtensionRequest by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.ExtensionRequest}
	 * @throws Exception
	 *             the exception
	 * @see ExtensionRequest
	 */
	public List<ExtensionRequest> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ExtensionRequest
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.ExtensionRequest}
	 * @throws Exception
	 *             the exception
	 */
	public List<ExtensionRequest> allExtensionRequest(int first, int pageSize) throws Exception {
		return dao.allExtensionRequest(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ExtensionRequest for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the ExtensionRequest
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(ExtensionRequest.class);
	}

	/**
	 * Lazy load ExtensionRequest with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            ExtensionRequest class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.ExtensionRequest} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> allExtensionRequest(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users user) throws Exception {
		return setJsoup(dao.allExtensionRequest(first, pageSize, sortField, sortOrder, filters, user));
	}

	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> allExtensionRequest(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return setJsoup(dao.allExtensionRequest(first, pageSize, sortField, sortOrder, filters));
	}
	
	public List<ExtensionRequest> allExtensionRequestByWspFinYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer selectedYear) throws Exception {
		return setJsoup(dao.allExtensionRequestByWspFinYear(first, pageSize, sortField, sortOrder, filters, selectedYear));
	}
	
	public int countAllExtensionRequestByWspFinYear(Map<String, Object> filters) throws Exception {
		return dao.countAllExtensionRequestByWspFinYear(filters);
	}
	
	public List<ExtensionRequest> allExtensionRequestAwaitingWspAssignment(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return setJsoup(dao.allExtensionRequestAwaitingWspAssignment(first, pageSize, sortField, sortOrder, filters));
	}
	
	public int countAllExtensionRequestAwaitingWspAssignment(Map<String, Object> filters) throws Exception {
		return dao.countAllExtensionRequestAwaitingWspAssignment(filters);
	}

	
	private List<ExtensionRequest> setJsoup(List<ExtensionRequest> allExtensionRequest) {
		for(ExtensionRequest extensionRequest: allExtensionRequest) {
			if(extensionRequest.getReasonForExtension() != null && !extensionRequest.getReasonForExtension().equals("")) {
				extensionRequest.setReasonForExtensionJsoep((Jsoup.parse(extensionRequest.getReasonForExtension()).text()));
			}else {
				extensionRequest.setReasonForExtensionJsoep("N/A");
			}			
		}
		return allExtensionRequest;
	}

	/**
	 * Get count of ExtensionRequest for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            ExtensionRequest class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the ExtensionRequest entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Map<String, Object> filters, Users user) throws Exception {
		return dao.countHql(filters, user);
	}

	public void completeTask(ExtensionRequest bankingDetails, Users user, Tasks task) throws Exception {
		TasksService.instance().findNextInProcessAndCreateTask("Extension request was checked and submitted by the admin", user, bankingDetails.getId(), bankingDetails.getClass().getName(), true, task, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	public void approveTask(ExtensionRequest extensionRequest, Users user, Tasks task) throws Exception {
		extensionRequest.setStatus(ApprovalEnum.Approved);
		update(extensionRequest);
		TasksService.instance().findNextInProcessAndCreateTask("Extension request was approved", user, extensionRequest.getId(), extensionRequest.getClass().getName(), true, task, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	public void rejectTask(ExtensionRequest bankingDetails, Users user, Tasks task) throws Exception {
		TasksService.instance().findPreviousInProcessAndCreateTask("Extension request was rejected please review", user, bankingDetails.getId(), bankingDetails.getClass().getName(), true, task, MailDef.instance(MailEnum.SdfForApproval), null);
	}

	public List<ExtensionRequest> extensionRequestGranyedCheck(Long wspId) throws Exception {
		return (List<ExtensionRequest>) dao.extensionRequestGranyedCheck(wspId);
	}

	/**
	 * System generated extensiom request / update
	 * @param wsp
	 * @throws Exception
	 */
	public void locateAndUpdateExtnesionRequest(Wsp wsp) throws Exception{
		List<ExtensionRequest> erlist = dao.findByWSP(wsp.getId());
		ExtensionRequest er = null;
		if (erlist.size() == 0) {
			er = new ExtensionRequest();
			er.setCreateDate(new Date());
			er.setNewSubmissionDate(GenericUtility.addDaysToDateExcludeWeekends(new Date(), 5));
			er.setWsp(wsp);
			er.setCompany(wsp.getCompany());
			er.setReasonForExtension("System Generated");
			er.setStatus(ApprovalEnum.Approved);
			create(er);
		} else {
			er = erlist.get(0);
			er.setNewSubmissionDate(GenericUtility.addDaysToDateExcludeWeekends(new Date(), 5));
			update(er);
		}
	}
	
	
	/**
	 * Populates additional Information against the ExtensionRequest.
	 * @see ExtensionRequest
	 * @param extensionRequestList
	 * @return List<ExtensionRequest>
	 * @throws Exception
	 */
	public List<ExtensionRequest> populateInformationList(List<ExtensionRequest> extensionRequestList) throws Exception{
		for (ExtensionRequest extensionRequest : extensionRequestList) {
			populateInformation(extensionRequest);
		}
		return extensionRequestList;
	}

	/**
	 * Populates additional information against the ExtensionRequest.
	 * 
	 * Populates the user object
	 * 
	 * @see ExtensionRequest
	 * @param extensionRequest
	 * @return extensionRequest
	 * @throws Exception
	 */
	public ExtensionRequest populateInformation(ExtensionRequest extensionRequest) throws Exception{
		UsersService usersService = new UsersService();
		
		// Populates the user information against the extension request
		if (extensionRequest.getUser() != null) {
			extensionRequest.setUser(usersService.findByKey(extensionRequest.getUser().getId()));
		}
		
		// populates the user who approved extension request
		if (extensionRequest.getApprovedUser() != null) {
			extensionRequest.setApprovedUser(usersService.findByKey(extensionRequest.getApprovedUser().getId()));
		}
		
		// populates the documents against the service request 
		prepareNewDocs(extensionRequest,ConfigDocProcessEnum.EXTENSION_REQUEST, CompanyUserTypeEnum.Company);
		
		usersService = null;
		return extensionRequest;
	}
	
	public List<ExtensionRequestReportBean> populateExtensionRequestReport() throws Exception {
		return dao.populateExtensionRequestReport();
	}
	
	public void downloadExtensionRequestReport() throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Create Date", "Entity ID", "Company Name", "Grant Year", "Applicant Details", "Status Assigned", "New Submission Date", "Reason For Extension"});
		counter++;
		// data population
		populateDataForExtensionRequestReport(data, counter);
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
			String fileName = "Extension Request Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private void populateDataForExtensionRequestReport(Map<String, Object[]> data, Integer counter) throws Exception{
		int counterForPopulation = counter;
		List<ExtensionRequestReportBean> resultsList = populateExtensionRequestReport();
		// populate data found into report
		for (ExtensionRequestReportBean entry : resultsList) {
			populateDataRegionReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}

	private void populateDataRegionReport(Map<String, Object[]> data, ExtensionRequestReportBean entry, Integer counter) throws Exception{
		
		String grantYear = "";
		if (entry.getGrantYear() == null) {
			grantYear = "Awaiting Assignment";
		} else {
			grantYear = entry.getGrantYear().toString();
		}
		
		String rasonForExtensionParsed = "";
		if (entry.getReasonForExtensionHtml() != null && !entry.getReasonForExtensionHtml().isEmpty()) {
			rasonForExtensionParsed = (Jsoup.parse(entry.getReasonForExtensionHtml()).text());
		}
		
		String createDate = "";
		if (entry.getCreateDate() != null) {
			createDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getCreateDate());
		}
		
		String newSubDate = "";
		if (entry.getNewSubmissionDate() != null) {
			newSubDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getNewSubmissionDate());
		}

		// new Object[] { "Create Date", "Entity ID", "Company Name", "Grant Year", "Applicant Details", "Status Assigned", "New Submission Date", "Reason For Extension"});
		data.put(counter.toString(), new Object[] { createDate, entry.getEntityId(), entry.getCompanyName(), grantYear, entry.getApplicantDetails(), entry.getStatusAssigned(), newSubDate, rasonForExtensionParsed });
		
	}
}
