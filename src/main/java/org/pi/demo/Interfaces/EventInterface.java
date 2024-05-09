package org.pi.demo.Interfaces;


import org.pi.demo.entities.Events;

import java.util.List;

public interface EventInterface {
    public void addEvent(Events ev);
    public List<Events> AfficherEvent();
    public boolean SupprimerEvent(int id);
    public boolean updateEvent(Events ev) ;
    public List<Events> searchByPlace(String place);
}
