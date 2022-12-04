/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edu.phonestore.ui;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.phonestore.dao.SalaryDao;
import edu.phonestore.dao.UserDao;
import edu.phonestore.helper.ButtonHelper;
import edu.phonestore.helper.DialogHelper;
import edu.phonestore.model.Salary;
import edu.phonestore.model.User;
import edu.phonestore.utils.FileUtils;
import java.awt.Color;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Văn Dự
 */
public class SalaryPanel extends javax.swing.JPanel {

    private DecimalFormat formatter = new DecimalFormat("###,###,###");
    private UserDao userDao = new UserDao();
    private SalaryDao salaryDao = new SalaryDao();
    private double totalSalary = 0.0;
    private String month;

    public SalaryPanel() {
        initComponents();
        setBounds(0, 0, 1282, 778);
        init();
        initButton();
        initTable();
//        salaryDao.autoUpdate(month);
//        fillTable();

    }

    private void init() {
        fillCbxUsers();
        fillTable();
        txtCommission.setEnabled(false);
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        int month = Integer.parseInt(sdf.format(new Date()));
        cbxMonth.setSelectedIndex(month - 1);
        setStatus(true);

    }

    private void initButton() {
        btnAdd.addMouseListener(new ButtonHelper(btnAdd));
        btnUpdate.addMouseListener(new ButtonHelper(btnUpdate));
        btnDelete.addMouseListener(new ButtonHelper(btnDelete));
        btnClear.addMouseListener(new ButtonHelper(btnClear));
        btnReload.addMouseListener(new ButtonHelper(btnReload));
        btnSum.addMouseListener(new ButtonHelper(btnSum));
        btnAutoUpdate.addMouseListener(new ButtonHelper(btnAutoUpdate));

    }

    private void initTable() {
        tblSalary.getTableHeader().setOpaque(false);
        tblSalary.getTableHeader().setBackground(new Color(0, 51, 153));
        tblSalary.getTableHeader().setForeground(Color.WHITE);
        tblSalary.setRowHeight(60);
        tblSalary.setShowVerticalLines(false);
    }

    private void fillCbxUsers() {
        DefaultComboBoxModel cbxmodel = (DefaultComboBoxModel) cbxUsers.getModel();
        cbxmodel.removeAllElements();
        cbxmodel.addElement("Chọn nhân viên");
        List<User> listUser = userDao.findAll();

        for (User user : listUser) {
            cbxmodel.addElement(user.getUsername());
        }
    }

    private void fillTable() {
        DefaultTableModel tblModel = (DefaultTableModel) tblSalary.getModel();
        tblModel.setRowCount(0);

        List<Salary> list = salaryDao.findAll();

        for (Salary salary : list) {
            User user = userDao.findById(salary.getUserId());
            ImageIcon icon = FileUtils.readLogo(user.getImage(), 40, 55);
            Object[] row = {
                icon, user.getUsername(), salary.getId(), salary.getBasicSalary(), salary.getCommisson(), salary.getAdvanceSalary(), salary.getSalaryDeducted(),
                salary.getBonus(), salary.getStratDate() + " -> " + salary.getEndDate(), formatter.format(salary.getTotalSalary()), salary.getNotes(),
                salary.getReceviedDate() == null ? "Chưa nhận" : salary.getReceviedDate()
            };

            tblModel.addRow(row);
        }
    }

