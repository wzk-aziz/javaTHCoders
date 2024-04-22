package org.pi.demo.Interfaces;


import org.pi.demo.entities.Event;

import java.util.List;

public interface EventInterface {
    public void addEvent(Event ev);

    static List<Event> AfficherEvent() {
        return null;
    }

    public void SupprimerEvent(int id);
    public boolean updateEvent(Event ev) ;
    public List<Event> searchByPlace(String place);
}
