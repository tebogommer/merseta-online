package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Language;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LanguageService;

// TODO: Auto-generated Javadoc
/**
 * The Class LanguageUI.
 */
@ManagedBean(name = "languageLookupUI")
@ViewScoped
public class LanguageUI extends AbstractUI {

	/** The service. */
	private LanguageService service = new LanguageService();
	
	/** The language list. */
	private List<Language> languageList = null;
	
	/** The languagefiltered list. */
	private List<Language> languagefilteredList = null;
	
	/** The language. */
	private Language language = null;
	
	/** The data model. */
	private LazyDataModel<Language> dataModel;

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
	 * Initialize method to get all Language and prepare a for a create of a new
	 * Language.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Language
	 */
	private void runInit() throws Exception {
		prepareNew();
		languageInfo();
	}

	/**
	 * Get all Language for data table.
	 *
	 * @author TechFinium
	 * @see Language
	 */
	public void languageInfo() {

		dataModel = new LazyDataModel<Language>() {

			private static final long serialVersionUID = 1L;
			private List<Language> retorno = new ArrayList<Language>();

			@Override
			public List<Language> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLanguage(Language.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Language.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Language obj) {
				return obj.getId();
			}

			@Override
			public Language getRowData(String rowKey) {
				for (Language obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Language into database.
	 *
	 * @author TechFinium
	 * @see Language
	 */
	public void languageInsert() {
		try {
			service.create(this.language);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			languageInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Language in database.
	 *
	 * @author TechFinium
	 * @see Language
	 */
	public void languageUpdate() {
		try {
			service.update(this.language);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			languageInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Language from database.
	 *
	 * @author TechFinium
	 * @see Language
	 */
	public void languageDelete() {
		try {
			service.delete(this.language);
			prepareNew();
			languageInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Language.
	 *
	 * @author TechFinium
	 * @see Language
	 */
	public void prepareNew() {
		language = new Language();
	}

	/*
	 * public List<SelectItem> getLanguageDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * languageInfo(); for (Language ug : languageList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Language> complete(String desc) {
		List<Language> l = null;
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
	 * Gets the language list.
	 *
	 * @return the language list
	 */
	public List<Language> getLanguageList() {
		return languageList;
	}

	/**
	 * Sets the language list.
	 *
	 * @param languageList the new language list
	 */
	public void setLanguageList(List<Language> languageList) {
		this.languageList = languageList;
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * Gets the languagefiltered list.
	 *
	 * @return the languagefiltered list
	 */
	public List<Language> getLanguagefilteredList() {
		return languagefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param languagefilteredList            the new languagefilteredList list
	 * @see Language
	 */
	public void setLanguagefilteredList(List<Language> languagefilteredList) {
		this.languagefilteredList = languagefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Language> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Language> dataModel) {
		this.dataModel = dataModel;
	}

}
