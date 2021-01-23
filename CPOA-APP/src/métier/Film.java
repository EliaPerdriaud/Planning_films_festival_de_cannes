/***********************************************************************
 * Module:  Film.java
 * Author:  pauli
 * Purpose: Defines the Class Film
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

/** @pdOid 4af33ebf-e408-438b-b04f-1445b0a9194c */
public class Film {
    private static Connection conn = Bdd.coBdd();
    
    
    public static int countFilm(){ 
        int i = 0;
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT COUNT(*) FROM Film");
            
            while(result.next()){
                i = result.getInt("COUNT(*)");
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Film.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    public static int countFilmCat(int cat){ 
        int i = 0;
        
        if (cat == 3){
            try {
                String sql = "SELECT COUNT(*) FROM Film WHERE Categorie_idCategorie = ? OR F_EnCompetition = 0";
                        PreparedStatement prepare = conn.prepareStatement(sql);
                        prepare.setInt(1,cat);
                        ResultSet result = prepare.executeQuery();

                        while(result.next()){
                            i = result.getInt("COUNT(*)");
                        }

                result.close();
                prepare.execute();
                prepare.close(); 
            } catch (CommunicationsException e) {
                Bdd.lostCO(e);
            } catch (SQLException ex) {
                Logger.getLogger(Film.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            try {
                String sql = "SELECT COUNT(*) FROM Film WHERE Categorie_idCategorie = ? ";
                        PreparedStatement prepare = conn.prepareStatement(sql);
                        prepare.setInt(1,cat);
                        ResultSet result = prepare.executeQuery();

                        while(result.next()){
                            i = result.getInt("COUNT(*)");
                        }

                result.close();
                prepare.execute();
                prepare.close(); 
            } catch (CommunicationsException e) {
                Bdd.lostCO(e);
            } catch (SQLException ex) {
                Logger.getLogger(Film.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return i;
    }
    
    
    public static Object[][] DonnéeFilmCat(int cat){
       Object[][] film = new Object[countFilmCat(cat)][4];
       int i = 0;
       
       if (cat == 3){
            try {
                String sql = "SELECT * FROM Film WHERE Categorie_idCategorie = ? OR F_EnCompetition = 0";
                        PreparedStatement prepare = conn.prepareStatement(sql);
                        prepare.setInt(1,cat);
                        ResultSet result = prepare.executeQuery();

                        while(result.next()){
                            film[i][0] = result.getInt("idFilm");
                            film[i][1] = result.getString("F_Nom");
                            film[i][2] = result.getString("F_DateSortie");
                            
                            if(result.getString("Categorie_idCategorie")==null){
                                film[i][3]=-1;
                            }
                            else{
                                film[i][3] = result.getInt("Categorie_idCategorie");
                            }

                            i++;
                        }

                result.close();
                prepare.execute();
                prepare.close(); 
            } catch (CommunicationsException e) {
                Bdd.lostCO(e);
            } catch (SQLException ex) {
                Logger.getLogger(Film.class.getName()).log(Level.SEVERE, null, ex);
            }
       }else{
           try {
                String sql = "SELECT * FROM Film WHERE Categorie_idCategorie = ? ";
                        PreparedStatement prepare = conn.prepareStatement(sql);
                        prepare.setInt(1,cat);
                        ResultSet result = prepare.executeQuery();

                        while(result.next()){
                            film[i][0] = result.getInt("idFilm");
                            film[i][1] = result.getString("F_Nom");
                            film[i][2] = result.getString("F_DateSortie");
                            film[i][3] = result.getInt("Categorie_idCategorie");

                            i++;
                        }

                result.close();
                prepare.execute();
                prepare.close(); 
            } catch (CommunicationsException e) {
                Bdd.lostCO(e);
            } catch (SQLException ex) {
                Logger.getLogger(Film.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       return film;
    }
    
    public static String[][] recupFilm(){ 
        String[][] film = new String[countFilm()+1][2];
        try {   
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT F_Nom, idFilm FROM Film");
            int i=0;
            while(result.next()){
                film[i][0] = result.getString("F_Nom");
                film[i][1] = result.getString("idFilm");
                i++;
            }
            result.close();
            state.close();
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Film.class.getName()).log(Level.SEVERE, null, ex);
        }
        return film;
    }
    
    public static int getDuree(int idFilm){
    int duree= 0;
        try {
            String sql = "SELECT F_Durée FROM Film WHERE idFilm = ?";
                PreparedStatement prepare = conn.prepareStatement(sql);
                prepare.setInt(1,idFilm);
                ResultSet result = prepare.executeQuery();

                while(result.next()){
                    duree = result.getInt("F_Durée");

                }

            result.close();
            prepare.execute();
            prepare.close(); 
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Film.class.getName()).log(Level.SEVERE, null, ex);
        }
        return duree;
    }
   
    
    public static String getCat(int idFilm){
    String categorie= null;
        try {
            String sql = "SELECT Categorie_idCategorie FROM Film WHERE idFilm = ?";
                PreparedStatement prepare = conn.prepareStatement(sql);
                prepare.setInt(1,idFilm);
                ResultSet result = prepare.executeQuery();

                while(result.next()){
                    categorie = result.getString("Categorie_idCategorie");

                }

            result.close();
            prepare.execute();
            prepare.close(); 
        } catch (CommunicationsException e) {
            Bdd.lostCO(e);
        } catch (SQLException ex) {
            Logger.getLogger(Film.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categorie;
    }
}