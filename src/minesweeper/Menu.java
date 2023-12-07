package minesweeper;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.URL;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    
        JFrame frame = new JFrame("Aselole Joss");
        ImageIcon backgroundIcon = new ImageIcon("asset/BG2.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);

        JPanel menu = new JPanel();
        JButton play = new JButton("Play");
        JButton score = new JButton("Score");
        JButton exit = new JButton("Exit");

        Menu() {
            menu.setOpaque(false);
            menu.setBorder(new EmptyBorder(200, 200, 200, 200));

            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ListPlayer();
                    frame.dispose();
                }
            });

            score.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add your Score button action here
                    JOptionPane.showMessageDialog(frame, "Score button clicked");
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add your Exit button action here
                    int choice = JOptionPane.showConfirmDialog(frame, "Do you really want to exit?",
                            "Confirm Exit",
                            JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });

            menu.add(play);
            menu.add(score);
            menu.add(exit);

            // Set up the window properties
            frame.setSize(backgroundIcon.getIconWidth() - 200, backgroundIcon.getIconHeight() - 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(backgroundLabel);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);
            frame.setVisible(true);
            frame.add(menu, BorderLayout.CENTER);

        }
        public void playBackgroundMusic(String filePath) {
            try {
                // Mengambil URL file musik
                URL url = new File(filePath).toURI().toURL();
    
                // Membuat AudioInputStream untuk file musik
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
    
                // Mendapatkan Clip dari AudioSystem
                Clip clip = AudioSystem.getClip();
    
                // Membuka AudioInputStream ke Clip
                clip.open(audioInputStream);
    
                // Mengulang pemutaran
                clip.loop(Clip.LOOP_CONTINUOUSLY);
    
                // Memulai pemutaran
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
    }

    // Metode untuk memainkan backsound musik
        }
    }
