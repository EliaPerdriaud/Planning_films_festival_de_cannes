/***********************************************************************
 * Module:  Planning.java
 * Author:  pauli
 * Purpose: Defines the Class Planning
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


/** @pdOid 6e47cc36-96e0-4e9e-8f19-f1c220875800 */
public class Planning {
    private static Connection conn = Bdd.coBdd();
    
    
    public static int countPlanning(){ 
        int i = 0;
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT COUNT(*) FROM Planning");
            
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
    
    public static int CréerPlanning(int idcategorie){ 
        int etat = 0;
        try {
            String sql = "INSERT INTO Planning(Categorie_idCategorie) VALUE (?)";
            PreparedStatement prepare = conn.prepareStatement(sql);
            
            prepare.setInt(1, idcategorie);
            
            prepare.execute();
            prepare.close();
            etat = 1;
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Planning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return etat;
    }
    
    public static String[][] recupPlanning(){
        String[][] tabPlanning = new String[countPlanning()][2]; 
        int i=0;
        
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM Planning");
            
            while(result.next()){
                tabPlanning[i][0] =Categories.getNomCat(result.getInt("Categorie_idCategorie"));
                tabPlanning[i][1] =result.getString("idPlanning");
                i++;
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Planning.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tabPlanning;
    }
    

    public static int suppPlanning(int idPlanning){
        int etat = 0;
        Seance.SuppSeancePlanning(idPlanning);
        try {
            String sql = "DELETE FROM Planning WHERE idPlanning = ?";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1,idPlanning);

            prepare.execute();
            prepare.close(); 
            etat=1;  
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Planning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return etat;
    }
    
    
    public static int getIdPlanning(int idCategorie){

        int idPlaning=0;
        
        try {   
            String sql = "SELECT idPlanning FROM Planning WHERE Categorie_idCategorie = ?";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1,idCategorie);
            ResultSet result = prepare.executeQuery();

            while(result.next()){
                idPlaning = result.getInt("idPlanning");
            }

            result.close();
            prepare.execute();
            prepare.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Planning.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idPlaning;
    }
    
    public static int getIdCatPlanning(int idPlanning){

        int idCategorie=0;
        
        try {   
            String sql = "SELECT Categorie_idCategorie FROM Planning WHERE idPlanning = ?";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1,idPlanning);
            ResultSet result = prepare.executeQuery();

            while(result.next()){
                idCategorie = result.getInt("Categorie_idCategorie");
            }

            result.close();
            prepare.execute();
            prepare.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Planning.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idCategorie;
    }
    
}