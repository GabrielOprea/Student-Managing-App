package dao;

import dao.databaseImplementation.DatabaseCourseDAO;
import dao.databaseImplementation.DatabaseStudentDAO;
import dao.fileImplementation.FileCourseDAO;
import dao.fileImplementation.FileStudentDAO;
import dao.hibernateImplementation.HibernateCourseDAO;
import dao.hibernateImplementation.HibernateStudentDAO;
import dao.interfaces.CourseDAO;
import dao.interfaces.StudentDAO;



public class DAOFactory {

    public StudentDAO getStudentDAO(String inputType){
        if(inputType == null){
            return null;
        }
        if(inputType.equalsIgnoreCase("File")){
            return FileStudentDAO.getInstance();
        } else if(inputType.equalsIgnoreCase("Database")) {
            return DatabaseStudentDAO.getInstance();
        } else if(inputType.equalsIgnoreCase("Hibernate")) {
            return HibernateStudentDAO.getInstance();
        }
        return null;
    }

    public CourseDAO getCourseDAO(String inputType){
        if(inputType == null){
            return null;
        }
        if(inputType.equalsIgnoreCase("File")){
            return FileCourseDAO.getInstance();
        } else if(inputType.equalsIgnoreCase("Database")) {
            return DatabaseCourseDAO.getInstance();
        } else if(inputType.equalsIgnoreCase("Hibernate")) {
            return HibernateCourseDAO.getInstance();
        }
        return null;
    }
}
