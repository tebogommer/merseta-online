package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.WspFirmSubmissionBean;
import haj.com.entity.QmrFinYears;
import haj.com.entity.lookup.FinancialYears;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrFinYearsService;
import haj.com.service.WspService;
import haj.com.service.lookup.FinancialYearsService;

/**
 * The Class FirmGrantsSubmissionUI.
 */
@ManagedBean(name = "firmGrantsSubmissionUI")
@ViewScoped
public class FirmGrantsSubmissionUI extends AbstractUI {
	
	/* Entity Level */
	private FinancialYears selectedFinancialYears = null;
	private QmrFinYears selectedQuarter = null;
	
	/* Service Level */
	private FinancialYearsService financialYearsService = new FinancialYearsService();
	private QmrFinYearsService qmrFinYearsService = new QmrFinYearsService();
	private WspService wspService = new WspService();
	
	/* Lazy Data Models */
	private LazyDataModel<FinancialYears> financialYearsDataModel;
	private LazyDataModel<QmrFinYears> qmrFinYearsDataModel;
	
	/* Array Lists */
	private List<WspFirmSubmissionBean> dataList = new ArrayList<>();
	
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

	private void runInit() throws Exception {	
		selectedFinancialYearsInfo();
	}
	
	private void selectedFinancialYearsInfo() {
		financialYearsDataModel = new LazyDataModel<FinancialYears>() {
			private static final long serialVersionUID = 1L;
			private List<FinancialYears> retorno = new ArrayList<>();
			@Override
			public List<FinancialYears> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = financialYearsService.allFinancialYears(FinancialYears.class, first, pageSize, sortField, sortOrder, filters);
					financialYearsDataModel.setRowCount(financialYearsService.count(FinancialYears.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(FinancialYears obj) {
				return obj.getId();
			}
			@Override
			public FinancialYears getRowData(String rowKey) {
				for (FinancialYears obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	private void clearQuarterAndReportInfo(){
		selectedQuarter = null;
		dataList.clear();
	}
	
	public void selectFinancialYear(){
		try {
			clearQuarterAndReportInfo();
			qmrFinYearsDataModelInfo();
			addInfoMessage("Financial Year Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void qmrFinYearsDataModelInfo() {
		qmrFinYearsDataModel = new LazyDataModel<QmrFinYears>() {
			private static final long serialVersionUID = 1L;
			private List<QmrFinYears> retorno = new ArrayList<>();
			@Override
			public List<QmrFinYears> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null || sortField.equals(" ")) {
						sortField = "finYearQuarters";
						sortOrder = SortOrder.ASCENDING;
					}
					if (selectedFinancialYears != null) {
						retorno = qmrFinYearsService.allQmrFinYearsByFinYear(first, pageSize, sortField, sortOrder, filters, selectedFinancialYears.getStartYear());
						qmrFinYearsDataModel.setRowCount(qmrFinYearsService.countAllQmrFinYearsByFinYear(filters));
					} 
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(QmrFinYears obj) {
				return obj.getId();
			}
			@Override
			public QmrFinYears getRowData(String rowKey) {
				for (QmrFinYears obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void generateFirmSubmissionReport(){
		try {
			dataList.clear();
			dataList = wspService.populateGrantSubmissionNsdpReportByQmrQuarter(selectedFinancialYears.getGrantFocusYear(), selectedQuarter);
			if (dataList.isEmpty()) {
				addWarningMessage("Action Complete, No Results Found.");
			} else {
				addInfoMessage("Action Complete");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and Setters */
	public FinancialYears getSelectedFinancialYears() {
		return selectedFinancialYears;
	}

	public void setSelectedFinancialYears(FinancialYears selectedFinancialYears) {
		this.selectedFinancialYears = selectedFinancialYears;
	}

	public LazyDataModel<FinancialYears> getFinancialYearsDataModel() {
		return financialYearsDataModel;
	}

	public void setFinancialYearsDataModel(LazyDataModel<FinancialYears> financialYearsDataModel) {
		this.financialYearsDataModel = financialYearsDataModel;
	}

	public QmrFinYears getSelectedQuarter() {
		return selectedQuarter;
	}

	public void setSelectedQuarter(QmrFinYears selectedQuarter) {
		this.selectedQuarter = selectedQuarter;
	}

	public LazyDataModel<QmrFinYears> getQmrFinYearsDataModel() {
		return qmrFinYearsDataModel;
	}

	public void setQmrFinYearsDataModel(LazyDataModel<QmrFinYears> qmrFinYearsDataModel) {
		this.qmrFinYearsDataModel = qmrFinYearsDataModel;
	}

	public List<WspFirmSubmissionBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<WspFirmSubmissionBean> dataList) {
		this.dataList = dataList;
	}
	
}
