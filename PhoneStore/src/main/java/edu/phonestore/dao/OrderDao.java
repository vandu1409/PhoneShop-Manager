/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.dao;

import edu.phonestore.helper.JdbcHelper;
import edu.phonestore.model.Order;
import edu.phonestore.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Văn Dự
 */
public class OrderDao {

    public boolean insert(Order order) {
        String query = "insert into Orders values(?,?,?,?,?,?,?,?,?)";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, order.getOrderId(), order.getTotal(), order.getCreateDate(),
                order.getCustomerId(), order.getVoucherCode(), order.getDiscount(), order.getMemberDiscount(), order.getStatus(), order.getCreator())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Order order) {
        String query = "update Orders set total = ?,createDate = ?,customerId = ?,voucherCode = ?,discount=?,memberDiscount=?,status=?,creator=?\n"
                + "where orderId = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, order.getTotal(), order.getCreateDate(),
                order.getCustomerId(), order.getVoucherCode(),
                order.getDiscount(), order.getMemberDiscount(),
                order.getStatus(), order.getCreator(), order.getOrderId())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(String orderId) {
        String query = "delete from Orders where orderId = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, orderId)) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Order> findAll() {
        String query = "select a.*,fullname from Orders a inner join Customer b on a.customerId=b.customerId";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Order> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = readFromResultSet(rs);
                order.setCustomerId(rs.getString("fullname"));
                list.add(order);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Order findById(String orderId) {
        String query = "select * from Orders where orderId = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, orderId)) {

            ResultSet rs = ps.executeQuery();

            Order order = null;
            while (rs.next()) {
                order = readFromResultSet(rs);
            }

            rs.close();

            return order;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Order> findByStatus(String status) {
        String query = "select * from Orders where status = ?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, status)) {
            List<Order> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = readFromResultSet(rs);
                list.add(order);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public List<Order> findByDate(String dateMin, String dateMax) {
        String query = "select a.*,fullname from Orders a inner join Customer b on a.customerId=b.customerId where a.createDate>=? and a.createDate<=?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, dateMin, dateMax)) {
            List<Order> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = readFromResultSet(rs);
                order.setCustomerId(rs.getString("fullname"));
                list.add(order);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public List<Order> findByCustomerName(String customerName) {
        String query = "select a.*,fullname from Orders a inner join Customer b on a.customerId=b.customerId where fullname like ?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, "%" + customerName + "%")) {
            List<Order> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = readFromResultSet(rs);
                order.setCustomerId(rs.getString("fullname"));
                list.add(order);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private Order readFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getString(1));
        order.setTotal(rs.getDouble(2));
        order.setCreateDate(rs.getDate(3));
        order.setCustomerId(rs.getString(4));
        order.setVoucherCode(rs.getString(5));
        order.setDiscount(rs.getDouble(6));
        order.setMemberDiscount(rs.getDouble(7));
        order.setStatus(rs.getString(8));
        order.setCreator(rs.getString(9));

        return order;

    }

}
