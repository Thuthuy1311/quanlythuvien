
package View;

import DAO.DocGia_Dao;
import DAO.DocGia_Dao_implement;
import DAO.KetNoiSQL;
import DAO.Sach_Dao_implement;
import DAO.Sach_dao_thuy;
import DAO.Thongke_Dao;
import Model.ChiTietPhieuMuon;
import Model.DanhMucSach;
import Model.Docgia;
import Model.PhieuMuon;
import Model.Sach;
import Model.sach_th;
import Service.CTPhieuMuon_Service;
import Service.DanhMucSach_Service;
import Service.PhieuMuon_Service;
import Service.Sach_Service;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import jdk.nashorn.internal.parser.TokenType;
/**
 *
 * @author KHP2T
 */
public class TrangChuThuThu extends javax.swing.JFrame {

    DefaultTableModel defaultTableModel_DM;
    DanhMucSach_Service dms_Service;
    DefaultTableModel defaultTableModel_S;
    Sach_Service s_Service;
    
    
    DocGia_Dao accessDocGia = new DocGia_Dao_implement();
    Sach_dao_thuy getSach = new Sach_Dao_implement();
    Thongke_Dao getThongke = new Thongke_Dao();
    
    CTPhieuMuon_Service ctpmsService;
    PhieuMuon_Service pmsService;
    DefaultTableModel defaultTableModel_DSPM;
    DefaultTableModel defaultTableModel_CTPM;
    
    public Connection conn = KetNoiSQL.getConnection();
    
    public TrangChuThuThu() {
        initComponents();
        disable_DM();
        btnH_luuSach.setEnabled(false);
        btnH_suaSach.setEnabled(false);
        //QuanLyPM
        pmsService = new PhieuMuon_Service();
        ctpmsService = new CTPhieuMuon_Service();
        defaultTableModel_DSPM = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        defaultTableModel_CTPM = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        loadData_TblDSPM();
        DesignTbl_DSPM();
        disable_CTPM();
        //Danh m???c s??ch
        dms_Service = new DanhMucSach_Service();
        defaultTableModel_DM = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tbl_DMSach.setModel(defaultTableModel_DM);
        defaultTableModel_DM.addColumn("M?? danh m???c");
        defaultTableModel_DM.addColumn("T??n danh m???c");
        
        setTableData_DM(dms_Service.getDSDanhMucSach());
        
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tbl_DMSach.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(0);
        tbl_DMSach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tbl_DMSach.setRowHeight(30);
        
        TableColumnModel columnModel = tbl_DMSach.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(170);
        columnModel.getColumn(1).setPreferredWidth(300);
        
        //S??ch
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
        
        tblH_Sach.setModel(defaultTableModel_S);
        defaultTableModel_S.addColumn("M?? s??ch");
        defaultTableModel_S.addColumn("T??n s??ch");
        defaultTableModel_S.addColumn("M?? DM");
        defaultTableModel_S.addColumn("M?? TL");
        defaultTableModel_S.addColumn("T??c gi???");
        defaultTableModel_S.addColumn("Nh?? XB");
        defaultTableModel_S.addColumn("N??m XB");
        defaultTableModel_S.addColumn("SL");
        defaultTableModel_S.addColumn("T??m t???t");
        //defaultTableModel_S.addColumn("???nh");
        
        mokhoa.setEnabled(false);
        
        setTableData_S(s_Service.getDSSach());
        
        DefaultTableCellRenderer renderers = (DefaultTableCellRenderer) tblH_Sach.getTableHeader().getDefaultRenderer();
        renderers.setHorizontalAlignment(0);
        tblH_Sach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
        tblH_Sach.setRowHeight(30);
        
        TableColumnModel columnModel1 = tblH_Sach.getColumnModel();
        columnModel1.getColumn(0).setPreferredWidth(150);
        columnModel1.getColumn(1).setPreferredWidth(350);
        columnModel1.getColumn(2).setPreferredWidth(150);
        columnModel1.getColumn(3).setPreferredWidth(150);
        columnModel1.getColumn(4).setPreferredWidth(170);
        columnModel1.getColumn(5).setPreferredWidth(250);
        columnModel1.getColumn(6).setPreferredWidth(120);
        columnModel1.getColumn(7).setPreferredWidth(50);
        columnModel1.getColumn(8).setPreferredWidth(120);
        //columnModel1.getColumn(9).setPreferredWidth(100);
        
        DefaultTableCellRenderer rendererdg = (DefaultTableCellRenderer) tableDocgia.getTableHeader().getDefaultRenderer();
        rendererdg.setHorizontalAlignment(0);
        tableDocgia.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tableDocgia.setRowHeight(30);
        
        TableColumnModel columnModeldg = tableDocgia.getColumnModel();
        columnModeldg.getColumn(0).setPreferredWidth(120);
        columnModeldg.getColumn(1).setPreferredWidth(180);
        columnModeldg.getColumn(4).setPreferredWidth(150);
        
        DefaultTableCellRenderer rendererxttdg = (DefaultTableCellRenderer) tablexemttdg.getTableHeader().getDefaultRenderer();
        rendererxttdg.setHorizontalAlignment(0);
        tablexemttdg.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tablexemttdg.setRowHeight(30);
        
        TableColumnModel columnModelxttdg = tablexemttdg.getColumnModel();
        columnModelxttdg.getColumn(0).setPreferredWidth(120);
        columnModelxttdg.getColumn(1).setPreferredWidth(180);
        columnModelxttdg.getColumn(2).setPreferredWidth(90);
        DefaultTableCellRenderer rendertkbd = (DefaultTableCellRenderer) tabletkbandoc.getTableHeader().getDefaultRenderer();
        rendertkbd.setHorizontalAlignment(0);
        tabletkbandoc.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
        tabletkbandoc.setRowHeight(30);
        
        TableColumnModel columnModetkbd = tablexemttdg.getColumnModel();
        columnModetkbd.getColumn(0).setPreferredWidth(120);
        columnModetkbd.getColumn(1).setPreferredWidth(180);
        columnModetkbd.getColumn(2).setPreferredWidth(220);
        
        DefaultTableCellRenderer rendertks = (DefaultTableCellRenderer) tabletksach.getTableHeader().getDefaultRenderer();
        rendertks.setHorizontalAlignment(0);
        tabletksach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
        tabletksach.setRowHeight(30);
        
        
        DefaultTableCellRenderer rendertktp = (DefaultTableCellRenderer) tabletktienphat.getTableHeader().getDefaultRenderer();
        rendertktp.setHorizontalAlignment(0);
        tabletktienphat.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
        tabletktienphat.setRowHeight(30);
        
       // load du lieu quan l?? ?????c gi???
        this.loaddg();
        
        //load d??? li???u s??ch v??o trang tra c??? s??ch
        
        DefaultTableCellRenderer renderers_SearchSach = (DefaultTableCellRenderer) tableSearchSach.getTableHeader().getDefaultRenderer();
        renderers_SearchSach.setHorizontalAlignment(0);
        tableSearchSach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
        tableSearchSach.setRowHeight(30);
        
        TableColumnModel columnModel_SearchSach = tableSearchSach.getColumnModel();
        columnModel_SearchSach.getColumn(0).setPreferredWidth(50);
        columnModel_SearchSach.getColumn(1).setPreferredWidth(300);
        columnModel_SearchSach.getColumn(2).setPreferredWidth(150);
        columnModel_SearchSach.getColumn(3).setPreferredWidth(200);
        columnModel_SearchSach.getColumn(4).setPreferredWidth(100);
        
        List<sach_th> allSach = getSach.getAllSach();
        
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        int i = 0;
        for (sach_th s : allSach) {
            i++;
            sachtb.addRow(new Object[] {i, s.getTenSach(), s.getTacgia(), s.getNhaSB(), s.getSoluong()});
        }
    }
    
    public void loaddg () {
        madg.setText("");
        tenTaikhoan.setText("");
        ngaysinh.setText("");
        emailDocgia.setText("");
        this.sdt.setText("");
        madg.setEditable(true);
        List<Docgia> allDocgia = accessDocGia.getAllDocgiar();
        DefaultTableModel tb = (DefaultTableModel) tableDocgia.getModel();
        DefaultTableModel tbxtt = (DefaultTableModel) tablexemttdg.getModel();
        tb.setRowCount(0);
        tbxtt.setRowCount(0);
        allDocgia.forEach((docgia) -> {
            String gt = "Nam";
            if (docgia.getGioiTinh() == 2) {
                gt = "N???";
            }
            tb.addRow(new Object[] {docgia.getMaTk(), docgia.getTenTk(), docgia.getNgaySinh(), gt, docgia.getEmail(), docgia.getSdt()});
            tbxtt.addRow(new Object[] {docgia.getMaTk(), docgia.getTenTk(), docgia.getNgaySinh(), gt, docgia.getEmail(), docgia.getSdt()});
        });
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
        H_tenSach.setEnabled(true);
        Hc_maTheLoai.setEnabled(true);
        //H_tenTheLoai.setEnabled(true);
        H_soLuongCon.setEnabled(true);
        H_namXB.setEnabled(true);
        H_tacGia.setEnabled(true);
        H_nhaXB.setEnabled(true);
        Hc_maDM.setEnabled(true);
        //H_tenDM.setEnabled(true);
        H_tomTat.setEnabled(true);
        //H_anhSach.setEnabled(true);
    }
    
    public void disable_S(){
        H_tenSach.setEnabled(false);
        Hc_maTheLoai.setEnabled(false);
        H_tenTheLoai.setEnabled(false);
        H_soLuongCon.setEnabled(false);
        H_namXB.setEnabled(false);
        H_tacGia.setEnabled(false);
        H_nhaXB.setEnabled(false);
        Hc_maDM.setEnabled(false);
        H_tenDM.setEnabled(false);
        H_tomTat.setEnabled(false);
        //H_anhSach.setEnabled(true);
    }
    
