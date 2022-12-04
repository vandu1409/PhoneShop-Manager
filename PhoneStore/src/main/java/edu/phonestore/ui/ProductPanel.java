/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edu.phonestore.ui;

import edu.phonestore.dao.BrandDao;
import edu.phonestore.dao.ProductDao;
import edu.phonestore.helper.ButtonHelper;
import edu.phonestore.helper.DialogHelper;

import edu.phonestore.model.Brand;
import edu.phonestore.model.Product;
import edu.phonestore.model.User;
import edu.phonestore.utils.FileUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Văn Dự
 */
public class ProductPanel extends javax.swing.JPanel {

    private BrandDao brandDao = new BrandDao();
    private ProductDao productDao = new ProductDao();
    private int index = -1;
    private List<Product> listProducts = productDao.findAll();
    private DecimalFormat formatter = new DecimalFormat("###,###,###");

    public ProductPanel() {
        initComponents();
        setBounds(0, 0, 1267, 760);
        init();
        initButton();
    }

    private void init() {
        tblProducts.getTableHeader().setOpaque(false);
        tblProducts.getTableHeader().setBackground(new Color(0, 51, 153));
        tblProducts.getTableHeader().setForeground(Color.WHITE);
        fillCbx();
        fillTable();
        setStatus(true);
    }

    private void initButton() {
        btnAdd.addMouseListener(new ButtonHelper(btnAdd));
        btnUpdate.addMouseListener(new ButtonHelper(btnUpdate));
        btnDelete.addMouseListener(new ButtonHelper(btnDelete));
        btnClear.addMouseListener(new ButtonHelper(btnClear));
    }

