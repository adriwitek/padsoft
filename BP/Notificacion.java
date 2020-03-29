package BP;

/** 
* 
* @author Adián Rubio adrian.rubiop@estudiante.uam.es, Guillermo Solla guillermo.solla@estudiante.uam.es 
* y Diego Tapia diego.tapias@estudiante.uam.es
*/

import java.util.Date;

public class Notificacion {
	
	private String titulo;
	private String descripcion;
	private Date fechaCreacion;
	
	 /**
	  * Constructor, con los datos a implementar
	  * @param titulo que asignar a la notificación
	  * @param descripción para asignar a la notificación
	  */
	
	public Notificacion(String titulo, String descripcion ) {
		this.setTitulo(titulo);
		this.setDescripcion(descripcion);
		this.fechaCreacion = new Date();
		
	}

	
	
	/**
	 * 
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * 
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * 
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * 
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	
	
}
