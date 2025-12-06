package giis.demo.descuento.ut.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Ejecutor de los tests cucumber de este paquete
 */
@Suite
@IncludeEngines("cucumber")
@SelectPackages("giis.demo.descuento.ut.cucumber")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, 
	value = "pretty, html:target/reports/cucumber.html") // para mostrar los escenarios en la consola y html
// no especifica glue porque por defecto usa todos los steps de este paquete
public class TestDescuentoRunner {
}
