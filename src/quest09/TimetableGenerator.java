package quest09;

import java.util.*;
import java.sql.*;

public class TimetableGenerator {
    private static final int DAYS = 5; 
    private static final int SLOTS = 6; 

    public Map<String, Map<String, String[][]>> generateAll() throws Exception {
        Map<String, String[][]> classTables = new HashMap<>();
        Map<String, String[][]> teacherTables = new HashMap<>();
        Set<String> teacherBusy = new HashSet<>(); 

        try (Connection conn = DBConnection.getConnection()) {
            // JOIN ensures Subject Code matches Teacher's Handled Subject
            String sql = "SELECT s.code, s.weekly_hours, t.id, t.name FROM Subjects s " +
                         "JOIN Teachers t ON s.code = t.subject_handled";
            
            List<String[]> masterList = new ArrayList<>();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                masterList.add(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
            }

            if (masterList.isEmpty()) {
                throw new Exception("Matching Failed! Ensure Subject Code matches Teacher Handled Subject.");
            }

            ResultSet rsClasses = conn.createStatement().executeQuery("SELECT name FROM Classes");
            while (rsClasses.next()) {
                String className = rsClasses.getString("name");
                String[][] grid = new String[DAYS][SLOTS];
                
                for (String[] sub : masterList) {
                    int hoursNeeded = Integer.parseInt(sub[1]); //
                    int placed = 0;

                    for (int d = 0; d < DAYS && placed < hoursNeeded; d++) {
                        for (int s = 0; s < SLOTS && placed < hoursNeeded; s++) {
                            String tKey = d + "-" + s + "-" + sub[2]; // Day-Slot-TeacherID

                            // Constraint: Slot is empty and Teacher is free
                            if (grid[d][s] == null && !teacherBusy.contains(tKey)) {
                                grid[d][s] = sub[0] + "\n" + sub[3]; // Subject Code + Teacher Name
                                teacherBusy.add(tKey); 
                                
                                teacherTables.putIfAbsent(sub[3], new String[DAYS][SLOTS]);
                                teacherTables.get(sub[3])[d][s] = sub[0] + "\n" + className;
                                placed++;
                            }
                        }
                    }
                    if (placed < hoursNeeded) {
                        throw new Exception("Conflict: Could not find enough slots for " + sub[0]);
                    }
                }
                classTables.put(className, grid);
            }
        }
        Map<String, Map<String, String[][]>> results = new HashMap<>();
        results.put("CLASSES", classTables);
        results.put("TEACHERS", teacherTables);
        return results;
    }
}