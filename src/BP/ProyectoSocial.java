package BP;

import java.util.Date;

public class ProyectoSocial extends Proyecto{
	private Boolean nacional;
	private String grupoSocial;
	
	public ProyectoSocial(Proponente p, Usuario uCreador,String nombre, String descrL, String descC , double cost ,String gSocial, Boolean nac) {
		
		super(p, uCreador, nombre, descrL, descC, cost);
		this.grupoSocial = gSocial;
		this.nacional = nac;
		 
	}
	
}
