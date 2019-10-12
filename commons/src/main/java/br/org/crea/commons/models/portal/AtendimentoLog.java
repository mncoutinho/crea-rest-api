package br.org.crea.commons.models.portal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.commons.TipoAtendimento;

	@Entity
	@Table(name="ATE_ATENDIMENTO_LOG")
	@SequenceGenerator(name="ATENDIMENTO_LOG_SEQUENCE",sequenceName="ATE_ATENDIMENTO_LOG_SEQ")
	public class AtendimentoLog {
	
		@Id
		@Column(name="CODIGO")
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ATENDIMENTO_LOG_SEQUENCE")
		private Long codigo;
		
		@OneToOne
		@JoinColumn(name="FK_CODIGO_ATENDIMENTO")
		private Atendimento atendimento;
		
		@OneToOne
		@JoinColumn(name="FK_CODIGO_TIPO_ATENDIMENTO")
		private TipoAtendimento tipoAtendimento;
		
		@Column(name="HORA_ATENDIMENTO")
		@Temporal(TemporalType.TIMESTAMP)
		private Date horaAtendimento;

		public Long getCodigo() {
			return codigo;
		}

		public void setCodigo(Long codigo) {
			this.codigo = codigo;
		}

		public Atendimento getAtendimento() {
			return atendimento;
		}

		public void setAtendimento(Atendimento atendimento) {
			this.atendimento = atendimento;
		}

		public TipoAtendimento getTipoAtendimento() {
			return tipoAtendimento;
		}

		public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
			this.tipoAtendimento = tipoAtendimento;
		}

		public Date getHoraAtendimento() {
			return horaAtendimento;
		}

		public void setHoraAtendimento(Date horaAtendimento) {
			this.horaAtendimento = horaAtendimento;
		}
	
	
}
