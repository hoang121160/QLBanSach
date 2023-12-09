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
import java.util.Timer;
import java.util.TimerTask;
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
                hoaDonChiTiet.getMaHDCT(),
                hoaDonChiTiet.getSanPhamChiTiet().getMaSPCT(),
                hoaDonChiTiet.getSanPhamChiTiet().getTen(),
                hoaDonChiTiet.getSoLuong(),
                hoaDonChiTiet.getDonGia(),
                hoaDonChiTiet.getThanhTien()
            };
            model.addRow(row);
        }
    }

    private void clearHoaDonChiTietTable() {
        DefaultTableModel model = (DefaultTableModel) tblHDCT.getModel();
        model.setRowCount(0);
    }
    
    private void searchSanPhamChiTiet(String searchText) {
        // Thực hiện tìm kiếm dựa trên nội dung của ô nhập liệu
        List<SanPhamChiTiet> sanPhamChiTietList = sanPhamChiTietController.getSanPhamChiTietByTen(searchText);

        // Cập nhật bảng với kết quả tìm kiếm
        updateSanPhamChiTietTable(sanPhamChiTietList);
        if (sanPhamChiTietList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm chi tiết với tên " + searchText, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
     private void updateSanPhamChiTietTable(List<SanPhamChiTiet> sanPhamChiTietList) {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        model.setRowCount(0);
        for (SanPhamChiTiet sanPhamChiTiet : sanPhamChiTietList) {
            Object[] rowData = new Object[12];
            rowData[0] = sanPhamChiTiet.getSanPham().getMaSP();
            rowData[1] = sanPhamChiTiet.getMaSPCT();
            rowData[2] = sanPhamChiTiet.getTacGia();
            rowData[3] = sanPhamChiTiet.getTheLoai();
            rowData[4] = sanPhamChiTiet.getTen();
            rowData[5] = sanPhamChiTiet.getSoLuong();
            rowData[6] = sanPhamChiTiet.getGia();
            rowData[7] = sanPhamChiTiet.getNgonNgu();
            rowData[8] = sanPhamChiTiet.getSoTrang();
            rowData[9] = sanPhamChiTiet.getNhaXuatBan();
            rowData[10] = sanPhamChiTiet.getNamXuatBan();
            rowData[11] = sanPhamChiTiet.getLanTaiBan();
            model.addRow(rowData);
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
        btnXoaHoaDon = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamChiTiet = new javax.swing.JTable();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
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
        btnThanhToan = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        cboTenKH = new javax.swing.JComboBox<>();
        cboTenNV = new javax.swing.JComboBox<>();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        btnXoaHDCT = new javax.swing.JButton();
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
                "Mã hóa đơn", "Tên KH", "Tên NV", "Tên KH", "Địa chỉ nhận", "SDT", "Hình thức TT", "Trạng thái", "Ngày tạo"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblHoaDon);

        btnXoaHoaDon.setText("Xóa");
        btnXoaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaHoaDon)
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(btnXoaHoaDon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Hóa đơn", jPanel10);

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

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
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(228, 228, 228))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(7, Short.MAX_VALUE)))
        );

        jTabbedPane3.addTab("Danh sách sản phẩm", jPanel11);

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

        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
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
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Hóa đơn", jPanel5);

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
                "Mã HDCT", "Mã SPCT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane5.setViewportView(tblHDCT);

        btnXoaHDCT.setText("Xóa");
        btnXoaHDCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHDCTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaHDCT)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(btnXoaHDCT))
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
                                .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGap(37, 37, 37)
                        .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(427, Short.MAX_VALUE))
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
            String maHDText = txtMaHD.getText();
            int maHD = 0;
            if (maHDText == null || maHDText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn đang trống!");
                return;
            } else {
                maHD = Integer.parseInt(maHDText);
            }

            // Hiển thị cửa sổ thông báo để nhập số lượng
            String input = JOptionPane.showInputDialog(this, "Nhập số lượng:");
            if (input != null && !input.isEmpty()) {
                try {
                    int soLuongNhap = Integer.parseInt(input);

                    HoaDonChiTiet existingHDCT = hoaDonChiTietService.getHoaDonChiTietByMaHDAndMaSPCT(maHD, maSPCT);
                    if (existingHDCT != null) {
                        // Nếu đã tồn tại, cập nhật số lượng
                        int soLuongCu = existingHDCT.getSoLuong();
                        int soLuongMoi = soLuongCu + soLuongNhap;
                        existingHDCT.setSoLuong(soLuongMoi);
                        existingHDCT.setThanhTien(existingHDCT.getDonGia().multiply(BigDecimal.valueOf(soLuongMoi)));
                        hoaDonChiTietService.updateHoaDonChiTiet(existingHDCT);
                        loadSanPhamChiTietToTable();
                        loadHoaDonToTable();
                        displayHoaDonChiTiet(maHD);
                    } else {
                        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.getSanPhamChiTietById(maSPCT);
                        String tenSPCT = sanPhamChiTiet.getTen();
                        BigDecimal gia = sanPhamChiTiet.getGia();
                        BigDecimal thanhTien = gia.multiply(BigDecimal.valueOf(soLuongNhap));
                        int soLuongTonKho = (int) tblSanPhamChiTiet.getValueAt(selectedRow, 4);
                        if (soLuongNhap > soLuongTonKho) {
                            JOptionPane.showMessageDialog(this, "Số lượng nhập vượt quá số lượng tồn kho.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

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
                        displayHoaDonChiTiet(maHD);
                    }

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ. Vui lòng nhập lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }//GEN-LAST:event_tblSanPhamChiTietMouseClicked

    private void btnThemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHoaDonActionPerformed
        // Lấy dữ liệu từ các combo box và các thành phần khác cần thiết
        // Hiển thị hộp thoại xác nhận
        int confirmResult = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc chắn muốn thêm hóa đơn?",
                "Xác nhận thêm hóa đơn",
                JOptionPane.YES_NO_OPTION
        );

// Kiểm tra kết quả xác nhận
        if (confirmResult == JOptionPane.YES_OPTION) {
            // Người dùng đã chọn "Yes", thực hiện thêm hóa đơn
            String tenKH = (String) cboTenKH.getSelectedItem();
            String tenNV = (String) cboTenNV.getSelectedItem();
            int maKH = khachHangService.getMaKHByTen(tenKH);
            int maNV = nhanVienService.getMaNVByTen(tenNV);
            KhachHang khachHang = khachHangService.getKhachHangByMaKH(maKH);

            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNV(maNV);

            HoaDon hoaDon = new HoaDon();
            hoaDon.setTenNguoiNhan(tenKH);
            hoaDon.setDiaChiNhan("Tại quầy");

            if (khachHang != null) {
                hoaDon.setSoDienThoai(khachHang.getSoDienThoai());
            }

            hoaDon.setHinhThucThanhToan((String) cboHinhThucTT.getSelectedItem());
            hoaDon.setTrangThai("Chưa thanh toán");
            hoaDon.setCreateAt(LocalDateTime.now());
            hoaDon.setKhachHang(khachHang);
            hoaDon.setNhanVien(nhanVien);

            hoaDonService.addHoaDon(hoaDon);
            loadHoaDonToTable();

            JOptionPane.showMessageDialog(null, "Hóa đơn đã được thêm thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Người dùng đã chọn "No" hoặc đóng hộp thoại, không thực hiện thêm hóa đơn
            JOptionPane.showMessageDialog(null, "Thêm hóa đơn đã bị hủy.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnThemHoaDonActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow != -1) {
            loadHoaDonToTextBoxes(selectedRow);
            int maHD = Integer.parseInt(tblHoaDon.getValueAt(selectedRow, 0).toString());

            // Gọi phương thức để hiển thị hóa đơn chi tiết tương ứng
            displayHoaDonChiTiet(maHD);
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void cboTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTenNVActionPerformed

    private void cboTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTenKHActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        try {
            // Lấy giá trị từ ô nhập liệu txtMaHD
            String maHDString = txtMaHD.getText();

            // Chuyển đổi giá trị từ JTextField sang BigDecimal
            BigDecimal tienKhachDua = new BigDecimal(txtTienKhachDua.getText());
            BigDecimal tongTienHang = new BigDecimal(txtTongTienHang.getText());

            // Kiểm tra xem số tiền khách đưa có đủ để thanh toán không
            if (tienKhachDua.compareTo(tongTienHang) < 0) {
                JOptionPane.showMessageDialog(this, "Số tiền không đủ thanh toán !");
                return;
            }

            // Kiểm tra xem ô nhập liệu có trống không
            if (maHDString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn !");
                // Dừng hành động nếu txtMaHD trống
                return;
            }

            // Parse mã hóa đơn thành kiểu số nguyên
            int maHD = Integer.valueOf(maHDString);
            String hinhThucThanhToanMoi = cboHinhThucTT.getSelectedItem().toString();
            // Hiển thị hộp thoại xác nhận
            int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thanh toán?", "Xác nhận thanh toán", JOptionPane.YES_NO_OPTION);

            if (confirmResult == JOptionPane.YES_OPTION) {
                // Người dùng đã chọn "Yes", thực hiện thanh toán
                hoaDonService.updateHoaDonTrangThai(maHD, "Đã thanh toán",hinhThucThanhToanMoi);
                clearHoaDonChiTietTable();
                loadHoaDonToTable();
                JOptionPane.showMessageDialog(this, "Thanh toán thành công !");
            } else {
                // Người dùng đã chọn "No" hoặc đóng hộp thoại
                JOptionPane.showMessageDialog(this, "Thanh toán đã bị hủy.");
            }

        } catch (NumberFormatException | ArithmeticException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void txtTienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachDuaActionPerformed

    private void txtTienKhachDuaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTienKhachDuaFocusLost
        tinhTienThua();        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachDuaFocusLost

    private void cboHinhThucTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHinhThucTTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHinhThucTTActionPerformed

    private void btnXoaHDCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHDCTActionPerformed
        // TODO add your handling code here:
        try {
            DefaultTableModel dtm = (DefaultTableModel) tblHDCT.getModel();
            int selectedRow = tblHDCT.getSelectedRow();
            int maHD = Integer.parseInt(txtMaHD.getText());
            if (selectedRow >= 0) {
                int maHDCT = (int) tblHDCT.getValueAt(selectedRow, 0);
                int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa HDCT này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    hoaDonChiTietService.deleteHDCT(maHDCT);
                    displayHoaDonChiTiet(maHD);
                    JOptionPane.showMessageDialog(this, "Xóa HDCT thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập định dạng số hợp lệ cho mã HDCT", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi trong quá trình xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaHDCTActionPerformed

    private void btnXoaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHoaDonActionPerformed
        // TODO add your handling code here:
        try {
            DefaultTableModel dtm = (DefaultTableModel) tblHDCT.getModel();
            int selectedRow = tblHoaDon.getSelectedRow();
            if (selectedRow >= 0) {
                int maHD = (int) tblHoaDon.getValueAt(selectedRow, 0);
                int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa hóa đơn này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    hoaDonService.deleteHoaDon(maHD);
                    loadHoaDonToTable();
                    clearHoaDonChiTietTable();
                    txtMaHD.setText("");
                    txtTongTienHang.setText("");
                    txtKhachCanTra.setText("");
                    txtTienKhachDua.setText("");
//                    displayHoaDonChiTiet(maHD);
                    JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập định dạng số hợp lệ cho mã hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi trong quá trình xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaHoaDonActionPerformed
    private boolean isInputComplete = true;
    private Timer searchTimer;
    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String searchText = txtSearch.getText().trim();

        // Kiểm tra xem văn bản tìm kiếm có trống không
        if (!searchText.isEmpty()) {
            isInputComplete = false;

            // Hủy bỏ timer hiện tại (nếu có)
            if (searchTimer != null) {
                searchTimer.cancel();
            }

            // Khởi tạo và chạy timer với khoảng thời gian trì hoãn (ví dụ: 1000ms)
            searchTimer = new Timer();
            searchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!isInputComplete) {
                        // Gọi hàm tìm kiếm khi đã chắc chắn rằng người dùng không nhập tiếp
                        searchSanPhamChiTiet(searchText);
                    }
                }
            }, 500); // Thời gian trì hoãn: 1000ms (1 giây)
        } else {
            isInputComplete = true; // Người dùng đã nhập xong
            loadSanPhamChiTietToTable();
        }
    }//GEN-LAST:event_txtSearchKeyReleased
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return sdf.format(currentDate);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemHoaDon;
    private javax.swing.JButton btnXoaHDCT;
    private javax.swing.JButton btnXoaHoaDon;
    private javax.swing.JComboBox<String> cboHinhThucTT;
    private javax.swing.JComboBox<String> cboTenKH;
    private javax.swing.JComboBox<String> cboTenNV;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPhamChiTiet;
    private javax.swing.JTextField txtKhachCanTra;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtSearch;
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
