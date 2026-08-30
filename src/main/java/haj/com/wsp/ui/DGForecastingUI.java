package haj.com.wsp.ui;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.ATRReportSummayBean;
import haj.com.bean.DGApplicationSummaryBean;
import haj.com.bean.EmpEmploymentStatusBean;
import haj.com.bean.EmployeeEquityProfileBean;
import haj.com.bean.EmployeeProfileBean;
import haj.com.bean.PivotalTrainingReportBean;
import haj.com.bean.WSPReportSummayBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.DgAllocation;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.Doc;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.ImpactOfStaffTraining;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.SDFCompanyHistory;
import haj.com.entity.Sites;
import haj.com.entity.TempSignoff;
import haj.com.entity.TrainingComittee;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspChecklist;
import haj.com.entity.WspDispute;
import haj.com.entity.WspSignoff;
import haj.com.entity.WspSkillsRequirements;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.DGYear;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.OrganisedLabourUnion;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Title;
import haj.com.exceptions.ValidationErrorException;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.BankingDetailsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DgAllocationParentService;
import haj.com.service.DgAllocationService;
import haj.com.service.DgVerificationService;
import haj.com.service.ExtensionRequestService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.ImpactOfStaffTrainingService;
import haj.com.service.JasperService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.SDFCompanyHistoryService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SitesService;
import haj.com.service.TasksService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.UsersService;
import haj.com.service.WspChecklistService;
import haj.com.service.WspDGService;
import haj.com.service.WspDisputeService;
import haj.com.service.WspSkillsRequirementsService;
import haj.com.service.lookup.DGYearLearningProgramsService;
import haj.com.service.lookup.DGYearService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.TitleService;
import haj.com.ui.SearchCompanyUI;
import haj.com.ui.SearchUserPassportOrIdUI;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

// TODO: Auto-generated Javadoc
/**
 * The Class InitiatewspUI.
 */
@ManagedBean(name = "dgforecastingUI")
@ViewScoped
public class DGForecastingUI extends AbstractUI {
	private List<DgAllocation>  forcastAllocationList;
	private DgAllocationService dgAllocationService = new DgAllocationService();

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * runInit()
	 * 
	 * @throws Exception
	 */
	private void runInit() throws Exception {
		populateDgAllocationForcatByWsp();
	}

	public void populateDgAllocationForcatByWsp() {
		try {
			if (getSessionUI().getWspSession() != null) {
				forcastAllocationList = dgAllocationService.doAllocationForecastReportingByWsp(getSessionUI().getWspSession());
				addInfoMessage("Report Generated");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	public List<DgAllocation> getForcastAllocationList() {
		return forcastAllocationList;
	}

	public void setForcastAllocationList(List<DgAllocation> forcastAllocationList) {
		this.forcastAllocationList = forcastAllocationList;
	}
}
