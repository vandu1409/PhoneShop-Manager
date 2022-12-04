/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edu.phonestore.ui;

import edu.phonestore.dao.BrandDao;
import edu.phonestore.dao.CustomerDao;
import edu.phonestore.dao.OrderDao;
import edu.phonestore.dao.OrderDetalisDao;
import edu.phonestore.dao.ProductDao;
import edu.phonestore.dao.VoucherDao;
import edu.phonestore.helper.ButtonHelper;
import edu.phonestore.helper.DialogHelper;
import edu.phonestore.helper.ShareData;
import edu.phonestore.helper.ComboxHelper;
import edu.phonestore.model.Brand;
import edu.phonestore.model.Customer;
import edu.phonestore.model.Order;
import edu.phonestore.model.OrderDetalis;
import edu.phonestore.model.Product;
import edu.phonestore.model.Voucher;
import edu.phonestore.utils.FileUtils;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Văn Dự
 */
public class SellPanel extends javax.swing.JPanel {

    /**
     * Creates new form SellPanel
     */
    private DecimalFormat formatter = new DecimalFormat("###,###,###");
    private ProductDao productDao = new ProductDao();
    private BrandDao brandDao = new BrandDao();
    private OrderDao orderDao = new OrderDao();
    private OrderDetalisDao orderDetalisDao = new OrderDetalisDao();
    private CustomerDao customerDao = new CustomerDao();
    private VoucherDao voucherDao = new VoucherDao();
    private int index = -1;
    private List<Product> listProductsOrder = new ArrayList<>();
    private List<Product> listProducts = productDao.findAll();
    private Double totalProcutCots; // tổng tiền sản phẩm
    private Double totalPrice;// tổng tiền
    private Double discount = 0.0, menberDiscount = 0.0;

    public SellPanel() {
        initComponents();
        init();
        setBounds(0, 0, 1267, 760);
        initTable();
        initButton();

    }

    void init() {

        dateCreateDate.setDate(new Date());
        dateCreateDate.setEnabled(false);

        fillTableProducts();
        fillCbxWaitingBill();
        fillCbxCustomer();
        fillCbxVoucher();

    }

    private void initTable() {
        tblProducts.getTableHeader().setOpaque(false);
        tblProducts.getTableHeader().setBackground(new Color(0, 51, 153));
        tblProducts.getTableHeader().setForeground(Color.WHITE);
        tblProducts.setShowVerticalLines(false);
        tblProducts.setRowHeight(55);

        tblOrders.getTableHeader().setOpaque(false);
        tblOrders.getTableHeader().setBackground(new Color(0, 51, 153));
        tblOrders.getTableHeader().setForeground(Color.WHITE);
        tblOrders.setShowVerticalLines(false);
        tblOrders.setRowHeight(55);
    }

    private void initButton() {
        btnPay.addMouseListener(new ButtonHelper(btnPay));
        btnWating.addMouseListener(new ButtonHelper(btnWating));
        btnSearch.addMouseListener(new ButtonHelper(btnSearch));
        btnReload.addMouseListener(new ButtonHelper(btnReload));
        btnRand.addMouseListener(new ButtonHelper(btnRand));
        btnReloadCbxCustomer.addMouseListener(new ButtonHelper(btnReloadCbxCustomer));
        btnReloadCbxVoucher.addMouseListener(new ButtonHelper(btnReloadCbxVoucher));
        btnClear.addMouseListener(new ButtonHelper(btnClear));
        btnDelete.addMouseListener(new ButtonHelper(btnDelete));
        btnDeleteProducts.addMouseListener(new ButtonHelper(btnDeleteProducts));
        btnDelete.setEnabled(false);
        btnDelete.setForeground(Color.black);
    }

    //đổ dư liệu vào bảng sản phẩm
    void fillTableProducts() {
        DefaultTableModel tblmodel = (DefaultTableModel) tblProducts.getModel();
        tblmodel.setRowCount(0);

        for (Product product : listProducts) {

            ImageIcon icon = FileUtils.readLogo(product.getImage(), 35, 50);

            Object[] row = {
                icon, product.getProductId(),
                product.getTitle(), formatter.format(product.getPrice()) + " VNĐ",
                product.getRam() + "GB - " + product.getRom() + "GB - " + product.getColor(), product.getQuantity()
            };

            tblmodel.addRow(row);
        }

    }

