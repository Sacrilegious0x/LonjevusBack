/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.dao;

import java.util.LinkedList;

/**
 *
 * @author User
 */
public interface CRUD <T> {
    public LinkedList<T> getAll();
    public void add(T t);
    public void deleteById(int id);
    public T findById(int id);
    public void update(T t);
}
