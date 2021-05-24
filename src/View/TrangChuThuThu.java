
package View;

import Service.DanhMucSach_Service;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import DAO.KetNoiSQL;
import Model.DanhMucSach;
import Model.Sach;
import Service.DanhMucSach_Service;
import Service.Sach_Service;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author KHP2T
 */
public class TrangChuThuThu extends javax.swing.JFrame {

    DefaultTableModel defaultTableModel_DM;
    DanhMucSach_Service dms_Service;
    DefaultTableModel defaultTableModel_S;
    Sach_Service s_Service;
    
    public Connection conn = KetNoiSQL.getConnection();
    
    public TrangChuThuThu() {
        initComponents();
        disable_DM();

        //Danh mục sách
        dms_Service = new DanhMucSach_Service();
        defaultTableModel_DM = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tbl_DMSach.setModel(defaultTableModel_DM);
        defaultTableModel_DM.addColumn("Mã danh mục");
        defaultTableModel_DM.addColumn("Tên danh mục");
        
        setTableData_DM(dms_Service.getDSDanhMucSach());
        
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tbl_DMSach.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(0);
        tbl_DMSach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tbl_DMSach.setRowHeight(30);
        
        TableColumnModel columnModel = tbl_DMSach.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(170);
        columnModel.getColumn(1).setPreferredWidth(300);
        
        //Sách
        loadmaDanhMuc();
        loadmaTheLoai();
        disable_S();
        refresh();
        
        
        s_Service = new Sach_Service();
        defaultTableModel_S = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tbl_Sach.setModel(defaultTableModel_S);
        defaultTableModel_S.addColumn("Mã sách");
        defaultTableModel_S.addColumn("Tên sách");
        defaultTableModel_S.addColumn("Mã DM");
        defaultTableModel_S.addColumn("Mã TL");
        defaultTableModel_S.addColumn("Tác giả");
        defaultTableModel_S.addColumn("Nhà XB");
        defaultTableModel_S.addColumn("Năm XB");
        defaultTableModel_S.addColumn("SL");
        defaultTableModel_S.addColumn("Tóm tắt");
        
        setTableData_S(s_Service.getDSSach());
        
        DefaultTableCellRenderer renderers = (DefaultTableCellRenderer) tbl_Sach.getTableHeader().getDefaultRenderer();
        renderers.setHorizontalAlignment(0);
        tbl_Sach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tbl_Sach.setRowHeight(30);
        
        TableColumnModel columnModel1 = tbl_Sach.getColumnModel();
        columnModel1.getColumn(0).setPreferredWidth(150);
        columnModel1.getColumn(1).setPreferredWidth(350);
        columnModel1.getColumn(2).setPreferredWidth(170);
        columnModel1.getColumn(3).setPreferredWidth(150);
        columnModel1.getColumn(4).setPreferredWidth(170);
        columnModel1.getColumn(5).setPreferredWidth(250);
        columnModel1.getColumn(6).setPreferredWidth(120);
        columnModel1.getColumn(7).setPreferredWidth(50);
        columnModel1.getColumn(8).setPreferredWidth(120);
        
    }
    
    private void setTableData_DM(List<DanhMucSach> dms){
        for(DanhMucSach dm : dms){
            defaultTableModel_DM.addRow(new Object[]{dm.getMaDM(), dm.getTenDM()});
        }
    }
    
    private void setTableData_S(List<Sach> ss){
        for(Sach s : ss){
            defaultTableModel_S.addRow(new Object[]{s.getMaSach(), s.getTenSach(), s.getMaDMSach(), s.getMaTheLoai(), s.getTacGia(), s.getNXB(), s.getNamXuatBan(), s.getSoLuongCon(), s.getTomTatND()});
        }
    }
    
    public void enable_DM(){
        txt_maDMSach.setEnabled(true);
        txt_tenDMSach.setEnabled(true);
    }
    public void disable_DM(){
        txt_maDMSach.setEnabled(false);
        txt_tenDMSach.setEnabled(false);
    }
    
    public void enable_S(){
        txt_tenSach.setEnabled(true);
        cbb_maTheLoai.setEnabled(true);
        txt_tenTheLoai.setEnabled(true);
        txt_soLuongCon.setEnabled(true);
        txt_namXuatBan.setEnabled(true);
        txt_tacGia.setEnabled(true);
        txt_nhaXuatBan.setEnabled(true);
        cbb_maDM.setEnabled(true);
        txt_tenDM.setEnabled(true);
        txt_tomTatND.setEnabled(true);
    }
    
