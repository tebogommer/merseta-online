package haj.com.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class AmountBean.
 */
public class ColumnBeans implements Serializable {

	private List<Object> objects;
	private List<ColumnBean> columns;
	private String tableID;

	public ColumnBeans(List<Object> objects, List<ColumnBean> columns, String tableID) {
		super();
		this.objects = objects;
		this.columns = columns;
		this.tableID = tableID;
	}

	public List<Object> getObjects() {
		return objects;
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}

	public List<ColumnBean> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnBean> columns) {
		this.columns = columns;
	}

	public String getTableID() {
		return tableID;
	}

	public void setTableID(String tableID) {
		this.tableID = tableID;
	}
}
