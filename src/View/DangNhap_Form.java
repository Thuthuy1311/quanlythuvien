/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DAO.KetNoiSQL;
import Model.TaiKhoan;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author KHP2T
 */
public class DangNhap_Form extends javax.swing.JFrame {

    public DangNhap_Form() {
        initComponents();
        
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_tenDangNhap = new javax.swing.JTextField();
        btn_DangNhap = new javax.swing.JButton();
        btn_Thoat = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txt_chucVu = new javax.swing.JComboBox<>();
        txt_matKhau = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\open-book.png")); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("ĐĂNG NHẬP");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Tên đăng nhập:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Mật khẩu:");

        txt_tenDangNhap.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        btn_DangNhap.setBackground(new java.awt.Color(255, 255, 255));
        btn_DangNhap.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_DangNhap.setText("Đăng nhập");
        btn_DangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DangNhapActionPerformed(evt);
            }
        });

        btn_Thoat.setBackground(new java.awt.Color(255, 255, 255));
        btn_Thoat.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_Thoat.setText("Thoát");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Chức vụ:");

        txt_chucVu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_chucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Độc giả", "Thủ thư" }));
        txt_chucVu.setSelectedIndex(-1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(28, 28, 28)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_tenDangNhap, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(73, 73, 73))
                    .addComponent(txt_matKhau)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_chucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(72, 72, 72))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(btn_DangNhap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Thoat)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel1)
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_chucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_tenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txt_matKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_DangNhap)
                    .addComponent(btn_Thoat))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btn_DangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DangNhapActionPerformed
        if(txt_tenDangNhap.getText().equals("") || txt_matKhau.getPassword().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa nhập đủ thông tin!");
        }
        else{
            if(txt_chucVu.getSelectedItem().equals("Độc giả")){
                TaiKhoan taikhoan = new TaiKhoan();
                taikhoan.setTenDangNhap(txt_tenDangNhap.getText());
                taikhoan.setMatKhau(String.valueOf(txt_matKhau.getPassword()));
                try {
                    if(ktraDocGia(taikhoan)){
                        JOptionPane.showMessageDialog(rootPane, "Bạn đã đăng nhập thành công!");
                        new TrangChuThuThu().setVisible(true);
                        this.setVisible(false);
                        
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Đăng nhập thất bại! Vui lòng kiểm tra lại!");
                        txt_matKhau.setText("");
                        txt_tenDangNhap.setText("");
                        txt_tenDangNhap.requestFocus();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DangNhap_Form.class.getName()).log(Level.SEVERE, null, ex);
                }
           }else{
                TaiKhoan taikhoan = new TaiKhoan();
                taikhoan.setTenDangNhap(txt_tenDangNhap.getText());
                taikhoan.setMatKhau(String.valueOf(txt_matKhau.getPassword()));
                try {
                    if(ktraThuThu(taikhoan)){
                        JOptionPane.showMessageDialog(rootPane, "Bạn đã đăng nhập thành công!");
                        new TrangChuThuThu().setVisible(true);
                        this.setVisible(false);
                        
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Đăng nhập thất bại! Vui lòng kiểm tra lại!");
                        txt_tenDangNhap.requestFocus();
                        txt_matKhau.setText("");
                        txt_tenDangNhap.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DangNhap_Form.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btn_DangNhapActionPerformed

    public boolean ktraDocGia(TaiKhoan taikhoan) throws SQLException{
        List<TaiKhoan> docGia = new ArrayList<TaiKhoan>();
        Connection conn = KetNoiSQL.getConnection();
        String sql = "SELECT maTaiKhoan, matKhau FROM TaiKhoan";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                TaiKhoan dg = new TaiKhoan();
                
                dg.setTenDangNhap(rs.getString("maTaiKhoan"));
                dg.setMatKhau(rs.getString("matKhau"));
                docGia.add(dg);
            }
        }
        catch(SQLException e){
            
        }
         for(TaiKhoan dg : docGia){
             if(dg.getTenDangNhap().equals(taikhoan.getTenDangNhap()) && dg.getMatKhau().equals(taikhoan.getMatKhau())){
                 return true;
             }
         }
         return false;
    }
    
        public boolean ktraThuThu(TaiKhoan taikhoan) throws SQLException{
        List<TaiKhoan> thuthu = new ArrayList<TaiKhoan>();
        Connection conn = KetNoiSQL.getConnection();
        String sql = "SELECT maCanBo, matKhau FROM CanBo";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                TaiKhoan tt = new TaiKhoan();
                
                tt.setTenDangNhap(rs.getString("maCanBo"));
                tt.setMatKhau(rs.getString("matKhau"));
                thuthu.add(tt);
            }
        }
        catch(SQLException e){
            
        }
         for(TaiKhoan tt : thuthu){
             if(tt.getTenDangNhap().equals(taikhoan.getTenDangNhap()) && tt.getMatKhau().equals(taikhoan.getMatKhau())){
                 return true;
             }
         }
         return false;
    }
    
        
        
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DangNhap_Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_DangNhap;
    private javax.swing.JButton btn_Thoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> txt_chucVu;
    private javax.swing.JPasswordField txt_matKhau;
    private javax.swing.JTextField txt_tenDangNhap;
    // End of variables declaration//GEN-END:variables
}
