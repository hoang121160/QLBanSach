package books.view;

import books.connect.DBconnect;
import books.controller.SanPhamChiTietController;
import books.model.SanPham;
import books.controller.SanPhamController;
import books.controller.TacGiaController;
import books.controller.TheLoaiController;
import books.model.TacGia;
import books.model.TheLoai;
import books.model.SanPhamChiTiet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public class SanPhamJPanel extends javax.swing.JPanel {

    /**
     * Creates new form SanPhamJPanel
     */
    private DefaultTableModel tblModel;
    private SanPhamChiTietController sanPhamChiTietController;
    private SanPhamController sanPhamController;
    private TheLoaiController theLoaiController;
    private TacGiaController tacGiaController;
    private SanPhamChiTietJFrame sanPhamChiTietJFrame;

    public SanPhamJPanel() {
        initComponents();
        tblModel = new DefaultTableModel();
        sanPhamChiTietController = new SanPhamChiTietController();
        sanPhamController = new SanPhamController();
        theLoaiController = new TheLoaiController();
        tacGiaController = new TacGiaController();
        loadSanPhamToTable();
        loadSanPhamChiTietToTable();
        rdoTacGia.setSelected(true);
        loadTacGiaToTable();

//        loadTheLoaiToTable();
//        loadTacGiaToTable();
//        List<TheLoai> theLoaiList = theLoaiController.getAllTheLoai();
//        for (TheLoai theLoai : theLoaiList) {
//            cboTheLoai.addItem(theLoai.getTen());
//        }
//        List<TacGia> tacGiaList = tacGiaController.getAllTacGia();
//        for (TacGia tacGia : tacGiaList) {
//            cboTacGia.addItem(tacGia.getTen());
//        }
//        List<SanPham> sanPhamList = sanPhamController.getAllSanPham();
//        for (SanPham sanPham : sanPhamList) {
//            cboTenSanPham.addItem(sanPham.getTen());
//        }
//        List<SanPhamChiTiet> sanPhamChiTietList = sanPhamChiTietController.getAllSanPhamChiTiet();
//        for (SanPhamChiTiet sanPhamChiTiet : sanPhamChiTietList) {
//            cboTenSPCT.addItem(sanPhamChiTiet.getTen());
//        }
        updateTheLoaiComboBox();
        updateTacGiaComboBox();
        updateSanPhamComboBox();
    }

    private void updateCboTenSanPham(int maSP) {
        // Sử dụng maSP để lấy tên sản phẩm từ cơ sở dữ liệu hoặc nơi khác
        String tenSanPham = getTenSanPhamFromDatabase(maSP); // Thay bằng cách lấy tên từ nguồn dữ liệu thích hợp

        // Đặt giá trị vào cboTenSanPham
        cboTenSanPham.setSelectedItem(tenSanPham);
    }

// Mô phỏng việc lấy tên sản phẩm từ cơ sở dữ liệu
    private String getTenSanPhamFromDatabase(int maSP) {
        List<SanPham> danhSachSanPham = sanPhamController.getAllSanPham();
        for (SanPham sp : danhSachSanPham) {
            if (sp.getMaSP() == maSP) {
                return sp.getTen();
            }
        }

        return null; // Trong trường hợp không tìm thấy
    }

    private void updateTheLoaiComboBox() {
        // Lấy danh sách thể loại từ cơ sở dữ liệu hoặc nơi khác
        List<TheLoai> danhSachTheLoai = theLoaiController.getAllTheLoai();

        // Tạo model mới chứa các chuỗi (ví dụ: tên thể loại)
        DefaultComboBoxModel<String> modelTheLoai = new DefaultComboBoxModel<>();

        // Thêm các giá trị vào model
        for (TheLoai theLoai : danhSachTheLoai) {
            modelTheLoai.addElement(theLoai.getTen()); // Thay "getTenTheLoai()" bằng phương thức thích hợp
        }

        // Gán model mới cho combobox
        cboTheLoai.setModel(modelTheLoai);
    }

    private void updateTacGiaComboBox() {
        // Lấy danh sách thể loại từ cơ sở dữ liệu hoặc nơi khác
        List<TacGia> danhSachTacGia = tacGiaController.getAllTacGia();

        // Tạo model mới chứa các chuỗi (ví dụ: tên thể loại)
        DefaultComboBoxModel<String> modelTacGia = new DefaultComboBoxModel<>();

        // Thêm các giá trị vào model
        for (TacGia tacGia : danhSachTacGia) {
            modelTacGia.addElement(tacGia.getTen()); // Thay "getTenTheLoai()" bằng phương thức thích hợp
        }

        // Gán model mới cho combobox
        cboTacGia.setModel(modelTacGia);
    }

    private void updateSanPhamComboBox() {
        // Lấy danh sách thể loại từ cơ sở dữ liệu hoặc nơi khác
        List<SanPham> danhSachSanPham = sanPhamController.getAllSanPham();

        // Tạo model mới chứa các chuỗi (ví dụ: tên thể loại)
        DefaultComboBoxModel<String> modelSanPham = new DefaultComboBoxModel<>();

        // Thêm các giá trị vào model
        for (SanPham sanPham : danhSachSanPham) {
            modelSanPham.addElement(sanPham.getTen());
        }

        // Gán model mới cho combobox
        cboTenSanPham.setModel(modelSanPham);
    }

    private void showSaveDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu tập tin Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel files", "xlsx"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Thiết lập tên file sẵn có
        String defaultFileName = "sanphamchitiet.xlsx";
        File defaultFile = new File(defaultFileName);
        fileChooser.setSelectedFile(defaultFile);

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String outputPath = fileToSave.getAbsolutePath();

            // Kiểm tra xem tên file có đuôi .xlsx hay không, nếu không, thêm vào
            if (!outputPath.toLowerCase().endsWith(".xlsx")) {
                outputPath += ".xlsx";
            }

            exportToExcel(outputPath);
            JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
        }
    }

    public void exportToExcel(String outputPath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("SPCT Data");

            // Tạo hàng tiêu đề
            XSSFRow headerRow = sheet.createRow(0);
            String[] columns = {"STT", "Mã sản phẩm", "Mã SPCT", "Tác giả", "Thể loại", "Tên", "Số lượng", "Giá", "Ngôn ngữ", "Số trang", "Nhà xuất bản", "Năm xuất bản", "Lần tái bản"};
            for (int i = 0; i < columns.length; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            try {
                Connection con = DBconnect.getConnection();
                String sql = "SELECT\n"
                        + "    sp.maSP as maSP,\n"
                        + "    spct.maSPCT,\n"
                        + "    tg.ten AS tenTacGia,\n"
                        + "    tl.ten AS tenTheLoai,\n"
                        + "    spct.ten, spct.soLuong,\n"
                        + "    spct.gia,\n"
                        + "    spct.ngonNgu,\n"
                        + "    spct.soTrang,\n"
                        + "    spct.nhaXuatBan,\n"
                        + "    spct.namXuatBan,\n"
                        + "    spct.lanTaiBan\n"
                        + "FROM\n"
                        + "    SanPhamChiTiet spct\n"
                        + "    INNER JOIN TacGia tg ON spct.MaTacGia = tg.maTacGia\n"
                        + "    INNER JOIN TheLoai tl ON spct.MaTheLoai = tl.MaTheLoai\n"
                        + "    INNER JOIN SanPham sp ON spct.maSP = sp.maSP";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                int rowIndex = 1;
                while (rs.next()) {
                    XSSFRow dataRow = sheet.createRow(rowIndex++);
                    dataRow.createCell(0).setCellValue(rowIndex - 1);
                    dataRow.createCell(1).setCellValue(rs.getString("maSP"));
                    dataRow.createCell(2).setCellValue(rs.getString("maSPCT"));
                    dataRow.createCell(3).setCellValue(rs.getString("tenTacGia"));
                    dataRow.createCell(4).setCellValue(rs.getString("tenTheLoai"));
                    dataRow.createCell(5).setCellValue(rs.getString("ten"));
                    dataRow.createCell(6).setCellValue(rs.getInt("soLuong"));
                    dataRow.createCell(7).setCellValue(rs.getDouble("gia"));
                    dataRow.createCell(8).setCellValue(rs.getString("ngonNgu"));
                    dataRow.createCell(9).setCellValue(rs.getInt("soTrang"));
                    dataRow.createCell(10).setCellValue(rs.getString("nhaXuatBan"));
                    dataRow.createCell(11).setCellValue(rs.getInt("namXuatBan"));
                    dataRow.createCell(12).setCellValue(rs.getInt("lanTaiBan"));
                }
            } catch (Exception e) {
            }

            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSanPhamChiTietToTable() {
        List<SanPhamChiTiet> sanPhamChiTietList = sanPhamChiTietController.getAllSanPhamChiTiet();
        DefaultTableModel dtm = (DefaultTableModel) tblSPCT.getModel();
        dtm.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        int stt = 1;
        for (SanPhamChiTiet sanPhamChiTiet : sanPhamChiTietList) {
            Object[] rowData = new Object[13];
            rowData[0] = stt++;
            rowData[1] = sanPhamChiTiet.getSanPham().getMaSP();
            rowData[2] = sanPhamChiTiet.getMaSPCT();
            rowData[3] = sanPhamChiTiet.getTacGia();
            rowData[4] = sanPhamChiTiet.getTheLoai();
            rowData[5] = sanPhamChiTiet.getTen();
            rowData[6] = sanPhamChiTiet.getSoLuong();
            rowData[7] = sanPhamChiTiet.getGia();
            rowData[8] = sanPhamChiTiet.getNgonNgu();
            rowData[9] = sanPhamChiTiet.getSoTrang();
            rowData[10] = sanPhamChiTiet.getNhaXuatBan();
            rowData[11] = sanPhamChiTiet.getNamXuatBan();
            rowData[12] = sanPhamChiTiet.getLanTaiBan();
            dtm.addRow(rowData);
        }
    }

    private void selectRow(int i) {
        SanPhamChiTiet sp = new SanPhamChiTiet();
        DefaultTableModel tblModel = (DefaultTableModel) tblSPCT.getModel();
        txtMaSanPham.setText(tblModel.getValueAt(i, 1).toString());
        txtMaSPCT.setText(tblModel.getValueAt(i, 2).toString());
        cboTacGia.setSelectedItem(tblModel.getValueAt(i, 3).toString());
        cboTheLoai.setSelectedItem(tblModel.getValueAt(i, 4).toString());
        cboTenSanPham.setSelectedItem(tblModel.getValueAt(i, 1).toString());
        txtTenSPCT.setText(tblModel.getValueAt(i, 5).toString());
        txtSoLg.setText(tblModel.getValueAt(i, 6).toString());
        txtGia.setText(tblModel.getValueAt(i, 7).toString());
        txtNgonNgu.setText(tblModel.getValueAt(i, 8).toString());
        txtSoTrang.setText(tblModel.getValueAt(i, 9).toString());
        txtNhaXuatBan.setText(tblModel.getValueAt(i, 10).toString());
        txtNamXuatBan.setText(tblModel.getValueAt(i, 11).toString());
        txtLanTaiBan.setText(tblModel.getValueAt(i, 12).toString());

    }

    public void loadSanPhamToTable() {
        List<SanPham> sanPham = sanPhamController.getAllSanPham();
        DefaultTableModel dtm = (DefaultTableModel) tblSanPham.getModel();
        dtm.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        int stt = 1; // Khởi tạo biến stt với giá trị ban đầu là 1
        for (SanPham sp : sanPham) {
            Object[] rowData = new Object[4];
            rowData[0] = stt++; // Gán giá trị stt vào cột đầu tiên và tăng giá trị stt sau đó
            rowData[1] = sp.getMaSP();
            rowData[2] = sp.getTen();
            rowData[3] = sp.getSoLuong();
            dtm.addRow(rowData);
        }
    }

    public void loadSanPhamToTable1() {
        List<SanPham> tacGia = sanPhamController.getAllSanPham();
        DefaultTableModel dtm = (DefaultTableModel) tblSPCT.getModel();
        dtm.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        int stt = 1; // Khởi tạo biến stt với giá trị ban đầu là 1
        for (SanPham tg : tacGia) {
            Object[] rowData = new Object[4];
            rowData[0] = stt++; // Gán giá trị stt vào cột đầu tiên và tăng giá trị stt sau đó
            rowData[1] = tg.getMaSP();
            rowData[2] = tg.getTen();
            dtm.addRow(rowData);
        }
    }

//    public void selectRow(int i, DefaultTableModel tblModel, JTextField txtMaSPCT, JComboBox<String> cboTacGia,
//            JComboBox<String> cboTheLoai, JTextField txtTen, JTextField txtGia, JTextField txtNgonNgu,
//            JTextField txtSoTrang, JTextField txtNhaXuatBan, JTextField txtNamXuatBan, JTextField txtLanTaiBan) {
//        if (tblModel == null) {
//            JOptionPane.showMessageDialog(this, "null");
//            return;
//        }
//        SanPhamChiTiet sp = new SanPhamChiTiet();
//        // Các dòng code để điền thông tin từ tblSanPhamChiTiet vào các JTextField
//        txtMaSPCT.setText(tblModel.getValueAt(i, 1).toString());
//        cboTacGia.setSelectedItem(tblModel.getValueAt(i, 2).toString());
//        cboTheLoai.setSelectedItem(tblModel.getValueAt(i, 3).toString());
//        txtTen.setText(tblModel.getValueAt(i, 4).toString());
//        txtGia.setText(tblModel.getValueAt(i, 5).toString());
//        txtNgonNgu.setText(tblModel.getValueAt(i, 6).toString());
//        txtSoTrang.setText(tblModel.getValueAt(i, 7).toString());
//        txtNhaXuatBan.setText(tblModel.getValueAt(i, 8).toString());
//        txtNamXuatBan.setText(tblModel.getValueAt(i, 9).toString());
//        txtLanTaiBan.setText(tblModel.getValueAt(i, 10).toString());
//    }
    public void loadSanPhamToTextBoxes(int i) {
        SanPham sanPham = new SanPham();
        txtMaSP.setText(tblSanPham.getValueAt(i, 1).toString());
        txtTen1.setText(tblSanPham.getValueAt(i, 2).toString());
        txtSoLuong.setText(tblSanPham.getValueAt(i, 3).toString());
    }

    private void loadTacGiaToTextBoxes(int i) {
        TacGia tacGia = new TacGia();
        txtMaThuocTinh.setText(tblThuocTinh.getValueAt(i, 0).toString());
        txtTen.setText(tblThuocTinh.getValueAt(i, 1).toString());
        txtTieuSu.setText(tblThuocTinh.getValueAt(i, 2).toString());
    }

    private void loadTheLoaiToTextBoxes(int i) {
        TheLoai theLoai = new TheLoai();
        txtMaThuocTinh.setText(tblThuocTinh.getValueAt(i, 0).toString());
        txtTen.setText(tblThuocTinh.getValueAt(i, 1).toString());
    }

    public void loadTheLoaiToTable() {
        List<TheLoai> theLoai = theLoaiController.getAllTheLoai();
        DefaultTableModel dtm = (DefaultTableModel) tblThuocTinh.getModel();
        dtm.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        for (TheLoai tg : theLoai) {
            Object[] rowData = new Object[2];
            rowData[0] = tg.getMaTheLoai();
            rowData[1] = tg.getTen();
            dtm.addRow(rowData);
        }
    }

    public void loadTacGiaToTable() {
        List<TacGia> tacGia = tacGiaController.getAllTacGia();
        DefaultTableModel dtm = (DefaultTableModel) tblThuocTinh.getModel();
        dtm.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        for (TacGia tg : tacGia) {
            Object[] rowData = new Object[3];
            rowData[0] = tg.getMaTacGia();
            rowData[1] = tg.getTen();
            rowData[2] = tg.getTieuSu();
            dtm.addRow(rowData);
        }
    }

    private void hideColumn(int columnIndex) {
        TableColumn column = tblThuocTinh.getColumnModel().getColumn(columnIndex);
        column.setMaxWidth(0);
        column.setMinWidth(0);
        column.setPreferredWidth(0);
    }

    private void showColumn(int columnIndex) {
        resetTableColumns();
        TableColumn column = tblThuocTinh.getColumnModel().getColumn(columnIndex);
        column.setMaxWidth(Integer.MAX_VALUE); // Khôi phục chiều rộng mặc định
        column.setMinWidth(15); // Đặt chiều rộng tối thiểu
        column.setPreferredWidth(75); // Đặt chiều rộng ưa thích (có thể điều chỉnh)
        column.setResizable(true); // Đảm bảo cột có thể điều chỉnh kích thước

    }

    private void resetTableColumns() {
        TableColumnModel columnModel = tblThuocTinh.getColumnModel();

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setMaxWidth(Integer.MAX_VALUE);
            column.setMinWidth(15);
            column.setResizable(true);
        }
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
        DefaultTableModel model = (DefaultTableModel) tblSPCT.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (SanPhamChiTiet sanPhamChiTiet : sanPhamChiTietList) {
            Object[] rowData = new Object[13];
            rowData[0] = stt++;
            rowData[1] = sanPhamChiTiet.getSanPham().getMaSP();
            rowData[2] = sanPhamChiTiet.getMaSPCT();
            rowData[3] = sanPhamChiTiet.getTacGia();
            rowData[4] = sanPhamChiTiet.getTheLoai();
            rowData[5] = sanPhamChiTiet.getTen();
            rowData[6] = sanPhamChiTiet.getSoLuong();
            rowData[7] = sanPhamChiTiet.getGia();
            rowData[8] = sanPhamChiTiet.getNgonNgu();
            rowData[9] = sanPhamChiTiet.getSoTrang();
            rowData[10] = sanPhamChiTiet.getNhaXuatBan();
            rowData[11] = sanPhamChiTiet.getNamXuatBan();
            rowData[12] = sanPhamChiTiet.getLanTaiBan();
            model.addRow(rowData);
        }
    }

//    public void selectRow(int i, DefaultTableModel otherTableModel) {
//        if (otherTableModel == null) {
//            JOptionPane.showMessageDialog(this, "null");
//            return;
//        }
//
//        // Các dòng code để điền thông tin từ tblSanPhamChiTiet vào các JTextField và JComboBox
//        txtMaSPCT.setText(otherTableModel.getValueAt(i, 1).toString());
//        cboTacGia.setSelectedItem(otherTableModel.getValueAt(i, 2).toString());
//        cboTheLoai.setSelectedItem(otherTableModel.getValueAt(i, 3).toString());
//        txtTenSPCT.setText(tblModel.getValueAt(i, 4).toString());
//        txtGia.setText(otherTableModel.getValueAt(i, 5).toString());
//        txtNgonNgu.setText(otherTableModel.getValueAt(i, 6).toString());
//        txtSoTrang.setText(otherTableModel.getValueAt(i, 7).toString());
//        txtNhaXuatBan.setText(otherTableModel.getValueAt(i, 8).toString());
//        txtNamXuatBan.setText(otherTableModel.getValueAt(i, 9).toString());
//        txtLanTaiBan.setText(otherTableModel.getValueAt(i, 10).toString());
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTen1 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        btnChiTietSP = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaSPCT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNgonNgu = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSoTrang = new javax.swing.JTextField();
        txtNamXuatBan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtLanTaiBan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNhaXuatBan = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cboTheLoai = new javax.swing.JComboBox<>();
        cboTacGia = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btbXoa = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        cboTenSanPham = new javax.swing.JComboBox<>();
        txtTenSPCT = new javax.swing.JTextField();
        txtSoLg = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSPCT = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane9 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtMaThuocTinh = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtTieuSu = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        rdoTacGia = new javax.swing.JRadioButton();
        rdoTheLoai = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        btnThemTG = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jTabbedPane10 = new javax.swing.JTabbedPane();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();

        jLabel10.setText("Mã sản phẩm:");

        txtMaSP.setEditable(false);

        jLabel13.setText("Tên sản phẩm:");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton2.setText("Thêm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton6.setText("Sửa");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        btnChiTietSP.setText("Chi tiết SP");
        btnChiTietSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietSPActionPerformed(evt);
            }
        });

        jButton8.setText("Làm mới");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChiTietSP)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addGap(18, 18, 18)
                .addComponent(btnChiTietSP)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addGap(10, 10, 10))
        );

        jLabel20.setText("Số lượng:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(40, 40, 40)
                                .addComponent(txtTen1))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(42, 42, 42)
                                .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(351, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTen1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        jTabbedPane6.addTab("Thông tin sản phẩm", jPanel13);

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên", "Số lượng"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        jLabel14.setText("Tìm kiếm sản phẩm");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1158, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane7.addTab("Thông tin sản phẩm", jPanel8);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTabbedPane7)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(83, 83, 83))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(174, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel1);

        jLabel1.setText("Mã SPCT:");

        txtMaSPCT.setEditable(false);

        jLabel2.setText("Tên SPCT:");

        jLabel3.setText("Giá:");

        jLabel4.setText("Ngôn ngữ:");

        jLabel5.setText("Số trang:");

        jLabel6.setText("Lần tái bản:");

        jLabel7.setText("Thể loại:");

        jLabel8.setText("Tác giả:");

        jLabel9.setText("Nhà xuất bản:");

        jLabel11.setText("Năm xuất bản:");

        cboTheLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTheLoaiActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jButton3.setText("Sửa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btbXoa.setText("Xóa");
        btbXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbXoaActionPerformed(evt);
            }
        });

        jButton5.setText("Làm mới");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton1.setText("Xuất Excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btbXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnThem)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(btbXoa)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addGap(27, 27, 27)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel18.setText("Mã sản phẩm:");

        txtMaSanPham.setEditable(false);

        jLabel19.setText("Tên sản phẩm:");

        jLabel21.setText("Số lượng:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21)
                        .addGap(44, 44, 44)
                        .addComponent(txtSoLg, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel12Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNgonNgu, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel12Layout.createSequentialGroup()
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel12Layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(40, 40, 40)))
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTenSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel18))))
                .addGap(34, 34, 34)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(42, 42, 42)
                        .addComponent(txtNamXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLanTaiBan, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboTenSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtNhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtNamXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtLanTaiBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cboTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cboTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNgonNgu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtSoLg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Thông tin sản phẩm", jPanel12);

        tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Mã SPCT", "Tác giả", "Thể loại", "Tên", "Số lượng", "Giá", "Ngôn ngữ", "Số trang", "Nhà xuất bản", "Năm xuất bản", "Lần tái bản"
            }
        ));
        tblSPCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPCTCTMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblSPCTCTMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblSPCT);

        jLabel12.setText("Tìm kiếm sản phẩm");

        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1107, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );

        jTabbedPane5.addTab("Thông tin sản phẩm", jPanel6);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane5)
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1214, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 853, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Chi tiết sản phẩm", jPanel2);

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel16.setText("Mã thuộc tính:");

        txtMaThuocTinh.setEditable(false);

        jLabel17.setText("Tên");

        jLabel15.setText("Tiểu sử:");

        txtTieuSu.setColumns(20);
        txtTieuSu.setRows(5);
        jScrollPane4.setViewportView(txtTieuSu);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaThuocTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(txtTen))
                .addGap(33, 33, 33)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(txtMaThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        rdoTacGia.setText("Tác giả");
        rdoTacGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoTacGiaMouseClicked(evt);
            }
        });
        rdoTacGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTacGiaActionPerformed(evt);
            }
        });

        rdoTheLoai.setText("Thể loại");
        rdoTheLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTheLoaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(rdoTacGia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdoTheLoai)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTacGia)
                    .addComponent(rdoTheLoai))
                .addGap(45, 45, 45))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThemTG.setText("Thêm");
        btnThemTG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTGActionPerformed(evt);
            }
        });

        jButton9.setText("Sửa");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Xóa");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemTG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnThemTG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        jTabbedPane9.addTab("Thiết lập thuộc tính", jPanel14);

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã thuộc tính", "Tên ", "Tiểu sử"
            }
        ));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblThuocTinh);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1096, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane10.addTab("Danh sách thuộc tính", jPanel15);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane10)
                    .addComponent(jTabbedPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jTabbedPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jTabbedPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thuộc tính sản phẩm", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String tenSanPham = txtTen1.getText();
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        // Kiểm tra các trường không được trống
        if (tenSanPham.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return; // Dừng việc thêm tác giả nếu trường rỗng
        }
        SanPham sanPham = new SanPham();
        // Thiết lập thông tin cho đối tượng TacGia
        sanPham.setTen(tenSanPham);
        sanPham.setSoLuong(soLuong);
        try {
            // Gọi phương thức addTacGia từ TacGiaController
            sanPhamController.addSanPham(sanPham);
            loadSanPhamToTable();
            // Thông báo thành công
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            // Sau khi thêm tác giả thành công, cập nhật combobox cboTacGia
            cboTacGia.addItem(sanPham.getTen());
            // Xóa dữ liệu trên các trường nhập liệu
            txtMaSP.setText("");
            txtTen1.setText("");
            txtSoLuong.setText("");

        } catch (Exception ex) {
            // Xử lý ngoại lệ khi thêm tác giả thất bại
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại: " + ex.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnChiTietSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietSPActionPerformed
        // TODO add your handling code here:
        int row = tblSanPham.getSelectedRow();
        if (row != -1) {
            // Lấy giá trị từ cột thứ 1 (index 1) của dòng được chọn
            int maSP = (int) tblSanPham.getValueAt(row, 1);

            // Tạo frame chi tiết và truyền mã sản phẩm
            SanPhamChiTietJFrame chiTietJFrame = new SanPhamChiTietJFrame(maSP);
            chiTietJFrame.setVisible(true);
        }
    }//GEN-LAST:event_btnChiTietSPActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        loadSanPhamToTable();
        txtMaSP.setText("");
        txtTen1.setText("");
        txtSoLuong.setText("");
        JOptionPane.showMessageDialog(this, "Sản phẩm đã dược làm mới!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked

        int selectedRow = tblSanPham.getSelectedRow();
        if (selectedRow != -1) {
            loadSanPhamToTextBoxes(selectedRow);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void cboTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTheLoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTheLoaiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

        try {
            // Lấy mã tác giả từ JComboBox
            String tenTacGia = cboTacGia.getSelectedItem().toString();

            // Lấy tên thể loại từ JComboBox
            String tenTheLoai = cboTheLoai.getSelectedItem().toString();

            // Lấy tên sản phẩm từ JComboBox
            String tenSanPham = cboTenSanPham.getSelectedItem().toString();

            // Tìm đối tượng TacGia có tên phù hợp
            List<TacGia> danhSachTacGia = tacGiaController.getAllTacGia();
            TacGia selectedTacGia = null;
            for (TacGia tacGia : danhSachTacGia) {
                if (tacGia.getTen().equals(tenTacGia)) {
                    selectedTacGia = tacGia;
                    break;
                }
            }

            // Tìm đối tượng TheLoai có tên phù hợp
            List<TheLoai> danhSachTheLoai = theLoaiController.getAllTheLoai();
            TheLoai selectedTheLoai = null;
            for (TheLoai theLoai : danhSachTheLoai) {
                if (theLoai.getTen().equals(tenTheLoai)) {
                    selectedTheLoai = theLoai;
                    break;
                }
            }

            // Tìm đối tượng SanPham có tên phù hợp
            List<SanPham> danhSachSanPham = sanPhamController.getAllSanPham();
            SanPham selectedSanPham = null;
            for (SanPham sanPham : danhSachSanPham) {
                if (sanPham.getTen().equals(tenSanPham)) {
                    selectedSanPham = sanPham;
                    break;
                }
            }

            // Kiểm tra xem đã chọn các giá trị hợp lệ chưa
            if (selectedTacGia == null || selectedTheLoai == null || selectedSanPham == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn giá trị hợp lệ cho tác giả, thể loại và sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy thông tin mới từ giao diện người dùng
            String ten = txtTenSPCT.getText();
            BigDecimal gia = new BigDecimal(txtGia.getText());
            String ngonNgu = txtNgonNgu.getText();
            int soTrang = Integer.parseInt(txtSoTrang.getText());
            String nhaXuatBan = txtNhaXuatBan.getText();
            int namXuatBan = Integer.parseInt(txtNamXuatBan.getText());
            int lanTaiBan = Integer.parseInt(txtLanTaiBan.getText());

            // Kiểm tra xem có thông tin cần thêm không
            if (ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin cần thêm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int maTacGia = selectedTacGia.getMaTacGia();
            int maTheLoai = selectedTheLoai.getMaTheLoai();
            int maSanPham = selectedSanPham.getMaSP();
            // Tạo một đối tượng SanPhamChiTiet mới
            SanPhamChiTiet newSanPhamChiTiet = new SanPhamChiTiet();
            newSanPhamChiTiet.setTacGia(new TacGia(maTacGia));
            newSanPhamChiTiet.setTheLoai(new TheLoai(maTheLoai));
            newSanPhamChiTiet.setSanPham(new SanPham(maSanPham));
            newSanPhamChiTiet.setTen(ten);
            newSanPhamChiTiet.setGia(gia);
            newSanPhamChiTiet.setNgonNgu(ngonNgu);
            newSanPhamChiTiet.setSoTrang(soTrang);
            newSanPhamChiTiet.setNhaXuatBan(nhaXuatBan);
            newSanPhamChiTiet.setNamXuatBan(namXuatBan);
            newSanPhamChiTiet.setLanTaiBan(lanTaiBan);

            // Thực hiện thêm mới
            sanPhamChiTietController.addSanPhamChiTiet(newSanPhamChiTiet);

            // Hiển thị thông báo thành công
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm chi tiết thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Các hành động bổ sung nếu cần, như làm mới hiển thị
            loadSanPhamChiTietToTable();
            txtMaSanPham.setText("");
            txtMaSPCT.setText("");
            txtTenSPCT.setText("");
            txtGia.setText("");
            txtNgonNgu.setText("");
            txtSoTrang.setText("");
            txtNhaXuatBan.setText("");
            txtNamXuatBan.setText("");
            txtLanTaiBan.setText("");

        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giá trị hợp lệ cho tác giả, thể loại và sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Định dạng giá trị không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btbXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbXoaActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tblSPCT.getModel();
        int selectedRow = tblSPCT.getSelectedRow();

        if (selectedRow >= 0) {
            int maSPCT = (int) tblSPCT.getValueAt(selectedRow, 2);
            sanPhamChiTietController.deleteSanPhamChiTiet(maSPCT);
            loadSanPhamChiTietToTable();
            txtMaSanPham.setText("");
            txtMaSPCT.setText("");
            txtTenSPCT.setText("");
            txtGia.setText("");
            txtNgonNgu.setText("");
            txtSoTrang.setText("");
            txtNhaXuatBan.setText("");
            txtNamXuatBan.setText("");
            txtLanTaiBan.setText("");
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!");
        }
    }//GEN-LAST:event_btbXoaActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        loadSanPhamChiTietToTable();
        JOptionPane.showMessageDialog(this, "Sản phẩm đã dược làm mới!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tblSPCTCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCTCTMouseClicked
//        if (evt.getClickCount() == 1) {
//            int row = tblSPCT.getSelectedRow();
//            if (row != -1) {
//                // Lấy giá trị từ cột thứ 1 (index 1) của dòng được chọn
//                int maSP = (int) tblSPCT.getValueAt(row, 1);
//
//                // Tạo frame chi tiết và truyền mã sản phẩm
//                SanPhamChiTietJFrame chiTietJFrame = new SanPhamChiTietJFrame(maSP);
//                chiTietJFrame.setVisible(true);
//            }
//        }
        int selectedRow = tblSPCT.getSelectedRow();
        if (selectedRow != -1) {
            selectRow(selectedRow);
            // Lấy mã sản phẩm từ cột thứ 2 (index 1) của dòng được chọn
            int maSP = Integer.parseInt(tblSPCT.getValueAt(selectedRow, 1).toString());

            // Cập nhật cboTenSanPham dựa trên mã sản phẩm
            updateCboTenSanPham(maSP);
        }
    }//GEN-LAST:event_tblSPCTCTMouseClicked

    private void tblSPCTCTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCTCTMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSPCTCTMouseEntered

    private void rdoTacGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTacGiaMouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_rdoTacGiaMouseClicked

    private void rdoTacGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTacGiaActionPerformed
        loadTacGiaToTable();
        if (rdoTacGia.isSelected()) {
            rdoTheLoai.setSelected(false);
            showColumn(2);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_rdoTacGiaActionPerformed

    private void rdoTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTheLoaiActionPerformed
        loadTheLoaiToTable();

        if (rdoTheLoai.isSelected()) {
            rdoTacGia.setSelected(false);
            hideColumn(2);
        } else {
            showColumn(2);
        }

    }//GEN-LAST:event_rdoTheLoaiActionPerformed

    private void btnThemTGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTGActionPerformed

        if (rdoTacGia.isSelected()) {
            String tenTacGia = txtTen.getText();
            String tieuSu = txtTieuSu.getText();
            // Kiểm tra các trường không được trống
            if (tenTacGia.isEmpty() || tieuSu.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin tác giả!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; // Dừng việc thêm tác giả nếu trường rỗng
            }
            TacGia tacGia = new TacGia();
            // Thiết lập thông tin cho đối tượng TacGia
            tacGia.setTen(tenTacGia);
            tacGia.setTieuSu(tieuSu);
            try {
                // Gọi phương thức addTacGia từ TacGiaController
                tacGiaController.addTacGia(tacGia);
                // Thông báo thành công
                JOptionPane.showMessageDialog(this, "Thêm tác giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // Sau khi thêm tác giả thành công, cập nhật combobox cboTacGia
                cboTacGia.addItem(tacGia.getTen());
                // Xóa dữ liệu trên các trường nhập liệu
                txtMaThuocTinh.setText("");
                txtTen.setText("");
                txtTieuSu.setText("");
                loadTacGiaToTable();
            } catch (Exception ex) {
                // Xử lý ngoại lệ khi thêm tác giả thất bại
                JOptionPane.showMessageDialog(this, "Thêm tác giả thất bại: " + ex.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (rdoTheLoai.isSelected()) {
            String tenTheLoai = txtTen.getText();
            // Kiểm tra các trường không được trống
            if (tenTheLoai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin thể loại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; // Dừng việc thêm thể loại nếu trường rỗng
            }
            TheLoai theLoai = new TheLoai();
            // Thiết lập thông tin cho đối tượng TheLoai
            theLoai.setTen(tenTheLoai);
            try {
                theLoaiController.addTheLoai(theLoai);
                // Thông báo thành công
                JOptionPane.showMessageDialog(this, "Thêm thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // Sau khi thêm tác giả thành công, cập nhật combobox cboTacGia
                cboTheLoai.addItem(theLoai.getTen());
                // Xóa dữ liệu trên các trường nhập liệu
                txtMaThuocTinh.setText("");
                txtTen.setText("");
                txtTieuSu.setText("");
                loadTheLoaiToTable();
            } catch (Exception ex) {
                // Xử lý ngoại lệ khi thêm tác giả thất bại
                JOptionPane.showMessageDialog(this, "Thêm tác giả thất bại: " + ex.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnThemTGActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (rdoTacGia.isSelected()) {
            try {
                // Lấy thông tin từ giao diện người dùng
                int maTacGia = Integer.parseInt(txtMaThuocTinh.getText());
                String tenTacGia = txtTen.getText();
                String tieuSu = txtTieuSu.getText();

                // Tạo đối tượng TacGia với thông tin cập nhật
                TacGia tacGiaToUpdate = new TacGia();
                tacGiaToUpdate.setMaTacGia(maTacGia);
                tacGiaToUpdate.setTen(tenTacGia);
                tacGiaToUpdate.setTieuSu(tieuSu);

                // Thực hiện cập nhật thông tin trong cơ sở dữ liệu
                tacGiaController.updateTacGia(tacGiaToUpdate);
                loadTacGiaToTable();
                txtMaThuocTinh.setText("");
                txtTen.setText("");
                txtTieuSu.setText("");
                // Hiển thị thông báo thành công
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin tác giả thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                // Các bước bổ sung sau khi cập nhật, như làm mới hiển thị, load lại dữ liệu, v.v.
                // ...
            } catch (NumberFormatException ex) {
                // Xử lý nếu có lỗi định dạng số
                JOptionPane.showMessageDialog(this, "Vui lòng nhập định dạng số hợp lệ cho mã tác giả", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Xử lý các lỗi khác nếu có
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi trong quá trình cập nhật", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        }
        if (rdoTheLoai.isSelected()) {
            try {
                // Lấy thông tin từ giao diện người dùng
                int maTheLoai = Integer.parseInt(txtMaThuocTinh.getText());
                String tenTheLoai = txtTen.getText();

                // Tạo đối tượng TacGia với thông tin cập nhật
                TheLoai theLoaiToUpdate = new TheLoai();
                theLoaiToUpdate.setMaTheLoai(maTheLoai);
                theLoaiToUpdate.setTen(tenTheLoai);
                // Thực hiện cập nhật thông tin trong cơ sở dữ liệu
                theLoaiController.updateTheLoai(theLoaiToUpdate);
                loadTheLoaiToTable();
                txtMaThuocTinh.setText("");
                txtTen.setText("");
                txtTieuSu.setText("");
                // Hiển thị thông báo thành công
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thể loại thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                // Các bước bổ sung sau khi cập nhật, như làm mới hiển thị, load lại dữ liệu, v.v.
                // ...
            } catch (NumberFormatException ex) {
                // Xử lý nếu có lỗi định dạng số
                JOptionPane.showMessageDialog(this, "Vui lòng nhập định dạng số hợp lệ cho mã thể loại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Xử lý các lỗi khác nếu có
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi trong quá trình cập nhật", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (rdoTacGia.isSelected()) {
            int selectedRow = tblThuocTinh.getSelectedRow();

            if (selectedRow == -1) {
                // Không có hàng nào được chọn
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                int maTacGia = (int) tblThuocTinh.getValueAt(selectedRow, 0);

                tacGiaController.deleteTacGia(maTacGia);
                txtTen.setText("");
                txtTieuSu.setText("");
                loadTacGiaToTable();
                if (selectedRow > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa tác giả thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa tác giả thất bại");
                }
            }
        }
        if (rdoTheLoai.isSelected()) {
            int selectedRow = tblThuocTinh.getSelectedRow();

            if (selectedRow == -1) {
                // Không có hàng nào được chọn
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                int maTheLoai = (int) tblThuocTinh.getValueAt(selectedRow, 0);

                theLoaiController.deleteTheLoai(maTheLoai);
                txtTen.setText("");
                txtTieuSu.setText("");
                loadTheLoaiToTable();
                if (selectedRow > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thể loại thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thể loại thất bại!");
                }
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        int selectedRow = tblThuocTinh.getSelectedRow();
        if (rdoTacGia.isSelected()) {
            if (selectedRow != -1) {
                loadTacGiaToTextBoxes(selectedRow);
            }
        }
        if (rdoTheLoai.isSelected()) {
            txtTieuSu.setText("");
            if (selectedRow != -1) {
                loadTheLoaiToTextBoxes(selectedRow);
            }
        }
    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            // Lấy ID của sản phẩm cần cập nhật
            int maSPToUpdate = Integer.parseInt(txtMaSP.getText());

            // Kiểm tra xem ID có hợp lệ không
            if (maSPToUpdate <= 0) {
                JOptionPane.showMessageDialog(this, "ID sản phẩm không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Dừng xử lý nếu ID không hợp lệ
            }

            // Lấy thông tin mới từ giao diện người dùng
            String tenMoi = txtTen1.getText();
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            // Kiểm tra xem có thông tin cần sửa không
            if (tenMoi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin cần sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo một đối tượng SanPham mới với thông tin cập nhật
            SanPham sanPhamToUpdate = new SanPham();
            sanPhamToUpdate.setMaSP(maSPToUpdate);
            sanPhamToUpdate.setTen(tenMoi);
            sanPhamToUpdate.setSoLuong(soLuong);

            // Thực hiện cập nhật
            sanPhamController.updateSanPham(sanPhamToUpdate);
            loadSanPhamToTable();
            // Hiển thị thông báo thành công
            JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Các hành động bổ sung nếu cần, như làm mới hiển thị
            txtMaSP.setText("");
            txtTen1.setText("");
            txtSoLuong.setText("");
        } catch (NumberFormatException e) {
            // Xử lý trường hợp ID không phải là số hợp lệ
            JOptionPane.showMessageDialog(this, "Định dạng ID sản phẩm không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // Lấy ID của sản phẩm chi tiết cần cập nhật
            int maSPCTToUpdate = Integer.parseInt(txtMaSPCT.getText());

            // Kiểm tra xem ID có hợp lệ không
            if (maSPCTToUpdate <= 0) {
                JOptionPane.showMessageDialog(this, "ID sản phẩm chi tiết không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Dừng xử lý nếu ID không hợp lệ
            }

            // Lấy thông tin mới từ giao diện người dùng
            String tenMoi = txtTenSPCT.getText();
            int soLuongMoi = Integer.parseInt(txtSoLg.getText());
            BigDecimal giaMoi = new BigDecimal(txtGia.getText());
            String ngonNguMoi = txtNgonNgu.getText();
            int soTrangMoi = Integer.parseInt(txtSoTrang.getText());
            String nhaXuatBanMoi = txtNhaXuatBan.getText();
            int namXuatBanMoi = Integer.parseInt(txtNamXuatBan.getText());
            int lanTaiBanMoi = Integer.parseInt(txtLanTaiBan.getText());

            // Kiểm tra xem có thông tin cần sửa không
            if (tenMoi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin cần sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo một đối tượng SanPhamChiTiet mới với thông tin cập nhật
            SanPhamChiTiet sanPhamChiTietToUpdate = new SanPhamChiTiet();
            sanPhamChiTietToUpdate.setMaSPCT(maSPCTToUpdate);
            sanPhamChiTietToUpdate.setTen(tenMoi);
            sanPhamChiTietToUpdate.setSoLuong(soLuongMoi);
            sanPhamChiTietToUpdate.setGia(giaMoi);
            sanPhamChiTietToUpdate.setNgonNgu(ngonNguMoi);
            sanPhamChiTietToUpdate.setSoTrang(soTrangMoi);
            sanPhamChiTietToUpdate.setNhaXuatBan(nhaXuatBanMoi);
            sanPhamChiTietToUpdate.setNamXuatBan(namXuatBanMoi);
            sanPhamChiTietToUpdate.setLanTaiBan(lanTaiBanMoi);

            // Thực hiện cập nhật
            sanPhamChiTietController.updateSanPhamChiTiet(sanPhamChiTietToUpdate);

            // Hiển thị thông báo thành công
            JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm chi tiết thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Các hành động bổ sung nếu cần, như làm mới hiển thị
            loadSanPhamChiTietToTable();
            txtMaSanPham.setText("");
            txtMaSPCT.setText("");
            txtTenSPCT.setText("");
            txtGia.setText("");
            txtNgonNgu.setText("");
            txtSoTrang.setText("");
            txtNhaXuatBan.setText("");
            txtNamXuatBan.setText("");
            txtLanTaiBan.setText("");

        } catch (NumberFormatException e) {
            // Xử lý trường hợp ID hoặc các giá trị không phải là số hợp lệ
            JOptionPane.showMessageDialog(this, "Định dạng ID hoặc giá trị không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        showSaveDialog();
    }//GEN-LAST:event_jButton1ActionPerformed
    private boolean isInputComplete = true;
    private Timer searchTimer;
    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
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

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbXoa;
    private javax.swing.JButton btnChiTietSP;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemTG;
    private javax.swing.JComboBox<String> cboTacGia;
    private javax.swing.JComboBox<String> cboTenSanPham;
    private javax.swing.JComboBox<String> cboTheLoai;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane10;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane9;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JRadioButton rdoTacGia;
    private javax.swing.JRadioButton rdoTheLoai;
    private javax.swing.JTable tblSPCT;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtLanTaiBan;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtMaSPCT;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtMaThuocTinh;
    private javax.swing.JTextField txtNamXuatBan;
    private javax.swing.JTextField txtNgonNgu;
    private javax.swing.JTextField txtNhaXuatBan;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLg;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoTrang;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTen1;
    private javax.swing.JTextField txtTenSPCT;
    private javax.swing.JTextArea txtTieuSu;
    // End of variables declaration//GEN-END:variables

}
