package haj.com.wsp.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.GrantBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Blank;
import haj.com.entity.Employees;
import haj.com.entity.EmployeesTraining;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.EmployeesService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantUI.
 */
@ManagedBean(name = "discretionarygrantpipUI")
@ViewScoped
public class DiscretionaryGrantPIPUI extends AbstractUI {

	private ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
	private List<ProjectImplementationPlan>  projectimplementationplanList    = null;
	private boolean                          doneProjectimplementationplan    = true;

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

	private void runInit() throws Exception {
		findPIP();
	}

	public void findPIP() {
		try {
			if (getSessionUI().getWspSession() != null) {
				projectimplementationplanList = projectImplementationPlanService.findByWsp(getSessionUI().getWspSession());
			} else {
				projectimplementationplanList = new ArrayList<>();
			}
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void completePIP() {
		try {
			if (projectimplementationplanList != null && !projectimplementationplanList.isEmpty()) projectImplementationPlanService.create(projectimplementationplanList);
			addInfoMessage("Update Successful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public double getRemaingAmount() {
		double remaining = getSessionUI().getWspSession().getApprovedAmount() == null ? (getSessionUI().getWspSession().getEstimatedOverallProjectCost() == null ? 0.0 : getSessionUI().getWspSession().getEstimatedOverallProjectCost()) : getSessionUI().getWspSession().getApprovedAmount();
		for (ProjectImplementationPlan projectImplementationPlan : projectimplementationplanList) {
			if (projectImplementationPlan.getTotalAwardAmount() != null) {
				remaining -= projectImplementationPlan.getTotalAwardAmount().doubleValue();
			}
		}
		return remaining;
	}

	public void checkRemaingAmount(ProjectImplementationPlan row) {
		double remaining = getSessionUI().getWspSession().getApprovedAmount();
		for (ProjectImplementationPlan projectImplementationPlan : projectimplementationplanList) {
			if (projectImplementationPlan.getTotalAwardAmount() != null) {
				remaining -= projectImplementationPlan.getTotalAwardAmount().doubleValue();
			}
		}
		if (remaining < 0) {
			row.setTotalAwardAmount(BigDecimal.ZERO);
		}

	}

	public void addNewPIP() {
		try {
			ProjectImplementationPlan projectImplementationPlan = new ProjectImplementationPlan();
			projectImplementationPlan.setWsp(getSessionUI().getWspSession());
			projectImplementationPlanService.create(projectImplementationPlan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ProjectImplementationPlan> getProjectimplementationplanList() {
		return projectimplementationplanList;
	}

	public void setProjectimplementationplanList(List<ProjectImplementationPlan> projectimplementationplanList) {
		this.projectimplementationplanList = projectimplementationplanList;
	}

	public boolean isDoneProjectimplementationplan() {
		return doneProjectimplementationplan;
	}

	public void setDoneProjectimplementationplan(boolean doneProjectimplementationplan) {
		this.doneProjectimplementationplan = doneProjectimplementationplan;
	}
}