    public void refresh(){
        H_maSach.setText("");
        H_tenSach.setText("");
        Hc_maTheLoai.setSelectedIndex(-1);
        H_tenTheLoai.setText("");
        H_soLuongCon.setText("");
        H_namXB.setText("");
        H_tacGia.setText("");
        H_nhaXB.setText("");
        Hc_maDM.setSelectedIndex(-1);
        H_tenDM.setText("");
        H_tomTat.setText("");
        //H_anhSach.setText("");
        
//        btnH_themSach.setEnabled(true);
//        btnH_luuSach.setEnabled(false);
//        btnH_suaSach.setEnabled(false);
//        btnH_xoaSach.setEnabled(false);
    }
    
    public void loadmaTheLoai(){
        Hc_maTheLoai.removeAllItems();
        String sql = "SELECT maTheLoai FROM TheLoai";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                this.Hc_maTheLoai.addItem(rs.getString("maTheLoai"));
            }
        }catch(Exception e){

        }
    }
    
    public void loadmaDanhMuc(){
        Hc_maDM.removeAllItems();
        String sql = "SELECT maDMSach FROM DanhMucSach";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                this.Hc_maDM.addItem(rs.getString("maDMSach"));
            }
        }catch(Exception e){

        }
    }
    
    public int getSoNgayTre(String maPM, String maSach, String ngayThucTra) {
        String sql = "select ((DAY('" + ngayThucTra + "') - DAY(pm.ngayMuon)) - pm.soNgayMuon) as 'S' from ChiTietPhieuMuon as ctpm, PhieuMuon as pm where ctpm.maPhieuMuon = pm.maPhieuMuon and ((DAY(ctpm.ngayThucTra) - DAY(pm.ngayMuon)) > pm.soNgayMuon) and maSach like '%" + maSach + "%' and pm.maPhieuMuon like '%" + maPM + "%'";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                
                return rs.getInt("S");
            }
        }catch(Exception e){
            System.out.println("khong tinh dc");
        }
        return 0;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gioitinhbtngroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTP_main = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        quanlyttdg = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDocgia = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        madg = new javax.swing.JTextField();
        tenTaikhoan = new javax.swing.JTextField();
        gioitinhnam = new javax.swing.JRadioButton();
        gioitinhnu = new javax.swing.JRadioButton();
        sdt = new javax.swing.JTextField();
        emailDocgia = new javax.swing.JTextField();
        themmoidg = new javax.swing.JButton();
        updatedg = new javax.swing.JButton();
        khoatk = new javax.swing.JButton();
        ngaysinh = new javax.swing.JTextField();
        mokhoa = new javax.swing.JButton();
        mokhoa1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablexemttdg = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        textSearch = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        H_maSach = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        H_tenSach = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        H_soLuongCon = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        H_tacGia = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        H_nhaXB = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        H_namXB = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        H_tomTat = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        H_tenDM = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        H_tenTheLoai = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblH_Sach = new javax.swing.JTable();
        btnH_themSach = new javax.swing.JButton();
        btnH_suaSach = new javax.swing.JButton();
        btnH_luuSach = new javax.swing.JButton();
        btn_lamMoiSach = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        Hc_maTheLoai = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        Hc_maDM = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
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
        btn_lammoi = new javax.swing.JButton();
        jPK_QuanLyPhieuMuon = new javax.swing.JPanel();
        jTPK_QuanLyPM = new javax.swing.JTabbedPane();
        Panel_DanhSachPM = new javax.swing.JPanel();
        jSPK_DSPM = new javax.swing.JScrollPane();
        tblK_DSPM = new javax.swing.JTable();
        btnK_themPM = new javax.swing.JButton();
        K_tieuDe = new javax.swing.JLabel();
        Panel_ChiTietPM = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        K_ngayThucTra = new com.toedter.calendar.JDateChooser();
        K_maPM = new javax.swing.JTextField();
        K_maSach = new javax.swing.JTextField();
        K_tinhTrangSach = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblK_ChiTiet = new javax.swing.JTable();
        btnK_veTrangTruoc = new javax.swing.JButton();
        btnK_suaChiTiet = new javax.swing.JButton();
        btnK_luuChiTiet = new javax.swing.JButton();
        btnK_lamMoi = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        cbb_chucNangThongKe = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabletkbandoc = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        cbb_chucNangThongKe1 = new javax.swing.JComboBox<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabletksach = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        cbb_chucNangThongKe2 = new javax.swing.JComboBox<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabletktienphat = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        timkiem = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        textboxsearch = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableSearchSach = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel6.setText("QU???N L?? TH?? VI???N");

        jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel36.setText(" TR?????NG ?????I H???C S?? PH???M K??? THU???T - ?????I H???C ???? N???NG");

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logout.png"))); // NOI18N
        jButton1.setText("????ng xu???t");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logo-truong-250.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(252, 252, 252)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel36)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(221, 221, 221)))
                    .addContainerGap(252, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(27, 27, 27)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addComponent(jLabel6)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel36)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jTP_main.setBackground(new java.awt.Color(255, 255, 204));
        jTP_main.setForeground(new java.awt.Color(0, 0, 102));
        jTP_main.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTP_main.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTP_main.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jTP_main.setMaximumSize(new java.awt.Dimension(300, 300));
        jTP_main.setPreferredSize(new java.awt.Dimension(300, 300));
        jTP_main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTP_mainMouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        quanlyttdg.setForeground(new java.awt.Color(0, 0, 153));
        quanlyttdg.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jPanel11.setBackground(new java.awt.Color(255, 255, 204));

        tableDocgia.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tableDocgia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "M?? t??i kho???n", "T??n t??i kho???n", "Ng??y sinh", "Gi???i t??nh", "Email", "SDT"
            }
        ));
        tableDocgia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDocgiaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableDocgia);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel7.setText("M?? ?????c gi???:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel8.setText("T??n ?????c gi???:");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel9.setText("Gi???i t??nh:");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel10.setText("S??? ??i???n tho???i:");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel11.setText("Email:");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel12.setText("Ng??y sinh");

        madg.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        madg.setForeground(new java.awt.Color(204, 0, 0));
        madg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                madgKeyPressed(evt);
            }
        });

        tenTaikhoan.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tenTaikhoan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tenTaikhoanKeyPressed(evt);
            }
        });

        gioitinhnam.setBackground(new java.awt.Color(255, 255, 204));
        gioitinhbtngroup.add(gioitinhnam);
        gioitinhnam.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        gioitinhnam.setText("Nam");

        gioitinhnu.setBackground(new java.awt.Color(255, 255, 204));
        gioitinhbtngroup.add(gioitinhnu);
        gioitinhnu.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        gioitinhnu.setText("N???");

        sdt.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        sdt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sdtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sdtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sdtKeyTyped(evt);
            }
        });

        emailDocgia.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

        themmoidg.setBackground(new java.awt.Color(255, 204, 204));
        themmoidg.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        themmoidg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus.png"))); // NOI18N
        themmoidg.setText("Th??m m???i");
        themmoidg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themmoidgActionPerformed(evt);
            }
        });

        updatedg.setBackground(new java.awt.Color(255, 204, 204));
        updatedg.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updatedg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exchange1.png"))); // NOI18N
        updatedg.setText("S???a ");
        updatedg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatedgActionPerformed(evt);
            }
        });

        khoatk.setBackground(new java.awt.Color(255, 204, 204));
        khoatk.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        khoatk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/block-user.png"))); // NOI18N
        khoatk.setText("Kh??a t??i kho???n");
        khoatk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                khoatkActionPerformed(evt);
            }
        });

        ngaysinh.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        ngaysinh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ngaysinhFocusLost(evt);
            }
        });
        ngaysinh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ngaysinhKeyPressed(evt);
            }
        });

        mokhoa.setBackground(new java.awt.Color(255, 204, 204));
        mokhoa.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        mokhoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/man.png"))); // NOI18N
        mokhoa.setText("M??? Kh??a");
        mokhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mokhoaActionPerformed(evt);
            }
        });

        mokhoa1.setBackground(new java.awt.Color(255, 204, 204));
        mokhoa1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        mokhoa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/eraser.png"))); // NOI18N
        mokhoa1.setText("L??m m???i");
        mokhoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mokhoa1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(gioitinhnam)
                                .addGap(18, 18, 18)
                                .addComponent(gioitinhnu))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(madg, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tenTaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sdt, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailDocgia, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(95, 95, 95))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(themmoidg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updatedg, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(khoatk, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mokhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(mokhoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(madg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(ngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(tenTaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(emailDocgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(gioitinhnam)
                        .addComponent(gioitinhnu)))
                .addGap(38, 38, 38)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(khoatk, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mokhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mokhoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(themmoidg, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatedg, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        quanlyttdg.addTab("Qu???n l?? ?????c gi???", jPanel11);

        jPanel10.setBackground(new java.awt.Color(255, 255, 204));

        tablexemttdg.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tablexemttdg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "M?? b???n ?????c", "T??n b???n ?????c", "Ng??y Sinh", "Gi???i T??nh", "Email", "S??? ??i???n tho???i"
            }
        ));
        tablexemttdg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablexemttdgMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablexemttdg);
        if (tablexemttdg.getColumnModel().getColumnCount() > 0) {
            tablexemttdg.getColumnModel().getColumn(2).setMinWidth(50);
            tablexemttdg.getColumnModel().getColumn(2).setPreferredWidth(90);
            tablexemttdg.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel5.setText("Nh???p th??ng tin b???n ?????c:");

        textSearch.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        textSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textSearchKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(531, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(247, Short.MAX_VALUE))
        );

        quanlyttdg.addTab("Xem th??ng tin ?????c gi???", jPanel10);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(quanlyttdg)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(quanlyttdg)
        );

        jTP_main.addTab("     QU???N L?? ?????C GI??? ", new javax.swing.ImageIcon(getClass().getResource("/Images/reading.png")), jPanel3); // NOI18N

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTabbedPane2.setForeground(new java.awt.Color(0, 0, 153));
        jTabbedPane2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jPanel8.setBackground(new java.awt.Color(255, 255, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 51, 0));
        jLabel13.setText("M?? s??ch:");

        H_maSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        H_maSach.setForeground(new java.awt.Color(255, 0, 0));
        H_maSach.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 51, 0));
        jLabel14.setText("T??n s??ch:");

        H_tenSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 51, 0));
        jLabel17.setText("S??? l?????ng c??n:");

        H_soLuongCon.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 51, 0));
        jLabel18.setText("T??c gi???:");

        H_tacGia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 51, 0));
        jLabel19.setText("Nh?? xu???t b???n:");

        H_nhaXB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 51, 0));
        jLabel20.setText("N??m xu???t b???n:");

        H_namXB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 51, 0));
        jLabel21.setText("T??m t???t n???i dung:");

        H_tomTat.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        H_tomTat.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 51, 0));
        jLabel22.setText("T??n danh m???c:");

        H_tenDM.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        H_tenDM.setEnabled(false);

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 51, 0));
        jLabel23.setText("Th??? lo???i:");

        H_tenTheLoai.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        H_tenTheLoai.setEnabled(false);

        tblH_Sach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblH_Sach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblH_Sach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblH_SachMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblH_Sach);

        btnH_themSach.setBackground(new java.awt.Color(255, 204, 204));
        btnH_themSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnH_themSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus.png"))); // NOI18N
        btnH_themSach.setText("Th??m");
        btnH_themSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnH_themSachActionPerformed(evt);
            }
        });

        btnH_suaSach.setBackground(new java.awt.Color(255, 204, 204));
        btnH_suaSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnH_suaSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exchange1.png"))); // NOI18N
        btnH_suaSach.setText("S???a ");
        btnH_suaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnH_suaSachActionPerformed(evt);
            }
        });

        btnH_luuSach.setBackground(new java.awt.Color(255, 204, 204));
        btnH_luuSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnH_luuSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/luu.png"))); // NOI18N
        btnH_luuSach.setText("L??u");
        btnH_luuSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnH_luuSachActionPerformed(evt);
            }
        });

        btn_lamMoiSach.setBackground(new java.awt.Color(255, 204, 204));
        btn_lamMoiSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_lamMoiSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/eraser.png"))); // NOI18N
        btn_lamMoiSach.setText("L??m m???i");
        btn_lamMoiSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lamMoiSachActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 51, 0));
        jLabel24.setText("M?? th??? lo???i:");

        Hc_maTheLoai.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        Hc_maTheLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Hc_maTheLoaiActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 51, 0));
        jLabel25.setText("M?? danh m???c:");

        Hc_maDM.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        Hc_maDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Hc_maDMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnH_themSach, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(btnH_luuSach, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnH_suaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177)
                        .addComponent(btn_lamMoiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel17))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(H_soLuongCon, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H_namXB, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(H_maSach, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H_tenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(H_tenTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Hc_maTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel25)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(H_tacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(H_nhaXB, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Hc_maDM, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(H_tenDM, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(H_tomTat, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator2))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(H_maSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(H_tacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(H_tenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(H_nhaXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(Hc_maTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(Hc_maDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(H_tenTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22)
                    .addComponent(H_tenDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(H_soLuongCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel21))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(H_namXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)))
                    .addComponent(H_tomTat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnH_themSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_lamMoiSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnH_luuSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnH_suaSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jTabbedPane2.addTab("Qu???n l?? S??ch", jPanel8);

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

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        jLabel1.setText("Th??ng tin danh m???c S??ch:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel2.setText("M?? danh m???c:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel3.setText("T??n danh m???c:");

        txt_maDMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_maDMSach.setEnabled(false);

        txt_tenDMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        btn_ThemDMSach.setBackground(new java.awt.Color(255, 204, 204));
        btn_ThemDMSach.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btn_ThemDMSach.setText("Th??m");
        btn_ThemDMSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemDMSachActionPerformed(evt);
            }
        });

        btn_LuuDMSach.setBackground(new java.awt.Color(255, 204, 204));
        btn_LuuDMSach.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btn_LuuDMSach.setText("L??u");
        btn_LuuDMSach.setEnabled(false);
        btn_LuuDMSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LuuDMSachActionPerformed(evt);
            }
        });

        btn_SuaDMSach.setBackground(new java.awt.Color(255, 204, 204));
        btn_SuaDMSach.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btn_SuaDMSach.setText("S???a");
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

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel4.setText("T??m ki???m");

        btn_lammoi.setBackground(new java.awt.Color(255, 204, 204));
        btn_lammoi.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btn_lammoi.setText("L??m m???i");
        btn_lammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txt_maDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(15, 15, 15)
                                .addComponent(txt_tenDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(123, 123, 123))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_SuaDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_ThemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_lammoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_LuuDMSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(128, 128, 128)))))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txt_timkiemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_timkiemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel1)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_maDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_tenDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_LuuDMSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_ThemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_SuaDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(138, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Qu???n l?? Danh m???c", jPanel9);

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

        jTP_main.addTab("          QU???N L?? S??CH  ", new javax.swing.ImageIcon(getClass().getResource("/Images/books.png")), jPanel4); // NOI18N

        jPK_QuanLyPhieuMuon.setBackground(new java.awt.Color(204, 204, 255));

        jTPK_QuanLyPM.setForeground(new java.awt.Color(51, 0, 102));
        jTPK_QuanLyPM.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        Panel_DanhSachPM.setBackground(new java.awt.Color(255, 255, 204));
        Panel_DanhSachPM.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        tblK_DSPM.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblK_DSPM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblK_DSPM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblK_DSPMMouseClicked(evt);
            }
        });
        jSPK_DSPM.setViewportView(tblK_DSPM);

        btnK_themPM.setBackground(new java.awt.Color(255, 204, 204));
        btnK_themPM.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnK_themPM.setText("Th??m phi???u m?????n");
        btnK_themPM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnK_themPMActionPerformed(evt);
            }
        });

        K_tieuDe.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        K_tieuDe.setText("C??C PHI???U M?????N ???? ????NG K??");

        javax.swing.GroupLayout Panel_DanhSachPMLayout = new javax.swing.GroupLayout(Panel_DanhSachPM);
        Panel_DanhSachPM.setLayout(Panel_DanhSachPMLayout);
        Panel_DanhSachPMLayout.setHorizontalGroup(
            Panel_DanhSachPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_DanhSachPMLayout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(Panel_DanhSachPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSPK_DSPM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 978, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnK_themPM, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29))
            .addGroup(Panel_DanhSachPMLayout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(K_tieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_DanhSachPMLayout.setVerticalGroup(
            Panel_DanhSachPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_DanhSachPMLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(K_tieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSPK_DSPM, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnK_themPM, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTPK_QuanLyPM.addTab("Danh s??ch phi???u m?????n", Panel_DanhSachPM);

        Panel_ChiTietPM.setBackground(new java.awt.Color(255, 255, 204));

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel26.setText("M?? phi???u m?????n:");

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel27.setText("M?? s??ch:");

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel28.setText("Ng??y th???c tr???:");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel30.setText("T??nh tr???ng s??ch:");

        K_ngayThucTra.setDateFormatString("yyyy-MM-dd");
        K_ngayThucTra.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

        K_maPM.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

        K_maSach.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

        K_tinhTrangSach.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        K_tinhTrangSach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "B??nh th?????ng", "M???t s??ch", "H?? h???ng", "Tr??? h???n" }));
        K_tinhTrangSach.setSelectedIndex(-1);
        K_tinhTrangSach.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                K_tinhTrangSachItemStateChanged(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 0, 0));
        jLabel38.setText("* Ch?? ??: - N???u s??ch b??? h?? h???ng, ti???n ph???t l?? 10.000??");

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(102, 0, 0));
        jLabel39.setText("- N???u s??ch b??? m???t, ti???n ph???t l?? 20.000??");

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(102, 0, 0));
        jLabel40.setText("- N???u tr??? s??ch qu?? h???n, ti???n ph???t b???ng s??? ng??y qu?? h???n * 10.000??");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(28, 28, 28)))))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(K_maSach, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(K_ngayThucTra, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K_maPM, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(K_tinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel38))
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(K_maPM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(K_tinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(K_maSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28)
                            .addComponent(K_ngayThucTra, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel40)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 204));

        tblK_ChiTiet.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tblK_ChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblK_ChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblK_ChiTietMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblK_ChiTiet);

        btnK_veTrangTruoc.setBackground(new java.awt.Color(255, 204, 204));
        btnK_veTrangTruoc.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btnK_veTrangTruoc.setText("V??? trang tr?????c");
        btnK_veTrangTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnK_veTrangTruocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnK_veTrangTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnK_veTrangTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnK_suaChiTiet.setBackground(new java.awt.Color(255, 204, 204));
        btnK_suaChiTiet.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btnK_suaChiTiet.setText("S???a th??ng tin");
        btnK_suaChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnK_suaChiTietActionPerformed(evt);
            }
        });

        btnK_luuChiTiet.setBackground(new java.awt.Color(255, 204, 204));
        btnK_luuChiTiet.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btnK_luuChiTiet.setText("L??u ");
        btnK_luuChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnK_luuChiTietActionPerformed(evt);
            }
        });

        btnK_lamMoi.setBackground(new java.awt.Color(255, 204, 204));
        btnK_lamMoi.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btnK_lamMoi.setText("L??m m???i");
        btnK_lamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnK_lamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_ChiTietPMLayout = new javax.swing.GroupLayout(Panel_ChiTietPM);
        Panel_ChiTietPM.setLayout(Panel_ChiTietPMLayout);
        Panel_ChiTietPMLayout.setHorizontalGroup(
            Panel_ChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Panel_ChiTietPMLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnK_suaChiTiet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnK_luuChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(317, 317, 317)
                .addComponent(btnK_lamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        Panel_ChiTietPMLayout.setVerticalGroup(
            Panel_ChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ChiTietPMLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(Panel_ChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnK_suaChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnK_luuChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnK_lamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTPK_QuanLyPM.addTab("Chi ti???t phi???u m?????n", Panel_ChiTietPM);

        javax.swing.GroupLayout jPK_QuanLyPhieuMuonLayout = new javax.swing.GroupLayout(jPK_QuanLyPhieuMuon);
        jPK_QuanLyPhieuMuon.setLayout(jPK_QuanLyPhieuMuonLayout);
        jPK_QuanLyPhieuMuonLayout.setHorizontalGroup(
            jPK_QuanLyPhieuMuonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTPK_QuanLyPM)
        );
        jPK_QuanLyPhieuMuonLayout.setVerticalGroup(
            jPK_QuanLyPhieuMuonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTPK_QuanLyPM)
        );

        jTP_main.addTab(" QU???N L?? M?????N TR??? ", new javax.swing.ImageIcon(getClass().getResource("/Images/exchange.png")), jPK_QuanLyPhieuMuon); // NOI18N

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        jTabbedPane5.setForeground(new java.awt.Color(0, 0, 102));
        jTabbedPane5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jPanel14.setBackground(new java.awt.Color(255, 255, 204));
        jPanel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel32.setText("L???a ch???n: ");

        cbb_chucNangThongKe.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbb_chucNangThongKe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "B???n ?????c ch??a tr??? s??ch", "B???n ?????c m?????n qu?? h???n", "B???n ?????c m?????n nhi???u nh???t" }));
        cbb_chucNangThongKe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_chucNangThongKeItemStateChanged(evt);
            }
        });

        tabletkbandoc.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tabletkbandoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(tabletkbandoc);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(26, 26, 26)
                        .addComponent(cbb_chucNangThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(195, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_chucNangThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Th??ng K?? B???n ?????c", jPanel14);

        jPanel15.setBackground(new java.awt.Color(255, 255, 204));

        jLabel33.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel33.setText("L???a ch???n:");

        cbb_chucNangThongKe1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbb_chucNangThongKe1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S??ch ???????c m?????n nhi???u nh???t theo ng??y", "T???ng s??? s??ch ???????n m?????n theo t???ng th??ng" }));
        cbb_chucNangThongKe1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_chucNangThongKe1ItemStateChanged(evt);
            }
        });

        tabletksach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tabletksach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(tabletksach);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(196, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addGap(26, 26, 26)
                .addComponent(cbb_chucNangThongKe1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(341, 341, 341))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(150, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_chucNangThongKe1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191))
        );

        jTabbedPane5.addTab("Th???ng K?? S??ch", jPanel15);

        jPanel16.setBackground(new java.awt.Color(255, 255, 204));

        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel34.setText("L???a ch???n:");

        cbb_chucNangThongKe2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbb_chucNangThongKe2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "T???ng ti???n ph???t theo th??ng" }));

        tabletktienphat.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tabletktienphat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane9.setViewportView(tabletktienphat);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(26, 26, 26)
                        .addComponent(cbb_chucNangThongKe2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(240, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_chucNangThongKe2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Th??ng K?? Ti???n Ph???t", jPanel16);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );

        jTP_main.addTab("                   TH???NG K?? ", new javax.swing.ImageIcon(getClass().getResource("/Images/statistics.png")), jPanel6); // NOI18N

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));

        timkiem.setBackground(new java.awt.Color(255, 255, 204));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 255));
        jLabel15.setText("T??m ki???m s??ch:");

        textboxsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textboxsearchActionPerformed(evt);
            }
        });
        textboxsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textboxsearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textboxsearchKeyTyped(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(255, 204, 204));
        jButton11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton11.setText("T??m ki???m");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel16.setText("L???c theo danh m???c:");

        jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel35.setText("L???c theo th??? lo???i:");

        jComboBox3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "T??m theo danh m???c", "Chuy??n ng??nh ??i???n-??i???n t???", "Chuy??n ng??nh C?? kh??", "Chuy??n ng??nh C??ng ngh??? th??ng tin", "Chuy??n ng??nh X??y d???ng", "S??ch Ti???ng Anh", "K??? n??ng s???ng" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jComboBox4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "T??m theo th??? lo???i", "Gi??o tr??nh h???c", "S??ch tham kh???o", "V??n h??a l???ch s???", "Ch??nh tr???, Ph??p lu???t", "T???p ch??" }));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        tableSearchSach.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tableSearchSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "T??n s??ch", "T??c gi???", "Nh?? s???n xu???t", "S??? l?????ng"
            }
        ));
        jScrollPane5.setViewportView(tableSearchSach);

        javax.swing.GroupLayout timkiemLayout = new javax.swing.GroupLayout(timkiem);
        timkiem.setLayout(timkiemLayout);
        timkiemLayout.setHorizontalGroup(
            timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timkiemLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(timkiemLayout.createSequentialGroup()
                        .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(timkiemLayout.createSequentialGroup()
                                .addComponent(textboxsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 313, Short.MAX_VALUE)))
                .addContainerGap())
        );
        timkiemLayout.setVerticalGroup(
            timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timkiemLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(textboxsearch)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(36, 36, 36)
                .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(timkiemLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel35))
                    .addGroup(timkiemLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox4)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1060, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(timkiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 687, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(timkiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTP_main.addTab("                      TRA C???U", new javax.swing.ImageIcon(getClass().getResource("/Images/research.png")), jPanel7); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTP_main, javax.swing.GroupLayout.DEFAULT_SIZE, 1367, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTP_main, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
        );

        jTP_main.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_timkiemDMSachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_timkiemDMSachKeyReleased
        String query = txt_timkiemDMSach.getText();
        timkiem(query);
    }//GEN-LAST:event_txt_timkiemDMSachKeyReleased

    private void btn_SuaDMSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaDMSachActionPerformed
        if(txt_maDMSach.getText().trim().equals("") || txt_tenDMSach.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Vui l??ng ch???n th??ng tin danh m???c mu???n s???a mu???n s???a!");
        } else{
            int x  = JOptionPane.showConfirmDialog(this, "B???n c?? ch???c ch???n mu???n thay ?????i kh??ng?");
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
                btn_ThemDMSach.setEnabled(true);
                btn_SuaDMSach.setEnabled(false);
                btn_LuuDMSach.setEnabled(false);
            }
        }
        disable_DM();
        txt_maDMSach.setText("");
        txt_tenDMSach.setText("");
    }//GEN-LAST:event_btn_SuaDMSachActionPerformed

    private void btn_LuuDMSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LuuDMSachActionPerformed
        if(txt_maDMSach.getText().trim().equals("") || txt_tenDMSach.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "B???n ch??a nh???p ????? th??ng tin!");
            txt_maDMSach.setEnabled(false);
            txt_tenDMSach.setEnabled(false);
            txt_maDMSach.setText("");
            txt_tenDMSach.setText("");
        } else{
            int x  = JOptionPane.showConfirmDialog(this, "B???n c?? ch???c ch???n mu???n th??m kh??ng?");
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
                
                txt_maDMSach.setEnabled(false);
                txt_tenDMSach.setEnabled(false);
                txt_maDMSach.setText("");
                txt_tenDMSach.setText("");
                
            }
        }
        loadmaDanhMuc();
        btn_LuuDMSach.setEnabled(false);
        btn_ThemDMSach.setEnabled(true);
        btn_SuaDMSach.setEnabled(false);

    }//GEN-LAST:event_btn_LuuDMSachActionPerformed

    private void btn_ThemDMSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemDMSachActionPerformed
        txt_tenDMSach.requestFocus();
        enable_DM();
       
        btn_LuuDMSach.setEnabled(true);
        btn_SuaDMSach.setEnabled(false);

        txt_maDMSach.setText("");
        txt_tenDMSach.setText("");
    }//GEN-LAST:event_btn_ThemDMSachActionPerformed

    private void tbl_DMSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DMSachMouseClicked
        int selectedRow = tbl_DMSach.getSelectedRow();
        txt_maDMSach.setText((String) tbl_DMSach.getValueAt(selectedRow, 0));
        txt_tenDMSach.setText((String) tbl_DMSach.getValueAt(selectedRow, 1));
        txt_tenDMSach.setEnabled(true);
        txt_maDMSach.setEnabled(false);
        btn_SuaDMSach.setEnabled(true);
        btn_ThemDMSach.setEnabled(false);
        btn_LuuDMSach.setEnabled(false);
    }//GEN-LAST:event_tbl_DMSachMouseClicked

    private void Hc_maDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hc_maDMActionPerformed
        String sql = "SELECT tenDMSach FROM DanhMucSach WHERE maDMSach = ?";
        try{
            String maDMString = (String) Hc_maDM.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maDMString);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                H_tenDM.setText(rs.getString("tenDMSach"));
            }
        } catch(Exception e){

        }
    }//GEN-LAST:event_Hc_maDMActionPerformed

    private void Hc_maTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hc_maTheLoaiActionPerformed
        String sql = "SELECT tenTheLoai FROM TheLoai WHERE maTheLoai = ?";
        try{
            String maTheLoaiString = (String) Hc_maTheLoai.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maTheLoaiString);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                H_tenTheLoai.setText(rs.getString("tenTheLoai"));
            }
        } catch(Exception e){

        }
    }//GEN-LAST:event_Hc_maTheLoaiActionPerformed

    private void btn_lamMoiSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lamMoiSachActionPerformed
        refresh();
        disable_S();
        btnH_luuSach.setEnabled(false);
        btnH_themSach.setEnabled(true);
        btnH_suaSach.setEnabled(false);
    }//GEN-LAST:event_btn_lamMoiSachActionPerformed

    private void btnH_luuSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH_luuSachActionPerformed
        if(H_tenSach.getText().trim().equals("") || H_tenTheLoai.getText().trim().equals("") || H_tenDM.getText().trim().equals("") || H_namXB.getText().trim().equals("") || H_nhaXB.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "B???n ch??a nh???p ????? th??ng tin!");
        } else{
            int x  = JOptionPane.showConfirmDialog(this, "B???n c?? ch???c ch???n mu???n th??m kh??ng?");
            if (x == JOptionPane.NO_OPTION){
                return;
            }
            else{
                Sach sach = new Sach();

                sach.setTenSach(H_tenSach.getText());
                sach.setMaDMSach(Hc_maDM.getItemAt(Hc_maDM.getSelectedIndex()));
                sach.setMaTheLoai(Hc_maTheLoai.getItemAt(Hc_maTheLoai.getSelectedIndex()));
                sach.setTacGia(H_tacGia.getText());
                sach.setNXB(H_nhaXB.getText());
                sach.setNamXuatBan(Integer.parseInt(H_namXB.getText()));
                sach.setSoLuongCon(Integer.parseInt(H_soLuongCon.getText()));
                sach.setTomTatND(H_tomTat.getText());
                //sach.setAnh(H_linkAnh.getText());
                ;

                s_Service.addSach(sach);
                defaultTableModel_S.setRowCount(0);
                setTableData_S(s_Service.getDSSach());
            }
            refresh();
            btnH_luuSach.setEnabled(false);
            btnH_themSach.setEnabled(true);
            btnH_suaSach.setEnabled(false);
            //btnH_xoaSach.setEnabled(false);
        }
    }//GEN-LAST:event_btnH_luuSachActionPerformed

    private void btnH_suaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH_suaSachActionPerformed
        if(H_tenSach.getText().trim().equals("") || H_tenTheLoai.getText().trim().equals("") || H_tenDM.getText().trim().equals("") || H_namXB.getText().trim().equals("") || H_nhaXB.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Vui l??ng ch???n th??ng tin s??ch mu???n x??a!");
        } else{
            int x  = JOptionPane.showConfirmDialog(this, "B???n c?? ch???c ch???n mu???n thay ?????i kh??ng?");
            if (x == JOptionPane.NO_OPTION){
                return;
            }
            else{
                Sach sach = new Sach();

                sach.setMaSach(H_maSach.getText());
                sach.setTenSach(H_tenSach.getText());
                sach.setMaDMSach(Hc_maDM.getItemAt(Hc_maDM.getSelectedIndex()));
                sach.setMaTheLoai(Hc_maTheLoai.getItemAt(Hc_maTheLoai.getSelectedIndex()));
                sach.setTacGia(H_tacGia.getText());
                sach.setNXB(H_nhaXB.getText());
                sach.setNamXuatBan(Integer.parseInt(H_namXB.getText()));
                sach.setSoLuongCon(Integer.parseInt(H_soLuongCon.getText()));
                sach.setTomTatND(H_tomTat.getText());

                s_Service.updateSach(sach);
                defaultTableModel_S.setRowCount(0);
                setTableData_S(s_Service.getDSSach());
            }
        }
        btnH_themSach.setEnabled(true);
        //btnH_xoaSach.setEnabled(false);
        btnH_suaSach.setEnabled(false);
        btnH_luuSach.setEnabled(false);
        disable_S();
    }//GEN-LAST:event_btnH_suaSachActionPerformed

    private void btnH_themSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH_themSachActionPerformed
        enable_S();
        refresh();
        H_tenSach.requestFocus();
        btnH_luuSach.setEnabled(true);
        btnH_suaSach.setEnabled(false);
        //btnH_xoaSach.setEnabled(false);
        btnH_themSach.setEnabled(false);
    }//GEN-LAST:event_btnH_themSachActionPerformed

    private void tblH_SachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblH_SachMouseClicked
        int selectedRow = tblH_Sach.getSelectedRow();
        //TableModel model = tblH_Sach.getModel();
        enable_S();

        H_maSach.setText((String) tblH_Sach.getValueAt(selectedRow, 0));
        H_tenSach.setText((String) tblH_Sach.getValueAt(selectedRow, 1));
        Hc_maDM.setSelectedItem((String) tblH_Sach.getValueAt(selectedRow, 2));
        Hc_maTheLoai.setSelectedItem((String) tblH_Sach.getValueAt(selectedRow, 3));
        H_tacGia.setText((String) tblH_Sach.getValueAt(selectedRow, 4));
        H_nhaXB.setText((String) tblH_Sach.getValueAt(selectedRow, 5));
        H_namXB.setText(tblH_Sach.getValueAt(selectedRow, 6).toString());
        H_soLuongCon.setText(tblH_Sach.getValueAt(selectedRow, 7).toString());
        H_tomTat.setText( tblH_Sach.getValueAt(selectedRow,8).toString());
        //        H_linkAnh.setText( tblH_Sach.getValueAt(selectedRow,9).toString());
        //        ImageIcon icon = new ImageIcon(tblH_Sach.getValueAt(selectedRow,9).toString());
        //        H_anhSach.setIcon(icon);
        enable_S();
        btnH_themSach.setEnabled(false);
        H_maSach.setEnabled(false);
        btnH_suaSach.setEnabled(true);
        btnH_luuSach.setEnabled(false);
       // btnH_xoaSach.setEnabled(true);
    }//GEN-LAST:event_tblH_SachMouseClicked

    private void K_tinhTrangSachItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_K_tinhTrangSachItemStateChanged
