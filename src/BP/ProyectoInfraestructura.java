package BP;

import java.util.Date;

public class ProyectoInfraestructura extends Proyecto {
	private String Distrito;
	private String Croquis;
	private String Imagen;
	
	public ProyectoInfraestructura(String nombre, String descripcionLarga, String descripcionCorta, Date fechaCreacion, Date fechaUltimoApoyo, Integer idProponenteCreador, String dist,
			Double coste, Double financiacionRecibida, String croquis, String imagen) {
		super(nombre, descripcionLarga, descripcionCorta, fechaCreacion, fechaUltimoApoyo, idProponenteCreador, coste, financiacionRecibida);
		Distrito = dist; Croquis = croquis; Imagen = imagen;
	}
}
