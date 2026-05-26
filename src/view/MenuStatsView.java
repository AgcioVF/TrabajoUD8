package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import Utils.MensajesUtils;
import control.Conexion;
import models.ErasmusModel;
import services.ErasmusService;
import services.InscriptionService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MenuStatsView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, panelEstadistica;
	private List<ErasmusModel> listaErasmus;
	private JButton bUsuariosPorEvento, bEventosPorEdad, bSalir;
	private JTextField intervalo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuStatsView frame = new MenuStatsView();
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
	public MenuStatsView() {
		setTitle("Estadísticas");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		bUsuariosPorEvento = new JButton("Estudiantes por evento");
		bUsuariosPorEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarEstudiantesPorEvento();
			}
		});
		bUsuariosPorEvento.setToolTipText("Muestra una gráfica de los usuarios por evento");
		bUsuariosPorEvento.setFont(new Font("Arial", Font.PLAIN, 10));
		bUsuariosPorEvento.setBounds(99, 15, 139, 50);
		contentPane.add(bUsuariosPorEvento);

		bEventosPorEdad = new JButton("Eventos por rango de edad");
		bEventosPorEdad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarEventosPorEdad();
			}
		});
		bEventosPorEdad.setToolTipText("Muestra una gráfica de los eventos por rango de edad");
		bEventosPorEdad.setFont(new Font("Arial", Font.PLAIN, 10));
		bEventosPorEdad.setBounds(337, 15, 155, 50);
		contentPane.add(bEventosPorEdad);

		bSalir = new JButton("Volver");
		bSalir.setToolTipText("Regresa al menu principal");
		bSalir.setFont(new Font("Arial", Font.PLAIN, 10));
		bSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuAdminView();
				dispose();
			}
		});
		bSalir.setBounds(330, 628, 75, 25);
		bSalir.setBackground(new Color(186, 199, 252));
		bSalir.setContentAreaFilled(false);
		bSalir.setOpaque(true);
		bSalir.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(bSalir);

		panelEstadistica = new JPanel();
		panelEstadistica.setBounds(18, 80, 708, 538);
		contentPane.add(panelEstadistica);

		JLabel lblNewLabel = new JLabel("Intervalo");
		lblNewLabel.setBounds(591, 15, 44, 19);
		contentPane.add(lblNewLabel);

		intervalo = new JTextField();
		intervalo.setBounds(591, 47, 44, 18);
		contentPane.add(intervalo);
		intervalo.setColumns(10);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void mostrarEventosPorEdad() {
		String interv = intervalo.getText().trim();

		if (interv.isEmpty()) {
			JOptionPane.showMessageDialog(null, "INTRODUZCA EL RANGO DE EDAD", "INTERVALO VACIO",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		panelEstadistica.removeAll();
		int nIntervalo = Integer.parseInt(interv);
		DefaultCategoryDataset datos = new DefaultCategoryDataset();
		for (int i = 18; i <= 120; i += nIntervalo) {
			int limInf = i;
			int limSup = i + nIntervalo;

			int valor = 0;
			try {
				valor = InscriptionService.contarErasmusPorEdad(limInf, limSup, Conexion.obtener());
			} catch (ClassNotFoundException e) {
				System.out.println(e);
				MensajesUtils.errorConexion();
				return;
			} catch (SQLException e) {
				System.out.println(e);
				MensajesUtils.errorBaseDatos();
				return;
			}
			if (valor != 0) {
				String texto = limInf + "-" + limSup;
				datos.addValue(valor, "ERASMUS", texto);

			}
		}
		JFreeChart grafica = ChartFactory.createBarChart("ERASMUS POR RANGO DE EDAD", "RANGO", "Nº ERASMUS", datos,
				PlotOrientation.VERTICAL, false, false, false);

		ChartPanel panelGrafica = new ChartPanel(grafica);
		panelEstadistica.add(panelGrafica);
		panelEstadistica.setLayout(new java.awt.BorderLayout());
		panelEstadistica.add(panelGrafica, java.awt.BorderLayout.CENTER);
		panelEstadistica.revalidate();
		panelEstadistica.repaint();
	}

	public void mostrarEstudiantesPorEvento() {
		panelEstadistica.removeAll();
		setListaErasmus();

		DefaultCategoryDataset datos = new DefaultCategoryDataset();

		for (ErasmusModel e : listaErasmus) {
			int asistentes = 0;
			try {
				asistentes = InscriptionService.contarAsistentes(e, Conexion.obtener());
			} catch (ClassNotFoundException ex) {
				System.out.println(ex);
				MensajesUtils.errorConexion();
				return;
			} catch (SQLException ex) {
				System.out.println(ex);
				MensajesUtils.errorFuncional("CONTAR LOS ASISTENTES DEL ERASMUS");
				return;
			}
			String texto = e.getDestino() + "(" + e.getUniversidad() + ")";
			datos.setValue(asistentes, "ASISTENTES", texto);
		}

		JFreeChart grafica = ChartFactory.createBarChart("ESTUDIANTES POR ERASMUS", "ERASMUS", "ESTUDIANTES", datos,
				PlotOrientation.VERTICAL, false, false, false);

		ChartPanel panelGrafica = new ChartPanel(grafica);
		panelEstadistica.add(panelGrafica);
		panelEstadistica.setLayout(new java.awt.BorderLayout());
		panelEstadistica.add(panelGrafica, java.awt.BorderLayout.CENTER);
		panelEstadistica.revalidate();
		panelEstadistica.repaint();
	}

	public void setListaErasmus() {
		try {
			listaErasmus = ErasmusService.getAllErasmus(Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorBaseDatosTabla();
			return;
		}
	}
}
