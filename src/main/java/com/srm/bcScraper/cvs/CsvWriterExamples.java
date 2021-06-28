package com.srm.bcScraper.cvs;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.srm.bcScraper.bean.ServiCentro;

public class CsvWriterExamples {

	private static final Logger log = LogManager.getLogger(CsvWriterExamples.class);

	public void writeCsvFromBean(Path path,List<ServiCentro> list) throws Exception {

		log.info("Escrive CSV");
		Writer writer = new FileWriter(path.toString());

		StatefulBeanToCsv<ServiCentro> sbc = new StatefulBeanToCsvBuilder<ServiCentro>(writer)
				.withSeparator(CSVWriter.DEFAULT_SEPARATOR).build();

		sbc.write(list);
		writer.close();
	}
}