    // đổ dữ liệu vào bảng hóa đơn
    private void fillTableOrder() {

        DefaultTableModel tblmodel = (DefaultTableModel) tblOrders.getModel();
        tblmodel.setRowCount(0);

        if (listProductsOrder == null) {

            return;
        }

        totalProcutCots = 0.0;
        for (Product product : listProductsOrder) {
            ImageIcon icon = FileUtils.readLogo(product.getImage(), 35, 50);

            Object[] row = {
                icon, product.getProductId(),
                product.getTitle(), formatter.format(product.getPrice()) + " VNĐ",
                product.getRam() + "GB - " + product.getRom() + "GB - " + product.getColor(),
                product.getQuantity(), String.format("%.2f", product.getPrice() * product.getQuantity()) + " VNĐ"};
            totalProcutCots += product.getPrice() * product.getQuantity();
            tblmodel.addRow(row);
        }

        lblTotalPriceProducts.setText(formatter.format(totalProcutCots) + " VNĐ");
        txtTotal.setText(formatter.format(totalProcutCots) + " VNĐ");
        selectCbxCustomer();
        selectCbxVoucher();

    }

    //đổ dữ liệu vào cbx hóa đơn chờ
    private void fillCbxWaitingBill() {

        DefaultComboBoxModel cbxmodel = (DefaultComboBoxModel) cbxWaittingBill.getModel();

        cbxmodel.removeAllElements();
        System.out.println("ghsajdkl;fgh");
        cbxmodel.addElement("Chọn hóa đơn");

        List<Order> list = orderDao.findByStatus(Order.UNPAID);

        for (Order order : list) {
            cbxmodel.addElement(order);
        }

    }

    private void fillCbxCustomer() {
        DefaultComboBoxModel cbxmodel = (DefaultComboBoxModel) cbxCustomer.getModel();
        cbxmodel.removeAllElements();

        cbxmodel.addElement("Chọn khách hàng");

        List<Customer> listCustomer = customerDao.findAll();

        for (Customer customer : listCustomer) {
            cbxmodel.addElement(customer);
        }
    }

    private void fillCbxVoucher() {
        DefaultComboBoxModel cbxmodel = (DefaultComboBoxModel) cbxVoucher.getModel();
        cbxmodel.removeAllElements();

        cbxmodel.addElement("Chọn mã giảm giá");

        List<Voucher> listVoucher = voucherDao.findAllVoucherToday();

        for (Voucher voucher : listVoucher) {
            cbxmodel.addElement(voucher);
        }
    }

