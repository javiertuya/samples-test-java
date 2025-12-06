package giis.demo.descuento.ut.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;


/**
 * Ejecutor de los tests cucumber de este paquete
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") 
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber.html") // para mostrar los escenarios en la consola
// no especifica features porque por defecto las localiza donde esta este fichero
// no especifica glue porque por defecto usa todos los steps de este paquete
public class TestDescuentoRunner {
}