//        if(K_tinhTrangSach.getSelectedItem().equals("B??nh th?????ng")) {
//            K_tienPhat.setText("0");
//        } else if(K_tinhTrangSach.getSelectedItem().equals("M???t s??ch")){
//            K_tienPhat.setText("20000");
//        } else if(K_tinhTrangSach.getSelectedItem().equals("H?? h???ng")){
//            K_tienPhat.setText("10000");
//        }else if(K_tinhTrangSach.getSelectedItem().equals("Tr??? h???n")){
//            //int ????? t??m s??? ng??y qu?? h???n 
//            int tienPhat = getSoNgayTre(K_maPM.getText(), K_maSach.getText(), new java.sql.Date(K_ngayThucTra.getDate().getTime()).toString()) * 10000;
//            K_tienPhat.setText(String.valueOf(tienPhat));
//            System.out.println("" + new java.sql.Date(K_ngayThucTra.getDate().getTime()).toString());
//        }
//        else
//            K_tienPhat.setText("");
    }//GEN-LAST:event_K_tinhTrangSachItemStateChanged

    private void btnK_veTrangTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnK_veTrangTruocActionPerformed

        jTPK_QuanLyPM.setSelectedIndex(0);
        Refresh_CTPM();
        
    }//GEN-LAST:event_btnK_veTrangTruocActionPerformed

    private void tblK_DSPMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblK_DSPMMouseClicked
        // TODO add your handling code here:
        jTPK_QuanLyPM.setSelectedIndex(1);
        int selectedRow = tblK_DSPM.getSelectedRow();
        K_maPM.setText(tblK_DSPM.getValueAt(selectedRow, 0).toString());
        loadData_TblCTPM();
        
    }//GEN-LAST:event_tblK_DSPMMouseClicked

    private void tblK_ChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblK_ChiTietMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblK_ChiTiet.getSelectedRow();
        K_maSach.setText(tblK_ChiTiet.getValueAt(selectedRow, 1).toString());
        ((JTextField)K_ngayThucTra.getDateEditor().getUiComponent()).setText("");
        try {
            ((JTextField)K_ngayThucTra.getDateEditor().getUiComponent()).setText(tblK_ChiTiet.getValueAt(selectedRow, 2).toString());
        } catch(Exception e) {
            
        }
        
        
        
        K_tinhTrangSach.setSelectedItem(tblK_ChiTiet.getValueAt(selectedRow, 3));
