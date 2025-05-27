/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.ResidentContact;
import java.util.LinkedList;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
public interface ResidentContactDAO extends CRUD<ResidentContact> {
    public LinkedList<ResidentContact> getAll(int id);
}
