package haj.com.bean;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class LookupMenuBean.
 */
public class LookupMenuBean implements Serializable {

	/** The heading. */
	private String heading;
	
	/** The link. */
	private String link;
	
	/**
	 * Instantiates a new lookup menu bean.
	 *
	 * @param heading the heading
	 * @param link the link
	 */
	public LookupMenuBean(String heading, String link) {
		super();
		this.heading = heading;
		this.link = link;
	}
	
	/**
	 * Instantiates a new lookup menu bean.
	 */
	public LookupMenuBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Gets the heading.
	 *
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}
	
	/**
	 * Sets the heading.
	 *
	 * @param heading the new heading
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}
	
	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * Sets the link.
	 *
	 * @param link the new link
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LookupMenuBean [heading=" + heading + ", link=" + link + "]";
	}
	
	
	
}
