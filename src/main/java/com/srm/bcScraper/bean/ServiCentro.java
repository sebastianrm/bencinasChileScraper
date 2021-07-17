/**
 * 
 */
package com.srm.bcScraper.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sebastian
 *
 */
public class ServiCentro {

	private String empresa;
	private String region;
	private String direccion;
	private Boolean autoservicio;
	private BigDecimal precio;
	private String tipoGasolina;
	private Date ultimaModificacion;
	private Date fcaExtraccion;
	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the autoservicio
	 */
	public Boolean getAutoservicio() {
		return autoservicio;
	}
	/**
	 * @param autoservicio the autoservicio to set
	 */
	public void setAutoservicio(Boolean autoservicio) {
		this.autoservicio = autoservicio;
	}
	/**
	 * @return the precio
	 */
	public BigDecimal getPrecio() {
		return precio;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	/**
	 * @return the tipoGasolina
	 */
	public String getTipoGasolina() {
		return tipoGasolina;
	}
	/**
	 * @param tipoGasolina the tipoGasolina to set
	 */
	public void setTipoGasolina(String tipoGasolina) {
		this.tipoGasolina = tipoGasolina;
	}
	/**
	 * @return the ultimaModificacion
	 */
	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}
	/**
	 * @param ultimaModificacion the ultimaModificacion to set
	 */
	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	/**
	 * @return the fcaExtraccion
	 */
	public Date getFcaExtraccion() {
		return fcaExtraccion;
	}
	
	/**
	 * @param fcaExtraccion the fcaExtraccion to set
	 */
	public void setFcaExtraccion(Date fcaExtraccion) {
		this.fcaExtraccion = fcaExtraccion;
	}
	@Override
	public String toString() {
		return "ServiCentro [empresa=" + empresa + ", region=" + region + ", direccion=" + direccion + ", autoservicio="
				+ autoservicio + ", precio=" + precio + ", tipoGasolina=" + tipoGasolina + ", ultimaModificacion="
				+ ultimaModificacion + ", Fecha Extraccion=" + fcaExtraccion + "]";
	}
	
	
	
}
