package haj.com.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.CoursewareDistibutionDAO;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.CoursewareDistibution;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.lookup.Modules;
import haj.com.entity.lookup.ModulesUnitStandards;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

public class CoursewareDistibutionService extends AbstractService {
	/** The dao. */
	private CoursewareDistibutionDAO dao = new CoursewareDistibutionDAO();
	CompanyUsersService companyUsersService = new CompanyUsersService();
	/**
	 * Get all CoursewareDistibution
	 * 
	 * @author TechFinium
	 * @see CoursewareDistibution
	 * @return a list of {@link haj.com.entity.CoursewareDistibution}
	 * @throws Exception
	 *             the exception
	 */
	public CoursewareDistibution findByCompanyAndModule(Company selectedCompany, Modules selectedModule	) throws Exception	{
		if(selectedCompany != null && selectedModule != null )
		{
			if(selectedCompany.getId() != null && selectedModule.getId() != null )
			{
				return dao.findByCompanyAndModule(selectedCompany.getId(), selectedModule.getId());	
			}
			else
			{
				return new CoursewareDistibution();
			}
		}
		else
		{
			return new CoursewareDistibution();
		}
	}
	
	public List<CoursewareDistibution> allCoursewareDistibution() throws Exception {
		return dao.allCoursewareDistibution();
	}

	public List<CoursewareDistibution> allCompany(Company company) throws Exception {
		return dao.allCompany(company);
	}

	public List<CoursewareDistibution> allCoursewareByUser(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users user, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return dao.allCoursewareByUser(class1, first, pageSize, sortField, sortOrder, filters, user, configDocProcessEnum);
	}

	public int countAllCoursewareByUser(Map<String, Object> filters) throws Exception {
		return dao.countAllCoursewareByUser(filters);
	}

	public List<CoursewareDistibution> allCompany(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		return dao.allCompany(class1, first, pageSize, sortField, sortOrder, filters, company);
	}
	
	public List<CoursewareDistibution> allCompanyByStatus(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company, ApprovalEnum status) throws Exception {
	
		List<CoursewareDistibution> list = dao.allCompanyByStatus(class1, first, pageSize, sortField, sortOrder, filters, company, status);
		List<CoursewareDistibution> newlist = new ArrayList<>();
		for(CoursewareDistibution coursewareDistibution: list)
		{
			List<RejectReasons>  regList = locateReasonsSelectedByTargetKeyClassAndProcess(coursewareDistibution);
			
			coursewareDistibution.setRejectReasons(regList);
			coursewareDistibution.setRejectReasonExport(coursewareDistibutionRejectExport(regList));
			//coursewareDistibution.setUnitStandartExport(coursewareDistibutionUnitSandartExport(coursewareDistibution.getModules().getModulesUnitStandards()));
			newlist.add(coursewareDistibution);
		}
		return newlist;
	}  
	
	public List<CoursewareDistibution> allCoursewareDistibutionForReport(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return dao.allCoursewareDistibutionForReport(class1, first, pageSize, sortField, sortOrder, filters);
	}  
	
	public List<CoursewareDistibution> allCoursewareDistibutionAprvedNotApproved(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		return dao.allCoursewareDistibutionAprvedNotApproved(class1, first, pageSize, sortField, sortOrder, filters, company);
	} 
	
	public String coursewareDistibutionRejectExport(List<RejectReasons>  regList)
	{
		String ex = "";
		for(RejectReasons rejectReasons: regList)
		{
			ex+=rejectReasons.getDescription();
		}
		return ex;
	}
	
	public String coursewareDistibutionUnitSandartExport(List<ModulesUnitStandards>  modulesUnitStandards)
	{
		String ex = "";
		for(ModulesUnitStandards modulesUnitStandard: modulesUnitStandards)
		{
			ex+=modulesUnitStandard.getCode();
		}
		return ex;
	}
	
	public int countCompanyReject(Map<String, Object> filters) throws Exception {
		return dao.countCompany(filters);
	}
	public int countAll(Map<String, Object> filters) throws Exception {
		return dao.countAll(filters);
	}
	
	
	public int countCompany(Map<String, Object> filters) throws Exception {
		return dao.countCompany(filters);
	}
	
