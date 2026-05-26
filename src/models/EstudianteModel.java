package models;

public class EstudianteModel {
	private int id, edad;
	private String nombre, email, role, contrasena;
	

	public EstudianteModel() {
		super();
	}

	public EstudianteModel(int id, String nombre, String email, int edad, String role, String contrasena) {
		super();
		this.id = id;
		this.edad = edad;
		this.nombre = nombre;
		this.email = email;
		this.role = role;
		this.contrasena = contrasena;
	}

	public EstudianteModel(String nombre, String email, int edad, String role, String contrasena) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.role = role;
		this.edad = edad;
		this.contrasena = contrasena;
	}

	public int getId() {
		return id;
	}

	public int getEdad() {
		return edad;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public String toString() {
		return "EstudianteModel [id=" + id + ", edad=" + edad + ", nombre=" + nombre + ", email=" + email + ", role="
				+ role + ", contrasena=" + contrasena + "]";
	}

}
