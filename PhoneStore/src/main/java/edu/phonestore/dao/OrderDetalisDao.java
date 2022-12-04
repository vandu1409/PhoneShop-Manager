/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.dao;

import edu.phonestore.helper.JdbcHelper;
import edu.phonestore.model.OrderDetalis;
import edu.phonestore.model.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Văn Dự
 */
public class OrderDetalisDao {

    public boolean insert(OrderDetalis orderDetali) {
        String query = "insert into OrderDetalis (productId\n"
                + "           ,orderId\n"
                + "           ,quatity\n"
                + "           ,totalPrice) values(?,?,?,?)";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, orderDetali.getProductId(),
                orderDetali.getOrderId(), orderDetali.getQuatity(), orderDetali.getTotalProduct())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean delete(String productId, String orderId) {

        String query = "delete from OrderDetalis where productId = ? and orderId = ?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, productId, orderId)) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean deleteAllByOrder(String orderId) {

        String query = "delete from OrderDetalis where orderId = ?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, orderId)) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public List<Product> findProductByOrderId(String orderId) {
        String query = "select p.*,o.quatity from Products p inner join OrderDetalis o on p.productId=o.productId where o.orderId =?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, orderId)) {
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
        product.setQuantity(rs.getInt(11));
        product.setColor(rs.getString(9));
        product.setBrandId(rs.getString(10));

        return product;
    }
}
