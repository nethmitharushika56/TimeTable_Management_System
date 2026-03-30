package quest09;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TeacherManagement extends JFrame {
    private JTextField txtId, txtName, txtSubject;
    private JTable table;
    private DefaultTableModel tableModel;

    public TeacherManagement() {
        setTitle("Manage Teachers - Quest 09");
        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Teacher"));

        inputPanel.add(new JLabel("Teacher ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Teacher Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Subject Handled:"));
        txtSubject = new JTextField();
        inputPanel.add(txtSubject);

        JButton btnAdd = new JButton("Add Teacher");
        inputPanel.add(btnAdd);
        add(inputPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Teacher ID", "Name", "Subject"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addTeacher());
        loadTableData();
        setVisible(true);
    }

    private void addTeacher() {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtSubject.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TimetableDB", "root", "yourpassword")) {
            String sql = "INSERT INTO Teachers (id, name, subject_handled) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtId.getText());
            pstmt.setString(2, txtName.getText());
            pstmt.setString(3, txtSubject.getText());
            pstmt.executeUpdate();
            loadTableData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TimetableDB", "root", "yourpassword")) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Teachers");
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getString("id"), rs.getString("name"), rs.getString("subject_handled")});
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}