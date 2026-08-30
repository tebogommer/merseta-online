package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SocioeconomicStatusCode;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SocioeconomicStatusCodeService;

// TODO: Auto-generated Javadoc
/**
 * The Class SocioeconomicStatusCodeUI.
 */
@ManagedBean(name = "socioeconomicstatuscodeUI")
@ViewScoped
public class SocioeconomicStatusCodeUI extends AbstractUI {

	/** The service. */
	private SocioeconomicStatusCodeService service = new SocioeconomicStatusCodeService();
	
	/** The socioeconomicstatuscode list. */
	private List<SocioeconomicStatusCode> socioeconomicstatuscodeList = null;
	
	/** The socioeconomicstatuscodefiltered list. */
	private List<SocioeconomicStatusCode> socioeconomicstatuscodefilteredList = null;
	
	/** The socioeconomicstatuscode. */
	private SocioeconomicStatusCode socioeconomicstatuscode = null;
	
	/** The data model. */
	private LazyDataModel<SocioeconomicStatusCode> dataModel;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all SocioeconomicStatusCode and prepare a for a
	 * create of a new SocioeconomicStatusCode.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see SocioeconomicStatusCode
	 */
	private void runInit() throws Exception {
		prepareNew();
		socioeconomicstatuscodeInfo();
	}

	/**
	 * Get all SocioeconomicStatusCode for data table.
	 *
	 * @author TechFinium
	 * @see SocioeconomicStatusCode
	 */
	public void socioeconomicstatuscodeInfo() {

		dataModel = new LazyDataModel<SocioeconomicStatusCode>() {

			private static final long serialVersionUID = 1L;
			private List<SocioeconomicStatusCode> retorno = new ArrayList<SocioeconomicStatusCode>();

			@Override
			public List<SocioeconomicStatusCode> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allSocioeconomicStatusCode(SocioeconomicStatusCode.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SocioeconomicStatusCode.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SocioeconomicStatusCode obj) {
				return obj.getId();
			}

			@Override
			public SocioeconomicStatusCode getRowData(String rowKey) {
				for (SocioeconomicStatusCode obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert SocioeconomicStatusCode into database.
	 *
	 * @author TechFinium
	 * @see SocioeconomicStatusCode
	 */
	public void socioeconomicstatuscodeInsert() {
		try {
			service.create(this.socioeconomicstatuscode);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			socioeconomicstatuscodeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SocioeconomicStatusCode in database.
	 *
	 * @author TechFinium
	 * @see SocioeconomicStatusCode
	 */
	public void socioeconomicstatuscodeUpdate() {
		try {
			service.update(this.socioeconomicstatuscode);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			socioeconomicstatuscodeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SocioeconomicStatusCode from database.
	 *
	 * @author TechFinium
	 * @see SocioeconomicStatusCode
	 */
	public void socioeconomicstatuscodeDelete() {
		try {
			service.delete(this.socioeconomicstatuscode);
			prepareNew();
			socioeconomicstatuscodeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SocioeconomicStatusCode.
	 *
	 * @author TechFinium
	 * @see SocioeconomicStatusCode
	 */
	public void prepareNew() {
		socioeconomicstatuscode = new SocioeconomicStatusCode();
	}

	/*
	 * public List<SelectItem> getSocioeconomicStatusCodeDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * socioeconomicstatuscodeInfo(); for (SocioeconomicStatusCode ug :
	 * socioeconomicstatuscodeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SocioeconomicStatusCode> complete(String desc) {
		List<SocioeconomicStatusCode> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the socioeconomic status code list.
	 *
	 * @return the socioeconomic status code list
	 */
	public List<SocioeconomicStatusCode> getSocioeconomicStatusCodeList() {
		return socioeconomicstatuscodeList;
	}

	/**
	 * Sets the socioeconomic status code list.
	 *
	 * @param socioeconomicstatuscodeList the new socioeconomic status code list
	 */
	public void setSocioeconomicStatusCodeList(List<SocioeconomicStatusCode> socioeconomicstatuscodeList) {
		this.socioeconomicstatuscodeList = socioeconomicstatuscodeList;
	}

	/**
	 * Gets the socioeconomicstatuscode.
	 *
	 * @return the socioeconomicstatuscode
	 */
	public SocioeconomicStatusCode getSocioeconomicstatuscode() {
		return socioeconomicstatuscode;
	}

	/**
	 * Sets the socioeconomicstatuscode.
	 *
	 * @param socioeconomicstatuscode the new socioeconomicstatuscode
	 */
	public void setSocioeconomicstatuscode(SocioeconomicStatusCode socioeconomicstatuscode) {
		this.socioeconomicstatuscode = socioeconomicstatuscode;
	}

	/**
	 * Gets the socioeconomic status codefiltered list.
	 *
	 * @return the socioeconomic status codefiltered list
	 */
	public List<SocioeconomicStatusCode> getSocioeconomicStatusCodefilteredList() {
		return socioeconomicstatuscodefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param socioeconomicstatuscodefilteredList            the new socioeconomicstatuscodefilteredList list
	 * @see SocioeconomicStatusCode
	 */
	public void setSocioeconomicStatusCodefilteredList(
			List<SocioeconomicStatusCode> socioeconomicstatuscodefilteredList) {
		this.socioeconomicstatuscodefilteredList = socioeconomicstatuscodefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SocioeconomicStatusCode> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SocioeconomicStatusCode> dataModel) {
		this.dataModel = dataModel;
	}

}
