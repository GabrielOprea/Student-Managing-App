package dao;
import java.sql.*;

public class DatabaseDAO implements DAO {

    private static DatabaseDAO INSTANCE = null;
    private Connection con;
    private Statement stmt;
    private final String dbName;
    private final String studentsTable;
    private final String coursesTable;
    private final String studToCoursesTable;

    private DatabaseDAO() {
        dbName = "studentmanaging";
        studentsTable = dbName + ".students";
        coursesTable = dbName + ".courses";
        studToCoursesTable = dbName + ".studentstocourses";
    }

    public static DatabaseDAO getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DatabaseDAO();
        }

        return INSTANCE;
    }

    private void initialiseConnection() throws SQLException {
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/" + dbName, "root",
                "root");

        stmt = con.createStatement();
    }

    private void closeConnection() throws SQLException {
        con.close();
    }

    @Override
    public void createStudent(String firstName, String lastName, int registrationNum) throws Exception {
        initialiseConnection();
        stmt.executeUpdate("INSERT INTO " + studentsTable +
                        " VALUES (" + registrationNum +",'" + firstName + "','" + lastName + "')" );

        closeConnection();
    }

    @Override
    public void showAllStudents() throws Exception {
        initialiseConnection();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + studentsTable);
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
        }

        closeConnection();
    }

    @Override
    public void addCourse(int registrationNum, String courseTitle, String description) throws Exception {
        initialiseConnection();
        stmt.executeUpdate("INSERT INTO " + coursesTable +
                " VALUES ('" + courseTitle + "','" + description + "')" );
        stmt.executeUpdate("INSERT INTO " + studToCoursesTable +
                " VALUES ('" + registrationNum + "','" + courseTitle + "')" );

        closeConnection();

    }

    @Override
    public void printCourses(int registrationNum) throws Exception {
        initialiseConnection();

        String query = "SELECT a.title, a.description FROM " + coursesTable + " a " +
                        "NATURAL JOIN " + studToCoursesTable + " b " +
                        "NATURAL JOIN " + studentsTable + " c " +
                        "WHERE c.registrationNumber=" + registrationNum;

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getString(1) + "  " + rs.getString(2));
        }
        closeConnection();
    }
}
