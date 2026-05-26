package Utils;

import javax.swing.JOptionPane;

public class MensajesUtils {
	public static void errorConexion() {
		JOptionPane.showMessageDialog(null, "CONEXIÓN CON LA BASE DE DATOS FALLIDA", "ERROR DE CONEXIÓN",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void errorBaseDatos() {
		JOptionPane.showMessageDialog(null, "ACCESO A LA BASE DE DATOS FALLIDO", "ERROR DE ACCESO",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void errorBaseDatosTabla() {
		JOptionPane.showMessageDialog(null, "ACCESO A LA BASE DE DATOS FALLIDO\nNO SE HA PODIDO CARGAR LA TABLA",
				"ERROR DE ACCESO", JOptionPane.ERROR_MESSAGE);
	}

	public static void errorCampoVacio() {
		JOptionPane.showMessageDialog(null, "ES NECESARIO RELLENAR TODOS LOS CAMPOS", "ERROR DE CAMPO VACÍO",
				JOptionPane.WARNING_MESSAGE);
	}

	public static void errorLogin() {
		JOptionPane.showMessageDialog(null, "USUARIO NO EXISTENTE", "ERROR LOGIN", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void errorValidacion(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, "ERROR CAMPO INVALIDO", JOptionPane.WARNING_MESSAGE);
	}

	public static void registroErasmusExitoso() {
		JOptionPane.showMessageDialog(null, "ERASMUS CREADO CON EXITO", "REGISTRO EXITOSO",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void registroEstudianteExitoso() {
		JOptionPane.showMessageDialog(null, "ESTUDIANTE CREADO CON EXITO", "REGISTRO EXITOSO",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void seleccionarFila() {
		JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "FILA NO SELECCIONADA",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void errorImagen() {
		JOptionPane.showMessageDialog(null, "NO SE HA PODIDO CARGAR LA IMAGEN", "ERROR IMAGEN",
				JOptionPane.ERROR_MESSAGE);
	}

	public static int confirmarLogout() {
		return JOptionPane.showConfirmDialog(null, "¿QUIERE CERRAR SESIÓN?", "CONFIRMAR LOGOUT",
				JOptionPane.YES_NO_OPTION);
	}

	public static int confirmarEliminarErasmus() {
		return JOptionPane.showConfirmDialog(null, "¿QUIERE PROCEDER CON LA ELIMINACIÓN DE ESTE ERASMUS?",
				"CONFIRMAR ELIMINACIÓN", JOptionPane.YES_NO_OPTION);
	}

	public static int confirmarEliminarEstudiante() {
		return JOptionPane.showConfirmDialog(null, "¿QUIERE PROCEDER CON LA ELIMINACIÓN DE ESTE ESTUDIANTE?",
				"CONFIRMAR ELIMINACIÓN", JOptionPane.YES_NO_OPTION);
	}

	public static int confirmarInscripcion() {
		return JOptionPane.showConfirmDialog(null, "¿QUIERE PROCEDER CON LA INSCRIPCIÓN A ESTE ERASMUS?",
				"CONFIRMAR INSCRIPCIÓN", JOptionPane.YES_NO_OPTION);
	}

	public static int confirmarCancelacion() {
		return JOptionPane.showConfirmDialog(null, "¿QUIERE PROCEDER CON LA CANCELACIÓN DE ESTA INSCRIPCIÓN?",
				"CONFIRMAR CANCELACIÓN", JOptionPane.YES_NO_OPTION);
	}

	public static void erasmusCompleto() {
		JOptionPane.showMessageDialog(null, "ESTE ERASMUS YA SE ESTÁ COMPLETO\nSELECCIONE OTRO", "ERASMUS COMPLETO",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void erasmusVacio() {
		JOptionPane.showMessageDialog(null, "ESTE ERASMUS CARECE DE ESTUDIANTES INSCRITOS", "ERASMUS VACIO",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void errorEliminarInscripcion() {
		JOptionPane.showMessageDialog(null, "SE HA PRODUCIDO UN FALLO AL ELIMINAR LA INSCRIPCIÓN",
				"ERROR CANCELAR INSCRIPCIÓN", JOptionPane.ERROR_MESSAGE);
	}

	public static void errorRealizarInscripcion() {
		JOptionPane.showMessageDialog(null, "SE HA PRODUCIDO UN FALLO AL REALIZAR LA INSCRIPCIÓN", "ERROR INSCRIPCIÓN",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void errorCrearErasmus() {
		JOptionPane.showMessageDialog(null, "SE HA PRODUCIDO UN FALLO AL CREAR EL ERASMUS", "ERROR CREAR ERASMUS",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void errorCrearEstudiante() {
		JOptionPane.showMessageDialog(null, "SE HA PRODUCIDO UN FALLO AL CREAR EL ESTUDIANTE", "ERROR CREAR ESTUDIANTE",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void errorEliminarErasmus() {
		JOptionPane.showMessageDialog(null, "SE HA PRODUCIDO UN FALLO BORRAR EL ERASMUS", "ERROR BORRAR ERASMUS",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void errorEliminarEstudiante() {
		JOptionPane.showMessageDialog(null, "SE HA PRODUCIDO UN FALLO AL BORRAR EL ESTUDIANTE",
				"ERROR BORRAR ESTUDIANTE", JOptionPane.ERROR_MESSAGE);
	}

}
