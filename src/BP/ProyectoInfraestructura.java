package BP;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;

import es.uam.eps.sadp.grants.GrantRequest;

public class ProyectoInfraestructura extends Proyecto {
	private String croquisPath;
	private String imgPath;
	private HashSet<String> distritos;
	
	 /**
	  * Constructor, con los datos a implementar
	  * @param p que asignar al proponente 
	  * @param uCreador para asignar al usuario que ha creado el proyecto
	  * @param nombre para asignar al proyecto
	  * @param descrL para asignar al proyecto
	  * @param descC para asignar al proyecto
	  * @param cost para asignar a la financiacón que se pretende obtener para crear el proyecto
	  * @param croquis para asignar un croquis al proyecto
	  * @param imgPath para asignar una imagen al proyecto
	  * @param distritos para asignar una zona de accion para el proyecto
	  */
	
	public ProyectoInfraestructura(Proponente p, Usuario uCreador,String nombre, String descrL, String descC , double cost,String croquis ,String imgPath,HashSet<String> distritos) {
		
		super(p, uCreador, nombre, descrL, descC, cost);
		this.distritos = new HashSet<String>();
		
		//Filtramos los distritos no admitidos
		for(String d :distritos) {
			if(Aplicacion.getInstancia(null, null, null).getDistritos().contains(d)) {
				this.distritos.add(d); 
			}
		}
		
		
		try {
			File sourceImg = new File(imgPath);
			File sourceCroquis = new File(croquis);

			this.imgPath = "img/" +   this.uniqueID + "img" + sourceImg.getName();
			this.croquisPath = "img/" +  this.uniqueID + "_croquis" +sourceCroquis.getName();

			
	        File dest1 = new File(this.imgPath);
	        File dest2 = new File(this.croquisPath);

	        
			Files.copy(sourceImg.toPath(), dest1.toPath(),StandardCopyOption.REPLACE_EXISTING);
			Files.copy(sourceCroquis.toPath(), dest2.toPath(),StandardCopyOption.REPLACE_EXISTING);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Esta funcion introduce en la lista de distritos (en los que actura el proyecto) un nuevo distrito
	 * 
	 * @param d
	 * @return
	 */
	
	public Boolean addDistrito(String d) {
		if(Aplicacion.getInstancia(null, null, null).getDistritos().contains(d)) {
			this.distritos.add(d); 
			return true;
		}
		return false;
	}
	
	
	/**
	 * Esta funcion devolvera un string que contendra una lista de distritos del proyecto
	 * 
	 * @return cadena
	 */

	public String getExtraData() {
		String cadena = "";
		if(this.distritos.size() == 1) {
			
			cadena= "El distrito del proyecto es" + this.distritos.toString();
		}else {
			cadena= "Los distritos a los que pertenece el proyecto son" + this.distritos.toString();
		}
		
		return cadena;
	}
	
	/**
	 * Esta funcion devuelve que tipo de proyecto es
	 */
	public GrantRequest.ProjectKind getProjectKind(){
		return GrantRequest.ProjectKind.Infrastructure;
	}
}
