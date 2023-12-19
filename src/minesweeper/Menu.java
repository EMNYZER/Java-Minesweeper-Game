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

    JFrame frame = new JFrame("Minesweeper");
    ImageIcon backgroundIcon = new ImageIcon("src\\resources\\BG2.jpg");
    JLabel backgroundLabel = new JLabel(backgroundIcon);
    
    JPanel menu = new JPanel();
    ImageIcon IMGbutton = new ImageIcon("src\\resources\\Start_Button.png");
    JButton play = new JButton(IMGbutton);
    ImageIcon IMGexit = new ImageIcon("src\\resources\\Exit_Button.png");
    JButton exit = new JButton(IMGexit);
    
    Menu() {
        play.setOpaque(false);
        play.setBorderPainted(false); 
        play.setContentAreaFilled(false); 
        play.setFocusPainted(false);
        
        exit.setOpaque(false);
        exit.setBorderPainted(false); 
        exit.setContentAreaFilled(false); 
        exit.setFocusPainted(false); 
        menu.setOpaque(false);
        menu.setBorder(new EmptyBorder(200, 200, 200, 200));

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListPlayer();
                frame.dispose();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "Do you really want to exit?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        menu.add(play);
        menu.add(exit);

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
            URL url = new File(filePath).toURI().toURL();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
