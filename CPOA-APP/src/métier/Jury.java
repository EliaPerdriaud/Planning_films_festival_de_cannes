/***********************************************************************
 * Module:  Jury.java
 * Author:  pauli
 * Purpose: Defines the Class Jury
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

/** @pdOid 171d0310-5b3c-429e-8aac-51a4313bb48a */
public class Jury {
    private static Connection conn = Bdd.coBdd();
    
    
    public static int countFilm(){ 
        int i = 0;
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT COUNT(*) FROM Jury");
            
            while(result.next()){
                i = result.getInt("COUNT(*)");
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Jury.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    
    public static int getIdJuryCat(int idcat){
        int idJury = 0;
        try {
            String sql = "SELECT idJury FROM Jury WHERE Categorie_idCategorie = ?";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1,idcat);
            ResultSet result = prepare.executeQuery();

            while(result.next()){
                idJury = result.getInt("idJury");
            }

            result.close();
            prepare.execute();
            prepare.close(); 
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Jury.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idJury;
    }
}