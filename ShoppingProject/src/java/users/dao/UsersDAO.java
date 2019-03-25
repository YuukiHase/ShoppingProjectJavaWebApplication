/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import users.dto.UsersDTO;
import utils.DBUtils;

/**
 *
 * @author tabal
 */
public class UsersDAO implements Serializable{
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public UsersDTO checkLogin(String txtEmail, String txtPassword) throws SQLException, ClassNotFoundException {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * From Users Where email = ? And password = ? And isDeleted = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, txtEmail);
                ps.setString(2, txtPassword);
                ps.setBoolean(3, false);
                
                rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString(2);
                    String email = rs.getString(3);
                    int role = rs.getInt(5);
                    UsersDTO dto = new UsersDTO(name, email, null, role, false);
                    return dto;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }
    
    public boolean signUp(String name, String email, String password, int role) throws ClassNotFoundException, SQLException {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Insert Into Users(name, email, password, role, isDeleted) "
                        + "Values(?, ?, ?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.setInt(4, role);
                ps.setBoolean(5, false);
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    private List<UsersDTO> listAdmin;

    public List<UsersDTO> getListAdmin() {
        return listAdmin;
    }
    
    public void loadListAdmin() throws SQLException, ClassNotFoundException {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * From Users Where role = 1";
                ps = con.prepareStatement(sql);
                
                rs = ps.executeQuery();
                
                while (rs.next()) {
                    String name = rs.getString(2);
                    String email = rs.getString(3);
                    boolean isDeleted = rs.getBoolean(6);
                    UsersDTO dto = new UsersDTO(name, email, null, -1, isDeleted);
                    
                    if (this.listAdmin == null) {
                        this.listAdmin = new ArrayList<>();
                    }
                    
                    this.listAdmin.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
    }
    
    public boolean deactiveAdmin(String pk) throws SQLException, ClassNotFoundException {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Update Users Set isDeleted = ? Where email = ?";
                ps = con.prepareStatement(sql);
                ps.setBoolean(1, true);
                ps.setString(2, pk);
                
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    public boolean activeAdmin(String pk) throws SQLException, ClassNotFoundException {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Update Users Set isDeleted = ? Where email = ?";
                ps = con.prepareStatement(sql);
                ps.setBoolean(1, false);
                ps.setString(2, pk);
                
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
}
