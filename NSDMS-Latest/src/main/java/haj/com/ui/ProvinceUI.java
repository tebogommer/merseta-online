package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Province;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ProvinceService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProvinceUI.
 */
@ManagedBean(name = "provinceUI")
@ViewScoped
public class ProvinceUI extends AbstractUI {

	/** The service. */
	private ProvinceService service = new ProvinceService();

	/** The province list. */
	private List<Province> provinceList = null;

	/** The provincefiltered list. */
	private List<Province> provincefilteredList = null;

	/** The province. */
	private Province province = null;

	/** The data model. */
	private LazyDataModel<Province> dataModel;

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
	 * Initialize method to get all Province and prepare a for a create of a new
	 * Province.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Province
	 */
	private void runInit() throws Exception {
		prepareNew();
		provinceInfo();
	}

	/**
	 * Get all Province for data table.
	 *
	 * @author TechFinium
	 * @see Province
	 */
	public void provinceInfo() {

		dataModel = new LazyDataModel<Province>() {

			private static final long serialVersionUID = 1L;
			private List<Province> retorno = new ArrayList<Province>();

			@Override
			public List<Province> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allProvince(Province.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Province.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Province obj) {
				return obj.getId();
			}

			@Override
			public Province getRowData(String rowKey) {
				for (Province obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Province into database.
	 *
	 * @author TechFinium
	 * @see Province
	 */
	public void provinceInsert() {
		try {
			service.create(this.province);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			provinceInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Province from database.
	 *
	 * @author TechFinium
	 * @see Province
	 */
	public void provinceDelete() {
		try {
			service.delete(this.province);
			prepareNew();
			provinceInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the province list.
	 *
	 * @return the province list
	 */
	public List<Province> getProvinceList() {
		return provinceList;
	}

	/**
	 * Sets the province list.
	 *
	 * @param provinceList
	 *            the new province list
	 */
	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}

	/**
	 * Gets the province.
	 *
	 * @return the province
	 */
	public Province getProvince() {
		return province;
	}

	/**
	 * Sets the province.
	 *
	 * @param province
	 *            the new province
	 */
	public void setProvince(Province province) {
		this.province = province;
	}

	/**
	 * Create new instance of Province .
	 *
	 * @author TechFinium
	 * @see Province
	 */
	public void prepareNew() {
		province = new Province();
	}

	/**
	 * Update Province in database.
	 *
	 * @author TechFinium
	 * @see Province
	 */
	public void provinceUpdate() {
		try {
			service.update(this.province);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			provinceInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare select one menu data.
	 *
	 * @author TechFinium
	 * @return the province DD
	 * @see Province
	 */
	public List<SelectItem> getProvinceDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		l.add(new SelectItem(Long.valueOf(-1L), "-------------Select-------------"));
		provinceInfo();
		for (Province ug : provinceList) {
			// l.add(new SelectItem(ug.getUserGroupId(),
			// ug.getUserGroupDesc()));
		}
		return l;
	}

	/**
	 * Gets the provincefiltered list.
	 *
	 * @return the provincefiltered list
	 */
	public List<Province> getProvincefilteredList() {
		return provincefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param provincefilteredList
	 *            the new provincefiltered list
	 * @see Province
	 */
	public void setProvincefilteredList(List<Province> provincefilteredList) {
		this.provincefilteredList = provincefilteredList;
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Province> complete(String desc) {
		List<Province> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Province> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<Province> dataModel) {
		this.dataModel = dataModel;
	}

}
