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

import haj.com.entity.WspCalculationData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspCalculationDataService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "wspcalculationdataUI")
@ViewScoped
public class WspCalculationDataUI extends AbstractUI {

	private WspCalculationDataService service = new WspCalculationDataService();
	private List<WspCalculationData> wspcalculationdataList = null;
	private List<WspCalculationData> wspcalculationdatafilteredList = null;
	private WspCalculationData wspcalculationdata = null;
	private LazyDataModel<WspCalculationData> dataModel;
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
	 * Initialize method to get all WspCalculationData and prepare a for a create of
	 * a new WspCalculationData
	 * 
	 * @author TechFinium
	 * @see WspCalculationData
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		wspcalculationdataInfo();
	}

	/**
	 * Get all WspCalculationData for data table
	 * 
	 * @author TechFinium
	 * @see WspCalculationData
	 */
	public void wspcalculationdataInfo() {

		dataModel = new LazyDataModel<WspCalculationData>() {

			private static final long serialVersionUID = 1L;
			private List<WspCalculationData> retorno = new ArrayList<WspCalculationData>();

			@Override
			public List<WspCalculationData> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allWspCalculationData(WspCalculationData.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(WspCalculationData.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WspCalculationData obj) {
				return obj.getId();
			}

			@Override
			public WspCalculationData getRowData(String rowKey) {
				for (WspCalculationData obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert WspCalculationData into database
	 * 
	 * @author TechFinium
	 * @see WspCalculationData
	 */
	public void wspcalculationdataInsert() {
		try {
			service.create(this.wspcalculationdata);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wspcalculationdataInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WspCalculationData in database
	 * 
	 * @author TechFinium
	 * @see WspCalculationData
	 */
	public void wspcalculationdataUpdate() {
		try {
			service.update(this.wspcalculationdata);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wspcalculationdataInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WspCalculationData from database
	 * 
	 * @author TechFinium
	 * @see WspCalculationData
	 */
	public void wspcalculationdataDelete() {
		try {
			service.delete(this.wspcalculationdata);
			prepareNew();
			wspcalculationdataInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WspCalculationData
	 * 
	 * @author TechFinium
	 * @see WspCalculationData
	 */
	public void prepareNew() {
		wspcalculationdata = new WspCalculationData();
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			logger.info("Starting file upload");

			List<WspCalculationData> mandatoryGrantCSVImports = csvUtil.getObjects(WspCalculationData.class, event.getFile().getInputstream(), ";");

			logger.info("Finished parsing upload. number of entries: " + mandatoryGrantCSVImports.size());

			service.save(mandatoryGrantCSVImports);

			logger.info("Finished persisting upload");
			wspcalculationdataInfo();
		} catch (ConstraintViolationException e) {
			addErrorMessage("ID number exists for the specified OFO and Intervention Type");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} finally {
			csvUtil = new CSVUtil();
		}

	}

	public List<WspCalculationData> complete(String desc) {
		List<WspCalculationData> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<WspCalculationData> getWspCalculationDataList() {
		return wspcalculationdataList;
	}

	public void setWspCalculationDataList(List<WspCalculationData> wspcalculationdataList) {
		this.wspcalculationdataList = wspcalculationdataList;
	}

	public WspCalculationData getWspcalculationdata() {
		return wspcalculationdata;
	}

	public void setWspcalculationdata(WspCalculationData wspcalculationdata) {
		this.wspcalculationdata = wspcalculationdata;
	}

	public List<WspCalculationData> getWspCalculationDatafilteredList() {
		return wspcalculationdatafilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param wspcalculationdatafilteredList
	 *            the new wspcalculationdatafilteredList list
	 * @see WspCalculationData
	 */
	public void setWspCalculationDatafilteredList(List<WspCalculationData> wspcalculationdatafilteredList) {
		this.wspcalculationdatafilteredList = wspcalculationdatafilteredList;
	}

	public LazyDataModel<WspCalculationData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspCalculationData> dataModel) {
		this.dataModel = dataModel;
	}

}
