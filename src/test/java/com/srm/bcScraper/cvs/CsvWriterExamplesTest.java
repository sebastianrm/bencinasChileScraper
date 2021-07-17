package com.srm.bcScraper.cvs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.srm.bcScraper.bean.ServiCentro;

class CsvWriterExamplesTest {

	@Test
	void test() {

		CsvWriterExamples csvWriterExamples = new CsvWriterExamples();

		ServiCentro serviCentro = new ServiCentro();

		serviCentro.setAutoservicio(Boolean.TRUE);
		serviCentro.setDireccion("LUIS VALENTE ROSSI 1990,Arica");
		serviCentro.setEmpresa("COPEC");
		serviCentro.setFcaExtraccion(new Date());
		serviCentro.setPrecio(new BigDecimal(838));
		serviCentro.setRegion("Arica y Parinacota");
		serviCentro.setTipoGasolina("Gasolina 93");
		serviCentro.setUltimaModificacion(new Date());

		ArrayList<ServiCentro> lista = new ArrayList<ServiCentro>();
		lista.add(serviCentro);
		try {
			csvWriterExamples.writeCsvFromBean("csv/writtenBeanTest.csv", lista);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
