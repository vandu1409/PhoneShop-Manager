/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.dao;

import edu.phonestore.helper.JdbcHelper;
import edu.phonestore.model.Order;
import edu.phonestore.model.Product;
import edu.phonestore.utils.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Văn Dự
 */
public class StatisticsDao {

    
    // tìm tất cả đơn hàng theo tháng
    public List<Order> findOrderByMonth(String month) {
        String query = "select a.*,fullname from Orders a inner join Users b on a.creator=b.username where MONTH(createDate) =?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, month)) {
            List<Order> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = readOrderFromResultSet(rs);
                list.add(order);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    // tìm tất cả đơn hàng
    public List<Order> findAll() {
        String query = "select a.*,fullname from Orders a inner join Users b on a.creator=b.username";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Order> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = readOrderFromResultSet(rs);
                list.add(order);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private Order readOrderFromResultSet(ResultSet rs) throws SQLException {
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

    // tìm sản phẩm sắp hết hàng
    public List<Product> findProductOutOfStock() {
        String query = "select * from Products where quatity <10";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Product> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = readProductFromResultSet(rs);
                list.add(product);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Product readProductFromResultSet(ResultSet rs) throws SQLException {

        Product product = new Product();
        product.setProductId(rs.getString(1));
        product.setTitle(rs.getString(2));
        product.setImage(rs.getString(3));
        product.setPrice(rs.getDouble(4));
        product.setImportPrice(rs.getDouble(5));
        product.setRam(rs.getInt(6));
        product.setRom(rs.getInt(7));
        product.setQuantity(rs.getInt(8));
        product.setColor(rs.getString(9));
        product.setBrandId(rs.getString(10));

        return product;
    }

    
    // tính tổng đơn hàng hôm nay
    public int totalOrderToday() {
        String query = "select COUNT(orderId) from Orders where createDate = CONVERT(date,GETDATE())";

        try ( Connection con = DBContext.getConnection();  Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return 0;
    }

    
    // tính tổng doanh thu hôm nay
    public double totalTurnoverToday() {
        String query = "select SUM(total) from Orders where createDate = CONVERT(date,GETDATE())";

        try ( Connection con = DBContext.getConnection();  Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return 0;
    }

    // tính tổng sản phẩm bán đc hôm nay
    public int totalQuantityProductToday() {
        String query = "select SUM(b.quatity) from Orders a inner join OrderDetalis b on a.orderId= b.orderId where createDate = CONVERT(date,GETDATE())";

        try ( Connection con = DBContext.getConnection();  Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return 0;
    }

    //top 5 sản phẩm bán chạy
    public List<Product> top5Product() {
        String query = "{call spTop5Product}";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Product> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString(1));
                product.setTitle(rs.getString(2));
                product.setImage(rs.getString(3));
                product.setPrice(rs.getDouble(4));
                product.setImportPrice(rs.getDouble(12));
                product.setRam(rs.getInt(6));
                product.setRom(rs.getInt(7));
                product.setQuantity(rs.getInt(8));
                product.setColor(rs.getString(9));
                product.setBrandId(rs.getString(10));

                list.add(product);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    
    //đếm sản phẩm trong kho
    public int countProduct() {
        String query = "select COUNT(productId) from Products";
        try ( Connection con = DBContext.getConnection();  Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return 0;
    }

    
    //
    public int countUser() {
        String query = "select COUNT(username) from Users";
        try ( Connection con = DBContext.getConnection();  Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return 0;
    }

    public int countOrder() {
        String query = "select COUNT(orderId) from Orders";
        try ( Connection con = DBContext.getConnection();  Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return 0;
    }

    
    // tổng doanh thu tháng hiện tại
    public Double totalTurnoverByMonth() {
        String query = "select sum(total) from Orders where MONTH(createDate) = MONTH(GETDATE())";
        try ( Connection con = DBContext.getConnection();  Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return 0.0;
    }

    // đếm tổng đơn hàng theo tháng
    public int countTotalOrderByMonth(String month) {
        String query = "select count(*) from Orders where MONTH(createDate) =?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, month)) {
            List<Order> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            int total = 0;

            if (rs.next()) {
                total = rs.getInt(1);
            }

            rs.close();
            return total;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    
    //tổng doanh thu theo tháng
    public double totalTurnoverByMonth(String month) {
        String query = "select SUM(total) from Orders where MONTH(createDate) =?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, month)) {

            ResultSet rs = ps.executeQuery();

            double total = 0;

            if (rs.next()) {
                total = rs.getDouble(1);
            }

            rs.close();
            return total;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;

    }

    // tổng lợi nhuận theo ngày
    public double totalProfitByDay(Date date) {
        String query = "{call spTotalProfitByDate (?)}";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, date)) {

            ResultSet rs = ps.executeQuery();

            double total = 0;

            if (rs.next()) {
                total = rs.getDouble(1);
            }

            rs.close();
            return total;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;

    }
    
    //tổng lợi nhuận theo tháng
     public double totalProfitByMonth(String month) {
        String query = "{call spTotalProfitByMonth (?)}";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, month)) {

            ResultSet rs = ps.executeQuery();

            double total = 0;

            if (rs.next()) {
                total = rs.getDouble(1);
            }

            rs.close();
            return total;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;

    }

     // tổng doanh thu theo ngfayf
    public double totalTurnoveByDay(Date date) {
        String query = "select sum(total) from Orders where createDate= CONVERT(date,?)";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, date)) {

            ResultSet rs = ps.executeQuery();

            double total = 0;

            if (rs.next()) {
                total = rs.getDouble(1);
            }

            rs.close();
            return total;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;

    }

    public static void main(String[] args) {
        StatisticsDao dao = new StatisticsDao();
        System.out.println(dao.totalTurnoveByDay(new Date()));
    }

}
