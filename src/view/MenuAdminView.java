package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utils.MensajesUtils;
import control.SesionIniciada;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MenuAdminView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton bSalir, bErasmus, bUser, bEstadisticas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuAdminView frame = new MenuAdminView();
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
	public MenuAdminView() {
		super("Menu Principal");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		bSalir = new JButton("Volver");
		bSalir.setToolTipText("Cierra la ventana");
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
		bSalir.setBounds(180, 228, 75, 25);
		bSalir.setBackground(new Color(186, 199, 252));
		bSalir.setContentAreaFilled(false);
		bSalir.setOpaque(true);
		bSalir.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(bSalir);

		bErasmus = new JButton("Erasmus");
		ImageIcon iconoUE = new ImageIcon("iconos/ue.png");
		bErasmus.setIcon(iconoUE);
		bErasmus.setFont(new Font("Arial", Font.PLAIN, 13));
		bErasmus.setToolTipText("Opciones de gestión de erasmus");
		bErasmus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuErasmusView();
				dispose();
			}
		});
		bErasmus.setBounds(78, 34, 100, 50);
		bErasmus.setBackground(new Color(153, 217, 251));
		bErasmus.setContentAreaFilled(false);
		bErasmus.setOpaque(true);
		bErasmus.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(bErasmus);

		bUser = new JButton("Estudiantes");
		ImageIcon iconoUser = new ImageIcon("iconos/user.png");
		bUser.setIcon(iconoUser);
		bUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuUserView();
				dispose();
			}
		});
		bUser.setFont(new Font("Arial", Font.PLAIN, 13));
		bUser.setBackground(new Color(153, 217, 251));
		bUser.setContentAreaFilled(false);
		bUser.setOpaque(true);
		bUser.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bUser.setToolTipText("Opciones de gestión de estudiantes");
		bUser.setBounds(256, 34, 100, 50);
		contentPane.add(bUser);

		bEstadisticas = new JButton("Estadisticas");
		ImageIcon iconoStats = new ImageIcon("iconos/stats.png");
		bEstadisticas.setIcon(iconoStats);
		bEstadisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuStatsView();
				dispose();
			}
		});
		bEstadisticas.setFont(new Font("Arial", Font.PLAIN, 13));
		bEstadisticas.setBackground(new Color(153, 217, 251));
		bEstadisticas.setContentAreaFilled(false);
		bEstadisticas.setOpaque(true);
		bEstadisticas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bEstadisticas.setToolTipText("Opciones de visualización de estadísticas");
		bEstadisticas.setBounds(168, 118, 100, 50);
		contentPane.add(bEstadisticas);

		setLocationRelativeTo(null);
		setVisible(true);

	}
}
