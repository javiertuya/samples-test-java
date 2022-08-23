package giis.demo.descuento;
/**
 * Cada una de las filas que muestran al usuario la lista de clientes y descuentos
 * IMPORTANTE: Cuando se usan los componentes de Apache Commons DbUtils debe
 * mantenerse de forma estricta el convenio de capitalización de Java:
 *  - Capitalizar todas las palabras que forman un identificador 
 *    excepto la primera letra de nombres de métodos y variables.
 *  - No utilizar subrayados
 * Seguir tambien estos mismos criterios en los nombres de tablas y campos de la BD
 */
public class DescuentoDisplayDTO {
	private int id;
	private int descuento;
	
	public DescuentoDisplayDTO() {
		//estos objetos solo se instancian utilizando consultas a la base de datos
	}
	public int getId() { return this.id; }
	public int getDescuento() { return this.descuento; }
	public void setId(int value) { this.id=value; }
	public void setDescuento(int value) { this.descuento=value; }
}
