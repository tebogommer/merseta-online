package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Seta;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SetaService;

// TODO: Auto-generated Javadoc
/**
 * The Class SetaUI.
 */
@ManagedBean(name = "setaUI")
@ViewScoped
public class SetaUI extends AbstractUI {

	/** The service. */
	private SetaService service = new SetaService();
	
	/** The seta list. */
	private List<Seta> setaList = null;
	
	/** The setafiltered list. */
	private List<Seta> setafilteredList = null;
	
	/** The seta. */
	private Seta seta = null;
	
	/** The data model. */
	private LazyDataModel<Seta> dataModel;

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
	 * Initialize method to get all Seta and prepare a for a create of a new
	 * Seta.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Seta
	 */
	private void runInit() throws Exception {
		prepareNew();
		setaInfo();
	}

	/**
	 * Get all Seta for data table.
	 *
	 * @author TechFinium
	 * @see Seta
	 */
	public void setaInfo() {

		dataModel = new LazyDataModel<Seta>() {

			private static final long serialVersionUID = 1L;
			private List<Seta> retorno = new ArrayList<Seta>();

			@Override
			public List<Seta> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allSeta(Seta.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Seta.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Seta obj) {
				return obj.getId();
			}

			@Override
			public Seta getRowData(String rowKey) {
				for (Seta obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Seta into database.
	 *
	 * @author TechFinium
	 * @see Seta
	 */
	public void setaInsert() {
		try {
			service.create(this.seta);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setaInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Seta in database.
	 *
	 * @author TechFinium
	 * @see Seta
	 */
	public void setaUpdate() {
		try {
			service.update(this.seta);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setaInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Seta from database.
	 *
	 * @author TechFinium
	 * @see Seta
	 */
	public void setaDelete() {
		try {
			service.delete(this.seta);
			prepareNew();
			setaInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Seta.
	 *
	 * @author TechFinium
	 * @see Seta
	 */
	public void prepareNew() {
		seta = new Seta();
	}

	/*
	 * public List<SelectItem> getSetaDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * setaInfo(); for (Seta ug : setaList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Seta> complete(String desc) {
		List<Seta> l = null;
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
	 * Gets the seta list.
	 *
	 * @return the seta list
	 */
	public List<Seta> getSetaList() {
		return setaList;
	}

	/**
	 * Sets the seta list.
	 *
	 * @param setaList the new seta list
	 */
	public void setSetaList(List<Seta> setaList) {
		this.setaList = setaList;
	}

	/**
	 * Gets the seta.
	 *
	 * @return the seta
	 */
	public Seta getSeta() {
		return seta;
	}

	/**
	 * Sets the seta.
	 *
	 * @param seta the new seta
	 */
	public void setSeta(Seta seta) {
		this.seta = seta;
	}

	/**
	 * Gets the setafiltered list.
	 *
	 * @return the setafiltered list
	 */
	public List<Seta> getSetafilteredList() {
		return setafilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param setafilteredList            the new setafilteredList list
	 * @see Seta
	 */
	public void setSetafilteredList(List<Seta> setafilteredList) {
		this.setafilteredList = setafilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Seta> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Seta> dataModel) {
		this.dataModel = dataModel;
	}

}
