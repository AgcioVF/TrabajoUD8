package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;

public class CrearUserView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textEmail, textEdad, textNombre;
	private JButton bGuardar, bCancelar;
	private JPasswordField passwordField;
	private JCheckBox ckAdmin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrearUserView frame = new CrearUserView();
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
	public CrearUserView() {
		setResizable(false);
		setTitle("Crear estudiante");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNombre.setBounds(104, 15, 58, 31);
		contentPane.add(lblNombre);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmail.setBounds(104, 61, 42, 31);
		contentPane.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setToolTipText("Dirección email única");
		textEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		textEmail.setColumns(10);
		textEmail.setBounds(224, 63, 157, 31);
		contentPane.add(textEmail);

		textEdad = new JTextField();
		textEdad.setToolTipText("Edad +18");
		textEdad.setFont(new Font("Arial", Font.PLAIN, 15));
		textEdad.setColumns(10);
		textEdad.setBounds(224, 110, 157, 31);
		contentPane.add(textEdad);

		JLabel lblEdad = new JLabel("Edad:");
		lblEdad.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEdad.setBounds(104, 107, 42, 31);
		contentPane.add(lblEdad);

		bGuardar = new JButton("Guardar");
		bGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarEstudiante();
			}
		});
		bGuardar.setToolTipText("Almacena el usuario");
		bGuardar.setBackground(new Color(186, 199, 252));
		bGuardar.setContentAreaFilled(false);
		bGuardar.setOpaque(true);
		bGuardar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bGuardar.setFont(new Font("Arial", Font.PLAIN, 15));
		bGuardar.setBounds(95, 253, 100, 50);
		contentPane.add(bGuardar);

		bCancelar = new JButton("Cancelar");
		bCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuUserView();
				dispose();
			}
		});
		bCancelar.setToolTipText("Regresa al menu de usuarios");
		bCancelar.setBackground(new Color(186, 199, 252));
		bCancelar.setContentAreaFilled(false);
		bCancelar.setOpaque(true);
		bCancelar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
		bCancelar.setBounds(290, 253, 100, 50);
		contentPane.add(bCancelar);

		JLabel lblAdmin = new JLabel("Admin:");
		lblAdmin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAdmin.setBounds(104, 199, 58, 31);
		contentPane.add(lblAdmin);

		ckAdmin = new JCheckBox("");
		ckAdmin.setFont(new Font("Arial", Font.PLAIN, 15));
		ckAdmin.setBackground(new Color(230, 255, 255));
		ckAdmin.setToolTipText("Asigna el role Admin al nuevo usuario");
		ckAdmin.setBounds(224, 204, 21, 21);
		contentPane.add(ckAdmin);

		textNombre = new JTextField();
		textNombre.setToolTipText("Nombre del usuario");
		textNombre.setFont(new Font("Arial", Font.PLAIN, 15));
		textNombre.setColumns(10);
		textNombre.setBounds(224, 16, 157, 31);
		contentPane.add(textNombre);

		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setFont(new Font("Arial", Font.PLAIN, 15));
		lblContrasea.setBounds(104, 153, 82, 31);
		contentPane.add(lblContrasea);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
		passwordField.setBounds(224, 157, 157, 31);
		contentPane.add(passwordField);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void guardarEstudiante() {
		String nombre = textNombre.getText().trim();
		String email = textEmail.getText().trim();
		String edad = textEdad.getText().trim();
		char[] contrasena = passwordField.getPassword();
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
		EstudianteModel estudianteNuevo;
		if (ckAdmin.isSelected()) {
			estudianteNuevo = new EstudianteModel(nombre, email, Integer.parseInt(edad), "ADMIN", cntr);
		} else {
			estudianteNuevo = new EstudianteModel(nombre, email, Integer.parseInt(edad), "USER", cntr);
		}

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
	}
}
