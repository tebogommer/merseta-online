package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyLearners;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;

@ManagedBean(name = "companylearnersUI")
@ViewScoped
public class CompanyLearnersUI extends AbstractUI {

	private CompanyLearnersService service = new CompanyLearnersService();
	private List<CompanyLearners> companylearnersList = null;
	private List<CompanyLearners> companylearnersfilteredList = null;
	private CompanyLearners companylearners = null;
	private CompanyLearners selectedCompanyLearnerHistory = new CompanyLearners();
	private LazyDataModel<CompanyLearners> dataModel;

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
	 * Initialize method to get all CompanyLearners and prepare a for a create of a
	 * new CompanyLearners
	 * 
	 * @author TechFinium
	 * @see CompanyLearners
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		companylearnersInfo();
	}

	/**
	 * Get all CompanyLearners for data table
	 * 
	 * @author TechFinium
	 * @see CompanyLearners
	 */
	public void companylearnersInfo() {

		dataModel = new LazyDataModel<CompanyLearners>() {

			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<CompanyLearners>();

			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.sortAndFilterWhereLearnerInfo( first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countWhereLearnerInfo(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearners obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearners getRowData(String rowKey) {
				for (CompanyLearners obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void prepareLearnerInfor()
	{
		try {
			if(companylearners.getCompany() !=null)
			{
				//CompanyService companyService=new CompanyService();
				//companylearners.setCompany(companyService.findByKey(companylearners.getCompany().getId()));
				companylearners.setCompanyLearnersList(service.findByUser(companylearners.getUser()));
				clearSelectedCompanyLearnerHistory();
			}
			service.resolveAllData(companylearners);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearSelectedCompanyLearnerHistory()
	{
		selectedCompanyLearnerHistory=new CompanyLearners();
	}
	
	public void prepareLearnerHistory()
	{
		try {
			service.resolveAllData(selectedCompanyLearnerHistory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Insert CompanyLearners into database
	 * 
	 * @author TechFinium
	 * @see CompanyLearners
	 */
	public void companylearnersInsert() {
		try {
			service.create(this.companylearners);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update CompanyLearners in database
	 * 
	 * @author TechFinium
	 * @see CompanyLearners
	 */
	public void companylearnersUpdate() {
		try {
			service.update(this.companylearners);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete CompanyLearners from database
	 * 
	 * @author TechFinium
	 * @see CompanyLearners
	 */
	public void companylearnersDelete() {
		try {
			service.delete(this.companylearners);
			prepareNew();
			companylearnersInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of CompanyLearners
	 * 
	 * @author TechFinium
	 * @see CompanyLearners
	 */
	public void prepareNew() {
		companylearners = new CompanyLearners();
	}

	/*
	 * public List<SelectItem> getCompanyLearnersDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * companylearnersInfo(); for (CompanyLearners ug : companylearnersList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return
	 * l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<CompanyLearners> complete(String desc) {
		List<CompanyLearners> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<CompanyLearners> getCompanyLearnersList() {
		return companylearnersList;
	}

	public void setCompanyLearnersList(List<CompanyLearners> companylearnersList) {
		this.companylearnersList = companylearnersList;
	}

	public CompanyLearners getCompanylearners() {
		return companylearners;
	}

	public void setCompanylearners(CompanyLearners companylearners) {
		this.companylearners = companylearners;
	}

	public List<CompanyLearners> getCompanyLearnersfilteredList() {
		return companylearnersfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param companylearnersfilteredList
	 *            the new companylearnersfilteredList list
	 * @see CompanyLearners
	 */
	public void setCompanyLearnersfilteredList(List<CompanyLearners> companylearnersfilteredList) {
		this.companylearnersfilteredList = companylearnersfilteredList;
	}

	public LazyDataModel<CompanyLearners> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearners> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * @return the selectedCompanyLearnerHistory
	 */
	public CompanyLearners getSelectedCompanyLearnerHistory() {
		return selectedCompanyLearnerHistory;
	}

	/**
	 * @param selectedCompanyLearnerHistory the selectedCompanyLearnerHistory to set
	 */
	public void setSelectedCompanyLearnerHistory(CompanyLearners selectedCompanyLearnerHistory) {
		this.selectedCompanyLearnerHistory = selectedCompanyLearnerHistory;
	}

}
