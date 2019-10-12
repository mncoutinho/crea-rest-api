package br.org.crea.commons.models.art;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;


@Entity
@Table(name="ART_GERADOR_SEQUENCIAS")
public class GeradorSequenciaArt implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 7045216505375495538L;

	@Id
	@Column(name="CODIGO")
    private Long codigo;
    
    @Column(name="ALFA")
    private String alfa;
    
    @Column(name="ANO")
    private String ano; 
    
    @Column(name="SEQUENCIAL")
    private long sequencial;
    
    @Version
	@Column(name="VERSION")
    private long version;
    
    
    public String getAlfa() {
        return alfa;
    }
    public void setAlfa(String alfa) {
        this.alfa = alfa;
    }
    
    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public long getSequencial() {
        return sequencial;
    }
    public void setSequencial(long sequencial) {
        this.sequencial = sequencial;
    }
    
    public long getVersion() {
        return version;
    }
    public void setVersion(long version) {
        this.version = version;
    }
    
    public String getAno() {
        return ano;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
	public String getAnoAtual() {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}
	public void proximo() {
		this.setSequencial(sequencial + 1);
		this.setVersion(version + 1);
	}
}
