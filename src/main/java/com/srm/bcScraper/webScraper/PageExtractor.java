/**
 * 
 */
package com.srm.bcScraper.webScraper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.srm.bcScraper.bean.ServiCentro;

/**
 * @author sebastian
 *
 */
public class PageExtractor {

	private static final Logger log = LogManager.getLogger(PageExtractor.class);

	private HtmlSelect combustibleSelect;
	private List<HtmlOption> combustiblesList = new ArrayList<HtmlOption>();
	private List<ServiCentro> servicentros = new ArrayList<ServiCentro>();

	private HtmlOption regionOption;

	private HtmlPage mainPage;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());

	/**
	 * Metodo que selecciona region
	 * 
	 * @param page
	 * @return
	 */
	private HtmlPage seleccionaRegion(HtmlPage page) {

//		id del select region
//		reporte_region

		log.info("Selecciona Region : " + regionOption.getText());

		HtmlSelect regionesSelect = (HtmlSelect) page.getElementById("reporte_region");
		regionesSelect.setSelectedAttribute(regionOption, true);

		return page;
	}

	/**
	 * Metodo que seleciona tipo de combustible
	 * 
	 * @param page
	 * @param combustibleOption
	 * @return
	 */
	private HtmlPage seleccionaCombustible(HtmlPage page, HtmlOption combustibleOption) {

//		id del select de tipo combustible
//		reporte_combustible

		log.info("Selecciona combustible : " + combustibleOption.getText());
		HtmlSelect combustibleSelect = (HtmlSelect) page.getElementById("reporte_combustible");
		combustibleSelect.setSelectedAttribute(combustibleOption, true);

		return page;

	}

	/**
	 * recupera lista de combustibles para iterar y extraer
	 * 
	 * @param page
	 */
	private void recuperoCombustibles(HtmlPage page) {

//		recupero el tipo de combustible en arica page

		log.info("Recupera lista de combustibles");

		combustibleSelect = (HtmlSelect) page.getElementById("reporte_combustible");

		combustiblesList.clear();

		Iterator<HtmlOption> iterator = combustibleSelect.getOptions().iterator();

		while (iterator.hasNext()) {

			HtmlOption next = iterator.next();

			if (!next.getText().trim().equalsIgnoreCase("Seleccione")) {
				combustiblesList.add(next);
			}

		}

	}

	/**
	 * Realiza el clik en ver reporte
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	private HtmlPage clikVerReporte(HtmlPage page) throws Exception {

		log.info("Click en btn Ver Reporte");

		List<Object> byXPath = page
				.getByXPath("/html/body/table/tbody/tr[4]/td/table[1]/tbody/tr/td/table[1]/tbody/tr[6]/td/input");

		return ((HtmlButtonInput) byXPath.get(0)).click();

	}

	/**
	 * Metodo publico de extr5accion de la data POR REGION Y COMBUSTIBLE
	 * 
	 * @throws Exception
	 */
	public void executeExtract() throws Exception {

		log.info("Ejecuta Extract");

//		selecciona la region
		mainPage = seleccionaRegion(mainPage);

//		recupera los tipos de combustibles de la region

		recuperoCombustibles(mainPage);

//		selecciona combustible
		Iterator<HtmlOption> iterator = combustiblesList.iterator();

		while (iterator.hasNext()) {

			HtmlOption combustible = iterator.next();

			mainPage = seleccionaCombustible(mainPage, combustible);

//			click en ver reporte

			mainPage = clikVerReporte(mainPage);

//				extraigo informacion del reporte

			extraigoData(mainPage, combustible);

		}

	}

	/**
	 * Extrae la tabla de resultado
	 * 
	 * @param page
	 * @param combustibleOption
	 * @throws ParseException
	 */
	private void extraigoData(HtmlPage page, HtmlOption combustibleOption) throws ParseException {
		// recupero tabla de resultados
		DomElement elementById = page.getElementById("tabla");

		DomNode firstChild = elementById.getFirstChild();

		HtmlTable tablaResultados = (HtmlTable) firstChild;
		
//		fecha de extraccion
		Date fcaExtract = new Date();  

		if (tablaResultados != null) {
			// itero por los servicentros recuperados
			for (final HtmlTableRow row : tablaResultados.getRows()) {

				ServiCentro serviCentro = new ServiCentro();

				serviCentro.setRegion(regionOption.getText());
				serviCentro.setTipoGasolina(combustibleOption.getText());

				int i = 0;

				for (final HtmlTableCell cell : row.getCells()) {

					if (i == 0) {

						log.info("Columna Direccion y empresa : " + i + " - " + cell.asText());

						if (!cell.asText().trim().equalsIgnoreCase("Servicentro")) {
							serviCentro = extraeEmpresaDireccion(cell.asText(), serviCentro);
						}

					} else if (i == 1) {

						log.info("Columna si es Auto Servicio: " + i + " - " + cell.asText());
						if (!cell.asText().trim().equalsIgnoreCase("Servicentro")) {
							serviCentro.setAutoservicio(!cell.asText().trim().equals("-"));
						}

					} else if (i == 2) {

						log.info("Columna Precio : " + i + " - " + cell.asText());
						if (!cell.asText().trim().equalsIgnoreCase("Servicentro")) {
							if (!cell.asText().trim().equalsIgnoreCase("Autoservicio")) {
								if (!cell.asText().trim().equalsIgnoreCase("Precio")) {
									serviCentro.setPrecio(new BigDecimal(cell.asText().trim().split(",")[0]));
								}
							}
						}
					} else if (i == 3) {

						log.info("Columna Ultima Actualizacion: " + i + " - " + cell.asText());
						if (!cell.asText().trim().equalsIgnoreCase("Servicentro")) {
							if (!cell.asText().trim().equalsIgnoreCase("Autoservicio")) {
								if (!cell.asText().trim().equalsIgnoreCase("Precio")) {
									if (!cell.asText().trim().equalsIgnoreCase("Última modificación")) {
										serviCentro.setUltimaModificacion(sdf.parse(cell.asText().trim()));
									}
								}
							}
						}
					}

					i++;
				}

				if (serviCentro.getPrecio() != null) {
					
					serviCentro.setFcaExtraccion(fcaExtract);
					servicentros.add(serviCentro);
				}

			}
		}

	}

	private ServiCentro extraeEmpresaDireccion(String txt, ServiCentro serviCentro) {

		String[] split = txt.split("\n");

		serviCentro.setEmpresa(split[0]);
		serviCentro.setDireccion(split[1]);

		return serviCentro;

	}

	/**
	 * @return the combustibles
	 */
	public List<HtmlOption> getCombustibles() {
		return combustiblesList;
	}

	/**
	 * @param combustibles the combustibles to set
	 */
	public void setCombustibles(List<HtmlOption> combustibles) {
		this.combustiblesList = combustibles;
	}

	/**
	 * @return the servicentros
	 */
	public List<ServiCentro> getServicentros() {
		return servicentros;
	}

	/**
	 * @param servicentros the servicentros to set
	 */
	public void setServicentros(List<ServiCentro> servicentros) {
		this.servicentros = servicentros;
	}

	/**
	 * @return the page
	 */
	public HtmlPage getPage() {
		return mainPage;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(HtmlPage page) {
		this.mainPage = page;
	}

	/**
	 * @return the region
	 */
	public HtmlOption getRegion() {
		return regionOption;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(HtmlOption region) {
		this.regionOption = region;
	}

}
