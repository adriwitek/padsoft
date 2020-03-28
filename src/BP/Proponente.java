package BP;

/** 
* 
* @author Adián Rubio adrian.rubiop@estudiante.uam.es, Guillermo Solla guillermo.solla@estudiante.uam.es 
* y Diego Tapia diego.tapias@estudiante.uam.es
*/

import java.util.HashSet;

public abstract class Proponente {
	private HashSet<Proyecto> proyectosPropuestos;

	 /**
	  * Constructor, sin datos a implementar
	  */
	public Proponente() {
		this.proyectosPropuestos = new HashSet<Proyecto>();

	}
	
	/**
	 * 
	 * @param p
	 */
	public void proponerProyecto(Proyecto p) {
		
		this.proyectosPropuestos.add(p);

	}
	
	

	
}
