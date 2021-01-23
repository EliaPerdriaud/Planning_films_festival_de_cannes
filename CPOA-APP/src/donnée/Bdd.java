package donnée;


import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author etien
 */
public class Bdd {// Toutes les variables de connexion au serveur ou sont stockées nos bases de données MySQL/PHP My admin
    private final static String BDD = "CPOA";
    private final static String url = "jdbc:mysql://etienne-brunel.freeboxos.fr:3306/" + BDD + "?useUnicode=yes&characterEncoding=UTF-8";
    private final static String user = "CPOA";
    private final static String passwd = "1234";
    private static Connection conn;

    
    public static Connection coBdd(){// Se connecte au serveur avec les informations fournies plus haut
        conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, passwd);
            //System.out.println("Connecté");
        } catch (Exception e){
            System.out.println("Erreur co BDD");
            vue.AccueilFDC.erreurDeConnexionBdd.setVisible(true);
        }
        
        return conn;
    }
    public static void lostCO(CommunicationsException e){//QUand on perd la connexion , on lance la fenetre d'erreur de connexion(ne pas oublier dans le catch)
        System.out.println("Connexion perdus");
        vue.AccueilFDC.erreurDeConnexionBdd.setVisible(true);
    }
}
