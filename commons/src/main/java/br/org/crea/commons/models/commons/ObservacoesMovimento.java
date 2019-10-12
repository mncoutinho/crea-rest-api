package br.org.crea.commons.models.commons;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.protocolo.ObservacaoProtocolo;


@Entity
@Table(name = "PRT_OBSERVACOESMOVIMENTOS")
@SequenceGenerator(name="OBSERVACOESMOVIMENTOS_SEQUENCE",sequenceName="PRT_OBSERVACOESMOVIMENTOS_SEQ",allocationSize = 1)
public class ObservacoesMovimento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OBSERVACOESMOVIMENTOS_SEQUENCE")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "DATA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Column(name="ANEXOPDF")
	@Lob
	@Basic(fetch=FetchType.LAZY)
	private byte[] anexo;
	
	@OneToOne
	@JoinColumn(name = "FK_ID_MOVIMENTOS")
	private Movimento movimento;
	
	@OneToOne
	@JoinColumn(name = "FK_ID_OBSERVACOES")
	private ObservacaoProtocolo observacao;
	
	@Column(name = "FK_ID_DEPARTAMENTOS")
	private Long idDepartamento;
	
	@Column(name = "FK_ID_FUNCIONARIOS")
	private Long idFuncionario;
	
	@Column(name = "FK_ID_CAD_DOCUMENTOS")
	private Long idCadDocumento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public byte[] getAnexo() {
		return anexo;
	}

	public void setAnexo(byte[] anexo) {
		this.anexo = anexo;
	}

	public Movimento getMovimento() {
		return movimento;
	}

	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}

	public ObservacaoProtocolo getObservacao() {
		return observacao;
	}

	public void setObservacao(ObservacaoProtocolo observacao) {
		this.observacao = observacao;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Long getIdCadDocumento() {
		return idCadDocumento;
	}

	public void setIdCadDocumento(Long idCadDocumento) {
		this.idCadDocumento = idCadDocumento;
	}

}
