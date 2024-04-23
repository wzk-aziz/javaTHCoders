package com.projet.azizprojet.ImServices;

import com.projet.azizprojet.Services.IAnnonce;
import com.projet.azizprojet.entities.Annonce;
import com.projet.azizprojet.utils.ConnectionBd;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AnnoncesServicesImp implements IAnnonce<Annonce> {
	Connection cnx = ConnectionBd.getInstance().getCnx();

	@Override
	public void ajouter(Annonce annonce) {
		try{
			String requete = "INSERT INTO annonces (titre, description, datedepub, liked,rating) VALUES (?,?,?,?,?)";
			java.sql.PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setString(1, annonce.getTitre());
			pst.setString(2, annonce.getDescription());
			pst.setDate(3, annonce.getDatedepub());
			pst.setInt(4, annonce.getLiked());
			pst.setDouble(5, annonce.getRating());

			pst.executeUpdate();
			System.out.println("Annonce ajoutée");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void supprimer(Annonce annonce) {
		try {
			String requete = "DELETE FROM annonces WHERE id = ?";
			java.sql.PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setInt(1, annonce.getId());
			pst.executeUpdate();
			System.out.println("Annonce supprimée");
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	@Override
	public void modifier(Annonce annonce) {

		try {
			String requete = "UPDATE annonces SET titre = ?, description = ?, datedepub = ?, liked = ? ,rating=?,WHERE id = ?";
			java.sql.PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setString(1, annonce.getTitre());
			pst.setString(2, annonce.getDescription());
			pst.setDate(3, annonce.getDatedepub());
			pst.setInt(4, annonce.getLiked());
			pst.setInt(5, annonce.getId());
			pst.setDouble(5, annonce.getRating());

			pst.executeUpdate();
			System.out.println("Annonce modifiée");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Annonce> afficher() {
		List<Annonce> list =new ArrayList<Annonce>();
		try {
			String requete = "SELECT * FROM annonces";
			java.sql.Statement st = cnx.createStatement();
			java.sql.ResultSet rs = st.executeQuery(requete);
			while (rs.next()) {
				Annonce annonce = new Annonce();
				annonce.setId(rs.getInt("id"));
				annonce.setTitre(rs.getString("titre"));
				annonce.setDescription(rs.getString("description"));
				annonce.setDatedepub(rs.getDate("datedepub"));
				annonce.setLiked(rs.getInt("liked"));
				annonce.setRating(rs.getDouble("Rating"));				list.add(annonce);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public List<Annonce> rechercherParTitre(String titre) {
		return null;
	}
}
