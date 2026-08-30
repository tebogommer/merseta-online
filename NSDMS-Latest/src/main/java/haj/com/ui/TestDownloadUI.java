package haj.com.ui;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringEscapeUtils;

import haj.com.bean.FormSectionQuestionsBean;
import haj.com.bean.MillwrightToolsListBean;
import haj.com.bean.SiteVisitFacilitatorAssessorBean;
import haj.com.bean.SiteVisitQualUnitStandardsBean;
import haj.com.bean.SiteVisitUserBean;
import haj.com.bean.SkillsProgrammeSkillsSetBean;
import haj.com.bean.UnitStandardBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyTradeTestEmployer;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.DgAllocation;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.Learners;
import haj.com.entity.MgVerification;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.SkillsRegistration;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.Wsp;
import haj.com.entity.enums.AnswerTypeEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.formconfig.FormSectionQuestions;
import haj.com.entity.formconfig.FormType;
import haj.com.entity.formconfig.FormTypeAnswers;
import haj.com.entity.formconfig.FormTypeSections;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Title;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.BankingDetailsService;
import haj.com.service.CompanyLearnersLostTimeService;
import haj.com.service.CompanyLearnersOtpSignoffService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyLearnersTradeTestService;
import haj.com.service.CompanyQualificationsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyTradeTestEmployerService;
import haj.com.service.CompanyUnitStandardService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DgAllocationParentService;
import haj.com.service.DgAllocationService;
import haj.com.service.FormTypeAnswersService;
import haj.com.service.FormTypeSectionsService;
import haj.com.service.FormTypeService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.JasperService;
import haj.com.service.LearnersService;
import haj.com.service.MgVerificationService;
import haj.com.service.MunicipalityService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SkillsRegistrationService;
import haj.com.service.SummativeAssessmentReportService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderVerficationService;
import haj.com.service.UsersDisabilityService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.WspService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.TitleService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
@ManagedBean(name = "testDownloadUI")
@ViewScoped
public class TestDownloadUI  extends AbstractUI{
	private String lNumber;
	private Integer finYear;
	UsersService userService=new UsersService();
	RejectReasonsService reasonsService=new RejectReasonsService();
	private CompanyLearnersLostTimeService companyLearnersLostTimeService =null;
	
	public void sendETQTP004AccreditationSiteVisitReport() throws Exception {
		
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		TrainingProviderApplication trainingProviderApplication=trainingProviderApplicationService.findByKey(3L);
		Map<String, Object> params = new HashMap<String, Object>();
		Users user=trainingProviderApplication.getUsers();
		List<SiteVisitUserBean> merSETARepList=new ArrayList<>();//To be populated
		List<SiteVisitUserBean> companyRepList=new ArrayList<>();//To be populated
		List<SiteVisitQualUnitStandardsBean> qualList=new ArrayList<>();//To be populated
		List<SiteVisitQualUnitStandardsBean> usList=new ArrayList<>();//To be populated
		List<SiteVisitQualUnitStandardsBean> approvedQualList=new ArrayList<>();//To be populated
		List<SiteVisitQualUnitStandardsBean> approvedUSList=new ArrayList<>();//To be populated
		List<SiteVisitFacilitatorAssessorBean> assessorList=new ArrayList<>();//To be populated
		List<SiteVisitFacilitatorAssessorBean> facilitatorList=new ArrayList<>();//To be populated
		
		String comment="User Comments";
		String programDirectorFullName="";
		boolean assessmentOnly=false;
		if(trainingProviderApplication.getAssessmentOnly()){
			assessmentOnly=true;
		}
		
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		params.put("company_id", trainingProviderApplication.getCompany().getId());
		params.put("self_evaluation_review_date",HAJConstants.sdfDDMMYYYY2.format(new Date()));//To be fixed
		params.put("provider_name",trainingProviderApplication.getCompany().getCompanyName());
		params.put("merSETARepDataSource",new JRBeanCollectionDataSource(merSETARepList));
		params.put("companyRepDataSource",new JRBeanCollectionDataSource(companyRepList));
		params.put("qualDataSource",new JRBeanCollectionDataSource(qualList));
		params.put("usDataSource",new JRBeanCollectionDataSource(usList));
		params.put("approvedQualDataSouce",new JRBeanCollectionDataSource(approvedQualList));
		params.put("approvedUSDataset",new JRBeanCollectionDataSource(approvedUSList));
		params.put("assessorDataSource",new JRBeanCollectionDataSource(assessorList));
		params.put("facilitatorDataSource",new JRBeanCollectionDataSource(facilitatorList));
		params.put("siteVisitDate",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
		params.put("reportDate",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitReportDate()));
		params.put("comment",comment);
		params.put("AssessmentOnly",assessmentOnly);
		params.put("programDirectorFullName",programDirectorFullName);
		JasperService jasperService=new JasperService();
		jasperService.createReportFromDBtoServletOutputStream("ETQ-TP-004-AccreditationSiteVisitReport.jasper", params,"Doc.pdf");
		/*byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-004-AccreditationSiteVisitReport.jasper", params);
		
		String subject = "ACCREDITATION SITE VISIT REPORT";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please find the attached accreditation site visit report."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>Manager: Quality Assurance</b>"
				+ "</p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "ETQ-TP-004-AccreditationSiteVisitReport.pdf", "pdf", null);*/

	}
	
	
	
	

