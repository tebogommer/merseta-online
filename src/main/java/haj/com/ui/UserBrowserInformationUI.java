package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import haj.com.entity.UserBrowserInformation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UserBrowserInformationService;

@ManagedBean(name = "userbrowserinformationUI")
@ViewScoped
public class UserBrowserInformationUI extends AbstractUI {

	private UserBrowserInformationService service = new UserBrowserInformationService();
	private List<UserBrowserInformation> userbrowserinformationList = null;
	private List<UserBrowserInformation> userbrowserinformationfilteredList = null;
	private UserBrowserInformation userbrowserinformation = null;
	private LazyDataModel<UserBrowserInformation> dataModel;
	private MapModel circleModel;

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
	 * Initialize method to get all UserBrowserInformation and prepare a for a create of a new UserBrowserInformation
 	 * @author TechFinium
 	 * @see    UserBrowserInformation
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
//		prepareNew();
		userbrowserinformationInfo();
	}

	/**
	 * Get all UserBrowserInformation for data table
 	 * @author TechFinium
 	 * @see    UserBrowserInformation
 	 */
	public void userbrowserinformationInfo() {


			 dataModel = new LazyDataModel<UserBrowserInformation>() {

			   private static final long serialVersionUID = 1L;
			   private List<UserBrowserInformation> retorno = new  ArrayList<UserBrowserInformation>();

			   @Override
			   public List<UserBrowserInformation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  {

				try {
					retorno = service.allUserBrowserInformation(UserBrowserInformation.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UserBrowserInformation.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
			    return retorno;
			   }

			    @Override
			    public Object getRowKey(UserBrowserInformation obj) {
			        return obj.getId();
			    }

			    @Override
			    public UserBrowserInformation getRowData(String rowKey) {
			        for(UserBrowserInformation obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }

			  };



	}

	/**
	 * Insert UserBrowserInformation into database
 	 * @author TechFinium
 	 * @see    UserBrowserInformation
 	 */
	public void userbrowserinformationInsert() {
		try {
				 service.create(this.userbrowserinformation);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 userbrowserinformationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Update UserBrowserInformation in database
 	 * @author TechFinium
 	 * @see    UserBrowserInformation
 	 */
    public void userbrowserinformationUpdate() {
		try {
				 service.update(this.userbrowserinformation);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 userbrowserinformationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UserBrowserInformation from database
 	 * @author TechFinium
 	 * @see    UserBrowserInformation
 	 */
	public void userbrowserinformationDelete() {
		try {
			 service.delete(this.userbrowserinformation);
			  prepareNew();
			 userbrowserinformationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of UserBrowserInformation
 	 * @author TechFinium
 	 * @see    UserBrowserInformation
 	 */
	public void prepareNew() {
		userbrowserinformation = new UserBrowserInformation();
	}

/*
    public List<SelectItem> getUserBrowserInformationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	userbrowserinformationInfo();
    	for (UserBrowserInformation ug : userbrowserinformationList) {
    		// l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc()));
		}
    	return l;
    }
  */

    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */
    public List<UserBrowserInformation> complete(String desc) {
		List<UserBrowserInformation> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

    public List<UserBrowserInformation> getUserBrowserInformationList() {
		return userbrowserinformationList;
	}
	public void setUserBrowserInformationList(List<UserBrowserInformation> userbrowserinformationList) {
		this.userbrowserinformationList = userbrowserinformationList;
	}
	public UserBrowserInformation getUserbrowserinformation() {
		return userbrowserinformation;
	}
	public void setUserbrowserinformation(UserBrowserInformation userbrowserinformation) {
		this.userbrowserinformation = userbrowserinformation;
	}

    public List<UserBrowserInformation> getUserBrowserInformationfilteredList() {
		return userbrowserinformationfilteredList;
	}

	/**
	 * Prepare auto complete data
 	 * @author TechFinium
 	 * @param userbrowserinformationfilteredList the new userbrowserinformationfilteredList list
 	 * @see    UserBrowserInformation
 	 */
	public void setUserBrowserInformationfilteredList(List<UserBrowserInformation> userbrowserinformationfilteredList) {
		this.userbrowserinformationfilteredList = userbrowserinformationfilteredList;
	}


	public LazyDataModel<UserBrowserInformation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UserBrowserInformation> dataModel) {
		this.dataModel = dataModel;
	}

	public MapModel getCircleModel() {
		return circleModel;
	}

	public void setCircleModel(MapModel circleModel) {
		this.circleModel = circleModel;
	}

	public void latLangCheck() {
		try {
           service.resolveIP(this.userbrowserinformation);
           if (this.userbrowserinformation.getLatitude()!=null) {
        	   circleModel = new DefaultMapModel();
        	   LatLng coord1 = new LatLng(this.userbrowserinformation.getLatitude(), this.userbrowserinformation.getLongitude());
        	    //Circle
               Circle circle1 = new Circle(coord1, 500);
               circle1.setStrokeColor("#d93c3c");
               circle1.setFillColor("#d93c3c");
               circle1.setFillOpacity(0.3);
               circleModel.addOverlay(circle1);
               circleModel.addOverlay(new Marker(coord1, this.userbrowserinformation.getIpAddress()));
           }

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

}
