package minesweeper;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ListPlayer {
    JFrame frame = new JFrame("Let's Play!!");
    ImageIcon backgroundIcon = new ImageIcon("asset/BG1.jpg");
    JLabel backgroundLabel = new JLabel(backgroundIcon);

    JPanel listPanel = new JPanel();
    DefaultListModel<String> playerListModel = new DefaultListModel<>();
    JList<String> playerList = new JList<>(playerListModel);
    JScrollPane listScrollPane = new JScrollPane(playerList);

    JPanel panel = new JPanel();
    JTextField nickname = new JTextField();
    JButton savename = new JButton("Add Player");

    //
    Connection conn = null;
    PreparedStatement ps = null;
    Statement stmt = null;
    ResultSet rs = null;

    ListPlayer() {
        nickname.setPreferredSize(new Dimension(250, 30));

        loadPlayerList();
        listPanel.setLayout(new BorderLayout());
        listScrollPane.getViewport().setOpaque(false);
        listScrollPane.setOpaque(false);
        listPanel.add(new JLabel("List of Players"), BorderLayout.NORTH);
        listPanel.add(listScrollPane, BorderLayout.CENTER);
        listPanel.setOpaque(false);

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

        playerList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Dapatkan pemain yang dipilih
                    String selectedPlayer = playerList.getSelectedValue();

                    // Dapatkan id_player dari database (gantilah dengan metode Anda)
                    int playerId = getPlayerId(selectedPlayer);

                    // Buka jendela bermain dengan membawa id_player
                    openPlayWindow();
                }
            }
        });

        frame.setSize(backgroundIcon.getIconWidth() - 200, backgroundIcon.getIconHeight() - 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(backgroundLabel);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(panel, BorderLayout.SOUTH);
        frame.add(listPanel, BorderLayout.CENTER);
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
                    loadPlayerList();
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menambahkan data!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan. Silakan coba lagi.");
        }
    }

    private void loadPlayerList() {
        playerListModel.clear();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minesweeper", "root", "");
            stmt = conn.createStatement();

            String query = "SELECT nama FROM users";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                playerListModel.addElement(rs.getString("nama"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private int getPlayerId(String playerName) {

        int playerId = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minesweeper", "root", "");

            String query = "SELECT id FROM users WHERE nama=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, playerName);

            rs = ps.executeQuery();

            if (rs.next()) {
                playerId = rs.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return playerId;
    }

    private void openPlayWindow() {
        // Implementasikan logika untuk membuka jendela bermain dengan membawa id_player
        // Gantilah dengan logika sesuai kebutuhan Anda
        frame.dispose();
        new Game();
    }
}