package com.projet.azizprojet.Services;

import java.util.List;

public interface IComment <T> {
	public void ajouter(T t);
	public void supprimer(T t);
	public void modifier(T t);
	public List<T> afficher();

}
