package com.velociteam.pspecs.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
		
		XSSFSheet sheetPictogramas = workbook.createSheet("5 Pictogrmas Mas utilizadas");
		rownum = 0;
		rownum = fillSheet(reportData, rownum, sheetPictogramas);
		
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

	private int fillSheet(ReportDTO reportData, int rownum, XSSFSheet sheetPictogramas) {
		for (String key : reportData.getUsuariosContactados().keySet()) {
			Row row = sheetPictogramas.createRow(rownum++);

			int cellnum = 0;
			Cell dateCell = row.createCell(cellnum++);
			UsuarioDTO usDto = usDao.getUserInfoById(key);
			dateCell.setCellValue(usDto.getNombre());
			for (Tuple tuple : reportData.getUsuariosContactados().get(key)) {
				Cell cell = row.createCell(cellnum++);
				cell.setCellValue(tuple.getLabel());
			}
		}
		return rownum;
	}

}
