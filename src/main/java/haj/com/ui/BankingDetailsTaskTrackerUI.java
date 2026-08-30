package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.BankingDetails;
import haj.com.entity.Blank;
import haj.com.entity.Tasks;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.BankingDetailsService;
import haj.com.service.TasksService;


@ManagedBean(name = "bankingDetailsTaskTrackerUI")
@ViewScoped
public class BankingDetailsTaskTrackerUI extends AbstractUI {

	/* Entity Level */
	private BankingDetails selectedBankingDetails = null;
	
	/* Service Level */
	private BankingDetailsService bankingDetailsService = null;
	
	/* Lazy Data Model lists */
	private LazyDataModel<BankingDetails> dataModelBankingDetails;
	
	/* Array Lists */
	private List<Tasks> tasksList = null;
	
    /**
     * Inits the.
     */
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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		populateServiceLevels();
		dataModelBankingDetailsInfo();
	}

	private void populateServiceLevels() {
		if (bankingDetailsService == null) {
			bankingDetailsService = new BankingDetailsService();
		}
	}
	
	public void dataModelBankingDetailsInfo() {
		dataModelBankingDetails = new LazyDataModel<BankingDetails>() {
			private static final long serialVersionUID = 1L;
			private List<BankingDetails> bankingDetailsList = new ArrayList<>();
			@Override
			public List<BankingDetails> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
//					bankingDetailsList = companyService.allCompany2(Company.class, first, pageSize, sortField, sortOrder, filters);
//					dataModelBankingDetails.setRowCount(companyService.count(Company.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return bankingDetailsList;
			}
			@Override
			public Object getRowKey(BankingDetails object) {
				// TODO Auto-generated method stub
				return object.getId();
			}
			@Override
			public BankingDetails getRowData(String rowKey) {
				for (BankingDetails obj : bankingDetailsList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void locateTasksForBankningDetails(){
		try {
			tasksList = new ArrayList<>();
			tasksList = TasksService.instance().findTasksByTypeAndKey(selectedBankingDetails.getClass().getName(), selectedBankingDetails.getId());
			addInfoMessage("Tasks Found: " + tasksList.size());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* getters and setters */
	public LazyDataModel<BankingDetails> getDataModelBankingDetails() {
		return dataModelBankingDetails;
	}

	public void setDataModelBankingDetails(LazyDataModel<BankingDetails> dataModelBankingDetails) {
		this.dataModelBankingDetails = dataModelBankingDetails;
	}

	public BankingDetails getSelectedBankingDetails() {
		return selectedBankingDetails;
	}

	public void setSelectedBankingDetails(BankingDetails selectedBankingDetails) {
		this.selectedBankingDetails = selectedBankingDetails;
	}

	public List<Tasks> getTasksList() {
		return tasksList;
	}

	public void setTasksList(List<Tasks> tasksList) {
		this.tasksList = tasksList;
	}

	
}
