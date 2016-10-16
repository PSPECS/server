package com.velociteam.pspecs.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.LineChartSerie;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTMarker;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTMarkerStyle;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumData;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumRef;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTStrData;
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
		Sheet sheetTiempoDeUso = workbook.createSheet("Tiempo de Uso");
		int rownum = 0;
		for (String key : reportData.getTiemposDeUso().keySet()) {
			Row row = sheetTiempoDeUso.createRow(rownum++);
			Cell dateCell = row.createCell(1);
			dateCell.setCellValue(key);
			Cell usosCell = row.createCell(2);
			usosCell.setCellValue(reportData.getTiemposDeUso().get(key));
		}
		
        createLinearChart(sheetTiempoDeUso, rownum);

        XSSFSheet sheetUsuarios = workbook.createSheet("Usuarios Mas contactados");
		rownum = 0;
		Tuple charDataRows = fillSheet(reportData.getUsuariosContactados(), rownum, sheetUsuarios);
		
		createPieChart(reportData.getUsuariosContactados(),sheetUsuarios,"'Usuarios Mas contactados'!$A$"+charDataRows.getLabel()+":$A$"+String.valueOf(charDataRows.getValue()),"'Usuarios Mas contactados'!$B$"+charDataRows.getLabel()+":$B$"+String.valueOf(charDataRows.getValue())); 
		
		XSSFSheet sheetPictogramas = workbook.createSheet("5 Pictogramas Mas utilizados");
		rownum = 0;
		charDataRows = fillSheet(reportData.getPictogramasMasUtilizados(), rownum, sheetPictogramas);
		
		createPieChart(reportData.getPictogramasMasUtilizados(),sheetPictogramas,"'5 Pictogramas Mas utilizados'!$A$"+charDataRows.getLabel()+":$A$"+String.valueOf(charDataRows.getValue()),"'5 Pictogramas Mas utilizados'!$B$"+charDataRows.getLabel()+":$B$"+String.valueOf(charDataRows.getValue()));
		
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

	private void createLinearChart(Sheet sheetTiempoDeUso, int rownum) {
		Drawing drawing = sheetTiempoDeUso.createDrawingPatriarch();
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 2 + 2, 3, 2 + 15, 20);

        Chart chart = drawing.createChart(anchor);
        ChartLegend legend = chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.RIGHT);

        LineChartData data = chart.getChartDataFactory().createLineChartData();

        ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheetTiempoDeUso, new CellRangeAddress(0, rownum - 1, 1, 1));
        ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheetTiempoDeUso, new CellRangeAddress(0, rownum - 1, 2, 2));

        LineChartSerie series1 = data.addSerie(xs, ys1);
        series1.setTitle("Tiempo de uso");

        chart.plot(data, bottomAxis, leftAxis);

        XSSFChart xssfChart = (XSSFChart) chart;
        CTPlotArea plotArea = xssfChart.getCTChart().getPlotArea();
        CTMarker ctMarker = CTMarker.Factory.newInstance();
        ctMarker.setSymbol(CTMarkerStyle.Factory.newInstance());
        for (CTLineSer ser : plotArea.getLineChartArray()[0].getSerArray()) {
            ser.setMarker(ctMarker);
        }
	}

	private void createPieChart(Map<String, List<Tuple>> data, XSSFSheet sheetUsuarios,String ref1,String ref2) {
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
//        CTStrData ctStrData = ctStrRef.addNewStrCache();
//        ctStrData.addNewPtCount().setVal(data.size());
//        for (String key : data.keySet()) {
//			ctStrData.addNewPt().setV(key);
//		}
        CTNumDataSource ctNumDataSource = ctPieSer.addNewVal();
        CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
        ctNumRef.setF(ref2);
//        CTNumData ctNumData = ctNumRef.addNewNumCache();
//        ctNumData.addNewPtCount().setVal(data.size());
//        for (String key : data.keySet()) {
//        	Integer sum = data.get(key).stream()
//        	.map(t->t.getValue())
//        	.reduce(Integer::sum).get();
//        	ctNumData.addNewPt().setV(String.valueOf(sum));
//		}
	}

	private Tuple fillSheet(Map<String, List<Tuple>> data, int rownum, XSSFSheet sheetPictogramas) {
		Map<String,Integer> charData = new HashMap<>();
		for (String key : data.keySet()) {
			Row row = sheetPictogramas.createRow(rownum++);
			int cellnum = 0;
			Cell dateCell = row.createCell(cellnum++);
			dateCell.setCellValue(key);
			for (Tuple tuple : data.get(key)) {
				Cell cell = row.createCell(cellnum++);
				UsuarioDTO usDto = usDao.getUserInfoById(tuple.getLabel());
				cell.setCellValue(usDto.getNombre());
				updateCharData(charData, tuple.getValue(), usDto.getNombre());
			}
		}
		rownum++;
		int chartRow=rownum+1;
		for (String key : charData.keySet()) {
			Row row = sheetPictogramas.createRow(rownum++);
			int cellnum = 0;
			Cell nameCell = row.createCell(cellnum++);
			nameCell.setCellValue(key);
			Cell valueCell = row.createCell(cellnum++);
			valueCell.setCellValue(charData.get(key));
		}
		
		return new Tuple(String.valueOf(chartRow), rownum);
	}

	private void updateCharData(Map<String, Integer> charData, Integer value, String key) {
		if(charData.containsKey(key)){
			charData.put(key, charData.get(key)+value);
		} else {
			charData.put(key, value);
		}
	}

}