	public List<CoursewareDistibution> allCompanyDashboard(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		return dao.allCompanyDashboard(class1, first, pageSize, sortField, sortOrder, filters, company);
	}
	
	public int countCompanyDashboard(Map<String, Object> filters) throws Exception {
		return dao.countCompanyDashboard(filters);
	}

	/**
	 * Create or update CoursewareDistibution.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CoursewareDistibution
	 */
	public void create(CoursewareDistibution entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void requestCourseWare(CoursewareDistibution coursewaredistibution, Company selectedCompany, Modules selectedModule, Users user, boolean sendMail) throws Exception {
		coursewaredistibution.setCompany(selectedCompany);
		coursewaredistibution.setModules(selectedModule);
		coursewaredistibution.setUser(user);
		coursewaredistibution.setApprovalEnum(ApprovalEnum.PendingApproval);
		coursewaredistibution.setApprovalDate(null);
		create(coursewaredistibution);		
		TasksService.instance().findFirstInProcessAndCreateTask("", user, coursewaredistibution.getId(), CoursewareDistibution.class.getName(), true, ConfigDocProcessEnum.COURSEWARE_DISTRIBUTION, null, null);
		if (sendMail) sendAcknoledgementEmail(user, coursewaredistibution, selectedCompany);
	}

	public void sendAcknoledgementEmail(Users u, CoursewareDistibution coursewaredistibution, Company selectedCompany) {
		String accreditationNumber = "N/A";
		if(selectedCompany.getAccreditationNumber()!=null)
		{
			accreditationNumber = selectedCompany.getAccreditationNumber();
		}
		
		String welcome = "<p>Dear #FirstName# #Surname#,</p>" 
						+ "<p>We acknowledge receipt of your Courseware Application for #CompanyName# (#EntityID#) submitted on #SubmissionDate#.</p>" 
						+ "<p>The application will be evaluated by the merSETA and should any additional information be required, this will be communicated to you.</p>"
						+ "<p>Please note that it may take up to 5 working days to process the application.</p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>merSETA Curriculum and Learning Programmes Unit</p>" 
						+ "<br/>";
		
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#CompanyName#", selectedCompany.getCompanyName());		
		welcome = welcome.replaceAll("#AccreditationNumber#",accreditationNumber);
		welcome = welcome.replaceAll("#EntityID#",selectedCompany.getLevyNumber());
		welcome = welcome.replaceAll("#SubmissionDate#",GenericUtility.sdf7.format(coursewaredistibution.getCreateDate()));
				
		GenericUtility.sendMail(u.getEmail(), "merSETA COURSEWARE APPLICATION", welcome, null);
	}
	
	public void sendApprovalEmail(CoursewareDistibution coursewareDistibution) {
		String accreditationNumber = "N/A";
		if(coursewareDistibution.getCompany().getAccreditationNumber()!=null)
		{
			accreditationNumber = coursewareDistibution.getCompany().getAccreditationNumber();
		}
		
		String welcome = "<p>Dear #FirstName# #Surname#,</p>" 
						+ "<p>The Courseware Application for #CompanyName# (#EntityID#) has been approved.</p>" 						
						+ "<p>The Courseware can be accessed under available Courseware.</p>"
						
						+ "<p>Please note that by applying for Courseware and "
						+ "being granted approval, you hereby acknowledge and "
						+ "accept the conditions of use for the available "
						+ "Courseware which are that:</p>"
						
						
						+"<ol>"
						+ "<li>The Courseware may not be re-produced for re-sale or for any commercial gain.</li>"
						+ "<li>The Courseware only be used for registered learners of the applying accredited provider on a SAQA registered qualification.</li>"
						+ "<li>All copyright on the Courseware remains with the merSETA.</li>"
						+ "<li> In the event of the provider being de-accredited for any reason whatsoever, the Courseware may not be used any longer and will no longer be available on the provider profile.</li>"
						+ "<li>The Courseware may only be altered for purposes of contextualising to suit the specific industry and learner needs.</li>"
						+ "<li>Although all possible procedures with subject matter experts from the Manufacturing and Engineering and Related Services Industry were followed, merSETA cannot guarantee the competency of the learner purely by using this courseware.</li>"
						+"</ol>"
						
						
						+ "<p>Yours sincerely,</p>" 
						+ "<p>merSETA Curriculum and Learning Programmes Unit</p>" 
						+ "<br/>";
						welcome = welcome.replaceAll("#FirstName#", coursewareDistibution.getUser().getFirstName());
						welcome = welcome.replaceAll("#Surname#", coursewareDistibution.getUser().getLastName());
						welcome = welcome.replaceAll("#CompanyName#", coursewareDistibution.getCompany().getCompanyName());		
						welcome = welcome.replaceAll("#AccreditationNumber#",accreditationNumber);
						welcome = welcome.replaceAll("#EntityID#",coursewareDistibution.getCompany().getLevyNumber());

		GenericUtility.sendMail(coursewareDistibution.getUser().getEmail(), "merSETA COURSEWARE APPLICATION", welcome, null);
	}
	
	public void sendRejectEmail(CoursewareDistibution coursewareDistibution) {
		String accreditationNumber = "N/A";
		if(coursewareDistibution.getCompany().getAccreditationNumber()!=null)
		{
			accreditationNumber = coursewareDistibution.getCompany().getAccreditationNumber();
		}
		String welcome = "<p>Dear #FirstName# #Surname#,</p>" 
						+ "<p>The Courseware Application for #CompanyName# (#EntityID#) has not been approved.</p>" 						
						+ "<p>Please be advised that you may apply for the Courseware again and may go to Available Courseware for Application and submitting a request.</p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>merSETA Curriculum and Learning Programmes Unit</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#FirstName#", coursewareDistibution.getUser().getFirstName());
		welcome = welcome.replaceAll("#Surname#", coursewareDistibution.getUser().getLastName());
		welcome = welcome.replaceAll("#CompanyName#", coursewareDistibution.getCompany().getCompanyName());		
		welcome = welcome.replaceAll("#AccreditationNumber#",accreditationNumber);
		welcome = welcome.replaceAll("#EntityID#",coursewareDistibution.getCompany().getLevyNumber());
		GenericUtility.sendMail(coursewareDistibution.getUser().getEmail(), "merSETA COURSEWARE APPLICATION", welcome, null);
	}

