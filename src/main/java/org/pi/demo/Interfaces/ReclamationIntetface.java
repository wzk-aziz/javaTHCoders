package org.pi.demo.Interfaces;

import org.pi.demo.entities.Reclamation;

import java.util.List;

public interface ReclamationIntetface {
    public void addReclamation(Reclamation r);
    public List<Reclamation> AfficherReclamation();
    public void SupprimerReclamation(int id);
    public boolean updateReclamation(Reclamation r) ;
    public List<Reclamation> searchById(int id);
}
