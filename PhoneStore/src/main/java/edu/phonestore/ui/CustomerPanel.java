/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edu.phonestore.ui;

import edu.phonestore.dao.CustomerDao;
import edu.phonestore.helper.ButtonHelper;
import edu.phonestore.helper.DialogHelper;
import edu.phonestore.model.Customer;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Văn Dự
 */
public class CustomerPanel extends javax.swing.JPanel {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private CustomerDao customerDao = new CustomerDao();
    private List<Customer> listCustomer = customerDao.findAll();

    public CustomerPanel() {
        initComponents();
        setBounds(0, 0, 1267, 760);
        init();
        initButton();
    }

    private void init() {
        fillTable();
        dateCreateDate.setDate(new Date());
        dateCreateDate.setEnabled(false);
        setStatus(true);

    }

    private void initButton() {
        btnAdd.addMouseListener(new ButtonHelper(btnAdd));
        btnUpdate.addMouseListener(new ButtonHelper(btnUpdate));
        btnDelete.addMouseListener(new ButtonHelper(btnDelete));
        btnClear.addMouseListener(new ButtonHelper(btnClear));
        btnRand.addMouseListener(new ButtonHelper(btnRand));
    }

    private void fillTable() {
        DefaultTableModel tblmodel = (DefaultTableModel) tblCustomer.getModel();
        tblmodel.setRowCount(0);

        for (Customer customer : listCustomer) {
            Object[] row = {
                customer.getCustomerId(), customer.getFullname(), customer.getPhone(), customer.getEmail(), customer.isGender(),
                customer.getBirthday(), customer.getCreateDate(), customer.getAddress(), customer.getNotes(), customer.getPurchaseTimes()
            };

            tblmodel.addRow(row);
        }
    }

    private void insert() {
        try {
            Customer customer = getModel();
            customer.setPurchaseTimes(0);
            if (customerDao.insert(customer)) {
                DialogHelper.alert(this, "Thêm khách hàng thành công!");
            } else {
                DialogHelper.alert(this, "Thêm khách hàng thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Thêm khách hàng thất bại!");
        }

        clear();

    }

    private void update() {
        try {
            Customer customer = getModel();
            if (customerDao.update(customer)) {
                DialogHelper.alert(this, "Cật nhật thông tin khách hàng thành công!");
            } else {
                DialogHelper.alert(this, "Cật nhật thông tin  khách hàng thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Cật nhật thông tin  khách hàng thất bại!");
        }
        clear();

    }

    private void delete() {
        try {
            Customer customer = getModel();
            if (customerDao.delete(customer.getCustomerId())) {
                DialogHelper.alert(this, "Xóa khách hàng thành công!");
            } else {
                DialogHelper.alert(this, "Xóa khách hàng thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Xóa khách hàng thất bại!");
        }
        clear();

    }

    private void clear() {
        txtCustomerId.setText("");
        txtFullname.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        rdoMale.setSelected(true);
        dateBirthDay.setDate(new Date());
        dateCreateDate.setDate(new Date());
        txtAddress.setText("");
        txtNotes.setText("");

        listCustomer = customerDao.findAll();
        fillTable();
        setStatus(true);
    }

    private Customer getModel() throws ParseException {
        Customer customer = new Customer();
        customer.setCustomerId(txtCustomerId.getText());
        customer.setFullname(txtFullname.getText());
        customer.setPhone(txtPhone.getText());
        customer.setEmail(txtEmail.getText());
        customer.setGender(rdoMale.isSelected());
        customer.setBirthday(dateBirthDay.getDate());
        customer.setCreateDate(dateCreateDate.getDate());
        customer.setAddress(txtAddress.getText());
        customer.setNotes(txtNotes.getText());

        return customer;
    }

    private void setModel(Customer customrer) {
        txtCustomerId.setText(customrer.getCustomerId());
        txtFullname.setText(customrer.getFullname());
        txtPhone.setText(customrer.getPhone());
        txtEmail.setText(customrer.getEmail());
        rdoMale.setSelected(customrer.isGender());
        dateBirthDay.setDate(customrer.getBirthday());
        dateCreateDate.setDate(customrer.getCreateDate());
        txtAddress.setText(customrer.getAddress());
        txtNotes.setText(customrer.getNotes());
    }

    void setStatus(boolean insertable) {
        txtCustomerId.setEditable(insertable);
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

    private boolean checkForm() {
        String customerId = txtCustomerId.getText();
        String fullname = txtFullname.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        Boolean gender = rdoMale.isSelected();
        Date birthDay = dateBirthDay.getDate();
        String address = txtAddress.getText();

        if (customerId.equals("") || fullname.equals("") || phone.equals("") || email.equals("") || birthDay.equals("") || address.equals("")) {
            DialogHelper.alert(this, "Vui lòng nhập đầy đủ thông tin !");
            return true;
        }

        String reEmail = "\\w+@\\w+(\\.\\w+){1,2}";
        if (!email.matches(reEmail)) {
            DialogHelper.alert(this, "Vui lòng nhập email đúng định dạng !");
            return true;
        }
        String rePhone = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
        if (!phone.matches(rePhone)) {
            DialogHelper.alert(this, "Vui lòng nhập số điện thoại đúng định dạng !");
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCustomerId = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtFullname = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNotes = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        dateCreateDate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        dateBirthDay = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        rdoMale = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        btnRand = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        btnClear1 = new javax.swing.JButton();

        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ KH", "TÊN KH", "SĐT", "EMAIL", "GIỚI TÍNH", "NGÀY SINH", "NGÀY TẠO", "ĐỊA CHỈ", "GHI CHÚ", "SÓ LẦN MUA"
            }
        ));
        tblCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCustomerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCustomer);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("MÃ KHÁCH HÀNG");

        jSeparator1.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator1.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("TÊN KHÁCH HÀNG");

        jSeparator2.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator2.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("GHI CHÚ");

        txtNotes.setColumns(20);
        txtNotes.setRows(5);
        txtNotes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        jScrollPane2.setViewportView(txtNotes);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("EMAIL");

        jSeparator3.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator3.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("SĐT KHÁCH HÀNG");

        jSeparator4.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator4.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("NGÀY SINH");

        dateCreateDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        dateCreateDate.setDateFormatString("dd-MM-yyyy");
        dateCreateDate.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setText("NGÀY TẠO");

        dateBirthDay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        dateBirthDay.setDateFormatString("dd-MM-yyyy");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setText("GIỚI TÍNH");

        buttonGroup1.add(rdoMale);
        rdoMale.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoMale.setText("NAM");

        buttonGroup1.add(rdoFemale);
        rdoFemale.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoFemale.setText("NỮ");

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

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 153));
        jLabel9.setText("ĐỊA CHỈ KHÁCH HÀNG");

