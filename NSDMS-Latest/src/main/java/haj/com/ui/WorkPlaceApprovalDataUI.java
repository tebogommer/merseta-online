package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.WorkPlaceApprovalData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkPlaceApprovalDataService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "workplaceapprovaldataUI")
@ViewScoped
public class WorkPlaceApprovalDataUI extends AbstractUI {

	private WorkPlaceApprovalDataService service = new WorkPlaceApprovalDataService();
	private List<WorkPlaceApprovalData> workplaceapprovaldataList = null;
	private List<WorkPlaceApprovalData> workplaceapprovaldatafilteredList = null;
	private WorkPlaceApprovalData workplaceapprovaldata = null;
	private LazyDataModel<WorkPlaceApprovalData> dataModel;
	public CSVUtil csvUtil = new CSVUtil();

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
	 * Initialize method to get all WorkPlaceApprovalData and prepare a for a create
	 * of a new WorkPlaceApprovalData
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApprovalData
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		workplaceapprovaldataInfo();
	}

	/**
	 * Get all WorkPlaceApprovalData for data table
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApprovalData
	 */
	public void workplaceapprovaldataInfo() {

		dataModel = new LazyDataModel<WorkPlaceApprovalData>() {

			private static final long serialVersionUID = 1L;
			private List<WorkPlaceApprovalData> retorno = new ArrayList<WorkPlaceApprovalData>();

			@Override
			public List<WorkPlaceApprovalData> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allWorkPlaceApprovalData(WorkPlaceApprovalData.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(WorkPlaceApprovalData.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WorkPlaceApprovalData obj) {
				return obj.getId();
			}

			@Override
			public WorkPlaceApprovalData getRowData(String rowKey) {
				for (WorkPlaceApprovalData obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert WorkPlaceApprovalData into database
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApprovalData
	 */
	public void workplaceapprovaldataInsert() {
		try {
			service.create(this.workplaceapprovaldata);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplaceapprovaldataInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void handleFileUpload(FileUploadEvent event) {
		try {


			List<WorkPlaceApprovalData> workPlaceApprovalDatas = (List<WorkPlaceApprovalData>) (List<?>) csvUtil.getObjects(WorkPlaceApprovalData.class, event.getFile().getInputstream(), ";");
			
			service.loadLookups(workPlaceApprovalDatas);
			
			super.addInfoMessage("Upload Succesful");
		} catch (ConstraintViolationException e) {
			addErrorMessage("ID number exists for the specified OFO and Intervention Type");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			csvUtil = new CSVUtil();
		}

	}

	/**
	 * Update WorkPlaceApprovalData in database
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApprovalData
	 */
	public void workplaceapprovaldataUpdate() {
		try {
			service.update(this.workplaceapprovaldata);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplaceapprovaldataInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WorkPlaceApprovalData from database
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApprovalData
	 */
	public void workplaceapprovaldataDelete() {
		try {
			service.delete(this.workplaceapprovaldata);
			prepareNew();
			workplaceapprovaldataInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WorkPlaceApprovalData
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApprovalData
	 */
	public void prepareNew() {
		workplaceapprovaldata = new WorkPlaceApprovalData();
	}

	/*
	 * public List<SelectItem> getWorkPlaceApprovalDataDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * workplaceapprovaldataInfo(); for (WorkPlaceApprovalData ug :
	 * workplaceapprovaldataList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<WorkPlaceApprovalData> complete(String desc) {
		List<WorkPlaceApprovalData> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<WorkPlaceApprovalData> getWorkPlaceApprovalDataList() {
		return workplaceapprovaldataList;
	}

	public void setWorkPlaceApprovalDataList(List<WorkPlaceApprovalData> workplaceapprovaldataList) {
		this.workplaceapprovaldataList = workplaceapprovaldataList;
	}

	public WorkPlaceApprovalData getWorkplaceapprovaldata() {
		return workplaceapprovaldata;
	}

	public void setWorkplaceapprovaldata(WorkPlaceApprovalData workplaceapprovaldata) {
		this.workplaceapprovaldata = workplaceapprovaldata;
	}

	public List<WorkPlaceApprovalData> getWorkPlaceApprovalDatafilteredList() {
		return workplaceapprovaldatafilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param workplaceapprovaldatafilteredList
	 *            the new workplaceapprovaldatafilteredList list
	 * @see WorkPlaceApprovalData
	 */
	public void setWorkPlaceApprovalDatafilteredList(List<WorkPlaceApprovalData> workplaceapprovaldatafilteredList) {
		this.workplaceapprovaldatafilteredList = workplaceapprovaldatafilteredList;
	}

	public LazyDataModel<WorkPlaceApprovalData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkPlaceApprovalData> dataModel) {
		this.dataModel = dataModel;
	}

}
