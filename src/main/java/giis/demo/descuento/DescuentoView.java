package giis.demo.descuento;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Vista de la pantalla que muestra la lista de descuentos de los usuarios y permite 
 * aplicar un filtro por edad
 * <br/>Se ha generado con WindowBulder y modificado para ser conforme a MVC teniendo en cuenta:
 * - Se elimina main (es invocada desde CarrerasMain) y se incluye Title en el frame
 * - No se incluye ningun handler de eventos pues estos van en el controlador
 * - Las tablas se encierran en JOptionPane para que se puedan visualizar las cabeceras
 * - Se asinga nombre a las tablas si se van a automatizar la ejecucion de pruebas
 * - Incluye al final los metodos adicionales necesarios para acceder al UI desde el controlador
 */
public class DescuentoView {

	private JFrame frame;
	private JTable tabDescuentos;
	private JTextField txtAnyos;
	private JButton btnAplicarFiltro;

	/**
	 * Create the application.
	 */
	public DescuentoView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Descuento");
		frame.setName("Descuento");
		frame.setBounds(0, 0, 350, 350);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "", ""));
		
		JLabel lblDescuentos = new JLabel("Descuentos");
		frame.getContentPane().add(lblDescuentos, "cell 0 5, grow");
		
		//Incluyo la tabla en un JScrollPane y anyado este en vez de la tabla para poder ver los headers de la tabla
		tabDescuentos = new JTable();
		tabDescuentos.setName("tabDescuentos");
		tabDescuentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabDescuentos.setDefaultEditor(Object.class, null); //readonly
		JScrollPane tablePanel = new JScrollPane(tabDescuentos);
		frame.getContentPane().add(tablePanel, "cell 0 6,grow");
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "cell 1 6,grow");
		panel.setLayout(new MigLayout("", "[306px][116px]", "[22px][25px][]"));
		
		JLabel lblFiltro = new JLabel("<html>Filtrar Clientes<br/>con edad menor que<br/>los años indicados</html>");
		panel.add(lblFiltro, "cell 0 0,alignx left,aligny center");
		
		txtAnyos = new JTextField();
		txtAnyos.setName("txtAnyos");
		panel.add(txtAnyos, "flowx,cell 0 1,alignx left,aligny top");
		txtAnyos.setColumns(10);
		
		btnAplicarFiltro = new JButton("Aplicar filtro");
		btnAplicarFiltro.setName("btnAplicarFiltro");
		panel.add(btnAplicarFiltro, "cell 0 2,alignx left,aligny top");
	}

	//Getters y Setters anyadidos para acceso desde el controlador (repersentacion compacta)
	public JFrame getFrame() { return this.frame; }
	public JButton getBtnAplicarFiltro() { return this.btnAplicarFiltro; }
	public JTable getTablaDescuentos() { return this.tabDescuentos; }
	public String getTxtAnyos() { return this.txtAnyos.getText(); }
	public void setTxtAnyos(String anyos) { this.txtAnyos.setText(anyos); }
	
}
