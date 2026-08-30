package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.BugReportDAO;
import haj.com.entity.BugReport;
import haj.com.entity.Company;
import haj.com.entity.MailLog;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.BugReportType;
import haj.com.entity.lookup.RegionTown;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RegionTownService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class BugReportService.
 */
public class BugReportService extends AbstractService {

	/** The dao. */
	private BugReportDAO dao = new BugReportDAO();

	/**
	 * Get all BugReport.
	 *
	 * @author TechFinium
	 * @return List<BugReport>
	 * @throws Exception the exception
	 * @see    BugReport
	 */
	public List<BugReport> allBugReport() throws Exception {
	  	return dao.allBugReport();
	}

	/**
	 * Create or update  BugReport.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    BugReport
	 */
	public void create(BugReport entity) throws Exception  {
	   if (entity.getId() ==null) {
		  entity.setCreateDate(new Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}

	/**
	 * Update  BugReport.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    BugReport
	 */
	public void update(BugReport entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  BugReport.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    BugReport
	 */
	public void delete(BugReport entity) throws Exception  {
		this.dao.delete(entity);
	}
	
	
	/**
	 * Submit bug.
	 *
	 * @param bugReport the bug report
	 * @throws Exception the exception
	 */
	public void submitBug(BugReport bugReport) throws Exception {
		bugReport.setReportType(BugReportType.ExternalParty);
		create(bugReport);

		if (bugReport.getBugImage() != ImagesService.findByBugReport(bugReport)) {
			bugReport.getBugImage().setBugReport(bugReport);
			ImagesService.create(bugReport.getBugImage());
		}

		String subject = "User Support Request submitted by: " + bugReport.getUser().getFirstName() + " "
				+ bugReport.getUser().getLastName();

		String text = "<p><b><u>Title:</u></b> " 
					+ bugReport.getBugTitle() + "</p>"
					+ "<p><b><u>User:</u></b> " 
					+ bugReport.getUser().getFirstName() + " "
					+ bugReport.getUser().getLastName() 
					+ " (Email: "+ bugReport.getUser().getEmail() + ")</p>";
		
		text += "<p><b><u>Content of User Support Request:</u></b>  </p>";
		text += bugReport.getBugReport();
		text += "<br/><br/>";

		List<String> receivers = new ArrayList<>();
		receivers.addAll(GenericUtility.processEmailNotfications());
		receivers.add("customerservice@merseta.org.za");
		
		for (String email : receivers) {
			MailLog mailLog = new MailLog(email, subject, text);
			mailLog.setBugReport(bugReport);
			MailLogService.create(mailLog);
			
			if (bugReport.getBugImage() != null) GenericUtility.sendMailWithAttachement(email, subject, text, bugReport.getBugImage().getSecurityPic(), "bugReportImage" + bugReport.getId(), bugReport.getBugImage().getNewFname(), bugReport.getBugImage().getContentType(), mailLog);
			else GenericUtility.sendMail(email, subject, text,null);
		}
		
	}
	
	public void submitExternalBug(BugReport bugReport) throws Exception {
		bugReport.setReportType(BugReportType.ExternalParty);
		create(bugReport);

		if (bugReport.getBugImage() != ImagesService.findByBugReport(bugReport)) {
			bugReport.getBugImage().setBugReport(bugReport);
			ImagesService.create(bugReport.getBugImage());
		}

		String subject = "User Support Request submitted by: " + bugReport.getUser().getFirstName() + " "
				+ bugReport.getUser().getLastName();

		String text = "<p><b><u>Title:</u></b> " 
					+ bugReport.getBugTitle() + "</p>"
					+ "<p><b><u>User:</u></b> " 
					+ bugReport.getUser().getFirstName() + " "
					+ bugReport.getUser().getLastName() 
					+ " (Email: "+ bugReport.getUser().getEmail() + ")</p>";
		
		text += "<p><b><u>Content of User Support Request:</u></b>  </p>";
		text += bugReport.getBugReport();
		text += "<br/><br/>";

		List<String> receivers = new ArrayList<>();
		
		
		if(bugReport.getCompanyUsers() !=null && bugReport.getCompanyUsers().getCompany() != null){
			Users clo = getCLO(bugReport.getCompanyUsers().getCompany());
			if(clo != null && clo.getId() != null) {
				receivers.add(clo.getEmail());
			}
			
			Users crm = getCRM(bugReport.getCompanyUsers().getCompany());
			if(crm != null && crm.getId() != null) {
				receivers.add(crm.getEmail());
			}
		}
		
		//receivers.add("MISSupport@merseta.org.za");
		
		//receivers.add("customerservice@merseta.org.za");
		
		for (String email : receivers) {
			MailLog mailLog = new MailLog(email, subject, text);
			mailLog.setBugReport(bugReport);
			MailLogService.create(mailLog);
			
			if (bugReport.getBugImage() != null) {
				GenericUtility.sendMailWithAttachement(email, subject, text, bugReport.getBugImage().getSecurityPic(), "bugReportImage" + bugReport.getId(), bugReport.getBugImage().getNewFname(), bugReport.getBugImage().getContentType(), mailLog);
			}
			else GenericUtility.sendMail(email, subject, text,null);
		}
		
	}
	
	public void submitSupport(BugReport bugReport) throws Exception {
		bugReport.setReportType(BugReportType.Support);
		create(bugReport);

		if (bugReport.getBugImage() != ImagesService.findByBugReport(bugReport)) {
			bugReport.getBugImage().setBugReport(bugReport);
			ImagesService.create(bugReport.getBugImage());
		}

		String subject = "System Suppport Request submitted by: " + bugReport.getUser().getFirstName() + " " + bugReport.getUser().getLastName();

		String text = "<p><b><u>Title:</u></b> " 
					+ bugReport.getBugTitle() + "</p>"
					+ "<p><b><u>User:</u></b> " 
					+ bugReport.getUser().getFirstName() 
					+ " "
					+ bugReport.getUser().getLastName() 
					+ " (Email: "+ bugReport.getUser().getEmail() + ")</p>";
		
		text += "<p><b><u>Content of System Suppport Request:</u></b>  </p>";
		text += bugReport.getBugReport();
		text += "<br/><br/>";

		List<String> receivers = new ArrayList<>();
		receivers.addAll(GenericUtility.processEmailNotfications());
		
		for (String email : receivers) {
			MailLog mailLog = new MailLog(email, subject, text);
			mailLog.setBugReport(bugReport);
			MailLogService.create(mailLog);
			
			if (bugReport.getBugImage() != null) GenericUtility.sendMailWithAttachement(email, subject, text, bugReport.getBugImage().getSecurityPic(), "bugReportImage" + bugReport.getId(), bugReport.getBugImage().getNewFname(), bugReport.getBugImage().getContentType(), mailLog);
			else GenericUtility.sendMail(email, subject, text,null);
		}
		
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a BugReport object
	 * @throws Exception the exception
	 * @see    BugReport
	 */
	public BugReport findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find BugReport by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return List<BugReport>
	 * @throws Exception the exception
	 * @see    BugReport
	 */
	public List<BugReport> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load BugReport.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return List<BugReport>
	 * @throws Exception the exception
	 */
	public List<BugReport> allBugReport(int first, int pageSize) throws Exception {
		return dao.allBugReport(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the BugReport
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(BugReport.class);
	}
	
    /**
     * All bug report.
     *
     * @author TechFinium
     * @param class1 BugReport class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return List<BugReport> containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<BugReport> allBugReport(Class<BugReport> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<BugReport>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	


	/**
     * Count.
     *
     * @param entity BugReport class
     * @param filters the filters
     * @return Number of rows in the BugReport entity
     * @throws Exception the exception
     */	
	public int count(Class<BugReport> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public Users getCLO(Company company) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		Users cloUser = rt.getClo().getUsers();
		return cloUser;
	}
	
	public Users getCRM(Company company) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		Users crmUser = rt.getCrm().getUsers();
		return crmUser;
	}
}
