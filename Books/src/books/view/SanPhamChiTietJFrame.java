/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package books.view;

import books.controller.SanPhamChiTietController;
import books.controller.TacGiaController;
import books.controller.TheLoaiController;
import books.model.SanPham;
import books.model.SanPhamChiTiet;
import books.model.TacGia;
import books.model.TheLoai;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class SanPhamChiTietJFrame extends javax.swing.JFrame {

    private DefaultTableModel tblModel;

    private JTextField txtMaSPCT;
    private JComboBox<String> cboTacGia;
    private JComboBox<String> cboTheLoai;
    private JTextField txtTen;
    private JTextField txtGia;
    private JTextField txtNgonNgu;
    private JTextField txtSoTrang;
    private JTextField txtNhaXuatBan;
    private JTextField txtNamXuatBan;
    private JTextField txtLanTaiBan;
    private SanPhamChiTietController sanPhamChiTietController;
    private SanPhamJPanel sanPhamJPanel;
    private TheLoaiController theLoaiController;
    private TacGiaController tacGiaController;
    private List<SanPhamChiTiet> sanPhamChiTietList;

    public SanPhamChiTietJFrame(int maSP) {
        sanPhamChiTietList = new ArrayList<>();
        sanPhamChiTietController = new SanPhamChiTietController();
        theLoaiController = new TheLoaiController();
        tacGiaController = new TacGiaController();
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadSanPhamChiTietToTable(maSP);
        setSanPhamJPanel(new SanPhamJPanel());
        List<TheLoai> theLoaiList = theLoaiController.getAllTheLoai();
        for (TheLoai theLoai : theLoaiList) {
            cboTL.addItem(theLoai.getTen());
        }
        List<TacGia> tacGiaList = tacGiaController.getAllTacGia();
        for (TacGia tacGia : tacGiaList) {
            cboTG.addItem(tacGia.getTen());
        }

    }

    public void setSanPhamJPanel(SanPhamJPanel sanPhamJPanel) {
        this.sanPhamJPanel = sanPhamJPanel;
    }

    public void loadSanPhamChiTietToTable(int maSP) {
        // Lấy dữ liệu chi tiết sản phẩm từ maSP
        List<SanPhamChiTiet> sanPhamChiTietList = sanPhamChiTietController.getSanPhamChiTietByMaSP(maSP);

        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        dtm.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        int stt = 1;
        for (SanPhamChiTiet sanPhamChiTiet : sanPhamChiTietList) {
            Object[] rowData = new Object[11];
            rowData[0] = stt++;
            rowData[1] = sanPhamChiTiet.getMaSPCT();
            rowData[2] = sanPhamChiTiet.getTacGia();
            rowData[3] = sanPhamChiTiet.getTheLoai();
            rowData[4] = sanPhamChiTiet.getTen();
            rowData[5] = sanPhamChiTiet.getGia();
            rowData[6] = sanPhamChiTiet.getNgonNgu();
            rowData[7] = sanPhamChiTiet.getSoTrang();
            rowData[8] = sanPhamChiTiet.getNhaXuatBan();
            rowData[9] = sanPhamChiTiet.getNamXuatBan();
            rowData[10] = sanPhamChiTiet.getLanTaiBan();
            dtm.addRow(rowData);
        }
    }

//    private void updateTable(List<SanPhamChiTiet> data) {
//        System.out.println("Updating Table with Data: " + data);
//        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamChiTiet.getModel();
//        dtm.setRowCount(0);
//
//        int stt = 1;
//        for (SanPhamChiTiet sanPhamChiTiet : data) {
//            Object[] rowData = new Object[11];
//            rowData[0] = stt++;
//            rowData[1] = sanPhamChiTiet.getMaSPCT();
//            rowData[2] = sanPhamChiTiet.getTacGia();
//            rowData[3] = sanPhamChiTiet.getTheLoai();
//            rowData[4] = sanPhamChiTiet.getTen();
//            rowData[5] = sanPhamChiTiet.getGia();
//            rowData[6] = sanPhamChiTiet.getNgonNgu();
//            rowData[7] = sanPhamChiTiet.getSoTrang();
//            rowData[8] = sanPhamChiTiet.getNhaXuatBan();
//            rowData[9] = sanPhamChiTiet.getNamXuatBan();
//            rowData[10] = sanPhamChiTiet.getLanTaiBan();
//            dtm.addRow(rowData);
//        }
//    }

    private void searchSanPhamChiTiet(int searchText) {
        // Thực hiện tìm kiếm dựa trên nội dung của ô nhập liệu
        List<SanPhamChiTiet> sanPhamChiTietList = sanPhamChiTietController.findSanPhamChiTietByMaSP(searchText);

        // Cập nhật bảng với kết quả tìm kiếm
        updateSanPhamChiTietTable(sanPhamChiTietList);

        if (sanPhamChiTietList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm chi tiết với tên " + searchText, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateSanPhamChiTietTable(List<SanPhamChiTiet> sanPhamChiTietList) {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        model.setRowCount(0);

        int stt = 1;
        for (SanPhamChiTiet sanPhamChiTiet : sanPhamChiTietList) {
            Object[] rowData = new Object[11];
            rowData[0] = stt++;
            rowData[1] = sanPhamChiTiet.getMaSPCT();
            rowData[2] = sanPhamChiTiet.getTacGia();
            rowData[3] = sanPhamChiTiet.getTheLoai();
            rowData[4] = sanPhamChiTiet.getTen();
            rowData[5] = sanPhamChiTiet.getGia();
            rowData[6] = sanPhamChiTiet.getNgonNgu();
            rowData[7] = sanPhamChiTiet.getSoTrang();
            rowData[8] = sanPhamChiTiet.getNhaXuatBan();
            rowData[9] = sanPhamChiTiet.getNamXuatBan();
            rowData[10] = sanPhamChiTiet.getLanTaiBan();
            model.addRow(rowData);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPhamChiTiet = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblCount = new javax.swing.JLabel();
        cboTG = new javax.swing.JComboBox<>();
        cboTL = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jInternalFrame1.setTitle("Chi tiết sản phẩm");
        jInternalFrame1.setVisible(true);

        tblSanPhamChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SPCT", "Tác giả", "Thể loại", "Tên", "Giá", "Ngôn ngữ", "Số trang", "Nhà xuất bản", "Năm xuất bản", "Lần tái bản"
            }
        ));
        tblSanPhamChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamChiTietMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPhamChiTiet);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel1.setText("Tìm kiếm");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel2.setText("Tổng số sản phẩm:");

        lblCount.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap(348, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblCount, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195)
                .addComponent(btnXoa)
                .addGap(104, 104, 104))
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(cboTG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(cboTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXoa)
                        .addComponent(jLabel2)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamChiTietMouseClicked
//        DefaultTableModel otherTableModel = (DefaultTableModel) tblSanPhamChiTiet.getModel();
//        int selectedRow = tblSanPhamChiTiet.getSelectedRow();
//
//        if (selectedRow != -1) {
//            sanPhamJPanel.selectRow(selectedRow, otherTableModel);
//            System.out.println("Value at column 1: " + otherTableModel.getValueAt(selectedRow, 1));
//            System.out.println("Value at column 2: " + otherTableModel.getValueAt(selectedRow, 2));
//        } else {
//            JOptionPane.showMessageDialog(this, "Lỗi!");
//        }
    }//GEN-LAST:event_tblSanPhamChiTietMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        int selectedRow = tblSanPhamChiTiet.getSelectedRow();

        if (selectedRow >= 0) {
            dtm.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String searchText = txtSearch.getText();

// Chuyển đổi chuỗi thành số nguyên
        try {
            int maSPCT = Integer.parseInt(searchText);

            // Gọi hàm tìm kiếm với mã sản phẩm chi tiết mới
            searchSanPhamChiTiet(maSPCT);
        } catch (NumberFormatException ex) {
            // Xử lý nếu người dùng nhập không phải là số nguyên
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm chi tiết là một số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SanPhamChiTietJFrame(10).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboTG;
    private javax.swing.JComboBox<String> cboTL;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCount;
    private javax.swing.JTable tblSanPhamChiTiet;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
