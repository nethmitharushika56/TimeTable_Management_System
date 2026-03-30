package quest09;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Map;

public class TimetableDisplay extends JFrame {
    public TimetableDisplay(Map<String, Map<String, String[][]>> data) {
        setTitle("VIEW TIMETABLE - 23_ENG_140"); //
        setSize(1100, 750);
        setLayout(new BorderLayout());

        JButton btnRefresh = new JButton("REFRESH DATA");
        btnRefresh.addActionListener(e -> { this.dispose(); new MainApp().generateAction(); });
        
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnRefresh);
        add(top, BorderLayout.NORTH);

        JTabbedPane outerTabs = new JTabbedPane();
        outerTabs.addTab("CLASS-WISE VIEW", createTabs(data.get("CLASSES"), "Class "));
        outerTabs.addTab("TEACHER-WISE VIEW", createTabs(data.get("TEACHERS"), ""));

        add(outerTabs, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private JTabbedPane createTabs(Map<String, String[][]> map, String prefix) {
        JTabbedPane tp = new JTabbedPane();
        String[] header = {"Day/Slot", "Slot 1", "Slot 2", "Slot 3", "Slot 4", "Slot 5", "Slot 6"};
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        int count = 1; 
        for (String key : map.keySet()) {
            DefaultTableModel mod = new DefaultTableModel(header, 0);
            String[][] g = map.get(key);
            for (int d = 0; d < 5; d++) {
                Object[] r = new Object[7];
                r[0] = days[d];
                for (int s = 0; s < 6; s++) {
                    if (g[d][s] != null) {
                        String[] p = g[d][s].split("\n");
                        r[s+1] = "<html><center><b>" + p[0] + "</b><br>" + p[1] + "</center></html>";
                    } else { r[s+1] = "---"; }
                }
                mod.addRow(r);
            }
            JTable t = new JTable(mod);
            t.setRowHeight(80);
            t.setShowGrid(true);
            t.setGridColor(Color.BLACK);
            
            String tabName = prefix.isEmpty() ? key : prefix + count; 
            tp.addTab(tabName, new JScrollPane(t));
            count++;
        }
        return tp;
    }
}