package fr.dwarfconan.produitsvoyageurs.ui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import fr.dwarfconan.produitsvoyageurs.SimpleContract;

public class ContractTableCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object value,
            final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        final JList<SimpleContract> list = new JList<>((SimpleContract[]) value);
        list.setEnabled(false);
        return list;
    }

}
