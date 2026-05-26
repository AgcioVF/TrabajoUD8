package models;

public class InscriptionModel {
	private int id_inscripcion, id_erasmus, id_estudiante;

	public InscriptionModel() {
		super();
	}

	public InscriptionModel(int id_erasmus, int id_estudiante) {
		super();
		this.id_erasmus = id_erasmus;
		this.id_estudiante = id_estudiante;
	}

	public InscriptionModel(int id_inscripcion, int id_erasmus, int id_estudiante) {
		super();
		this.id_inscripcion = id_inscripcion;
		this.id_erasmus = id_erasmus;
		this.id_estudiante = id_estudiante;
	}

	public int getId_inscripcion() {
		return id_inscripcion;
	}

	public int getId_erasmus() {
		return id_erasmus;
	}

	public int getId_estudiante() {
		return id_estudiante;
	}

	public void setId_inscripcion(int id_inscripcion) {
		this.id_inscripcion = id_inscripcion;
	}

	public void setId_erasmus(int id_erasmus) {
		this.id_erasmus = id_erasmus;
	}

	public void setId_estudiante(int id_estudiante) {
		this.id_estudiante = id_estudiante;
	}

	@Override
	public String toString() {
		return "InscriptionModel [id_inscripcion=" + id_inscripcion + ", id_erasmus=" + id_erasmus + ", id_estudiante="
				+ id_estudiante + "]";
	}

}
