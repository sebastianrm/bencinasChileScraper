package com.srm.bcScraper.cvs;

import java.io.FileWriter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.supercsv.cellprocessor.FmtBool;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.srm.bcScraper.bean.ServiCentro;

/**
 * 
 * @author sebastian
 *
 */
public class CsvWriterExamples {

	private static final Logger log = LogManager.getLogger(CsvWriterExamples.class);

	public void writeCsvFromBean(String path, List<ServiCentro> list) throws Exception {

		log.info("Escrive CSV");

		ICsvBeanWriter beanWriter = null;

		try {
			beanWriter = new CsvBeanWriter(new FileWriter(path), CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the bean values to each column (names
			// must match)
			final String[] header = new String[] { "empresa", "region", "direccion", "autoservicio", "precio",
					"tipoGasolina", "ultimaModificacion", "fcaExtraccion" };

			final CellProcessor[] processors = getProcessors();

			// write the header
			beanWriter.writeHeader(header);

			// write the beans
			for (final ServiCentro customer : list) {
				beanWriter.write(customer, header, processors);
			}

		} finally {
			if (beanWriter != null) {
				beanWriter.close();
			}
		}
	}

	private static CellProcessor[] getProcessors() {

		final CellProcessor[] processors = new CellProcessor[] { 
				new NotNull(), // empresa
				new NotNull(), // region
				new NotNull(), // direccion
				new NotNull(new FmtBool("True", "False")), // autoservicio
				new NotNull(), // precio
				new NotNull(), // tipoGasolina
				new NotNull(new FmtDate("yyyy-MM-dd HH:mm:ss")), // ultimaModificacion
				new NotNull(new FmtDate("yyyy-MM-dd HH:mm:ss")) // fcaExtraccion

		};

		return processors;
	}
}
