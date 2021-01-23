/***********************************************************************
 * Module:  Horaire.java
 * Author:  pauli
 * Purpose: Defines the Class Horaire
 ***********************************************************************/

package métier;


import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import donnée.Bdd;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @pdOid e8ce7113-5633-44d5-9202-2bf56680c3c4 */
public class Horaire {
    private static Connection conn = Bdd.coBdd();
    
    
    public static int countHoraire(){ 
        int i = 0;
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT COUNT(*) FROM Horaire");
            
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
    
    public static String[][] recupHoraire(){
        String[][] tabHoraires = new String[countHoraire()][3]; 
        try {
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM Horaire");
            
            int i = 0;
            while(result.next()){
                tabHoraires[i][0] = result.getString("H_Debut");
                tabHoraires[i][1] = result.getString("idHoraire");
                tabHoraires[i][2] = result.getString("H_nom");
                i++;
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tabHoraires;
    }
    
//    public static Time getHoraireDebut(int idHoraire){
//        Time horaire= null;
//        try {
//            String sql = "SELECT H_debut FROM Horaire WHERE idHoraire = ?";
//                PreparedStatement prepare = conn.prepareStatement(sql);
//                prepare.setInt(1,idHoraire);
//                ResultSet result = prepare.executeQuery();
//
//                while(result.next()){
//                    horaire = result.getTime("H_debut");
//
//                }
//
//            result.close();
//            prepare.execute();
//            prepare.close(); 
//        } catch (CommunicationsException e) {
//            Bdd.lostCO(e);
//        } catch (SQLException ex) {
//            Logger.getLogger(Horaire.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return horaire;
//    }
   
}