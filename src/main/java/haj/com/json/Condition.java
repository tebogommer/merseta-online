
package haj.com.json;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// TODO: Auto-generated Javadoc
/**
 * The Class Condition.
 */
public class Condition implements Serializable {

	/** The text. */
	@SerializedName("text")
	@Expose
	private String text;

	/** The icon. */
	@SerializedName("icon")
	@Expose
	private String icon;

	/** The code. */
	@SerializedName("code")
	@Expose
	private Integer code;

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Sets the icon.
	 *
	 * @param icon
	 *            the new icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code
	 *            the new code
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

}
