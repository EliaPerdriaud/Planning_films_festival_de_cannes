/***********************************************************************
 * Module:  Categories.java
 * Author:  pauli
 * Purpose: Defines the Class Categories
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

/** @pdOid f97ba32a-c06a-4ac9-a460-10b05adf0c73 */
public class Categories {
    private static Connection conn = Bdd.coBdd();
    
    
    public static String[][] recupCatDispo(){
        String[][] CategorieNonPlanifié = new String[4][2]; 
        try {
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM Categorie WHERE Categorie.idCategorie NOT IN (SELECT Categorie_idCategorie FROM Planning)");
            //ResultSet result = state.executeQuery("SELECT * FROM Categorie WHERE Categorie.idCategorie = 22");
            
            int i = 0;
            while(result.next()){
                CategorieNonPlanifié[i][0] = result.getString("C_Nom");
                //System.out.println(result.getString("C_Nom"));
                CategorieNonPlanifié[i][1] = result.getString("idCategorie");
                //System.out.println(result.getString("idCategorie"));
                //System.out.println("--");
                i++;
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CategorieNonPlanifié;
    }
    
    public static String getNomCat(int id){
        String nomCat= null;
        try {
            String sql = "SELECT C_Nom FROM Categorie WHERE idCategorie = ?";
                PreparedStatement prepare = conn.prepareStatement(sql);
                prepare.setInt(1,id);
                ResultSet result = prepare.executeQuery();

                while(result.next()){
                    nomCat = result.getString("C_Nom");

                }

            result.close();
            prepare.execute();
            prepare.close(); 
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nomCat;
    }
}