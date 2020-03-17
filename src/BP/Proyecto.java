package BP;

import java.util.Date;

public abstract class Proyecto {
	private String nombre;
	private String descripcionLarga, descripcionCorta;
	private Date fechaCreacion, fechaUltimoApoyo;
	private int idProponenteCreador;
	private double coste, financiacionRecibida;
	public Proyecto(String nomb, String descl, String descc, Date fechac, Date fechaua, int proponente, double cost, double finrec) {
		nombre = nomb; descripcionLarga = decl; descripcionCorta = descc; fechaCreacion = fechac; fechaUltimoApoyo = fechaua;
	}
}
