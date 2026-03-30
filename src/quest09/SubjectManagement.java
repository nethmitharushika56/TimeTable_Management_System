package quest09;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SubjectManagement extends JFrame {
    private JTextField txtCode, txtName, txtHours;
    private JTable table;
    private DefaultTableModel tableModel;

    public SubjectManagement() {
        setTitle("Manage Subjects - Quest 09");
        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Subject"));

        inputPanel.add(new JLabel("Subject Code:"));
        txtCode = new JTextField();
        inputPanel.add(txtCode);

        inputPanel.add(new JLabel("Subject Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Weekly Hours:"));
        txtHours = new JTextField();
        inputPanel.add(txtHours);

        JButton btnAdd = new JButton("Add Subject");
        inputPanel.add(btnAdd);
        add(inputPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Code", "Name", "Weekly Hours"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addSubject());
        loadTableData();
        setVisible(true);
    }

    private void addSubject() {
        if (txtCode.getText().isEmpty() || txtName.getText().isEmpty() || txtHours.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TimetableDB", "root", "yourpassword")) {
            String sql = "INSERT INTO Subjects (code, name, weekly_hours) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtCode.getText());
            pstmt.setString(2, txtName.getText());
            pstmt.setInt(3, Integer.parseInt(txtHours.getText()));
            pstmt.executeUpdate();
            loadTableData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TimetableDB", "root", "yourpassword")) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Subjects");
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getString("code"), rs.getString("name"), rs.getInt("weekly_hours")});
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}