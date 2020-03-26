package BP;

import java.util.HashSet;

public abstract class Proponente {
	private HashSet<Proyecto> proyectosPropuestos;

	public Proponente() {
		this.proyectosPropuestos = new HashSet<Proyecto>();

	}
	
	
	public void proponerProyecto(Proyecto p) {
		
		this.proyectosPropuestos.add(p);

	}
	

	
}
