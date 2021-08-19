package dao;

import java.sql.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
public class DatabaseConnection {

    private Connection con;

    public DatabaseConnection() throws SQLException {
        initialiseConnection();
    }

    public void initialiseConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentmanaging", "root",
                "root");
        this.con = con;
    }

    public void closeConnection() throws SQLException {
        con.close();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return con.prepareStatement(sql);
    }

}
