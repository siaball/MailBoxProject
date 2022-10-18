package mailBoxProject;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class MainTableModel extends AbstractTableModel{
	
	Vector<Vector<String>> data;
	Vector<String> columns;
	
	MainTableModel(Vector<Vector<String>> data,Vector<String> columns){
		this.data = data;
		this.columns = columns;
	}
	
	public String getcolumnrow(int rowIndex) {
		return columns.get(rowIndex);
	}
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object string = data.get(rowIndex).get(columnIndex);
		return string;
	}
	
	@Override
	public String getColumnName(int column) {
		return columns.get(column);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex==0) {			
			return false;
		}else {
			return true;
		}
	}
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		try {
			new Dao().updateDataById(this.getColumnName(columnIndex), (String)aValue, (String)this.getValueAt(rowIndex, 0));
		} catch (SQLException e) {
			System.out.println("匯入資料發生問題!");
			e.printStackTrace();
		}
		Vector<String> rowdata = data.get(rowIndex);
		rowdata.set(columnIndex,(String)aValue);
		fireTableCellUpdated(rowIndex, columnIndex);;
	}
	
}
