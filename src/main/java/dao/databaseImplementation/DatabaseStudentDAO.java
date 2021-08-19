package dao.databaseImplementation;
import dao.DatabaseConnection;
import dao.interfaces.StudentDAO;
import dto.Course;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DatabaseStudentDAO implements StudentDAO {

    private static Logger logger = Logger.getLogger(DatabaseStudentDAO.class.getName());
    private static DatabaseStudentDAO INSTANCE = null;
    private final String dbName;
    private final String studentsTable;


    private DatabaseStudentDAO() {
        dbName = "studentmanaging";
        studentsTable = dbName + ".students";
    }

    public static DatabaseStudentDAO getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DatabaseStudentDAO();
        }

        return INSTANCE;
    }

    @Override
    public void createStudent(String firstName, String lastName, int registrationNum) throws Exception {

        String sql = "INSERT INTO " + studentsTable + " VALUES(?, ?, ?)";
        DatabaseConnection dbConnection = new DatabaseConnection();
        PreparedStatement stmt = dbConnection.prepareStatement(sql);

        stmt.setInt(1, registrationNum);
        stmt.setString(2, firstName);
        stmt.setString(3, lastName);

        stmt.executeUpdate();
        dbConnection.closeConnection();
    }

    @Override
    public void showAllStudents() throws Exception {

        String sql = "SELECT * FROM " + studentsTable;
        DatabaseConnection dbConnection = new DatabaseConnection();

        PreparedStatement stmt = dbConnection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            logger.log(Level.INFO,
                    rs.getInt(1) + "  "
                            + rs.getString(2) + "  "
                            + rs.getString(3));
        }

        dbConnection.closeConnection();
    }

    @Override
    public void createStudentWithCourses(String firstName, String lastName, int registrationNum, List<Course> courses) {

    }
}
