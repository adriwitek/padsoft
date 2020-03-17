package BP;

import java.util.Date;

public class ProyectoSocial extends Proyecto{
	private Boolean Nacional;
	private String GrupoSocial;
	
	public ProyectoSocial(String nombre, String descripcionLarga, String descripcionCorta, Date fechaCreacion, Date fechaUltimoApoyo, Integer idProponenteCreador, Boolean nac,
			Double coste, Double financiacionRecibida, String gsocial) {
		super(nombre, descripcionLarga, descripcionCorta, fechaCreacion, fechaUltimoApoyo, idProponenteCreador, coste, financiacionRecibida);
		GrupoSocial = gsocial; Nacional = nac;
	}
	
}
