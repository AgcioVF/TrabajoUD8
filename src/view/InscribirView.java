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
import models.ErasmusModel;
import models.EstudianteModel;
import services.EstudianteService;
import services.InscriptionService;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class InscribirView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaEstudiantes;
	private JButton bInscribir, bVolver;
	private List<EstudianteModel> estudiantes;
	private ErasmusModel erasmus;

	/**
	 * Create the frame.
	 */
	public InscribirView(Object erasmusSeleccionado) {
		setResizable(false);
		erasmus = (ErasmusModel) erasmusSeleccionado;
		setTitle("Inscribir");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane panelEstudiantes = new JScrollPane();
		panelEstudiantes.setBounds(10, 70, 416, 303);
		tablaEstudiantes = new JTable();
		tablaEstudiantes.setFont(new Font("Arial", Font.PLAIN, 15));
		tablaEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaEstudiantes.setDefaultEditor(Object.class, null);
		panelEstudiantes.setViewportView(tablaEstudiantes);
		mostrarTablaEstudiantes();
		contentPane.add(panelEstudiantes);

		bInscribir = new JButton("Inscribir");
		bInscribir.setToolTipText("Inscribe el estudiante seleccionado en este erasmus");
		bInscribir.setFont(new Font("Arial", Font.PLAIN, 15));
		bInscribir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inscribir();
			}
		});
		bInscribir.setBackground(new Color(153, 217, 251));
		bInscribir.setContentAreaFilled(false);
		bInscribir.setOpaque(true);
		bInscribir.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bInscribir.setFont(new Font("Arial", Font.PLAIN, 15));
		bInscribir.setBounds(168, 10, 100, 50);
		contentPane.add(bInscribir);

		bVolver = new JButton("Volver");
		bVolver.setToolTipText("Regresa al menu de erasmus");
		bVolver.setBackground(new Color(186, 199, 252));
		bVolver.setContentAreaFilled(false);
		bVolver.setOpaque(true);
		bVolver.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bVolver.setFont(new Font("Arial", Font.PLAIN, 10));
		bVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuErasmusView();
				dispose();
			}
		});
		bVolver.setBounds(176, 383, 84, 20);
		contentPane.add(bVolver);

		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void mostrarTablaEstudiantes() {
		try {
			estudiantes = EstudianteService.getEstudiantesNoInscritos(erasmus, Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorBaseDatosTabla();
		}
		DefaultTableModel modelo = new DefaultTableModel();

		modelo.setColumnIdentifiers(new Object[] { "Nombre", "Email", "Edad", "Role" });

		for (EstudianteModel e : estudiantes) {
			modelo.addRow(new Object[] { e.getNombre(), e.getEmail(), e.getEdad(), e.getRole() });
		}

		tablaEstudiantes.setModel(modelo);
	}

	public void inscribir() {
		int filaEstudiantes = tablaEstudiantes.getSelectedRow();

		if (filaEstudiantes >= 0) {
			try {
				InscriptionService.insertarInscripcion(erasmus, estudiantes.get(filaEstudiantes), Conexion.obtener());
			} catch (ClassNotFoundException e) {
				System.out.println(e);
				MensajesUtils.errorConexion();
				return;
			} catch (SQLException e) {
				System.out.println(e);
				MensajesUtils.errorFuncional("REALIZAR LA INSCRIPCIÓN");
				return;
			}
			mostrarTablaEstudiantes();
			if (getAsistentes() >= erasmus.getCapacidad()) {
				MensajesUtils.erasmusCompleto();
				new MenuErasmusView();
				dispose();
			}
		} else {
			MensajesUtils.seleccionarFila();
			return;
		}

	}

	public int getAsistentes() {
		int i = 0;
		try {
			i = InscriptionService.contarAsistentes(erasmus, Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return -1;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorFuncional("OBTENER LOS ASISTENTES DEL ERASMUS");
			return -1;
		}
		return i;
	}
}
