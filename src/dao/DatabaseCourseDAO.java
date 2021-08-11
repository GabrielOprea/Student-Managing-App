package dao;

import java.sql.*;

public class DatabaseCourseDAO implements CourseDAO {

    private static DatabaseCourseDAO INSTANCE = null;
    private Connection con;
    private Statement stmt;
    private final String dbName;
    private final String coursesTable;
    private final String studentsTable;
    private final String studToCoursesTable;

    private DatabaseCourseDAO() {
        dbName = "studentmanaging";
        coursesTable = dbName + ".courses";
        studToCoursesTable = dbName + ".studentstocourses";
        studentsTable = dbName + ".students";
    }

    public static DatabaseCourseDAO getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DatabaseCourseDAO();
        }

        return INSTANCE;
    }

    @Override
    public void addCourse(int registrationNum, String courseTitle, String description) throws Exception {

        String sql = "INSERT INTO " + coursesTable + " VALUES (?, ?)";
        DatabaseConnection dbConnection = new DatabaseConnection();
        PreparedStatement stmt = dbConnection.prepareStatement(sql);
        stmt.setString(1, courseTitle);
        stmt.setString(2, description);
        stmt.executeUpdate();

        sql = "INSERT INTO " + studToCoursesTable + " VALUES (?, ?)";
        stmt = dbConnection.prepareStatement(sql);
        stmt.setInt(1, registrationNum);
        stmt.setString(2, courseTitle);
        stmt.executeUpdate();

        dbConnection.closeConnection();
    }

    @Override
    public void printCourses(int registrationNum) throws Exception {
        String sql = "SELECT a.title, a.description FROM " + coursesTable + " a " +
                    "NATURAL JOIN " + studToCoursesTable + " b " +
                    "NATURAL JOIN " + studentsTable + " c " +
                    "WHERE c.registrationNumber = ?";

        DatabaseConnection dbConnection = new DatabaseConnection();

        PreparedStatement stmt = dbConnection.prepareStatement(sql);
        stmt.setInt(1, registrationNum);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString(1) + "  " + rs.getString(2));
        }

        dbConnection.closeConnection();
    }
}
