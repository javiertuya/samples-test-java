package giis.demo.descuento;

import java.util.List;
import giis.demo.util.*;

/**
 * Acceso a los datos de descuentos, 
 * utilizado como modelo para el ejemplo de swing y para las pruebas unitarias y de interfaz de usuario.
 * 
 * <br/>En los metodos de este ejemplo toda la logica de negocio se realiza mediante una unica query sql por lo que siempre
 * se utilizan los metodos de utilidad en la clase Database que usan apache commons-dbutils y controlan la conexion. 
 * En caso de que en un mismo metodo se realicen diferentes queries se deberia controlar la conexion desde esta clase 
 * (ver como ejemplo la implementacion en Database).
 * 
 * <br/>Si utilizase algún otro framework para manejar la persistencia, la funcionalidad proporcionada por esta clase sería la asignada
 * a los Servicios, Repositorios y DAOs.
 */
public class DescuentoModel {
	private Database db=new Database();

	/**
	 * Obtencion de descuento para un usuario en funcion los parametros.
	 * @param nuevo indica si es nuevo cliente, si no, es habitual
	 * @param cupon indica si tiene cupon descuento
	 * @param tarjeta indica si tiene tarjeta de fidelizacion
	 * @return el porcentaje de descuento aplicable segun los parametros anteriores
	 */
	public int getDescuento(boolean nuevo, boolean cupon, boolean tarjeta) {
		int descuento=0;
		if (nuevo) {
			descuento+=15; //por ser nuevo cliente
			if (cupon) 
				descuento=20; //por tarjeta se anyadiria 10, pero no es acumulable se aplica el porcentaje del cupon de descuento
			if (tarjeta)
				throw new ApplicationException("Un cliente nuevo no puede disponer de tarjeta de fidelizacion");
		} else {
			if (cupon)
				descuento+=20;
			if (tarjeta)
				descuento+=10;
		}
		return descuento;
	}
	
	/**
	 * Obtiene una fila por cada usuario de la base de datos junto con el descuento aplicable
	 * (se omiten las conbinaciones invalidas)
	 * @param edad filtro que oculta los resultados de aquellos con edad menor que la especificada
	 * (se supone no negativo)
	 * @return lista de objetos con el id de usuario y descuento aplicable
	 */
	public List<DescuentoDisplayDTO> getListaDescuentos(int edad) {
		//La misma logica que en la funcion anterior pero implementada completamente en SQL
		String sql="Select id, Descuento FROM (Select id, edad, "
				+" CASE WHEN nuevo='S' and cupon='N' and tarjeta='N' then 15 "
				+"   WHEN nuevo='S' and cupon='S' and tarjeta='N' then 20 "
				+"   WHEN nuevo='N' and cupon='S' and tarjeta='N' then 20 "
				+"   WHEN nuevo='N' and cupon='N' and tarjeta='S' then 10 "
				+"   WHEN nuevo='N' and cupon='S' and tarjeta='S' then 30 "
				+"   ELSE 0 END As descuento  From Clientes) As descuentoClientes"
				+" WHERE descuento > 0 AND edad>=?"
				+" ORDER BY id";
		return db.executeQueryPojo(DescuentoDisplayDTO.class, sql, edad);
	}
	/**
	 * Obtiene una fila por cada usuario de la base de datos junto con el descuento aplicable
	 * (se omiten las conbinaciones invalidas) sin aplicar ningun filtro
	 * @return lista de objetos con el id de usuario y descuento aplicable
	 */
	public List<DescuentoDisplayDTO> getListaDescuentos() {
		return getListaDescuentos(0);
	}


}
