package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Wsp;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.WspService;

@ManagedBean(name = "monitorTrainingProvidersStatusUI")
@ViewScoped
public class MonitorTrainingProvidersStatusUI extends AbstractUI {

	/* Entity Level */
	private TrainingProviderApplication trainingProviderApplication = null;

	/* Service Level */
	private TrainingProviderApplicationService service = new TrainingProviderApplicationService();

	/* Lazy Data Models */
	private LazyDataModel<TrainingProviderApplication> dataModelTrainingProviderApplication;

	/* Booleans */
	private boolean filterByApprovedApplications = false;

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
		dataModelTrainingProviderApplicationInfo();
	}

	public void dataModelTrainingProviderApplicationInfo() {
		dataModelTrainingProviderApplication = new LazyDataModel<TrainingProviderApplication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderApplication> retorno = new ArrayList<TrainingProviderApplication>();

			@Override
			public List<TrainingProviderApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.sortAndFilterWhereTPWhere(first, pageSize, sortField, sortOrder, filters);
					dataModelTrainingProviderApplication.setRowCount(service.countTPWhere(filters));					
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(TrainingProviderApplication obj) {
				return obj.getId();
			}
			@Override
			public TrainingProviderApplication getRowData(String rowKey) {
				for (TrainingProviderApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void prepare() {
		
	}
	
	public void updateProvider(){
		try {
			service.update(trainingProviderApplication);
			trainingProviderApplication = null;
			addInfoMessage("Status updated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void reSendStatementOfQual(){
		try {
			service.resendStatmentOfQualification(trainingProviderApplication);
			trainingProviderApplication = null;
			addInfoMessage("Message Sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void reSendStatementOfQualToAllApprovedProviderApplications(){
		try {
			service.resendStatmentOfQualificationToAllApprovedApplications();
			trainingProviderApplication = null;
			addInfoMessage("Message Sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* getters and setters */
	public boolean isFilterByApprovedApplications() {
		return filterByApprovedApplications;
	}

	public void setFilterByApprovedApplications(boolean filterByApprovedApplications) {
		this.filterByApprovedApplications = filterByApprovedApplications;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public LazyDataModel<TrainingProviderApplication> getDataModelTrainingProviderApplication() {
		return dataModelTrainingProviderApplication;
	}

	public void setDataModelTrainingProviderApplication(
			LazyDataModel<TrainingProviderApplication> dataModelTrainingProviderApplication) {
		this.dataModelTrainingProviderApplication = dataModelTrainingProviderApplication;
	}

}