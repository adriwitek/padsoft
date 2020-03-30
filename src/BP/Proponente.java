package BP;

/** 
* 
* @author Adián Rubio adrian.rubiop@estudiante.uam.es, Guillermo Solla guillermo.solla@estudiante.uam.es 
* y Diego Tapia diego.tapias@estudiante.uam.es
*/

import java.util.HashSet;

public abstract class Proponente implements java.io.Serializable {
	private HashSet<Proyecto> proyectosPropuestos;

	 /**
	  * Constructor, sin datos a implementar
	  */
	public Proponente() {
		this.proyectosPropuestos = new HashSet<Proyecto>();

	}
	
	/**
	 * Esta funcion permite a un proponente proponer un proyecto
	 * 
	 * @param p
	 */
	public void proponerProyecto(Proyecto p) {
		
		this.proyectosPropuestos.add(p);

	}
	
	

	
}
