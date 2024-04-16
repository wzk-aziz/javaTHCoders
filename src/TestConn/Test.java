package TestConn;
import java.sql.*;
public class Test {

    private  static String url = "jdbc:mysql://localhost:3306/pi";
    private static String user="root";
    private  static String pwd="";
    private static Connection con;


    private static Statement ste;

    public static void main(String[] args) {
      try {
          con = DriverManager.getConnection(url, user, pwd);
          System.out.println("connexion établie");
      }catch (SQLException e)
      {
          System.out.println(e);
      }
      try {
          ste=con.createStatement();
      }catch (SQLException e)
      {
          System.out.println(e);
      }

      String req="INSERT INTO `actualite` ( `titre`, `contenu`, `date`, `categorie`, `auteur`) VALUES ( a,b,c,d,e);";

      try {
          ste.executeUpdate(req);
          System.out.println("élément insérer");
      }catch (SQLException e)
      {
          System.out.println(e);
      }

      try {
          ResultSet res= ste.executeQuery("select * from actualite");

          while (res.next()){

              int id_actualite = res.getInt(1);
              String titre = res.getString("titre");
              String contenu = res.getString(3);
              Date date = res.getDate(4);
              String categorie = res.getString(5);
              String auteur = res.getString(6);

              System.out.println("id :"+id_actualite+" titre :"+titre+" contenu :"+contenu+" date :"+date+" categorie :"+categorie+" auteur :"+auteur);
          }
      }catch (SQLException e)
      {
          System.out.println(e);
      }



    }
}
