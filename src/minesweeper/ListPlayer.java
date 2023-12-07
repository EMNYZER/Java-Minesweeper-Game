package minesweeper;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class ListPlayer {
    JFrame frame = new JFrame("Let's Play!!");
    ImageIcon backgroundIcon = new ImageIcon("asset/BG1.jpg");
    JLabel backgroundLabel = new JLabel(backgroundIcon);

    JPanel panel = new JPanel();
    JTextField nickname = new JTextField();
    JButton savename = new JButton("Add Player");



    ListPlayer() {
        nickname.setPreferredSize(new Dimension(250, 30));

        panel.setLayout(new FlowLayout(0, 10, 10));
        panel.setOpaque(false);

        savename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayerButton(e);

                
            }
        });

        panel.add(nickname);
        panel.add(savename);

        frame.setSize(backgroundIcon.getIconWidth() - 200, backgroundIcon.getIconHeight() - 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(backgroundLabel);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(panel, BorderLayout.SOUTH);
    }

    private void addPlayerButton(ActionEvent evt) {
        String username = nickname.getText();
    
        // Gunakan try-with-resources untuk otomatis menutup koneksi
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minesweeper", "root", "");
             PreparedStatement checkStatement = conn.prepareStatement("SELECT * FROM users WHERE nama = ?");
             PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO users (nama) VALUES (?)")) {
    
            // Set parameter untuk query SELECT
            checkStatement.setString(1, username);
            ResultSet rs = checkStatement.executeQuery();
    
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Username sudah terdaftar!");
            } else {
                // Set parameter untuk query INSERT
                insertStatement.setString(1, username);
                int result = insertStatement.executeUpdate();
    
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Player Berhasil Ditambahkan");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menambahkan data!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan. Silakan coba lagi.");
        }
    }
        
}
