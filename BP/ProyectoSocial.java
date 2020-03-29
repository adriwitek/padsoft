package BP;

import es.uam.eps.sadp.grants.GrantRequest;

public class ProyectoSocial extends Proyecto {
	private Boolean nacional;
	private String grupoSocial;
	public ProyectoSocial(Proponente p, Usuario uCreador,String nombre, String descrL, String descC , double cost ,String gSocial, Boolean nac) {
		
		super(p, uCreador, nombre, descrL, descC, cost);
		this.grupoSocial = gSocial;
		this.nacional = nac;
		 
	}
	
	
	
	public String getExtraData() {
		String cadena = "El proyecto ";
		if(this.nacional) {
			cadena += "es nacional ";
		}else {
			cadena += "NO es nacional ";
		}
		
		cadena += "El grupo social del proyecto es :" + this.grupoSocial;
		return cadena;
	}
	
	
	public GrantRequest.ProjectKind getProjectKind(){
		return GrantRequest.ProjectKind.Social;
	}
	
}
