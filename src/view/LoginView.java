package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import Utils.MensajesUtils;
import control.Conexion;
import control.SesionIniciada;
import models.EstudianteModel;
import services.EstudianteService;

import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField campoCntr;
	private JTextField campoNombre;
	private JButton bAceptar, bCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
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
	public LoginView() {
		setResizable(false);
		setTitle("Inicio sesión");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNombre.setBounds(93, 45, 58, 15);
		contentPane.add(lblNombre);

		JLabel lblCntr = new JLabel("Contraseña:");
		lblCntr.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCntr.setBounds(93, 105, 82, 15);
		contentPane.add(lblCntr);

		campoCntr = new JPasswordField();
		campoCntr.setFont(new Font("Arial", Font.PLAIN, 15));
		campoCntr.setColumns(10);
		campoCntr.setBounds(185, 90, 157, 31);
		contentPane.add(campoCntr);

		bAceptar = new JButton("Aceptar");
		bAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inicioSesion();
			}
		});
		bAceptar.setBackground(new Color(186, 199, 252));
		bAceptar.setContentAreaFilled(false);
		bAceptar.setOpaque(true);
		bAceptar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bAceptar.setFont(new Font("Arial", Font.PLAIN, 15));
		bAceptar.setBounds(78, 202, 100, 50);
		contentPane.add(bAceptar);

		bCancelar = new JButton("Cancelar");
		bCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuLoginView();
				dispose();
			}
		});
		bCancelar.setBackground(new Color(186, 199, 252));
		bCancelar.setContentAreaFilled(false);
		bCancelar.setOpaque(true);
		bCancelar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
		bCancelar.setBounds(256, 202, 100, 50);
		contentPane.add(bCancelar);

		campoNombre = new JTextField();
		campoNombre.setFont(new Font("Arial", Font.PLAIN, 15));
		campoNombre.setColumns(10);
		campoNombre.setBounds(185, 38, 157, 31);
		contentPane.add(campoNombre);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void inicioSesion() {
		String nombre = campoNombre.getText().trim();
		char[] contrasena = campoCntr.getPassword();
		String cntr = new String(contrasena);
		if (nombre.isEmpty() || cntr.isEmpty()) {
			MensajesUtils.errorCampoVacio();
			return;
		}
		EstudianteModel estudianteLogin = null;
		try {
			estudianteLogin = EstudianteService.existeEstudiante(nombre, cntr, Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorBaseDatos();
			return;
		}
		if (estudianteLogin != null) {
			if (estudianteLogin.getRole().equalsIgnoreCase("ADMIN")) {
				SesionIniciada.setEstudianteIniciado(estudianteLogin);
				new MenuAdminView();
				dispose();
			} else {
				SesionIniciada.setEstudianteIniciado(estudianteLogin);
				new MenuEstudianteView();
				dispose();
			}
		} else {
			MensajesUtils.errorLogin();
		}

	}

}
