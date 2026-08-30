package haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.Wsp;
import haj.com.entity.datamodel.WspDatamodel;
import haj.com.entity.datamodel.WspNotRecommendedDatamodel;
import haj.com.entity.datamodel.WspProjectOwnerDatamodel;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspDGService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "projectreviewUI")
@ViewScoped
public class ProjectReviewUI extends AbstractUI {

	private WspDGService       service = new WspDGService();
	private LazyDataModel<Wsp> dataModel;
	private LazyDataModel<Wsp> dataModelPending;
	private LazyDataModel<Wsp> dataModelCompleted;
	private Wsp                wsp;

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
			e.printStackTrace();
		}
	}

	public void wsplocationsInfo() {
		dataModel          = new WspNotRecommendedDatamodel();
		dataModelPending   = new WspProjectOwnerDatamodel(getSessionUI().getActiveUser());
		dataModelCompleted = new WspDatamodel();
	}

	public void approveRecommendation() {
		try {
			service.approveRecommendation(getSessionUI().getActiveUser());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void goToWspForCompany() {
		try {
			super.redirect("/pages/externalparty/wsp/reviewapplicationdg.jsf?idW=" + this.wsp.getWspGuid());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public LazyDataModel<Wsp> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Wsp> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<Wsp> getDataModelPending() {
		return dataModelPending;
	}

	public void setDataModelPending(LazyDataModel<Wsp> dataModelPending) {
		this.dataModelPending = dataModelPending;
	}

	public LazyDataModel<Wsp> getDataModelCompleted() {
		return dataModelCompleted;
	}

	public void setDataModelCompleted(LazyDataModel<Wsp> dataModelCompleted) {
		this.dataModelCompleted = dataModelCompleted;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}
}