    private void insert() {
        Salary salary = getModel();

        try {
            if (salaryDao.insert(salary)) {
                DialogHelper.alert(this, "Thêm lương cho nhân viên thành công");
            } else {
                DialogHelper.alert(this, "Thêm lương cho nhân viên thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Thêm lương cho nhân viên thất bại");
        }
        clear();
    }

    private void update() {
        Salary salary = getModel();

        try {
            if (salaryDao.update(salary)) {
                DialogHelper.alert(this, "Cật nhập lương cho nhân viên thành công");
            } else {
                DialogHelper.alert(this, "Cật nhập lương cho nhân viên thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Cật nhập lương cho nhân viên thất bại");
        }

        clear();
    }

    private void delete() {
        Salary salary = getModel();

        try {
            if (salaryDao.delete(salary.getId())) {
                DialogHelper.alert(this, "Xóa lương cho nhân viên thành công");
            } else {
                DialogHelper.alert(this, "Xóa lương cho nhân viên thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Xóa lương cho nhân viên thất bại");
        }

        clear();
    }

    private void clear() {
        cbxUsers.setSelectedIndex(0);
        cbxUsers.setToolTipText("");
        txtBasicSalary.setText("");
        txtCommission.setText("");
        txtAdvanceSalary.setText("");
        txtNumberDayWorking.setText("");
        txtSalaryDeducted.setText("");
        txtBonus.setText("");
        dateStrartDate.setDate(new Date());
        dateEndDate.setDate(new Date());
        lblTotalSalary.setText("0 VNĐ");
        txtNotes.setText("");
        rdoNotReceived.setSelected(true);
        fillTableByMonth();
        setStatus(true);
    }

    private Salary getModel() {
        Salary salary = new Salary();
        if (!cbxUsers.getToolTipText().equals("")) {
            salary.setId(Integer.parseInt(cbxUsers.getToolTipText()));
            System.out.println(cbxUsers.getToolTipText());
        }
        salary.setUserId((String) cbxUsers.getSelectedItem());
        salary.setBasicSalary(Double.parseDouble(txtBasicSalary.getText()));
        salary.setCommisson(Double.parseDouble(txtCommission.getText()));
        salary.setAdvanceSalary(Double.parseDouble("-" + txtAdvanceSalary.getText()));
        salary.setSalaryDeducted(Double.parseDouble("-" + txtSalaryDeducted.getText()));
        salary.setBonus(Double.parseDouble(txtBonus.getText()));
        salary.setStratDate(dateStrartDate.getDate());
        salary.setEndDate(dateEndDate.getDate());
        salary.setNumberDayWorking(Double.parseDouble(txtNumberDayWorking.getText()));
        salary.setTotalSalary(totalSalary);
//        salary.setTotalSalary(Double.parseDouble(lblTotalProce.getText()));
        if (rdoReceived.isSelected()) {
            salary.setReceviedDate(new Date());
        }

        salary.setNotes(txtNotes.getText());

        return salary;

    }

    private void setModel(Salary salary) {

        cbxUsers.setSelectedItem(salary.getUserId());
        cbxUsers.setToolTipText(String.valueOf(salary.getId()));
        txtBasicSalary.setText(String.valueOf(salary.getBasicSalary()));
        txtCommission.setEditable(true);
        txtCommission.setText(String.valueOf(salary.getCommisson()));
        txtCommission.setEditable(false);
        txtAdvanceSalary.setText(String.valueOf(salary.getAdvanceSalary() * -1));
        txtNumberDayWorking.setText(String.valueOf(salary.getNumberDayWorking()));
        txtSalaryDeducted.setText(String.valueOf(salary.getSalaryDeducted() * -1));
        txtBonus.setText(String.valueOf(salary.getBonus()));
        dateStrartDate.setDate(salary.getStratDate());
        dateEndDate.setDate(salary.getEndDate());
        lblTotalSalary.setText(formatter.format(salary.getTotalSalary()));
        txtNotes.setText(salary.getNotes());
        if (salary.getReceviedDate() == null) {
            rdoNotReceived.setSelected(true);
        }else{
        rdoReceived.setSelected(true);
        }
    }

    private boolean checkForm() {

        try {
            Date stratDate = dateStrartDate.getDate();
            Date endDate = dateEndDate.getDate();
            if (txtBasicSalary.getText().equals("") || txtCommission.getText().equals("") || txtAdvanceSalary.getText().equals("")
                    || txtSalaryDeducted.getText().equals("") || txtBonus.getText().equals("") || txtNumberDayWorking.getText().equals("")
                    || stratDate.equals("") || endDate.equals("")) {

                DialogHelper.alert(this, "Vui lòng nhập đầy đủ thông tin!");
                return true;
            }

            if (cbxUsers.getSelectedIndex() == 0) {
                DialogHelper.alert(this, "Vui lòng chọn nhân viên!");
                return true;
            }

            double basicSalary = Double.parseDouble(txtBasicSalary.getText());
            double commission = Double.parseDouble(txtCommission.getText());
            double advanceSalary = Double.parseDouble(txtAdvanceSalary.getText());
            double salaryDeducted = Double.parseDouble(txtSalaryDeducted.getText());
            double bonus = Double.parseDouble(txtBonus.getText());
            double numberDayWorking = Double.parseDouble(txtNumberDayWorking.getText());
            String userID = (String) cbxUsers.getSelectedItem();

            if (basicSalary <= 0) {
                DialogHelper.alert(this, "Lương cơ bản phải lớn hơn 0!");
                return true;
            }

            if (advanceSalary < 0 || salaryDeducted < 0) {
                DialogHelper.alert(this, "Vui lòng không nhập số âm!");
                return true;
            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Vui lòng nhập đúng định dạng số!");
            return true;
        }

        return false;
    }

    private void exportPdf() {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\vandu\\Documents\\demo58888986.pdf"));
            document.open();

            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_CENTER);

            paragraph.add("LƯƠNG NHÂN VIÊN");

            Paragraph paragraph1 = new Paragraph();
            paragraph1.setSpacingBefore(15);
            paragraph1.setAlignment(Element.ALIGN_LEFT);

            paragraph1.add("MÃ NV" + cbxUsers.getSelectedItem() + "\t\t");
            paragraph1.add("HỌ TÊN" + txtFullname.getText() + "\n");
            paragraph1.add("LƯƠNG CỞ BẢN : " + txtBasicSalary.getText() + " VNĐ\t\t");
            paragraph1.add("TIỀN HUÊ HỒNG: " + txtCommission.getText() + " VNĐ \n");
            paragraph1.add("LƯƠNG THƯỞNG : " + txtBonus.getText() + " VNĐ \t\t");
            paragraph1.add("SỐ NGÀY ĐI LÀM : " + txtNumberDayWorking.getText() + " NGÀY\n");

            document.add(paragraph);
            document.add(paragraph1);

            document.close();
            DialogHelper.alert(this, "In phiếu lương thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "In phiếu lương thất bại!");
        }
    }

    void setStatus(boolean insertable) {

        btnAdd.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);

        if (insertable) {
            btnUpdate.setBackground(Color.gray);
            btnDelete.setBackground(Color.gray);
            btnAdd.setBackground(new Color(0, 51, 153));
        } else {
            btnUpdate.setBackground(new Color(0, 51, 153));
            btnDelete.setBackground(new Color(0, 51, 153));
            btnAdd.setBackground(Color.gray);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSalary = new javax.swing.JTable();
        lblImage = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dateStrartDate = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNotes = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        txtFullname = new javax.swing.JTextField();
        txtCommission = new javax.swing.JTextField();
        txtBasicSalary = new javax.swing.JTextField();
        txtBonus = new javax.swing.JTextField();
        txtSalaryDeducted = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        txtAdvanceSalary = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        lblTotalSalary = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        dateEndDate = new com.toedter.calendar.JDateChooser();
        txtNumberDayWorking = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        rdoNotReceived = new javax.swing.JRadioButton();
        rdoReceived = new javax.swing.JRadioButton();
        cbxUsers = new javax.swing.JComboBox<>();
        btnReload = new javax.swing.JButton();
        btnSum = new javax.swing.JButton();
        btnAutoUpdate = new javax.swing.JButton();
        cbxMonth = new javax.swing.JComboBox<>();

        tblSalary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ảnh", "Tên", "ID", "Lương cơ bản ", "Huê hồng", "Lương ứng", "Lương bị trừ", "Thưởng", "Tháng", "Tổng", "Ghi chú","Ngày nhận"
            }
        ) {
            Class[] types = new Class [] {
                javax.swing.ImageIcon.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblSalary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSalaryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSalary);

        lblImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153), 2));
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("TÊN ĐĂNG NHẬP");

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("HỌ VÀ TÊN");

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("LƯƠNG CƠ BẢN(theo ngày)");

