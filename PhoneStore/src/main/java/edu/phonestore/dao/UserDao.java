/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.dao;

import edu.phonestore.helper.JdbcHelper;
import edu.phonestore.model.User;
import edu.phonestore.utils.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Văn Dự
 */
public class UserDao {

    public User checkLogin(String username, String password) {
        String query = "select * from Users where username=? and password=?";

        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, username);
            ps.setString(2, password);

            try {
                ResultSet rs = ps.executeQuery();
                User user = null;
                if (rs.next()) {
                    user = readFromResultSet(rs);

                }
                rs.close();
                return user;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> findAll() {
        String query = "select * from Users";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<User> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = readFromResultSet(rs);
                list.add(user);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public User findById(String username) {
        String query = "select * from Users where username = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, username)) {

            ResultSet rs = ps.executeQuery();

            User user = null;
            while (rs.next()) {
                user = readFromResultSet(rs);
            }

            rs.close();

            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insert(User user) {

        String query = "INSERT INTO Users VALUES(?,?,?,?,?,?,?)";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, user.getUsername(),
                user.getPassword(), user.getEmail(), user.getImage(), user.getFullname(), user.isGender(), user.isRole())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(User user) {

        String query = "UPDATE Users SET"
                + "      password = ?"
                + "      ,email = ?"
                + "      ,image = ?"
                + "      ,fullname = ?"
                + "      ,gender = ?"
                + "      ,role = ?  where username = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query,
                user.getPassword(), user.getEmail(),user.getImage(), user.getFullname(), user.isGender(), user.isRole(), user.getUsername())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(String username) {
        String query = "delete from Users where username = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, username)) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private User readFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString(1));
        user.setPassword(rs.getString(2));
        user.setEmail(rs.getString(3));
        user.setImage(rs.getString(4));
        user.setFullname(rs.getString(5));
        user.setGender(rs.getBoolean(6));
        user.setRole(rs.getBoolean(7));

        return user;
    }

    public List<User> findByKeyWord(String username) {
        String query = "select * from Users where username like ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, "%" + username + "%")) {
            List<User> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = readFromResultSet(rs);
                list.add(user);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
