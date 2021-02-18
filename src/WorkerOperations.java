
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class WorkerOperations {
    
    private Connection conn = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
   
    public WorkerOperations() {
        
        String url = "jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.dbName + "?useUnicode=true&characterEncoding=utf8";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            LoginFrame frame=new LoginFrame();
            JOptionPane.showMessageDialog(frame, "Driver Not Found!"); //
        }
        
        try {
            conn = DriverManager.getConnection(url, Database.userName, Database.password);
            System.out.println("Connection Success!");
        } catch (SQLException ex) {
            Logger.getLogger(WorkerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean login(String userName, String password){
        
        String query = "Select * From admin where username=? and password=?";
        
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            return rs.next();
            
        } catch (SQLException ex) {
            LoginFrame frame=new LoginFrame();
            JOptionPane.showMessageDialog(frame, "Sql Error!");
        }
        return false;
        
    }
    
    public ArrayList<Worker> bringWorkers(){
        
        ArrayList<Worker> output = new ArrayList<Worker>();
        
        try {
            statement = conn.createStatement();
            String query = "Select * From worker";
            
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {                
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String department = rs.getString("department");
                int salary = rs.getInt("salary");
                
                output.add(new Worker(id, name, surname, department, salary));
                
            }
            return output;
            
        } catch (SQLException ex) {
            Logger.getLogger(WorkerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
    
    public void addWorker(String name, String surname, String department, int salary){
        
        String query = "Insert into worker (name, surname, department, salary) VALUES (?, ?, ?, ?)";
          
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, department);
            preparedStatement.setInt(4, salary);
            
            preparedStatement.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(WorkerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void updateWorker(int id, String name, String surname, String department, int salary){
        
        String query = "Update worker set name=?, surname=?, department=?, salary=? where id=?";
        
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, department);
            preparedStatement.setInt(4, salary);
            preparedStatement.setInt(5, id);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(WorkerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void removeWorker(int id){
        
        String query = "Delete from worker where id=?";
        
        try {
            preparedStatement =conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(WorkerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
    
    
    
    
}
