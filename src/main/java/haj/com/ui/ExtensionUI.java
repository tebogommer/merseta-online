package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.entity.SDFCompany;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SDFCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "extensionUI")
@ViewScoped
public class ExtensionUI extends AbstractUI {

	private SDFCompanyService service = new SDFCompanyService();

	/** The data model. */
	private LazyDataModel<SDFCompany> dataModel;

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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		companysdfInfo();
	}

	public void companysdfInfo() {
		dataModel = new LazyDataModel<SDFCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (getSessionUI().getActiveUser().getAdmin() == null || !getSessionUI().getActiveUser().getAdmin()) {
						retorno = service.allCompanyFromSDF(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						dataModel.setRowCount(service.allCompanyFromSDFCount(filters, getSessionUI().getActiveUser()).intValue());
					} else {
						retorno = service.allSDFCompany(SDFCompany.class, first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(service.count(SDFCompany.class, filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDFCompany obj) {
				return obj.getId();
			}

			@Override
			public SDFCompany getRowData(String rowKey) {
				for (SDFCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public LazyDataModel<SDFCompany> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SDFCompany> dataModel) {
		this.dataModel = dataModel;
	}

}
