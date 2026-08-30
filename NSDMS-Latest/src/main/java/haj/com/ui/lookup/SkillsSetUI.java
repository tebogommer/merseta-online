package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SkillsProgramUnitStandards;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.SkillsSetUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SkillsProgramUnitStandardsService;
import haj.com.service.lookup.SkillsSetService;
import haj.com.service.lookup.SkillsSetUnitStandardsService;

@ManagedBean(name = "skillssetUI")
@ViewScoped
public class SkillsSetUI extends AbstractUI {

	private SkillsSetService service = new SkillsSetService();
	private List<SkillsSet> skillssetList = null;
	private List<SkillsSet> skillssetfilteredList = null;
	private SkillsSet skillsset = null;
	private LazyDataModel<SkillsSet> dataModel;
	private LazyDataModel<SkillsSet> dataModelMerseta;
	private List<UnitStandards> unitStandards = null;
	private UnitStandards unitStandard = null;
	private SkillsSetUnitStandardsService  skillsSetUnitStandardsService  = new SkillsSetUnitStandardsService ();
	private SkillsSetUnitStandards  skillsSetUnitStandards  = new SkillsSetUnitStandards();
	private List<SkillsSetUnitStandards> list;
	
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
	 * Initialize method to get all SkillsSet and prepare a for a create of a new
	 * SkillsSet
	 * 
	 * @author TechFinium
	 * @see SkillsSet
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		skillssetInfo();
		dataModelMersetaInfo();
	}

	/**
	 * Get all SkillsSet for data table
	 * 
	 * @author TechFinium
	 * @see SkillsSet
	 */
	public void skillssetInfo() {

		dataModel = new LazyDataModel<SkillsSet>() {

			private static final long serialVersionUID = 1L;
			private List<SkillsSet> retorno = new ArrayList<SkillsSet>();

			@Override
			public List<SkillsSet> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allSkillsSet(SkillsSet.class, first, pageSize, sortField, sortOrder, filters);
					for(SkillsSet skillsSet: retorno) {
						skillsSet.setSkillsSetUnitStandards(skillsSetUnitStandardsService.findBySkillsSetKey(skillsSet));
					}
					dataModel.setRowCount(service.count(SkillsSet.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SkillsSet obj) {
				return obj.getId();
			}

			@Override
			public SkillsSet getRowData(String rowKey) {
				for (SkillsSet obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void dataModelMersetaInfo() {

		dataModelMerseta = new LazyDataModel<SkillsSet>() {

			private static final long serialVersionUID = 1L;
			private List<SkillsSet> retorno = new ArrayList<SkillsSet>();

			@Override
			public List<SkillsSet> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allSkillsSetByEtqa(first, pageSize, sortField, sortOrder, filters);
					for(SkillsSet skillsSet: retorno) {
						skillsSet.setSkillsSetUnitStandards(skillsSetUnitStandardsService.findBySkillsSetKey(skillsSet));
					}
					dataModelMerseta.setRowCount(service.countAllSkillsSetByEtqa(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SkillsSet obj) {
				return obj.getId();
			}

			@Override
			public SkillsSet getRowData(String rowKey) {
				for (SkillsSet obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert SkillsSet into database
	 * 
	 * @author TechFinium
	 * @see SkillsSet
	 */
	public void skillssetInsert() {
		try {
			service.create(this.skillsset, this.unitStandards);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillssetInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepSkillsSet() {
		try {
			this.skillsSetUnitStandards = new  SkillsSetUnitStandards();
			this.unitStandards = new ArrayList<>();
			list = service.allSkillsSetUnitStandards(skillsset);
			for (SkillsSetUnitStandards skillsSetUnitStandards : list) {
				this.unitStandards.add(skillsSetUnitStandards.getUnitStandards());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void addSkillsSetUnitStandardsToList() {
		try {
			skillsSetUnitStandardsService.addSkillsSetUnitStandardsToList(skillsSetUnitStandards , this.skillsset);
			//skillsprogram = new SkillsProgram();
	
			skillsSetUnitStandards = new SkillsSetUnitStandards();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			//skillsprogramInfo();
			prepSkillsSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void addUnitStandardToList() {
		this.unitStandards.add(unitStandard);
	}

	public void removeUnitStandardFromList() {
		this.unitStandards.remove(unitStandard);
	}

	/**
	 * Update SkillsSet in database
	 * 
	 * @author TechFinium
	 * @see SkillsSet
	 */
	public void skillssetUpdate() {
		try {
			service.update(this.skillsset);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillssetInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SkillsSet from database
	 * 
	 * @author TechFinium
	 * @see SkillsSet
	 */
	public void skillssetDelete() {
		try {
			service.delete(this.skillsset);
			prepareNew();
			skillssetInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeSkillsSetUnitStandardsList() {
		try {
			this.skillsSetUnitStandardsService.delete(skillsSetUnitStandards);
			prepSkillsSet();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SkillsSet
	 * 
	 * @author TechFinium
	 * @see SkillsSet
	 */
	public void prepareNew() {
		skillsset = new SkillsSet();
		this.unitStandards = new ArrayList<>();
	}

	/*
	 * public List<SelectItem> getSkillsSetDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * skillssetInfo(); for (SkillsSet ug : skillssetList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SkillsSet> complete(String desc) {
		List<SkillsSet> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SkillsSet> getSkillsSetList() {
		return skillssetList;
	}

	public void setSkillsSetList(List<SkillsSet> skillssetList) {
		this.skillssetList = skillssetList;
	}

	public SkillsSet getSkillsset() {
		return skillsset;
	}

	public void setSkillsset(SkillsSet skillsset) {
		this.skillsset = skillsset;
	}

	public List<SkillsSet> getSkillsSetfilteredList() {
		return skillssetfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param skillssetfilteredList
	 *            the new skillssetfilteredList list
	 * @see SkillsSet
	 */
	public void setSkillsSetfilteredList(List<SkillsSet> skillssetfilteredList) {
		this.skillssetfilteredList = skillssetfilteredList;
	}

	public LazyDataModel<SkillsSet> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SkillsSet> dataModel) {
		this.dataModel = dataModel;
	}

	public List<UnitStandards> getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(List<UnitStandards> unitStandards) {
		this.unitStandards = unitStandards;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	public SkillsSetUnitStandards getSkillsSetUnitStandards() {
		return skillsSetUnitStandards;
	}

	public void setSkillsSetUnitStandards(SkillsSetUnitStandards skillsSetUnitStandards) {
		this.skillsSetUnitStandards = skillsSetUnitStandards;
	}

	public List<SkillsSetUnitStandards> getList() {
		return list;
	}

	public void setList(List<SkillsSetUnitStandards> list) {
		this.list = list;
	}

	public LazyDataModel<SkillsSet> getDataModelMerseta() {
		return dataModelMerseta;
	}

	public void setDataModelMerseta(LazyDataModel<SkillsSet> dataModelMerseta) {
		this.dataModelMerseta = dataModelMerseta;
	}
}
