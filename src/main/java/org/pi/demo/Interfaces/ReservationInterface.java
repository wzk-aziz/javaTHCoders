package org.pi.demo.Interfaces;


import org.pi.demo.entities.Reservation;

import java.util.List;

public interface ReservationInterface {
    public void addReservation(Reservation rv);
    public List<Reservation> AfficherReservation();
    public void SupprimerReservation(int id);
    public boolean updateReservatoin(Reservation rv) ;
}
