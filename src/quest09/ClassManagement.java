package quest09;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ClassManagement extends JFrame {
    private JTextField txtId, txtName, txtStudents;
    private JTable table;
    private DefaultTableModel tableModel;

    public ClassManagement() {
        setTitle("Manage Classes - Quest 09");
        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));

        // --- Input Panel (Top) ---
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Class"));

        inputPanel.add(new JLabel("Class ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Class Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Number of Students:"));
        txtStudents = new JTextField();
        inputPanel.add(txtStudents);

        JButton btnAdd = new JButton("Add Record");
        inputPanel.add(btnAdd);
        
        add(inputPanel, BorderLayout.NORTH);

        // --- Table Panel (Center) ---
        tableModel = new DefaultTableModel(new String[]{"Class ID", "Class Name", "No. Students"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Event Handling ---
        btnAdd.addActionListener(e -> addClass());

        // Initial Load
        loadTableData();
        setVisible(true);
    }

    private void addClass() {
        String id = txtId.getText();
        String name = txtName.getText();
        String students = txtStudents.getText();

        
        if (id.isEmpty() || name.isEmpty() || students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TimetableDB", "root", "yourpassword")) {
            String query = "INSERT INTO Classes (id, name, num_students) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, Integer.parseInt(students));
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Class added successfully!");
            
            
            txtId.setText(""); txtName.setText(""); txtStudents.setText("");
            loadTableData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadTableData() {
        tableModel.setRowCount(0); // Clear existing rows
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TimetableDB", "root", "yourpassword")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Classes");
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), 
                    rs.getString("name"), 
                    rs.getInt("num_students")
                });
            }
        } catch (Exception ex) {
            System.out.println("Error loading table: " + ex.getMessage());
        }
    }
}
