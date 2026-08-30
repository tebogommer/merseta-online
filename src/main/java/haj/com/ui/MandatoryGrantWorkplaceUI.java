package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantWorkplace;
import haj.com.entity.Sites;
import haj.com.entity.datamodel.MandatoryGrantWorkplaceDatamodel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MandatoryGrantWorkplaceService;
import haj.com.service.SitesService;

@ViewScoped
@ManagedBean(name = "mandatorygrantworkpalceUI")
public class MandatoryGrantWorkplaceUI extends AbstractUI {

	private MandatoryGrantWorkplaceService         service      = new MandatoryGrantWorkplaceService();
	private SitesService                           sitesService = new SitesService();
	private List<MandatoryGrantWorkplace>          mandatorygrantworkpalceList;
	private List<MandatoryGrantWorkplace>          mandatorygrantworkpalcefilteredList;
	private MandatoryGrantWorkplace                mandatorygrantworkpalce;
	private MandatoryGrant                         mandatorygrant;
	private LazyDataModel<MandatoryGrantWorkplace> dataModel;
	private List<Sites>                            trainingsites;

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
		prepareNew();
		mandatorygrantworkpalceInfo();
	}

	public void mandatorygrantworkpalceInfo() {
		dataModel = new MandatoryGrantWorkplaceDatamodel();
	}

	public void mandatorygrantworkpalceInsert() {
		try {
			service.create(this.mandatorygrantworkpalce);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			mandatorygrantworkpalceInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void mandatorygrantworkpalceUpdate() {
		try {
			service.update(this.mandatorygrantworkpalce);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			mandatorygrantworkpalceInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void mandatorygrantworkpalceDelete() {
		try {
			service.delete(this.mandatorygrantworkpalce);
			prepareNew();
			mandatorygrantworkpalceInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareNew() {
		mandatorygrantworkpalce = new MandatoryGrantWorkplace();
		if (mandatorygrant != null) {
			mandatorygrantworkpalce.setMandatoryGrant(mandatorygrant);
			findSites();
		}
	}

	public void findSites() {
		try {
			trainingsites = sitesService.findByCompanyTraining(mandatorygrant.getWsp().getCompany());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MandatoryGrantWorkplace> complete(String desc) {
		List<MandatoryGrantWorkplace> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<MandatoryGrantWorkplace> getMandatoryGrantWorkplaceList() {
		return mandatorygrantworkpalceList;
	}

	public void setMandatoryGrantWorkplaceList(List<MandatoryGrantWorkplace> mandatorygrantworkpalceList) {
		this.mandatorygrantworkpalceList = mandatorygrantworkpalceList;
	}

	public MandatoryGrantWorkplace getMandatorygrantworkpalce() {
		return mandatorygrantworkpalce;
	}

	public void setMandatorygrantworkpalce(MandatoryGrantWorkplace mandatorygrantworkpalce) {
		this.mandatorygrantworkpalce = mandatorygrantworkpalce;
	}

	public List<MandatoryGrantWorkplace> getMandatoryGrantWorkplacefilteredList() {
		return mandatorygrantworkpalcefilteredList;
	}

	public void setMandatoryGrantWorkplacefilteredList(List<MandatoryGrantWorkplace> mandatorygrantworkpalcefilteredList) {
		this.mandatorygrantworkpalcefilteredList = mandatorygrantworkpalcefilteredList;
	}

	public LazyDataModel<MandatoryGrantWorkplace> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrantWorkplace> dataModel) {
		this.dataModel = dataModel;
	}

	public MandatoryGrant getMandatorygrant() {
		return mandatorygrant;
	}

	public void setMandatorygrant(MandatoryGrant mandatorygrant) {
		this.mandatorygrant = mandatorygrant;
	}

	public List<Sites> getTrainingsites() {
		return trainingsites;
	}

	public void setTrainingsites(List<Sites> trainingsites) {
		this.trainingsites = trainingsites;
	}

}
