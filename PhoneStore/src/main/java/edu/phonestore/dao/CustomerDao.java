/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.dao;

import edu.phonestore.helper.JdbcHelper;
import edu.phonestore.model.Customer;
import edu.phonestore.model.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Văn Dự
 */
public class CustomerDao {

    public boolean insert(Customer customer) {
        String query = "insert into Customer values(?,?,?,?,?,?,?,?,?,?)";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, customer.getCustomerId(), customer.getFullname(), customer.getPhone(),
                customer.getEmail(), customer.isGender(), customer.getBirthday(), customer.getCreateDate(),
                customer.getAddress(), customer.getNotes(), customer.getPurchaseTimes())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Customer customer) {
        String query = "update Customer set fullname=?,"
                + "phone=?,email=?,gender=?,birthday=?,createdate=?,address=?,notes=?,purchaseTimes=? where customerId = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, customer.getFullname(), customer.getPhone(),
                customer.getEmail(), customer.isGender(), customer.getBirthday(), customer.getCreateDate(),
                customer.getAddress(), customer.getNotes(),customer.getPurchaseTimes(), customer.getCustomerId())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(String customerId) {
        String query = "delete from Customer where customerId = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, customerId)) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Customer> findAll() {
        String query = "select * from Customer";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Customer> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer customer = readFromResultSet(rs);
                list.add(customer);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Customer findById(String customerId) {
        String query = "select * from Customer where customerId = ?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, customerId)) {

            ResultSet rs = ps.executeQuery();
            Customer customer = null;
            if (rs.next()) {
                customer = readFromResultSet(rs);
            }

            rs.close();
            return  customer;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
  public List<Customer> findByKeyWord(String keyword) {
        String query = "select * from Customer where fullname like ?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query,"%"+keyword+"%")) {
            List<Customer> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer customer = readFromResultSet(rs);
                list.add(customer);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    private Customer readFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getString(1));
        customer.setFullname(rs.getString(2));
        customer.setPhone(rs.getString(3));
        customer.setEmail(rs.getString(4));
        customer.setGender(rs.getBoolean(5));
        customer.setBirthday(rs.getDate(6));
        customer.setCreateDate(rs.getDate(7));
        customer.setAddress(rs.getString(8));
        customer.setNotes(rs.getString(9));
        customer.setPurchaseTimes(rs.getInt(10));

        return customer;

    }
}
