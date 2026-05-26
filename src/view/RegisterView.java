package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import Utils.MensajesUtils;
import Utils.ValidacionesUtils;
import control.Conexion;
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

public class RegisterView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField campoEmail, campoEdad, campoNombre;
	private JButton bAceptar, bCancelar;
	private JPasswordField campoContrasena;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterView frame = new RegisterView();
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
	public RegisterView() {
		setResizable(false);
		setTitle("Crear cuenta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNombre.setBounds(93, 14, 58, 31);
		contentPane.add(lblNombre);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmail.setBounds(93, 59, 42, 31);
		contentPane.add(lblEmail);

		campoEmail = new JTextField();
		campoEmail.setToolTipText("Dirección email única");
		campoEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		campoEmail.setColumns(10);
		campoEmail.setBounds(185, 59, 157, 31);
		contentPane.add(campoEmail);

		campoEdad = new JTextField();
		campoEdad.setToolTipText("Edad del usuario");
		campoEdad.setFont(new Font("Arial", Font.PLAIN, 15));
		campoEdad.setColumns(10);
		campoEdad.setBounds(185, 104, 157, 31);
		contentPane.add(campoEdad);

		JLabel lblEdad = new JLabel("Edad:");
		lblEdad.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEdad.setBounds(93, 104, 42, 31);
		contentPane.add(lblEdad);

		bAceptar = new JButton("Aceptar");
		bAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionRegistrar();
			}
		});
		bAceptar.setFont(new Font("Arial", Font.PLAIN, 15));
		bAceptar.setBackground(new Color(186, 199, 252));
		bAceptar.setContentAreaFilled(false);
		bAceptar.setOpaque(true);
		bAceptar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
		campoNombre.setToolTipText("Nombre del usuario");
		campoNombre.setFont(new Font("Arial", Font.PLAIN, 15));
		campoNombre.setColumns(10);
		campoNombre.setBounds(185, 14, 157, 31);
		contentPane.add(campoNombre);

		campoContrasena = new JPasswordField();
		campoContrasena.setToolTipText("Edad (+18)");
		campoContrasena.setFont(new Font("Arial", Font.PLAIN, 15));
		campoContrasena.setColumns(10);
		campoContrasena.setBounds(185, 149, 157, 31);
		contentPane.add(campoContrasena);

		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setFont(new Font("Arial", Font.PLAIN, 15));
		lblContrasea.setBounds(93, 149, 82, 31);
		contentPane.add(lblContrasea);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void accionRegistrar() {
		String nombre = campoNombre.getText().trim();
		String email = campoEmail.getText().trim();
		String edad = campoEdad.getText().trim();
		char[] contrasena = campoContrasena.getPassword();
		String cntr = new String(contrasena);

		if (nombre.isEmpty() || email.isEmpty() || edad.isEmpty() || cntr.isEmpty()) {
			MensajesUtils.errorCampoVacio();
			return;
		}
		int cantEmail = emailUnico(email);

		if (ValidacionesUtils.validarEstudiante(cantEmail, email, edad) != null) {
			String mensaje = ValidacionesUtils.validarEstudiante(cantEmail, email, edad);
			MensajesUtils.errorValidacion(mensaje);
			return;
		}
		EstudianteModel estudianteNuevo = new EstudianteModel(nombre, email, Integer.parseInt(edad), "USER", cntr);
		crearEstudiante(estudianteNuevo);
	}

	public int emailUnico(String email) {
		int cantidad = -1;
		try {
			cantidad = EstudianteService.existeEmail(email, Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return -1;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorBaseDatos();
			return -1;
		}
		return cantidad;
	}

	public void crearEstudiante(EstudianteModel estudiante) {
		try {
			EstudianteService.insertarEstudiante(estudiante, Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorFuncional("CREAR EL ESTUDIANTE");
			return;
		}

		MensajesUtils.registroEstudianteExitoso();
		new MenuLoginView();
		dispose();
	}
}
