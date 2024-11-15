package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class mini extends JFrame implements ActionListener {
    String pin;
    JButton button;

    mini(String pin){
        this.pin = pin;
        getContentPane().setBackground(new Color(255,204,204));
        setSize(400,600);
        setLocation(20,20);
        setLayout(null);

        JLabel label1 = new JLabel();
        label1.setBounds(20,140,400,200);
        add(label1);

        JLabel label2 = new JLabel("Transaction Database");
        label2.setFont(new Font("System", Font.BOLD,15));
        label2.setBounds(100,20,200,20);
        add(label2);

        JLabel label3 = new JLabel();
        label3.setBounds(20,80,300,20);
        add(label3);

        JLabel label4 = new JLabel();
        label4.setBounds(20,400,300,20);
        add(label4);

        // Fetch Card Number
        try {
            Connn c = new Connn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM login WHERE pin = '"+pin+"'");
            while (resultSet.next()) {
                label3.setText("Card Number: " + resultSet.getString("card_number").substring(0,4) + "XXXXXXXX" + resultSet.getString("card_number").substring(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fetch and display transaction data
        try {
            int balance = 0;
            Connn c = new Connn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM bank WHERE pin = '"+pin+"'");
            label1.setText(""); // Clear previous data
            while (resultSet.next()) {
                String amountStr = resultSet.getString("amount");
                try {
                    int amount = Integer.parseInt(amountStr); // Parse amount safely
                    if (resultSet.getString("type").equals("Deposit")) {
                        balance += amount;
                    } else {
                        balance -= amount;
                    }
                    label1.setText(label1.getText() + "<html>" + resultSet.getString("date") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + resultSet.getString("type") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + amount + "<br><br><html>");
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing amount: " + amountStr);
                }
            }
            label4.setText("Your Total Balance is Rs " + balance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Exit button
        button = new JButton("Exit");
        button.setBounds(20,500,100,25);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new mini("");
    }
}
