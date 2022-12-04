/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.dao;

import edu.phonestore.helper.JdbcHelper;
import edu.phonestore.model.Salary;
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
public class SalaryDao {

    public boolean insert(Salary salary) {
        String query = "INSERT INTO Salary\n"
                + "           (userId\n"
                + "           ,basicSalary\n"
                + "           ,commission\n"
                + "           ,advanceSalary\n"
                + "           ,salaryDeducted\n"
                + "           ,bonus\n"
                + "           ,startDate\n"
                + "           ,endDate\n"
                + "           ,numberDay\n"
                + "           ,totalSalary\n"
                + "           ,receivedDate\n"
                + "           ,notes)\n"
                + "     VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, salary.getUserId(), salary.getBasicSalary(), salary.getCommisson(),
                salary.getAdvanceSalary(), salary.getSalaryDeducted(), salary.getBonus(), salary.getStratDate(),
                salary.getEndDate(), salary.getNumberDayWorking(), salary.getTotalSalary(), salary.getReceviedDate(), salary.getNotes())) {

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Salary salary) {
        String query = "UPDATE Salary\n"
                + "   SET userId = ?\n"
                + "      ,basicSalary =?\n"
                + "      ,commission = ?\n"
                + "      ,advanceSalary = ?\n"
                + "      ,salaryDeducted =?\n"
                + "      ,bonus = ?\n"
                + "      ,startDate = ?\n"
                + "      ,endDate = ?\n"
                + "      ,numberDay = ?\n"
                + "      ,totalSalary = ?\n"
                + "      ,receivedDate = ?\n"
                + "      ,notes =?\n"
                + " WHERE id= ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, salary.getUserId(), salary.getBasicSalary(), salary.getCommisson(),
                salary.getAdvanceSalary(), salary.getSalaryDeducted(), salary.getBonus(), salary.getStratDate(),
                salary.getEndDate(), salary.getNumberDayWorking(), salary.getTotalSalary(), salary.getReceviedDate(), salary.getNotes(), salary.getId())) {

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        String query = "delete Salary where id = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, id)) {

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Salary> findAll() {
        String query = "select * from Salary";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Salary> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Salary salary = readFromResultSet(rs);
                list.add(salary);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
    
        public List<Salary> findByMonth(String month) {
        String query = "select * from Salary where MONTH(startDate)=?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query,month)) {
            List<Salary> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Salary salary = readFromResultSet(rs);
                list.add(salary);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Salary findByID(int id) {
        String query = "select * from Salary where id = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, id)) {

            ResultSet rs = ps.executeQuery();
            Salary salary = new Salary();
            if (rs.next()) {
                salary = readFromResultSet(rs);

            }

            rs.close();

            return salary;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Salary checkSalary(String userId, String date) {
        String query = "select * from Salary where userId=? and endDate>=?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, userId, date)) {

            ResultSet rs = ps.executeQuery();
            Salary salary = null;
            if (rs.next()) {
                salary = readFromResultSet(rs);

            }

            rs.close();

            return salary;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private Salary readFromResultSet(ResultSet rs) throws SQLException {
        Salary salary = new Salary();
        salary.setId(rs.getInt(1));
        salary.setUserId(rs.getString(2));
        salary.setBasicSalary(rs.getDouble(3));
        salary.setCommisson(rs.getDouble(4));
        salary.setAdvanceSalary(rs.getDouble(5));
        salary.setSalaryDeducted(rs.getDouble(6));
        salary.setBonus(rs.getDouble(7));
        salary.setStratDate(rs.getDate(8));
        salary.setEndDate(rs.getDate(9));
        salary.setNumberDayWorking(rs.getDouble(10));
        salary.setTotalSalary(rs.getDouble(11));
        salary.setReceviedDate(rs.getDate(12));
        salary.setNotes(rs.getString(13));

        return salary;
    }

    public boolean updateCommission(String username,String month) {
        String query = "update Salary \n"
                + "set commission = (select sum(c.totalPrice*0.01) from Users a inner join Orders b on a.username=b.creator inner join OrderDetalis c on b.orderId=c.orderId "
                + "where a.username =? and MONTH(b.createDate) = ?)";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, username,month)) {

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public double getCommission(String username,String month) {
        String query = "select sum(c.totalPrice*0.01) from Users a inner join Orders b on a.username=b.creator inner join OrderDetalis c on b.orderId=c.orderId "
                + "where a.username =? and MONTH(b.createDate) = ?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, username,month)) {

            ResultSet rs = ps.executeQuery();
            double commission = 0.0;
            if (rs.next()) {
                commission = rs.getInt(1);
            }

            rs.close();

            return commission;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public void autoUpdate(String month) {
        List<Salary> list = findByMonth(month);
        for (Salary salary : list) {
            salary.setCommisson(getCommission(salary.getUserId(),month));
            double totalSalary = (salary.getBasicSalary() * salary.getNumberDayWorking()) + salary.getCommisson() + salary.getAdvanceSalary() + salary.getSalaryDeducted() + salary.getBonus();
            salary.setTotalSalary(totalSalary);
            update(salary);
        }
    }
}
