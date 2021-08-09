package dao;

public class DAOFactory {
    public DAO getDAO(String inputType){
        if(inputType == null){
            return null;
        }
        if(inputType.equalsIgnoreCase("File")){
            return FileDAO.getInstance();

        } else if(inputType.equalsIgnoreCase("Database")) {
            return DatabaseDAO.getInstance();
        }
        return null;
    }
}