	///************************************************************************************************************************************
	
	
	public void generateImpementationPlan()
	{
		try {
			CompanyService companyService=new CompanyService();
			WspService wspSevice=new WspService();
			Company comp=companyService.searchBySDL(lNumber);
			ProjectImplementationPlanService projectImplementationPlanService=new ProjectImplementationPlanService();
			if(comp !=null)
			{
				List<Wsp> wspList=wspSevice.findByCompanyAndFinancialYear(comp.getId(), finYear);
				if(wspList !=null && wspList.size() >0)
				{
					List<ProjectImplementationPlan> pipList=projectImplementationPlanService.findByWsp(wspList.get(0));
					//Deleting old PIP
					for(ProjectImplementationPlan pip:pipList)
					{
						//projectImplementationPlanService.delete(pip);
					}
//					projectImplementationPlanService.generateImpementationPlan(wspList.get(0));
				}
				else
				{
					throw new Exception("No WSP data found");
				}
				
			}
			else
			{
				throw new Exception("Company does not exist");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public void dgApplicationSuccessful(Users user,Company company,String reason,Users clo,String cloRegion,String grantyear, String appealSubmissionDate) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		String cloFullName=clo.getFirstName()+" "+clo.getLastName();
		String cloEmail=clo.getEmail();
		String region=cloRegion;
		
		params.put("company_id", company.getId());
		params.put("user_id",user.getId());
		params.put("crmUser",cloFullName);
		params.put("cloEmail",cloEmail);
		params.put("region",region);
		
		byte[] bites = JasperService.instance().convertJasperReportToByte("DG_Application_Successful.jasper", params);
		
		String subject = "DISCRETIONARY GRANT APPLICATION OUTCOME FOR: "+company.getCompanyName()+" ("+company.getLevyNumber()+") APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Thank you for submitting a discretionary grant application for "+grantyear+"."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We are pleased to inform you that the application was "
				+ "<b>successful</b>. Please may you complete the project implementation "
				+ "plan on the NSDMS and submit together with the signed Memorandum "
				+ "of Agreement (MOA) within <b>30 business days</b> from the date of receipt."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please note that should the enclosed MOA is not uploaded "
				+ "on the NSDMS within 30 business days from the date of issue, "
				+ "the merSETA may withdraw the MOA."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "For further assistance/support, kindly contact the designated "
				+ "Client Liaison Officer: "+cloFullName+" (Email: "+cloEmail+") or the respective "+region+" Office."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Programme Implementation Unit</b>"
				+ "</p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "DG_Application_Successful.pdf", "pdf", null);

	}
	
	
	
	
	public void dgApplicationWithdrawal(Users user,Company company,String reason,Users clo,String cloRegion,String grantyear) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		String cloFullName=clo.getFirstName()+" "+clo.getLastName();
		String cloEmail=clo.getEmail();
		String region=cloRegion;
		params.put("company_id", company.getId());
		params.put("user_id",user.getId());
		params.put("crmUser",cloFullName);
		params.put("cloEmail",cloEmail);
		params.put("region",region);
		byte[] bites = JasperService.instance().convertJasperReportToByte("DG_Application_Withdrawal.jasper", params);
		
		String subject = "WITHDRAWAL OF DISCRETIONARY GRANT APPLICATION FOR: "+company.getCompanyName()+" ("+company.getLevyNumber()+")";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Thank you for submitting a discretionary grant application for "+grantyear+"."
				+ "</p>"
				
				+" <ul><li>"+reason+"</li></ul> "
				
					
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "For further assistance/support, kindly contact the designated "
				+ "Client Liaison Officer: "+cloFullName+" (Email: "+cloEmail+") or the respective "+region+" Office."
				+ "</p>"

				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Programme Implementation Unit</b>"
				+ "</p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "DG_Application_Withdrawal.pdf", "pdf", null);

	}
	
	public void dgApplicationRequestHigherAllocation(Users user,Company company,String reason,Users clo,String cloRegion) throws Exception {
		/*CompanyService companyService=new CompanyService();
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("company_id", 21L);
		params.put("user_id",109L);*/
		
		String cloFullName=clo.getFirstName()+" "+clo.getLastName();
		String cloEmail=clo.getEmail();
		String region=cloRegion;
		
		//byte[] bites = JasperService.instance().convertJasperReportToByte("filename.jasper", params);//To be changed
		
		String subject = "DISCRETIONARY GRANT APPLICATION REQUEST FOR HIGHER ALLOCATION: "+company.getCompanyName()+" ("+company.getLevyNumber()+")";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA acknowledges receipt of your request for a higher allocation for the following reason:"
				+ "</p>"
				
				+" <ul><li>"+reason+"</li></ul> "
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We wish to inform you that a Memorandum of Agreement (MOA) "
				+ "for the current allocation is enclosed and must be processed "
				+ "within 30 working days from date of issue while the merSETA "
				+ "considers your request. "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that you are required to submit a "
				+ "motivation/proposal to the merSETA via email: "
				+ "dgmoa@merseta.org.za with respect to the request "
				+ "for a higher allocation. If your request is successful, "
				+ "another MOA will be issued.  "
				+ "</p>"
				

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that should the enclosed MOA is not "
				+ "uploaded on the NSDMS within 30 working days from the "
				+ "date of issue, the merSETA may withdraw the MOA. Furthermore, "
				+ "please note that you are also required to complete the "
				+ "Project Implementation Plan online within the same period."
				+ "</p>"

				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "For further assistance/support, kindly contact the designated "
				+ "Client Liaison Officer: "+cloFullName+" (Email: "+cloEmail+") or the respective "+region+" Office."
				+ "</p>"

				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Programme Implementation Unit</b>"
				+ "</p>";

		//GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "DG_Application_Request_Higher_Allocation.pdf", "pdf", null);
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}
	
	public void dgApplicationAppealUnsuccessful(Users user,Company company,String reason,Users clo,String cloRegion,String grantyear, String appealSubmissionDate) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		
		String cloFullName=clo.getFirstName()+" "+clo.getLastName();
		String cloEmail=clo.getEmail();
		String region=cloRegion;
		
		params.put("company_id", company.getId());
		params.put("user_id",user.getId());
		params.put("crmUser",cloFullName);
		params.put("cloEmail",cloEmail);
		params.put("region",region);
		
		byte[] bites = JasperService.instance().convertJasperReportToByte("DG_Application_Unsuccessful.jasper", params);
		
		String subject = "DISCRETIONARY GRANT APPLICATION APPEAL FOR: "+company.getCompanyName()+" ("+company.getLevyNumber()+")";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We write to inform you that the merSETA has considered the "
				+ "discretionary grant application appeal for your organisation "
				+ "submitted on "+appealSubmissionDate+". "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We regret to advise that the outcome for the discretionary "
				+ "grant application appeal is <b>unsuccessful</b>. "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "For further assistance/support, kindly contact the designated "
				+ "Client Liaison Officer: "+cloFullName+" (Email: "+cloEmail+") or the respective "+region+" Office."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Programme Implementation Unit</b>"
				+ "</p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "DG_Application_Appeal_Unsuccessful_Outcome.pdf", "pdf", null);

	}
	
	public void dgApplicationAppealSuccessfulEmail(Users user,Company company,String reason,Users clo,String cloRegion,String grantyear, String appealSubmissionDate) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		String cloFullName=clo.getFirstName()+" "+clo.getLastName();
		String cloEmail=clo.getEmail();
		String region=cloRegion;
		
		params.put("company_id", company.getId());
		params.put("user_id",user.getId());
		params.put("crmUser",cloFullName);
		params.put("cloEmail",cloEmail);
		params.put("region",region);
		
		byte[] bites = JasperService.instance().convertJasperReportToByte("DG_Application_Successful.jasper", params);
		
