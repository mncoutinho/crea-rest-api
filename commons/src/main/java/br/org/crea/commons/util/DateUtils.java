package br.org.crea.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.inject.Inject;

import br.org.crea.commons.dao.financeiro.FinFeriadoDao;



public class DateUtils {
	
	@Inject
	private static FinFeriadoDao finFeriadoDao;
	
	static Locale ptBr = new Locale("pt", "BR");

	public static final SimpleDateFormat DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy", ptBr);
	public static final SimpleDateFormat EEEE = new SimpleDateFormat("EEEE", ptBr);
	public static final SimpleDateFormat DD_MM_YYYY_HH_MM = new SimpleDateFormat("dd/MM/yyyy HH:mm", ptBr);
	public static final SimpleDateFormat HH_MM = new SimpleDateFormat("HH:mm", ptBr);
	public static final SimpleDateFormat a = new SimpleDateFormat("a", ptBr);
	public static final SimpleDateFormat DD_MM_YYYY_HH_MM_SS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", ptBr);
	public static final SimpleDateFormat DD_MM_YYYY_HH_MM_SS_SSS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS", ptBr);
	public static final SimpleDateFormat DD_MM_YYYY_HH_MM_SS_TRACOS = new SimpleDateFormat("dd_MM_yyyy_HH-mm-ss", ptBr);
	public static final SimpleDateFormat DD_MM_YYYY_HH_MM_TRACOS = new SimpleDateFormat("dd_MM_yyyy_HH-mm", ptBr);
	public static final SimpleDateFormat YY = new SimpleDateFormat("yy", ptBr);
	public static final SimpleDateFormat MM = new SimpleDateFormat("MM", ptBr);
	public static final SimpleDateFormat YYYY_MM_DD_COM_TRACOS = new SimpleDateFormat("yyyy-MM-dd", ptBr);
	public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy/MM/dd", ptBr);
	public static final SimpleDateFormat DD = new SimpleDateFormat("dd", ptBr);
	public static final SimpleDateFormat DD_MM = new SimpleDateFormat("dd/MM", ptBr);
	public static final SimpleDateFormat MM_YYYY = new SimpleDateFormat("MM/yyyy", ptBr);
	public static final SimpleDateFormat YYYYMM = new SimpleDateFormat("yyyyMM", ptBr);
	public static final SimpleDateFormat YYYY = new SimpleDateFormat("yyyy", ptBr);

	public static String formataCmptModelParaCmptView(String cmptModel) {
		String cmptView = "";
		if ((StringUtil.isValidAndNotEmpty(cmptModel)) && (cmptModel.length() == 6)) {
			cmptView = cmptModel.substring(4, 5) + "/" + cmptModel.substring(0, 4);
		}
		return cmptView;
	}
	
