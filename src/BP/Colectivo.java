package BP;

import java.util.Collections;

/** 
* 
* @author Adián Rubio adrian.rubiop@estudiante.uam.es, Guillermo Solla guillermo.solla@estudiante.uam.es 
* y Diego Tapia diego.tapias@estudiante.uam.es
*/

import java.util.HashSet;
import java.util.Objects;

public class Colectivo extends Proponente{
	private String nombre;
	private Usuario usuarioRepresentanteDeColectivo;
	private Colectivo colectivoPadre;
	private HashSet<Usuario> participantes;
	private HashSet<Colectivo> subcolectivos;
	
	 /**
	  * Constructor, con los datos a implementar
	  * @param nombre que asignar al colectivo 
	  * @param uRepresentante para asignar al usuario que ha creado el colectivo
	  * @param colectivoPadre para asignar al colectivo del que es subcolectivo (en el caso de serlo)
	  */
	
	public Colectivo(Usuario uRepresentante, String nombre,Colectivo colectivoPadre) {
		if(nombre.isEmpty()|| Objects.isNull(uRepresentante)  ||  Objects.isNull(nombre) ) {
				throw new IllegalArgumentException("Debes de introducir los datos validos");
			}
		
		this.nombre = nombre;
		usuarioRepresentanteDeColectivo = uRepresentante;
		this.colectivoPadre = colectivoPadre;
		participantes = new HashSet<>();
		subcolectivos = new HashSet<>();

	}

	
	
	
	
	
	//TODO
	
	
	/**
	 * Esta funcion se encargara de crear un subcolectivo para el colectivo especificado
	 * 
	 * @param nombre
	 * 
	 * @return the c
	 */
	public Colectivo crearSubcolectivo(String nombre) {
		
		Colectivo c = new Colectivo(this.usuarioRepresentanteDeColectivo, nombre,this);		
		this.subcolectivos.add(c);
		return c;
	}
	
	/**
	 * Esta funcion nos dira si un usuario especificado se encuentra en un colectivo o un subcolectivo de este
	 * devolviendo true si contiene el usuario y false en el caso contrario
	 * 
	 * @param u
	 * @return 
	 */
	public Boolean getIsUsuarioEnColectivoSubcolectivo(Usuario u) {
		
		if(this.participantes.contains(u)){
			return true;
		}else {
			for(@SuppressWarnings("unused") Colectivo c: this.subcolectivos) {
				 c.getIsUsuarioEnColectivoSubcolectivo(u);
			}
		}
		return false;
	}
	
	/**
	 * Esta funcion se encargara de eliminar a un usuario especificado de un colectivo o de un subcolectivo del colectivo,
	 * para ello la funcion comprueba que inicialmete este usuario se encuentre en un colectivo o subcolectivo
	 * 
	 * @param u
	 */
	public void eliminarUsuarioDeColectivoSubcolectivos(Usuario u) {
		if(getIsUsuarioEnColectivoSubcolectivo( u)) {
			
			if(this.participantes.contains(u)){
				this.participantes.remove(u);
				return ;
			}else {
				for(@SuppressWarnings("unused") Colectivo c: this.subcolectivos) {
					eliminarUsuarioDeColectivoSubcolectivos( u);
				}
			}
			
			return;
		}
	}
	
	/**
	 * Esta funcion se encargara de unir a un usuario al colectivo especificado,
	 * si el usuario ya pertenece al colectivo la funcion devolvera false, en caso contrario devolvera true
	 * 
	 * @param u
	 * @return 
	 */
	public Boolean suscribirseColectivo(Usuario u) {
		
		if(getIsUsuarioEnColectivoSubcolectivo( u)) {
			return false;
		}else {
			this.participantes.add(u);
			return true;
		}
		
	}
	
	
	/**
	 * Esta funcion se encargara de devolver la afinidad entre dos colectivos
	 * 
	 * @param c
	 * @return the afinidad
	 */
	public double obtenerAfinidad(Colectivo c) {
		double afinidad =0;
		HashSet<Usuario> participantesContenidos = new HashSet<Usuario>();
		
		participantesContenidos.addAll(this.participantes);
		for(@SuppressWarnings("unused") Colectivo subC: this.subcolectivos) {
			participantesContenidos.addAll(subC.participantes);

		}

		
		for(Usuario participante: participantesContenidos) {
			if(c.getIsUsuarioEnColectivoSubcolectivo(participante)) {
				afinidad++;
				
			}
		}
		
		return afinidad;
		
	}

	
	
	
	
	
	
	/**** getters y setter generarles    ****/
	
	
	
	/**
	 * Esta funcion devuelve el nombre del colectivo
	 * 
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Esta funcion devuelve el representante de un colectivo
	 * 
	 * @return the usuarioRepresentanteDeColectivo
	 */
	public Usuario getUsuarioRepresentanteDeColectivo() {
		return usuarioRepresentanteDeColectivo;
	}


	

	/**
	 * Esta funcion devuelve la lista de participantes del colectivo
	 * 
	 * @return the participantes
	 */
	public HashSet<Usuario> getParticipantes() {
		return participantes;
	}
	
	/**
	 * Esta funcion devuelve el colectivo padre de un colectivo especifico
	 * 
	 * @return the colectivoPadre
	 */
	public Colectivo getColectivoPadre() {
		return this.colectivoPadre;
	}





	/**
	 * Esta funcion devuelve la lista de subcolectivos de un colectivo especifico
	 * 
	 * @return
	 */
	public HashSet<Colectivo> getSubcolectivos() {
		//return (HashSet<Colectivo>) Collections.unmodifiableSet(this.subcolectivos);
		return this.subcolectivos;
	}
	

}