    public void disable_S(){
        txt_tenSach.setEnabled(false);
        cbb_maTheLoai.setEnabled(false);
        txt_tenTheLoai.setEnabled(false);
        txt_soLuongCon.setEnabled(false);
        txt_namXuatBan.setEnabled(false);
        txt_tacGia.setEnabled(false);
        txt_nhaXuatBan.setEnabled(false);
        cbb_maDM.setEnabled(false);
        txt_tenDM.setEnabled(false);
        txt_tomTatND.setEnabled(false);
    }
    
    public void refresh(){
        txt_maSach.setText("");
        txt_tenSach.setText("");
        cbb_maTheLoai.setSelectedIndex(-1);
        txt_tenTheLoai.setText("");
        txt_soLuongCon.setText("");
        txt_namXuatBan.setText("");
        txt_tacGia.setText("");
        txt_nhaXuatBan.setText("");
        cbb_maDM.setSelectedIndex(-1);
        txt_tenDM.setText("");
        txt_tomTatND.setText("");
        
        btn_themSach.setEnabled(true);
        btn_luuSach.setEnabled(false);
        btn_suaSach.setEnabled(false);
        btn_xoaSach.setEnabled(false);
    }
    
    public void loadmaTheLoai(){
        cbb_maTheLoai.removeAllItems();
        String sql = "SELECT maTheLoai FROM TheLoai";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                this.cbb_maTheLoai.addItem(rs.getString("maTheLoai"));
            }
        }catch(Exception e){

        }
    }
    
    public void loadmaDanhMuc(){
        cbb_maDM.removeAllItems();
        String sql = "SELECT maDMSach FROM DanhMucSach";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                this.cbb_maDM.addItem(rs.getString("maDMSach"));
            }
        }catch(Exception e){

        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txt_maSach = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_tenSach = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txt_soLuongCon = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_tacGia = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_nhaXuatBan = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txt_namXuatBan = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txt_tomTatND = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txt_tenDM = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txt_tenTheLoai = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_Sach = new javax.swing.JTable();
        btn_themSach = new javax.swing.JButton();
        btn_suaSach = new javax.swing.JButton();
        btn_xoaSach = new javax.swing.JButton();
        btn_luuSach = new javax.swing.JButton();
        btn_lamMoiSach = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        cbb_maTheLoai = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        cbb_maDM = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_DMSach = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_maDMSach = new javax.swing.JTextField();
        txt_tenDMSach = new javax.swing.JTextField();
        btn_ThemDMSach = new javax.swing.JButton();
        btn_LuuDMSach = new javax.swing.JButton();
        btn_SuaDMSach = new javax.swing.JButton();
        txt_timkiemDMSach = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 96, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 204));
        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 102));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(300, 300));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(300, 300));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jTabbedPane3.setForeground(new java.awt.Color(0, 0, 153));
        jTabbedPane3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(255, 255, 204));

        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Tìm kiếm:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("Lọc:");

        jTextField1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\searching.png")); // NOI18N
        jButton1.setText("Tìm độc giả");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        jTabbedPane3.addTab("Xem thông tin Độc giả", jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 204));

        jTable2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("Mã độc giả:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Tên độc giả:");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setText("Giới tính:");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setText("Số điện thoại:");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Email:");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setText("Chức vụ:");

        jTextField2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 204));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jRadioButton1.setText("Nam");

        jRadioButton2.setBackground(new java.awt.Color(255, 255, 204));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jRadioButton2.setText("Nữ");

        jTextField4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jTextField5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jComboBox2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setText("Thêm mới");

        jButton3.setBackground(new java.awt.Color(255, 204, 204));
        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton3.setText("Sửa ");

        jButton4.setBackground(new java.awt.Color(255, 204, 204));
        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton4.setText("Xóa");

        jButton5.setIcon(new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\printer.png")); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(215, 215, 215)
                        .addComponent(jButton5))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton5))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Quản lý Độc giả", jPanel11);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );

        jTabbedPane1.addTab("     QUẢN LÝ ĐỘC GIẢ ", new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\reading.png"), jPanel3); // NOI18N

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTabbedPane2.setForeground(new java.awt.Color(0, 0, 153));
        jTabbedPane2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jPanel8.setBackground(new java.awt.Color(255, 255, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 51, 0));
        jLabel13.setText("Mã sách:");

        txt_maSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_maSach.setForeground(new java.awt.Color(255, 0, 0));
        txt_maSach.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 51, 0));
        jLabel14.setText("Tên sách:");

        txt_tenSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_tenSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenSachActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 51, 0));
        jLabel17.setText("Số lượng còn:");

        txt_soLuongCon.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 51, 0));
        jLabel18.setText("Tác giả:");

        txt_tacGia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 51, 0));
        jLabel19.setText("Nhà xuất bản:");

        txt_nhaXuatBan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_nhaXuatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nhaXuatBanActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 51, 0));
        jLabel20.setText("Năm xuất bản:");

        txt_namXuatBan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 51, 0));
        jLabel21.setText("Tóm tắt nội dung:");

        txt_tomTatND.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_tomTatND.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_tomTatND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tomTatNDActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 51, 0));
        jLabel22.setText("Tên danh mục:");

        txt_tenDM.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_tenDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenDMActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 51, 0));
        jLabel23.setText("Thể loại:");

        txt_tenTheLoai.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_tenTheLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenTheLoaiActionPerformed(evt);
            }
        });

        tbl_Sach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbl_Sach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_Sach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_SachMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_Sach);

        btn_themSach.setBackground(new java.awt.Color(255, 255, 102));
        btn_themSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_themSach.setIcon(new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\them.png")); // NOI18N
        btn_themSach.setText("Thêm");
        btn_themSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themSachActionPerformed(evt);
            }
        });

        btn_suaSach.setBackground(new java.awt.Color(255, 255, 102));
        btn_suaSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_suaSach.setIcon(new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\sua.png")); // NOI18N
        btn_suaSach.setText("Sửa ");
        btn_suaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaSachActionPerformed(evt);
            }
        });

        btn_xoaSach.setBackground(new java.awt.Color(255, 255, 102));
        btn_xoaSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_xoaSach.setIcon(new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\xoa.png")); // NOI18N
        btn_xoaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaSachActionPerformed(evt);
            }
        });

        btn_luuSach.setBackground(new java.awt.Color(255, 255, 102));
        btn_luuSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_luuSach.setIcon(new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\luu.png")); // NOI18N
        btn_luuSach.setText("Lưu");
        btn_luuSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_luuSachActionPerformed(evt);
            }
        });

        btn_lamMoiSach.setBackground(new java.awt.Color(255, 255, 102));
        btn_lamMoiSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_lamMoiSach.setIcon(new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\lammoi.png")); // NOI18N
        btn_lamMoiSach.setText("Làm mới");
        btn_lamMoiSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lamMoiSachActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 51, 0));
        jLabel24.setText("Mã thể loại:");

        cbb_maTheLoai.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbb_maTheLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_maTheLoaiActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 51, 0));
        jLabel25.setText("Mã danh mục:");

        cbb_maDM.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbb_maDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_maDMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel23))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_tenTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_soLuongCon, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_namXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addGap(37, 37, 37)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel13))
                                    .addGap(24, 24, 24)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_maSach, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_tenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbb_maTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(108, 108, 108))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel25)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19))))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_tenDM)
                            .addComponent(txt_tacGia)
                            .addComponent(txt_nhaXuatBan)
                            .addComponent(cbb_maDM, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tomTatND, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addComponent(jSeparator1)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_themSach)
                                .addGap(1, 1, 1)
                                .addComponent(btn_luuSach)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_suaSach)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_xoaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(112, 112, 112)
                                .addComponent(btn_lamMoiSach)
                                .addGap(19, 19, 19)))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txt_maSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txt_tenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(cbb_maTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txt_tenTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txt_soLuongCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txt_namXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txt_tacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(txt_nhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(cbb_maDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel22)
                                    .addComponent(txt_tenDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(txt_tomTatND, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_xoaSach)
                    .addComponent(btn_luuSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_suaSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_themSach)
                    .addComponent(btn_lamMoiSach))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Quản lý Sách", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 204));

        tbl_DMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbl_DMSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_DMSach.setGridColor(new java.awt.Color(255, 255, 255));
        tbl_DMSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_DMSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_DMSach);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\category (1).png")); // NOI18N
        jLabel1.setText("Thông tin danh mục Sách:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Mã danh mục:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Tên danh mục:");

        txt_maDMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_maDMSach.setEnabled(false);

        txt_tenDMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        btn_ThemDMSach.setBackground(new java.awt.Color(255, 204, 204));
        btn_ThemDMSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_ThemDMSach.setText("Thêm");
        btn_ThemDMSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemDMSachActionPerformed(evt);
            }
        });

        btn_LuuDMSach.setBackground(new java.awt.Color(255, 204, 204));
        btn_LuuDMSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_LuuDMSach.setText("Lưu");
        btn_LuuDMSach.setEnabled(false);
        btn_LuuDMSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LuuDMSachActionPerformed(evt);
            }
        });

        btn_SuaDMSach.setBackground(new java.awt.Color(255, 204, 204));
        btn_SuaDMSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_SuaDMSach.setText("Sửa");
        btn_SuaDMSach.setEnabled(false);
        btn_SuaDMSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaDMSachActionPerformed(evt);
            }
        });

        txt_timkiemDMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_timkiemDMSach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_timkiemDMSachKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txt_maDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(15, 15, 15)
                                .addComponent(txt_tenDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(btn_ThemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_LuuDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(95, 95, 95))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(btn_SuaDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(147, 147, 147)))))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txt_timkiemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)))
                .addGap(24, 24, 24))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_timkiemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel1)
                        .addGap(38, 38, 38)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_maDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_tenDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_LuuDMSach, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(btn_ThemDMSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btn_SuaDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(179, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Quản lý Danh mục", jPanel9);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        jTabbedPane1.addTab("          QUẢN LÝ SÁCH  ", new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\books.png"), jPanel4); // NOI18N

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 891, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("tab1", jPanel12);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 891, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("tab2", jPanel13);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );

        jTabbedPane1.addTab(" QUẢN LÝ MƯỢN TRẢ ", new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\exchange.png"), jPanel5); // NOI18N

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 896, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 653, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("                   THỐNG KÊ ", new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\statistics.png"), jPanel6); // NOI18N

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 896, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 653, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("                      TRA CỨU", new javax.swing.ImageIcon("D:\\Documents\\NetBeansProjects\\projectQuanLyThuVien\\src\\Images\\research.png"), jPanel7); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1184, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ThemDMSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemDMSachActionPerformed
        enable_DM();
        btn_ThemDMSach.setEnabled(false);
        btn_LuuDMSach.setEnabled(true);
        btn_SuaDMSach.setEnabled(false);
        
        txt_maDMSach.setText("");
        txt_tenDMSach.setText("");
    }//GEN-LAST:event_btn_ThemDMSachActionPerformed

    private void btn_LuuDMSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LuuDMSachActionPerformed
        if(txt_maDMSach.getText().trim().equals("") || txt_tenDMSach.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");
        } else{
            int x  = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm không?");
            if (x == JOptionPane.NO_OPTION){
                return;
            }
            else{
                DanhMucSach danhmuc = new DanhMucSach();
                danhmuc.setMaDM(txt_maDMSach.getText());
                danhmuc.setTenDM(txt_tenDMSach.getText());

                dms_Service.addDanhMucSach(danhmuc, txt_maDMSach.getText());
                defaultTableModel_DM.setRowCount(0);
                setTableData_DM(dms_Service.getDSDanhMucSach());
            }
        }
        btn_LuuDMSach.setEnabled(false);
        btn_ThemDMSach.setEnabled(true);
        btn_SuaDMSach.setEnabled(false);
        
    }//GEN-LAST:event_btn_LuuDMSachActionPerformed

    private void tbl_DMSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DMSachMouseClicked
        int selectedRow = tbl_DMSach.getSelectedRow();

        txt_maDMSach.setText((String) tbl_DMSach.getValueAt(selectedRow, 0));
        txt_tenDMSach.setText((String) tbl_DMSach.getValueAt(selectedRow, 1));
        
        txt_maDMSach.setEnabled(false);
        btn_SuaDMSach.setEnabled(true);
        
    }//GEN-LAST:event_tbl_DMSachMouseClicked

    private void btn_SuaDMSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaDMSachActionPerformed
        if(txt_maDMSach.getText().trim().equals("") || txt_tenDMSach.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin danh mục muốn sửa muốn sửa!");
        } else{
            int x  = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thay đổi không?");
            if (x == JOptionPane.NO_OPTION){
                return;
            }
            else{
                DanhMucSach danhmuc = new DanhMucSach();
                danhmuc.setMaDM(txt_maDMSach.getText());                       
                danhmuc.setTenDM(txt_tenDMSach.getText());

                dms_Service.updateDanhMucSach(danhmuc);
                defaultTableModel_DM.setRowCount(0);
                setTableData_DM(dms_Service.getDSDanhMucSach());
            }
        }
    }//GEN-LAST:event_btn_SuaDMSachActionPerformed

    private void txt_timkiemDMSachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_timkiemDMSachKeyReleased
        String query = txt_timkiemDMSach.getText();
        timkiem(query);
    }//GEN-LAST:event_txt_timkiemDMSachKeyReleased

    private void txt_tenSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenSachActionPerformed

    private void txt_nhaXuatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nhaXuatBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nhaXuatBanActionPerformed

    private void txt_tomTatNDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tomTatNDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tomTatNDActionPerformed

    private void txt_tenDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenDMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenDMActionPerformed

    private void txt_tenTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenTheLoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenTheLoaiActionPerformed

    private void btn_suaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaSachActionPerformed
        if(txt_tenSach.getText().trim().equals("") || txt_tenTheLoai.getText().trim().equals("") || txt_tenDM.getText().trim().equals("") || txt_namXuatBan.getText().trim().equals("") || txt_nhaXuatBan.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin sách muốn xóa!");
        } else{
            int x  = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thay đổi không?");
            if (x == JOptionPane.NO_OPTION){
                return;
            }
            else{
                Sach sach = new Sach();
                
                sach.setMaSach(txt_maSach.getText());
                sach.setTenSach(txt_tenSach.getText());
                sach.setMaDMSach(cbb_maDM.getItemAt(cbb_maDM.getSelectedIndex()));
                sach.setMaTheLoai(cbb_maTheLoai.getItemAt(cbb_maTheLoai.getSelectedIndex()));
                sach.setTacGia(txt_tacGia.getText());
                sach.setNXB(txt_nhaXuatBan.getText());
                sach.setNamXuatBan(Integer.parseInt(txt_namXuatBan.getText()));
                sach.setSoLuongCon(Integer.parseInt(txt_soLuongCon.getText()));
                sach.setTomTatND(txt_tomTatND.getText());
                
                s_Service.updateSach(sach);
                defaultTableModel_S.setRowCount(0);
                setTableData_S(s_Service.getDSSach());  
            }
        }
        btn_themSach.setEnabled(true);
        btn_xoaSach.setEnabled(false);
        btn_suaSach.setEnabled(false);
        btn_luuSach.setEnabled(false);
    }//GEN-LAST:event_btn_suaSachActionPerformed

    private void btn_lamMoiSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lamMoiSachActionPerformed
        refresh();
        disable_S();
    }//GEN-LAST:event_btn_lamMoiSachActionPerformed

    private void btn_themSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themSachActionPerformed
        enable_S();
        refresh();
        
        btn_luuSach.setEnabled(true);
        btn_suaSach.setEnabled(false);
        btn_xoaSach.setEnabled(false);
        btn_themSach.setEnabled(false);
    }//GEN-LAST:event_btn_themSachActionPerformed

    private void cbb_maTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_maTheLoaiActionPerformed
        String sql = "SELECT tenTheLoai FROM TheLoai WHERE maTheLoai = ?"; 
        try{
            String maTheLoaiString = (String) cbb_maTheLoai.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maTheLoaiString);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                txt_tenTheLoai.setText(rs.getString("tenTheLoai"));
            }
        } catch(Exception e){
            
        }
    }//GEN-LAST:event_cbb_maTheLoaiActionPerformed

    private void cbb_maDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_maDMActionPerformed
        String sql = "SELECT tenDMSach FROM DanhMucSach WHERE maDMSach = ?"; 
        try{
            String maDMString = (String) cbb_maDM.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maDMString);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                txt_tenDM.setText(rs.getString("tenDMSach"));
            }
        } catch(Exception e){
            
        }
    }//GEN-LAST:event_cbb_maDMActionPerformed

    private void tbl_SachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_SachMouseClicked
        int selectedRow = tbl_Sach.getSelectedRow();
        enable_S();
        
        txt_maSach.setText((String) tbl_Sach.getValueAt(selectedRow, 0));
        txt_tenSach.setText((String) tbl_Sach.getValueAt(selectedRow, 1));
        cbb_maDM.setSelectedItem((String) tbl_Sach.getValueAt(selectedRow, 2));
        cbb_maTheLoai.setSelectedItem((String) tbl_Sach.getValueAt(selectedRow, 3));
        txt_tacGia.setText((String) tbl_Sach.getValueAt(selectedRow, 4));
        txt_nhaXuatBan.setText((String) tbl_Sach.getValueAt(selectedRow, 5));
        txt_namXuatBan.setText(tbl_Sach.getValueAt(selectedRow, 6).toString());
        txt_soLuongCon.setText(tbl_Sach.getValueAt(selectedRow, 7).toString());
        txt_tomTatND.setText((String) tbl_Sach.getValueAt(selectedRow, 8));
        
        txt_maSach.setEnabled(false);
        btn_suaSach.setEnabled(true);
        btn_xoaSach.setEnabled(true);
    }//GEN-LAST:event_tbl_SachMouseClicked

    private void btn_luuSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_luuSachActionPerformed
        if(txt_tenSach.getText().trim().equals("") || txt_tenTheLoai.getText().trim().equals("") || txt_tenDM.getText().trim().equals("") || txt_namXuatBan.getText().trim().equals("") || txt_nhaXuatBan.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");
        } else{
            int x  = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm không?");
            if (x == JOptionPane.NO_OPTION){
                return;
            }
            else{
                Sach sach = new Sach();
                
                sach.setTenSach(txt_tenSach.getText());
                sach.setMaDMSach(cbb_maDM.getItemAt(cbb_maDM.getSelectedIndex()));
                sach.setMaTheLoai(cbb_maTheLoai.getItemAt(cbb_maTheLoai.getSelectedIndex()));
                sach.setTacGia(txt_tacGia.getText());
                sach.setNXB(txt_nhaXuatBan.getText());
                sach.setNamXuatBan(Integer.parseInt(txt_namXuatBan.getText()));
                sach.setSoLuongCon(Integer.parseInt(txt_soLuongCon.getText()));
                sach.setTomTatND(txt_tomTatND.getText());

                s_Service.addSach(sach);
                defaultTableModel_S.setRowCount(0);
                setTableData_S(s_Service.getDSSach());
            }
            refresh();
            btn_luuSach.setEnabled(false);
            btn_themSach.setEnabled(true);
            btn_suaSach.setEnabled(false);
            btn_xoaSach.setEnabled(false);
        }
    }//GEN-LAST:event_btn_luuSachActionPerformed

    private void btn_xoaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaSachActionPerformed
        if(txt_tenSach.getText().trim().equals("") || txt_tenTheLoai.getText().trim().equals("") || txt_tenDM.getText().trim().equals("") || txt_namXuatBan.getText().trim().equals("") || txt_nhaXuatBan.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin sách muốn xóa!");
        } else{
            int x  = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không?");
            if (x == JOptionPane.NO_OPTION){
                return;
            }
            else{
                int selectedRow = tbl_Sach.getSelectedRow();
                s_Service.deleteSach(tbl_Sach.getValueAt(selectedRow, 0).toString());
                refresh();
                enable_S();

                defaultTableModel_S.setRowCount(0);
                setTableData_S(s_Service.getDSSach());
            }
        }
        
        btn_luuSach.setEnabled(false);
        btn_themSach.setEnabled(true);
        btn_suaSach.setEnabled(false);
        btn_xoaSach.setEnabled(false);
    }//GEN-LAST:event_btn_xoaSachActionPerformed
    private void timkiem(String query){
           TableRowSorter<DefaultTableModel> tbl = new TableRowSorter<DefaultTableModel>(defaultTableModel_DM);
           tbl_DMSach.setRowSorter(tbl);
           tbl.setRowFilter(RowFilter.regexFilter(query));

       }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrangChuThuThu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_LuuDMSach;
    private javax.swing.JButton btn_SuaDMSach;
    private javax.swing.JButton btn_ThemDMSach;
    private javax.swing.JButton btn_lamMoiSach;
    private javax.swing.JButton btn_luuSach;
    private javax.swing.JButton btn_suaSach;
    private javax.swing.JButton btn_themSach;
    private javax.swing.JButton btn_xoaSach;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbb_maDM;
    private javax.swing.JComboBox<String> cbb_maTheLoai;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tbl_DMSach;
    private javax.swing.JTable tbl_Sach;
    private javax.swing.JTextField txt_maDMSach;
    private javax.swing.JTextField txt_maSach;
    private javax.swing.JTextField txt_namXuatBan;
    private javax.swing.JTextField txt_nhaXuatBan;
    private javax.swing.JTextField txt_soLuongCon;
    private javax.swing.JTextField txt_tacGia;
    private javax.swing.JTextField txt_tenDM;
    private javax.swing.JTextField txt_tenDMSach;
    private javax.swing.JTextField txt_tenSach;
    private javax.swing.JTextField txt_tenTheLoai;
    private javax.swing.JTextField txt_timkiemDMSach;
    private javax.swing.JTextField txt_tomTatND;
    // End of variables declaration//GEN-END:variables
}
