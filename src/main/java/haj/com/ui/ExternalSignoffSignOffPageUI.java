package haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Signoff;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.framework.AbstractUI;
import haj.com.service.RegisterService;
import haj.com.service.SignoffService;
import haj.com.service.UsersService;
import haj.com.service.WorkplaceMonitoringSiteVisitService;

// TODO: Auto-generated Javadoc
/**
 * The Class ExternalSignoffSignOffPageUI.
 */
@ManagedBean(name = "externalSignoffSignOffPageUI")
@ViewScoped
public class ExternalSignoffSignOffPageUI extends AbstractUI {

	/** The service. */
	private SignoffService service = new SignoffService();
	
	/** The uuid. */
	private String uuid;
	
	/** The valid link. */
	private boolean validLink;
	
	/** The link expired. */
	private boolean linkExpired;
	
	/** The Signoff. */
	private Signoff signoff;
	
	private Boolean validatedIdNumber;
	
	private String idNumberEntered = "";
	
	private Boolean accept = false;
	
	private Boolean completed = false;
	
	/* Service Levels */
	private WorkplaceMonitoringSiteVisitService workplaceMonitoringSiteVisitService = null;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		validLink = false;
		if (super.getParameter("uuid", false) != null) {
			this.uuid = (String) super.getParameter("uuid", false);
			this.validLink = true;
			signoff = service.findByGUIDnotAccepted(this.uuid.trim());
			if (signoff == null) {
				this.validLink = false;
			} else {
				if (signoff.getAccept()) {
					this.validLink = false;
				} else {
					validatedIdNumber = false;
					idNumberEntered = "";
					accept = false;
				}
			}
		}
	}
	
	public void validateIdNumber(){
		try {
			
			if (signoff.getTempSignoff().getIdPassportSelection() != null) {
				
				Boolean matched = false;
				
				switch (signoff.getTempSignoff().getIdPassportSelection()) {
				case Passport:
					matched = idNumberEntered.trim().toUpperCase().equals(signoff.getTempSignoff().getPassportNumber().trim().toUpperCase());
					break;
				case RsaId:
					matched = idNumberEntered.trim().equals(signoff.getTempSignoff().getIdNumber().trim());
					break;
				default:
					matched = false;
					break;
				}
				
				if (matched) {
					validatedIdNumber = true;
					accept = false;
					completed = false;
					addInfoMessage("Please accept the sign off");
				} else {
					validatedIdNumber = false;
					addErrorMessage("Inavlid Identification Number Provided");
				}
				
				
			} else {
				if (idNumberEntered.trim().equals(signoff.getTempSignoff().getIdNumber().trim())) {
					validatedIdNumber = true;
					accept = false;
					completed = false;
					addInfoMessage("Please accept the sign off");
				} else {
					validatedIdNumber = false;
					addErrorMessage("Inavlid Identification Number Provided");
				}
			}
			
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void signOff() {
		try {
			if (accept == null || !accept) {
				addWarningMessage("Please accept the sign off before proceeding");
			} else {
				signoff.setAccept(true);
				signoff.setSignOffDate(getNow());
				if (signoff.getTargetClass().equals(WorkplaceMonitoringSiteVisit.class.getName())) {
					if (workplaceMonitoringSiteVisitService == null) {
						workplaceMonitoringSiteVisitService = new WorkplaceMonitoringSiteVisitService();
					}
					workplaceMonitoringSiteVisitService.signOffWorkplaceMonitoringExternal(signoff);
				}
				completed = true;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the uuid.
	 *
	 * @param uuid the new uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Checks if is valid link.
	 *
	 * @return true, if is valid link
	 */
	public boolean isValidLink() {
		return validLink;
	}

	/**
	 * Sets the valid link.
	 *
	 * @param validLink the new valid link
	 */
	public void setValidLink(boolean validLink) {
		this.validLink = validLink;
	}

	/**
	 * Checks if is link expired.
	 *
	 * @return true, if is link expired
	 */
	public boolean isLinkExpired() {
		return linkExpired;
	}

	/**
	 * Sets the link expired.
	 *
	 * @param linkExpired the new link expired
	 */
	public void setLinkExpired(boolean linkExpired) {
		this.linkExpired = linkExpired;
	}

	public Signoff getSignoff() {
		return signoff;
	}

	public void setSignoff(Signoff signoff) {
		this.signoff = signoff;
	}

	public String getIdNumberEntered() {
		return idNumberEntered;
	}

	public void setIdNumberEntered(String idNumberEntered) {
		this.idNumberEntered = idNumberEntered;
	}

	public Boolean getAccept() {
		return accept;
	}

	public void setAccept(Boolean accept) {
		this.accept = accept;
	}

	public Boolean getValidatedIdNumber() {
		return validatedIdNumber;
	}

	public void setValidatedIdNumber(Boolean validatedIdNumber) {
		this.validatedIdNumber = validatedIdNumber;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

}
