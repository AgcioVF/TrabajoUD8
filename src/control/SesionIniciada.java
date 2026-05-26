package control;

import models.EstudianteModel;

public class SesionIniciada {
	private static EstudianteModel estudianteIniciado;

	public static void setEstudianteIniciado(EstudianteModel estudiante) {
		estudianteIniciado = estudiante;
	}

	public static EstudianteModel getEstudianteIniciado() {
		return estudianteIniciado;
	}

	public static void cerrarSesion() {
		estudianteIniciado = null;
	}

}
