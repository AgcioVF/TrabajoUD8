package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utils.MensajesUtils;
import control.SesionIniciada;

import java.awt.Color;

public class MenuEstudianteView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton bSalir, bErasmus, bMisErasmus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuEstudianteView frame = new MenuEstudianteView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuEstudianteView() {
		setTitle("Menu Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		bSalir = new JButton("Volver");
		bSalir.setToolTipText("Regresa al inicio de sesión");
		bSalir.setFont(new Font("Arial", Font.PLAIN, 10));
		bSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmar = MensajesUtils.confirmarLogout();
				if (confirmar == 0) {
					SesionIniciada.cerrarSesion();
					new MenuLoginView();
					dispose();
				}
			}
		});
		bSalir.setBounds(180, 178, 75, 25);
		bSalir.setBackground(new Color(186, 199, 252));
		bSalir.setContentAreaFilled(false);
		bSalir.setOpaque(true);
		bSalir.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(bSalir);

		bErasmus = new JButton("Erasmus");
		bErasmus.setFont(new Font("Arial", Font.PLAIN, 15));
		bErasmus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ErasmusUserView();
				dispose();
			}
		});
		bErasmus.setBounds(78, 81, 100, 50);
		bErasmus.setBackground(new Color(153, 217, 251));
		bErasmus.setContentAreaFilled(false);
		bErasmus.setOpaque(true);
		bErasmus.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(bErasmus);

		bMisErasmus = new JButton("Mis Erasmus");
		bMisErasmus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MisErasmusView();
				dispose();
			}
		});
		bMisErasmus.setFont(new Font("Arial", Font.PLAIN, 15));
		bMisErasmus.setBackground(new Color(153, 217, 251));
		bMisErasmus.setContentAreaFilled(false);
		bMisErasmus.setOpaque(true);
		bMisErasmus.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bMisErasmus.setBounds(256, 81, 100, 50);
		contentPane.add(bMisErasmus);

		setLocationRelativeTo(null);
		setVisible(true);
	}

}