    private void insert() {
        Product product = getModel();
        try {
            if (productDao.insert(product)) {
                DialogHelper.alert(this, "Thêm sản phẩm thành công");
                clear();
            } else {
                DialogHelper.alert(this, "Thêm sản phẩm thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Thêm sản phẩm thất bại!");
        }
    }

    private void update() {
        Product product = getModel();
        try {
            if (productDao.update(product)) {
                DialogHelper.alert(this, "Cật nhật sản phẩm thành công");
                clear();
            } else {
                DialogHelper.alert(this, "Cật nhật sản phẩm thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Cật nhật sản phẩm thất bại!");
        }

    }

    private void delete() {

        try {
            if (productDao.delete(txtId.getText())) {
                DialogHelper.alert(this, "Xóa sản phẩm thành công");
                clear();
            } else {
                DialogHelper.alert(this, "Xóa sản phẩm thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Xóa sản phẩm thất bại!");
        }

    }

    void clear() {
        txtId.setText("");
        txtTitle.setText("");
        txtImportPrice.setText("");
        txtPrice.setText("");
        txtRam.setText("");
        txtRom.setText("");
        txtQuantity.setText("");
        cbxBrand.setSelectedIndex(0);
        cbxColor.setSelectedIndex(0);
        tblProducts.clearSelection();
        lblImage.setIcon(null);
        lblImage.setToolTipText("");
        setStatus(true);
        listProducts = productDao.findAll();
        fillTable();
    }

    void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (FileUtils.saveLogo(file)) {
                // Hiển thị hình lên form
                lblImage.setIcon(FileUtils.readLogo(file.getName(), 182, 194));
                lblImage.setToolTipText(file.getName());
            }
        }
    }

    public Product getModel() {
        Product product = new Product();
        product.setProductId(txtId.getText());
        product.setTitle(txtTitle.getText());
        product.setPrice(Double.parseDouble(txtPrice.getText()));
        product.setImportPrice(Double.parseDouble(txtImportPrice.getText()));
        product.setRam(Integer.parseInt(txtRam.getText()));
        product.setRom(Integer.parseInt(txtRom.getText()));
        product.setQuantity(Integer.parseInt(txtQuantity.getText()));
        product.setImage(lblImage.getToolTipText());
        Brand brand = (Brand) cbxBrand.getSelectedItem();
        product.setBrandId(brand.getId());
        product.setColor((String) cbxColor.getSelectedItem());

        return product;
    }

    private void fillCbx() {
        DefaultComboBoxModel cbxmodel = (DefaultComboBoxModel) cbxBrand.getModel();
        cbxmodel.removeAllElements();
        cbxmodel.addElement("Chọn thương hiệu");
        List<Brand> list = brandDao.findAll();

        for (Brand brand : list) {
            cbxmodel.addElement(brand);
        }

    }

    private void fillTable() {
        DefaultTableModel tblmodel = (DefaultTableModel) tblProducts.getModel();
        tblmodel.setRowCount(0);

        for (Product product : listProducts) {

            Object[] row = {
                product.getProductId(), product.getTitle(),
                formatter.format(product.getImportPrice()) + " VNĐ", formatter.format(product.getPrice()) + " VNĐ",
                product.getRam(), product.getRom(), product.getColor(), product.getBrandId(), product.getQuantity()
            };

            tblmodel.addRow(row);
        }
    }

    private void setModel(Product product) {
        txtId.setText(product.getProductId());
        txtTitle.setText(product.getTitle());
        txtImportPrice.setText(String.format("%.2f", product.getImportPrice()));
        txtPrice.setText(String.format("%.2f", product.getPrice()));
        txtRam.setText(String.valueOf(product.getRam()));
        txtRom.setText(String.valueOf(product.getRom()));
        txtQuantity.setText(String.valueOf(product.getQuantity()));
        Brand brand = brandDao.findById(product.getBrandId());
        cbxBrand.setSelectedItem(brand);
        cbxColor.setSelectedItem(product.getColor());

        if (product.getImage() == null) {
            lblImage.setIcon(null);
            lblImage.setToolTipText("");
        } else {
            lblImage.setIcon(FileUtils.readLogo(product.getImage(), 182, 194));
            lblImage.setToolTipText(product.getImage());
        }

    }

    void edit() {
        try {
            String productId = (String) tblProducts.getValueAt(this.index, 0);
            Product model = productDao.findById(productId);
            if (model != null) {
                this.setModel(model);
                tblProducts.setRowSelectionInterval(index, index);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setStatus(boolean insertable) {
        txtId.setEditable(insertable);
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
        if (txtId.getText().equals("") || txtTitle.getText().equals("")
                || txtPrice.getText().equals("") || txtImportPrice.getText().equals("")
                || txtRam.getText().equals("") || txtRom.getText().equals("") || cbxColor.getSelectedIndex() == 0 || cbxBrand.getSelectedIndex() == 0) {
            DialogHelper.alert(this, "Vui lòng nhập đầy đủ thông tin !");
            return true;
        }

        try {
            double price = Double.parseDouble(txtPrice.getText());
            double importPrice = Double.parseDouble(txtImportPrice.getText());
            double ram = Double.parseDouble(txtRam.getText());
            double rom = Double.parseDouble(txtRom.getText());
            double quantity = Double.parseDouble(txtQuantity.getText());

            if (ram <= 0) {
                DialogHelper.alert(this, "Ram phải lớn hơn 0 ! Vui lòng kiểm tra lại !");
                return true;
            }

            if (rom <= 0) {
                DialogHelper.alert(this, "Rom phải lớn hơn 0 ! Vui lòng kiểm tra lại !");
                return true;
            }

            if (quantity <= 0) {
                DialogHelper.alert(this, "Số lượng phải lớn hơn 0 ! Vui lòng kiểm tra lại !");
                return true;
            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Vui lòng nhập đúng định dạng!");
            return true;
        }

        if (lblImage.getToolTipText() == null || lblImage.getToolTipText().equals("")) {
            DialogHelper.alert(this, "Vui lòng chọn hình ảnh sản phẩm!");
            return true;
        }

        return false;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        lblImage = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtImportPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRom = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtRam = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbxColor = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbxBrand = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("Tìm kiếm");

        txtSearch.setBackground(new java.awt.Color(240, 240, 240));
        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSearch.setBorder(null);
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator1.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "TÊN SẢN PHẨM", "GIÁ NHẬP VÀO", "GIÁ BÁN RA", "RAM", "ROM", "MÀU SẮC", "THƯƠNG HIỆU", "SỐ LƯỢNG"
            }
        ));
        tblProducts.getTableHeader().setReorderingAllowed(false);
        tblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProducts);

        lblImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153), 2));
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Tên sản phẩm");

        txtPrice.setBackground(new java.awt.Color(240, 240, 240));
        txtPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrice.setBorder(null);
        txtPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Giá nhập");

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Giá bán");

        txtImportPrice.setBackground(new java.awt.Color(240, 240, 240));
        txtImportPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtImportPrice.setBorder(null);

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("ID");

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setText("RAM");

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setText("ROM");

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 153));
        jLabel9.setText("SỐ LƯỢNG");

        jLabel10.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 153));
        jLabel10.setText("MÀU SẮC");

        cbxColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn màu sắc", "Đỏ", "Cam", "Xanh Lá", "Xanh Ngọc", "Đen", "Vàng", "Tím", "Trắng", "Hồng", "Xanh đậm", "Xanh dương", " " }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 153));
        jLabel11.setText("THƯƠNG HIỆU");

        cbxBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn thương hiệu" }));

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

        btnReload.setBackground(new java.awt.Color(0, 51, 153));
        btnReload.setForeground(new java.awt.Color(0, 51, 153));
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/replace_32px.png"))); // NOI18N
        btnReload.setBorder(null);
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator2.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jSeparator3.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator3.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("VNĐ");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("VNĐ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(54, 1167, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cbxColor, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtRam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtRom)
                                    .addComponent(cbxBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtTitle)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtImportPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel12))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(130, 130, 130)
                                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnReload))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtImportPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12))
                        .addGap(8, 8, 8)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelete)
                            .addComponent(btnAdd)
                            .addComponent(btnClear)
                            .addComponent(btnUpdate))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRom, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRam, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxColor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed

    }//GEN-LAST:event_txtPriceActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (checkForm()) {
            return;
        }
        if (productDao.findById(txtId.getText()) != null) {
            DialogHelper.alert(this, "Mã sản phẩm đã tồn tại!Vui lòng nhập lại!");
            return;
        }
        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (checkForm()) {
            return;
        }
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked
        selectImage();
    }//GEN-LAST:event_lblImageMouseClicked

    private void tblProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductsMouseClicked
        index = tblProducts.getSelectedRow();
        edit();
    }//GEN-LAST:event_tblProductsMouseClicked

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        fillCbx();
        fillTable();
    }//GEN-LAST:event_btnReloadActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        listProducts = productDao.findByKeyword(txtSearch.getText());
        fillTable();
    }//GEN-LAST:event_txtSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbxBrand;
    private javax.swing.JComboBox<String> cbxColor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtImportPrice;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtRam;
    private javax.swing.JTextField txtRom;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables
}
