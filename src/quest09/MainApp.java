package quest09;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainApp extends JFrame {
    public MainApp() {
        setTitle("TIMETABLE GENERATOR"); 
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setBorder(BorderFactory.createTitledBorder("System Dashboard"));

        JButton btn1 = new JButton("Manage Classes");
        JButton btn2 = new JButton("Manage Subjects");
        JButton btn3 = new JButton("Manage Teachers");
        JButton btn4 = new JButton("Generate Timetable");
        JButton btnExit = new JButton("Exit");

        Dimension btnSize = new Dimension(280, 45);
        JButton[] btns = {btn1, btn2, btn3, btn4, btnExit};
        for (JButton b : btns) {
            b.setMaximumSize(btnSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlMain.add(Box.createRigidArea(new Dimension(0, 15)));
            pnlMain.add(b);
        }

        btn1.addActionListener(e -> new ClassManagement().setVisible(true));
        btn2.addActionListener(e -> new SubjectManagement().setVisible(true));
        btn3.addActionListener(e -> new TeacherManagement().setVisible(true));
        btn4.addActionListener(e -> generateAction());
        btnExit.addActionListener(e -> System.exit(0));

        add(pnlMain);
    }

    public void generateAction() {
        try {
            TimetableGenerator logic = new TimetableGenerator();
            Map<String, Map<String, String[][]>> data = logic.generateAll();
            new TimetableDisplay(data).setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "CONFLICT ERROR: " + ex.getMessage()); 
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
        catch (Exception e) {}
        new MainApp().setVisible(true);
    }
}