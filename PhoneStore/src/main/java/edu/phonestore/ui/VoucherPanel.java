/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edu.phonestore.ui;

import edu.phonestore.dao.VoucherDao;
import edu.phonestore.helper.ButtonHelper;
import edu.phonestore.helper.DialogHelper;
import edu.phonestore.model.Voucher;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Văn Dự
 */
public class VoucherPanel extends javax.swing.JPanel {

    VoucherDao voucherDao = new VoucherDao();
    DecimalFormat formatter = new DecimalFormat("###,###,###");

    public VoucherPanel() {
        initComponents();
        setBounds(0, 0, 1267, 760);
        init();
        initButton();

    }

    private void init() {
        dateStartDate.setDate(new Date());
        dateEndDate.setDate(new Date());
        fillTableVouchersToday();
        fillTableVouchersComningSoon();
        fillTableVouchersExpired();
        setStatus(true);

    }

    private void initButton() {
        btnAdd.addMouseListener(new ButtonHelper(btnAdd));
        btnUpdate.addMouseListener(new ButtonHelper(btnUpdate));
        btnDelete.addMouseListener(new ButtonHelper(btnDelete));
        btnClear.addMouseListener(new ButtonHelper(btnClear));

    }

    private void insert() {
        try {
            Voucher voucher = getModel();

            if (voucherDao.insert(voucher)) {
                DialogHelper.alert(this, "Thêm voucher thành công !");
            } else {
                DialogHelper.alert(this, "Thêm voucher thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Thêm voucher thất bại!");
        }

        clear();
    }

    private void update() {
        try {
            Voucher voucher = getModel();

            if (voucherDao.update(voucher)) {
                DialogHelper.alert(this, "Cật nhật voucher thành công !");
            } else {
                DialogHelper.alert(this, "Cật nhật voucher thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Cật nhật voucher thất bại!");
        }
        clear();
    }

    private void delete() {
        try {
            Voucher voucher = getModel();

            if (voucherDao.delete(voucher.getVoucherCode())) {
                DialogHelper.alert(this, "Xóa voucher thành công !");
            } else {
                DialogHelper.alert(this, "Xóa voucher thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Xóa voucher thất bại!");
        }
        clear();
    }

    private void clear() {
        txtVoucherCode.setText("");
        txtVoucherName.setText("");
        txtDiscount.setText("");
        txtAmountApplied.setText("");
        txtMaximunAmout.setText("");
        txtNotes.setText("");
        cbxTypeOfVoucher.setSelectedIndex(0);
        dateStartDate.setDate(new Date());
        dateEndDate.setDate(new Date());
        fillTableVouchersToday();
        fillTableVouchersComningSoon();
        fillTableVouchersExpired();
        setStatus(true);

    }

    private void fillTableVouchersToday() {
        DefaultTableModel tblmodel = (DefaultTableModel) tblVouchersToday.getModel();
        tblmodel.setRowCount(0);

        List<Voucher> listVocher = voucherDao.findAllVoucherToday();
        for (Voucher voucher : listVocher) {
            Object[] row = {
                voucher.getVoucherName(), voucher.getVoucherCode(),
                voucher.getDiscountByPrice() == 0 ? "THEO PHẦN TRĂM" : "THEO GIÁ TIỀN",
                voucher.getStratDate() + " - " + voucher.getEnddate(),
                voucher.getDiscountByPrice() == 0 ? voucher.getDiscountByPercent() + " %" : formatter.format(voucher.getDiscountByPrice()) + " VNĐ",
                formatter.format(voucher.getAmountApplied()) + " VNĐ", formatter.format(voucher.getMaximunAmount()) + " VNĐ", voucher.getNotes()
            };

            tblmodel.addRow(row);
        }

    }

    private void fillTableVouchersComningSoon() {
        DefaultTableModel tblmodel = (DefaultTableModel) tblVoucherComningSoon.getModel();
        tblmodel.setRowCount(0);

        List<Voucher> listVocher = voucherDao.findAllVoucherComingSoon();
        for (Voucher voucher : listVocher) {
           Object[] row = {
                voucher.getVoucherName(), voucher.getVoucherCode(),
                voucher.getDiscountByPrice() == 0 ? "THEO PHẦN TRĂM" : "THEO GIÁ TIỀN",
                voucher.getStratDate() + " - " + voucher.getEnddate(),
                voucher.getDiscountByPrice() == 0 ? voucher.getDiscountByPercent() + " %" : formatter.format(voucher.getDiscountByPrice()) + " VNĐ",
                formatter.format(voucher.getAmountApplied()) + " VNĐ", formatter.format(voucher.getMaximunAmount()) + " VNĐ", voucher.getNotes()
            };

            tblmodel.addRow(row);
        }

    }

    private void fillTableVouchersExpired() {
        DefaultTableModel tblmodel = (DefaultTableModel) tblVouchersExpired.getModel();
        tblmodel.setRowCount(0);

        List<Voucher> listVocher = voucherDao.findAllVoucherExpired();
        for (Voucher voucher : listVocher) {
           Object[] row = {
                voucher.getVoucherName(), voucher.getVoucherCode(),
                voucher.getDiscountByPrice() == 0 ? "THEO PHẦN TRĂM" : "THEO GIÁ TIỀN",
                voucher.getStratDate() + " - " + voucher.getEnddate(),
                voucher.getDiscountByPrice() == 0 ? voucher.getDiscountByPercent() + " %" : formatter.format(voucher.getDiscountByPrice()) + " VNĐ",
                formatter.format(voucher.getAmountApplied()) + " VNĐ", formatter.format(voucher.getMaximunAmount()) + " VNĐ", voucher.getNotes()
            };

            tblmodel.addRow(row);
        }

    }

    private Voucher getModel() {
        Voucher voucher = new Voucher();
        voucher.setVoucherCode(txtVoucherCode.getText());
        voucher.setVoucherName(txtVoucherName.getText());
        if (cbxTypeOfVoucher.getSelectedIndex() == 1) {
            voucher.setDiscountByPrice(Double.parseDouble(txtDiscount.getText()));
            voucher.setDiscountByPercent(0.0);
        } else {
            voucher.setDiscountByPercent(Double.parseDouble(txtDiscount.getText()));
            voucher.setDiscountByPrice(0.0);
        }

        voucher.setStratDate(dateStartDate.getDate());
        voucher.setEnddate(dateEndDate.getDate());
        voucher.setAmountApplied(Double.parseDouble(txtAmountApplied.getText()));
        voucher.setMaximunAmount(Double.parseDouble(txtMaximunAmout.getText()));
        voucher.setNotes(txtNotes.getText());

        return voucher;
    }

    private void setModel(Voucher voucher) {
        txtVoucherCode.setText(voucher.getVoucherCode());
        txtVoucherName.setText(voucher.getVoucherName());

        if (voucher.getDiscountByPrice() == 0) {
            txtDiscount.setText(String.valueOf(voucher.getDiscountByPercent()));
            cbxTypeOfVoucher.setSelectedIndex(2);
        } else {
            txtDiscount.setText(String.valueOf(voucher.getDiscountByPrice()));
            cbxTypeOfVoucher.setSelectedIndex(1);
        }

        txtAmountApplied.setText(String.valueOf(voucher.getAmountApplied()));
        txtMaximunAmout.setText(String.valueOf(voucher.getMaximunAmount()));
        txtNotes.setText(voucher.getNotes());

        dateStartDate.setDate(voucher.getStratDate());
        dateEndDate.setDate(voucher.getEnddate());
    }

    private void tableMouseClick(String voucherCode) {

        Voucher voucher = voucherDao.findById(voucherCode);

        setModel(voucher);
        setStatus(false);
    }

    private boolean checkForm() {
        String voucherCode = txtVoucherCode.getText();
        String voucherName = txtVoucherName.getText();
        Date startDate = dateStartDate.getDate();
        Date endDate = dateEndDate.getDate();

        if (voucherCode.equals("") || voucherName.equals("") || startDate.equals("")
                || endDate.equals("") || txtAmountApplied.getText().equals("") || txtMaximunAmout.getText()
                .equals("") || txtDiscount.getText().equals("")) {
            DialogHelper.alert(this, "Vui lòng nhập đầy đủ thông tin !");
            return true;

        }

        if (cbxTypeOfVoucher.getSelectedIndex() == 0) {
            DialogHelper.alert(this, "Vui lòng chọn mã giảm giá!");
            return true;
        }

        try {
            Double amountApplied = Double.parseDouble(txtAmountApplied.getText());
            Double maximunAmount = Double.parseDouble(txtMaximunAmout.getText());
            Double discount = Double.parseDouble(txtDiscount.getText());

            if (amountApplied <= 0) {
                DialogHelper.alert(this, "Số tiền được áp dụng phải lớn hơn 0! Vui lòng nhập lại!");
                return true;
            }
            if (maximunAmount <= 0) {
                DialogHelper.alert(this, "Số tiền giảm tối đa phải lớn hơn 0! Vui lòng nhập lại!");
                return true;
            }
            if (discount <= 0) {
                DialogHelper.alert(this, "Mức giảm giá phải lớn hơn 0! Vui lòng nhập lại!");
                return true;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Vui lòng nhập đúng định dạng số !");
            return true;
        }

        return false;

    }

    void setStatus(boolean insertable) {
        txtVoucherCode.setEditable(insertable);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtVoucherName = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txtVoucherCode = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        cbxTypeOfVoucher = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        dateStartDate = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        dateEndDate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtAmountApplied = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        txtMaximunAmout = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNotes = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVouchersToday = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblVoucherComningSoon = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblVouchersExpired = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TẠO MÃ GIẢM GIÁ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("TÊN CHƯƠNG TRÌNH");

        jSeparator1.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator1.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("MÃ GIẢM GIÁ");

        jSeparator2.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator2.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("LOẠI GIẢM GIÁ");

        cbxTypeOfVoucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn loại mã", "Giảm giá theo giá tiền", "Giảm giá theo phần trăm" }));
        cbxTypeOfVoucher.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("TỪ NGÀY");

        dateStartDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        dateStartDate.setDateFormatString("dd-MM-yyyy");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("ĐẾN NGÀY");

        dateEndDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        dateEndDate.setDateFormatString("dd-MM-yyyy");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setText("SỐ TIỀN ĐƯỢC ÁP DỤNG");

        jSeparator3.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator3.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setText("SỐ TIỀN GIẢM TỐI ĐA");

        jSeparator4.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator4.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 153));
        jLabel9.setText("GHI CHÚ");

        txtNotes.setColumns(20);
        txtNotes.setRows(5);
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

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 153));
        jLabel10.setText("MỨC GIẢM GIÁ");

        jSeparator5.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator5.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        tblVouchersToday.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "TÊN CT", "MÃ GIẢM GIÁ", "LOẠI", "THỜI GIAN", "GIẢM GIÁ", "ĐIỀU KIỆN", "HẠN MỨC", "GHI CHÚ"
            }
        ));
        tblVouchersToday.getTableHeader().setReorderingAllowed(false);
        tblVouchersToday.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVouchersTodayMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVouchersToday);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 767, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 723, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("ĐANG DIỄN RA", jPanel1);

        tblVoucherComningSoon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "TÊN CT", "MÃ GIẢM GIÁ", "LOẠI", "THỜI GIAN", "GIẢM GIÁ", "ĐIỀU KIỆN", "HẠN MỨC", "GHI CHÚ"
            }
        ));
        tblVoucherComningSoon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVoucherComningSoonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblVoucherComningSoon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 767, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 723, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("SẮP DIỄN RA", jPanel2);

        tblVouchersExpired.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "TÊN CT", "MÃ GIẢM GIÁ", "LOẠI", "THỜI GIAN", "GIẢM GIÁ", "ĐIỀU KIỆN", "HẠN MỨC", "GHI CHÚ"
            }
        ));
        tblVouchersExpired.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVouchersExpiredMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblVouchersExpired);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 767, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 723, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("HẾT HẠN", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtVoucherName, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxTypeOfVoucher, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtVoucherCode, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel7)
                                                            .addComponent(txtAmountApplied, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGap(31, 31, 31))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel5)
                                                    .addGap(182, 182, 182)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(dateStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dateEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel8)
                                                .addComponent(txtMaximunAmout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtDiscount, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 38, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtVoucherName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtVoucherCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(cbxTypeOfVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAmountApplied, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaximunAmout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete)
                    .addComponent(btnAdd)
                    .addComponent(btnClear)
                    .addComponent(btnUpdate))
                .addGap(58, 58, 58))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (checkForm()) {
            return;
        }
        if (voucherDao.findById(txtVoucherCode.getText()) != null) {
            DialogHelper.alert(this, "Mã giảm giá đã tồn tại vui lòng nhập lại!");
            return;
        }
        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (checkForm()) {
            return;
        }
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblVouchersTodayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVouchersTodayMouseClicked
        String voucherCode = (String) tblVouchersToday.getValueAt(tblVouchersToday.getSelectedRow(), 1);
        tableMouseClick(voucherCode);
    }//GEN-LAST:event_tblVouchersTodayMouseClicked

    private void tblVoucherComningSoonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVoucherComningSoonMouseClicked
        String voucherCode = (String) tblVoucherComningSoon.getValueAt(tblVoucherComningSoon.getSelectedRow(), 1);
        tableMouseClick(voucherCode);
    }//GEN-LAST:event_tblVoucherComningSoonMouseClicked

    private void tblVouchersExpiredMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVouchersExpiredMouseClicked
        String voucherCode = (String) tblVouchersExpired.getValueAt(tblVouchersExpired.getSelectedRow(), 1);
        tableMouseClick(voucherCode);
    }//GEN-LAST:event_tblVouchersExpiredMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbxTypeOfVoucher;
    private com.toedter.calendar.JDateChooser dateEndDate;
    private com.toedter.calendar.JDateChooser dateStartDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblVoucherComningSoon;
    private javax.swing.JTable tblVouchersExpired;
    private javax.swing.JTable tblVouchersToday;
    private javax.swing.JTextField txtAmountApplied;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtMaximunAmout;
    private javax.swing.JTextArea txtNotes;
    private javax.swing.JTextField txtVoucherCode;
    private javax.swing.JTextField txtVoucherName;
    // End of variables declaration//GEN-END:variables
}
