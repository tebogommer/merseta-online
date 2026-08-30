package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ProviderAccreditAssessor;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ProviderAccreditAssessorService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderAccreditAssessorUI.
 */
@ManagedBean(name = "provideraccreditassessorUI")
@ViewScoped
public class ProviderAccreditAssessorUI extends AbstractUI {

	/** The service. */
	private ProviderAccreditAssessorService service = new ProviderAccreditAssessorService();
	
	/** The provideraccreditassessor list. */
	private List<ProviderAccreditAssessor> provideraccreditassessorList = null;
	
	/** The provideraccreditassessorfiltered list. */
	private List<ProviderAccreditAssessor> provideraccreditassessorfilteredList = null;
	
	/** The provideraccreditassessor. */
	private ProviderAccreditAssessor provideraccreditassessor = null;
	
	/** The data model. */
	private LazyDataModel<ProviderAccreditAssessor> dataModel;

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
	 * Initialize method to get all ProviderAccreditAssessor and prepare a for a
	 * create of a new ProviderAccreditAssessor.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see ProviderAccreditAssessor
	 */
	private void runInit() throws Exception {
		prepareNew();
		provideraccreditassessorInfo();
	}

	/**
	 * Get all ProviderAccreditAssessor for data table.
	 *
	 * @author TechFinium
	 * @see ProviderAccreditAssessor
	 */
	public void provideraccreditassessorInfo() {

		dataModel = new LazyDataModel<ProviderAccreditAssessor>() {

			private static final long serialVersionUID = 1L;
			private List<ProviderAccreditAssessor> retorno = new ArrayList<ProviderAccreditAssessor>();

			@Override
			public List<ProviderAccreditAssessor> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allProviderAccreditAssessor(ProviderAccreditAssessor.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(ProviderAccreditAssessor.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(ProviderAccreditAssessor obj) {
				return obj.getId();
			}

			@Override
			public ProviderAccreditAssessor getRowData(String rowKey) {
				for (ProviderAccreditAssessor obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert ProviderAccreditAssessor into database.
	 *
	 * @author TechFinium
	 * @see ProviderAccreditAssessor
	 */
	public void provideraccreditassessorInsert() {
		try {
			service.create(this.provideraccreditassessor);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			provideraccreditassessorInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update ProviderAccreditAssessor in database.
	 *
	 * @author TechFinium
	 * @see ProviderAccreditAssessor
	 */
	public void provideraccreditassessorUpdate() {
		try {
			service.update(this.provideraccreditassessor);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			provideraccreditassessorInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ProviderAccreditAssessor from database.
	 *
	 * @author TechFinium
	 * @see ProviderAccreditAssessor
	 */
	public void provideraccreditassessorDelete() {
		try {
			service.delete(this.provideraccreditassessor);
			prepareNew();
			provideraccreditassessorInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ProviderAccreditAssessor.
	 *
	 * @author TechFinium
	 * @see ProviderAccreditAssessor
	 */
	public void prepareNew() {
		provideraccreditassessor = new ProviderAccreditAssessor();
	}

	/*
	 * public List<SelectItem> getProviderAccreditAssessorDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * provideraccreditassessorInfo(); for (ProviderAccreditAssessor ug :
	 * provideraccreditassessorList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ProviderAccreditAssessor> complete(String desc) {
		List<ProviderAccreditAssessor> l = null;
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
	 * Gets the provider accredit assessor list.
	 *
	 * @return the provider accredit assessor list
	 */
	public List<ProviderAccreditAssessor> getProviderAccreditAssessorList() {
		return provideraccreditassessorList;
	}

	/**
	 * Sets the provider accredit assessor list.
	 *
	 * @param provideraccreditassessorList the new provider accredit assessor list
	 */
	public void setProviderAccreditAssessorList(List<ProviderAccreditAssessor> provideraccreditassessorList) {
		this.provideraccreditassessorList = provideraccreditassessorList;
	}

	/**
	 * Gets the provideraccreditassessor.
	 *
	 * @return the provideraccreditassessor
	 */
	public ProviderAccreditAssessor getProvideraccreditassessor() {
		return provideraccreditassessor;
	}

	/**
	 * Sets the provideraccreditassessor.
	 *
	 * @param provideraccreditassessor the new provideraccreditassessor
	 */
	public void setProvideraccreditassessor(ProviderAccreditAssessor provideraccreditassessor) {
		this.provideraccreditassessor = provideraccreditassessor;
	}

	/**
	 * Gets the provider accredit assessorfiltered list.
	 *
	 * @return the provider accredit assessorfiltered list
	 */
	public List<ProviderAccreditAssessor> getProviderAccreditAssessorfilteredList() {
		return provideraccreditassessorfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param provideraccreditassessorfilteredList            the new provideraccreditassessorfilteredList list
	 * @see ProviderAccreditAssessor
	 */
	public void setProviderAccreditAssessorfilteredList(
			List<ProviderAccreditAssessor> provideraccreditassessorfilteredList) {
		this.provideraccreditassessorfilteredList = provideraccreditassessorfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<ProviderAccreditAssessor> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<ProviderAccreditAssessor> dataModel) {
		this.dataModel = dataModel;
	}

}