	public static Calendar dateToCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
  }

	public static String formatCmpt(String cmptView) {

		String cmptRetorno = "";

		if ((cmptView != null) && (!cmptView.equals("")) && (cmptView.length() >= 6)) {
			cmptRetorno = cmptView.substring(2, 6) + cmptView.substring(0, 2);
		}
		return cmptRetorno;
	}

	public static Date convertCmptToDate(String cmpt) {
		YYYYMM.setLenient(false);

		try {
			return YYYYMM.parse(cmpt);
		} catch (ParseException e) {
			return null;
		}

	}

	public static Boolean isCmptInvalida(String cmpt) {
		return convertCmptToDate(cmpt) == null ? true : false;

	}

	public static Boolean isIntervaloCmptInvalido(String cmptInicial, String cmptFinal) {
		Date dataInicial = convertCmptToDate(cmptInicial);
		Date dataFinal = convertCmptToDate(cmptFinal);

		if (dataInicial == null || dataFinal == null)
			return true;

		return convertCmptToDate(cmptInicial).after(convertCmptToDate(cmptFinal));

	}

	public static Boolean isIntervaloCmptExercicioInvalido(String cmptInicial, String cmptFinal) {

		return !isMesmoAno(convertCmptToDate(cmptInicial), convertCmptToDate(cmptFinal));

	}

	public static String format(Date data, SimpleDateFormat formato) {
		if (data != null) {
			return formato.format(data);
		}
		return "";
	}



	public static boolean isMesmoDia(Date data1, Date data2) {

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);

		return calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
				&& calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
	}

	public static boolean isMesmoAno(Date data1, Date data2) {

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);

		return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
	}

	public static boolean isMesmoMesEAno(Date data1, Date data2) {

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);

		return calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
	}
	
	public static boolean isMesmoDiaMesAno(Date data1, Date data2) {

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);

		return calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
	}

	public static Date adicionaHorasA(Date data, int horas) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		calendar.add(Calendar.HOUR, horas);

		return calendar.getTime();

	}
	
	public static String convertCalendarToString(Calendar calendar) {
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		return format1.format(calendar.getTime());
	}
	

	public static Date adicionaOrSubtraiDiasA(Date data, int dias) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		calendar.add(Calendar.DAY_OF_YEAR, dias);

		return calendar.getTime();

	}
	
	public static Date adicionaOrSubtraiMesesA(Date data, int meses) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		calendar.add(Calendar.MONTH, meses);

		return calendar.getTime();

	}

	public static boolean primeiraDataeMenorQueSegunda(Date data1, Date data2) {
		int comparacao = data1.compareTo(data2);

		if (comparacao < 0) {
			return true;
		}
		return false;
	}

	
	public static boolean primeiraDataeMenorIgualQueSegunda(Date data1, Date data2) {
		int comparacao = data1.compareTo(data2);

		if (comparacao <= 0) {
			return true;
		}
		return false;
	}
	
	public static boolean primeiraDataeMaiorQueSegunda(Date data1, Date data2) {
		int comparacao = data1.compareTo(data2);

		if (comparacao > 0) {
			return true;
		}
		return false;
	}

	public static boolean primeiraDataeMaiorOuIgualQueSegunda(Date data1, Date data2) {
		int comparacao = data1.compareTo(data2);

		if (comparacao >= 0) {
			return true;
		}
		return false;
	}

	
	
	public static boolean eMaiorOuIgualAoDiaAtual(Date data) {

		Date dataAtual = Calendar.getInstance().getTime();

		int comparacao = dataAtual.compareTo(data);

		if (comparacao < 0 || DateUtils.isMesmoDia(dataAtual, data)) {
			return true;
		}
		return false;

	}

	public static boolean eMenorOuIgualAoDiaAtual(Date data) {

		Date dataAtual = Calendar.getInstance().getTime();

		int comparacao = dataAtual.compareTo(data);

		if (comparacao > 0 || DateUtils.isMesmoDia(dataAtual, data)) {
			return true;
		}
		return false;

	}

	public static boolean eMenorQueoDiaAtual(Date data) {

		Date dataAtual = Calendar.getInstance().getTime();

		int comparacao = dataAtual.compareTo(data);

		if (comparacao > 0) {
			return true;
		}
		return false;
	}

	public static boolean eNoDiaAtual(Date data) {

		Date dataAtual = DateUtils.getDataAtual();

		if (isMesmoDia(data, dataAtual)) {
			return true;
		}
		return false;
	}

	public static String getDataAtual(SimpleDateFormat formato) {
		return formato.format(Calendar.getInstance().getTime());
	}

	public static Date getDataAtual() {
		return Calendar.getInstance().getTime();
	}

	public static Date getDataComUltimoHorario(Date data) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}

	public static Integer get(Date data, int tipoCalendar) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		return calendar.get(tipoCalendar);
	}

	public static Date getDataAtualComUltimoHorario() {
		return getDataComUltimoHorario(DateUtils.getDataAtual());
	}

	public static Date getDataAnteriorComHorarioFinal() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Calendar.getInstance().getTime());
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	public static Calendar getDataAtualCalendar() {
		return Calendar.getInstance();
	}

	public static Calendar converteDataParaFinalDoDiaCorrente(Calendar dataAtual) {
		dataAtual.set(Calendar.HOUR_OF_DAY, 23);
		dataAtual.set(Calendar.MINUTE, 59);
		dataAtual.set(Calendar.SECOND, 59);
		dataAtual.set(Calendar.MILLISECOND, 59);

		return dataAtual;
	}

	public static Long converteMilisegundosParaMinutos(long milisegundos) {
		return milisegundos / 1000 / 60;
	}

	public static int getAnoCorrente() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	public static int getMesCorrente() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH);
	}

	public static boolean isMesmoPeriodo(Date data1, Date data2, int tipoCalendar) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);

		return calendar1.get(tipoCalendar) == calendar2.get(tipoCalendar);
	}


	public static Date generateDate(String data) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return (Date) format.parseObject(data);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean validaDataFormatada(String data) {

		String regex = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)$";

		if (Pattern.matches(regex, data)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean validaAno(String ano) {

		String regex = "^((19|20)\\d\\d)$";

		if (Pattern.matches(regex, ano)) {
			return true;
		} else {
			return false;
		}

	}

	public static Date convertCompetenciaToDate(String data) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			return (Date) format.parseObject(data);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date convertStringToDate(String data) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return (Date) format.parseObject(data);
		} catch (Exception e) {
			return null;
		}
	}

	public static String convertCompetenciaToParcialDate(String data) {
		try {
			return data.substring(4) + "/" + data.substring(0, 4);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date convertStringToDate(String data, SimpleDateFormat formato) {
		
		try {
			return (Date) formato.parse(data);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean eMaiorQueoDiaAtual(Date data) {

		Date dataAtual = Calendar.getInstance().getTime();

		int comparacao = dataAtual.compareTo(data);

		if (comparacao < 0) {
			return true;
		}
		return false;
	}

	public static boolean isCompetenciaVigente(String cmptInicio, String cmptFim) {
		boolean retornoInicio = (!isCmptInvalida(cmptInicio) ? eMenorOuIgualAoDiaAtual(convertCmptToDate(cmptInicio)) : false);
		boolean retornoFim = (!isCmptInvalida(cmptFim) ? eMaiorOuIgualAoDiaAtual(convertCmptToDate(cmptFim)) : false);

		return (retornoInicio && retornoFim);
	}

	public static boolean isCompetenciaVigente(String cmptInicio) {
		return (!isCmptInvalida(cmptInicio) ? eMenorOuIgualAoDiaAtual(convertCmptToDate(cmptInicio)) : false);
	}

	public static String getCompetenciaAtual() {
		Calendar cal = Calendar.getInstance();
		int mes = (cal.get(Calendar.MONTH) + 1);
		return (cal.get(Calendar.YEAR) + "" + (mes < 10 ? "0" + mes : mes)).toString();
	}
	
	public static LocalDate convertDateToLocalDate(Date date) {
		
		Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	}
	
	public static long getDiferencaDiasEntreDatas(Date dataInicio, Date dataFinal) {
		return ChronoUnit.DAYS.between(convertDateToLocalDate(dataFinal), convertDateToLocalDate(dataInicio));
	}

	public static Date getUltimoDiaDoAnoCorrente() {
		return convertStringToDate("31/12/" + getAnoCorrente());
	}
	
	public static Date getEnesimoDiaUtilDoMesAno(int qtdDiaUtil, String mes, String ano) {
		Calendar data = new GregorianCalendar(Integer.parseInt(ano), Integer.parseInt(mes)-1, 1);
		
		int dias = 0;
		while (dias < qtdDiaUtil) {
			if (data.get(Calendar.DAY_OF_WEEK) == 1) {
				data.add(Calendar.DAY_OF_MONTH, 1);
			} else if (data.get(Calendar.DAY_OF_WEEK) == 7) {
				data.add(Calendar.DAY_OF_MONTH, 2);
			} else if (finFeriadoDao.verificaSeEhFeriado(data.getTime())) {
				data.add(Calendar.DAY_OF_MONTH, 1);
			} else {
				data.add(Calendar.DAY_OF_MONTH, 1);
				dias = dias + 1;
			}
		}
		data.add(Calendar.DAY_OF_MONTH, -1);
		
		return data.getTime();
	}
}
