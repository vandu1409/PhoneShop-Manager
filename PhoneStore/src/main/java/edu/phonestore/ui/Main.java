/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.phonestore.ui;

import edu.phonestore.helper.DialogHelper;
import edu.phonestore.helper.MenuHelper;
import edu.phonestore.helper.ShareData;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Văn Dự
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    //264, 632
    int width = 264;
    SellPanel sellPanel = new SellPanel();
    ProductPanel productPanel = new ProductPanel();
    StaffPanel staffPanel = new StaffPanel();
    OrderPanel orderPanel = new OrderPanel();
    StatisticsPanel statisticsPanel = new StatisticsPanel();
    CustomerPanel customerPanel = new CustomerPanel();
    VoucherPanel voucherPanel = new VoucherPanel();
    HomePanel home = new HomePanel();
    SalaryPanel salaryPanel = new SalaryPanel();

    public Main() {
        initComponents();
        initMenu();
        init();
        new LoginDialog(null, true).setVisible(true);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        menuClick(home);
        pnlBrand.setVisible(false);
        pnlChangePass.setVisible(false);
        pnlUserInfo.setVisible(false);

    }

    private void init() {
        pnlMain.add(sellPanel);
        pnlMain.add(productPanel);
        pnlMain.add(staffPanel);
        pnlMain.add(orderPanel);
        pnlMain.add(statisticsPanel);
        pnlMain.add(customerPanel);
        pnlMain.add(voucherPanel);
        pnlMain.add(salaryPanel);
        pnlMain.add(home);
    }

    private void initMenu() {
        pnlHome.addMouseListener(new MenuHelper(pnlHome) {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuClick(home);
            }

        });
        pnlSell.addMouseListener(new MenuHelper(pnlSell) {
            @Override
            public void mouseClicked(MouseEvent e) {

                menuClick(sellPanel);
            }

        });
        pnlProduct.addMouseListener(new MenuHelper(pnlProduct) {
            @Override
            public void mouseClicked(MouseEvent e) {

                menuClick(productPanel);
            }

        });
        pnlOrder.addMouseListener(new MenuHelper(pnlOrder) {
            @Override
            public void mouseClicked(MouseEvent e) {

                menuClick(orderPanel);
            }

        });
        pnlStaff.addMouseListener(new MenuHelper(pnlStaff) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!ShareData.USER.isRole()) {
                    DialogHelper.alert(null, "Vui lòng đăng nhập với vai trò Admin !!!");
                    return;
                }

                menuClick(staffPanel);
            }

        });
        pnlSalary.addMouseListener(new MenuHelper(pnlSalary) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!ShareData.USER.isRole()) {
                    DialogHelper.alert(null, "Vui lòng đăng nhập với vai trò Admin !!!");
                    return;
                }
                menuClick(salaryPanel);
            }

        });

        pnlVoucher.addMouseListener(new MenuHelper(pnlVoucher) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!ShareData.USER.isRole()) {
                    DialogHelper.alert(null, "Vui lòng đăng nhập với vai trò Admin !!!");
                    return;
                }

                menuClick(voucherPanel);
            }

        });

        pnlCustomer.addMouseListener(new MenuHelper(pnlCustomer) {
            @Override
            public void mouseClicked(MouseEvent e) {

                menuClick(customerPanel);
            }

        });

        pnlStatictis.addMouseListener(new MenuHelper(pnlStatictis) {
            @Override
            public void mouseClicked(MouseEvent e) {

                menuClick(statisticsPanel);
            }

        });
        pnlBrand.addMouseListener(new MenuHelper(pnlBrand) {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!ShareData.USER.isRole()) {
                    DialogHelper.alert(null, "Vui lòng đăng nhập với vai trò Admin !!!");
                    return;
                }
                new BrandDialog(null, true).setVisible(true);
            }

        });
        pnlChangePass.addMouseListener(new MenuHelper(pnlChangePass) {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChangePassword(null, true).setVisible(true);
            }

        });

        pnlUserInfo.addMouseListener(new MenuHelper(pnlUserInfo) {
            @Override
            public void mouseClicked(MouseEvent e) {
                new UserDetalis(null, true).setVisible(true);
            }

        });

    }

    private void menuClick(JPanel panel) {
        sellPanel.setVisible(false);
        productPanel.setVisible(false);
        staffPanel.setVisible(false);
        orderPanel.setVisible(false);
        statisticsPanel.setVisible(false);
        customerPanel.setVisible(false);
        voucherPanel.setVisible(false);
        salaryPanel.setVisible(false);
        home.setVisible(false);

        panel.setVisible(true);

    }

    private void openMenu(int time) {
        System.out.println("88888");
        pnlBrand.setVisible(false);
        pnlChangePass.setVisible(false);
        pnlUserInfo.setVisible(false);

        Thread t = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < width; i++) {
                    pnlMenu.setSize(i, 1000);

                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException ex) {

                    }
                }
            }

        };
        t.start();

    }

    
    
    private void closeMenu() {

        pnlBrand.setVisible(true);
        pnlChangePass.setVisible(true);
        pnlUserInfo.setVisible(true);

        Thread t = new Thread() {
            @Override
            public void run() {
                for (int i = width; i > 0; i--) {
                    pnlMenu.setSize(i, 1000);

                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException ex) {

                    }
                }
            }

        };
        t.start();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lblLogout = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();
        pnlMenu2 = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        pnlHome = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnlSell = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pnlProduct = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlStaff = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        pnlSalary = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        pnlOrder = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlVoucher = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        pnlCustomer = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlStatictis = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        lblmenu = new javax.swing.JLabel();
        pnlBrand = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pnlChangePass = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        pnlTemp = new javax.swing.JPanel();
        pnlUserInfo = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        buttonCircle1 = new edu.phonestore.swing.ButtonCircle();
        buttonCircle2 = new edu.phonestore.swing.ButtonCircle();
        buttonCircle3 = new edu.phonestore.swing.ButtonCircle();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        pnlHeader.setBackground(new java.awt.Color(0, 51, 153));

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        lblLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logout_32px.png"))); // NOI18N
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addComponent(lblLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 6, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlMenu2.setBackground(new java.awt.Color(0, 51, 153));
        pnlMenu2.setSize(260, 1000);
        pnlMenu2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlMenu.setBackground(new java.awt.Color(0, 51, 153));

        pnlHome.setBackground(new java.awt.Color(0, 51, 153));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_Home32_32.png"))); // NOI18N
        jLabel2.setText("TRANG CHỦ    ");

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHomeLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pnlSell.setBackground(new java.awt.Color(0, 51, 153));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_add_shopping_cart_32.png"))); // NOI18N
        jLabel4.setText("BÁN HÀNG      ");

        javax.swing.GroupLayout pnlSellLayout = new javax.swing.GroupLayout(pnlSell);
        pnlSell.setLayout(pnlSellLayout);
        pnlSellLayout.setHorizontalGroup(
            pnlSellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSellLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlSellLayout.setVerticalGroup(
            pnlSellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSellLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pnlProduct.setBackground(new java.awt.Color(0, 51, 153));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_Phonelink_Ring_32.png"))); // NOI18N
        jLabel5.setText("SẢN PHẨM    ");

        javax.swing.GroupLayout pnlProductLayout = new javax.swing.GroupLayout(pnlProduct);
        pnlProduct.setLayout(pnlProductLayout);
        pnlProductLayout.setHorizontalGroup(
            pnlProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlProductLayout.setVerticalGroup(
            pnlProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProductLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pnlStaff.setBackground(new java.awt.Color(0, 51, 153));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_staff32_32.png"))); // NOI18N
        jLabel8.setText("NHÂN VIÊN  ");

        javax.swing.GroupLayout pnlStaffLayout = new javax.swing.GroupLayout(pnlStaff);
        pnlStaff.setLayout(pnlStaffLayout);
        pnlStaffLayout.setHorizontalGroup(
            pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlStaffLayout.setVerticalGroup(
            pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStaffLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pnlSalary.setBackground(new java.awt.Color(0, 51, 153));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Stack of Money_32px.png"))); // NOI18N
        jLabel7.setText("TIỀN LƯƠNG");

        javax.swing.GroupLayout pnlSalaryLayout = new javax.swing.GroupLayout(pnlSalary);
        pnlSalary.setLayout(pnlSalaryLayout);
        pnlSalaryLayout.setHorizontalGroup(
            pnlSalaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSalaryLayout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlSalaryLayout.setVerticalGroup(
            pnlSalaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSalaryLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pnlOrder.setBackground(new java.awt.Color(0, 51, 153));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_Purchase_Order_32.png"))); // NOI18N
        jLabel9.setText("HÓA ĐƠN       ");

        javax.swing.GroupLayout pnlOrderLayout = new javax.swing.GroupLayout(pnlOrder);
        pnlOrder.setLayout(pnlOrderLayout);
        pnlOrderLayout.setHorizontalGroup(
            pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlOrderLayout.setVerticalGroup(
            pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOrderLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("VAN DU STORE");

        pnlVoucher.setBackground(new java.awt.Color(0, 51, 153));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/voucher_32px.png"))); // NOI18N
        jLabel11.setText("GIẢM GIÁ     ");

        javax.swing.GroupLayout pnlVoucherLayout = new javax.swing.GroupLayout(pnlVoucher);
        pnlVoucher.setLayout(pnlVoucherLayout);
        pnlVoucherLayout.setHorizontalGroup(
            pnlVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlVoucherLayout.setVerticalGroup(
            pnlVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVoucherLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pnlCustomer.setBackground(new java.awt.Color(0, 51, 153));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/management_32px.png"))); // NOI18N
        jLabel15.setText("KHÁCH HÀNG");

        javax.swing.GroupLayout pnlCustomerLayout = new javax.swing.GroupLayout(pnlCustomer);
        pnlCustomer.setLayout(pnlCustomerLayout);
        pnlCustomerLayout.setHorizontalGroup(
            pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlCustomerLayout.setVerticalGroup(
            pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCustomerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartphones_127px.png"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close_32px.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        pnlStatictis.setBackground(new java.awt.Color(0, 51, 153));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_statistics_32.png"))); // NOI18N
        jLabel16.setText("THỐNG KÊ     ");

        javax.swing.GroupLayout pnlStatictisLayout = new javax.swing.GroupLayout(pnlStatictis);
        pnlStatictis.setLayout(pnlStatictisLayout);
        pnlStatictisLayout.setHorizontalGroup(
            pnlStatictisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlStatictisLayout.setVerticalGroup(
            pnlStatictisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStatictisLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlSell, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlStatictis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlSalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(28, 28, 28)
                .addComponent(pnlHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlSell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlStatictis, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(202, Short.MAX_VALUE))
        );

        pnlMenu2.add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1, 264, 870));

        lblmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu_32px.png"))); // NOI18N
        lblmenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblmenuMouseClicked(evt);
            }
        });
        pnlMenu2.add(lblmenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 36, -1));

        pnlBrand.setBackground(new java.awt.Color(0, 51, 153));
        pnlBrand.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBrandMouseEntered(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8_xbox_cross_32.png"))); // NOI18N
        jLabel6.setText("THÊM THƯƠNG HIỆU");

        javax.swing.GroupLayout pnlBrandLayout = new javax.swing.GroupLayout(pnlBrand);
        pnlBrand.setLayout(pnlBrandLayout);
        pnlBrandLayout.setHorizontalGroup(
            pnlBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBrandLayout.setVerticalGroup(
            pnlBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBrandLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMenu2.add(pnlBrand, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 52, 260, -1));

        pnlChangePass.setBackground(new java.awt.Color(0, 51, 153));
        pnlChangePass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlChangePassMouseEntered(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/replace_32px.png"))); // NOI18N
        jLabel13.setText("ĐỔI MẬT KHẨU          ");

        javax.swing.GroupLayout pnlChangePassLayout = new javax.swing.GroupLayout(pnlChangePass);
        pnlChangePass.setLayout(pnlChangePassLayout);
        pnlChangePassLayout.setHorizontalGroup(
            pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlChangePassLayout.setVerticalGroup(
            pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChangePassLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMenu2.add(pnlChangePass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 104, 260, -1));

        pnlTemp.setBackground(new java.awt.Color(0, 51, 153));

        javax.swing.GroupLayout pnlTempLayout = new javax.swing.GroupLayout(pnlTemp);
        pnlTemp.setLayout(pnlTempLayout);
        pnlTempLayout.setHorizontalGroup(
            pnlTempLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        pnlTempLayout.setVerticalGroup(
            pnlTempLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );

        pnlMenu2.add(pnlTemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 512, -1, -1));

        pnlUserInfo.setBackground(new java.awt.Color(0, 51, 153));
        pnlUserInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlUserInfoMouseEntered(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/user_shield_32px.png"))); // NOI18N
        jLabel14.setText("TÀI KHOẢN               ");

        javax.swing.GroupLayout pnlUserInfoLayout = new javax.swing.GroupLayout(pnlUserInfo);
        pnlUserInfo.setLayout(pnlUserInfoLayout);
        pnlUserInfoLayout.setHorizontalGroup(
            pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlUserInfoLayout.setVerticalGroup(
            pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMenu2.add(pnlUserInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 156, 260, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        buttonCircle1.setBackground(new java.awt.Color(0, 204, 0));
        buttonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCircle1ActionPerformed(evt);
            }
        });

        buttonCircle2.setBackground(new java.awt.Color(255, 0, 0));
        buttonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCircle2ActionPerformed(evt);
            }
        });

        buttonCircle3.setBackground(new java.awt.Color(255, 255, 0));
        buttonCircle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCircle3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCircle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(buttonCircle3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(buttonCircle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonCircle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCircle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCircle3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(pnlMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblmenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblmenuMouseClicked
        openMenu(2);
    }//GEN-LAST:event_lblmenuMouseClicked

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked
        new LoginDialog(this, true).setVisible(true);
        menuClick(home);
      
    }//GEN-LAST:event_lblLogoutMouseClicked

    private void buttonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCircle2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_buttonCircle2ActionPerformed

    private void buttonCircle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCircle3ActionPerformed
        if (this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {

            this.setExtendedState(JFrame.NORMAL);
        } else {

            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }//GEN-LAST:event_buttonCircle3ActionPerformed

    private void buttonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCircle1ActionPerformed

        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_buttonCircle1ActionPerformed

    private void pnlBrandMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBrandMouseEntered
        if (pnlMenu.getWidth() == 264) {
            pnlBrand.setVisible(false);
        }
    }//GEN-LAST:event_pnlBrandMouseEntered

    private void pnlChangePassMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlChangePassMouseEntered
        if (pnlMenu.getWidth() == 264) {
            pnlChangePass.setVisible(false);
        }
    }//GEN-LAST:event_pnlChangePassMouseEntered

    private void pnlUserInfoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlUserInfoMouseEntered
        if (pnlMenu.getWidth() == 264) {
            pnlUserInfo.setVisible(false);
        }
    }//GEN-LAST:event_pnlUserInfoMouseEntered

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        closeMenu();
    }//GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private edu.phonestore.swing.ButtonCircle buttonCircle1;
    private edu.phonestore.swing.ButtonCircle buttonCircle2;
    private edu.phonestore.swing.ButtonCircle buttonCircle3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblmenu;
    private javax.swing.JPanel pnlBrand;
    private javax.swing.JPanel pnlChangePass;
    private javax.swing.JPanel pnlCustomer;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlMenu2;
    private javax.swing.JPanel pnlOrder;
    private javax.swing.JPanel pnlProduct;
    private javax.swing.JPanel pnlSalary;
    private javax.swing.JPanel pnlSell;
    private javax.swing.JPanel pnlStaff;
    private javax.swing.JPanel pnlStatictis;
    private javax.swing.JPanel pnlTemp;
    private javax.swing.JPanel pnlUserInfo;
    private javax.swing.JPanel pnlVoucher;
    // End of variables declaration//GEN-END:variables
}
