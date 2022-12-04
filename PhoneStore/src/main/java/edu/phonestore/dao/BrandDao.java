/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.dao;

import edu.phonestore.helper.JdbcHelper;
import edu.phonestore.model.Brand;
import edu.phonestore.model.Product;
import edu.phonestore.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Văn Dự
 */
public class BrandDao {

    public List<Brand> findAll() {

        String query = "select * from Brand";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Brand> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Brand brand = readFromResultSet(rs);
                list.add(brand);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Brand findById(String id) {
        String query = "select * from Brand where id = ?";
    try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, id)) {

            ResultSet rs = ps.executeQuery();

             Brand brand = null;
            while (rs.next()) {
                brand = readFromResultSet(rs);
            }

            rs.close();

            return brand;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Brand readFromResultSet(ResultSet rs) throws SQLException {
        Brand brand = new Brand();
        brand.setId(rs.getString("id"));
        brand.setName(rs.getString("name"));

        return brand;
    }
    
    
    public boolean insert(Brand brand) {

        String query = "INSERT INTO Brand VALUES(?,?)";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, brand.getId(),brand.getName())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
     public boolean update(Brand brand) {

        String query = "Update Brand set name = ? where id = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query,brand.getName(),brand.getId())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
     
      public boolean delete(String id) {

        String query = "delete Brand where id = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query,id)) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
