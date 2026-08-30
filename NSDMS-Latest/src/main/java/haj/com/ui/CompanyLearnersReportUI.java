package haj.com.ui;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

import haj.com.bean.CompanyLearnersBean;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.Learners;
import haj.com.entity.Users;
import haj.com.entity.datamodel.CompanyLearnersDataModel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.DocService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "CompanyLearnersReportUI")
@ViewScoped
public class CompanyLearnersReportUI extends AbstractUI {
	private List<Learners> learnersfilteredList = null;
	private LazyDataModel<CompanyLearners> dataModel;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private CompanyLearners companyLearners;
	private Doc doc;
	private List<CompanyLearnersBean> companyLearnersList;
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
		companyLearnersList=companyLearnersService.generateCompanyLearnerReport();
	}
	
	public void learnersInfo() {
		dataModel = new CompanyLearnersDataModel();
	}
	
	public void prepareCompanyLearners() {
		
	}
	
	public void prepareCompanyLearnersDocuments() {
		try {
			companyLearners.setDocs(DocService.instance().searchByTargetKeyAndClass(CompanyLearners.class.getName(), companyLearners.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
		
	public void companyLearnersUpdate() {
		try {
			companyLearnersService.update(companyLearners);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void companyLearnersUpdateDocument() {
		try {
			doc.setTargetClassTemp(doc.getTargetClass());
			doc.setTargetKeyTemp(doc.getTargetKey());
			doc.setTargetClass(null);
			doc.setTargetKey(null);
			DocService.instance().update(doc);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			prepareCompanyLearnersDocuments();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void startDateFilterData() {
		try {
			calculateEndDate();		
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void calculateEndDate() {
		this.companyLearners.setCompletionDate(GenericUtility.addMonthsToDate(this.companyLearners.getCommencementDate(), this.companyLearners.getInterventionType().getDuration()));
	}
	
	public List<Learners> getLearnersfilteredList() {
		return learnersfilteredList;
	}

	public void setLearnersfilteredList(List<Learners> learnersfilteredList) {
		this.learnersfilteredList = learnersfilteredList;
	}

	public LazyDataModel<CompanyLearners> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearners> dataModel) {
		this.dataModel = dataModel;
	}

	public boolean checkRequireGaurdian(Users user) {
		boolean requireGaurdian = user.getDateOfBirth() != null && GenericUtility.getAge(user.getDateOfBirth()) < 18;		
		return requireGaurdian;		
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<CompanyLearnersBean> getCompanyLearnersList() {
		return companyLearnersList;
	}

	public void setCompanyLearnersList(List<CompanyLearnersBean> companyLearnersList) {
		this.companyLearnersList = companyLearnersList;
	}
}