	public void finalApproveRegistration(CoursewareDistibution registration, Users user, Tasks tasks) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Approved);
		registration.setApprovalDate(getSynchronizedDate());
		create(registration);
		sendApprovalEmail(registration);
		TasksService.instance().completeTask(tasks);
//		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, tasks, null, null);
	}

	public void finalRejectRegistration(CoursewareDistibution registration, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		registration.setApprovalDate(getSynchronizedDate());
		this.dao.update(registration);
		
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(registration.getId(), CoursewareDistibution.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.COURSEWARE_DISTRIBUTION);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		
		sendRejectEmail(registration);
		TasksService.instance().completeTask(tasks);
	}
	
	public List<RejectReasons> locateReasonsSelectedByTargetKeyClassAndProcess(CoursewareDistibution coursewareDistibution) throws Exception
	{
		 RejectReasonsService rejectReasonsService = new RejectReasonsService();
		 
		 return rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(coursewareDistibution.getId(), coursewareDistibution.getClass().getName(),
				 ConfigDocProcessEnum.COURSEWARE_DISTRIBUTION);
	}
	
	/**
	 * Update CoursewareDistibution.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CoursewareDistibution
	 */
	public void update(CoursewareDistibution entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CoursewareDistibution.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CoursewareDistibution
	 */
	public void delete(CoursewareDistibution entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CoursewareDistibution}
	 * @throws Exception
	 *             the exception
	 * @see CoursewareDistibution
	 */
	public CoursewareDistibution findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find CoursewareDistibution by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CoursewareDistibution}
	 * @throws Exception
	 *             the exception
	 * @see CoursewareDistibution
	 */
	public List<CoursewareDistibution> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load CoursewareDistibution
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CoursewareDistibution}
	 * @throws Exception
	 *             the exception
	 */
	public List<CoursewareDistibution> allCoursewareDistibution(int first, int pageSize) throws Exception {
		return dao.allCoursewareDistibution(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CoursewareDistibution for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CoursewareDistibution
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CoursewareDistibution.class);
	}
	

	/**
	 * Lazy load CoursewareDistibution with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CoursewareDistibution class
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
	 * @return a list of {@link haj.com.entity.CoursewareDistibution} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCoursewareDistibution(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CoursewareDistibution>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}
	
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCoursewareDistibutionByStatus(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CoursewareDistibution>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of CoursewareDistibution for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CoursewareDistibution class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CoursewareDistibution entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CoursewareDistibution> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	/** Reporting Start */
	
	/**
	 * Populates list of status used on CoursewareDistibution life cycle
	 * @return
	 * @throws Exception
	 */
	public List<ApprovalEnum> getStatusUsed() throws Exception{
		List<ApprovalEnum> statusUsed = new ArrayList<>();
		statusUsed.add(ApprovalEnum.Approved);
		statusUsed.add(ApprovalEnum.Rejected);
		statusUsed.add(ApprovalEnum.PendingApproval);
		return statusUsed;
	}
	
	/**
	 * Check on the database if there are any results form the selected criteria provided
	 * @param dateFilter
	 * @param fromDate
	 * @param toDate
	 * @param filterByStatus
	 * @param statusSelectedList
	 * @return int the count of results
	 * @throws Exception
	 */
	public int checkForResultsForFilterReport(boolean dateFilter, Date fromDate, Date toDate, boolean filterByStatus, List<ApprovalEnum> statusSelectedList) throws Exception {
		return dao.checkForResultsForFilterReport(dateFilter, fromDate, toDate, filterByStatus, statusSelectedList);
	}
	
	public String validateParamaters(boolean dateFilter, Date fromDate, Date toDate, boolean filterByStatus, List<ApprovalEnum> statusSelectedList) throws Exception{
		String error = "";
		// date filter check
		if (dateFilter) {
			if (fromDate == null) {
				error += " Provide From Date";
			}
			if (toDate == null) {
				if (error == "") {
					error += " Provide From To Date";
				}else {
					error += ", Provide From Date";
				}	
			}
		}
		if (filterByStatus) {
			if (statusSelectedList == null || statusSelectedList.size() == 0) {
				if (error == "") {
					error += " Provide Atleast One Filter Status";
				}else {
					error += ", Provide Atleast One Filter Status";
				}	
			}
		}
		if (error == "") {
			if (checkForResultsForFilterReport(dateFilter, fromDate, toDate, filterByStatus, statusSelectedList) == 0) {
				if (error == "") {
					error += " No Results Found: Select Differenet Filter Criteria";
				}else {
					error += ", No Results Found: Select Differenet Filter Criteria";
				}
			}
		}
		return error;
	}
	
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> coursewareDistibutionReportByFilters(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,
			boolean dateFilter, Date fromDate, Date toDate, boolean filterByStatus, List<ApprovalEnum> statusSelectedList) throws Exception {
		String hql = "select o from CoursewareDistibution o ";
		if (dateFilter) {
			hql += " where date(o.createDate) between date(:fromDate) and date(:toDate) ";
			filters.put("fromDate", fromDate);
			filters.put("toDate", toDate);
		}
		if (filterByStatus) {
			if (dateFilter) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			for (ApprovalEnum approvalEnum : statusSelectedList) {
				if (count == statusSelectedList.size()) {
					hql += " o.approvalEnum = :selectedStatus" + count + " ";
				} else {
					hql += " o.approvalEnum = :selectedStatus" + count + " or ";
				}
				filters.put("selectedStatus" + count, approvalEnum);
				count++;
			}
			hql += " ) ";
		}
		return (List<CoursewareDistibution>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countCoursewareDistibutionReportByFilters(Map<String, Object> filters,
			boolean dateFilter, Date fromDate, Date toDate, boolean filterByStatus, List<ApprovalEnum> statusSelectedList) throws Exception {
		String hql = "select count(o) from CoursewareDistibution o ";
		if (dateFilter) {
			hql += " where date(o.createDate) between date(:fromDate) and date(:toDate) ";
		}
		if (filterByStatus) {
			if (dateFilter) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			for (ApprovalEnum approvalEnum : statusSelectedList) {
				if (count == statusSelectedList.size()) {
					hql += " o.approvalEnum = :selectedStatus" + count + " ";
				} else {
					hql += " o.approvalEnum = :selectedStatus" + count + " or ";
				}
				count++;
			}
			hql += " ) ";
		}
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Modules> mostRequestedCoursewareDistibution(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return sortTotalModuleRequest(setTotalModuleRequest((List<Modules>) dao.coursewareDistibutionModules(Modules.class, first, pageSize, sortField, sortOrder, filters)));
	}

	@SuppressWarnings("unchecked")
	public List<Company> coursewareDistibutionReportByCompany(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return sortTotalCompanyRequest(setCompanyTotalRequest( dao.coursewareDistibutionReportByCompany(CoursewareDistibution.class, first, pageSize, sortField, sortOrder, filters)));
	}
	
	public int countCoursewareDistibutionReportByCompany(Map<String, Object> filters) throws Exception {
		return dao.countCoursewareByCompany(filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> companyCoursewareDistibution(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return dao.allCompanyCourseware(CoursewareDistibution.class, first, pageSize, sortField, sortOrder, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> coursewareDistibutionbyYearAndMonth(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters, String searchType) throws Exception {
		return dao.coursewareDistibutionbyYearAndMonth(CoursewareDistibution.class, first, pageSize, sortField, sortOrder, filters,searchType);
	}
	
	public int countCoursewareDistibutionbyYearAndMonth(Map<String, Object> filters,String searchType) throws Exception {
		return dao.countCoursewareDistibutionbyYearAndMonth(filters,searchType);
	}
	
	
	public int mostRequestedCoursewareCount(Map<String, Object> filters) throws Exception {
		return dao.countCoursewareModules(filters);
	}
	
	public int countCompanyCoursewareDistibution(Map<String, Object> filters) throws Exception {
		return dao.countAllCompanyCourseware(filters);
	}
	
	public  List<CoursewareDistibution> settotalRequest( List<CoursewareDistibution> list)
	{
		for(CoursewareDistibution cw:list)
		{
			 Map<String, Object> filters =new Hashtable<>();
			filters.put("company",cw);
			String hql="select count(o) from CoursewareDistibution o where o.company = :company ";
			int count= dao.countWhere(filters, hql);
			cw.setTotalRequest(count);
		}
		return list;
	}
	
	public  List<Company> setCompanyTotalRequest(List<Company> list) throws Exception
	{
		for(Company cw:list)
		{
			 Map<String, Object> filters =new Hashtable<>();
			filters.put("company",cw);
			int count= dao.countCompanyWhere(filters);
			cw.setTotalRequest(count);
		}
		return list;
	}
	
	public  List<Modules> setTotalModuleRequest( List<Modules> list)
	{
		for(Modules cw:list)
		{
			 Map<String, Object> filters =new Hashtable<>();
			filters.put("modules", cw);
			int count= dao.countModulesWhere(filters);
			cw.setTotalRequest(count);
		}
		return list;
	}
	
	public List<Company> sortTotalCompanyRequest(List<Company> list) throws Exception
	{
		  Collections.sort(list, new Comparator<Company>() {

		        public int compare(Company o1, Company o2) {
		            return o2.getTotalRequest().compareTo(o1.getTotalRequest());
		        }
		    });
		 return list;
	}
	
	public List<Modules> sortTotalModuleRequest(List<Modules> list)
	{
		  Collections.sort(list, new Comparator<Modules>() {

		        public int compare(Modules o1, Modules o2) {
		            return o2.getTotalRequest().compareTo(o1.getTotalRequest());
		        }
		    });
		 return list;
	}
	

	
	/** Reporting End */
}
