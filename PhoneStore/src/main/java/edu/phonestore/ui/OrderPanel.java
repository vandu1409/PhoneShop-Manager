/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edu.phonestore.ui;

import edu.phonestore.dao.OrderDao;
import edu.phonestore.dao.OrderDetalisDao;
import edu.phonestore.helper.DialogHelper;
import edu.phonestore.model.Order;
import edu.phonestore.model.Product;
import edu.phonestore.utils.FileUtils;
import java.awt.Color;
import java.awt.Dialog;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Văn Dự
 */
public class OrderPanel extends javax.swing.JPanel {

    private DecimalFormat formatter = new DecimalFormat("###,###,###");
    OrderDao orderDao = new OrderDao();
    OrderDetalisDao orderDetalisDao = new OrderDetalisDao();
    List<Order> listOrders = orderDao.findAll();

    public OrderPanel() {
        initComponents();
        setBounds(0, 0, 1233, 749);
        fillTableOrder();
        tblProducts.setRowHeight(75);
        tblProducts.setShowGrid(false);
        tblProducts.getTableHeader().setOpaque(false);
        tblProducts.getTableHeader().setBackground(new Color(0, 51, 153));
        tblProducts.getTableHeader().setForeground(Color.WHITE);
        dcDateMin.setDate(new Date());
        dcDateMax.setDate(new Date());
    }

    private void fillTableOrder() {
        DefaultTableModel tblmodel = (DefaultTableModel) tblOrders.getModel();
        tblmodel.setRowCount(0);

        for (Order order : listOrders) {
            Object[] row = {order.getOrderId(), formatter.format(order.getTotal()) + " VNĐ", order.getCreateDate(), order.getCustomerId(),
                order.getVoucherCode(), formatter.format(order.getDiscount()) + " VNĐ", formatter.format(order.getMemberDiscount()) + " VNĐ", order.getStatus(), order.getCreator()};
            tblmodel.addRow(row);
        }
    }

    private Order getModelFromTblOrder() {
        String orderId = (String) tblOrders.getValueAt(tblOrders.getSelectedRow(), 0);
        Order order = orderDao.findById(orderId);

        return order;

    }

    private void fillTableProducts() {
        String orderId = (String) tblOrders.getValueAt(tblOrders.getSelectedRow(), 0).toString().trim();
        DefaultTableModel tblmodel = (DefaultTableModel) tblProducts.getModel();
        tblmodel.setRowCount(0);

        List<Product> list = orderDetalisDao.findProductByOrderId(orderId);

        for (Product product : list) {

            ImageIcon icon = FileUtils.readLogo(product.getImage(), 50, 70);

            Object[] row = {
                icon, product.getProductId(),
                product.getTitle(), formatter.format(product.getPrice()) + " VNĐ", product.getQuantity(),
                product.getRam() + "GB - " + product.getRom() + "GB - " + product.getColor(),
                formatter.format(product.getPrice() * product.getQuantity()) + " VNĐ"
            };

            tblmodel.addRow(row);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSearch = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        dcDateMax = new com.toedter.calendar.JDateChooser();
        btnFillter = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        dcDateMin = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();

        txtSearch.setBackground(new java.awt.Color(240, 240, 240));
        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSearch.setBorder(null);

        jSeparator1.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator1.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("TÌM KIẾM THEO KHÁCH HÀNG");

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("LỌC THEO NGÀY");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DANH SÁCH HÓA ĐƠN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N

        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ HD", "TỔNG TIỀN", "NGÀY TẠO", "TÊN KH", "MÃ GIẢM GIÁ", "GIẢM GIÁ", "THÀNH VIÊN", "TRẠNG THÁI", "NGƯỜI TẠO"
            }
        ));
        tblOrders.getTableHeader().setReorderingAllowed(false);
        tblOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdersMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblOrders);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1182, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SẢN PHẨM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ẢNH", "MÃ SP", "TÊN SP", "GIÁ", "SỐ LƯỢNG", "RAM - ROM - COLOR", "TỔNG TIỀN"
            }
        ) {
            Class[] types = new Class [] {
                javax.swing.ImageIcon.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblProducts.getTableHeader().setReorderingAllowed(false);
        tblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductsMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblProducts);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );

        dcDateMax.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        dcDateMax.setDateFormatString("dd-MM-yyyy");

        btnFillter.setBackground(new java.awt.Color(0, 51, 153));
        btnFillter.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFillter.setForeground(new java.awt.Color(255, 255, 255));
        btnFillter.setText("LỌC");
        btnFillter.setBorder(null);
        btnFillter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFillterActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("ĐẾN");

        dcDateMin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        dcDateMin.setDateFormatString("dd-MM-yyyy");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("TỪ");

        jButton1.setBackground(new java.awt.Color(0, 51, 153));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/replace_32px.png"))); // NOI18N
        jButton1.setBorder(null);

        btnSearch.setBackground(new java.awt.Color(0, 51, 153));
        btnSearch.setForeground(new java.awt.Color(0, 51, 15));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Search_32px.png"))); // NOI18N
        btnSearch.setBorder(null);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(582, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(dcDateMin, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(dcDateMax, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnFillter, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1214, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(9, 9, 9)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dcDateMax, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(dcDateMin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFillter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdersMouseClicked
        fillTableProducts();
    }//GEN-LAST:event_tblOrdersMouseClicked

    private void tblProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductsMouseClicked
        String productId = (String) tblProducts.getValueAt(tblProducts.getSelectedRow(), 1);
        new ProductDetails(null, true, productId).setVisible(true);
    }//GEN-LAST:event_tblProductsMouseClicked

    private void btnFillterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFillterActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date dateMin = this.dcDateMin.getDate();
        Date dateMax = this.dcDateMax.getDate();

        if (dateMin == null || dateMax == null) {
            DialogHelper.alert(this, "Vui lòng chọn ngày!");
            return;
        }

        if (dateMax.compareTo(dateMin) < 0) {
            DialogHelper.alert(this, "Vui lòng chọn ngày sau lớn hơn ngày trước!");
            return;
        }

        listOrders = orderDao.findByDate(sdf.format(dateMin), sdf.format(dateMax));
        fillTableOrder();
        tblOrders.clearSelection();
        tblProducts.clearSelection();
        DefaultTableModel tblmodel = (DefaultTableModel) tblProducts.getModel();
        tblmodel.setRowCount(0);
    }//GEN-LAST:event_btnFillterActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String customerName = txtSearch.getText().trim();
        listOrders = orderDao.findByCustomerName(customerName);
        fillTableOrder();
        tblOrders.clearSelection();
        tblProducts.clearSelection();
        DefaultTableModel tblmodel = (DefaultTableModel) tblProducts.getModel();
        tblmodel.setRowCount(0);
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFillter;
    private javax.swing.JButton btnSearch;
    private com.toedter.calendar.JDateChooser dcDateMax;
    private com.toedter.calendar.JDateChooser dcDateMin;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblOrders;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
