package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.Vector;

public class PosMenu {
    private JPanel rootPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton button13;
    private JButton button14;
    private JButton button15;
    private JButton button16;
    private JButton payButton;
    private JButton printButton;
    private JButton buttonDelete;
    private JButton buttonDeleteAll;
    private JLabel q1;
    private JLabel q2;
    private JLabel q3;
    private JLabel q4;
    private JLabel q5;
    private JLabel q6;
    private JLabel q7;
    private JLabel q8;
    private JLabel q9;
    private JLabel q10;
    private JLabel q11;
    private JLabel q12;
    private JLabel q13;
    private JLabel q14;
    private JLabel q15;
    private JLabel q16;
    private JTextField payInput;
    private JTextArea bills;
    private JLabel totalText;
    private JLabel balanceText;
    private JTable table1;
    private JTabbedPane tabbedPane1;
    private JLabel[] quantityLabels;
    private JButton[] itemButtons;

    public PosMenu() {
        initializeComponents();
        createTable();
        createStyle();
        setupItemButtons();
        setupDeleteButtons();
        setupPayButton();
        setupPrintButton();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void initializeComponents() {
        quantityLabels = new JLabel[]{q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16};
        itemButtons = new JButton[]{button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15, button16};
    }

    private void createTable() {
        table1.setModel(new DefaultTableModel(null, new String[]{"ID", "Item", "Qty", "Price"}));
    }

    private void createStyle() {
        table1.getColumnModel().getColumn(0).setPreferredWidth(30);
        table1.getColumnModel().getColumn(1).setPreferredWidth(200);
        table1.getColumnModel().getColumn(2).setPreferredWidth(50);
        table1.getColumnModel().getColumn(3).setPreferredWidth(120);
    }

    private void setupItemButtons() {
        String[] itemNames = {
                "Margaretha \nPizza", "Pepperoni \nPizza", "All-Meat \nPizza", "Chocolate \nPizza", "Chicken \nWrap",
                "All-Vege \nWrap", "Donner \nWrap", "Fries", "Nuggets", "Pepsi (1 Liter)", "Pepsi", "Coca Cola",
                "Milo", "Tango", "Fruits Shoot", "Dr Pepper"
        };
        int[] itemPrices = {
                60000, 75000, 80000, 65000, 35000, 30000, 40000, 15000, 20000,
                15000, 10000, 10000, 10000, 10000, 8000, 12000
        };

        for (int i = 0; i < itemButtons.length; i++) {
            int index = i;
            itemButtons[i].addActionListener(e -> {
                incrementQuantity(index);
                addTable(index + 1, itemNames[index], Integer.parseInt(quantityLabels[index].getText()), itemPrices[index]);
                calculateTotal();
            });
        }
    }

    private void incrementQuantity(int index) {
        int quantity = Integer.parseInt(quantityLabels[index].getText()) + 1;
        quantityLabels[index].setText(String.valueOf(quantity));
    }

    private void setupDeleteButtons() {
        buttonDelete.addActionListener(e -> {
            DefaultTableModel dt = (DefaultTableModel) table1.getModel();
            int selectedRow = table1.getSelectedRow();
            if (selectedRow >= 0) {
                int itemId = Integer.parseInt(dt.getValueAt(selectedRow, 0).toString());
                dt.removeRow(selectedRow);
                quantityLabels[itemId - 1].setText("0");
                calculateTotal();
            }
        });

        buttonDeleteAll.addActionListener(e -> {
            DefaultTableModel dt = (DefaultTableModel) table1.getModel();
            dt.setRowCount(0);
            for (JLabel label : quantityLabels) {
                label.setText("0");
            }
            calculateTotal();
        });
    }

    private void setupPayButton() {
        payButton.addActionListener(e -> {
            int total = rupiahToInt(totalText.getText());
            int paid = Integer.valueOf(payInput.getText());
            int balance = paid - total;
            if (total > paid){
                JOptionPane.showMessageDialog(rootPanel, "Uang yang dibayarkan kurang.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                balanceText.setText(intToRupiah(balance));
            }
        });
    }

    private void setupPrintButton() {
        printButton.addActionListener(e -> {
            try {
                generateBill();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void generateBill() {
        StringBuilder billContent = new StringBuilder("         FastPOS \n")
                .append("         Jl. Prof Soedarto,\n")
                .append("         Tembalang, Semarang,\n")
                .append("         +62895422529607,\n")
                .append("--------------------------------------------------------\n")
                .append("Name\tQty\tPrice\n")
                .append("--------------------------------------------------------\n");

        DefaultTableModel dt = (DefaultTableModel) table1.getModel();
        for (int i = 0; i < dt.getRowCount(); i++) {
            billContent.append(dt.getValueAt(i, 1)).append("\t")
                    .append(dt.getValueAt(i, 2)).append("\t")
                    .append(dt.getValueAt(i, 3)).append("\n");
        }

        billContent.append("--------------------------------------------------------\n")
                .append("Subtotal\t: ").append(totalText.getText().replace(" ", "\t")).append("\n")
                .append("Cash\t: Rp\t").append(intToCommaSeparated(Integer.valueOf(payInput.getText()))).append("\n")
                .append("Balance\t: ").append(balanceText.getText().replace(" ", "\t")).append("\n")
                .append("--------------------------------------------------------\n")
                .append("               Thanks for your order..!\n")
                .append("--------------------------------------------------------\n")
                .append("               Software by @pcdevv_");

        bills.setText(billContent.toString());
    }

    private void addTable(int id, String name, int qty, int price) {
        DefaultTableModel dt = (DefaultTableModel) table1.getModel();
        int totalPrice = price * qty;

        for (int row = 0; row < dt.getRowCount(); row++) {
            if (name.equals(dt.getValueAt(row, 1))) {
                dt.removeRow(row);
                break;
            }
        }

        Vector<Object> rowData = new Vector<>();
        rowData.add(id);
        rowData.add(name);
        rowData.add(qty);
        rowData.add(intToCommaSeparated(totalPrice));

        dt.addRow(rowData);
    }

    private void calculateTotal() {
        int total = 0;
        DefaultTableModel dt = (DefaultTableModel) table1.getModel();
        for (int i = 0; i < dt.getRowCount(); i++) {
            total += rupiahToInt(dt.getValueAt(i, 3).toString());
        }
        totalText.setText(intToRupiah(total));
    }

    private String intToRupiah(int amount) {
        return new DecimalFormat("'Rp' #,##0").format(amount);
    }

    private String intToCommaSeparated(int amount) {
        return new DecimalFormat("#,##0").format(amount);
    }

    private int rupiahToInt(String formattedRupiah) {
        return Integer.parseInt(formattedRupiah.replace("Rp ", "").replace(",", ""));
    }
}