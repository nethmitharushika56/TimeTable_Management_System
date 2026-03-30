Programming Quest 09

NAME 			INDEX
THARUSHIKA A.K.N.	23/ENG/140
PATHIRAGE L.P.D.P.G     23/ENG/091



1. TECHNICAL CONSTRAINTS

- DB Connecting Jar: mysql-connector-j-9.6.0.jar
- Primary Key: Assigned automatically via AUTO_INCREMENT

2. DATABASE SETUP

1. Open XAMPP Control Panel and start Apache and MySQL.
2. Navigate to http://localhost/phpmyadmin.
3. Create a database named: timetabledb
4. Import the provided 'timetabledb.sql' file.
   - Note: The database password is set to ''
 
3. SYSTEM CONSTRAINTS

* WEEKLY HOURS: Each subject is automatically scheduled until its specific 
  'weekly_hours' count is reached.
* 5-DAY / 6-SLOT GRID: The system only generates slots for Monday through 
  Friday, with 6 slots per day (Total 30 slots).
* TEACHER AVAILABILITY: A teacher CANNOT be assigned to more than one 
  class at the same time.
* MULTIPLE SUBJECTS: While a teacher can handle two different subjects, 
  the system ensures they are scheduled in different slots.
* CLASS CONFLICTS: A class cannot have more than one subject in the 
  same time slot.

4. HOW TO RUN THE APPLICATION

1. Add 'mysql-connector-java.jar' to your project's Build Path.
2. Run 'MainApp.java'.
3. Use 'Manage Subjects' and 'Manage Teachers' to input data.
   - IMPORTANT: The 'Subject Code' in the Subjects table MUST match 
     the 'Subject Handled' in the Teachers table.
4. Click 'Generate Timetable' to see the Class-Wise and Teacher-Wise 
   automated views.
5. Use the 'REFRESH DATA' button to update the view after making 
   changes to the database.
