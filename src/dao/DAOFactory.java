package dao;

public class DAOFactory {

    public StudentDAO getStudentDAO(String inputType){
        if(inputType == null){
            return null;
        }
        if(inputType.equalsIgnoreCase("File")){
            return FileStudentDAO.getInstance();

        } else if(inputType.equalsIgnoreCase("Database")) {
            return DatabaseStudentDAO.getInstance();
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
        }
        return null;
    }
}