    //lấy sản phẩm đc chọn trong bảng
    private Product getModelFromTableProduct() {
        try {
            String productId = (String) tblProducts.getValueAt(this.index, 1);
            Product product = productDao.findById(productId);

            return product;
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

        return null;
    }

    //thêm sản  phẩm vào bảng hóa đơn
    private void addProduct() {
        String quantity = DialogHelper.prompt(this, "Số lượng");
        try {

            if (quantity == null || quantity.equals("")) {
                tblProducts.clearSelection();
                return;
            }

            if (Integer.parseInt(quantity) < 0) {
                DialogHelper.alert(this, "Vui lòng nhập số lượng lớn hơn 0");
                return;
            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Vui lòng nhập đúng định dạng!");
            return;
        }

        Product product = getModelFromTableProduct();
        product.setQuantity(Integer.parseInt(quantity));
        listProductsOrder.add(product);
        fillTableOrder();

    }

    private Order getModel() {
        Order order = new Order();
        Customer customer = (Customer) cbxCustomer.getSelectedItem();

        if (cbxVoucher.getSelectedIndex() == 0) {
            order.setVoucherCode(null);
        } else {
            Voucher voucher = (Voucher) cbxVoucher.getSelectedItem();
            order.setVoucherCode(voucher.getVoucherCode());
        }

        order.setOrderId(txtId.getText());
        order.setCustomerId(customer.getCustomerId());

        order.setDiscount(discount);
        order.setMemberDiscount(menberDiscount);
        order.setTotal(totalPrice);
        order.setCreator(ShareData.USER.getUsername());
        order.setCreateDate(new Date());
        return order;
    }

    private void setModel(Order order) {
        Customer customer = customerDao.findById(order.getCustomerId());
        Voucher voucher = voucherDao.findById(order.getVoucherCode());

        txtId.setText(order.getOrderId());
        txtId.setEnabled(false);
        txtStaff.setText(ShareData.USER.getUsername());
        dateCreateDate.setDate(order.getCreateDate());
        setSelectedValue(order.getCustomerId());

    }

    private void pay() {
        try {
            Order order = getModel();
            order.setStatus(Order.PAID);

            if (insertOrUpdate(order) == false) {
                DialogHelper.alert(this, "Thanh toán thành công!");
                Customer customer = customerDao.findById(order.getCustomerId());
                customer.setPurchaseTimes(customer.getPurchaseTimes() + 1);
                customerDao.update(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Thanh toán thất bại!");
        }
    }

    //chuyển sang hóa đơn chờ
    private void waittingBill() {
        try {
            Order order = getModel();
            order.setStatus(Order.UNPAID);

            if (insertOrUpdate(order) == false) {
                DialogHelper.alert(this, "Đã chuyển hóa đơn sang hóa đơn chờ!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Chuyển hóa đơn thất bại!");
        }
    }

    private boolean insertOrUpdate(Order order) {
        if (orderDao.findById(order.getOrderId()) == null) {
            if (order.getStatus().equals(Order.PAID)) {
                orderDao.insert(order);
            } else {
                order.setVoucherCode(null);
                order.setDiscount(0.0);
                orderDao.insert(order);
            }

        } else {

            //trả lại số lượng
            List<Product> list = orderDetalisDao.findProductByOrderId(order.getOrderId());
            for (Product product : list) {
                Product p = productDao.findById(product.getProductId());
                p.setQuantity(p.getQuantity() + product.getQuantity());
                productDao.update(p);
            }

            orderDetalisDao.deleteAllByOrder(order.getOrderId());

            orderDao.update(order);

        }

        for (Product product : listProductsOrder) {
            OrderDetalis orderDetalis = new OrderDetalis();
            orderDetalis.setProductId(product.getProductId());
            orderDetalis.setOrderId(order.getOrderId());
            orderDetalis.setQuatity(product.getQuantity());
            orderDetalis.setTotalProduct(product.getQuantity() * product.getPrice());

            // trừ số lượng
            Product p = productDao.findById(product.getProductId());
            p.setQuantity(p.getQuantity() - product.getQuantity());

            if (p.getQuantity() < 0) {
                DialogHelper.alert(this, "Sản phẩm " + p.getTitle() + " không còn đủ số lương trong kho!");
                orderDao.delete(order.getOrderId());
                return true;
            }
            productDao.update(p);
            orderDetalisDao.insert(orderDetalis);
        }

        clear(true);
        reload();
        return false;
    }

    private void delete() {
        try {
            String orderId = txtId.getText();

            List<Product> list = orderDetalisDao.findProductByOrderId(orderId);
            for (Product product : list) {
                Product p = productDao.findById(product.getProductId());
                p.setQuantity(p.getQuantity() + product.getQuantity());
                productDao.update(p);
            }

            if (orderDao.delete(orderId)) {
                DialogHelper.alert(this, "Xóa hóa đơn thành công!");

            } else {
                DialogHelper.alert(this, "Xóa hóa đơn thất bại!");
            }

            clear(true);
            reload();
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Xóa hóa đơn thất bại!");

        }
    }

    private void clear(boolean check) {
        txtId.setText("");
        txtId.setEnabled(true);
        txtTotal.setText("");
        txtDiscount.setText("");
        txtMemberDiscount.setText("");
        cbxCustomer.setSelectedIndex(0);
        cbxVoucher.setSelectedIndex(0);
        totalProcutCots = 0.0;
        totalPrice = 0.0;
        discount = 0.0;
        menberDiscount = 0.0;
        fillCbxCustomer();
        fillCbxVoucher();
        btnDelete.setEnabled(false);
//        btnDelete.setBackground(Color.gray);
        btnDelete.setForeground(Color.white);

        if (check) {
            listProductsOrder = new ArrayList<>();
            fillTableOrder();
            txtTotal.setText("");
            cbxWaittingBill.setSelectedIndex(0);
        }

    }

    private void selectCbxWaittingBill() {
        if (cbxWaittingBill.getSelectedItem() != null && cbxWaittingBill.getSelectedItem().equals("Chọn hóa đơn")) {
            clear(true);
            return;
        }

        Order order = (Order) cbxWaittingBill.getSelectedItem();

        if (order == null) {
            return;
        }

        listProductsOrder = null;

        List<Product> list = orderDetalisDao.findProductByOrderId(order.getOrderId());
        listProductsOrder = list;

        setModel(order);
        fillTableOrder();
    }

    private void reload() {
        fillCbxWaitingBill();
        fillTableOrder();
        listProducts = productDao.findAll();
        fillTableProducts();
        fillCbxCustomer();
        fillCbxVoucher();

        dateCreateDate.setDate(new Date());

        txtStaff.setText(ShareData.USER.getFullname());
    }

    private void selectCbxCustomer() {
        if (cbxCustomer.getSelectedItem() != null && cbxCustomer.getSelectedIndex() == 0) {
            if (totalProcutCots != null) {
                menberDiscount = 0.0;
                txtMemberDiscount.setText("-" + formatter.format(menberDiscount) + " VNĐ");
                totalPrice = totalProcutCots - discount - menberDiscount;
                txtTotal.setText(formatter.format(totalPrice) + " VNĐ");
            }
            return;
        }

        Customer c = (Customer) cbxCustomer.getSelectedItem();

        if (c == null) {
            return;
        }

        Customer customer = customerDao.findById(c.getCustomerId());

        if (customer.getPurchaseTimes() > 2) {
            System.out.println("5555");
            menberDiscount = totalProcutCots * 0.01;
            if (menberDiscount > 200000) {
                menberDiscount = 200000.0;
            }
        } else {
            menberDiscount = 0.0;
        }

        txtMemberDiscount.setText("-" + formatter.format(menberDiscount) + " VNĐ");

        totalPrice = totalProcutCots - discount - menberDiscount;
        txtTotal.setText(formatter.format(totalPrice) + " VNĐ");

    }

    private void selectCbxVoucher() {
        if (cbxVoucher.getSelectedItem() != null && cbxVoucher.getSelectedIndex() == 0) {
            if (totalProcutCots != null) {
                discount = 0.0;
                txtDiscount.setText("-" + formatter.format(discount) + " VNĐ");
                totalPrice = totalProcutCots - discount - menberDiscount;
                txtTotal.setText(formatter.format(totalPrice) + " VNĐ");
            }
            return;
        }

        Voucher voucher = (Voucher) cbxVoucher.getSelectedItem();

        if (voucher == null) {
            return;
        }

        if (totalProcutCots > voucher.getAmountApplied()) {
            if (voucher.getDiscountByPrice() == 0) {
                discount = totalProcutCots * (voucher.getDiscountByPercent() / 100);

                if (discount > voucher.getMaximunAmount()) {
                    discount = voucher.getMaximunAmount();
                }
            } else {
                discount = totalProcutCots - voucher.getDiscountByPrice();

                if (discount > voucher.getMaximunAmount()) {
                    discount = voucher.getMaximunAmount();
                }
            }

            txtDiscount.setText("-" + formatter.format(discount) + " VNĐ");
            totalPrice = totalProcutCots - discount - menberDiscount;
            txtTotal.setText(formatter.format(totalPrice) + " VNĐ");
        } else {
            DialogHelper.alert(this, "Đơn hàng chưa đạt tới gián trị áp dụng mã giảm giá này");
            cbxVoucher.setSelectedIndex(0);
        }
    }

    private boolean checkForm() {
        String orderId = txtId.getText();
        if (orderId.equals("")) {
            DialogHelper.alert(this, "Vui lòng nhập mã hóa đơn!");
            return true;
        }

        if (listProductsOrder.size() == 0) {
            DialogHelper.alert(this, "Vui lòng chọn sản phẩm hóa đơn đang trống!");
            return true;
        }

        if (cbxCustomer.getSelectedIndex() == 0) {
            DialogHelper.alert(this, "Vui lòng chọn khách hàng!");
            return true;
        }

        return false;
    }

    private void setSelectedValue(String value) {
        DefaultComboBoxModel cbxmodel = (DefaultComboBoxModel) cbxCustomer.getModel();

        for (int i = 1; i < cbxCustomer.getItemCount(); i++) {
            Object item = (Object) cbxmodel.getElementAt(i);
            String temp = String.valueOf(item);
            String text = temp.substring(0, temp.indexOf("-")).trim();
            System.out.println(item);

            if (text.equalsIgnoreCase(value)) {
                cbxCustomer.setSelectedIndex(i);
                return;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnWating = new javax.swing.JButton();
        btnPay = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        btnDeleteProducts = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblTotalPriceProducts = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtStaff = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtRefunds = new javax.swing.JTextField();
        txtMoneyGiven = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        cbxWaittingBill = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnSearch = new javax.swing.JButton();
        cbxVoucher = new javax.swing.JComboBox<>();
        cbxCustomer = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        txtMemberDiscount = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        btnRand = new javax.swing.JButton();
        btnReloadCbxVoucher = new javax.swing.JButton();
        btnReloadCbxCustomer = new javax.swing.JButton();
        dateCreateDate = new com.toedter.calendar.JDateChooser();

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("NGÀY TẠO");

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("TÌM KIẾM SẢN PHẨM");

        btnWating.setBackground(new java.awt.Color(0, 51, 153));
        btnWating.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnWating.setForeground(new java.awt.Color(255, 255, 255));
        btnWating.setText(" HÓA ĐƠN CHỜ");
        btnWating.setBorder(null);
        btnWating.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWatingActionPerformed(evt);
            }
        });

        btnPay.setBackground(new java.awt.Color(0, 51, 153));
        btnPay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPay.setForeground(new java.awt.Color(255, 255, 255));
        btnPay.setText("THANH TOÁN");
        btnPay.setBorder(null);
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("MÃ HD");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DANH SÁCH SẢN PHẨM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ẢNH", "MÃ SP", "TÊN SP", "GIÁ BÁN", "RAM - ROM -COLOR", "KHO"
            }
        ) {
            Class[] types = new Class [] {
                javax.swing.ImageIcon.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProducts);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HÓA ĐƠN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N

        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ẢNH", "MÃ SP", "TÊN SP", "GIÁ", "RAM - ROM - COLOR", "SỐ LƯỢNG", "TỔNG TIỀN"
            }
        ) {
            Class[] types = new Class [] {
                javax.swing.ImageIcon.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class

            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdersMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblOrdersMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblOrders);

        btnDeleteProducts.setBackground(new java.awt.Color(0, 51, 153));
        btnDeleteProducts.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDeleteProducts.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteProducts.setText("XÓA SẢN PHẨM");
        btnDeleteProducts.setBorder(null);
        btnDeleteProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductsActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("TỔNG TIỂN SẢN PHẨM");

        lblTotalPriceProducts.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotalPriceProducts.setForeground(new java.awt.Color(255, 0, 0));
        lblTotalPriceProducts.setText("0 VNĐ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotalPriceProducts)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeleteProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(lblTotalPriceProducts))
                .addContainerGap())
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 153));
        jLabel9.setText("THÔNG TIN HÓA ĐƠN");

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("NHÂN VIÊN");

        txtStaff.setEnabled(false);
        txtStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStaffActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setText("TIỀN KHÁCH ĐƯA");

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setText("TIỀN THỐI");

        txtRefunds.setBackground(new java.awt.Color(240, 240, 240));
        txtRefunds.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRefunds.setForeground(new java.awt.Color(255, 102, 102));
        txtRefunds.setBorder(null);
        txtRefunds.setEnabled(false);

        txtMoneyGiven.setBackground(new java.awt.Color(240, 240, 240));
        txtMoneyGiven.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMoneyGiven.setBorder(null);
        txtMoneyGiven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMoneyGivenActionPerformed(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(0, 51, 153));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jSeparator3.setBackground(new java.awt.Color(0, 51, 153));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 153));
        jLabel11.setText("DANH SÁCH HÓA ĐƠN CHỜ");

        cbxWaittingBill.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        cbxWaittingBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxWaittingBillMouseClicked(evt);
            }
        });
        cbxWaittingBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxWaittingBillActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 152));
        jLabel12.setText("MÃ GIẢM GIÁ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 153));
        jLabel13.setText("KHÁCH HÀNG");

        btnDelete.setBackground(new java.awt.Color(0, 51, 153));
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("HỦY");
        btnDelete.setBorder(null);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 51, 153));
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("MỚI");
        btnClear.setBorder(null);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnReload.setBackground(new java.awt.Color(0, 51, 153));
        btnReload.setForeground(new java.awt.Color(0, 51, 153));
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Refresh_30px.png"))); // NOI18N
        btnReload.setBorder(null);
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        txtSearch.setBackground(new java.awt.Color(240, 240, 240));
        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSearch.setBorder(null);
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 51, 153));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153), 2));

        btnSearch.setBackground(new java.awt.Color(0, 51, 153));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Search_32px.png"))); // NOI18N
        btnSearch.setBorder(null);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cbxVoucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxVoucherActionPerformed(evt);
            }
        });

        cbxCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCustomerActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("GIẢM GIÁ");

        txtDiscount.setBackground(new java.awt.Color(240, 240, 240));
        txtDiscount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiscount.setForeground(new java.awt.Color(255, 153, 153));
        txtDiscount.setBorder(null);
        txtDiscount.setEnabled(false);

        jSeparator5.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator5.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        jLabel15.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("GIẢM THÀNH VIÊN");

        txtMemberDiscount.setBackground(new java.awt.Color(240, 240, 240));
        txtMemberDiscount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMemberDiscount.setForeground(new java.awt.Color(255, 153, 153));
        txtMemberDiscount.setBorder(null);
        txtMemberDiscount.setEnabled(false);

        jSeparator6.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator6.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        jLabel10.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("TỔNG TIỀN");

        txtTotal.setBackground(new java.awt.Color(240, 240, 240));
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(255, 153, 153));
        txtTotal.setBorder(null);
        txtTotal.setEnabled(false);

        jSeparator4.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator4.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        btnRand.setBackground(new java.awt.Color(0, 51, 153));
        btnRand.setForeground(new java.awt.Color(0, 51, 153));
        btnRand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Plus_32px.png"))); // NOI18N
        btnRand.setBorder(null);
        btnRand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandActionPerformed(evt);
            }
        });

        btnReloadCbxVoucher.setBackground(new java.awt.Color(0, 51, 153));
        btnReloadCbxVoucher.setForeground(new java.awt.Color(0, 51, 153));
        btnReloadCbxVoucher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Refresh_30px.png"))); // NOI18N
        btnReloadCbxVoucher.setBorder(null);
        btnReloadCbxVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadCbxVoucherActionPerformed(evt);
            }
        });

        btnReloadCbxCustomer.setBackground(new java.awt.Color(0, 51, 153));
        btnReloadCbxCustomer.setForeground(new java.awt.Color(0, 51, 153));
        btnReloadCbxCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Refresh_30px.png"))); // NOI18N
        btnReloadCbxCustomer.setBorder(null);
        btnReloadCbxCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadCbxCustomerActionPerformed(evt);
            }
        });

        dateCreateDate.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator1)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxWaittingBill, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(112, 112, 112))
                                        .addComponent(dateCreateDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(85, 85, 85)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMoneyGiven, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDiscount)
                                        .addComponent(jSeparator5))
                                    .addGap(36, 36, 36)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtMemberDiscount)
                                        .addComponent(jSeparator6)
                                        .addComponent(txtRefunds)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jSeparator3)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(22, 22, 22)
                                    .addComponent(btnWating, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btnPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTotal)
                                .addComponent(jSeparator4))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbxVoucher, javax.swing.GroupLayout.Alignment.LEADING, 0, 352, Short.MAX_VALUE)
                                    .addComponent(cbxCustomer, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnReloadCbxVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnReloadCbxCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRand))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReloadCbxCustomer))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSearch)
                                .addComponent(cbxWaittingBill, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnReloadCbxVoucher)
                            .addComponent(cbxVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtMoneyGiven, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(txtRefunds, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMemberDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnWating, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStaffActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStaffActionPerformed

    private void tblProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductsMouseClicked
        index = tblProducts.getSelectedRow();
        addProduct();
    }//GEN-LAST:event_tblProductsMouseClicked

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        if (checkForm()) {
            return;
        }

        pay();
    }//GEN-LAST:event_btnPayActionPerformed

    private void btnWatingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWatingActionPerformed
        if (checkForm()) {
            return;
        }
        waittingBill();
    }//GEN-LAST:event_btnWatingActionPerformed

    private void cbxWaittingBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxWaittingBillActionPerformed
        btnDelete.setEnabled(true);
        btnDelete.setBackground(new Color(0, 51, 153));
        btnDelete.setForeground(Color.white);
        selectCbxWaittingBill();
    }//GEN-LAST:event_cbxWaittingBillActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear(true);
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        reload();
    }//GEN-LAST:event_btnReloadActionPerformed

    private void tblOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdersMouseClicked

    }//GEN-LAST:event_tblOrdersMouseClicked

    private void cbxWaittingBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxWaittingBillMouseClicked

