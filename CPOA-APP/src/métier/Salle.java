/***********************************************************************
 * Module:  Salle.java
 * Author:  pauli
 * Purpose: Defines the Class Salle
 ***********************************************************************/

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

/** @pdOid cd8e6480-29ab-4283-9fbf-80987acaa1c7 */
public class Salle {
    
        private static Connection conn = Bdd.coBdd();
    
    
    public static int countSalle(){ 
        int i = 0;
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT COUNT(*) FROM Salle");
            
            while(result.next()){
                i = result.getInt("COUNT(*)");
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Planning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i+1;
    }
    
    public static String[][] recupSalle(int idCategorie,boolean lendemain){
        String[][] tabSalle = new String[countSalle()][2]; 
        int i = 0;
        if(lendemain==true){
            try {
                String sql = "SELECT * FROM Salle WHERE S_Lendemain = 1 AND Categorie_IdCategorie = ? OR Categorie_idCategorie1 = ?";
                PreparedStatement prepare = conn.prepareStatement(sql);
                prepare.setInt(1,idCategorie);
                prepare.setInt(2,idCategorie);
                ResultSet result = prepare.executeQuery();

                while(result.next()){
                    tabSalle[i][0] = result.getString("S_Nom");
                    tabSalle[i][1] = result.getString("idSalle");
                    i++;
                }

                result.close();
                prepare.execute();
                prepare.close(); 
            } catch (CommunicationsException e) {
                Bdd.lostCO(e);
            } catch (SQLException ex) {
                Logger.getLogger(Planning.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else {
            try {
                String sql = "SELECT * FROM Salle WHERE S_Lendemain = 0 AND Categorie_IdCategorie = ? OR Categorie_idCategorie1 = ?";
                PreparedStatement prepare = conn.prepareStatement(sql);
                prepare.setInt(1,idCategorie);
                prepare.setInt(2,idCategorie);
                ResultSet result = prepare.executeQuery();

                while(result.next()){
                    tabSalle[i][0] = result.getString("S_Nom");
                    tabSalle[i][1] = result.getString("idSalle");
                    i++;
                }

                result.close();
                prepare.execute();
                prepare.close(); 
            } catch (CommunicationsException e) {
                Bdd.lostCO(e);
            } catch (SQLException ex) {
                Logger.getLogger(Planning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return tabSalle;
        
    }
    
    public static String[] getNomSalles(){ 
        String [] nomSalles = new String[countSalle()-1];
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT S_Nom FROM Salle");
            int i = 0;
            while(result.next()){
                nomSalles[i] = result.getString("S_Nom");
                i++;
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Planning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nomSalles;
    }
    
    public static String[][] recupSalle(){ 
        String [][] nomSalles = new String[countSalle()][2];
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT S_Nom, idSalle FROM Salle");
            int i = 0;
            while(result.next()){
                nomSalles[i][0] = result.getString("S_Nom");
                nomSalles[i][1] = result.getString("idSalle");
                i++;
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Planning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nomSalles;
    }
   
}