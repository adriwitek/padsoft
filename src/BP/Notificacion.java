package BP;

import java.util.Date;

public class Notificacion {
	
	private String titulo;
	private String descripcion;
	private Date fechaCreacion;
	
	
	public Notificacion(String titulo, String descripcion ) {
		this.setTitulo(titulo);
		this.setDescripcion(descripcion);
		this.fechaCreacion = new Date();
		
	}

	
	
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	
	
}
