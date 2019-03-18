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
                String sql = "Select * From Users Where email = ? And password = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, txtEmail);
                ps.setString(2, txtPassword);
                
                rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString(2);
                    String email = rs.getString(3);
                    int role = rs.getInt(5);
                    UsersDTO dto = new UsersDTO(name, email, null, role);
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
                String sql = "Insert Into Users(name, email, password, role) "
                        + "Values(?, ?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.setInt(4, role);
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
