package giis.demo.descuento;

import java.util.List;
import javax.swing.table.TableModel;
import giis.demo.util.SwingUtil;

/**
 * Controlador para la funcionalidad de visualizacion descuentos de clientes.
 * Es el punto de entrada de esta pantalla que se invocará:
 * -instanciando el controlador con la vista y el modelo
 * -ejecutando initController que instalara los manejadores de eventos
 */
public class DescuentoController {
	private DescuentoModel model;
	private DescuentoView view;

	public DescuentoController(DescuentoModel m, DescuentoView v) {
		this.model = m;
		this.view = v;
		this.initView();
	}
	/**
	 * Inicializacion del controlador: anyade los manejadores de eventos a los objetos del UI.
	 * Cada manejador de eventos se instancia de la misma forma, para que invoque un metodo privado
	 * de este controlador, encerrado en un manejador de excepciones generico para mostrar ventanas
	 * emergentes cuando ocurra algun problema o excepcion controlada.
	 */
	public void initController() {
		view.getBtnAplicarFiltro().addActionListener(e -> SwingUtil.exceptionWrapper(() -> getListaDescuentos()));
	}
	
	public void initView() {
		//la lista de descuentos se mostrara de acuerdo con los valores iniciales de la edad en el interfaz
		this.getListaDescuentos();
		//Abre la ventana (sustituye al main generado por WindowBuilder)
		view.getFrame().setVisible(true); 
	}
	/**
	 * La obtencion de la lista de descuentos solo necesita obtener la lista de objetos del modelo 
	 * y usar metodo de SwingUtil para crear un tablemodel que se asigna finalmente a la tabla.
	 */
	public void getListaDescuentos() {
		String anyosTxt=view.getTxtAnyos().trim();
		List<DescuentoDisplayDTO> descuento;
		if ("".equals(anyosTxt)) //diferente metodo si se indican o no los anyos
			descuento=model.getListaDescuentos();
		else
			descuento=model.getListaDescuentos(Integer.parseInt(anyosTxt));
		TableModel tmodel=SwingUtil.getTableModelFromPojos(descuento, new String[] {"id", "descuento"});
		view.getTablaDescuentos().setModel(tmodel);
		SwingUtil.autoAdjustColumns(view.getTablaDescuentos());
	}
}
