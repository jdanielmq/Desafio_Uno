package cl.previred.app.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.previred.app.data.dto.ResponseDto;

public class Fecha {
	private static final Logger logger = LoggerFactory.getLogger(Fecha.class);
	
	public static final String FORMAT_YYYYMMDD_GUION = "YYYY'-'MM'-'dd";
	public static final String FORMAT_YYYYMMDD_ISO = "yyyy-MM-dd";
	
	/**
	 *  Metodo de formateo de Fecha
	 *  
	 * @param LocalDate  
	 * @return String
	 */
	public static String getLocalDateTimeString(LocalDate date) throws Exception, IllegalArgumentException {
		try {
			Locale locale = new Locale("es", "CL");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYYMMDD_GUION, locale);
			return date.format(formatter);		
		}catch (DateTimeParseException e) {
			logger.warn("Error en la clase Fecha al pasar LocalDate a String", e);
				throw new IllegalArgumentException("Ha ocurrido un problema en el método getLocalDateTimeString(date) - error: ".concat(e.getMessage()));
		}catch (Exception e) {
			logger.error("Error inesperado en la clase Fecha->getLocalDateTimeString", e);
				throw new Exception("Ha ocurrido un problema en el método getLocalDateTimeString(date) - error: ".concat(e.getMessage()));
		}
	}
	
	/**
	 *  Metodo para generar el listado de fechas
	 *  
	 * @param  ResponseDto
	 * @return List<String> 
	 * @exception Exception, IllegalArgumentException
	 */
	public static List<String> listadoFechasFaltantes(ResponseDto response) throws Exception, IllegalArgumentException { 
		List<String> fechaFaltante = new ArrayList<String>();
		try {
			Locale locale = new Locale("es", "CL");
			LocalDate start = LocalDate.parse(response.getFechaCreacion(), DateTimeFormatter.ofPattern(FORMAT_YYYYMMDD_ISO, locale));
			LocalDate end = LocalDate.parse(response.getFechaFin(), DateTimeFormatter.ofPattern(FORMAT_YYYYMMDD_ISO, locale));

			for (LocalDate date = start; !date.isAfter(end); date = date.plusMonths(1)) { 
				String fechaCreada = Fecha.getLocalDateTimeString(date);
				if(!response.getFechas().contains(fechaCreada)) {
					fechaFaltante.add(fechaCreada); 
				}
			}	
			
		}catch (DateTimeParseException e) {
			logger.warn("Error en la clase Fecha al crear la lista de fechas faltantes", e);
			throw new IllegalArgumentException("Ha ocurrido un problema en el método listadoFechas(fechaIni,fechaFin) - error: ".concat(e.getMessage()));
		}catch (Exception e) {
			logger.error("Error inesperado en la clase Fecha->listadoFechasFaltantes", e);
			throw e;
		}

		return fechaFaltante; 
	}
	

}
