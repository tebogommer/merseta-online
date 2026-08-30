package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.SarsEmployerCompanyBean;
import haj.com.entity.Blank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsFiles;
import haj.com.service.SarsEmployerDetailService;
import haj.com.service.SarsFilesService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "sarsStatusChangedUI")
@ViewScoped
public class SarsStatusChangedUI extends AbstractUI {

	private SarsEmployerDetailService service =  new SarsEmployerDetailService();
	private SarsFilesService sarsFilesService = new SarsFilesService();
	private SarsFiles sarsFiles;
	private  List<SarsEmployerCompanyBean> list;
	private  List<SarsEmployerCompanyBean> listFiltered;

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
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		this.sarsFiles = sarsFilesService.latestSarsLevyFile();
		if (this.sarsFiles!=null) {
			this.list = service.sarsStatusChanged(this.sarsFiles.getId());
		}
	
	}

	public List<SarsEmployerCompanyBean> getList() {
		return list;
	}

	public void setList(List<SarsEmployerCompanyBean> list) {
		this.list = list;
	}

	public SarsFiles getSarsFiles() {
		return sarsFiles;
	}

	public void setSarsFiles(SarsFiles sarsFiles) {
		this.sarsFiles = sarsFiles;
	}

	public List<SarsEmployerCompanyBean> getListFiltered() {
		return listFiltered;
	}

	public void setListFiltered(List<SarsEmployerCompanyBean> listFiltered) {
		this.listFiltered = listFiltered;
	}

	public void procesPending() {
		try {
			service.procesPending(list);
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
}
