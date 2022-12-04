/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edu.phonestore.ui;

import edu.phonestore.dao.UserDao;
import edu.phonestore.helper.ButtonHelper;
import edu.phonestore.helper.DialogHelper;
import edu.phonestore.model.User;
import edu.phonestore.utils.ExcelUtils;
import edu.phonestore.utils.FileUtils;
import java.awt.Color;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Văn Dự
 */
public class StaffPanel extends javax.swing.JPanel {

    UserDao userDao = new UserDao();
    int index = -1;
    byte[] image;

    public StaffPanel() {
        initComponents();
        setBounds(0, 0, 1267, 760);
        initButton();
        init();

    }

    private void init() {
        tblUsers.getTableHeader().setOpaque(false);
        tblUsers.getTableHeader().setBackground(new Color(0, 51, 153));
        tblUsers.getTableHeader().setForeground(Color.WHITE);
        fillTable();
        setStatus(true);
    }

    private void initButton() {
        btnAdd.addMouseListener(new ButtonHelper(btnAdd));
        btnUpdate.addMouseListener(new ButtonHelper(btnUpdate));
        btnDelete.addMouseListener(new ButtonHelper(btnDelete));
        btnClear.addMouseListener(new ButtonHelper(btnClear));
        btnFirst.addMouseListener(new ButtonHelper(btnFirst));
        btnPrev.addMouseListener(new ButtonHelper(btnPrev));
        btnNext.addMouseListener(new ButtonHelper(btnNext));
        btnLast.addMouseListener(new ButtonHelper(btnLast));
        btnReload.addMouseListener(new ButtonHelper(btnReload));
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
        model.setRowCount(0);
        List<User> list = userDao.findAll();

        for (User user : list) {
            Object[] row = {
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFullname(),
                user.isGender() ? "NAM" : "NỮ",
                user.isRole() ? "CHỦ" : "NHÂN VIÊN"
            };

            model.addRow(row);
        }
    }

    private void insert() {
        User user = getModel();
        if (userDao.insert(user)) {
            DialogHelper.alert(this, "Thêm tài khoản thành công!");
        } else {
            DialogHelper.alert(this, "Thêm tài khoản thất bại");
        }

        clear();
        fillTable();
    }

    void update() {
        User user = getModel();

        if (userDao.update(user)) {
            DialogHelper.alert(this, "Cập nhật tài khoản thành công!");
        } else {
            DialogHelper.alert(this, "Cập nhật tài khoản thất bại");
        }

        clear();
        fillTable();
    }

    void delete() {
        String username = txtUsername.getText();

        if (userDao.delete(username)) {
            DialogHelper.alert(this, "Xóa thành công!");
        } else {
            DialogHelper.alert(this, "Xóa thất bại!");
        }

        clear();
        fillTable();
    }

    private User getModel() {

        User user = new User();
        user.setUsername(txtUsername.getText());
        user.setPassword(txtPassword.getText());
        user.setEmail(txtEmail.getText());
        user.setFullname(txtFullname.getText());
        user.setGender(rdoMale.isSelected() ? true : false);
        user.setRole(rdoAdmin.isSelected() ? true : false);
        if (lblImage.getToolTipText() == null) {
            user.setImage(null);
        } else {
            user.setImage(lblImage.getToolTipText());
        }
        return user;
    }

