package org.pi.demo.Interfaces;

import org.pi.demo.entities.Echange;

import java.util.List;

public interface EchangeInterface {
    public void addEchange(Echange e);
    public List<Echange> AfficherEchange();
    public void SupprimerEchange(int id);
    public boolean updateEchange(Echange e) ;
    public List<Echange> searchById(int id);
}
