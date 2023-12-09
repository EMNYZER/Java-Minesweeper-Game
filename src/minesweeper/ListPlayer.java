package minesweeper;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ListPlayer {
    private Connection connection;
    JFrame frame = new JFrame("Let's Play!!");
    ImageIcon backgroundIcon = new ImageIcon("src\\resources\\BG2.jpg");
    JLabel backgroundLabel = new JLabel(backgroundIcon);

    JPanel listPanel = new JPanel();
    DefaultListModel<String> playerListModel = new DefaultListModel<>();
    JList<String> playerList = new JList<>(playerListModel);
    JScrollPane listScrollPane = new JScrollPane(playerList);

    JPanel panel = new JPanel();
    JTextField nickname = new JTextField();
    JButton savename = new JButton("Add Player");

    //

    PreparedStatement ps = null;
    Statement stmt = null;
    ResultSet rs = null;

    ListPlayer() {
        this.connection = DatabaseConnection.getConnection();
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
                    String selectedPlayer = playerList.getSelectedValue();
                    int playerId = getPlayerId(selectedPlayer);
                    openPlayWindow(playerId);
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

        try (
                PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM users WHERE nama = ?");
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users (nama) VALUES (?)")) {

            checkStatement.setString(1, username);
            ResultSet rs = checkStatement.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Username sudah terdaftar!");
            } else {

                insertStatement.setString(1, username);
                int result = insertStatement.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Player Berhasil Ditambahkan");
                    System.out.println("Berhasil ditambah");
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

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nama FROM users")) {

            while (rs.next()) {
                playerListModel.addElement(rs.getString("nama"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int getPlayerId(String playerName) {
        int playerId = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE nama=?")) {

            ps.setString(1, playerName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    playerId = rs.getInt("id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return playerId;
    }

    private void openPlayWindow(int playerId) {
        frame.dispose();
        new Game(playerId);
    }
}