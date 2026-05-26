package Utils;

import java.time.LocalDate;

public class ValidacionesUtils {

	public static String validarFecha(String fechaErasmus) {
		if (fechaErasmus.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
			return null;
		}
		return "FORMATO DE FECHA INCORRECTO (DD/MM/YYYY)";
	}

	public static String validarFechaAvanzado(String fechaErasmus) {
		if (!fechaErasmus.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
			return validarFecha(fechaErasmus);
		}
		String[] campos = fechaErasmus.split("/");
		int dias = Integer.parseInt(campos[0]);
		int mes = Integer.parseInt(campos[1]);
		int year = Integer.parseInt(campos[2]);

		if (mes < 1 || mes > 12) {
			return "MES NO VÁLIDO";
		}

		if (dias < 1) {
			return "DÍA NO VÁLIDO";
		}

		switch (mes) {
		case 1 -> {
			if (dias > 31) {
				return "ENERO TIENE 31 DÍAS";
			}
		}
		case 2 -> {
			if (dias > 28) {
				return "FEBRERO TIENE 28 DÍAS";
			}
		}
		case 3 -> {
			if (dias > 31) {
				return "MARZO TIENE 31 DÍAS";
			}
		}
		case 4 -> {
			if (dias > 30) {
				return "ABRIL TIENE 30 DÍAS";
			}
		}
		case 5 -> {
			if (dias > 31) {
				return "MAYO TIENE 31 DÍAS";
			}
		}
		case 6 -> {
			if (dias > 30) {
				return "JUNIO TIENE 30 DÍAS";
			}
		}
		case 7 -> {
			if (dias > 31) {
				return "JULIO TIENE 31 DÍAS";
			}
		}
		case 8 -> {
			if (dias > 31) {
				return "AGOSTO TIENE 31 DÍAS";
			}
		}
		case 9 -> {
			if (dias > 30) {
				return "SEPTIEMBRE TIENE 30 DÍAS";
			}
		}
		case 10 -> {
			if (dias > 31) {
				return "OCTUBRE TIENE 31 DÍAS";
			}
		}
		case 11 -> {
			if (dias > 30) {
				return "NOVIEMBRE TIENE 30 DÍAS";
			}
		}
		case 12 -> {
			if (dias > 31) {
				return "DICIEMBRE TIENE 31 DÍAS";
			}
		}
		default -> {
			return "CAMPO MES NO CONTROLADO";
		}
		}
		if (year < LocalDate.now().getYear()) {
			return "NO ES POSIBLE SELECCIONAR UN AÑO PREVIO AL ACTUAL";
		}

		return null;
	}

	public static String aforoPositivo(String aforoErasmus) {
		if (!aforoErasmus.matches("^\\d+$")) {
			return "EL AFORO DEBE SER UN NÚMERO POSITIVO";
		}
		return null;
	}

	public static String validarDuracion(String duracionErasmus) {
		if (!duracionErasmus.matches("^\\d+$")) {
			return "LA DURACIÓN DEBE SER UN NÚMERO";
		}
		int duracion = Integer.parseInt(duracionErasmus);
		if (duracion <= 0) {
			return "LA DURACIÓN DEBE SER POSITIVA";
		}
		if (duracion < 2) {
			return "LA DURACIÓN MÍNIMA ES DE 2 MESES";
		}
		if (duracion > 12) {
			return "LA DURACIÓN MÁXIMA ES DE 12 MESES";
		}
		return null;
	}

	public static String validarErasmus(String fecha, String aforo, String duracion) {

		if (validarFechaAvanzado(fecha) != null) {
			return validarFechaAvanzado(fecha);
		}
		if (validarDuracion(duracion) != null) {
			return validarDuracion(duracion);
		}
		if (aforoPositivo(aforo) != null) {
			return aforoPositivo(aforo);
		}
		return null;
	}

	public static String validarEstudiante(int contEmail, String email, String edad) {
		if (emailUnico(contEmail) != null) {
			return emailUnico(contEmail);
		}
		if (validarFormatoEmail(email) != null) {
			return validarFormatoEmail(email);
		}
		if (validarEdad(edad) != null) {
			return validarEdad(edad);
		}

		return null;
	}

	public static String emailUnico(int contEmail) {
		if (contEmail != 0) {
			return "EMAIL YA EXISTENTE";
		}

		return null;
	}

	public static String validarFormatoEmail(String emailEstudiante) {
		if (emailEstudiante.matches("^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			return null;
		}
		return "FORMADO DE EMAIL INVALIDO";
	}

	public static String validarEdad(String edadEstudiante) {
		if (!edadEstudiante.matches("^\\d+$")) {
			return "LA EDAD DEBE SER UN NÚMERO";
		}
		int edad = Integer.parseInt(edadEstudiante);

		if (edad < 18) {
			return "LA EDAD MÍNIMA ES 18 AÑOS";
		}
		if (edad > 120) {
			return "NI LA ACTUAL PERSONA MÁS LONGEVA TIENE ESA EDAD";
		}

		return null;
	}

}
