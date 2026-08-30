
package haj.com.bean;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tiefaces.components.websheet.TieWebSheetBean;

import haj.com.constants.HAJConstants;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressLookup.
 */
public class WebSheetViewer extends TieWebSheetBean {

	private static final long serialVersionUID = 1L;
	private static final String path = HAJConstants.APP_PATH;
	private List<TestBean> itemList = null;
	private UsersService usersService = new UsersService();

	public WebSheetViewer() {
		try {
//	        this.setSkipConfiguration(true);
			itemList = new ArrayList<TestBean>();
			itemList.add(new TestBean());
			HashMap<String, Object> context = new HashMap<String, Object>();
			context.put("items", itemList);
			InputStream reportStream = new BufferedInputStream(new FileInputStream(path + "/templates/WebSheetTemplate-5.xlsx"));
		    loadWebSheet(reportStream, context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getData() {
		Map<String, Object>m = getDataContext();
		for (TestBean users : itemList) {
			System.out.println(users);
		}
	}

}