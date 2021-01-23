/***********************************************************************
 * Module:  Seance.java
 * Author:  pauli
 * Purpose: Defines the Class Seance
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


/** @pdOid dd86f940-2535-49ae-979a-11a9c40a4050 */
public class Seance {
    private static Connection conn = Bdd.coBdd();
    
    
    public static int countSéance(){ 
        int i = 0;
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT COUNT(*) FROM Séance");
            
            while(result.next()){
                i = result.getInt("COUNT(*)");
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Seance.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    public static int countSéance(int idPlanning){ 
        int i = 0;
        try {   
            String sql = "SELECT COUNT(*) FROM Séance WHERE Planning_idPlanning = ?";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1,idPlanning);
            ResultSet result = prepare.executeQuery();
            while(result.next()){
                i = result.getInt("COUNT(*)");
                i++;
            }

            result.close();
            prepare.execute();
            prepare.close(); 
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Seance.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    
    public static int CréationSéance(int idPlanning, int idSalle, int idFilm, int idHoraire, int idCat, int idDate, boolean lendemain){ 
        int etat = 0;
        
        if (RecupMemeSeance(idSalle, idDate, idHoraire)>0){
            etat =-1; //-1 pour une séance est déjà sur ce créneau
        }
        
        if(idCat==3){
            if (NbSeanceJury(idDate,Jury.getIdJuryCat(idCat))>=3 && lendemain == false){
                etat =-2; //-2 pour trop de Long métrages dans la même journée pour le même jury
            }
        }
        else if(idCat==1){
            if(NbSeanceJury(idDate,Jury.getIdJuryCat(idCat))>=4 && lendemain == false){
                etat=-3; //-3 pour trop de UCR dans la meme journée pour le même jury
            }
        }
        
        if (etat == 0){
            try {

                String sql = "INSERT INTO Séance(S_Lendemain, Date_idDate, Horaire_idHoraire, Jury_idJury, Salle_idSalle, Film_idFilm, Planning_idPlanning) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepare = conn.prepareStatement(sql);

                prepare.setBoolean(1, lendemain);
                prepare.setInt(2, idDate);
                prepare.setInt(3, idHoraire);
                
                if(Film.getCat(idFilm)!=null){
                    prepare.setInt(4, Jury.getIdJuryCat(idCat)); //si le film est HC il n'a pas de jury
                }
                else{
                    prepare.setNull(4,java.sql.Types.NULL);
                }
                prepare.setInt(5, idSalle);
                prepare.setInt(6, idFilm);
                prepare.setInt(7, idPlanning);

                prepare.execute();
                prepare.close();
           
            } catch (CommunicationsException e) {
                Bdd.lostCO(e);
            } catch (SQLException ex) {
                Logger.getLogger(Seance.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
            return etat;
    }
    
    public static int RecupMemeSeance(int idSalle, int idDate, int idHoraire){
        int countMemeSeance =0;
        
        try{
            String sql = "SELECT COUNT(*) FROM Séance WHERE Date_idDate=? AND Salle_idSalle= ? AND Horaire_idHoraire=?";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1, idDate);
            prepare.setInt(2, idSalle);
            prepare.setInt(3,idHoraire);
            ResultSet result = prepare.executeQuery();
            
            while(result.next()){
                countMemeSeance = result.getInt("COUNT(*)");
            }
            result.close();
            prepare.execute();
            prepare.close(); 
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Seance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return countMemeSeance;
    }
    
        public static int NbSeanceJury(int idDate, int idJury){
        int countNbSeanceJury =0;
        
        try{
            String sql = "SELECT COUNT(*) FROM Séance WHERE Date_idDate=? AND Jury_idJury= ?";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1, idDate);
            prepare.setInt(2, idJury);
            ResultSet result = prepare.executeQuery();
            
            while(result.next()){
                countNbSeanceJury = result.getInt("COUNT(*)");
            }
            result.close();
            prepare.execute();
            prepare.close(); 
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Seance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return countNbSeanceJury;
    }
    
    public static int SuppSeancePlanning(int idPlanning){
        int etat=0;
            try {
                String sql = "DELETE FROM Séance WHERE Planning_idPlanning = ?";
                PreparedStatement prepare = conn.prepareStatement(sql);
                prepare.setInt(1,idPlanning);

                prepare.execute();
                prepare.close(); 
                etat=1;
            } catch (CommunicationsException e) {
                Bdd.lostCO(e);
            } catch (SQLException ex) {
                Logger.getLogger(Seance.class.getName()).log(Level.SEVERE, null, ex);
            }
            return etat;
    }
    
    public static String[][] getSeancesFormPlanning(int idPlanning){
        String[][] tabSeance = new String[countSéance(idPlanning)][6]; 
        try{
            String sql = "SELECT * FROM Séance WHERE Planning_idPlanning = ?";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1, idPlanning);
            ResultSet result = prepare.executeQuery();
            int i = 0;
            while(result.next()){
                tabSeance[i][0] = result.getString("S_Lendemain");
                tabSeance[i][1] = result.getString("Date_idDate");
                tabSeance[i][2] = result.getString("Horaire_idHoraire");
                tabSeance[i][3] = result.getString("Jury_idJury");
                tabSeance[i][4] = result.getString("Salle_idSalle");
                tabSeance[i][5] = result.getString("Film_idFilm");
                i++;
            }
            result.close();
            prepare.execute();
            prepare.close(); 
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Seance.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tabSeance;
    }
}