//        K_tienPhat.setText(tblK_ChiTiet.getValueAt(selectedRow, 4).toString());
        btnK_suaChiTiet.setEnabled(true);
    }//GEN-LAST:event_tblK_ChiTietMouseClicked

    private void btnK_suaChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnK_suaChiTietActionPerformed
        // TODO add your handling code here:
        K_tinhTrangSach.setEnabled(true);
        K_ngayThucTra.setEnabled(true);
//        K_tienPhat.setEnabled(true);
        
        btnK_luuChiTiet.setEnabled(true);
        btnK_suaChiTiet.setEnabled(false);
    }//GEN-LAST:event_btnK_suaChiTietActionPerformed

    private void btnK_luuChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnK_luuChiTietActionPerformed
        // TODO add your handling code here:
        ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon();
        ctpm.setMaPM(K_maPM.getText());
        ctpm.setMaSach(K_maSach.getText());
        ctpm.setNgayThucTra(new java.sql.Date(K_ngayThucTra.getDate().getTime()).toString());
        ctpm.setTinhTrangSach(K_tinhTrangSach.getSelectedItem().toString());
//        ctpm.setTienPhat(Integer.parseInt(K_tienPhat.getText()));
        
        ctpmsService.updateChiTietPM(ctpm);
        loadData_TblCTPM();
        Refresh_CTPM();
    }//GEN-LAST:event_btnK_luuChiTietActionPerformed

    private void btnK_lamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnK_lamMoiActionPerformed
        // TODO add your handling code here:
