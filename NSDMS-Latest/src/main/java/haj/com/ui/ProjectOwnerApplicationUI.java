package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.Wsp;
import haj.com.entity.datamodel.ProjectOwnerWspDatamodel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.PaymentRequestService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.WspDGService;

@ManagedBean(name = "projectownerapplicationUI")
@ViewScoped
public class ProjectOwnerApplicationUI extends AbstractUI {

	private ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
	private PaymentRequestService            paymentRequestService            = new PaymentRequestService();
	private WspDGService                     service                          = new WspDGService();
	private LazyDataModel<Wsp>               dataModel;
	private ProjectImplementationPlan        projectImplementationPlan;
	private Wsp                              wsp;

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
		wsplocationsInfo();
	}

	public void insertWSP() {
		try {
			service.create(wsp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void wsplocationsInfo() {
		dataModel = new ProjectOwnerWspDatamodel(getSessionUI().getActiveUser());
	}

	public List<ProjectImplementationPlan> getDeliverables() {
		List<ProjectImplementationPlan> deliverables = new ArrayList<>();
		try {
			deliverables = projectImplementationPlanService.findByWsp(wsp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return deliverables;
	}

	public void goToWspForCompany() {
		try {
			super.redirect("/pages/externalparty/wsp/reviewapplicationdg.jsf?idW=" + this.wsp.getWspGuid());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void makeRequest() {
		try {
			paymentRequestService.requesteWorkflow(projectImplementationPlan, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public LazyDataModel<Wsp> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Wsp> dataModel) {
		this.dataModel = dataModel;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public ProjectImplementationPlan getProjectImplementationPlan() {
		return projectImplementationPlan;
	}

	public void setProjectImplementationPlan(ProjectImplementationPlan projectImplementationPlan) {
		this.projectImplementationPlan = projectImplementationPlan;
	}
}
