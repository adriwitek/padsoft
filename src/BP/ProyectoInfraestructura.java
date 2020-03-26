package BP;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

public class ProyectoInfraestructura extends Proyecto {
	private String distrito;
	private String croquis;
	private String imgPath;
	
	public ProyectoInfraestructura(Proponente p, Usuario uCreador,String nombre, String descrL, String descC , double cost,String dist,String croquis ,String imgPath) {
		
		super(p, uCreador, nombre, descrL, descC, cost);
		this.distrito = dist;
		this.croquis = croquis; 
		
		
		try {
			File source = new File(imgPath);
			this.imgPath = "img/" + nombre + "_" + this.uniqueID;
	        File dest = new File(this.imgPath);
			Files.copy(source.toPath(), dest.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
