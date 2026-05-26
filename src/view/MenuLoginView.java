package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utils.MensajesUtils;
import control.Conexion;
import services.EstudianteService;

public class MenuLoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton bLogin, bRegister, bCerrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuLoginView frame = new MenuLoginView();
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
	public MenuLoginView() {
		setResizable(false);
		setForeground(new Color(255, 255, 255));
		setTitle("Sistema Erasmus");

		comprobarAdmin();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		bLogin = new JButton("Login");
		bLogin.setSize(100, 50);
		bLogin.setLocation(168, 54);
		bLogin.setBackground(new Color(153, 217, 251));
		bLogin.setContentAreaFilled(false);
		bLogin.setOpaque(true);
		bLogin.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		bLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoginView();
				dispose();
			}
		});
		bLogin.setToolTipText("Abre menu inicio de sesión");
		bLogin.setFont(new Font("Arial", Font.PLAIN, 10));

		contentPane.add(bLogin);

		bRegister = new JButton("Register");
		bRegister.setBackground(new Color(153, 217, 251));
		bRegister.setContentAreaFilled(false);
		bRegister.setOpaque(true);
		bRegister.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegisterView();
				dispose();
			}
		});
		bRegister.setToolTipText("Abre menu de registro");
		bRegister.setFont(new Font("Arial", Font.PLAIN, 10));
		bRegister.setBounds(168, 158, 100, 50);
		contentPane.add(bRegister);

		bCerrar = new JButton("Cerrar");
		bCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		bCerrar.setBackground(new Color(186, 199, 252));
		bCerrar.setContentAreaFilled(false);
		bCerrar.setOpaque(true);
		bCerrar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bCerrar.setToolTipText("Finaliza el programa");
		bCerrar.setFont(new Font("Arial", Font.PLAIN, 10));
		bCerrar.setBounds(180, 228, 75, 25);
		contentPane.add(bCerrar);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void comprobarAdmin() {
		try {
			EstudianteService.existeAdmin(Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorBaseDatos();
			return;
		}
	}
}
