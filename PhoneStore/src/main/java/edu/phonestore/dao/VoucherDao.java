/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.dao;

import edu.phonestore.helper.JdbcHelper;
import edu.phonestore.model.Voucher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Văn Dự
 */
public class VoucherDao {

    public boolean insert(Voucher voucher) {
        String query = "insert into Vouchers values(?,?,?,?,?,?,?,?,?)";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, voucher.getVoucherCode(), voucher.getVoucherName(),
                voucher.getDiscountByPrice(), voucher.getDiscountByPercent(), voucher.getStratDate(),
                voucher.getEnddate(), voucher.getAmountApplied(),
                voucher.getMaximunAmount(), voucher.getNotes())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Voucher voucher) {
        String query = "update Vouchers set name = ?,discountByPrice = ?,discountByPercent = ?"
                + ",startDate=?,endDate = ?,amountApplied=?,maximumAmount=?,notes=? where voucherCode = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, voucher.getVoucherName(),
                voucher.getDiscountByPrice(), voucher.getDiscountByPercent(), voucher.getStratDate(),
                voucher.getEnddate(), voucher.getAmountApplied(),
                voucher.getMaximunAmount(), voucher.getNotes(), voucher.getVoucherCode())) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(String voucherCode) {
        String query = "delete from Vouchers where voucherCode = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, voucherCode)) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Voucher> findAll() {
        String query = "select * from Vouchers";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Voucher> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Voucher voucher = readFromResultSet(rs);
                list.add(voucher);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }

    public Voucher findById(String voucherCode) {
        String query = "select * from Vouchers where voucherCode = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, voucherCode)) {

            ResultSet rs = ps.executeQuery();
            Voucher voucher = null;
            while (rs.next()) {
                voucher = readFromResultSet(rs);

            }

            rs.close();

            return voucher;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }

    public List<Voucher> findAllVoucherToday() {
        String query = " select * from Vouchers where startDate<=CONVERT(date,GETDATE()) and endDate>=CONVERT(date,GETDATE()) ";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Voucher> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Voucher voucher = readFromResultSet(rs);
                list.add(voucher);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }

    public List<Voucher> findAllVoucherComingSoon() {
        String query = " select * from Vouchers where \n"
                + "startDate>CONVERT(date,GETDATE()) ";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Voucher> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Voucher voucher = readFromResultSet(rs);
                list.add(voucher);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }

    public List<Voucher> findAllVoucherExpired() {
        String query = "select * from Vouchers where \n"
                + "endDate<CONVERT(date,GETDATE()) ";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Voucher> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Voucher voucher = readFromResultSet(rs);
                list.add(voucher);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }

    private Voucher readFromResultSet(ResultSet rs) throws SQLException {
        Voucher voucher = new Voucher();
        voucher.setVoucherCode(rs.getString(1));
        voucher.setVoucherName(rs.getString(2));
        voucher.setDiscountByPrice(rs.getDouble(3));
        voucher.setDiscountByPercent(rs.getDouble(4));
        voucher.setStratDate(rs.getDate(5));
        voucher.setEnddate(rs.getDate(6));
        voucher.setAmountApplied(rs.getDouble(7));
        voucher.setMaximunAmount(rs.getDouble(8));
        voucher.setNotes(rs.getString(9));

        return voucher;
    }
}
