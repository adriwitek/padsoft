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
	 * Esta funcion devuelve el titulo de una notificacion
	 * 
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * Esta funcion introduce el titulo a un notificacion
	 * 
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * Esta funcion devuelve la descripcion de una funcion
	 * 
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Esta funcion introduce una descripcion a una notificacion especifica
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Esta funcion devuelve la fecha de creacion de la notificacion
	 * 
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	
	
}
