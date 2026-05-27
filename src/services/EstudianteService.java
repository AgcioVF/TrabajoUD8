package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.ErasmusModel;
import models.EstudianteModel;

public class EstudianteService {
	private static final String tabla = "estudiantes";

	public static void existeAdmin(Connection conexion) throws SQLException {
		int cantAdmin = 0;

		PreparedStatement consultaExisteAdmin = conexion
				.prepareStatement("SELECT Count(*) FROM " + tabla + "  WHERE role = 'ADMIN'");

		ResultSet resultadoCantAdmins = consultaExisteAdmin.executeQuery();

		if (resultadoCantAdmins.next()) {
			cantAdmin = resultadoCantAdmins.getInt(1);
		}

		if (cantAdmin <= 0) {
			PreparedStatement consultaAdmin = conexion.prepareStatement("INSERT INTO " + tabla
					+ " (nombre,email,edad,role, contrasena) VALUES ('Admin','admin@gmail.com',21,'ADMIN',1234)");
			consultaAdmin.executeUpdate();
		}
	}

	public static void insertarEstudiante(EstudianteModel estudiante, Connection conexion) throws SQLException {
		PreparedStatement consulta = conexion
				.prepareStatement("INSERT INTO " + tabla + " (nombre,email,edad,role,contrasena) VALUES (?,?,?,?,?)");
		consulta.setString(1, estudiante.getNombre());
		consulta.setString(2, estudiante.getEmail());
		consulta.setInt(3, estudiante.getEdad());
		consulta.setString(4, estudiante.getRole());
		consulta.setString(5, estudiante.getContrasena());

		consulta.executeUpdate();
	}

	public static EstudianteModel existeEstudiante(String nombre, String cntr, Connection conexion)
			throws SQLException {
		PreparedStatement consulta = conexion
				.prepareStatement("SELECT * FROM " + tabla + " WHERE nombre = ? AND contrasena = ?");
		consulta.setString(1, nombre);
		consulta.setString(2, cntr);

		ResultSet resultado = consulta.executeQuery();
		if (resultado.next()) {
			return new EstudianteModel(resultado.getInt("id"), resultado.getString("nombre"),
					resultado.getString("email"), resultado.getInt("edad"), resultado.getString("role"),
					resultado.getString("contrasena"));
		}
		return null;
	}

	public static int existeEmail(String email, Connection conexion) throws SQLException {
		PreparedStatement consulta = conexion.prepareStatement("SELECT Count(*) FROM " + tabla + " WHERE email = ?");
		consulta.setString(1, email);

		ResultSet resultado = consulta.executeQuery();
		if (resultado.next()) {
			return resultado.getInt(1);
		}
		return 0;
	}

	public static List<EstudianteModel> getEstudiantePorNombre(String nombre, Connection conexion) throws SQLException {
		List<EstudianteModel> estudiantesLista = new ArrayList<>();
		PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM " + tabla + " WHERE nombre LIKE ?");
		consulta.setString(1, nombre + "%");

		ResultSet resultado = consulta.executeQuery();

		while (resultado.next()) {
			estudiantesLista.add(new EstudianteModel(resultado.getInt("id"), resultado.getString("nombre"),
					resultado.getString("email"), resultado.getInt("edad"), resultado.getString("role"),
					resultado.getString("contrasena")));
		}

		return estudiantesLista;
	}

	public static void eliminarEstudiante(EstudianteModel estudiante, Connection conexion) throws SQLException {
		PreparedStatement consulta = conexion.prepareStatement("DELETE FROM " + tabla + " WHERE id = ?");
		consulta.setInt(1, estudiante.getId());
		consulta.executeUpdate();
	}

	public static List<EstudianteModel> getAllEstudiantes(Connection conexion) throws SQLException {
		List<EstudianteModel> estudiantesLista = new ArrayList<>();
		PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM " + tabla);

		ResultSet resultado = consulta.executeQuery();

		while (resultado.next()) {
			estudiantesLista.add(new EstudianteModel(resultado.getInt("id"), resultado.getString("nombre"),
					resultado.getString("email"), resultado.getInt("edad"), resultado.getString("role"),
					resultado.getString("contrasena")));
		}

		return estudiantesLista;
	}

	public static List<EstudianteModel> getEstudiantesNoInscritos(ErasmusModel erasmus, Connection conexion)
			throws SQLException {
		List<EstudianteModel> estudiantesLista = new ArrayList<>();
		PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM " + tabla
				+ " WHERE id NOT IN (SELECT id_estudiante FROM inscripciones WHERE id_erasmus = ?) AND role = 'USER'");
		consulta.setInt(1, erasmus.getId());
		ResultSet resultado = consulta.executeQuery();

		while (resultado.next()) {
			estudiantesLista.add(new EstudianteModel(resultado.getInt("id"), resultado.getString("nombre"),
					resultado.getString("email"), resultado.getInt("edad"), resultado.getString("role"),
					resultado.getString("contrasena")));
		}

		return estudiantesLista;
	}

	public static List<EstudianteModel> getEstudiantesEnEsteErasmus(ErasmusModel erasmus, Connection conexion)
			throws SQLException {
		List<EstudianteModel> estudiantesLista = new ArrayList<>();
		PreparedStatement consulta = conexion.prepareStatement(
				"SELECT * FROM estudiantes WHERE id IN (SELECT id_estudiante FROM inscripciones WHERE id_erasmus = ?)");
		consulta.setInt(1, erasmus.getId());
		ResultSet resultado = consulta.executeQuery();

		while (resultado.next()) {
			estudiantesLista.add(new EstudianteModel(resultado.getInt("id"), resultado.getString("nombre"),
					resultado.getString("email"), resultado.getInt("edad"), resultado.getString("role"),
					resultado.getString("contrasena")));
		}

		return estudiantesLista;
	}
}
