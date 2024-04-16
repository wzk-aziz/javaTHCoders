package Service;

import Entites.Actualite;

import java.sql.SQLException;
import java.util.List;

public interface IService <T>{

    public void ajouter(T t) throws SQLException;
    public void delete(T t) throws SQLException;

    public void update (T t) throws SQLException;

     T findById(int id) throws SQLException;

    List<T> readAll() throws SQLException;

    List<T> readOnly() throws SQLException;
}