//        selectCbxWaittingBill();

    }//GEN-LAST:event_cbxWaittingBillMouseClicked

    private void tblOrdersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdersMousePressed
        if (evt.getClickCount() == 2) {
            String productId = (String) tblOrders.getValueAt(tblOrders.getSelectedRow(), 1);
            new ProductDetails(null, true, productId).setVisible(true);
        }
    }//GEN-LAST:event_tblOrdersMousePressed

    private void btnDeleteProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductsActionPerformed
        String productID = (String) tblOrders.getValueAt(tblOrders.getSelectedRow(), 1);

        for (Product product : listProductsOrder) {
            if (product.getProductId().equals(productID)) {
                listProductsOrder.remove(product);
                fillTableOrder();
                return;
            }
        }


    }//GEN-LAST:event_btnDeleteProductsActionPerformed

    private void txtMoneyGivenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMoneyGivenActionPerformed
        try {
            Double money = Double.parseDouble(txtMoneyGiven.getText());
            Double result = money - (totalProcutCots - discount - menberDiscount);

            if (result < 0) {
                DialogHelper.alert(this, "Khách trả chưa đủ tiền");

            }

            txtRefunds.setText(formatter.format(result) + " VNĐ");

        } catch (Exception e) {
            DialogHelper.alert(this, "Vui lòng nhập đúng định dạng!");
        }
    }//GEN-LAST:event_txtMoneyGivenActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        btnSearchActionPerformed(evt);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String keyword = txtSearch.getText();
        listProducts = productDao.findByKeyword(keyword);
        fillTableProducts();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnRandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandActionPerformed
        do {
            int rand = (int) (Math.random() * 999999);
            txtId.setText("HD" + rand);
            txtStaff.setText(ShareData.USER.getFullname());
        } while (orderDao.findById(txtId.getText()) != null);
    }//GEN-LAST:event_btnRandActionPerformed

    private void cbxCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCustomerActionPerformed
        selectCbxCustomer();
    }//GEN-LAST:event_cbxCustomerActionPerformed

    private void cbxVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxVoucherActionPerformed
        selectCbxVoucher();

    }//GEN-LAST:event_cbxVoucherActionPerformed

    private void btnReloadCbxVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadCbxVoucherActionPerformed
        fillCbxVoucher();
    }//GEN-LAST:event_btnReloadCbxVoucherActionPerformed

    private void btnReloadCbxCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadCbxCustomerActionPerformed
        fillCbxCustomer();
    }//GEN-LAST:event_btnReloadCbxCustomerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteProducts;
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnRand;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnReloadCbxCustomer;
    private javax.swing.JButton btnReloadCbxVoucher;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnWating;
    private javax.swing.JComboBox<String> cbxCustomer;
    private javax.swing.JComboBox<String> cbxVoucher;
    private javax.swing.JComboBox<String> cbxWaittingBill;
    private com.toedter.calendar.JDateChooser dateCreateDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lblTotalPriceProducts;
    private javax.swing.JTable tblOrders;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMemberDiscount;
    private javax.swing.JTextField txtMoneyGiven;
    private javax.swing.JTextField txtRefunds;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStaff;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
