package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utils.MensajesUtils;
import control.Conexion;
import models.ErasmusModel;
import services.InscriptionService;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class DetallesUserView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ErasmusModel erasmus;
	private JTextField fecha, capacidad, asistentes, duracion, destino, universidad;
	private JButton bSalir;
	private JLabel lblDestino, lblFecha, lblCapacidad, lblAsistentes, lblDuracion;
	private JLabel lblNewLabel;
	private JScrollPane panelImagen;

	public DetallesUserView(Object erasmusSelec) {
		setResizable(false);
		erasmus = (ErasmusModel) erasmusSelec;
		setTitle("Detalles Erasmus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblDestino = new JLabel("Destino:");
		lblDestino.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDestino.setBounds(10, 10, 56, 31);
		contentPane.add(lblDestino);

		lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblFecha.setBounds(10, 99, 46, 31);
		contentPane.add(lblFecha);

		fecha = new JTextField();
		fecha.setText(erasmus.getFecha());
		fecha.setEditable(false);
		fecha.setToolTipText("Fecha de inicio (DD/MM/YY)");
		fecha.setFont(new Font("Arial", Font.PLAIN, 15));
		fecha.setColumns(10);
		fecha.setBounds(122, 99, 157, 31);
		contentPane.add(fecha);

		lblDuracion = new JLabel("Duración:");
		lblDuracion.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDuracion.setBounds(10, 145, 77, 31);
		contentPane.add(lblDuracion);

		capacidad = new JTextField();
		capacidad.setText(String.valueOf(erasmus.getCapacidad()));
		capacidad.setEditable(false);
		capacidad.setToolTipText("Número de plazas disponibles");
		capacidad.setFont(new Font("Arial", Font.PLAIN, 15));
		capacidad.setColumns(10);
		capacidad.setBounds(122, 191, 157, 31);
		contentPane.add(capacidad);

		lblCapacidad = new JLabel("Capacidad:");
		lblCapacidad.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCapacidad.setBounds(10, 191, 77, 31);
		contentPane.add(lblCapacidad);

		duracion = new JTextField();
		duracion.setText(String.valueOf(erasmus.getDuracion()));
		duracion.setEditable(false);
		duracion.setToolTipText("Duración del Erasmus (meses)");
		duracion.setFont(new Font("Arial", Font.PLAIN, 15));
		duracion.setColumns(10);
		duracion.setBounds(122, 145, 157, 31);
		contentPane.add(duracion);

		lblAsistentes = new JLabel("Asistentes:");
		lblAsistentes.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAsistentes.setBounds(10, 237, 77, 31);
		contentPane.add(lblAsistentes);

		asistentes = new JTextField();
		asistentes.setText(getAsistentes());
		asistentes.setEditable(false);
		asistentes.setToolTipText("Número de plazas disponibles");
		asistentes.setFont(new Font("Arial", Font.PLAIN, 15));
		asistentes.setColumns(10);
		asistentes.setBounds(122, 232, 157, 31);
		contentPane.add(asistentes);

		bSalir = new JButton("Volver");
		bSalir.setToolTipText("Cierra la ventana");
		bSalir.setFont(new Font("Arial", Font.PLAIN, 10));
		bSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MisErasmusView();
				dispose();
			}
		});
		bSalir.setBounds(122, 278, 75, 25);
		bSalir.setBackground(new Color(186, 199, 252));
		bSalir.setContentAreaFilled(false);
		bSalir.setOpaque(true);
		bSalir.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(bSalir);

		destino = new JTextField();
		destino.setFont(new Font("Arial", Font.PLAIN, 15));
		destino.setText(erasmus.getDestino());
		destino.setEditable(false);
		destino.setBounds(122, 10, 157, 31);
		contentPane.add(destino);
		destino.setColumns(10);

		lblNewLabel = new JLabel("Universidad:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 54, 83, 31);
		contentPane.add(lblNewLabel);

		universidad = new JTextField();
		universidad.setText(erasmus.getUniversidad());
		universidad.setFont(new Font("Arial", Font.PLAIN, 15));
		universidad.setEditable(false);
		universidad.setBounds(122, 51, 157, 31);
		contentPane.add(universidad);
		universidad.setColumns(10);

		ImageIcon foto = new ImageIcon(erasmus.getImagen());
		JLabel imagen = new JLabel(foto);
		panelImagen = new JScrollPane(imagen);
		panelImagen.setBounds(289, 10, 388, 293);

		contentPane.add(panelImagen);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public String getAsistentes() {
		int i = 0;
		try {
			i = InscriptionService.contarAsistentes(erasmus, Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return null;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorFuncional("OBTENER LOS ASISTENTES DEL ERASMUS");
			return null;
		}
		return String.valueOf(i);
	}

}
