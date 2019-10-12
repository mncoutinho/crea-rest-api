package br.org.crea.commons.report;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;

import br.org.crea.commons.util.StringUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class ReportCrea {
	

	private String templateSubreport;
	private String template;
	private List<Object> lista;
	private TypeExportEnum tipo;
	private final Map<String, Object> params = new HashMap<String, Object>();
	private JRDataSource source;
	private File tempFile;
	private String pathTemp;
	
		
	public ReportCrea listDataSourcePreview(List<Object> list) {
		this.source = new JRBeanCollectionDataSource(list);
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("brazil.jpg").getFile());
		InputStream brasao = null;
		try {
			brasao = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		params.put("BRASAO", brasao);
		return this;

	}

	public ReportCrea listDataSource(List<Object> list) {
		this.source = new JRBeanCollectionDataSource(list);
		File file = new File("/opt/images/brazil.jpg");
		InputStream brasao = null;
		try {
			brasao = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		params.put("BRASAO", brasao);
		return this;
	}
	
	public ReportCrea objectDataSource(List<Map<String, Object>> listParamsObject) throws IllegalArgumentException, IllegalAccessException, IOException {

		this.source = new JRBeanCollectionDataSource((Collection<?>) listParamsObject);
		
		File fileBrasao = new File("/opt/images/brazil.jpg");
		InputStream brasao = null;
		brasao = new FileInputStream(fileBrasao);
		InputStream assinatura = null;
		
		File chancelaAssinatura = getChancelaAssinante(listParamsObject); 
		
		if( chancelaAssinatura != null ) {
			
			assinatura = new FileInputStream(chancelaAssinatura);
			params.put("ASSINATURA", assinatura);
		}

		params.put("BRASAO", brasao);

		return this;
	}
	public ReportCrea objectDataSourceArt(List<Map<String, Object>> listParamsObject) throws IllegalArgumentException, IllegalAccessException, IOException {

		this.source = new JRBeanCollectionDataSource((Collection<?>) listParamsObject);
		
		File fileBrasao = new File("/opt/images/brazil.jpg");
		InputStream brasao = null;
		brasao = new FileInputStream(fileBrasao);

		params.put("BRASAO", brasao);

		return this;
	}

	public ReportCrea template(String template) {
		this.template = template;
		return this;
	}
	
	public ReportCrea pathTemp(String path) {
		this.pathTemp = path;
		return this;
	}
	
	public ReportCrea templateSubreport(String templateSubreport){
		this.templateSubreport = templateSubreport;
		params.put("SUBREPORT_DIR", this.templateSubreport);
		return this;
	}
	
	public ReportCrea pdf(){
		this.tipo = TypeExportEnum.PDF;

		params.put("TIPO_REL", "PDF");
		params.put(JRParameter.IS_IGNORE_PAGINATION,  Boolean.FALSE);
		params.put(JRParameter.REPORT_LOCALE,  new Locale("pt","BR"));
		return this;
	}

	public ReportCrea html(){
		this.tipo = TypeExportEnum.HTML;
		params.put("TIPO_REL", "HTML");
		return this;
	}

	public ReportCrea xls(){
		params.put("TIPO_REL", "XLS");
		return this;
	}
	public JRDataSource getSource() {
		return source;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplateLocalTest(String template){
		this.template = template;
	}
	public List<Object> getLista() {
		return lista;
	}
	public void setLista(List<Object> lista) {
		this.lista = lista;
	}
	public TypeExportEnum getTipo() {
		return tipo;
	}
	public void setTipo(TypeExportEnum tipo) {
		this.tipo = tipo;
	}
	public ReportCrea param(Object key, Object value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException("key nor value can be null.");
		}
		params.put((String) key, value);
		return this;
	}
	
	public File getTempFile() {
		return tempFile;
	}
	
	public File getChancelaAssinante(List<Map<String, Object>> listParams) throws IllegalArgumentException, IllegalAccessException, IOException {
		File a =  null;
		try {
			Map<String, Object> paramsObject = new HashMap<String, Object>();
			paramsObject = listParams.get(0); 
			
			a = imageToFile((InputStream)paramsObject.get("inputAssinatura"));		
		} catch (Exception e) {
			a = null;
		}
		return a;
		
	}

	public boolean ehPdf(){
		return this.tipo.equals(TypeExportEnum.PDF);
	}

	public boolean ehXls(){
		return this.tipo.equals(TypeExportEnum.XLS);
	}

	public boolean ehHtml(){
		return this.tipo.equals(TypeExportEnum.HTML);
	}
	public Map<String, Object> getParams() {
		return params;
	}
	
	 public File imageToFile(InputStream image) throws IOException {
			
		File imageFile = new File(pathTemp + StringUtil.randomUUID() + ".jpg");
		BufferedImage imageResult = ImageIO.read(image);  
		
		if( imageResult != null ) {
	
			ImageIO.write(imageResult, "jpg", imageFile);
			this.tempFile = imageFile;
			return tempFile;
			
		} else {
			
			return null;
		}
	}

}
