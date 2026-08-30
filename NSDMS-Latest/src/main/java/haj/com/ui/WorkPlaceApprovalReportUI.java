package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.WorkplaceApprovalBean;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.lookup.LegacyEmployerWA2WorkplaceService;

@ManagedBean(name = "workPlaceApprovalReportUI")
@ViewScoped
public class WorkPlaceApprovalReportUI extends AbstractUI {

	/** Lazy Load Lists */
	private LazyDataModel<WorkPlaceApproval> dataModel;
	private LazyDataModel<LegacyEmployerWA2Workplace> dataModelLegacy;
	private List<WorkplaceApprovalBean> workplaceApprovalBeanList;
	
	/** Service Level */
	private WorkPlaceApprovalService service = new WorkPlaceApprovalService();
	private LegacyEmployerWA2WorkplaceService serviceLegacy = new LegacyEmployerWA2WorkplaceService();

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

	/**
	 * Initialize method to get all WorkPlaceApproval and prepare a for a create
	 * of a new WorkPlaceApproval
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApproval
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		workplaceApprovalBeanList = service.populateWorkplaceApprovalBean();
		// workplaceapprovalInfo();
		// legacyemployerwa2workplaceInfo();
	}


	/**
	 * Get all WorkPlaceApproval for data table
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApproval
	 */
	public void workplaceapprovalInfo() {
		dataModel = new LazyDataModel<WorkPlaceApproval>() {
			private static final long serialVersionUID = 1L;
			private List<WorkPlaceApproval> retorno = new ArrayList<WorkPlaceApproval>();
			@Override
			public List<WorkPlaceApproval> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allWorkPlaceApproval(WorkPlaceApproval.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(WorkPlaceApproval.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkPlaceApproval obj) {
				return obj.getId();
			}
			@Override
			public WorkPlaceApproval getRowData(String rowKey) {
				for (WorkPlaceApproval obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	/**
	 * Get all LegacyEmployerWA2Workplace for data table
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Workplace
	 */
	public void legacyemployerwa2workplaceInfo() {
		dataModelLegacy = new LazyDataModel<LegacyEmployerWA2Workplace>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyEmployerWA2Workplace> retorno = new ArrayList<LegacyEmployerWA2Workplace>();
			@Override
			public List<LegacyEmployerWA2Workplace> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if(filters == null) {
						filters = new HashMap<>();
					}	
					retorno = serviceLegacy.allLegacyEmployerWA2Workplace(LegacyEmployerWA2Workplace.class, first, pageSize, sortField, sortOrder, filters);
					for(LegacyEmployerWA2Workplace legacyEmployerWA2Workplace:retorno) {
						serviceLegacy.populateData(legacyEmployerWA2Workplace);
					}
					dataModelLegacy.setRowCount(serviceLegacy.count(LegacyEmployerWA2Workplace.class, filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyEmployerWA2Workplace obj) {
				return obj.getId();
			}
			@Override
			public LegacyEmployerWA2Workplace getRowData(String rowKey) {
				for (LegacyEmployerWA2Workplace obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void downloadWorkplaceApprovalReport(){
		try {
			service.downloadWorkplaceApprovalReport();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public LazyDataModel<WorkPlaceApproval> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkPlaceApproval> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<LegacyEmployerWA2Workplace> getDataModelLegacy() {
		return dataModelLegacy;
	}

	public void setDataModelLegacy(LazyDataModel<LegacyEmployerWA2Workplace> dataModelLegacy) {
		this.dataModelLegacy = dataModelLegacy;
	}

	public List<WorkplaceApprovalBean> getWorkplaceApprovalBeanList() {
		return workplaceApprovalBeanList;
	}

	public void setWorkplaceApprovalBeanList(List<WorkplaceApprovalBean> workplaceApprovalBeanList) {
		this.workplaceApprovalBeanList = workplaceApprovalBeanList;
	}
}
