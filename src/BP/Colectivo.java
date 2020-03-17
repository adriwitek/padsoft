package BP;

public class Colectivo extends Proponente{
	private String Nombre;
	private String IdUsuarioRepresentanteDeColectivo;
	private String IDColectivo;
	
	public Colectivo(String nom, String idrepresentante, String idc) {
		Nombre = nom; IdUsuarioRepresentanteDeColectivo = idrepresentante; IDColectivo = idc;
	}
}