    void clear() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        txtFullname.setText("");
        rdoMale.setSelected(true);
        rdoAdmin.setSelected(true);
        tblUsers.clearSelection();
        txtSearch.setText("");
        index = -1;
        lblImage.setIcon(null);
        lblImage.setToolTipText(null);
        setStatus(true);
        fillTable();
    }

    private void setModel(User user) {
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
        txtEmail.setText(user.getEmail());
        txtFullname.setText(user.getFullname());
        rdoMale.setSelected(user.isGender() ? true : false);
        rdoFemale.setSelected(user.isGender() ? false : true);
        rdoAdmin.setSelected(user.isRole() ? true : false);
        rdoStaff.setSelected(user.isRole() ? false : true);

        if (user.getImage() == null) {

            lblImage.setIcon(null);
            lblImage.setToolTipText("");
        } else {
            lblImage.setIcon(FileUtils.readLogo(user.getImage(), 130, 150));
            lblImage.setToolTipText(user.getImage());
        }
    }

    void edit() {
        try {
            String username = (String) tblUsers.getValueAt(this.index, 0);
            User model = userDao.findById(username);
            if (model != null) {
                this.setModel(model);
                tblUsers.setRowSelectionInterval(index, index);
                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setStatus(boolean insertable) {
        txtUsername.setEditable(insertable);
        btnAdd.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblUsers.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnLast.setEnabled(!insertable && last);
        btnNext.setEnabled(!insertable && last);

        if (insertable) {
            btnUpdate.setBackground(Color.gray);
            btnDelete.setBackground(Color.gray);
            btnAdd.setBackground(new Color(0, 51, 153));
        } else {
            btnUpdate.setBackground(new Color(0, 51, 153));
            btnDelete.setBackground(new Color(0, 51, 153));
            btnAdd.setBackground(Color.gray);
        }

        if (index == -1) {
            btnFirst.setBackground(Color.gray);
            btnPrev.setBackground(Color.gray);
            btnNext.setBackground(Color.gray);
            btnLast.setBackground(Color.gray);
            return;
        }

        if (!first) {
            btnFirst.setBackground(Color.gray);
            btnPrev.setBackground(Color.gray);
            btnNext.setBackground(new Color(0, 51, 153));
            btnLast.setBackground(new Color(0, 51, 153));
        } else if (!last) {
            btnFirst.setBackground(new Color(0, 51, 153));
            btnPrev.setBackground(new Color(0, 51, 153));
            btnNext.setBackground(Color.gray);
            btnLast.setBackground(Color.gray);
        } else {
            btnFirst.setBackground(new Color(0, 51, 153));
            btnPrev.setBackground(new Color(0, 51, 153));
            btnNext.setBackground(new Color(0, 51, 153));
            btnLast.setBackground(new Color(0, 51, 153));
        }
    }

    void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (FileUtils.saveLogo(file)) {
                // Hiển thị hình lên form
                lblImage.setIcon(FileUtils.readLogo(file.getName(), 130, 150));
                lblImage.setToolTipText(file.getName());
            }
        }
    }

    void search() {
        String keyword = txtSearch.getText();
        DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
        model.setRowCount(0);
        List<User> list = userDao.findByKeyWord(keyword);

        for (User user : list) {
            Object[] row = {
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFullname(),
                user.isGender() ? "NAM" : "NỮ",
                user.isRole() ? "CHỦ" : "NHÂN VIÊN"
            };

            model.addRow(row);
        }
    }

    void exportExcel(String excelFilePath) {
        try {
            Workbook workbook = ExcelUtils.getWorkbook(excelFilePath.toString());
            Sheet sheet = workbook.createSheet("DANH SÁCH TÀI KHOẢN");

            ExcelUtils.writeHeader(sheet, 3, "TÊN TÀI KHOẢN", "HỌ VÀ TÊN", "EMAIL", "GIỚI TÍNH", "CHỨC VỤ");

            List<User> list = userDao.findAll();
            Row row = null;
            Cell cell = null;
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(4 + i);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(list.get(i).getUsername());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(list.get(i).getFullname());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(list.get(i).getEmail());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(list.get(i).isGender() ? "Nam" : "Nữ");

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(list.get(i).isRole() ? "Chủ" : "Nhân viên");

            }

            ExcelUtils.autosizeColumn(sheet, 6);

            ExcelUtils.createOutputFile(workbook, excelFilePath);
            DialogHelper.alert(this, "Xuất file thành công!");
        } catch (IOException ex) {
            ex.printStackTrace();
            DialogHelper.alert(this, "Xuất file thất bại!");
        }

    }

    private boolean checkForm() {
        String username = txtUsername.getText();
        String fullname = txtFullname.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();

        if (username.equals("") || fullname.equals("") || password.equals("") || email.equals("")) {
            DialogHelper.alert(this, "Vui lòng nhập đầy đủ thông tin!");
            return true;
        }

        String reEmail = "\\w+@\\w+(\\.\\w+){1,2}";
        if (!email.matches(reEmail)) {
            DialogHelper.alert(this, "Vui lòng nhập email đúng định dạng !");
            return true;
        }

        if (username.length() < 7 || username.length() > 20) {
            DialogHelper.alert(this, "Tên tài khoản phải từ 7-20 kí tự");
            return true;
        }

        if (password.length() < 6 || password.length() > 20) {
            DialogHelper.alert(this, "Mật khẩu phải từ 6-20 kí tự");
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        lblImage = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdoMale = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtFullname = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        rdoAdmin = new javax.swing.JRadioButton();
        rdoStaff = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnPrev = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();

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
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_eraser_32.png"))); // NOI18N
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

        btnAdd.setBackground(new java.awt.Color(0, 51, 153));
        btnAdd.setForeground(new java.awt.Color(0, 51, 153));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_xbox_cross_32.png"))); // NOI18N
        btnAdd.setBorder(null);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lblImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153), 2));
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("TÊN ĐĂNG NHẬP");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("MẬT KHẨU");

        txtUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("HỌ VÀ TÊN");

        txtPassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("GIỚI TÍNH");

        buttonGroup1.add(rdoMale);
        rdoMale.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        rdoMale.setSelected(true);
        rdoMale.setText("NAM");

        buttonGroup1.add(rdoFemale);
        rdoFemale.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        rdoFemale.setText("NỮ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setText("CHỨC  VỤ");

        txtFullname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFullname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFullnameActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setText("EMAIL");

        buttonGroup2.add(rdoAdmin);
        rdoAdmin.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        rdoAdmin.setText("CHỦ ");

        buttonGroup2.add(rdoStaff);
        rdoStaff.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        rdoStaff.setSelected(true);
        rdoStaff.setText("NHÂN VIÊN");

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên đăng nhập", "Mật khẩu", "Email", "Tên", "Giới tính", "Chức vụ"
            }
        ));
        tblUsers.getTableHeader().setReorderingAllowed(false);
        tblUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsers);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("TÌM KIẾM");

        txtSearch.setBackground(new java.awt.Color(240, 240, 240));
        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSearch.setBorder(null);
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 51, 153));

        btnPrev.setBackground(new java.awt.Color(0, 51, 153));
        btnPrev.setForeground(new java.awt.Color(0, 51, 153));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_rewind_32.png"))); // NOI18N
        btnPrev.setBorder(null);
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnReload.setBackground(new java.awt.Color(0, 51, 153));
        btnReload.setForeground(new java.awt.Color(0, 51, 153));
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/replace_32px.png"))); // NOI18N
        btnReload.setBorder(null);
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(0, 51, 153));
        btnFirst.setForeground(new java.awt.Color(0, 51, 153));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_skip_to_start_32.png"))); // NOI18N
        btnFirst.setBorder(null);
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(0, 51, 153));
        btnNext.setForeground(new java.awt.Color(0, 51, 153));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_fast_forward_32.png"))); // NOI18N
        btnNext.setBorder(null);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(0, 51, 153));
        btnLast.setForeground(new java.awt.Color(0, 51, 153));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_end_32.png"))); // NOI18N
        btnLast.setBorder(null);
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("QUẢN LÍ NHÂN VIÊN");

        btnExcel.setBackground(new java.awt.Color(0, 51, 153));
        btnExcel.setForeground(new java.awt.Color(0, 51, 153));
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_microsoft_excel_32.png"))); // NOI18N
        btnExcel.setBorder(null);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rdoMale)
                                        .addGap(51, 51, 51)
                                        .addComponent(rdoFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rdoAdmin)
                                        .addGap(53, 53, 53)
                                        .addComponent(rdoStaff))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtEmail)
                                .addComponent(txtFullname)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtUsername)
                                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(58, Short.MAX_VALUE))
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel9)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnReload)))
                    .addComponent(btnExcel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoMale)
                            .addComponent(rdoFemale))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoAdmin)
                            .addComponent(rdoStaff))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrev)
                    .addComponent(btnFirst)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addGap(23, 23, 23))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtFullnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFullnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFullnameActionPerformed

    private void tblUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsersMouseClicked
        index = tblUsers.getSelectedRow();
        edit();
    }//GEN-LAST:event_tblUsersMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (checkForm()) {
            return;
        }

        if (userDao.findById(txtUsername.getText()) != null) {
            DialogHelper.alert(this, "Tên đăng nhập đã tồn tại!");
            return;
        }
        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        index = 0;
        edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        index--;
        edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        index++;
        edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        index = tblUsers.getRowCount() - 1;
        edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        search();

    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        fillTable();

    }//GEN-LAST:event_btnReloadActionPerformed

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked
        selectImage();
    }//GEN-LAST:event_lblImageMouseClicked

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        JFileChooser choose = new JFileChooser();

        int result = choose.showOpenDialog(null);
        String file = "";
        if (result == JFileChooser.APPROVE_OPTION) {
            file = choose.getSelectedFile().getPath();
            if (file.endsWith("xls") || file.endsWith("xlsx")) {

            } else {
                file = choose.getSelectedFile().getPath() + ".xls";
            }

        } else {
            return;
        }

        File excelFilePath = new File(file);

        if (excelFilePath.exists()) {
            JOptionPane.showMessageDialog(this, "File đã tồn tại!", "Thông báo", HEIGHT);
            return;
        }

        exportExcel(excelFilePath.toString());
    }//GEN-LAST:event_btnExcelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JRadioButton rdoAdmin;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JRadioButton rdoStaff;
    private javax.swing.JTable tblUsers;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
