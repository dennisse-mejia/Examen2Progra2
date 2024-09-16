/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 *
 * @author dennisse
 */
public class MainMenuGUI extends JFrame {

    private PSNUsers psnUsers;

    public MainMenuGUI(PSNUsers psnUsers) {
        super("PSN User Manager");
        this.psnUsers = psnUsers;

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));
        setLocationRelativeTo(null);

        JButton addUserButton = new JButton("Agregar Usuario");
        JButton deactivateButton = new JButton("Desactivar Cuenta");
        JButton addTrophyButton = new JButton("Agregar Trofeo");
        JButton playerInfoButton = new JButton("Player Info");
        JButton exitButton = new JButton("Salir");

        add(addUserButton);
        add(deactivateButton);
        add(addTrophyButton);
        add(playerInfoButton);
        add(exitButton);

        addUserButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Ingrese nombre usuario:");
            if (username != null && !username.isEmpty()) {
                try {
                    if (psnUsers.addUser(username)) {
                        JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Username ya exite.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deactivateButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Ingrese el usuario que desea desactivar:");
            if (username != null && !username.isEmpty()) {
                try {
                    if (psnUsers.deactivateUser(username)) {
                        JOptionPane.showMessageDialog(this, "Usuario desactivado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        addTrophyButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Ingrese el usuario:");
            if (username != null && !username.isEmpty()) {
                try {
                    if (!psnUsers.addTrophieTo(username.toLowerCase(), null, null, null)) {
                        return;
                    }

                    String trophyGame = JOptionPane.showInputDialog(this, "Ingrese el trophy game:");
                    String trophyName = JOptionPane.showInputDialog(this, "Ingrese el trophy name:");
                    Trophy[] trophies = {Trophy.PLATINO, Trophy.ORO, Trophy.PLATA, Trophy.BRONCE};
                    Trophy type = (Trophy) JOptionPane.showInputDialog(this, "Seleccione el trophy type:", "Trophy Type",
                            JOptionPane.QUESTION_MESSAGE, null, trophies, trophies[0]);

                    if (trophyGame != null && trophyName != null && type != null) {
                        if (psnUsers.addTrophieTo(username.toLowerCase(), trophyGame, trophyName, type)) {
                            JOptionPane.showMessageDialog(this, "Trophy agregado exitosamente!");
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        playerInfoButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Ingrese el username para ver info:");
            if (username != null && !username.isEmpty()) {
                try {
                    String info = psnUsers.getPlayerInfo(username);
                    if (info != null) {
                        JTextArea textArea = new JTextArea(info);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new Dimension(300, 200));
                        JOptionPane.showMessageDialog(this, scrollPane, "Player Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitButton.addActionListener(e -> {
            System.exit(0); 
        });

    }

    public static void main(String[] args) {
        try {
            PSNUsers psnUsers = new PSNUsers("psn.dat", "trophies.dat");
            MainMenuGUI mainMenu = new MainMenuGUI(psnUsers);
            mainMenu.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
