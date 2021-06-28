package com.srm.bcScraper.webScraper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlMap;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.srm.bcScraper.bean.ServiCentro;
import com.srm.bcScraper.cvs.CsvWriterExamples;

public class ExtractJson {

	private static final String URL_MAIN_PAGE = "http://www.bencinaenlinea.cl";

	private static final Logger log = LogManager.getLogger(ExtractJson.class);

	private List<HtmlOption> regiones = new ArrayList<HtmlOption>();

	private List<ServiCentro> servicentrosAll = new ArrayList<ServiCentro>();

	private CsvWriterExamples csvWriter = new CsvWriterExamples();

	/**
	 * Metodo que realiza el primer click a la primera region
	 * 
	 * @param webClient
	 * @return
	 * @throws Exception
	 */
	private HtmlPage extractMainPage(WebClient webClient) throws Exception {

		HtmlPage mainPage = webClient.getPage(URL_MAIN_PAGE);

		HtmlMap regionesMap = (HtmlMap) mainPage.getElementById("m_mapa_re1");

		Iterable<DomElement> childElements = regionesMap.getChildElements();

		Iterator<DomElement> iterator = childElements.iterator();

		while (iterator.hasNext()) {
			DomElement next = iterator.next();
			mainPage = next.click();
			break;
		}

		return mainPage;

	}

	/**
	 * Metodo que recupera las regiones desde el combo de regiones
	 * 
	 * @param page
	 */
	private void recuperoRegiones(HtmlPage page) {

		HtmlSelect regionesSelect = (HtmlSelect) page.getElementById("reporte_region");

		regiones.clear();

		regiones = regionesSelect.getOptions();
	}

	/**
	 * ejecuta la extraccion
	 * 
	 * @throws Exception
	 */
	public void executeExtract() {

		WebClient webClient = null;

		webClient = setNavegador();

		try {

			/**
			 * Primer click
			 */
			HtmlPage mainPage = extractMainPage(webClient);

			recuperoRegiones(mainPage);

			for (HtmlOption regionOption : regiones) {
				// Clase que realiza la extraxccion
				PageExtractor pageExt = new PageExtractor();
				pageExt.setRegion(regionOption);
				pageExt.setPage(mainPage);
				pageExt.executeExtract();
				servicentrosAll.addAll(pageExt.getServicentros());
			}

//		log.info("Lista de combustibles \n" + pageExt.getServicentros().toString());

//		cierro todas las conexiones
			webClient.close();

//		escrivo resultados en CSV
			Path path = Paths.get("csv/writtenBean.csv");
			File directorio = new File("csv/");

			if (!directorio.exists()) {
				if (directorio.mkdirs()) {
					log.info("Directorio creado");
				} else {
					log.info("Error al crear directorio");
				}
			}

			csvWriter.writeCsvFromBean(path, servicentrosAll);

		} catch (Exception e) {
			log.error(e);
		} finally {
			webClient.close();
		}

		log.info("FIN");

	}

	private WebClient setNavegador() {
		WebClient webClient;
		// seteo las configuracion del browser a emular

		webClient = new WebClient(BrowserVersion.CHROME);

		webClient.getOptions().setDownloadImages(false);
		webClient.getOptions().setCssEnabled(false);

		webClient.getOptions().setHistorySizeLimit(0);

		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setJavaScriptEnabled(true);

		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		webClient.getOptions().setTimeout(10000);
		return webClient;
	}

}
