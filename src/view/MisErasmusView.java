package view;

import java.awt.Color;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Utils.MensajesUtils;
import control.Conexion;
import control.SesionIniciada;
import models.ErasmusModel;
import services.ErasmusService;
import services.InscriptionService;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MisErasmusView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;
	private List<ErasmusModel> listaErasmus;
	private JButton bCancelar, bDetalles, bVolver;

	/**
	 * Create the frame.
	 */
	public MisErasmusView() {
		setResizable(false);
		setTitle("Mis Erasmus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane panel = new JScrollPane();
		panel.setBounds(10, 70, 416, 303);
		tabla = new JTable();
		tabla.setFont(new Font("Arial", Font.PLAIN, 15));
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setDefaultEditor(Object.class, null);
		;
		panel.setViewportView(tabla);
		mostrarTabla();
		contentPane.add(panel);

		bCancelar = new JButton("Cancelar");
		bCancelar.setToolTipText("Cancelar mi inscripción a este erasmus");
		bCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
		bCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		bCancelar.setBackground(new Color(153, 217, 251));
		bCancelar.setContentAreaFilled(false);
		bCancelar.setOpaque(true);
		bCancelar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
		bCancelar.setBounds(78, 10, 100, 50);
		contentPane.add(bCancelar);

		bVolver = new JButton("Volver");
		bVolver.setBackground(new Color(186, 199, 252));
		bVolver.setContentAreaFilled(false);
		bVolver.setOpaque(true);
		bVolver.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bVolver.setFont(new Font("Arial", Font.PLAIN, 10));
		bVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuEstudianteView();
				dispose();
			}
		});
		bVolver.setBounds(176, 383, 84, 20);
		contentPane.add(bVolver);

		bDetalles = new JButton("Detalles");
		bDetalles.setToolTipText("Visualización avanzada de erasmus");
		bDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detalles();
			}
		});
		bDetalles.setFont(new Font("Arial", Font.PLAIN, 15));
		bDetalles.setBackground(new Color(153, 217, 251));
		bDetalles.setContentAreaFilled(false);
		bDetalles.setOpaque(true);
		bDetalles.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bDetalles.setBounds(256, 10, 100, 50);
		contentPane.add(bDetalles);

		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void mostrarTabla() {
		try {
			listaErasmus = ErasmusService.getErasmusUser(SesionIniciada.getEstudianteIniciado(), Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorBaseDatosTabla();
		}
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new Object[] { "DESTINO", "UNIVERSIDAD", "FECHA" });

		for (ErasmusModel e : listaErasmus) {
			modelo.addRow(new Object[] { e.getDestino(), e.getUniversidad(), e.getFecha() });
		}

		tabla.setModel(modelo);
	}

	public void eliminar() {
		int fila = tabla.getSelectedRow();

		if (fila >= 0) {
			int confirmacion = MensajesUtils.confirmarCancelacion();
			if (confirmacion == 0) {
				ErasmusModel erasmus = listaErasmus.get(fila);
				try {
					InscriptionService.eliminarInscripcion(erasmus, SesionIniciada.getEstudianteIniciado(),
							Conexion.obtener());
				} catch (ClassNotFoundException e) {
					System.out.println(e);
					MensajesUtils.errorConexion();
					return;
				} catch (SQLException e) {
					System.out.println(e);
					MensajesUtils.errorEliminarInscripcion();
					return;
				}
				mostrarTabla();
			} else {
				return;
			}
		} else {
			MensajesUtils.seleccionarFila();
			return;
		}

	}

	public void detalles() {
		int fila = tabla.getSelectedRow();
		if (fila >= 0) {
			ErasmusModel erasmusSelec = listaErasmus.get(fila);
			new DetallesUserView(erasmusSelec);
			dispose();
		} else {
			MensajesUtils.seleccionarFila();
		}
	}
}
