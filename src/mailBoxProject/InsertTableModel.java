package mailBoxProject;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class InsertTableModel extends AbstractTableModel{
	
	Vector<Vector<String>> data;
	Vector<String> columns;
	
	InsertTableModel(Vector<Vector<String>> data,Vector<String> columns){
		this.data = data;
		this.columns = columns;
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
			return true;
	}
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Vector<String> rowdata = data.get(rowIndex);
		rowdata.set(columnIndex,(String)aValue);
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
}