        jSeparator5.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator5.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        btnRand.setBackground(new java.awt.Color(0, 51, 153));
        btnRand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Plus_32px.png"))); // NOI18N
        btnRand.setBorder(null);
        btnRand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(0, 51, 153));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Search_32px.png"))); // NOI18N
        btnSearch.setBorder(null);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 153));
        jLabel10.setText("TÌM KIẾM KHÁCH HÀNG");

        txtSearch.setBackground(new java.awt.Color(240, 240, 240));
        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSearch.setBorder(null);
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jSeparator6.setBackground(new java.awt.Color(0, 51, 153));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153), 2));

        btnClear1.setBackground(new java.awt.Color(0, 51, 153));
        btnClear1.setForeground(new java.awt.Color(0, 51, 153));
        btnClear1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Refresh_30px.png"))); // NOI18N
        btnClear1.setBorder(null);
        btnClear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addComponent(txtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                            .addComponent(jSeparator5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(23, 23, 23)
                                                .addComponent(btnRand))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel4)
                                                .addComponent(txtEmail)
                                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(235, 235, 235)
                                        .addComponent(jLabel8)
                                        .addGap(163, 163, 163))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(70, 70, 70)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel5)
                                            .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                                            .addComponent(jLabel2)
                                            .addComponent(txtFullname)
                                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                                            .addComponent(txtPhone))
                                        .addGap(92, 92, 92)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dateBirthDay, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(dateCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(rdoMale)
                                .addGap(27, 27, 27)
                                .addComponent(rdoFemale))
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                            .addComponent(jSeparator6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClear1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch)
                    .addComponent(btnClear1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnRand))
                                .addGap(37, 37, 37)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dateCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dateBirthDay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoMale)
                        .addComponent(rdoFemale)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnRandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandActionPerformed
        int rand = (int) (Math.random() * 999999);
        txtCustomerId.setText("KH" + rand);
    }//GEN-LAST:event_btnRandActionPerformed

    private void tblCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomerMouseClicked
        String customerId = (String) tblCustomer.getValueAt(tblCustomer.getSelectedRow(), 0);

        Customer customer = customerDao.findById(customerId);
        setModel(customer);
        setStatus(false);
    }//GEN-LAST:event_tblCustomerMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (checkForm()) {
            return;
        }

        if (customerDao.findById(txtCustomerId.getText()) != null) {
            DialogHelper.alert(this, "Mã khách hàng đã tồn tại!Vui lòng nhập lại!");
            return;
        }

        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String keyword = txtSearch.getText();
        listCustomer = customerDao.findByKeyWord(keyword);
        fillTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        btnSearchActionPerformed(evt);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnClear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear1ActionPerformed
        listCustomer=customerDao.findAll();
        fillTable();
    }//GEN-LAST:event_btnClear1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClear1;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRand;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dateBirthDay;
    private com.toedter.calendar.JDateChooser dateCreateDate;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JTable tblCustomer;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtCustomerId;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JTextArea txtNotes;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