//        btnK_suaChiTiet.setEnabled(false);
//        ((JTextField)K_ngayThucTra.getDateEditor().getUiComponent()).setText("");
//        K_tienPhat.setText("");
//        K_ngayThucTra.setEnabled(false);
//        K_tinhTrangSach.setEnabled(false);
//        K_tienPhat.setEnabled(false);
        Refresh_CTPM();
    }//GEN-LAST:event_btnK_lamMoiActionPerformed

    private void btnK_themPMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnK_themPMActionPerformed
        // TODO add your handling code here:
        QuanLyPhieuMuon qlPM = new QuanLyPhieuMuon();
        qlPM.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnK_themPMActionPerformed

    private void textSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textSearchKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textSearchKeyTyped

    private void tableDocgiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDocgiaMouseClicked
        DefaultTableModel tbxtt = (DefaultTableModel) tableDocgia.getModel();
        int selected = tableDocgia.getSelectedRow();
        if (selected != -1) {
            String tenDg = tbxtt.getValueAt(selected, 1).toString();
            String ngaySinh = tbxtt.getValueAt(selected, 2).toString();
            String gioTinh = tbxtt.getValueAt(selected, 3).toString();
            String email = tbxtt.getValueAt(selected, 4).toString();
            String sdt = tbxtt.getValueAt(selected, 5).toString();
            if (gioTinh.equals("Nam")) {
                gioitinhnam.setSelected(true);
            } else {
                gioitinhnu.setSelected(true);
            }
            this.madg.setEditable(false);
            tenTaikhoan.setText(tenDg);
            ngaysinh.setText(ngaySinh);
            emailDocgia.setText(email);
            this.sdt.setText(sdt);
            int i = accessDocGia.getOneDocgia(tbxtt.getValueAt(selected, 0).toString());
            if (i == 0) {
                mokhoa.setEnabled(true);
                khoatk.setEnabled(false);
            } else {
                khoatk.setEnabled(true);
                mokhoa.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui L??ng Ch???n Tr?????c Khi kh??a", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_tableDocgiaMouseClicked

    private void sdtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sdtKeyPressed
        int key = evt.getKeyCode();
        System.out.println(key);
        if ((key < 48 || key > 57) && key != 8 && (key < 96 || key > 105)) {
            if (key == 10) {
                ngaysinh.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "S??? ??i???n Tho???i kh??ng Ch??? K?? T??? Kh??c S???", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                sdt.setBorder(new LineBorder(Color.red, 1));                
            }
        }
    }//GEN-LAST:event_sdtKeyPressed

    private void sdtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sdtKeyTyped
        String sodt = sdt.getText();
        if (sodt.length() > 11 || sodt.length() < 9) {
            sdt.setBorder(new LineBorder(Color.red, 1));
        } else {
            sdt.setBorder(new LineBorder(Color.green, 1));
        }

    }//GEN-LAST:event_sdtKeyTyped

    private void themmoidgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themmoidgActionPerformed
        String madg = this.madg.getText();
        String tendg = tenTaikhoan.getText();
        String email = emailDocgia.getText();
        String sdt = this.sdt.getText();
        String ngaysinh = this.ngaysinh.getText();
        int gioitinh;

        if (gioitinhnam.isSelected())
            gioitinh = 1;
        else
            gioitinh = 2;
        
        if (madg.equals("") || tendg.equals("") || email.equals("") || sdt.equals("") || ngaysinh.equals("")) {
            JOptionPane.showMessageDialog(null, "B???n ch??a nh???p ????? th??ng tin!");
            return;
        }
        Docgia dg = new Docgia(madg, tendg, ngaysinh, gioitinh, email, sdt);
        DefaultTableModel tbadddg = (DefaultTableModel) tableDocgia.getModel();
        DefaultTableModel tbxttdg = (DefaultTableModel) tablexemttdg.getModel();
        int addcheck = accessDocGia.addDocgia(dg);
        if (addcheck != 0) {
            this.loaddg();
            tbadddg.addRow(new Object[] {madg, tendg, ngaysinh, (gioitinh == 1 ? "Nam" : "N???"), email, sdt});
            tbxttdg.addRow(new Object[] {madg, tendg, ngaysinh, (gioitinh == 1 ? "Nam" : "N???"), email, sdt});
            JOptionPane.showMessageDialog(null, "???? th??m th??nh c??ng!");
        } else {
            JOptionPane.showMessageDialog(null, "Th??ng tin ch??a ch??nh x??c Ho???c M?? t??i kho???n ???? tr??ng, Th??m th???t b???i");
        }
    }//GEN-LAST:event_themmoidgActionPerformed

    private void updatedgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatedgActionPerformed
        DefaultTableModel tbxtt = (DefaultTableModel) tableDocgia.getModel();
        int selected = tableDocgia.getSelectedRow();
        if (selected != -1) {
            String madg = tbxtt.getValueAt(selected, 0).toString();
            String tenDg = tenTaikhoan.getText();
            String ngaySinh = ngaysinh.getText();
            int gioitinh;
            if (gioitinhnam.isSelected())
            gioitinh = 1;
            else
            gioitinh = 2;
            String email =  emailDocgia.getText();
            String sdt = this.sdt.getText();

            int addcheck = accessDocGia.updateDocgia(new Docgia(madg, tenDg, ngaySinh, gioitinh, email, sdt));
            if (addcheck != 0) {
                this.loaddg();
                JOptionPane.showMessageDialog(null, "S???a Th??nh c??ng");
            } else {
                JOptionPane.showMessageDialog(null, "Th??ng tin ch??a ch??nh x??c, S???a th???t b???i");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui L??ng Ch???n Tr?????c Khi kh??a", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_updatedgActionPerformed

    private void khoatkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_khoatkActionPerformed
        DefaultTableModel tbxtt = (DefaultTableModel) tableDocgia.getModel();
        int selected = tableDocgia.getSelectedRow();
        if (selected != -1) {
            String madg = tbxtt.getValueAt(selected, 0).toString();
            int xacnhan = JOptionPane.showConfirmDialog(null, "B???n ch???c ch???n mu???n kh??a t??i kho???n ?", "X??c nh???n kh??a",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (xacnhan == 0) {
                if (accessDocGia.deleteDocgia(madg) != 0 ) {
//                    tbxtt.removeRow(selected);
//                    tbxtt.setRow
                    tenTaikhoan.setText("");
                    ngaysinh.setText("");
                    emailDocgia.setText("");
                    this.sdt.setText("");
                    khoatk.setEnabled(false);
                    mokhoa.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "???? kh??a", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "C?? L???i", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui L??ng Ch???n Tr?????c Khi kh??a", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_khoatkActionPerformed

    private void cbb_chucNangThongKeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_chucNangThongKeItemStateChanged

        try {
            int index = cbb_chucNangThongKe.getSelectedIndex();
            if (index == 0) {
                ResultSet chuatrasach = getThongke.chuatrasach();
                DefaultTableModel tkdg = (DefaultTableModel) tabletkbandoc.getModel();
                tkdg.setColumnCount(0);
                tkdg.addColumn("ma T??i Kho???n");
                tkdg.addColumn("t??n T??i Kho???n");
                tkdg.setRowCount(0);
                while (chuatrasach.next()) {
                    tkdg.addRow(new Object[] {chuatrasach.getString(1), chuatrasach.getString(2)});
                }
            }
            if (index == 1 ) {
                ResultSet quahan = getThongke.quahantra();
                DefaultTableModel tkdg = (DefaultTableModel) tabletkbandoc.getModel();
                tkdg.setColumnCount(0);
                tkdg.addColumn("t??n T??i Kho???n");
                tkdg.addColumn("ng??y m?????n");
                tkdg.addColumn("s??? ng??y m?????n");
                tkdg.addColumn("ng??y th???c tr???");
                tkdg.addColumn("s??? ng??y qu?? h???n");

                tkdg.setRowCount(0);
                while (quahan.next()) {
                    tkdg.addRow(new Object[] {quahan.getString(1), quahan.getString(2),quahan.getInt(3),quahan.getString(4),quahan.getInt(5)});
                }
            }
            if (index == 2) {
                ResultSet quahan = getThongke.muonnhiunhat();
                DefaultTableModel tkdg = (DefaultTableModel) tabletkbandoc.getModel();
                tkdg.setColumnCount(0);
                tkdg.addColumn("t??n T??i Kho???n");
                tkdg.addColumn("s??? l???n m?????n");
                tkdg.setRowCount(0);
                while (quahan.next()) {
                    tkdg.addRow(new Object[] {quahan.getString(2),quahan.getInt(1)});
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuThuThu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbb_chucNangThongKeItemStateChanged

    private void cbb_chucNangThongKe1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_chucNangThongKe1ItemStateChanged

        try {
            int index = cbb_chucNangThongKe1.getSelectedIndex();
            if (index == 0) {
                ResultSet sachmuonnhiunhat = getThongke.sachdocnhiunhat();
                DefaultTableModel sach = (DefaultTableModel) tabletksach.getModel();
                sach.setColumnCount(0);
                sach.addColumn("M?? S??ch");
                sach.addColumn("T??n S??ch");
                sach.setRowCount(0);
                while (sachmuonnhiunhat.next()) {

                    sach.addRow(new Object[] {sachmuonnhiunhat.getString(2), sachmuonnhiunhat.getString(3),sachmuonnhiunhat.getInt(1), sachmuonnhiunhat.getString(4)});
                }
            }
            if (index == 1 ) {
                ResultSet quahan = getThongke.sachnhatthang();
                DefaultTableModel tkdg = (DefaultTableModel) tabletksach.getModel();
                tkdg.setColumnCount(0);
                tkdg.addColumn("Th??ng");
                tkdg.addColumn("S??? S??ch ???????c M?????n");

                tkdg.setRowCount(0);
                while (quahan.next()) {
                    tkdg.addRow(new Object[] {quahan.getString(2), quahan.getInt(1)});
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(TrangChuThuThu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbb_chucNangThongKe1ItemStateChanged

    private void textboxsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textboxsearchKeyReleased
        String textSearch = textboxsearch.getText();
        List<sach_th> searchSach = getSach.getAllSach();
        List<sach_th> resultSearch = new ArrayList<>();
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        int i = 0;
        for (sach_th s : searchSach) {
            if (s.getTenSach().toLowerCase().contains(textSearch.toLowerCase()))
                resultSearch.add(s);
        }
        for (sach_th s : resultSearch) {
            i++;
            sachtb.addRow(new Object[] {i, s.getTenSach(), s.getTacgia(), s.getNhaSB(), s.getSoluong()});
        }
    }//GEN-LAST:event_textboxsearchKeyReleased

    private void textboxsearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textboxsearchKeyTyped

    }//GEN-LAST:event_textboxsearchKeyTyped

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String textSearch = textboxsearch.getText();
        List<sach_th> searchSach = getSach.getAllSach();
        List<sach_th> resultSearch = new ArrayList<>();
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        int i = 0;
        for (sach_th s : searchSach) {
            if (s.getTenSach().toLowerCase().contains(textSearch.toLowerCase())){
                resultSearch.add(s);
            }
        }
        for (sach_th s : resultSearch) {
            i++;
            sachtb.addRow(new Object[] {i, s.getTenSach(), s.getTacgia(), s.getNhaSB(), s.getSoluong()});
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        String DM = "DM00";
        int index = jComboBox3.getSelectedIndex();
        if (index == 0) return;
        DM+= Integer.toString(index);
        List<sach_th> sachByCate = getSach.getSachByCategory(DM);
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        int i = 0;
        for (sach_th s : sachByCate) {
            i++;
            sachtb.addRow(new Object[] {i, s.getTenSach(), s.getTacgia(), s.getNhaSB(), s.getSoluong()});
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        // TODO add your handling code here:
        String TL = "TL00";
        int index = jComboBox4.getSelectedIndex();
        if (index == 0) return;
        TL+= Integer.toString(index);
        List<sach_th> sachBytheloai = getSach.getSachByTheloai(TL);
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        int i = 0;
        for (sach_th s : sachBytheloai) {
            i++;
            sachtb.addRow(new Object[] {i, s.getTenSach(), s.getTacgia(), s.getNhaSB(), s.getSoluong()});
        }
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jTP_mainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTP_mainMouseClicked
        try {
            ResultSet chuatrasach = getThongke.chuatrasach();
            DefaultTableModel tkdg = (DefaultTableModel) tabletkbandoc.getModel();
            tkdg.setColumnCount(0);
            tkdg.addColumn("ma T??i Kho???n");
            tkdg.addColumn("t??n T??i Kho???n");
            tkdg.setRowCount(0);
            while (chuatrasach.next()) {
                tkdg.addRow(new Object[] {chuatrasach.getString(1), chuatrasach.getString(2)});
            }
            
            ResultSet sachmuonnhiunhat = getThongke.sachdocnhiunhat();
            DefaultTableModel sach = (DefaultTableModel) tabletksach.getModel();
            sach.setColumnCount(0);
            sach.addColumn("M?? S??ch");
            sach.addColumn("T??n S??ch");
            sach.addColumn("S??? L???n M?????n S??ch");
            sach.addColumn("Ng??y M?????n");
            
            sach.setRowCount(0);
            while (sachmuonnhiunhat.next()) {
                
                sach.addRow(new Object[] {sachmuonnhiunhat.getString(2), sachmuonnhiunhat.getString(3),sachmuonnhiunhat.getInt(1), sachmuonnhiunhat.getString(4)});
            }
            
            DefaultTableModel tien = (DefaultTableModel) tabletktienphat.getModel();
            tien.setColumnCount(0);
            tien.addColumn("Th??ng");
            tien.addColumn("T???ng Ti???n");
            
            ResultSet tienphat = getThongke.tienphatthang();
            
            tien.setRowCount(0);
            while (tienphat.next()) {
                tien.addRow(new Object[] {tienphat.getString(1), tienphat.getString(2)});
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuThuThu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jTP_mainMouseClicked

    private void textboxsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textboxsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textboxsearchActionPerformed

    private void textSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textSearchKeyReleased
       
        String matk = textSearch.getText();
        DefaultTableModel tbxtt = (DefaultTableModel) tablexemttdg.getModel();
        tbxtt.setRowCount(0);
        List<Docgia> searchDocgia = accessDocGia.getAllDocgiar();
        List<Docgia> resultSearch = new ArrayList<>();
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        
        for (Docgia d : searchDocgia) {
            if (d.getMaTk().toLowerCase().contains(matk.toLowerCase()))
                resultSearch.add(d);
            if (d.getTenTk().toLowerCase().contains(matk.toLowerCase()))
                resultSearch.add(d);
        }
        for (Docgia d : resultSearch) {
            tbxtt.addRow(new Object[] {d.getMaTk(), d.getTenTk(), d.getNgaySinh(), d.getGioiTinh(), d.getEmail(), d.getSdt()});
        }
    }//GEN-LAST:event_textSearchKeyReleased

    private void mokhoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mokhoaActionPerformed
        DefaultTableModel tbxtt = (DefaultTableModel) tableDocgia.getModel();
        int selected = tableDocgia.getSelectedRow();
        if (selected != -1) {
            String madg = tbxtt.getValueAt(selected, 0).toString();
            int xacnhan = JOptionPane.showConfirmDialog(null, "B???n ch???c ch???n mu???n m??? t??i kho???n ?", "X??c nh???n kh??a",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (xacnhan == 0) {
                if (accessDocGia.unlock(madg) != 0 ) {
//                    tbxtt.removeRow(selected);
//                    tbxtt.setRow
                    tenTaikhoan.setText("");
                    ngaysinh.setText("");
                    emailDocgia.setText("");
                    this.sdt.setText("");
                    JOptionPane.showMessageDialog(null, "???? m??? kh??a", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "C?? L???i", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui L??ng Ch???n Tr?????c Khi M??? kh??a", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_mokhoaActionPerformed

    private void tablexemttdgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablexemttdgMouseClicked
        DefaultTableModel tbxtt = (DefaultTableModel) tablexemttdg.getModel();
        DefaultTableModel tbx1 = (DefaultTableModel) tableDocgia.getModel();
        int selected = tablexemttdg.getSelectedRow();
        if (selected != -1) {
            quanlyttdg.setSelectedIndex(1);
            String matk = tbxtt.getValueAt(selected, 0).toString();
            String tenDg = tbxtt.getValueAt(selected, 1).toString();
            String ngaySinh = tbxtt.getValueAt(selected, 2).toString();
            String gioTinh = tbxtt.getValueAt(selected, 3).toString();
            String email = tbxtt.getValueAt(selected, 4).toString();
            String sdt = tbxtt.getValueAt(selected, 5).toString();
            if (gioTinh.equals("Nam")) {
                gioitinhnam.setSelected(true);
            } else {
                gioitinhnu.setSelected(true);
            }
            this.madg.setEditable(false);
            tenTaikhoan.setText(tenDg);
            ngaysinh.setText(ngaySinh);
            emailDocgia.setText(email);
            this.sdt.setText(sdt);
            
            int i = accessDocGia.getOneDocgia(tbxtt.getValueAt(selected, 0).toString());
            if (i == 0) {
                mokhoa.setEnabled(true);
                khoatk.setEnabled(false);
            } else {
                khoatk.setEnabled(true);
                mokhoa.setEnabled(false);
            }
            tbx1.setRowCount(0);
            tbx1.addRow(new Object[] {matk, tenDg,ngaySinh, gioTinh, email, sdt});
            quanlyttdg.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Vui L??ng Ch???n Tr?????c Khi kh??a", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_tablexemttdgMouseClicked

    private void mokhoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mokhoa1ActionPerformed
        this.loaddg();
        
    }//GEN-LAST:event_mokhoa1ActionPerformed

    private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiActionPerformed
        txt_maDMSach.setEnabled(false);
        txt_tenDMSach.setEnabled(false);
        txt_maDMSach.setText("");
        txt_tenDMSach.setText("");
        btn_LuuDMSach.setEnabled(false);
        btn_SuaDMSach.setEnabled(false);
        btn_ThemDMSach.setEnabled(true);
    }//GEN-LAST:event_btn_lammoiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        new TrangChuChinh().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
    private String cutChar(String arry){
            return arry.replaceAll("\\D+","");
        }
    
    private void sdtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sdtKeyReleased
        sdt.setText(cutChar(sdt.getText()).trim());
        sdt.setVisible(true);
    }//GEN-LAST:event_sdtKeyReleased

    private void ngaysinhFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ngaysinhFocusLost
        String dateString = ngaysinh.getText();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false); // set false ????? ki???m tra t??nh h???p l??? c???a date. VD: th??ng 2 ph???i c?? 28-29 ng??y, n??m c?? 12 th??ng,....
        try {
            df.parse(dateString); // parse dateString th??nh ki???u Date
        }
        catch (ParseException e) { // qu??ng l???i n???u dateString ko h???p l???
           JOptionPane.showMessageDialog(this, "Sai ?????nh d???ng! Nh??p l???i! (VD: 2001-01-01");
           ngaysinh.requestFocus();
           ngaysinh.setText("");
        }
    }//GEN-LAST:event_ngaysinhFocusLost

    private void madgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_madgKeyPressed
        if (evt.getKeyCode() == 10) tenTaikhoan.requestFocus();
    }//GEN-LAST:event_madgKeyPressed

    private void tenTaikhoanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tenTaikhoanKeyPressed
        if (evt.getKeyCode() == 10) sdt.requestFocus();
    }//GEN-LAST:event_tenTaikhoanKeyPressed

    private void ngaysinhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ngaysinhKeyPressed
        if (evt.getKeyCode() == 10) emailDocgia.requestFocus();
    }//GEN-LAST:event_ngaysinhKeyPressed
    private void timkiem(String query){
           TableRowSorter<DefaultTableModel> tbl = new TableRowSorter<DefaultTableModel>(defaultTableModel_DM);
           tbl_DMSach.setRowSorter(tbl);
           tbl.setRowFilter(RowFilter.regexFilter(query));

    }
    
    private void setTableData_DSPM(List<PhieuMuon> pms) {
        for(PhieuMuon pm : pms) {
            defaultTableModel_DSPM.addRow(new Object[] {pm.getMaPM(), pm.getMaTaiKhoan(), pm.getMaCanBo(),
                            pm.getNgayMuon(), pm.getSoNgayMuon(), pm.getGhiChu(), pm.getTrangThai()});
        }
    }
    
    private void setTableData_CTPM(List<ChiTietPhieuMuon> ctpms) {
        for(ChiTietPhieuMuon ctpm : ctpms) {
            defaultTableModel_CTPM.addRow(new Object[]{ctpm.getMaPM(), ctpm.getMaSach(), ctpm.getNgayThucTra(),
                            ctpm.getTinhTrangSach(), ctpm.getTienPhat()});
        }
    }
    
    private void disable_CTPM() {
        K_maPM.setEnabled(false);
        K_ngayThucTra.setEnabled(false);
        K_maSach.setEnabled(false);
        K_tinhTrangSach.setEnabled(false);
//        K_tienPhat.setEnabled(false);
        
        btnK_suaChiTiet.setEnabled(false);
        btnK_luuChiTiet.setEnabled(false);
    }
    
    private void loadData_TblDSPM() {
        tblK_DSPM.setModel(defaultTableModel_DSPM);
        defaultTableModel_DSPM.addColumn("M?? phi???u m?????n");
        defaultTableModel_DSPM.addColumn("T??n b???n ?????c");
        defaultTableModel_DSPM.addColumn("T??n c??n b???");
        defaultTableModel_DSPM.addColumn("Ng??y m?????n");
        defaultTableModel_DSPM.addColumn("S??? ng??y m?????n");
        defaultTableModel_DSPM.addColumn("Ghi ch??");
        defaultTableModel_DSPM.addColumn("Tr???ng th??i");
        
        setTableData_DSPM(pmsService.getDSPhieuMuon());
    }
    
    private void loadData_TblCTPM() {
        tblK_ChiTiet.setModel(defaultTableModel_CTPM);
        defaultTableModel_CTPM.setColumnCount(0);
        defaultTableModel_CTPM.setRowCount(0);
        defaultTableModel_CTPM.addColumn("M?? Phi???u m?????n");
        defaultTableModel_CTPM.addColumn("M?? s??ch");
        defaultTableModel_CTPM.addColumn("Ng??y th???c tr???");
        defaultTableModel_CTPM.addColumn("T??nh tr???ng s??ch");
        defaultTableModel_CTPM.addColumn("Ti???n ph???t");
        DesignTbl_CTPM();
        
        setTableData_CTPM(ctpmsService.getDSCHiTietPM(K_maPM.getText()));
    }
    
    private void DesignTbl_DSPM() {
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblK_DSPM.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(0);
        tblK_DSPM.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tblK_DSPM.setRowHeight(30);
        
        TableColumnModel columnModel_DSPM = tblK_DSPM.getColumnModel();
        columnModel_DSPM.getColumn(0).setPreferredWidth(200);
        columnModel_DSPM.getColumn(1).setPreferredWidth(225);
        columnModel_DSPM.getColumn(2).setPreferredWidth(225);
        columnModel_DSPM.getColumn(3).setPreferredWidth(200);
        columnModel_DSPM.getColumn(4).setPreferredWidth(200);
        columnModel_DSPM.getColumn(5).setPreferredWidth(200);
        columnModel_DSPM.getColumn(6).setPreferredWidth(150);
    }
    
    private void DesignTbl_CTPM() {
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblK_ChiTiet.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(0);
        tblK_ChiTiet.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tblK_ChiTiet.setRowHeight(30);
        
        TableColumnModel columnModel_DSPM = tblK_ChiTiet.getColumnModel();
        columnModel_DSPM.getColumn(0).setPreferredWidth(200);
        columnModel_DSPM.getColumn(1).setPreferredWidth(200);
        columnModel_DSPM.getColumn(2).setPreferredWidth(200);
        columnModel_DSPM.getColumn(3).setPreferredWidth(200);
        columnModel_DSPM.getColumn(4).setPreferredWidth(200);
    }
    
    private void Refresh_CTPM() {
        ((JTextField)K_ngayThucTra.getDateEditor().getUiComponent()).setText("");
//        K_tienPhat.setText("");
        K_ngayThucTra.setEnabled(false);
        K_tinhTrangSach.setEnabled(false);
//        K_tienPhat.setEnabled(false);
        btnK_suaChiTiet.setEnabled(false);
        btnK_luuChiTiet.setEnabled(false);
        K_maSach.setText("");
        loadData_TblCTPM();
    }
    public void setDSPM() {
        jTP_main.setSelectedIndex(2);
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrangChuThuThu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField H_maSach;
    private javax.swing.JTextField H_namXB;
    private javax.swing.JTextField H_nhaXB;
    private javax.swing.JTextField H_soLuongCon;
    private javax.swing.JTextField H_tacGia;
    private javax.swing.JTextField H_tenDM;
    private javax.swing.JTextField H_tenSach;
    private javax.swing.JTextField H_tenTheLoai;
    private javax.swing.JTextField H_tomTat;
    private javax.swing.JComboBox<String> Hc_maDM;
    private javax.swing.JComboBox<String> Hc_maTheLoai;
    private javax.swing.JTextField K_maPM;
    private javax.swing.JTextField K_maSach;
    private com.toedter.calendar.JDateChooser K_ngayThucTra;
    private javax.swing.JLabel K_tieuDe;
    private javax.swing.JComboBox<String> K_tinhTrangSach;
    private javax.swing.JPanel Panel_ChiTietPM;
    private javax.swing.JPanel Panel_DanhSachPM;
    private javax.swing.JButton btnH_luuSach;
    private javax.swing.JButton btnH_suaSach;
    private javax.swing.JButton btnH_themSach;
    private javax.swing.JButton btnK_lamMoi;
    private javax.swing.JButton btnK_luuChiTiet;
    private javax.swing.JButton btnK_suaChiTiet;
    private javax.swing.JButton btnK_themPM;
    private javax.swing.JButton btnK_veTrangTruoc;
    private javax.swing.JButton btn_LuuDMSach;
    private javax.swing.JButton btn_SuaDMSach;
    private javax.swing.JButton btn_ThemDMSach;
    private javax.swing.JButton btn_lamMoiSach;
    private javax.swing.JButton btn_lammoi;
    private javax.swing.JComboBox<String> cbb_chucNangThongKe;
    private javax.swing.JComboBox<String> cbb_chucNangThongKe1;
    private javax.swing.JComboBox<String> cbb_chucNangThongKe2;
    private javax.swing.JTextField emailDocgia;
    private javax.swing.ButtonGroup gioitinhbtngroup;
    private javax.swing.JRadioButton gioitinhnam;
    private javax.swing.JRadioButton gioitinhnu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPK_QuanLyPhieuMuon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jSPK_DSPM;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTPK_QuanLyPM;
    private javax.swing.JTabbedPane jTP_main;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JButton khoatk;
    private javax.swing.JTextField madg;
    private javax.swing.JButton mokhoa;
    private javax.swing.JButton mokhoa1;
    private javax.swing.JTextField ngaysinh;
    private javax.swing.JTabbedPane quanlyttdg;
    private javax.swing.JTextField sdt;
    private javax.swing.JTable tableDocgia;
    private javax.swing.JTable tableSearchSach;
    private javax.swing.JTable tabletkbandoc;
    private javax.swing.JTable tabletksach;
    private javax.swing.JTable tabletktienphat;
    private javax.swing.JTable tablexemttdg;
    private javax.swing.JTable tblH_Sach;
    private javax.swing.JTable tblK_ChiTiet;
    private javax.swing.JTable tblK_DSPM;
    private javax.swing.JTable tbl_DMSach;
    private javax.swing.JTextField tenTaikhoan;
    private javax.swing.JTextField textSearch;
    private javax.swing.JTextField textboxsearch;
    private javax.swing.JButton themmoidg;
    private javax.swing.JPanel timkiem;
    private javax.swing.JTextField txt_maDMSach;
    private javax.swing.JTextField txt_tenDMSach;
    private javax.swing.JTextField txt_timkiemDMSach;
    private javax.swing.JButton updatedg;
    // End of variables declaration//GEN-END:variables
}
