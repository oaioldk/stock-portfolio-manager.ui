package com.proserus.stocks.ui.view.actions;

import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.proserus.stocks.ui.controller.PortfolioController;
import com.proserus.stocks.ui.controller.ViewControllers;
import com.proserus.stocks.ui.view.general.Window;

public class ExportToCsvAction extends AbstractAction {
	private static final String EXPORT = "Export";
	private static final String STOCK_PORTFOLIO_EXPORT_CSV = "stock-portfolio_export.csv";
	private static final String CSV_EXTENSION = ".csv";
	private static Logger LOGGER = Logger.getLogger(ExportToCsvAction.class);
	private static final long serialVersionUID = 201404031819L;
	private PortfolioController controller = ViewControllers.getController();
	private Window window = ViewControllers.getWindow();

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fc = new JFileChooser(new File(
				STOCK_PORTFOLIO_EXPORT_CSV));
		fc.addChoosableFileFilter(new CsvFileFilter());
		fc.setDialogTitle(EXPORT);
		fc.showDialog(window, EXPORT);
		File file = fc.getSelectedFile();

		if (file != null) {
			if (!file.getName().toLowerCase().endsWith(CSV_EXTENSION)) {
				file = new File(file.getPath() + CSV_EXTENSION);
			}

			ByteArrayOutputStream baos = controller.exportTransactions();

			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
				fos.write(baos.toByteArray());
				fos.close();
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.FATAL, "Could not find file", e);
			} catch (IOException e) {
				LOGGER.log(Level.FATAL, "Unexpected error", e);
			}
		}
	}

}