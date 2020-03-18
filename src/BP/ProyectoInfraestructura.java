package BP;

import java.util.Date;

public class ProyectoInfraestructura extends Proyecto {
	private String distrito;
	private String croquis;
	private String imagen;
	
	public ProyectoInfraestructura(Proponente p, Usuario uCreador,String nombre, String descrL, String descC , double cost,String dist,String croquis ,String imagen) {
		
		super(p, uCreador, nombre, descrL, descC, cost);
		this.distrito = dist;
		this.croquis = croquis; 
		this.imagen = imagen;
	}
	
	
	
	
}
