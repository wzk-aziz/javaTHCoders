package Service;

import Entites.Actualite;
import Entites.Commentaire;
import Utils.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class ServiceCommentaire implements IService<Commentaire>{
    private Connection con= DataSource.getInstance().getCon();

    private Statement ste;
    private List<Commentaire> listeDeTousLesCommentaires;

    public ServiceCommentaire()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }


    }
    @Override
    public void ajouter(Commentaire commentaire) throws SQLException {
        String dateC = commentaire.getDateC() != null ? "'" + commentaire.getDateC().toString() + "'" : "NULL";
        String req = "INSERT INTO `commentaire`(`id_actualite`, `id_user`, `contenuC`, `dateC`) " +
                "VALUES ('" + commentaire.getId_actualite() + "', '" + commentaire.getId_user() + "', '" +
                commentaire.getContenuC() + "', " + dateC + ");";
        ste.executeUpdate(req);
    }




    @Override
    public void delete(Commentaire commentaire) throws SQLException {
        String req = "DELETE from commentaire where id_com = " + commentaire.getId_com() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

   @Override
    public void update(Commentaire commentaire) throws SQLException {

       String req = "UPDATE commentaire SET contenuC = ? WHERE id_com = ?";
       try (PreparedStatement st = con.prepareStatement(req)) {
           st.setString(1, commentaire.getContenuC());
           st.setInt(2, commentaire.getId_com());

           int rowsUpdated = st.executeUpdate();
           if (rowsUpdated > 0) {
               System.out.println("Forum modifié !");
           } else {
               System.out.println("Aucun reponse modifié.");
           }
       } catch (SQLException ev) {
           System.out.println(ev.getMessage());
       }
    }



    @Override
    public Commentaire findById(int id) throws SQLException {
        String req = "SELECT * FROM commentaire WHERE id_com = " + id + ";";
        ResultSet res = ste.executeQuery(req);
        if (res.next()) {
            int id_com = res.getInt(1);
            int id_actualite = res.getInt(2);
            int id_user = res.getInt(3);
            String contenuC = res.getString(4);
            Date dateC = res.getDate(5);
            return new Commentaire(id_com, id_actualite, id_user, contenuC, dateC);
        }
        return null;
    }

    @Override
    public List<Commentaire> readAll() throws SQLException {

        List<Commentaire> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from commentaire");
        while (res.next()) {

            int id_com = res.getInt(1);
            int id_actualite = res.getInt(2);
            int id_user = res.getInt(3);
            String contenuC = res.getString("contenuC");
            Date dateC = res.getDate(5);

            Commentaire c1 =new Commentaire (id_com,id_actualite,id_user,contenuC,dateC);
            list.add(c1);
        }

        return list;
    }
    Actualite getActualiteselect;
    public List<Commentaire> readOnly() throws SQLException {
        List<Commentaire> list2=new ArrayList<>();

        ResultSet res=ste.executeQuery("SELECT contenuC from commentaire WHERE id_actualite ='" + getActualiteselect +"';");
        while (res.next()) {

            int id_com = res.getInt(1);
            int id_actualite = res.getInt(2);
            int id_user = res.getInt(3);
            String contenuC = res.getString(4);
            Date dateC = res.getDate(5);
            Commentaire p1=new Commentaire(id_com,id_actualite,id_user,contenuC,dateC);
            list2.add(p1);
        }

        return list2;
    }
    public List<Commentaire> readCommentairesForSelectedActualite(Actualite selectedActualite) throws SQLException {
        List<Commentaire> commentairesList = new ArrayList<>();

        // Assuming you have a database connection (conn) and a Statement (ste) in your ServiceForum class
        String query = "SELECT * FROM commentaire WHERE id_actualite = " + selectedActualite.getId();
        ResultSet resultSet = ste.executeQuery(query);

        while (resultSet.next()) {
            int id_com = resultSet.getInt("id_com");
            int id_actualite = resultSet.getInt("id_actualite");
            int id_user = resultSet.getInt("id_user");
            String contenuC = resultSet.getString("contenuC");
            Date dateC = resultSet.getDate("dateC");

            Commentaire response = new Commentaire(id_com, id_user,id_actualite, contenuC, dateC);
            commentairesList.add(response);
        }

        return commentairesList;
    }



    /*public List<Commentaire> readCommentairesForIDActualite(int id) throws SQLException {
        List<Commentaire> commentairesList = new ArrayList<>();

        // Assuming you have a database connection (conn) and a Statement (ste) in your ServiceForum class
        String query = "SELECT * FROM commentaire WHERE id_actualite = " + id;
        ResultSet resultSet = ste.executeQuery(query);

        while (resultSet.next()) {
            int id_com = resultSet.getInt("id_com");
            int id_actualite = resultSet.getInt("id_actualite");
            int id_user = resultSet.getInt("id_user");
            String contenuC = resultSet.getString("contenuC");
            Date dateC = resultSet.getDate("dateC");

            Commentaire response = new Commentaire(id_com, id_user,id_actualite, contenuC, dateC);
            commentairesList.add(response);
        }

        return commentairesList;
    }*/

    public List<Commentaire> readByActualiteId(int actualiteId) throws SQLException {
        String query = "SELECT contenuC, dateC FROM commentaire WHERE id_actualite = ?";

        List<Commentaire> commentaires = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, actualiteId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String contenuC = resultSet.getString("contenuC");
                LocalDate dateC = resultSet.getDate("dateC").toLocalDate();

                // Ajouter le commentaire à la liste
                Commentaire commentaire = new Commentaire(contenuC, dateC);
                commentaires.add(commentaire);

                // Afficher le contenu de chaque commentaire récupéré
                System.out.println("Contenu du commentaire : " + contenuC);
            }
        }

        // Afficher la taille de la liste des commentaires avant de la renvoyer
        System.out.println("Nombre de commentaires récupérés : " + commentaires.size());

        return commentaires;
    }



}
