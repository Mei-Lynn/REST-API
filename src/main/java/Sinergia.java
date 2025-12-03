public class Sinergia {

	private String nombre;
	private String descripcion;
	private String calidad;
	private String recomendacion; // NUEVO

	public Sinergia(String nombre, String descripcion, String calidad, String recomendacion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.calidad = calidad;
		this.recomendacion = recomendacion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getCalidad() {
		return calidad;
	}

	public String getRecomendacion() {
		return recomendacion;
	}
}
