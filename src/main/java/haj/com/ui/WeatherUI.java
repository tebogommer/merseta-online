package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
//import javax.enterprise.context.SessionScoped;
import javax.faces.bean.SessionScoped;

import haj.com.entity.Blank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.json.Weather;

// TODO: Auto-generated Javadoc
/**
 * The Class WeatherUI.
 */
@ManagedBean(name = "weatherUI")
@SessionScoped
public class WeatherUI extends AbstractUI {

	
	/** The weather. */
	private Weather weather;
	
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
		this.weather = getSessionUI().getWeather();
	}

	/**
	 * Gets the weather.
	 *
	 * @return the weather
	 */
	public Weather getWeather() {
		return weather;
	}

	/**
	 * Sets the weather.
	 *
	 * @param weather the new weather
	 */
	public void setWeather(Weather weather) {
		this.weather = weather;
	}


	
}