		String subject = "DISCRETIONARY GRANT APPLICATION APPEAL: "+company.getCompanyName()+" ("+company.getLevyNumber()+")";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We wish to inform you that the merSETA has considered "
				+ "the discretionary grant application appeal for your "
				+ "organisation submitted on "+appealSubmissionDate+". "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We are pleased to inform you that the application was "
				+ "<b>successful</b>. Please may you complete the project "
				+ "implementation plan on the NSDMS and submit together "
				+ "with the signed Memorandum of Agreement (MOA) within "
				+ "<b>30 business days</b> from the date of receipt."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please note that should the enclosed MOA is not uploaded "
				+ "on the NSDMS within 30 business days from the date of issue, "
				+ "the merSETA may withdraw the MOA. "
				+ "</p>"
								
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "For further assistance/support, kindly contact the designated "
				+ "Client Liaison Officer: "+cloFullName+" (Email: "+cloEmail+") or the respective "+region+" Office."
				+ "</p>"

				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Programme Implementation Unit</b>"
				+ "</p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "DG_Application_Appeal_Successful_Outcome.pdf", "pdf", null);

	}
	
	
	public void dgApplicationAllocationChange(Users user,Company company,String reason,Users clo,String cloRegion,String grantyear)  throws Exception {
	
		String cloFullName=clo.getFirstName()+" "+clo.getLastName();
		String cloEmail=clo.getEmail();
		
		String subject = "ALLOCATION CHANGE IN DISCRETIONARY GRANT APPLICATION FOR: "+company.getCompanyName()+" ("+company.getLevyNumber()+")";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Thank you for submitting a discretionary grant application for "+grantyear+"."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We acknowledge the request to change the allocation in your application for the following reason(s):"
				+ "</p>"
				
				+" <ul><li>"+reason+"</li></ul> "
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly be advised that the request will be considered by "
				+ "the merSETA within 14 days from receipt of the request "
				+ "and after consultation with your organisation."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Should you have any queries please do not hesitate to contact "
				+ "your Client Liaison Officer: "+cloFullName+" (Email: "+cloEmail+") for assistance. "
				+ "</p>"

				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Programme Implementation Unit</b>"
				+ "</p>";

		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}
	
	
	
	
	//************************************************************************************************************************
	
	
	public void testEmails()
	{
		DgAllocationService dgAllocationService=new DgAllocationService();
		
		CompanyService companyService=new CompanyService();
		UsersService userService=new UsersService();
		
		
		try {
			DgAllocationParentService ss = new DgAllocationParentService();
			DgAllocationParent entity= ss.findByKey1(4L);
			Company company=companyService.findByKey(40739L);
			Users user= userService.findByKey(11131);
			Users clo=user;
			String reason="Incomplete  application";
			String cloRegion="Gauteng";
			String grantyear="2019";
			String appealSubmissionDate="24 April 2018";
			ArrayList<RejectReasons> rejectReasons=new ArrayList<>();
			int count=0;
			for(RejectReasons rs:reasonsService.allRejectReasons())
			{
				rejectReasons.add(rs);
				if(count>5)
				{
					break;
				}
				count++;
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				user.setEmail(emailNotificiations);
				break;
			}
			dgAllocationService.sendDGApplicationRequestHigherAllocationEmail(entity,user, company, clo, cloRegion, grantyear, reason, true);
			//dgAllocationService.sendDGApplicationWithdrawalEmail(user, company, clo, cloRegion, grantyear, appealSubmissionDate, reason);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	
	public void downloadMOA()
	{
		DgAllocationService dgAllocationService=new DgAllocationService();
		
		CompanyService companyService=new CompanyService();
		UsersService userService=new UsersService();
		
		
		try {
			DgAllocationParentService ss = new DgAllocationParentService();
			DgAllocationParent entity= ss.findByKey1(4L);
			Company company=companyService.findByKey(40739L);
			Users user= userService.findByKey(11131);
			Users clo=user;
			String reason="Incomplete  application";
			String cloRegion="Gauteng";
			String grantyear="2019";
			String appealSubmissionDate="24 April 2018";
			
			
			ArrayList<RejectReasons> rejectReasons=new ArrayList<>();

			int count=0;
			for(RejectReasons rs:reasonsService.allRejectReasons())
			{
				rejectReasons.add(rs);
				if(count>5)
				{
					break;
				}
				count++;
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				user.setEmail(emailNotificiations);
				break;
			}
			
			//dgAllocationService.sendDGApplicationRequestHigherAllocationEmail(entity,user, company, clo, cloRegion, grantyear, reason, true);
			
			dgAllocationService.downloadMOA(entity, true);
			//dgAllocationService.sendDGApplicationWithdrawalEmail(user, company, clo, cloRegion, grantyear, appealSubmissionDate, reason);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	private String txt;
	
	public String createId() //don't need the arguments any more
	{
	     UUID uuid = UUID.randomUUID();
	     return uuid.toString();
	}
	
	public void downloadLearnerAgreementForm() {
		/*Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);*/
		
		String thisYear = new SimpleDateFormat("yy").format(new Date());
		String merSETA = "17";
		
		System.out.println(merSETA+"/"+ createId() + "/"+ thisYear);
		
		/*Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		try {
			
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
			
			UsersService usersService=new UsersService();
			CompanyService companyService=new CompanyService();
			Users legalGaurdian = new Users();
			Users learner =usersService.findByKey(55L);
			
			if(learner.getLegalGaurdian()!=null)
			{
				legalGaurdian=usersService.findByKey(learner.getLegalGaurdian().getId());
			}
			
			Company employer=companyService.findByKey(27469L);
			Users employerContactPerson=usersService.findByKey(47L);
			Company skillDevProvider=companyService.findByKey(27468L);
			Users skillDevProviderContactPerson=usersService.findByKey(52L);
			
			params.put("learner", learner);
			params.put("legalGaurdian",legalGaurdian);
			params.put("employer",employer);
			params.put("employer_contact_person",employerContactPerson);
			params.put("skill_dev_provider",skillDevProvider);
			params.put("skill_dev_provider_contact_person",skillDevProviderContactPerson);
			
			params.put("have_bullet", true);
			//JasperService.addLogo(params);
			//Add Image params
			
			//js.createReportFromDBtoServletOutputStream("LPM-FM-002-LearnershipAgreementForOfficeUseOnlys.jasper", params,"AgreementForm.pdf");
			js.createReportFromDBtoServletOutputStream("LPM-FM-002-LearnershipAgreementForOfficeUseOnly.jasper", params,"AgreementForm.pdf");
			
			System.out.println(txt);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	
	
	public void downloadApplicationForm() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		try {
			
			
			params.put("txt", removeHTMCode(txt));
			/*params.put("recipient_id", 47L);
			params.put("call_center_number", "0823266598");
			params.put("document_number", "DOC123");
			params.put("revision_number", "REV 3");
			params.put("access", "Control");
			params.put("last_revision_date", "2017-04-24");
			JasperService.addLogo(params);*/
			js.createReportFromDBtoServletOutputStream("Bullets.jasper", params,"document.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void downloadOustandingInfoRegAssessmentModeration() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		try {
			
			
		
			UsersService usersService=new UsersService();
			Users user = usersService.findByKey(120L,ConfigDocProcessEnum.AM, CompanyUserTypeEnum.User);
			params.put("DocumentsDataSource", new JRBeanCollectionDataSource(user.getDocs()));
			params.put("company_id", 21L);
			params.put("am_app_id", 7L);
			params.put("desktop_date", "01 April 2018");
			params.put("App_Type", "an Assessor");
			JasperService.addLogo(params);
			js.createReportFromDBtoServletOutputStream("ETQ-TP-014-OustandingInfoRegAssessmentModeration.jasper", params,"document.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadPIM() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		try {
			
			ProjectImplementationPlanService projectImplementationPlanService=new ProjectImplementationPlanService();
			params.put("PIMDataSource1", new JRBeanCollectionDataSource(projectImplementationPlanService.allProjectImplementationPlan()));
			
			List<ProjectImplementationPlan> dummyList=new ArrayList<>();
			for(int x=0;x<5;x++)
			{
				ProjectImplementationPlan imp=new ProjectImplementationPlan();
				dummyList.add(imp);
			}
			params.put("PIMDataSource2", new JRBeanCollectionDataSource(dummyList));
			JasperService.addLogo(params);
			js.createReportFromDBtoServletOutputStream("Project_Implementation_Plan.jasper", params,"document.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadDummyCertificate() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		try {
			params.put("first_name", "DunnyName");
			params.put("last_name","DunnySurname");
			params.put("moduleID",143L);
			params.put("objectives", txt);
			
			params.put("have_bullet", true);
			//JasperService.addLogo(params);
			//Add Image params
			
			js.createReportFromDBtoServletOutputStream("knowledgecenterCertificate.jasper", params,"document.pdf");
			
			System.out.println(txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String removeHTMCode(String html)
	{
		html=html.replaceAll("<ul>", "");
		html=html.replaceAll("</ul>", "");
		html=html.replaceAll("<li>", "\n   \u2022   ");
		html=html.replaceAll("</li>", "\n");
		html= html.replaceAll("\\<[^>]*>","");
		html=StringEscapeUtils.unescapeHtml3(html);
		
		return html;
	}
	
	
	public void sendTPEmails()
	{
		try 
		{
			TrainingProviderApplicationService tProviderApplicationService=new TrainingProviderApplicationService();
			TrainingProviderApplication trainingProviderApplication=tProviderApplicationService.findByKey(1L);
			CompanyQualificationsService companyQualificationsService=new CompanyQualificationsService();
			List<CompanyQualifications> companyQualifications=companyQualificationsService.findByCompany(trainingProviderApplication.getCompany());
			CompanyUnitStandardService companyUnitStandardService=new CompanyUnitStandardService();
			List<CompanyUnitStandard> unitStandards=companyUnitStandardService.findByCompany(trainingProviderApplication.getCompany());
			TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
			//trainingProviderApplicationService.sendProFormaLetterRorFullAccreditationEmail(  trainingProviderApplication.getAccreditationNumber(), trainingProviderApplication,companyQualifications, unitStandards);
		}
		catch (Exception e) {
		
			e.printStackTrace();
		}
	
	}
	
	
	public void sendEmails()
	{
		UsersService usersService=new UsersService();
		CompanyService companyService=new CompanyService();
		BankingDetailsService bankingDetailsService=new BankingDetailsService();
		try 
		{
			Users user=usersService.findByKey(44L);
			Company company=companyService.findByKey(21L);
			
			SDFCompanyService.instance().genApplicationForm(company);
			
			/*bankingDetailsService.sendBankingDeatildDocNotRequiredEmail(user, company, new Date());
			bankingDetailsService.sendBankingDeatildDocRequirementsEmail(user, company);*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void sendBankingDeatildDocNotRequiredEmail() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		
		params.put("call_center_number", HAJConstants.MERSETA_CALL_CENTRE);
		params.put("company_id", 21L);
		params.put("user_id",109L);
		params.put("contact_person_user_id",79L);
		params.put("confirmed_date","29/03/2018");
		
		byte[] bites = JasperService.instance().convertJasperReportToByte("Banking_Details_Not_Required_ Final_Reminder.jasper", params);
		CompanyService companyService=new CompanyService();
		
		String subject = "CONFIRMATION OF BANKING DETAILS - " + "GLOBAL PIPING CCGLOBAL PIPING CC" + " (" + "L660736895"+ ")";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +"Christoph" + " " + "Sibiya" + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				
				+ "Thank you for submitting the banking details for GLOBAL PIPING CCGLOBAL PIPING CC on the NSDMS. The merSETA wishes to inform you "
				+ "that you are not required to send original banking detail documentation for the organisation as this "
				+ "documentation has previously been submitted to the merSETA"
			
				+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact the merSETA Call Centre on: " + HAJConstants.MERSETA_CALL_CENTRE + " for assistance. </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";

		for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
			GenericUtility.sendMailWithAttachement(emailNotificiations, subject, mssg, bites, "Banking_Details_Required_Final_Reminder.pdf", "pdf", null);
		}
		

	}
	
	public void sendBankingDeatildDocRequirementsEmail() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		
		params.put("call_center_number", HAJConstants.MERSETA_CALL_CENTRE);
		params.put("company_id", 21L);
		params.put("user_id",109L);
		byte[] bites = JasperService.instance().convertJasperReportToByte("Banking_Details_Required_ Final_Reminder.jasper", params);

		String subject = "BANKING DETAILS DOCUMENTATION REQUIRED - " + "GLOBAL PIPING CCGLOBAL PIPING CC" + " (" + "L660736895"+ ")";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +"Christoph" + " " + "Sibiya" + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA hereby requires the following original documentation with regards to the banking details of the company:</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<ol style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ " <li>An original letter on the company letterhead confirming the company's levy number/s"
				+ " banking details and must be signed by the company Director, CEO or Financial Manager. "
				+ "The letter must reflect the name, designation and signature of the person confirming the "
				+ "information and the levy number of the company together with the e-mail address of the"
				+ " person who has signed the letter.<u> Please note that this letter is not the letter from your banking institution.</u> </li>"
				+ "<br/>"
				+ "<li>An original cancelled cheque or an original current bank statement or letter from the bank that includes the bank stamp.</li>"
				+ "</ol>"
				+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The documents must be delivered to merSETA Head Office at the following address:</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>"
				+ "8 Hillside Road, Metropolitan Park, Block C Parktown, 2193 <br/>"
				+ "</b>"
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please note that the final assessment of the grant application will only be done upon receipt and verification of the original documentation.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact the merSETA Call Centre on: " + HAJConstants.MERSETA_CALL_CENTRE + " for assistance. </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";
		for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
			GenericUtility.sendMailWithAttachement(emailNotificiations, subject, mssg, bites, "Banking_Details_Required_Final_Reminder.pdf", "pdf", null);
		}
		

	}
	
	
	public void sendAcknowledgementOfSubmission() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		Calendar prevYear = Calendar.getInstance();
		prevYear.add(Calendar.YEAR, -1);
		int year = prevYear.get(Calendar.YEAR);

		String period = "1 January " + year + " to " + "31 December " + year;
		params.put("period", period);
		params.put("submited_by_user_id", 109L);
		params.put("call_center_number", HAJConstants.MERSETA_CALL_CENTRE);
		params.put("company_id", 21L);
		byte[] bites = JasperService.instance().convertJasperReportToByte("Aknowledgement_Of_WSP_Submission.jasper", params);

		String subject = "ACKNOWLEDGEMENT OF MANDATORY GRANT SUBMISSION:  Company name";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear Christoph Sibiya,</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Thank you for using the merSETA online facility to submit the mandatory grant application for Company Name).</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We acknowledge receipt of the application and include an acknowledgement of submission notification for your records.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We will be in contact with you should there be any outstanding information required. Please further note that the payment" 
				+ " of mandatory grants can only be made once the levies for the company have been received by the merSETA and if the application is approved.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact the merSETA Call Centre on: " + HAJConstants.MERSETA_CALL_CENTRE + " for assistance. </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";
		for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
			GenericUtility.sendMailWithAttachement(emailNotificiations, subject, mssg, bites, "Aknowledgement_Of_WSP_Submission.pdf", "pdf", null);
		}
		

	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
	
	
	
	public ArrayList<MillwrightToolsListBean> getMillwrightToolList()
	{
		String comment="";
		MillwrightToolsListBean bean=null;
		ArrayList<MillwrightToolsListBean> list=new ArrayList<>();
		
		
		bean= new MillwrightToolsListBean("Spanners - open end 6-30mm", "1:1",true,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Spanners - open - closed end combination 6-30mm", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Adjustable wrench", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Screw drivers - Flat set no1-4", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Screw drivers - Phillips  no1-4", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Screw drivers instrument set ", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Screw driver - Flat (Insulated set for electrical use)", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("screw driver - Phillips (Insulated set for electrical use)", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Pliers - long nose  (Insulated handles)", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Pliers - Combination  (Insulated handles)", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Pliers - Side cut  (Insulated handles)", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Pliers - Water pump ", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Pliers - Round nose (Insulated handles)", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Pliers - Circle  inner and outer (Bent and straight nose)", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("Pliers - Crimper for terminals", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("", "1:1",false,  comment);
		list.add(bean);
		
		bean= new MillwrightToolsListBean("", "1:1",false,  comment);
		list.add(bean);
		
		return list;
	}
	
	public void downloadLPMFM006() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		try {
			
			
		
			UsersService usersService=new UsersService();
			LearnersService learnersService=new LearnersService();
			CompanyService companyService=new CompanyService();
			Users lead_emp_contact_person = usersService.findByKey(120L);
			Learners learner=learnersService.findByKey(1L);
			Company employer=companyService.findByKey(21L);
			Company second_employer=companyService.findByKey(7L);
			Company third_employer=companyService.findByKey(10L);
			Company lead_employer=companyService.findByKey(21L);
			Users lead_emp_sdf = usersService.findByKey(47L);
			Company host_employer=companyService.findByKey(18L);
			Users host_emp_contact_person = usersService.findByKey(107L);
			Users host_emp_sdf = usersService.findByKey(119L);
			
			params.put("lead_emp_contact_person",lead_emp_contact_person);
			params.put("learner", learner);
			params.put("employer", employer);
			params.put("second_employer", second_employer);
			params.put("third_employer", third_employer);
			params.put("lead_employer", lead_employer);
			params.put("lead_emp_sdf", lead_emp_sdf);
			params.put("host_employer", host_employer);
			params.put("host_emp_contact_person", host_emp_contact_person);
			params.put("host_emp_sdf", host_emp_sdf);
			
			JasperService.addLogo(params);
			js.createReportFromDBtoServletOutputStream("LPM-FM-006(C)-ApprenticeshipContractATRAMI.jasper", params,"LPM-FM-006(C).pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendSuccessfulDGVerificationOutcomeEmail() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		
		CompanyService companyService=new CompanyService();
		UsersService usersService=new UsersService();
		
		String grantYear="2018";
		Company company=companyService.findByKey(27468L);
		Users user = usersService.findByKey(1141L);
		
		String clientLiaisonOffice="Pretoria West";
		
		params.put("grantYear", grantYear);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("clientLiaisonOffice", clientLiaisonOffice);
		byte[] bites = JasperService.instance().convertJasperReportToByte("Notification-SuccessfulDGverificationOutcome.jasper", params);

		String subject = "OUTCOME OF DISCRETIONARY GRANT VERIFICATION FOR "+company.getCompanyName()+" - "+company.getCompanyName()+" ";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+"Thank you for submitting a discretionary grant application for "+grantYear+"."
				+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+"We wish to inform you that the merSETA has completed the discretionary grant application verification. "
				+"You are required to review and sign off the outcome of the verification."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+"Please be advised that should the discretionary grant "
				+ "application verification not be signed off online on the "
				+ "NSDMS within five (5) working days from the date of this "
				+ "notification, the merSETA will automatically use the "
				+ "outcome to progress to the next phase of the application process."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+"We wish to advise that while the discretionary grant "
				+ "verification has been successfully completed, "
				+ "the application has not yet been finalised. "
				+ "The final outcome of the discretionary grant "
				+ "application will be communicated at a later date. "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA DG Team </p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Successful_DG_verification_Outcome.pdf", "pdf", null);

	}
	

	
	public void sendDiscritionaryGrandRejectionEmail() throws Exception {
		
		sendSuccessfulDGVerificationOutcomeEmail();
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		
		
		ArrayList<RejectReasons> rejectReasons=new ArrayList<>();
		CompanyService companyService=new CompanyService();
		UsersService usersService=new UsersService();
		RejectReasonsService reasonsService=new RejectReasonsService();
		
		int count=0;
		for(RejectReasons rs:reasonsService.allRejectReasons())
		{
			rejectReasons.add(rs);
			if(count>5)
			{
				break;
			}
			count++;
		}
		
		String grantYear="2018";
		Company company=companyService.findByKey(27468L);
		Users user = usersService.findByKey(1141L);
		
		String clientLiaisonOffice="Pretoria West";
		
		params.put("grantYear", grantYear);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		
		
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
		byte[] bites = JasperService.instance().convertJasperReportToByte("DiscretionaryGrantVerificationOutcome.jasper", params);

		String subject = "OUTCOME OF DISCRETIONARY GRANT VERIFICATION FOR "+company.getCompanyName()+" - "+company.getCompanyName()+" ";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Thank you for submitting a discretionary grant application for "+grantYear+"."
				+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We regret to advise that the application has not been successful. "
				+ "Should you wish to appeal the outcome, please submit an appeal to "
				+ "DGClaims@merseta.org.za within 14 days of receipt of this notification."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Should you have any queries please do not hesitate to contact your Client "
				+ "Liaison Officer: "+clientLiaisonOffice+" for assistance. "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA DG Team </p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Discretionary_Grant_Verification_Outcome.pdf", "pdf", null);

	}
	
	public void downloadDG() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		JasperService.addLogo(params);
		
		try {
			ArrayList<RejectReasons> rejectReasons=new ArrayList<>();
			CompanyService companyService=new CompanyService();
			UsersService usersService=new UsersService();
			
			RejectReasonsService reasonsService=new RejectReasonsService();
			int count=0;
			for(RejectReasons rs:reasonsService.allRejectReasons())
			{
				rejectReasons.add(rs);
				if(count>3)
				{
					break;
				}
				count++;
			}
			
			
			
			String grantYear="2019";
			Company company=companyService.findByKey1(27468L);
			Users user = usersService.findByKey(47L);
			
			params.put("company_id", company.getId());
			params.put("user_id", user.getId());
			
			params.put("grantyear", grantYear);
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String appealsubmissiondate = now.format(formatter);
			
			params.put("appealsubmissiondate", appealsubmissiondate);
			
			params.put("grantyear", grantYear);
			params.put("cloEmail","testingEmail@a.com");
			
			params.put("cloFullName", "CLO User Full Name");
			
			params.put("region", "Gauteng");
			params.put("ceoFullname", "Owami Nonhlakanipho");
			
			params.put("reason", convertStringToHTML("reason one"));
			params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
			
			JasperService.addLogo(params);
			
			//js.createReportFromDBtoServletOutputStream("DGD-TP-003-Notification_Discretionary_Grants_Application_Outcome_Appeal_Successful.jasper", params,"DGD-TP-003.pdf");
			
			//js.createReportFromDBtoServletOutputStream("DGD-TP-004-Notification_Discretionary_Grants_Application_Appeal _Unsuccessful.jasper", params,"DGD-TP-004.pdf");
						
			//js.createReportFromDBtoServletOutputStream("DGD-TP-005-Notification_Discretionary_Grants_Application_Withdrawal.jasper", params,"DGD-TP-005.pdf");
			
			js.createReportFromDBtoServletOutputStream("DGD-TP-006-Notification_Discretionary_Grants_Application_Unsuccessful.jasper", params,"DGD-TP-006.pdf");
			
			//js.createReportFromDBtoServletOutputStream("DGD-TP-007-Notification_Discretionary_Grants_Application_Outcome_Successful.jasper", params,"DGD-TP-007.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String convertStringToHTML(String r){		
		String sb ="<ul>"+r+"</ul>";
		return sb;
	}
	
	
	
	public void sendAcknoledgementEmail() {
			
		   String path= System.getProperty("java.class.path");
		   
		   System.out.println("Path: "+path);
		   
			
		}
	
	
	
	//************************************************************CAR AUDIT****************************************************************************
	public void downlaodQuestionPaper() {
	
		try {
			FormTypeService formTypeService=new FormTypeService();
			FormTypeSectionsService formTypeSectionsService=new FormTypeSectionsService();
			UsersService usersService=new UsersService();
			
			FormType formtype=formTypeService.findByKey(1L);//To be Changed
			//PROJECT MANAGER 
			Users projectManager=usersService.findByKey(54L);//To be Changed
			//AUDIT PREPARED BY
			Users auditUser=usersService.findByKey(55L);//To be Changed
			//CONTRACT NUMBER
			String contractNumber="9632587454511222";//To be Changed
			
			formtype.setFormTypeSectionsList(resolveQuestion((ArrayList<FormTypeSections>) formTypeSectionsService.findByFormType(formtype)));
			downloadQuestionPaper(formtype, projectManager, auditUser,contractNumber);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public ArrayList<FormTypeSections> resolveQuestion(ArrayList<FormTypeSections> formTypeSectionsList) throws Exception
	{
		FormTypeSectionsService formTypeSectionsService=new FormTypeSectionsService();
		for(FormTypeSections formTypeSection:formTypeSectionsList)
		{
			formTypeSectionsService.findQuestions(formTypeSection);
		}
		return formTypeSectionsList;
	}
	
	public void downloadQuestionPaper(FormType formtype,Users projectManager,Users auditUser,String contractNumber) throws Exception {
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		ArrayList<FormSectionQuestionsBean> questionList = createFormSectionQuestionsBeanList(formtype);
		int totalMarks = 0;
		for (FormSectionQuestionsBean question : questionList) {
			totalMarks += question.getMarks();
		}
	
		JasperService.addLogo(params);
		params.put("formSectionQuestionsBeanDataSource", new JRBeanCollectionDataSource(questionList));
		params.put("formType", formtype);
		params.put("totalMarks", totalMarks);
		params.put("projectManager", projectManager);
		params.put("auditUser", auditUser);
		params.put("contractNumber", contractNumber);
		params.put("color", "#F7E83B");//Change this to Company Color
		JasperService.instance().createReportFromDBtoServletOutputStream("CARSiteAudits.jasper", params, "QuestionPaper.pdf");
	
	}
	
	
	public ArrayList<FormSectionQuestionsBean> createFormSectionQuestionsBeanList(FormType formtype) throws Exception {
		ArrayList<FormSectionQuestionsBean> list = new ArrayList<>();
		ArrayList<FormTypeSections> formTypeSectionsList=formtype.getFormTypeSectionsList();
		for(FormTypeSections formTypeSection:formTypeSectionsList)
		{
			List<FormSectionQuestions> formSectionQuestions=formTypeSection.getFormSectionQuestions();
			FormSectionQuestionsBean bean = null;
			int count=0;
			int questionNumberCount=1;
			for (FormSectionQuestions question :formSectionQuestions) {
				String data="";
				//Adding Section title
				if(count==0){
					data +="<b>"+ formTypeSection.getSectionTitle() +"</b> <br/><br/>";
				}
				data +=question.getQuestion() + "<br/><br/>";
				data +=getQuestionAndAnswer(question);
				bean = new FormSectionQuestionsBean(data, question.getMarks(), question.getAnswerType());
				bean.setQuestionNum(questionNumberCount+".");
				if(count==0){bean.setContainTitle(true);}else{bean.setContainTitle(false);}
				list.add(bean);
				count++;
				questionNumberCount++;
			}
		}
		return list;
	
	}
	
	public String getQuestionAndAnswer(FormSectionQuestions question)
	{
		String data="";
		FormTypeAnswersService answersService=new FormTypeAnswersService();
		try {
			if (question.getAnswerType() == AnswerTypeEnum.Text) {
				data ="textAnswer";//Get the answer from FormTypeAnswers 
			} else if (question.getAnswerType() == AnswerTypeEnum.TextArea) {
				data ="textAnswer";//Get the answer from FormTypeAnswers 
				
			} else if (question.getAnswerType() == AnswerTypeEnum.DropDown) {
				data ="selectedAnswer";//Get the answer from FormTypeAnswers 
	
			} else if (question.getAnswerType() == AnswerTypeEnum.RadioButtons) {
				data ="selectedAnswer";//Get the answer from FormTypeAnswers 
				
			} else if (question.getAnswerType() == AnswerTypeEnum.Date) {
				data ="dateAnswer";//Get the answer from FormTypeAnswers 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "<i>"+data+"</i><br/><br/>";
	}
	public String addSpace(String txt) {
		String space = "";
		for (int x = 0; x <= (155 - txt.length()); x++) {
			space += "&nbsp;";
		}
		space += txt;
		return space;
	}
	
	public ArrayList<FormSectionQuestions> randomQuestions(ArrayList<FormSectionQuestions> formSectionQuestions, int numQuestion) {
		ArrayList<FormSectionQuestions> randomQuestionList = new ArrayList<>();
		Random randomGenerator = new Random();
		do {
			int index = randomGenerator.nextInt(formSectionQuestions.size());
			randomQuestionList.add(formSectionQuestions.get(index));
			formSectionQuestions.remove(index);
		} while (randomQuestionList.size() < numQuestion && formSectionQuestions.size() > 0);
	
		return randomQuestionList;
	
	}
	
	
	
	public void sendForOfficeUseOnlyForm() {
		CompanyLearnersService companyLearnersService = new CompanyLearnersService();	
		UsersService userService =new UsersService();
		try {
			CompanyLearners companylearners = companyLearnersService.findByKey(97L);			
			Users user = userService.findByKey(4319L);	
			companyLearnersService.sendForOfficeUseOnlyForm(companylearners, user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
	}
	
	public void downloadFile() {
		try {
			CompanyLearnersTradeTestService tt = new CompanyLearnersTradeTestService();
//			tt.downloadCompanyLearnersTradeTest();
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
	}
	public void downloadFile1() {
		CompanyLearnersService companyLearnersService = new CompanyLearnersService();		

		try {
			CompanyLearners companylearners = companyLearnersService.findByKey(109L);			
			Users createUser = companylearners.getCreateUser();//
			companyLearnersService.sendLPMTP024Letter(createUser, companylearners.getCompany());
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
		
	}
	
	public void downloadOtpSignoff() {
		CompanyLearnersOtpSignoffService companyLearnersService = new CompanyLearnersOtpSignoffService();	
		try {
			CompanyLearners companylearners = companyLearnersService.findByKey(319L);	
			companyLearnersService.downloadWorkBasedLearningProgrammeAgreement(companylearners);
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}
	
	public void downloadForOfficeUseOnlyForm() {
		CompanyLearnersService companyLearnersService = new CompanyLearnersService();	
		try {
			CompanyLearners companylearners = companyLearnersService.findByKey(112L);			
			Users createUser = companylearners.getCreateUser();
			companyLearnersService.downloadForOfficeUseOnlyForm(companylearners,createUser);
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
		
	}
	
	public void downloadCertificate() {
		TrainingProviderVerficationService trainingProviderVerficationService = new TrainingProviderVerficationService();	
		CompanyLearnersService companyLearnersService = new CompanyLearnersService();
		try {
			//TrainingProviderVerfication trainingProviderVerfication = trainingProviderVerficationService.findByKey(33L);	
			TrainingProviderVerfication trainingProviderVerfication = trainingProviderVerficationService.findByKey(8L);
			CompanyLearners  companyLearners = companyLearnersService.findByKey(trainingProviderVerfication.getCompanyLearners().getId());
			Users createUser = companyLearners.getCreateUser();
			trainingProviderVerficationService.downloadCertificate(trainingProviderVerfication,createUser);
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}
	
	public void download007() {
		TrainingProviderVerficationService trainingProviderVerficationService = new TrainingProviderVerficationService();
		SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
		CompanyLearnersService companyLearnersService = new CompanyLearnersService();
		try {
			//TrainingProviderVerfication trainingProviderVerfication = trainingProviderVerficationService.findByKey(33L);	
			TrainingProviderVerfication trainingProviderVerfication = trainingProviderVerficationService.findByKey(8L);
			CompanyLearners  companyLearners = companyLearnersService.findByKey(trainingProviderVerfication.getCompanyLearners().getId());
			Users createUser = companyLearners.getCreateUser();
			summativeAssessmentReportService.downloadReport(trainingProviderVerfication,createUser);
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}
	
	public void downloadLPMFM009() {
		try {
			CompanyLearnersTradeTestService service = new CompanyLearnersTradeTestService();
			CompanyTradeTestEmployerService companyTradeTestEmployerService = new CompanyTradeTestEmployerService();
			
			CompanyLearnersTradeTest companylearnerstradetest = service.findByKey(19L);
			CompanyTradeTestEmployer companyTradeTestEmployer= companyTradeTestEmployerService.findByKey(9);
			
			companylearnerstradetest.setEmployer(companyTradeTestEmployer);
			service.downloadLPMFM009(companylearnerstradetest, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void download011() {
		TrainingProviderVerficationService trainingProviderVerficationService = new TrainingProviderVerficationService();
		SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
		CompanyLearnersService companyLearnersService = new CompanyLearnersService();
		try {
			//TrainingProviderVerfication trainingProviderVerfication = trainingProviderVerficationService.findByKey(33L);	
			TrainingProviderVerfication trainingProviderVerfication = trainingProviderVerficationService.findByKey(8L);
			CompanyLearners  companyLearners = companyLearnersService.findByKey(trainingProviderVerfication.getCompanyLearners().getId());
			Users createUser = companyLearners.getCreateUser();
			summativeAssessmentReportService.downloadETQ_TP_011_Report(trainingProviderVerfication, createUser);
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}
	
	public void downloadLearnerForm() {
		CompanyService companyService=new CompanyService();
		UsersService userService =new UsersService();
		CompanyUsersService companyUsersService = new CompanyUsersService();
		CompanyLearnersService companyLearnersService = new CompanyLearnersService();
		MunicipalityService municipalityService = new MunicipalityService();
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		JasperService.addLogo(params);
		Users createUser = null; 
		CompanyLearners companylearners = null;
		Users learner = null; 
		Company company = null; 
		Company trainingProvider = null; 
		boolean requireGaurdian = false;  
		
		UsersLanguageService usersLanguageService = new UsersLanguageService();
		List<UsersLanguage> userlanguages = new ArrayList<>();
		try {
			userlanguages = usersLanguageService.findByUserId(9756L);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			companylearners = companyLearnersService.findByKey(95L);
			createUser = userService.findByKey(2662L);
			learner = companylearners.getUser();
			company = companylearners.getCompany();
			trainingProvider = companyService.findByKey(27468L);
			
			// Setting Residential Address District
			Address residentialAddress = learner.getResidentialAddress();
			if (residentialAddress.getMunicipality().getDistrict() != null) {
				residentialAddress.getMunicipality().setDistrict(municipalityService.findByKey(residentialAddress.getMunicipality().getDistrict().getId()));
			} else {
				residentialAddress.getMunicipality().setDistrict(null);
			}
			learner.setResidentialAddress(residentialAddress);
			
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
			
			Users leadCompanyContactPerson = companyUsersService.findCompanyContactPerson(company.getId());
			
			Users leadCompanyContactSupervisor = companyUsersService.findCompanyContactPerson(company.getId());//To be fixed
			
			Company hostCompany =companyService.findByKey(companylearners.getCompany().getId()) ;
			
			
			Users hostCompanyContactPerson = companyUsersService.findCompanyContactPerson(hostCompany.getId());
			Users skillDevelopmentProviderContactPerson = companyUsersService.findCompanyContactPerson(trainingProvider.getId());
			// Setting Residential Address District
			if (trainingProvider.getResidentialAddress() != null) {
				trainingProvider.setResidentialAddress(AddressService.instance().findByKeyJoinDistric(trainingProvider.getResidentialAddress().getId()));
			} else {
				trainingProvider.setRegisteredAddress(new Address());
			}
			// Checking workplace approval for lead company
			boolean isLeadComWorkplaceApproved = false;
			WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
			if (workPlaceApprovalService.findByCompanyCount(company) > 0) {
				isLeadComWorkplaceApproved = true;
			}
			// Checking workplace approval for lead company
			boolean isHostCompWorkplaceApproved = false;
			if (workPlaceApprovalService.findByCompanyCount(hostCompany) > 0) {
				isHostCompWorkplaceApproved = true;
			}
			String leadCompanySETA = "merSETA";
			if (company.getCompanyStatus() == CompanyStatusEnum.NonMersetaCompany) {
				// Set ETQA des as leadCompanySETA
				if (company.getEtqa() != null) {
					leadCompanySETA = company.getEtqa().getDescription();
				} else {
					leadCompanySETA = "N/A";
				}
			}
			String training_intervention = companylearners.getInterventionType().getDescription();
			boolean funded_by_merseta = companylearners.getMersetaFunded().getYesNoBoolean();
			boolean isHavingHostEmployer = true; // To be fixed
			boolean liveInUrban = false;
			if (learner.getUrbanRuralEnum().getFriendlyName().equalsIgnoreCase("Urban")) {
				liveInUrban = true;
			}
			List<CompanyLearners>companylearnersList = companyLearnersService.findByUser(companylearners.getUser());
			boolean physical_addresses = true;
			boolean commencement_completion_date = true;
			boolean sa_citizen = true;
			boolean skills_type = true;
			boolean application_correctly_completed = true;
			
			ArrayList<SkillsProgrammeSkillsSetBean> skillsProgrammeSkillsSetBeanList = new ArrayList<>();
			ArrayList<UnitStandardBean> unitStandardBeanList = new ArrayList<>();
			SkillsProgrammeSkillsSetBean sks = new SkillsProgrammeSkillsSetBean("Suspension Fitter Part 2", "SKS96565", new Date(), new Date());
			skillsProgrammeSkillsSetBeanList.add(sks);
			UnitStandardBean usb = new UnitStandardBean("Physical Planning and Construction", "SAQA 10105/14", new Date(), new Date());
			unitStandardBeanList.add(usb);
			// *******************END To be selected from DB********************

			params.put("SkillsProgrammeSkillsSetBeanDataSource", new JRBeanCollectionDataSource(skillsProgrammeSkillsSetBeanList));
			params.put("UnitStandardBeanDataSource", new JRBeanCollectionDataSource(unitStandardBeanList));
			params.put("companylearners", companylearners);
			params.put("application_correctly_completed", application_correctly_completed);
			params.put("skills_type", skills_type);
			params.put("sa_citizen", sa_citizen);
			params.put("commencement_completion_date", commencement_completion_date);
			params.put("physical_addresses", physical_addresses);
			params.put("training_intervention", training_intervention);
			params.put("funded_by_merseta", funded_by_merseta);
			params.put("isHavingHostEmployer", isHavingHostEmployer);
			params.put("isHavingHostEmployer", isHavingHostEmployer);
			params.put("live_in_urban", liveInUrban);
			params.put("learner", learner);
			params.put("leadCompany", company);
			params.put("leadCompanySETA", leadCompanySETA);
			params.put("isLeadComWorkplaceApproved", isLeadComWorkplaceApproved);
			params.put("leadCompanyContactPerson", leadCompanyContactPerson);
			params.put("leadCompanyContactSupervisor", leadCompanyContactSupervisor);
			params.put("hostCompany", hostCompany);
			params.put("isHostCompWorkplaceApproved", isHostCompWorkplaceApproved);
			params.put("hostCompanyContactPerson", hostCompanyContactPerson);
			params.put("skillDevelopmentProvider", trainingProvider);
			params.put("skillDevelopmentProviderContactPerson", skillDevelopmentProviderContactPerson);
			params.put("isMinor", requireGaurdian);
			if (requireGaurdian) {
				Users legalGaurdian = learner.getLegalGaurdian();
				params.put("parentGuadian", legalGaurdian);
			}
			params.put("userlanguages", new JRBeanCollectionDataSource(userlanguages));
			params.put("companylearnersList", new JRBeanCollectionDataSource(companylearnersList));
			
			params.put("txt", removeHTMCode(txt));
			JasperService.addLogo(params);
			js.createReportFromDBtoServletOutputStream("LPM-FM-015-SkillsProgrammeLearnerRegistrationForm.jasper", params,"document.pdf");
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void downloadLearnerForm1() {
		CompanyService companyService=new CompanyService();
		UsersService userService =new UsersService();
		CompanyUsersService companyUsersService = new CompanyUsersService();
		CompanyLearnersService companyLearnersService = new CompanyLearnersService();
		MunicipalityService municipalityService = new MunicipalityService();
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		JasperService.addLogo(params);
		Users createUser = null; 
		CompanyLearners companylearners = null;
		Users learner = null; 
		Company company = null; 
		Company trainingProvider = null; 
		boolean requireGaurdian = false;  
		
		
		try {
			companylearners = companyLearnersService.findByKey(59L);
			createUser = userService.findByKey(2662L);
			learner = companylearners.getUser();
			company = companylearners.getCompany();
			if(company.getPostalAddress() != null) {
				company.setPostalAddress(AddressService.instance().findByKey(company.getPostalAddress().getId()));
			}else {
				company.setPostalAddress(new Address());
			}
			trainingProvider = companyService.findByKey(27468L);
			if(trainingProvider.getPostalAddress() != null) {
				trainingProvider.setPostalAddress(AddressService.instance().findByKey(trainingProvider.getPostalAddress().getId()));
			}else {
				company.setPostalAddress(new Address());
			}
			
			// Setting Residential Address District
			Address residentialAddress = learner.getResidentialAddress();
			if (residentialAddress.getMunicipality().getDistrict() != null) {
				residentialAddress.getMunicipality().setDistrict(municipalityService.findByKey(residentialAddress.getMunicipality().getDistrict().getId()));
			} else {
				residentialAddress.getMunicipality().setDistrict(null);
			}
			learner.setResidentialAddress(residentialAddress);
			
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
			
			Users leadCompanyContactPerson = companyUsersService.findCompanyContactPerson(company.getId());
			
			Users leadCompanyContactSupervisor = companyUsersService.findCompanyContactPerson(company.getId());//To be fixed
			
			Company hostCompany =companyService.findByKey(companylearners.getCompany().getId()) ;
			
			
			Users hostCompanyContactPerson = companyUsersService.findCompanyContactPerson(hostCompany.getId());
			Users skillDevelopmentProviderContactPerson = companyUsersService.findCompanyContactPerson(trainingProvider.getId());
			// Setting Residential Address District
			if (trainingProvider.getResidentialAddress() != null) {
				trainingProvider.setResidentialAddress(AddressService.instance().findByKeyJoinDistric(trainingProvider.getResidentialAddress().getId()));
			} else {
				trainingProvider.setRegisteredAddress(new Address());
			}
			// Checking workplace approval for lead company
			boolean isLeadComWorkplaceApproved = false;
			WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
			if (workPlaceApprovalService.findByCompanyCount(company) > 0) {
				isLeadComWorkplaceApproved = true;
			}
			// Checking workplace approval for lead company
			boolean isHostCompWorkplaceApproved = false;
			if (workPlaceApprovalService.findByCompanyCount(hostCompany) > 0) {
				isHostCompWorkplaceApproved = true;
			}
			String leadCompanySETA = "merSETA";
			if (company.getCompanyStatus() == CompanyStatusEnum.NonMersetaCompany) {
				// Set ETQA des as leadCompanySETA
				if (company.getEtqa() != null) {
					leadCompanySETA = company.getEtqa().getDescription();
				} else {
					leadCompanySETA = "N/A";
				}
			}
			String training_intervention = companylearners.getInterventionType().getDescription();
			boolean funded_by_merseta = companylearners.getMersetaFunded().getYesNoBoolean();
			boolean isHavingHostEmployer = true; // To be fixed
			boolean liveInUrban = false;
			if (learner.getUrbanRuralEnum() != null && learner.getUrbanRuralEnum().getFriendlyName().equalsIgnoreCase("Urban")) {
				liveInUrban = true;
			}
			
			boolean physical_addresses = true;
			boolean commencement_completion_date = true;
			boolean sa_citizen = true;
			boolean skills_type = true;
			boolean application_correctly_completed = true;
			
			params.put("application_correctly_completed", application_correctly_completed);
			params.put("skills_type", skills_type);
			params.put("sa_citizen", sa_citizen);
			params.put("commencement_completion_date", commencement_completion_date);
			params.put("physical_addresses", physical_addresses);
			params.put("training_intervention", training_intervention);
			params.put("funded_by_merseta", funded_by_merseta);
			params.put("isHavingHostEmployer", isHavingHostEmployer);
			params.put("isHavingHostEmployer", isHavingHostEmployer);
			params.put("live_in_urban", liveInUrban);
			params.put("learner", learner);
			params.put("leadCompany", company);
			params.put("leadCompanySETA", leadCompanySETA);
			params.put("isLeadComWorkplaceApproved", isLeadComWorkplaceApproved);
			params.put("leadCompanyContactPerson", leadCompanyContactPerson);
			params.put("leadCompanyContactSupervisor", leadCompanyContactSupervisor);
			params.put("hostCompany", hostCompany);
			params.put("isHostCompWorkplaceApproved", isHostCompWorkplaceApproved);
			params.put("hostCompanyContactPerson", hostCompanyContactPerson);
			params.put("skillDevelopmentProvider", trainingProvider);
			params.put("skillDevelopmentProviderContactPerson", skillDevelopmentProviderContactPerson);
			params.put("isMinor", requireGaurdian);
			params.put("employer", company);
			if (requireGaurdian) {
				Users legalGaurdian = learner.getLegalGaurdian();
				params.put("parentGuadian", legalGaurdian);
			}
			params.put("txt", removeHTMCode(txt));
			JasperService.addLogo(params);
			js.createReportFromDBtoServletOutputStream("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT.jasper", params,"document.pdf");
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}




	public String getlNumber() {
		return lNumber;
	}




	public void setlNumber(String lNumber) {
		this.lNumber = lNumber;
	}





	public Integer getFinYear() {
		return finYear;
	}





	public void setFinYear(Integer finYear) {
		this.finYear = finYear;
	}
	
	public void downloadForms() {
		AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
		
		TrainingProviderVerficationService trainingProviderVerficationService = new TrainingProviderVerficationService();
		
		
		try {
			TrainingProviderVerfication entity = trainingProviderVerficationService.findByKey(25L); 
			//TrainingProviderVerfication entity = trainingProviderVerficationService.findByKey(24L); 
			List<AuditorMonitorReview>mainList = auditorMonitorReviewService.findByTargetKeyAndClass(TrainingProviderVerfication.class.getName(), entity.getId());
			//List<AuditorMonitorReview>mainList = auditorMonitorReviewService.findByTargetKeyAndClass(TrainingProviderVerfication.class.getName(), entity.getTrainingProviderVerficationParent().getId());
			Users activeUser = userService.findByKey(10512L);
			
			//trainingProviderVerficationService.downloadLearnerModerationReport(mainList, entity, activeUser);
			
			//trainingProviderVerficationService.downloadCompanyModerationReport(mainList, entity, activeUser);			
			
			//trainingProviderVerficationService.sendAprovalMail(entity, activeUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void downloadForm() {
		Users user = new Users();
		MgVerification entity =  new MgVerification();
		ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
		
		
		MgVerificationService mgVerificationService = new MgVerificationService();
		
		try {
			int count=0;
			for(RejectReasons rs:reasonsService.allRejectReasons())
			{
				selectedRejectReason.add(rs);
				if(count>5)
				{
					break;
				}
				count++;
			}
			entity = mgVerificationService.findByKey(1492L);
			user = userService.findByKey(10512L);
			mgVerificationService.sendApprovalEmailAndLetter(user, entity);
			//mgVerificationService.sendRejectEmailAndLetter(user, entity, selectedRejectReason);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadForm3() {
		WorkPlaceApproval workPlaceApproval = new WorkPlaceApproval();
		Users user = new Users();
		Tasks tasks = new Tasks();
		WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();		
		try {
			user = userService.findByKey(10512L);
			tasks.setActionUser(user);
			workPlaceApproval = workPlaceApprovalService.findByKey(26L);
			workPlaceApprovalService.sendApprovalEmailTest(workPlaceApproval, tasks);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadForm4() {
		SkillsRegistration skillsRegistration = new SkillsRegistration();
		Users user = new Users();
		Tasks tasks = new Tasks();
		SkillsRegistrationService skillsRegistrationService = new SkillsRegistrationService();		
		try {
			user = userService.findByKey(10512L);
			tasks.setActionUser(user);
			skillsRegistration = skillsRegistrationService.findByKey(26L);
			skillsRegistrationService.sendLPM_TP_021_ApprovalEmail(skillsRegistration, user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadAuditForms() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		try {
			
			UsersService usersService=new UsersService();
			CompanyService companyService = new CompanyService();
			Users user = usersService.findByKey(24L);
			Company company = companyService.findByKey(27468L);
			Address address = AddressService.instance().findByKey(16L);
			//System.out.println(user.getFirstName());
		
			params.put("user", user);
			params.put("company", company);
			params.put("address", address);
			params.put("String", "01 April 2018");
			params.put("regional_manager", "Patrick Mthombeni");
			params.put("title", "Mr");
			JasperService.addLogo(params);
			//js.createReportFromDBtoServletOutputStream("ETQ-TP-050-OutcomeMonitoringSite.jasper", params,"ETQ-TP-050document.pdf");
			js.createReportFromDBtoServletOutputStream("ETQ-TP-052-OutcomeAuditSiteVisit.jasper", params,"ETQ-TP-052document.pdf");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sentMandatoryGrantAppealOutcomeEmail()  {
		try {
			UsersService usersService=new UsersService();
			CompanyService companyService = new CompanyService();
			Users user = usersService.findByKey(24L);
			Company company = companyService.findByKey(27468L);
			Address address = AddressService.instance().findByKey(16L);
			company.setResidentialAddress(address);
	
			Date submissiondate = user.getCreateDate();
			Date appealsubmissiondate = user.getCreateDate();
			ArrayList<RejectReasons> rejectReasons=new ArrayList<>();
			
			int count=0;
			for(RejectReasons rs:reasonsService.allRejectReasons())
			{
				rejectReasons.add(rs);
				if(count>1)
				{
					break;
				}
				count++;
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			JasperService js=new JasperService();
			JasperService.addLogo(params);
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
			params.put("submissiondate", sdf.format(submissiondate));
			params.put("appealsubmissiondate", sdf.format(appealsubmissiondate));
			params.put("company_id", company.getId());
			params.put("user_id", user.getId());
			params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
			
			js.createReportFromDBtoServletOutputStream("CSD-TP-MANDATORY GRANT NON-APPROVAL.jasper", params,"MANDATORY GRANT APPLICATION OUTCOME.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	public void downLoadWorkplaceApprovalForm()  {
		try {			
			WorkPlaceApprovalService wpaService = new WorkPlaceApprovalService();
			WorkPlaceApproval registration = wpaService.findByKey(30L);
			wpaService.downloadVTwo(registration);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
//*********************************************************************************************************************************************
	
	
	public void tesstScheduler() {
		try {
			if (companyLearnersLostTimeService == null) {
				companyLearnersLostTimeService = new CompanyLearnersLostTimeService();
			}
			companyLearnersLostTimeService.runLearneAggrementCheck();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
			logger.fatal(e);
			//GenericUtility.mailError("Report Generation Schedule Completed With Errors (reportGenerationService.runReportgeneration())", e.getMessage());
		}	
	}

}
