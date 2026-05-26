package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Utils.ImagenesUtils;
import Utils.MensajesUtils;
import Utils.PaisesUtils;
import Utils.ValidacionesUtils;
import control.Conexion;
import models.ErasmusModel;
import services.ErasmusService;

public class CrearErasmusView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> cbNombre;
	private JButton bGuardar, bCancelar, bSeleccionaImagen;
	private JTextField campoFecha, campoDuracion, campoCapacidad, campoUniversidad;
	private File archivoImg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrearErasmusView frame = new CrearErasmusView();
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
	public CrearErasmusView() {
		setResizable(false);
		setTitle("Crear Erasmus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cbNombre = new JComboBox<>();
		cbNombre.setFont(new Font("Arial", Font.PLAIN, 15));
		cbNombre.setModel(new DefaultComboBoxModel<>(PaisesUtils.Lista_Paises));
		cbNombre.setMaximumRowCount(5);
		cbNombre.setSelectedIndex(-1);
		cbNombre.setToolTipText("Paises disponibles");
		cbNombre.setBounds(224, 22, 157, 31);
		contentPane.add(cbNombre);

		JLabel lblDestino = new JLabel("Destino:");
		lblDestino.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDestino.setBounds(112, 22, 56, 31);
		contentPane.add(lblDestino);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblFecha.setBounds(112, 128, 46, 31);
		contentPane.add(lblFecha);

		campoFecha = new JTextField();
		campoFecha.setToolTipText("Fecha de inicio (DD/MM/YY)");
		campoFecha.setFont(new Font("Arial", Font.PLAIN, 15));
		campoFecha.setColumns(10);
		campoFecha.setBounds(224, 128, 157, 31);
		contentPane.add(campoFecha);

		campoDuracion = new JTextField();
		campoDuracion.setToolTipText("Duración del Erasmus (meses)");
		campoDuracion.setFont(new Font("Arial", Font.PLAIN, 15));
		campoDuracion.setColumns(10);
		campoDuracion.setBounds(224, 181, 157, 31);
		contentPane.add(campoDuracion);

		JLabel lblDuracin = new JLabel("Duración:");
		lblDuracin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDuracin.setBounds(112, 181, 77, 31);
		contentPane.add(lblDuracin);

		campoCapacidad = new JTextField();
		campoCapacidad.setToolTipText("Número de plazas disponibles");
		campoCapacidad.setFont(new Font("Arial", Font.PLAIN, 15));
		campoCapacidad.setColumns(10);
		campoCapacidad.setBounds(224, 234, 157, 31);
		contentPane.add(campoCapacidad);

		JLabel lblCapacidad = new JLabel("Aforo:");
		lblCapacidad.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCapacidad.setBounds(112, 234, 77, 31);
		contentPane.add(lblCapacidad);

		bGuardar = new JButton("Guardar");
		bGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearErasmus();
			}
		});
		bGuardar.setToolTipText("Guarda el Erasmus");
		bGuardar.setBackground(new Color(186, 199, 252));
		bGuardar.setContentAreaFilled(false);
		bGuardar.setOpaque(true);
		bGuardar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bGuardar.setFont(new Font("Arial", Font.PLAIN, 15));
		bGuardar.setBounds(95, 353, 100, 50);
		contentPane.add(bGuardar);

		bCancelar = new JButton("Cancelar");
		bCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuErasmusView();
				dispose();
			}
		});
		bCancelar.setToolTipText("Regresa al menu de Erasmus");
		bCancelar.setBackground(new Color(186, 199, 252));
		bCancelar.setContentAreaFilled(false);
		bCancelar.setOpaque(true);
		bCancelar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
		bCancelar.setBounds(290, 353, 100, 50);
		contentPane.add(bCancelar);

		JLabel lblImagen = new JLabel("Imagen:");
		lblImagen.setFont(new Font("Arial", Font.PLAIN, 15));
		lblImagen.setBounds(112, 287, 56, 31);
		contentPane.add(lblImagen);

		bSeleccionaImagen = new JButton("Selecciona imagen");
		bSeleccionaImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				archivoImg = seleccionarImagen();
			}
		});
		bSeleccionaImagen.setToolTipText("Guarda el Erasmus");
		bSeleccionaImagen.setFont(new Font("Arial", Font.PLAIN, 14));
		bSeleccionaImagen.setBounds(224, 287, 157, 31);
		contentPane.add(bSeleccionaImagen);

		JLabel lblNewLabel = new JLabel("Universidad:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel.setBounds(112, 75, 83, 31);
		contentPane.add(lblNewLabel);

		campoUniversidad = new JTextField();
		campoUniversidad.setFont(new Font("Arial", Font.PLAIN, 15));
		campoUniversidad.setBounds(224, 75, 157, 31);
		contentPane.add(campoUniversidad);
		campoUniversidad.setColumns(10);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void crearErasmus() {
		String destino = (String) cbNombre.getSelectedItem();
		String universidad = campoUniversidad.getText().trim();
		String fecha = campoFecha.getText().trim();
		String capacidad = campoCapacidad.getText().trim();
		String duracion = campoDuracion.getText().trim();

		if (destino == null || fecha.isEmpty() || capacidad.isEmpty() || duracion.isEmpty() || universidad.isEmpty()
				|| archivoImg == null) {
			MensajesUtils.errorCampoVacio();
			return;
		}

		if (ValidacionesUtils.validarErasmus(fecha, capacidad, duracion) != null) {
			String mensaje = ValidacionesUtils.validarErasmus(fecha, capacidad, duracion);
			MensajesUtils.errorValidacion(mensaje);
			return;
		}

		String imagen = null;
		try {
			imagen = ImagenesUtils.crearImagen(archivoImg);
		} catch (IOException e) {
			System.out.println(e);
			MensajesUtils.errorImagen();
		}

		ErasmusModel erasmusNuevo = new ErasmusModel(destino, universidad, fecha, imagen, Integer.parseInt(capacidad),
				Integer.parseInt(duracion));
		crearErasmus(erasmusNuevo);
	}

	public File seleccionarImagen() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (JPG, PNG, JPEG)", "jpg", "jpeg", "png"));
		int resultado = chooser.showOpenDialog(null);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}

	public void crearErasmus(ErasmusModel erasmus) {
		try {
			ErasmusService.insertarErasmus(erasmus, Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorCrearErasmus();
			return;
		}
		MensajesUtils.registroErasmusExitoso();

	}
}
