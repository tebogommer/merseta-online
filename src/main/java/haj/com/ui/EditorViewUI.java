package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;

import haj.com.entity.Address;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.AssessorModeratorCompany;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.Municipality;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.Users;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SICCode;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.AssessorModeratorCompanyService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.MailDef;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.UserQualificationsService;
import haj.com.service.UserUnitStandardService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.lookup.ChamberService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.SICCodeService;

// TODO: Auto-generated Javadoc
/**
 * The Class AmDetailUI.
 */
@ManagedBean(name = "editorView")
@ViewScoped
public class EditorViewUI extends AbstractUI {

	String text;
	
	
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
	 * @throws Exception             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