        jSeparator2.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator2.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jSeparator3.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator3.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jSeparator4.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator4.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("TIỀN HUÊ HỒNG");

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 153));
        jLabel9.setText("LƯƠNG ỨNG TRƯỚC");

        jSeparator6.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator6.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel10.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 153));
        jLabel10.setText("LƯƠNG BỊ TRỪ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setText("TG BẮT ĐẦU");

        dateStrartDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        dateStrartDate.setDateFormatString("yyyy-MM-dd");

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setText("LƯƠNG THƯỞNG");

        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel11.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 153));
        jLabel11.setText("GHI CHÚ");

        txtNotes.setColumns(20);
        txtNotes.setRows(5);
        txtNotes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        jScrollPane2.setViewportView(txtNotes);

        btnAdd.setBackground(new java.awt.Color(0, 51, 153));
        btnAdd.setForeground(new java.awt.Color(0, 51, 153));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_xbox_cross_32.png"))); // NOI18N
        btnAdd.setBorder(null);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(0, 51, 153));
        btnDelete.setForeground(new java.awt.Color(0, 51, 153));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_trash_can_32.png"))); // NOI18N
        btnDelete.setBorder(null);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 51, 153));
        btnClear.setForeground(new java.awt.Color(0, 51, 153));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Refresh_30px.png"))); // NOI18N
        btnClear.setBorder(null);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 51, 153));
        btnUpdate.setForeground(new java.awt.Color(0, 51, 153));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_pencil_32.png"))); // NOI18N
        btnUpdate.setBorder(null);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        txtFullname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jSeparator8.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator8.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 51));
        jLabel12.setText("TỔNG LĨNH");

        jSeparator5.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator5.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        lblTotalSalary.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        lblTotalSalary.setForeground(new java.awt.Color(255, 0, 0));
        lblTotalSalary.setText("0 VNĐ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 153));
        jLabel13.setText("TG KẾT THÚC");

        dateEndDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        dateEndDate.setDateFormatString("yyyy-MM-dd");

        jLabel14.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 153));
        jLabel14.setText("SỐ NGÀY ĐI LÀM");

        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel15.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 153));
        jLabel15.setText("TRẠNG THÁI");

        buttonGroup1.add(rdoNotReceived);
        rdoNotReceived.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        rdoNotReceived.setSelected(true);
        rdoNotReceived.setText("CHƯA NHẬN");

        buttonGroup1.add(rdoReceived);
        rdoReceived.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        rdoReceived.setText("ĐÃ NHẬN");

        cbxUsers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxUsers.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        cbxUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxUsersActionPerformed(evt);
            }
        });

        btnReload.setBackground(new java.awt.Color(0, 51, 153));
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Refresh_30px.png"))); // NOI18N
        btnReload.setBorder(null);
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        btnSum.setBackground(new java.awt.Color(0, 51, 153));
        btnSum.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSum.setForeground(new java.awt.Color(255, 255, 255));
        btnSum.setText("TÍNH");
        btnSum.setToolTipText("");
        btnSum.setBorder(null);
        btnSum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSumActionPerformed(evt);
            }
        });

        btnAutoUpdate.setBackground(new java.awt.Color(0, 51, 153));
        btnAutoUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Refresh_30px.png"))); // NOI18N
        btnAutoUpdate.setBorder(null);
        btnAutoUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutoUpdateActionPerformed(evt);
            }
        });

        cbxMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "THÁNG 1", "THÁNG 2", "THÁNG 3", "THÁNG 4", "THÁNG 5", "THÁNG 6", "THÁNG 7", "THÁNG 8", "THÁNG 9", "THÁNG 10", "THÁNG 11", "THÁNG 12" }));
        cbxMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMonthActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(dateStrartDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtAdvanceSalary, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSalaryDeducted)
                                    .addComponent(dateEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(83, 83, 83)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAutoUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator2)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFullname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                                .addComponent(cbxUsers, 0, 267, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(107, 107, 107)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(25, 25, 25)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(27, 27, 27)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(27, 27, 27)
                                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator5)
                                        .addComponent(lblTotalSalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnSum, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(rdoNotReceived)
                        .addGap(53, 53, 53)
                        .addComponent(rdoReceived))
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtBonus, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(txtBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator4)
                                .addComponent(txtCommission, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnReload)))
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtNumberDayWorking, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAutoUpdate)
                    .addComponent(cbxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(12, 12, 12)
                                .addComponent(txtSalaryDeducted, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtAdvanceSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateStrartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCommission, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(16, 16, 16)
                .addComponent(txtBonus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel14)
                .addGap(16, 16, 16)
                .addComponent(txtNumberDayWorking, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel15)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNotReceived)
                    .addComponent(rdoReceived))
                .addGap(29, 29, 29)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTotalSalary)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSum, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(117, 117, 117))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSalaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSalaryMouseClicked
        int id = (int) tblSalary.getValueAt(tblSalary.getSelectedRow(), 2);
        Salary salary = salaryDao.findByID(id);
        setModel(salary);
        setStatus(false);
    }//GEN-LAST:event_tblSalaryMouseClicked

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked

    }//GEN-LAST:event_lblImageMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (checkForm()) {
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (salaryDao.checkSalary((String) cbxUsers.getSelectedItem(), sdf.format(dateStrartDate.getDate())) != null) {
            System.out.println(sdf.format(dateStrartDate.getDate()));
            DialogHelper.alert(this, "Ngày bắt đầu nằm trong tháng lương đã đc tạo!");
            return;
        }

        btnSumActionPerformed(evt);
        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (cbxUsers.getToolTipText() == null) {
            DialogHelper.alert(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (checkForm()) {
            return;
        }
        btnSumActionPerformed(evt);
//        exportPdf();
        update();

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void cbxUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxUsersActionPerformed
        if (cbxUsers.getSelectedIndex() == 0) {
            txtFullname.setText("");
            lblImage.setIcon(null);
            txtCommission.setText("0.0");
            return;
        }

        String username = (String) cbxUsers.getSelectedItem();

        if (username == null) {
            return;
        }
        User user = userDao.findById(username);
        lblImage.setIcon(FileUtils.readLogo(user.getImage(), 130, 150));
        txtFullname.setText(user.getFullname());
        txtCommission.setText(String.valueOf(salaryDao.getCommission((String) cbxUsers.getSelectedItem(), month)));
    }//GEN-LAST:event_cbxUsersActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed

        txtCommission.setText(String.valueOf(salaryDao.getCommission((String) cbxUsers.getSelectedItem(), month)));
    }//GEN-LAST:event_btnReloadActionPerformed

    private void btnSumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSumActionPerformed
        Salary salary = getModel();
        totalSalary = (salary.getBasicSalary() * salary.getNumberDayWorking()) + salary.getCommisson() + salary.getAdvanceSalary() + salary.getSalaryDeducted() + salary.getBonus();
        lblTotalSalary.setText(formatter.format(totalSalary) + " VNĐ");
    }//GEN-LAST:event_btnSumActionPerformed

    private void btnAutoUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutoUpdateActionPerformed
        salaryDao.autoUpdate(month);
        fillTableByMonth();
    }//GEN-LAST:event_btnAutoUpdateActionPerformed

    private void cbxMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMonthActionPerformed
        clear();
        month = String.valueOf(cbxMonth.getSelectedIndex() + 1);
        System.out.println(month);
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        date.setDate(cal.getActualMinimum(Calendar.DATE));
        date.setMonth(cbxMonth.getSelectedIndex());
        date.setYear(new Date().getYear());

        dateStrartDate.setDate(date);
        dateStrartDate.setEnabled(false);
        
        fillTableByMonth();


    }//GEN-LAST:event_cbxMonthActionPerformed

    private void fillTableByMonth() {
        DefaultTableModel tblModel = (DefaultTableModel) tblSalary.getModel();
        tblModel.setRowCount(0);

        List<Salary> list = salaryDao.findByMonth(String.valueOf(cbxMonth.getSelectedIndex() + 1));

        for (Salary salary : list) {
            User user = userDao.findById(salary.getUserId());
            ImageIcon icon = FileUtils.readLogo(user.getImage(), 40, 55);
            Object[] row = {
                icon, user.getUsername(), salary.getId(), salary.getBasicSalary(), salary.getCommisson(), salary.getAdvanceSalary(), salary.getSalaryDeducted(),
                salary.getBonus(), salary.getStratDate() + " -> " + salary.getEndDate(), formatter.format(salary.getTotalSalary()), salary.getNotes(),
                salary.getReceviedDate() == null ? "Chưa nhận" : salary.getReceviedDate()
            };

            tblModel.addRow(row);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAutoUpdate;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSum;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxMonth;
    private javax.swing.JComboBox<String> cbxUsers;
    private com.toedter.calendar.JDateChooser dateEndDate;
    private com.toedter.calendar.JDateChooser dateStrartDate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblTotalSalary;
    private javax.swing.JRadioButton rdoNotReceived;
    private javax.swing.JRadioButton rdoReceived;
    private javax.swing.JTable tblSalary;
    private javax.swing.JTextField txtAdvanceSalary;
    private javax.swing.JTextField txtBasicSalary;
    private javax.swing.JTextField txtBonus;
    private javax.swing.JTextField txtCommission;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JTextArea txtNotes;
    private javax.swing.JTextField txtNumberDayWorking;
    private javax.swing.JTextField txtSalaryDeducted;
    // End of variables declaration//GEN-END:variables
}
