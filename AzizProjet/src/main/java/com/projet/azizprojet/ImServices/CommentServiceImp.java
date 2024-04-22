package com.projet.azizprojet.ImServices;

import com.projet.azizprojet.Services.IComment;
import com.projet.azizprojet.entities.Comment;
import com.projet.azizprojet.entities.CommentDTO;
import com.projet.azizprojet.utils.ConnectionBd;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceImp implements IComment<Comment> {
	Connection cnx = ConnectionBd.getInstance().getCnx();

	@Override
	public void ajouter(Comment comment) {

		try{
			String requete = "INSERT INTO commnet (contenu, datecommnt, annonces_id, liked) VALUES (?,?,?,?)";
			java.sql.PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setString(1, comment.getContenu());
			pst.setDate(2, comment.getDatecommnt());
			pst.setInt(3, comment.getAnnonces_id());
			pst.setInt(4, comment.getLiked());
			pst.executeUpdate();
			System.out.println("Commentaire ajouté");
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	@Override
	public void supprimer(Comment comment) {
try {
			String requete = "DELETE FROM commnet WHERE id = ?";
			java.sql.PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setInt(1, comment.getId());
			pst.executeUpdate();
			System.out.println("Commentaire supprimé");
		} catch (Exception e) {
			System.out.println(e.getMessage());
}
	}

	@Override
	public void modifier(Comment comment) {
		try {
			String requete = "UPDATE commnet SET contenu = ?, datecommnt = ?, annonces_id = ?, liked = ? WHERE id = ?";
			java.sql.PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setString(1, comment.getContenu());
			pst.setDate(2, comment.getDatecommnt());
			pst.setInt(3, comment.getAnnonces_id());
			pst.setInt(4, comment.getLiked());
			pst.setInt(5, comment.getId());
			pst.executeUpdate();
			System.out.println("Commentaire modifié");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Comment> afficher() {
		return null;
	}

	public  List<CommentDTO>AfficheR(){
		List<CommentDTO> list =new ArrayList<CommentDTO>();
		try {
			String requete = "SELECT c.id, c.contenu, c.datecommnt, a.titre, a.description, c.liked FROM commnet c JOIN annonces a ON c.annonces_id = a.id";
			java.sql.PreparedStatement pst = cnx.prepareStatement(requete);
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()){
				CommentDTO c = new CommentDTO();
				c.setId(rs.getInt("id"));
				c.setContenu(rs.getString("contenu"));
				c.setDatecommnt(rs.getDate("datecommnt"));
				c.setAnnonceTitre(rs.getString("titre"));
				c.setAnnonceDescription(rs.getString("description"));
				c.setLiked(rs.getInt("liked"));
				list.add(c);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
}
