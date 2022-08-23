package giis.demo.descuento.it.jbehave;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;

import giis.demo.descuento.ut.jbehave.JBehaveConfig;

/**
 * Configuracion para ejecucion con jbehave de los escenarios escritos en gherkin determinando:
 * -la configuracion generar a utilizar
 * -los archivos que contienen los escenarios y pasos en gherkin
 * -las clases java que implementan el mapeo de los pasos
 */
@RunWith(JUnitReportingRunner.class) //para visualizar los tests desde la vista JUnit de eclipse
public class DescuentoITStories extends JUnitStories {
	//clase de utilidad con metodos generales para la configuracion
	private JBehaveConfig config=new JBehaveConfig(this.getClass(),false); 
    @Override
    public Configuration configuration() {
    	return config.getConfiguration();
    }
    /**
     * Asocia las clases java que implementan cada uno de los pasos de los escenarios
     */
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new DescuentoITSteps());
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