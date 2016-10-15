package com.velociteam.pspecs.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumRef;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTStrRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.ReportDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;
import com.velociteam.pspecs.exception.BussinessException;

@Service
public class ReportGenerator {
	
	@Autowired
	private UsuariosDao usDao;

	public String generateReport(ReportDTO reportData){
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheetTiempoDeUso = workbook.createSheet("Tiempo de Uso");
		int rownum = 0;
		for (String key : reportData.getTiemposDeUso().keySet()) {
			Row row = sheetTiempoDeUso.createRow(rownum++);
			Cell dateCell = row.createCell(1);
			dateCell.setCellValue(key);
			Cell usosCell = row.createCell(2);
			usosCell.setCellValue(reportData.getTiemposDeUso().get(key));
		}
		
		XSSFSheet sheetUsuarios = workbook.createSheet("Usuarios Mas contactados");
		rownum = 0;
		rownum = fillSheet(reportData, rownum, sheetUsuarios);
		
		createPieChart(sheetUsuarios,"Sheet2!$A$2:$A$3","Sheet2!$B$2:$B$3"); 
		
		XSSFSheet sheetPictogramas = workbook.createSheet("5 Pictogrmas Mas utilizadas");
		rownum = 0;
		rownum = fillSheet(reportData, rownum, sheetPictogramas);
		
		createPieChart(sheetPictogramas,"Sheet3!$A$2:$A$3","Sheet3!$B$2:$B$3");
		
		//Write the workbook in file system  
	    FileOutputStream out;
	    String filename = String.format("ReporteActividad%s.xlsx", new Date().getTime());
		try {
			out = new FileOutputStream(new File(filename));
			workbook.write(out);  
			out.close();  
		} catch (IOException e) {
			throw new BussinessException("Se produjo un error al generar el archivo del reporte.",e);
		}  
		return filename;
	}

	private void createPieChart(XSSFSheet sheetUsuarios,String ref1,String ref2) {
		Drawing drawing = sheetUsuarios.createDrawingPatriarch();
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 5, 20);

        Chart chart = drawing.createChart(anchor);

        CTChart ctChart = ((XSSFChart)chart).getCTChart();
        CTPlotArea ctPlotArea = ctChart.getPlotArea();
        CTPieChart ctPieChart = ctPlotArea.addNewPieChart();
        CTBoolean ctBoolean = ctPieChart.addNewVaryColors();
        ctBoolean.setVal(true);
        CTPieSer ctPieSer = ctPieChart.addNewSer();

        ctPieSer.addNewIdx().setVal(0);     

        CTAxDataSource cttAxDataSource = ctPieSer.addNewCat();
        CTStrRef ctStrRef = cttAxDataSource.addNewStrRef();
        ctStrRef.setF(ref1); 
        CTNumDataSource ctNumDataSource = ctPieSer.addNewVal();
        CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
        ctNumRef.setF(ref2);
	}

	private int fillSheet(ReportDTO reportData, int rownum, XSSFSheet sheetPictogramas) {
		for (String key : reportData.getUsuariosContactados().keySet()) {
			Row row = sheetPictogramas.createRow(rownum++);

			int cellnum = 0;
			Cell dateCell = row.createCell(cellnum++);
			dateCell.setCellValue(key);
			for (Tuple tuple : reportData.getUsuariosContactados().get(key)) {
				Cell cell = row.createCell(cellnum++);
				UsuarioDTO usDto = usDao.getUserInfoById(tuple.getLabel());
				cell.setCellValue(usDto.getNombre());
			}
		}
		return rownum;
	}

}
