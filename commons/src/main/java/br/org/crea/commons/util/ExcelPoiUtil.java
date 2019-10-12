package br.org.crea.commons.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelPoiUtil {
	
	private Workbook workbook;
	private Sheet sheet;
	private PrintSetup printSetup;
	private Row row;
	private Cell cell;
	private String fileName;
	private Integer rowNum;
	private Integer cellNum;
	private int totalCols;

	public static enum Style {
		TABLE_HEADER, HEADER, CNES, INTEGER, PERCENT, CENTER, DATE, TOTAL, BOLD, TITLE, BORDER, NUMBER, VALOR_DESTAQUE, DESTAQUE_DARK, DESTAQUE_LIGHT, DANGER, WARNING
	}

	public ExcelPoiUtil() {
		this.workbook = new XSSFWorkbook();
		this.sheet = this.workbook.createSheet();
		this.printSetup = sheet.getPrintSetup();

		this.rowNum = 0;
		this.cellNum = 0;
		this.totalCols = 0;

		this.printSetup.setLandscape(true);
		this.sheet.setFitToPage(true);
	}

	public void setTitleCreaRJ() {

		List<Cabecalho> cabecalhos = new ArrayList<>();
		cabecalhos.add(new Cabecalho("CREA RJ", Style.TITLE, Style.BOLD));

		for (Cabecalho cabecalho : cabecalhos) {
			setCell(cabecalho.getTitulo(), cabecalho.getEstilos());
			this.sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowNum) + ":$H$" + (rowNum)));
			newRow();
		}
	}

	public void setSubTituloDatasus(List<String> frasesSubTitulo) {

		if (frasesSubTitulo == null)
			return;
		if (frasesSubTitulo.size() == 0)
			return;

		List<Cabecalho> cabecalhos = new ArrayList<>();
		for (String frase : frasesSubTitulo) {
			cabecalhos.add(new Cabecalho(frase, Style.TITLE, Style.BOLD));
		}
		for (Cabecalho cabecalho : cabecalhos) {
			setCell(cabecalho.getTitulo(), cabecalho.getEstilos());
			this.sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowNum) + ":$H$" + (rowNum)));
			newRow();
		}
	}

	public ExcelPoiUtil setCell(String value, Style... estilo) {
		this.row = getRow();
		this.cell = row.createCell(cellNum++);
		this.cell.setCellStyle(this.getCellStyle(estilo));

		if (NumericUtils.isNumericValue(value)) {
			this.cell.setCellType( CellType.NUMERIC );
			this.cell.setCellValue(NumericUtils.numericConverter(value));
		} else {
			this.cell.setCellValue(value);
		}
		return this;
	}

	public ExcelPoiUtil setCell(Date value, Style... estilo) {
		this.row = getRow();
		this.cell = row.createCell(cellNum++);
		this.cell.setCellStyle(this.getCellStyle(estilo));
		if (value != null) {
			this.cell.setCellValue(value);
		}
		return this;
	}

	public ExcelPoiUtil setMergedCell(String value, int range, Style... estilo) {
		try {
			this.totalCols = (this.totalCols == 0 ? range + 1 : this.totalCols);
			this.row = getRow();
			this.cell = row.createCell(cellNum++);
			this.cell.setCellValue(value);
			this.cell.setCellStyle(this.getCellStyle(estilo));
			this.sheet.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), range));
			this.cellNum += range;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	
		return this;
	}

	public ExcelPoiUtil setCell(String value) {
		this.row = getRow();
		this.cell = row.createCell(cellNum++);
		this.cell.setCellValue(value);
		return this;
	}
	
	public ExcelPoiUtil setCell(int value) {
		this.row = getRow();
		this.cell = row.createCell(cellNum++);
		this.cell.setCellValue(value);
		return this;
	}

	public ExcelPoiUtil newRow() {
		this.row = this.sheet.createRow(this.rowNum++);
		this.cellNum = 0;
		return this;
	}

	private Row getRow() {
		if (this.row == null)
			newRow();
		return this.row;
	}

	public void buildToFile(String fileName) {

		this.fileName = fileName;

		for (int colNum = 0; colNum < this.totalCols; colNum++) {
			this.workbook.getSheetAt(0).autoSizeColumn(colNum);
		}

		try {
			FileOutputStream out = new FileOutputStream("/home/rodrigo/" + this.getFileName());
			this.workbook.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private XSSFCellStyle getCellStyle(Style... css) {

		Font font = this.workbook.createFont();
		XSSFCellStyle style = (XSSFCellStyle) this.workbook.createCellStyle();
		DataFormat format = this.workbook.createDataFormat();

		XSSFColor lightSteelBlue = new XSSFColor(Color.decode("#B0C4DE"));
		XSSFColor Salmon = new XSSFColor(Color.decode("#FA8072"));
		XSSFColor SlateGray = new XSSFColor(Color.decode("#D3D3D3"));
		XSSFColor LightSlateGray = new XSSFColor(Color.decode("#DCDCDC"));
		XSSFColor coral = new XSSFColor(Color.decode("#FF7F50"));
		XSSFColor danger = new XSSFColor(Color.decode("#d9534f"));
		XSSFColor warning = new XSSFColor(Color.decode("#f0ad4e"));

		for (int i = 0; i < css.length; i++) {

			if (css[i].equals(Style.BOLD)) {
				font.setBold(true);
			} else if (css[i].equals(Style.TITLE)) {
				font.setFontHeightInPoints((short) 11);
				style.setAlignment(HorizontalAlignment.LEFT);
				style.setAlignment(HorizontalAlignment.CENTER );

			} else if (css[i].equals(Style.TABLE_HEADER)) {
				font.setFontHeightInPoints((short) 11);
				font.setBold(true);
				font.setColor(IndexedColors.BLACK.getIndex() );

				style.setAlignment(HorizontalAlignment.CENTER );
				style.setVerticalAlignment( VerticalAlignment.CENTER );
				style.setFillForegroundColor(Salmon);
				style.setFillPattern( FillPatternType.SOLID_FOREGROUND );
				style.setWrapText(true);

			} else if (css[i].equals(Style.HEADER)) {
				font.setFontHeightInPoints((short) 10);
				font.setBold(true);

				font.setColor(IndexedColors.BLACK.getIndex());
				style.setVerticalAlignment( VerticalAlignment.CENTER );
				style.setAlignment(HorizontalAlignment.LEFT);

				style.setWrapText(false);
				style.setFillForegroundColor(lightSteelBlue);
				style.setFillPattern( FillPatternType.SOLID_FOREGROUND );

			} else if (css[i].equals(Style.CENTER)) {
				style.setAlignment(HorizontalAlignment.CENTER);

			} else if (css[i].equals(Style.BORDER)) {
				style.setBorderRight( BorderStyle.THIN );
				style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderLeft( BorderStyle.THIN );
				style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderTop( BorderStyle.THIN );
				style.setTopBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderBottom( BorderStyle.THIN );
				style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

			} else if (css[i].equals(Style.NUMBER)) {
				font.setFontHeightInPoints((short) 10);
				style.setAlignment( HorizontalAlignment.RIGHT );
				style.setVerticalAlignment( VerticalAlignment.CENTER );
				style.setDataFormat(format.getFormat("#,##0.00"));

			} else if (css[i].equals(Style.CNES)) {
				font.setFontHeightInPoints((short) 10);
				style.setAlignment(HorizontalAlignment.LEFT);
				style.setVerticalAlignment( VerticalAlignment.CENTER );
				style.setDataFormat(format.getFormat("0000000"));

			} else if (css[i].equals(Style.INTEGER)) {
				font.setFontHeightInPoints((short) 10);
				style.setAlignment( HorizontalAlignment.RIGHT );
				style.setVerticalAlignment( VerticalAlignment.CENTER );
				style.setDataFormat(format.getFormat("0"));

			} else if (css[i].equals(Style.PERCENT)) {
				font.setFontHeightInPoints((short) 10);
				style.setAlignment( HorizontalAlignment.RIGHT );
				style.setVerticalAlignment( VerticalAlignment.CENTER );
				style.setDataFormat(format.getFormat("0%"));

			} else if (css[i].equals(Style.VALOR_DESTAQUE)) {
				font.setColor(IndexedColors.BLACK.getIndex());
				style.setFillForegroundColor(coral);
				style.setFillPattern( FillPatternType.SOLID_FOREGROUND );

			} else if (css[i].equals(Style.DESTAQUE_DARK)) {
				style.setFillForegroundColor(SlateGray);
				style.setFillPattern( FillPatternType.SOLID_FOREGROUND );

			} else if (css[i].equals(Style.DESTAQUE_LIGHT)) {
				style.setFillForegroundColor(LightSlateGray);
				style.setFillPattern( FillPatternType.SOLID_FOREGROUND );

			} else if (css[i].equals(Style.DANGER)) {
				style.setFillForegroundColor(danger);
				style.setFillPattern( FillPatternType.SOLID_FOREGROUND );

			} else if (css[i].equals(Style.WARNING)) {
				style.setFillForegroundColor(warning);
				style.setFillPattern( FillPatternType.SOLID_FOREGROUND );

			} else if (css[i].equals(Style.DATE)) {
				style.setDataFormat(this.workbook.createDataFormat().getFormat("dd/mm/yyyy"));
			}
		}

		style.setFont(font);
		return style;

	}

	public byte[] buildToStream() {

		for (int i = 0; i <= this.totalCols; i++) {
			this.workbook.getSheetAt(0).autoSizeColumn(i);
		}
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			this.workbook.write(bos);
			return bos.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	class Cabecalho {

		private String titulo;
		private Style[] estilos;

		Cabecalho(String titulo, Style... estilos) {
			this.titulo = titulo;
			this.setEstilos(estilos);
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public Style[] getEstilos() {
			return estilos;
		}

		public void setEstilos(Style[] estilos) {
			this.estilos = estilos;
		}
	}

}
