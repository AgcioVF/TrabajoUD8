package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Utils.MensajesUtils;
import control.Conexion;
import control.SesionIniciada;
import models.ErasmusModel;
import services.ErasmusService;
import services.InscriptionService;

public class ErasmusUserView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;
	private JTextField textFiltrar;
	private JButton bInscribir, bSalir;
	private List<ErasmusModel> listaErasmus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErasmusUserView frame = new ErasmusUserView();
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
	public ErasmusUserView() {
		setTitle("Erasmus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		bInscribir = new JButton("Inscribir");
		bInscribir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inscribirEstudiante();
			}
		});
		bInscribir.setToolTipText("Despliega el menu de creación de erasmus");
		bInscribir.setBackground(new Color(153, 217, 251));
		bInscribir.setContentAreaFilled(false);
		bInscribir.setOpaque(true);
		bInscribir.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bInscribir.setFont(new Font("Arial", Font.PLAIN, 15));
		bInscribir.setBounds(331, 13, 100, 50);
		contentPane.add(bInscribir);

		JLabel lblFiltrar = new JLabel("Filtrar por nombre:");
		lblFiltrar.setFont(new Font("Arial", Font.PLAIN, 15));
		lblFiltrar.setBounds(33, 23, 124, 31);
		contentPane.add(lblFiltrar);

		textFiltrar = new JTextField();
		textFiltrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String cadenaAFiltrar = textFiltrar.getText();
				actualizarTabla(cadenaAFiltrar);
			}
		});

		textFiltrar.setToolTipText("Muestra aquellos Erasmus que coinciden con el campo de texto");
		textFiltrar.setFont(new Font("Arial", Font.PLAIN, 15));
		textFiltrar.setBounds(164, 23, 157, 31);
		contentPane.add(textFiltrar);
		textFiltrar.setColumns(10);

		bSalir = new JButton("Salir");
		bSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuEstudianteView();
				dispose();
			}
		});
		bSalir.setToolTipText("Regresa al menu principal");
		bSalir.setBackground(new Color(186, 199, 252));
		bSalir.setContentAreaFilled(false);
		bSalir.setOpaque(true);
		bSalir.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bSalir.setFont(new Font("Arial", Font.PLAIN, 10));
		bSalir.setBounds(205, 378, 75, 25);
		contentPane.add(bSalir);

		JScrollPane panelTabla = new JScrollPane();
		panelTabla.setBounds(33, 76, 420, 286);

		tabla = new JTable();
		tabla.setFont(new Font("Arial", Font.PLAIN, 15));
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setDefaultEditor(Object.class, null);
		panelTabla.setViewportView(tabla);
		mostrarTabla();

		contentPane.add(panelTabla);

		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void mostrarTabla() {
		try {
			listaErasmus = ErasmusService.getErasmusSinEsteEstudiante(SesionIniciada.getEstudianteIniciado(),
					Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorBaseDatosTabla();
			return;
		}
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new Object[] { "DESTINO", "UNIVERSIDAD", "FECHA" });

		for (ErasmusModel e : listaErasmus) {
			modelo.addRow(new Object[] { e.getDestino(), e.getUniversidad(), e.getFecha() });
		}

		tabla.setModel(modelo);
	}

	public void actualizarTabla(String filtrar) {
		try {
			listaErasmus = ErasmusService.getErasmusPorDestino(filtrar, SesionIniciada.getEstudianteIniciado(),
					Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorBaseDatosTabla();
			return;
		}
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new Object[] { "DESTINO", "UNIVERSIDAD", "FECHA" });

		for (ErasmusModel e : listaErasmus) {
			modelo.addRow(new Object[] { e.getDestino(), e.getUniversidad(), e.getFecha() });
		}

		tabla.setModel(modelo);
	}

	public void inscribirEstudiante() {
		int fila = tabla.getSelectedRow();
		if (fila >= 0) {
			int confirmar = MensajesUtils.confirmar("INSCRIPCIÓN DE A ESTE ERASMUS");
			if (confirmar == 0) {
				ErasmusModel erasmusSeleccionado = listaErasmus.get(fila);
				try {
					InscriptionService.insertarInscripcion(erasmusSeleccionado, SesionIniciada.getEstudianteIniciado(),
							Conexion.obtener());
				} catch (ClassNotFoundException e) {
					System.out.println(e);
					MensajesUtils.errorConexion();
					return;
				} catch (SQLException e) {
					System.out.println(e);
					MensajesUtils.errorFuncional("REALIZAR LA INSCRIPCIÓN");
					return;
				}
				mostrarTabla();
			}
		} else {
			MensajesUtils.seleccionarFila();
			return;
		}
	}

}
