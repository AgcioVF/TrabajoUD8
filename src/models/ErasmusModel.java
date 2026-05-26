package models;

public class ErasmusModel {
	private int id, capacidad, duracion;
	private String destino, fecha, imagen, universidad;
	private int asistentes = 0;

	public ErasmusModel() {
		super();
	}

	public ErasmusModel(int id, String destino, String universidad, String fecha, String imagen, int capacidad,
			int duracion, int asistentes) {
		super();
		this.id = id;
		this.destino = destino;
		this.universidad = universidad;
		this.fecha = fecha;
		this.imagen = imagen;
		this.capacidad = capacidad;
		this.duracion = duracion;
		this.asistentes = asistentes;
	}

	public ErasmusModel(String destino, String universidad, String fecha, String imagen, int capacidad, int duracion) {
		super();
		this.destino = destino;
		this.universidad = universidad;
		this.fecha = fecha;
		this.imagen = imagen;
		this.capacidad = capacidad;
		this.duracion = duracion;
	}

	public int getId() {
		return id;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public int getDuracion() {
		return duracion;
	}

	public String getDestino() {
		return destino;
	}

	public String getFecha() {
		return fecha;
	}

	public String getImagen() {
		return imagen;
	}

	public String getUniversidad() {
		return universidad;
	}

	public int getAsistentes() {
		return asistentes;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public void setAsistentes(int asistentes) {
		this.asistentes = asistentes;
	}

	@Override
	public String toString() {
		return "ErasmusModel [id=" + id + ", capacidad=" + capacidad + ", duracion=" + duracion + ", destino=" + destino
				+ ", fecha=" + fecha + ", imagen=" + imagen + ", universidad=" + universidad + ", asistentes="
				+ asistentes + "]";
	}

}
