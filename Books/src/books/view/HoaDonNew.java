/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package books.view;

import books.controller.HoaDonChiTietController;
import books.controller.SanPhamChiTietController;
import books.model.HoaDon;
import books.model.KhachHang;
import books.model.HoaDonChiTiet;
import books.model.NhanVien;
import books.model.SanPhamChiTiet;
import books.service.HoaDonChiTietService;
import books.service.SanPhamChiTietService;
import books.service.HoaDonService;
import books.service.KhachHangService;
import books.service.NhanVienService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class HoaDonNew extends javax.swing.JPanel {

    DefaultTableModel model = new DefaultTableModel();
    List<HoaDon> dshoaHoaDons = new ArrayList<>();
    private SanPhamChiTietController sanPhamChiTietController;
    private HoaDonChiTietService hoaDonChiTietService;
    private HoaDonService hoaDonService;
    private KhachHangService khachHangService;
    private NhanVienService nhanVienService;
    private int indexHoaDonSelected;
    private KhachHang kh;
    private SanPhamChiTietService sanPhamChiTietService;

    public HoaDonNew() {
        initComponents();
        sanPhamChiTietService = new SanPhamChiTietService();
        hoaDonChiTietService = new HoaDonChiTietService();
        hoaDonService = new HoaDonService();
        khachHangService = new KhachHangService();
        nhanVienService = new NhanVienService();
        sanPhamChiTietController = new SanPhamChiTietController();
//        loadHoaDonChiTietToTable();
        loadSanPhamChiTietToTable();
//        model = (DefaultTableModel) tblHoaDon.getModel();
//        dshoaHoaDons = serviceHoaDon.getAll();
//        loadHoaDonLenTalbe(dshoaHoaDons);
        loadHoaDonToTable();
        loadTenKHToComboBox();
        loadTenNVToComboBox();
    }

    public void loadHoaDonToTable() {
        List<HoaDon> hoaDonList = hoaDonService.getUnpaidHoaDon();
        DefaultTableModel dtm = (DefaultTableModel) tblHoaDon.getModel();
        dtm.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        for (HoaDon hoaDon : hoaDonList) {
            Object[] rowData = new Object[9];
            rowData[0] = hoaDon.getMaHD();
            rowData[1] = hoaDon.getKhachHang().getTen();
            rowData[2] = hoaDon.getNhanVien().getTen();
            rowData[3] = hoaDon.getTenNguoiNhan();
            rowData[4] = hoaDon.getDiaChiNhan();
            rowData[5] = hoaDon.getSoDienThoai();
            rowData[6] = hoaDon.getHinhThucThanhToan();
            rowData[7] = hoaDon.getTrangThai();
            rowData[8] = hoaDon.getCreateAt();
            dtm.addRow(rowData);
        }
    }

//    public void loadHoaDonChiTietToTable() {
//        List<HoaDonChiTiet> hoaDonChiTietList = service.getAllHoaDonChiTiet();
//        DefaultTableModel dtm = (DefaultTableModel) tblHDCT.getModel();
//        dtm.setRowCount(0); // Xóa dữ liệu cũ trong bảng
//        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
//            Object[] rowData = new Object[6];
//            rowData[0] = hoaDonChiTiet.getMaSPCT();
//            rowData[1] = service.getTenSPByMaSPCT(hoaDonChiTiet.getMaSPCT()); // Lấy tên sản phẩm từ mã sản phẩm chi tiết
//            rowData[2] = hoaDonChiTiet.getSoLuong();
//            rowData[3] = hoaDonChiTiet.getDonGia();
//            rowData[4] = hoaDonChiTiet.getThanhTien();
//            rowData[5] = hoaDonChiTiet.getTrangThai();
//            dtm.addRow(rowData);
//        }
//    }
    public void loadSanPhamChiTietToTable() {
        List<SanPhamChiTiet> sanPhamChiTietList = sanPhamChiTietController.getAllSanPhamChiTiet();
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        dtm.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        for (SanPhamChiTiet sanPhamChiTiet : sanPhamChiTietList) {
            Object[] rowData = new Object[11];
            rowData[0] = sanPhamChiTiet.getMaSPCT();
            rowData[1] = sanPhamChiTiet.getTacGia();
            rowData[2] = sanPhamChiTiet.getTheLoai();
            rowData[3] = sanPhamChiTiet.getTen();
            rowData[4] = sanPhamChiTiet.getSoLuong();
            rowData[5] = sanPhamChiTiet.getGia();
            rowData[6] = sanPhamChiTiet.getNgonNgu();
            rowData[7] = sanPhamChiTiet.getSoTrang();
            rowData[8] = sanPhamChiTiet.getNhaXuatBan();
            rowData[9] = sanPhamChiTiet.getNamXuatBan();
            rowData[10] = sanPhamChiTiet.getLanTaiBan();
            dtm.addRow(rowData);
        }
    }

    public BigDecimal tinhTongTienHang(int maHD) {
        BigDecimal tongTien = BigDecimal.ZERO;

        List<HoaDonChiTiet> danhSachHDCT = hoaDonChiTietService.getHoaDonChiTietByMaHD(maHD);
        for (HoaDonChiTiet hdct : danhSachHDCT) {
            tongTien = tongTien.add(hdct.getThanhTien());
        }

        return tongTien;
    }

    private void loadHoaDonToTextBoxes(int i) {
        HoaDon hd = new HoaDon();
        int maHD = Integer.parseInt(tblHoaDon.getValueAt(i, 0).toString());
        txtMaHD.setText(String.valueOf(maHD));
        String tenKH = tblHoaDon.getValueAt(i, 1).toString();
        String tenNV = tblHoaDon.getValueAt(i, 2).toString();
        String hinhThucTT = tblHoaDon.getValueAt(i, 6).toString();
        cboTenKH.setSelectedItem(tenKH);
        cboTenNV.setSelectedItem(tenNV);
        cboHinhThucTT.setSelectedItem(hinhThucTT);

        // Tính và hiển thị tổng tiền hàng
        BigDecimal tongTienHang = tinhTongTienHang(maHD);
        txtTongTienHang.setText(tongTienHang.toString());
        txtKhachCanTra.setText(tongTienHang.toString());
    }

    private void loadTenKHToComboBox() {
        // Gọi phương thức để lấy danh sách tên khách hàng từ cơ sở dữ liệu
        List<String> tenKHList = khachHangService.getAllTenKH();

        // Nếu danh sách không rỗng, thì cập nhật dữ liệu cho ComboBox
        if (!tenKHList.isEmpty()) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(tenKHList.toArray(new String[0]));
            cboTenKH.setModel(model);
        }
    }

    private void loadTenNVToComboBox() {
        // Gọi phương thức để lấy danh sách tên nhân viên từ cơ sở dữ liệu
        List<String> tenNVList = nhanVienService.getAllTenNV();

        // Nếu danh sách không rỗng, thì cập nhật dữ liệu cho ComboBox
        if (!tenNVList.isEmpty()) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(tenNVList.toArray(new String[0]));
            cboTenNV.setModel(model);
        }
    }

    private void displayHoaDonChiTiet(int maHD) {
        // Gọi phương thức hoặc thực hiện logic để lấy danh sách hóa đơn chi tiết của hóa đơn có mã maHD
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.getHoaDonChiTietByMaHD(maHD);

        // Hiển thị danh sách hóa đơn chi tiết trong tblHoaDonChiTiet
        DefaultTableModel model = (DefaultTableModel) tblHDCT.getModel();
        model.setRowCount(0); // Xóa tất cả các dòng hiện tại trong tblHoaDonChiTiet

        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
            Object[] row = {
                hoaDonChiTiet.getSanPhamChiTiet().getMaSPCT(),
                hoaDonChiTiet.getSanPhamChiTiet().getTen(),
                hoaDonChiTiet.getSoLuong(),
                hoaDonChiTiet.getDonGia(),
                hoaDonChiTiet.getThanhTien()
            };
            model.addRow(row);
        }
    }
