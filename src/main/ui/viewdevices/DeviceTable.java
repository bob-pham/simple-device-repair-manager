package ui.viewdevices;

import javax.swing.table.AbstractTableModel;

public abstract class DeviceTable extends AbstractTableModel {
    protected String[] header;

    private Object[][] data;

    public DeviceTable() {
        super();
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    //EFFECT: returns the length of a row of the table
    @Override
    public int getRowCount() {
        return data.length;
    }

    //EFFECT: returns the number of columns in the table
    @Override
    public int getColumnCount() {
        return header.length;
    }

    //EFFECT: returns the value at index [rowIndex][columnIndex] in data array
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    //EFFECT: makes all cells non-editable
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    //EFFECT: returns the name of the column
    @Override
    public String getColumnName(int col) {
        return header[col];
    }
}
