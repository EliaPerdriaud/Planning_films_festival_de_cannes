/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package métier;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import donnée.Bdd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author etien
 */
public class Date {
    
    private static Connection conn = Bdd.coBdd();
    
        public static int countDate(){ 
        int i = 0;
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT COUNT(*) FROM Date");
            
            while(result.next()){
                i = result.getInt("COUNT(*)");
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Date.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i+1;
    }
    
    public static String[][] recupDate(){
        String[][] tabHoraires = new String[countDate()][2]; 
        try {
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM Date");
            
            int i = 0;
            while(result.next()){
                tabHoraires[i][0] = result.getString("D_Date");
                tabHoraires[i][1] = result.getString("idDate");
                i++;
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Date.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tabHoraires;
    }
    
    public static String[][] recupDate2(){
        String[][] tabHoraires = new String[countDate()-1][2]; 
        try {
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM Date");
            
            int i = 0;
            while(result.next()){
                tabHoraires[i][0] = result.getString("idDate");
                tabHoraires[i][1] = result.getString("D_Date");
                i++;
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Date.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tabHoraires;
    }
    
    
    
    
    public static void majDate(String id, String date){
        try {
                    String sql = "UPDATE Date SET D_Date= ? WHERE idDate = ?";
                    PreparedStatement majDate = conn.prepareStatement(sql);
                    majDate.setString(1, date);
                    majDate.setString(2, id);
                    
                    majDate.execute();
                    majDate.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Date.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
}