//    public void loadHoaDonLenTalbe(List<HoaDon> ds) {
//        model.setRowCount(0);
//        for (HoaDon hd : ds) {
//            model.addRow(new Object[]{
//                hd.getMaHD(),
//                hd.getNhanVien(),
//                hd.getTenNguoiNhan(),
//                hd.getTrangThai(),
//                hd.getCreateAt(),
//            });
//            
//        }
//        
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamChiTiet = new javax.swing.JTable();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtTongTienHang = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtKhachCanTra = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cboHinhThucTT = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtTienThua = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        cboTenKH = new javax.swing.JComboBox<>();
        cboTenNV = new javax.swing.JComboBox<>();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnThemHoaDon = new javax.swing.JButton();

        jButton2.setText("jButton2");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Tên KH", "Tên NV", "Tên người nhận", "Địa chỉ nhận", "Số điện thoại", "Hình thức thanh toán", "Trạng thái", "Ngày tạo"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jTabbedPane2.addTab("Hóa đơn", jPanel10);

        jLabel1.setText("Tìm kiếm");

        tblSanPhamChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SPCT", "Tác giả", "Thể loại", "Tên", "Số lượng", "Giá", "Ngôn ngữ", "Số trang", "Nhà xuất bản", "Năm xuất bản", "Lần tái bản"
            }
        ));
        tblSanPhamChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamChiTietMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPhamChiTiet);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(536, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(23, Short.MAX_VALUE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(228, 228, 228))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(7, Short.MAX_VALUE)))
        );

        jTabbedPane3.addTab("Danh sách sản phẩm", jPanel11);

        jLabel11.setText("Tên khách hàng:");

        jLabel21.setText("SDT:");

        jLabel22.setText("Địa chỉ:");

        jLabel23.setText("Tổng tiền hàng:");

        jLabel24.setText("-----------------------------------------------------------------");

        jTextField20.setEditable(false);

        jLabel25.setText("-----------------------------------------------------------------");

        jLabel26.setText("Khách cần trả:");

        jTextField21.setEditable(false);

        jLabel27.setText("HT thanh toán:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "ATM" }));

        jLabel28.setText("Tiền khách đưa:");

        jLabel29.setText("Tiền thừa:");

        jTextField23.setEditable(false);

        jLabel30.setText("Tiền ship:");

        jLabel31.setText("Ghi chú:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jButton4.setText("Giao hàng");

        jButton5.setText("Đã giao");

        jButton9.setText("Hoàn trả");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel26))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField21)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jLabel24)
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        jTabbedPane7.addTab("Đặt hàng", jPanel6);

        jLabel10.setText("Tên NV:");

        jLabel13.setText("Tên KH:");

        jLabel14.setText("Tổng tiền hàng:");

        txtTongTienHang.setEditable(false);

        jLabel15.setText("Khách cần trả:");

        txtKhachCanTra.setEditable(false);

        jLabel16.setText("HT thanh toán:");

        cboHinhThucTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chuyển khoản", "Tiền mặt" }));
        cboHinhThucTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHinhThucTTActionPerformed(evt);
            }
        });

        jLabel17.setText("Tiền khách đưa:");

        txtTienKhachDua.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTienKhachDuaFocusLost(evt);
            }
        });
        txtTienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaActionPerformed(evt);
            }
        });
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        jLabel18.setText("Tiền thừa:");

        txtTienThua.setEditable(false);

        jLabel19.setText("-----------------------------------------------------------------");

        jLabel20.setText("-----------------------------------------------------------------");

        jButton6.setText("Thanh toán");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel32.setText("Mã hóa đơn");

        txtMaHD.setEditable(false);

        cboTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenKHActionPerformed(evt);
            }
        });

        cboTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(61, 61, 61)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboTenNV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboTenKH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel15))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKhachCanTra)
                                    .addComponent(cboHinhThucTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTongTienHang, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14))
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(cboTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTongTienHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtKhachCanTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cboHinhThucTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Tại quầy", jPanel5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane7)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jTabbedPane7)
                .addGap(0, 0, 0))
        );

        jTabbedPane6.addTab("Đơn hàng", jPanel4);

        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SPCT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane5.setViewportView(tblHDCT);

        jButton1.setText("Xóa");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButton1))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("Giỏ hàng", jPanel7);

        btnThemHoaDon.setText("Tạo hóa đơn");
        btnThemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemHoaDon)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTabbedPane8, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGap(37, 37, 37)
                        .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(434, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hóa đơn", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 846, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamChiTietMouseClicked
        int selectedRow = tblSanPhamChiTiet.getSelectedRow();

        if (selectedRow != -1) {
            int maSPCT = Integer.parseInt(tblSanPhamChiTiet.getValueAt(selectedRow, 0).toString());
            int maHD = Integer.parseInt(txtMaHD.getText());
            if (txtMaHD.getText() == null || txtMaHD.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn đang trống !");
            }

            // Hiển thị cửa sổ thông báo để nhập số lượng
            String input = JOptionPane.showInputDialog(this, "Nhập số lượng:");
            if (input != null && !input.isEmpty()) {
                try {
                    int soLuongNhap = Integer.parseInt(input);
                    SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.getSanPhamChiTietById(maSPCT);
                    String tenSPCT = sanPhamChiTiet.getTen();
                    BigDecimal gia = sanPhamChiTiet.getGia();
                    BigDecimal thanhTien = gia.multiply(BigDecimal.valueOf(soLuongNhap));

                    // Tạo mới Hóa đơn chi tiết
                    HoaDonChiTiet hdct = new HoaDonChiTiet();
                    hdct.setHoaDon(maHD);
                    hdct.setSanPhamChiTiet(maSPCT);
                    hdct.setTenSPCT(tenSPCT);
                    hdct.setSoLuong(soLuongNhap);
                    hdct.setDonGia(gia);
                    hdct.setThanhTien(thanhTien);

                    // Gọi phương thức để thêm Hóa đơn chi tiết vào cơ sở dữ liệu
                    hoaDonChiTietService.addHoaDonChiTiet(hdct);

                    // Giảm số lượng của Sản phẩm chi tiết
                    // Gọi phương thức để giảm số lượng của Sản phẩm chi tiết trong cơ sở dữ liệu
                    sanPhamChiTietService.giamSoLuongSanPhamChiTiet(maSPCT, soLuongNhap);
                    loadSanPhamChiTietToTable();
                    loadHoaDonToTable();
                    // Cập nhật giao diện hoặc thực hiện các hành động khác nếu cần
                    // ...
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ. Vui lòng nhập lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }//GEN-LAST:event_tblSanPhamChiTietMouseClicked

    private void btnThemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHoaDonActionPerformed
        // Lấy dữ liệu từ các combo box và các thành phần khác cần thiết
        String tenKH = (String) cboTenKH.getSelectedItem();
        String tenNV = (String) cboTenNV.getSelectedItem();

        // Lấy mã khách hàng và nhân viên từ cơ sở dữ liệu
        int maKH = khachHangService.getMaKHByTen(tenKH);
        int maNV = nhanVienService.getMaNVByTen(tenNV);

        // Lấy thông tin khách hàng từ cơ sở dữ liệu
        KhachHang khachHang = khachHangService.getKhachHangByMaKH(maKH);

        // Tạo đối tượng KhachHang và NhanVien từ mã
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(maNV);

        // Tạo đối tượng HoaDon và set thông tin
        HoaDon hoaDon = new HoaDon();
        hoaDon.setTenNguoiNhan(tenKH);
        hoaDon.setDiaChiNhan("Tại quầy");

        // Set số điện thoại từ thông tin khách hàng
        if (khachHang != null) {
            hoaDon.setSoDienThoai(khachHang.getSoDienThoai());
        }

        hoaDon.setHinhThucThanhToan((String) cboHinhThucTT.getSelectedItem());
        hoaDon.setTrangThai("Chưa thanh toán");
        hoaDon.setCreateAt(LocalDateTime.now());
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(nhanVien);

        // Gọi phương thức addHoaDon từ dịch vụ HoaDonService
        hoaDonService.addHoaDon(hoaDon);
        loadHoaDonToTable();

        // Hiển thị thông báo thành công
        JOptionPane.showMessageDialog(null, "Hóa đơn đã được thêm thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnThemHoaDonActionPerformed

    private void cboHinhThucTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHinhThucTTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHinhThucTTActionPerformed

    private void cboTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTenKHActionPerformed

    private void cboTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTenNVActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow != -1) {
            loadHoaDonToTextBoxes(selectedRow);
            int maHD = Integer.parseInt(tblHoaDon.getValueAt(selectedRow, 0).toString());

            // Gọi phương thức để hiển thị hóa đơn chi tiết tương ứng
            displayHoaDonChiTiet(maHD);
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int maHD = Integer.valueOf(txtMaHD.getText());
        hoaDonService.updateHoaDonTrangThai(maHD, "Đã thanh toán");
        loadHoaDonToTable();
        JOptionPane.showMessageDialog(this, "Thanh toán thành công !");
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtTienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTienKhachDuaActionPerformed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void txtTienKhachDuaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTienKhachDuaFocusLost
        tinhTienThua();        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachDuaFocusLost
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return sdf.format(currentDate);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThemHoaDon;
    private javax.swing.JComboBox<String> cboHinhThucTT;
    private javax.swing.JComboBox<String> cboTenKH;
    private javax.swing.JComboBox<String> cboTenNV;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPhamChiTiet;
    private javax.swing.JTextField txtKhachCanTra;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTongTienHang;
    // End of variables declaration//GEN-END:variables

    private void tinhTienThua() {
        try {
            // Lấy giá trị từ ô txtTienKhachDua
            BigDecimal tienKhachDua = new BigDecimal(txtTienKhachDua.getText().trim());

            // Lấy giá trị tổng tiền hàng từ ô txtTongTienHang
            BigDecimal tongTienHang = new BigDecimal(txtTongTienHang.getText().trim());

            // Kiểm tra nếu số tiền khách đưa nhỏ hơn tổng tiền hàng
            if (tienKhachDua.compareTo(tongTienHang) < 0) {
                // Hiển thị thông báo lỗi
                JOptionPane.showMessageDialog(this, "Số tiền khách đưa không đủ.", "Lỗi", JOptionPane.ERROR_MESSAGE);

                // Xóa nội dung ô txtTienKhachDua
                txtTienKhachDua.setText("");
            } else {
                // Tính tiền thừa
                BigDecimal tienThua = tienKhachDua.subtract(tongTienHang);

                // Hiển thị giá trị tiền thừa trong ô txtTienThua
                txtTienThua.setText(tienThua.toString());
            }
        } catch (NumberFormatException ex) {
            // Xử lý khi nhập không đúng định dạng số
            JOptionPane.showMessageDialog(this, "Nhập không đúng định dạng số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

}
