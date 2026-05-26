package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.ErasmusModel;
import models.EstudianteModel;

public class ErasmusService {
	private static final String tabla = "erasmus";

	public static void insertarErasmus(ErasmusModel erasmus, Connection conexion) throws SQLException {
		PreparedStatement consulta = conexion.prepareStatement(
				"INSERT INTO " + tabla + " (destino,universidad,fecha,imagen,capacidad,duracion) VALUES (?,?,?,?,?,?)");
		consulta.setString(1, erasmus.getDestino());
		consulta.setString(2, erasmus.getUniversidad());
		consulta.setString(3, erasmus.getFecha());
		consulta.setString(4, erasmus.getImagen());
		consulta.setInt(5, erasmus.getCapacidad());
		consulta.setInt(6, erasmus.getDuracion());

		consulta.executeUpdate();
	}

	public static List<ErasmusModel> getErasmusPorDestino(String destino, Connection conexion) throws SQLException {
		List<ErasmusModel> erasmusLista = new ArrayList<>();
		PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM " + tabla + " WHERE destino LIKE ?");
		consulta.setString(1, destino + "%");

		ResultSet resultado = consulta.executeQuery();

		while (resultado.next()) {
			erasmusLista.add(new ErasmusModel(resultado.getInt("id"), resultado.getString("destino"),
					resultado.getString("universidad"), resultado.getString("fecha"), resultado.getString("imagen"),
					resultado.getInt("capacidad"), resultado.getInt("duracion")));
		}

		return erasmusLista;
	}

	public static List<ErasmusModel> getErasmusPorDestino(String destino, EstudianteModel estudiante,
			Connection conexion) throws SQLException {
		List<ErasmusModel> erasmusLista = new ArrayList<>();
		PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM " + tabla
				+ " e WHERE destino LIKE ? AND id NOT IN (SELECT id_erasmus FROM inscripciones WHERE id_estudiante = ?) AND (SELECT Count(*) FROM inscripciones i WHERE i.id_erasmus = e.id) < e.capacidad");
		consulta.setString(1, destino + "%");
		consulta.setInt(2, estudiante.getId());

		ResultSet resultado = consulta.executeQuery();

		while (resultado.next()) {
			erasmusLista.add(new ErasmusModel(resultado.getInt("id"), resultado.getString("destino"),
					resultado.getString("universidad"), resultado.getString("fecha"), resultado.getString("imagen"),
					resultado.getInt("capacidad"), resultado.getInt("duracion")));
		}

		return erasmusLista;
	}

	public static void eliminarErasmus(ErasmusModel erasmus, Connection conexion) throws SQLException {
		PreparedStatement consulta = conexion.prepareStatement("DELETE FROM " + tabla + " WHERE id = ?");
		consulta.setInt(1, erasmus.getId());
		consulta.executeUpdate();
	}

	public static List<ErasmusModel> getAllErasmus(Connection conexion) throws SQLException {
		List<ErasmusModel> erasmusLista = new ArrayList<>();
		PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM " + tabla);
		ResultSet resultado = consulta.executeQuery();

		while (resultado.next()) {
			erasmusLista.add(new ErasmusModel(resultado.getInt("id"), resultado.getString("destino"),
					resultado.getString("universidad"), resultado.getString("fecha"), resultado.getString("imagen"),
					resultado.getInt("capacidad"), resultado.getInt("duracion")));
		}
		return erasmusLista;
	}

	public static List<ErasmusModel> getErasmusUser(EstudianteModel estudiante, Connection conexion)
			throws SQLException {
		List<ErasmusModel> lista = new ArrayList<>();
		PreparedStatement consulta = conexion.prepareStatement(
				"SELECT * FROM erasmus WHERE id IN (SELECT id_erasmus FROM inscripciones WHERE id_estudiante = ?)");
		consulta.setInt(1, estudiante.getId());
		ResultSet resultado = consulta.executeQuery();

		while (resultado.next()) {
			lista.add(new ErasmusModel(resultado.getInt("id"), resultado.getString("destino"),
					resultado.getString("universidad"), resultado.getString("fecha"), resultado.getString("imagen"),
					resultado.getInt("capacidad"), resultado.getInt("duracion")));
		}

		return lista;
	}

	public static List<ErasmusModel> getErasmusSinEsteEstudiante(EstudianteModel estudiante, Connection conexion)
			throws SQLException {
		List<ErasmusModel> lista = new ArrayList<>();
		PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM " + tabla
				+ " e WHERE id NOT IN (SELECT id_erasmus FROM inscripciones WHERE id_estudiante = ?) AND (SELECT Count(*) FROM inscripciones i WHERE i.id_erasmus = e.id) < e.capacidad");
		consulta.setInt(1, estudiante.getId());
		ResultSet resultado = consulta.executeQuery();

		while (resultado.next()) {
			lista.add(new ErasmusModel(resultado.getInt("id"), resultado.getString("destino"),
					resultado.getString("universidad"), resultado.getString("fecha"), resultado.getString("imagen"),
					resultado.getInt("capacidad"), resultado.getInt("duracion")));
		}

		return lista;
	}

}
