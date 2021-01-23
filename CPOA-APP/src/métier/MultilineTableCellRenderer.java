package métier;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;


public class MultilineTableCellRenderer extends JTextArea
implements TableCellRenderer {
    private List<List<Integer>> rowColHeight = new ArrayList<>();
    private static final int MAX_LEN = 150;
    
    public MultilineTableCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }
    
    protected String shortener(String str) {
        if (str.length() < MAX_LEN) {
            return str;
        } else {
          return str.substring(0, MAX_LEN - 10) + "...";
        }
    }
    
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        setFont(table.getFont());    
        

        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
            if (table.isCellEditable(row, column)) {
                setForeground(UIManager.getColor("Table.focusCellForeground"));
                setBackground(UIManager.getColor("Table.focusCellBackground"));
            }
        } else {
            setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(208, 208, 208)));
        }
        if (value != null) {
            setText(shortener(value.toString()));
        } else {
            setText("");
        }
        adjustRowHeight(table, row, column);
        return this;
    }
    

    private void adjustRowHeight(JTable table, int row, int column) {

        int cWidth = table.getTableHeader().getColumnModel().getColumn(column).getWidth();
        setSize(new Dimension(cWidth, 1000));
        int prefH = getPreferredSize().height;
        while (rowColHeight.size() <= row) {
            rowColHeight.add(new ArrayList<Integer>(column));
        }
        List<Integer> colHeights = rowColHeight.get(row);
        while (colHeights.size() <= column) {
            colHeights.add(0);
        }
        colHeights.set(column, prefH);
        int maxH = prefH;
        for (Integer colHeight : colHeights) {
            if (colHeight > maxH) {
                maxH = colHeight;
            }
        }
        if (table.getRowHeight(row) != maxH) {
            table.setRowHeight(row, maxH);
        }
    }
}