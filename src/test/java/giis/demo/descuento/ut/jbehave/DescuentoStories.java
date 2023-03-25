package giis.demo.descuento.ut.jbehave;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;

import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;

/**
 * Configuracion para ejecucion con jbehave de los escenarios escritos en gherkin determinando:
 * <br/>-la configuracion generar a utilizar
 * <br/>-los archivos que contienen los escenarios y pasos en gherkin
 * <br/>-las clases java que implementan el mapeo de los pasos
 */
@RunWith(JUnitReportingRunner.class) //para visualizar los tests desde la vista JUnit de eclipse
public class DescuentoStories extends JUnitStories {
	//clase de utilidad con metodos generales para la configuracion
	private JBehaveConfig config=new JBehaveConfig(this.getClass(),true); 
	/**
	 * Configuracion general por defecto, anyadiendo reports de surefire
	 */
	@Override
	public Configuration configuration() {
		return config.getConfiguration();
	}
    /**
     * Asocia las clases java que implementan cada uno de los pasos de los escenarios
     */
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new DescuentoFunctionSteps(), new DescuentoDatabaseSteps());
    }
    /**
     * Asocia (localiza) los archivos con escenarios (*.story) que se encuentran en la misma carpeta que esta clase
     * (antes de ejecutar los tests se copian desde la carpeta de fuentes a la carpeta de clases)
     */
    @Override
    protected List<String> storyPaths() {
    	return config.getStoryPaths();
    }
}