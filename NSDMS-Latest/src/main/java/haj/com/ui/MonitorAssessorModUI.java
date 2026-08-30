package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AssessorModeratorApplicationService;

@ManagedBean(name = "monitorAssessorModUI")
@ViewScoped
public class MonitorAssessorModUI extends AbstractUI {

	/* Entity Level */
	private AssessorModeratorApplication assessorModeratorApplication = null;

	/* Service Level */
	private AssessorModeratorApplicationService service = new AssessorModeratorApplicationService();

	/* Lazy Data Models */
	private LazyDataModel<AssessorModeratorApplication> assessorApplicationsDataModel;
	private LazyDataModel<AssessorModeratorApplication> moderatorApplicationsDataModel;
	
	/* Booleans */
	private boolean filterByApprovedAssessorApplications = false;
	private boolean filterByApprovedModeratorApplications = false;

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
		allAssessorApplicationInfo();
		allModeratorApplicationInfo();
	}

	public void allAssessorApplicationInfo() {
		assessorApplicationsDataModel = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (Boolean.TRUE.equals(filterByApprovedAssessorApplications)) {
						retorno = service.sortAndFilterWhereAMInfoApprovedAndByApplicationType(first, pageSize, sortField, sortOrder, filters, AssessorModeratorAppTypeEnum.getAssessorValues());
						assessorApplicationsDataModel.setRowCount(service.countWhereAMInfoApprovedAndByApplicationType(filters,AssessorModeratorAppTypeEnum.getAssessorValues()));
					} else {
						retorno = service.sortAndFilterWhereAMInfoApplicationType(first, pageSize, sortField, sortOrder, filters, AssessorModeratorAppTypeEnum.getAssessorValues());
						assessorApplicationsDataModel.setRowCount(service.countWhereAMInfoApplicationType(filters,AssessorModeratorAppTypeEnum.getAssessorValues()));
					}

				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}
			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void allModeratorApplicationInfo() {
		moderatorApplicationsDataModel = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (Boolean.TRUE.equals(filterByApprovedModeratorApplications)) {
						retorno = service.sortAndFilterWhereAMInfoApprovedAndByApplicationType(first, pageSize, sortField, sortOrder, filters, AssessorModeratorAppTypeEnum.getModeratorValues() );
						moderatorApplicationsDataModel.setRowCount(service.countWhereAMInfoApprovedAndByApplicationType(filters, AssessorModeratorAppTypeEnum.getModeratorValues()));
					} else {
						retorno = service.sortAndFilterWhereAMInfoApplicationType(first, pageSize, sortField, sortOrder, filters, AssessorModeratorAppTypeEnum.getModeratorValues() );
						moderatorApplicationsDataModel.setRowCount(service.countWhereAMInfoApplicationType(filters,AssessorModeratorAppTypeEnum.getModeratorValues() ));
					}

				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}
			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void reSendStatementOfQual(){
		try {
			service.sendStatementOfQualificationsError(assessorModeratorApplication);
			assessorModeratorApplication = null;
			addInfoMessage("Message Sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void reSendStatementOfQualToAllApprovedAssessorApplications(){
		try {
			service.resendStatmentOfQualificationToAllApprovedApplicationsByTypeList(AssessorModeratorAppTypeEnum.getAssessorValues());
			assessorModeratorApplication = null;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void reSendStatementOfQualToAllApprovedModeratorApplications(){
		try {
			service.resendStatmentOfQualificationToAllApprovedApplicationsByTypeList(AssessorModeratorAppTypeEnum.getModeratorValues());
			assessorModeratorApplication = null;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* getters and setters */
	public AssessorModeratorApplication getAssessorModeratorApplication() {
		return assessorModeratorApplication;
	}

	public void setAssessorModeratorApplication(AssessorModeratorApplication assessorModeratorApplication) {
		this.assessorModeratorApplication = assessorModeratorApplication;
	}
	
	public LazyDataModel<AssessorModeratorApplication> getModeratorApplicationsDataModel() {
		return moderatorApplicationsDataModel;
	}

	public void setModeratorApplicationsDataModel(
			LazyDataModel<AssessorModeratorApplication> moderatorApplicationsDataModel) {
		this.moderatorApplicationsDataModel = moderatorApplicationsDataModel;
	}

	public LazyDataModel<AssessorModeratorApplication> getAssessorApplicationsDataModel() {
		return assessorApplicationsDataModel;
	}

	public void setAssessorApplicationsDataModel(
			LazyDataModel<AssessorModeratorApplication> assessorApplicationsDataModel) {
		this.assessorApplicationsDataModel = assessorApplicationsDataModel;
	}

	public boolean isFilterByApprovedAssessorApplications() {
		return filterByApprovedAssessorApplications;
	}

	public void setFilterByApprovedAssessorApplications(boolean filterByApprovedAssessorApplications) {
		this.filterByApprovedAssessorApplications = filterByApprovedAssessorApplications;
	}

	public boolean isFilterByApprovedModeratorApplications() {
		return filterByApprovedModeratorApplications;
	}

	public void setFilterByApprovedModeratorApplications(boolean filterByApprovedModeratorApplications) {
		this.filterByApprovedModeratorApplications = filterByApprovedModeratorApplications;
	}

}