package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.DgAllocationStatusReportBean;
import haj.com.entity.DgAllocationParent;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DgAllocationParentService;
import haj.com.service.WspService;

@ManagedBean(name = "dgAllocationStatusReportUI")
@ViewScoped
public class DgAllocationStatusReportUI extends AbstractUI {

	/* Entity Level */
	private DgAllocationParent dgallocationparent = null;
	
	/* Service Level */
	private WspService wspService = new WspService();
	private DgAllocationParentService service = new DgAllocationParentService();
	
	/* Lazy Data Models */
	private LazyDataModel<DgAllocationParent> dataModel;
	
	/* Array Lists */
	private List<Integer> financialYears;
	private List<DgAllocationStatusReportBean> resultsList = new ArrayList<>();
	
	/* Vars */
	private Integer selectedYear;
	private Boolean displayReport;

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
	 * Initialize method to get all DgAllocationParent and prepare a for a
	 * create of a new DgAllocationParent
	 * 
	 * @author TechFinium
	 * @see DgAllocationParent
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		populatesDistinctFinancialYears();
		dgallocationparentInfo();
		resultsList.clear();
		displayReport = false;
	}
	
	public void populatesDistinctFinancialYears() throws Exception {
		financialYears = wspService.findDictinctFinYears();
		if (!financialYears.isEmpty()) {
			selectedYear = financialYears.get(0);
		}
	}

	/**
	 * Get all DgAllocationParent for data table
	 * 
	 * @author TechFinium
	 * @see DgAllocationParent
	 */
	private void dgallocationparentInfo() {
		dataModel = new LazyDataModel<DgAllocationParent>() {
			private static final long serialVersionUID = 1L;
			private List<DgAllocationParent> retorno = new ArrayList<>();
			@Override
			public List<DgAllocationParent> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedYear != null) {
						retorno = service.allDgAllocationParentReportByGrantYear(first, pageSize, sortField, sortOrder, filters, selectedYear);
						setRowCount(service.countAllDgAllocationParentReportByGrantYear( filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			
			@Override
			public Object getRowKey(DgAllocationParent obj) {
				return obj.getId();
			}
			
			@Override
			public DgAllocationParent getRowData(String rowKey) {
				for (DgAllocationParent obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	
	public void selectFinYear(){
		try {
			dgallocationparentInfo();
			addInfoMessage("Filter Applied");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateResults(){
		try {
			resultsList = service.populateNativeAllocationStatusList(selectedYear);
			displayReport = true;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	public DgAllocationParent getDgallocationparent() {
		return dgallocationparent;
	}

	public void setDgallocationparent(DgAllocationParent dgallocationparent) {
		this.dgallocationparent = dgallocationparent;
	}

	public LazyDataModel<DgAllocationParent> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DgAllocationParent> dataModel) {
		this.dataModel = dataModel;
	}

	public List<Integer> getFinancialYears() {
		return financialYears;
	}

	public void setFinancialYears(List<Integer> financialYears) {
		this.financialYears = financialYears;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public Boolean getDisplayReport() {
		return displayReport;
	}

	public void setDisplayReport(Boolean displayReport) {
		this.displayReport = displayReport;
	}

	public List<DgAllocationStatusReportBean> getResultsList() {
		return resultsList;
	}

	public void setResultsList(List<DgAllocationStatusReportBean> resultsList) {
		this.resultsList = resultsList;
	}

}
