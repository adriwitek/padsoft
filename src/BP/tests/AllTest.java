package BP.tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * La clase AllTests lanza todas las clases de prueba de la aplicacion
 * 
 *
 */

public class AllTest {


	@RunWith(Suite.class)
	@SuiteClasses({ AplicacionTest.class,
					ColectivoTest.class,
					NotificacionTest.class,
					ProyectoInfraestructuraTest.class,
					ProyectoSocialTest.class,
					TestUsuario.class,

				})

	public class AllTests {

	}
	
	
	
	
	
	
	
}
