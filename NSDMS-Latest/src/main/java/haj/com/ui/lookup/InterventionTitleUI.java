package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.InterventionTitle;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.InterventionTitleService;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionTitleUI.
 */
@ManagedBean(name = "interventiontitleUI")
@ViewScoped
public class InterventionTitleUI extends AbstractUI {

	/** The service. */
	private InterventionTitleService service = new InterventionTitleService();

	/** The interventiontitle list. */
	private List<InterventionTitle> interventiontitleList = null;

	/** The interventiontitlefiltered list. */
	private List<InterventionTitle> interventiontitlefilteredList = null;

	/** The interventiontitle. */
	private InterventionTitle interventiontitle = null;

	/** The data model. */
	private LazyDataModel<InterventionTitle> dataModel;

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
	 * Initialize method to get all InterventionTitle and prepare a for a create of
	 * a new InterventionTitle.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see InterventionTitle
	 */
	private void runInit() throws Exception {
		prepareNew();
		interventiontitleInfo();
	}

	/**
	 * Get all InterventionTitle for data table.
	 *
	 * @author TechFinium
	 * @see InterventionTitle
	 */
	public void interventiontitleInfo() {

		dataModel = new LazyDataModel<InterventionTitle>() {

			private static final long serialVersionUID = 1L;
			private List<InterventionTitle> retorno = new ArrayList<InterventionTitle>();

			@Override
			public List<InterventionTitle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allInterventionTitle(InterventionTitle.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(InterventionTitle.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(InterventionTitle obj) {
				return obj.getId();
			}

			@Override
			public InterventionTitle getRowData(String rowKey) {
				for (InterventionTitle obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert InterventionTitle into database.
	 *
	 * @author TechFinium
	 * @see InterventionTitle
	 */
	public void interventiontitleInsert() {
		try {
			service.create(this.interventiontitle);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			interventiontitleInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update InterventionTitle in database.
	 *
	 * @author TechFinium
	 * @see InterventionTitle
	 */
	public void interventiontitleUpdate() {
		try {
			service.update(this.interventiontitle);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			interventiontitleInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete InterventionTitle from database.
	 *
	 * @author TechFinium
	 * @see InterventionTitle
	 */
	public void interventiontitleDelete() {
		try {
			service.delete(this.interventiontitle);
			prepareNew();
			interventiontitleInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted "));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of InterventionTitle .
	 *
	 * @author TechFinium
	 * @see InterventionTitle
	 */
	public void prepareNew() {
		interventiontitle = new InterventionTitle();
	}

	/*
	 * public List<SelectItem> getInterventionTitleDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * interventiontitleInfo(); for (InterventionTitle ug : interventiontitleList) {
	 * // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<InterventionTitle> complete(String desc) {
		List<InterventionTitle> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionTitle> getInterventiontitleList() {
		return interventiontitleList;
	}

	public void setInterventiontitleList(List<InterventionTitle> interventiontitleList) {
		this.interventiontitleList = interventiontitleList;
	}

	public List<InterventionTitle> getInterventiontitlefilteredList() {
		return interventiontitlefilteredList;
	}

	public void setInterventiontitlefilteredList(List<InterventionTitle> interventiontitlefilteredList) {
		this.interventiontitlefilteredList = interventiontitlefilteredList;
	}

	public InterventionTitle getInterventiontitle() {
		return interventiontitle;
	}

	public void setInterventiontitle(InterventionTitle interventiontitle) {
		this.interventiontitle = interventiontitle;
	}

	public LazyDataModel<InterventionTitle> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<InterventionTitle> dataModel) {
		this.dataModel = dataModel;
	}


}
