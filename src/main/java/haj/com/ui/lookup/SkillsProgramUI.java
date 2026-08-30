package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.enums.UnitStandardTypeEnum;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsProgramUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.service.lookup.SkillsProgramUnitStandardsService;

@ManagedBean(name = "skillsprogramUI")
@ViewScoped
public class SkillsProgramUI extends AbstractUI {


	private SkillsProgramService service = new SkillsProgramService();
	private List<SkillsProgram> skillsprogramList = null;
	private List<SkillsProgram> skillsprogramfilteredList = null;
	private SkillsProgram skillsprogram = null;
	private LazyDataModel<SkillsProgram> dataModel;
	private LazyDataModel<SkillsProgram> dataModelMerseta;
	private List<UnitStandards> unitStandards = null;
	private UnitStandards unitStandard = null;
	private SkillsProgramUnitStandardsService  skillsProgramUnitStandardsService = new SkillsProgramUnitStandardsService();
	private UnitStandardTypeEnum unitStandardTypeEnum= null;
	private List<SkillsProgramUnitStandards> list ;
	private SkillsProgramUnitStandards skillsProgramUnitStandards = null;
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
	 * Initialize method to get all SkillsProgram and prepare a for a create of a
	 * new SkillsProgram
	 * 
	 * @author TechFinium
	 * @see SkillsProgram
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		skillsprogramInfo();
		dataModelMersetaInfo();
	}

	/**
	 * Get all SkillsProgram for data table
	 * 
	 * @author TechFinium
	 * @see SkillsProgram
	 */
	public void dataModelMersetaInfo() {
		dataModelMerseta = new LazyDataModel<SkillsProgram>() {
			private static final long serialVersionUID = 1L;
			private List<SkillsProgram> retorno = new ArrayList<SkillsProgram>();
			@Override
			public List<SkillsProgram> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allSkillsProgramByEtqa(first, pageSize, sortField, sortOrder, filters);
					for(SkillsProgram skillsProgram :retorno) {
						skillsProgram.setSkillsProgramUnitStandards(skillsProgramUnitStandardsService.findBySkillsProgramKey(skillsProgram));	
					}
					dataModelMerseta.setRowCount(service.countAllSkillsProgramByEtqa(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SkillsProgram obj) {
				return obj.getId();
			}

			@Override
			public SkillsProgram getRowData(String rowKey) {
				for (SkillsProgram obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void skillsprogramInfo() {

		dataModel = new LazyDataModel<SkillsProgram>() {

			private static final long serialVersionUID = 1L;
			private List<SkillsProgram> retorno = new ArrayList<SkillsProgram>();

			@Override
			public List<SkillsProgram> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allSkillsProgram(SkillsProgram.class, first, pageSize, sortField, sortOrder, filters);
					for(SkillsProgram skillsProgram :retorno) {
						skillsProgram.setSkillsProgramUnitStandards(skillsProgramUnitStandardsService.findBySkillsProgramKey(skillsProgram));	
					}
					dataModel.setRowCount(service.count(SkillsProgram.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SkillsProgram obj) {
				return obj.getId();
			}

			@Override
			public SkillsProgram getRowData(String rowKey) {
				for (SkillsProgram obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert SkillsProgram into database
	 * 
	 * @author TechFinium
	 * @see SkillsProgram
	 */
	public void skillsprogramInsert() {
		try {
			service.create(this.skillsprogram, this.unitStandards);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillsprogramInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void skillsprogramInsertPP() {
		try {
			skillsProgramUnitStandardsService.createLink(this.unitStandard, this.skillsprogram, unitStandardTypeEnum);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillsprogramInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepSkillsSet() {
		try {
			this.skillsProgramUnitStandards = new  SkillsProgramUnitStandards();
			this.unitStandards = new ArrayList<>();
			list = service.allSkillsProgramUnitStandards(skillsprogram);
			for (SkillsProgramUnitStandards skillsSetUnitStandards : list) {
				this.unitStandards.add(skillsSetUnitStandards.getUnitStandards());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Update SkillsProgram in database
	 * 
	 * @author TechFinium
	 * @see SkillsProgram
	 */
	public void skillsprogramUpdate() {
		try {
			service.update(this.skillsprogram);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillsprogramInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SkillsProgram from database
	 * 
	 * @author TechFinium
	 * @see SkillsProgram
	 */
	public void skillsprogramDelete() {
		try {
			service.delete(this.skillsprogram);
			prepareNew();
			skillsprogramInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addUnitStandardToList() {
		this.unitStandards.add(unitStandard);
	}

	public void addSkillsProgramUnitStandardsToList() {
		try {
			skillsProgramUnitStandardsService.addSkillsProgramUnitStandardsToList(skillsProgramUnitStandards , this.skillsprogram);
			//skillsprogram = new SkillsProgram();
			unitStandardTypeEnum=null;
			skillsProgramUnitStandards = new SkillsProgramUnitStandards();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			//skillsprogramInfo();
			prepSkillsSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void removeUnitStandardFromListOld() {
		this.unitStandards.remove(unitStandard);
	}
	
	public void removeUnitStandardFromList() {
		this.unitStandards.remove(unitStandard);
	}
	
	public void removeSkillsProgramUnitStandardsList() {
		try {
			this.skillsProgramUnitStandardsService.delete(skillsProgramUnitStandards);
			prepSkillsSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * Create new instance of SkillsProgram
	 * 
	 * @author TechFinium
	 * @see SkillsProgram
	 */
	public void prepareNew() {
		skillsProgramUnitStandards = new SkillsProgramUnitStandards();
		skillsprogram = new SkillsProgram();
		this.unitStandards = new ArrayList<>();
	}

	/*
	 * public List<SelectItem> getSkillsProgramDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * skillsprogramInfo(); for (SkillsProgram ug : skillsprogramList) { //
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
	public List<SkillsProgram> complete(String desc) {
		List<SkillsProgram> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SkillsProgram> getSkillsProgramList() {
		return skillsprogramList;
	}

	public void setSkillsProgramList(List<SkillsProgram> skillsprogramList) {
		this.skillsprogramList = skillsprogramList;
	}

	public SkillsProgram getSkillsprogram() {
		return skillsprogram;
	}

	public void setSkillsprogram(SkillsProgram skillsprogram) {
		this.skillsprogram = skillsprogram;
	}

	public List<SkillsProgram> getSkillsProgramfilteredList() {
		return skillsprogramfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param skillsprogramfilteredList
	 *            the new skillsprogramfilteredList list
	 * @see SkillsProgram
	 */
	public void setSkillsProgramfilteredList(List<SkillsProgram> skillsprogramfilteredList) {
		this.skillsprogramfilteredList = skillsprogramfilteredList;
	}

	public LazyDataModel<SkillsProgram> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SkillsProgram> dataModel) {
		this.dataModel = dataModel;
	}

	public List<UnitStandards> getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(List<UnitStandards> unitStandards) {
		this.unitStandards = unitStandards;
	}

	public List<SkillsProgram> getSkillsprogramList() {
		return skillsprogramList;
	}

	public void setSkillsprogramList(List<SkillsProgram> skillsprogramList) {
		this.skillsprogramList = skillsprogramList;
	}

	public List<SkillsProgram> getSkillsprogramfilteredList() {
		return skillsprogramfilteredList;
	}

	public void setSkillsprogramfilteredList(List<SkillsProgram> skillsprogramfilteredList) {
		this.skillsprogramfilteredList = skillsprogramfilteredList;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	public UnitStandardTypeEnum getUnitStandardTypeEnum() {
		return unitStandardTypeEnum;
	}

	public void setUnitStandardTypeEnum(UnitStandardTypeEnum unitStandardTypeEnum) {
		this.unitStandardTypeEnum = unitStandardTypeEnum;
	}

	public List<SkillsProgramUnitStandards> getList() {
		return list;
	}

	public void setList(List<SkillsProgramUnitStandards> list) {
		this.list = list;
	}

	public SkillsProgramUnitStandards getSkillsProgramUnitStandards() {
		return skillsProgramUnitStandards;
	}

	public void setSkillsProgramUnitStandards(SkillsProgramUnitStandards skillsProgramUnitStandards) {
		this.skillsProgramUnitStandards = skillsProgramUnitStandards;
	}

	public LazyDataModel<SkillsProgram> getDataModelMerseta() {
		return dataModelMerseta;
	}

	public void setDataModelMerseta(LazyDataModel<SkillsProgram> dataModelMerseta) {
		this.dataModelMerseta = dataModelMerseta;
	}

}
