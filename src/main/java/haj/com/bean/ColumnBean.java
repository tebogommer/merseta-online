package haj.com.bean;

import java.io.Serializable;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class AmountBean.
 */
public class ColumnBean implements Serializable {

	private String title;
	private Object val;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Object getVal() {
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}
}
