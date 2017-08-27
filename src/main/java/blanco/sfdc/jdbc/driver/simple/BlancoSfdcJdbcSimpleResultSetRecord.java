package blanco.sfdc.jdbc.driver.simple;

import java.util.ArrayList;
import java.util.List;

public class BlancoSfdcJdbcSimpleResultSetRecord {
	private List<BlancoSfdcJdbcSimpleResultSetRecordItem> itemList = new ArrayList<BlancoSfdcJdbcSimpleResultSetRecordItem>();

	public List<BlancoSfdcJdbcSimpleResultSetRecordItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<BlancoSfdcJdbcSimpleResultSetRecordItem> itemList) {
		this.itemList = itemList;
	}
}
