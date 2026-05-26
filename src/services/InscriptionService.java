package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.ErasmusModel;
import models.EstudianteModel;

public class InscriptionService {
	private static final String tabla = "inscripciones";

	public static void insertarInscripcion(ErasmusModel erasmus, EstudianteModel estudiante, Connection conexion)
			throws SQLException {
		PreparedStatement consulta = conexion
				.prepareStatement("INSERT INTO " + tabla + " (id_erasmus,id_estudiante) VALUES (?,?)");
		consulta.setInt(1, erasmus.getId());
		consulta.setInt(2, estudiante.getId());

		consulta.executeUpdate();
	}

	public static void eliminarInscripcion(ErasmusModel erasmus, EstudianteModel estudiante, Connection conexion)
			throws SQLException {
		PreparedStatement consulta = conexion
				.prepareStatement("DELETE FROM " + tabla + " WHERE id_erasmus = ? AND id_estudiante = ?");
		consulta.setInt(1, erasmus.getId());
		consulta.setInt(2, estudiante.getId());

		consulta.executeUpdate();
	}

	public static int contarAsistentes(ErasmusModel erasmus, Connection conexion) throws SQLException {
		PreparedStatement consulta = conexion
				.prepareStatement("SELECT Count(*) FROM " + tabla + " WHERE id_erasmus = ?");
		consulta.setInt(1, erasmus.getId());
		ResultSet resultado = consulta.executeQuery();
		if (resultado.next()) {
			return resultado.getInt(1);
		}
		return 0;
	}

	public static int contarErasmusPorEdad(int inf, int sup, Connection conexion) throws SQLException {
		PreparedStatement consulta = conexion.prepareStatement("SELECT Count(*) FROM " + tabla
				+ " WHERE id_estudiante IN (SELECT id FROM estudiantes WHERE edad >= ? AND edad < ?)");
		consulta.setInt(1, inf);
		consulta.setInt(2, sup);
		ResultSet resultado = consulta.executeQuery();

		if (resultado.next()) {
			return resultado.getInt(1);
		}
		return 0;
	}

}
