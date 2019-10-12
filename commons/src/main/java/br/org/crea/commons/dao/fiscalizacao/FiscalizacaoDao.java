package br.org.crea.commons.dao.fiscalizacao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.fiscalizacao.dtos.AutoInfracaoDto;
import br.org.crea.commons.models.fiscalizacao.dtos.AutoReincidenciaDto;
import br.org.crea.commons.models.fiscalizacao.dtos.MultaInfracaoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class FiscalizacaoDao {
	
	@Inject HttpClientGoApi httpGoApi;

	@PersistenceContext(unitName = "dscrea")
	protected EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	public AutoInfracaoDto getAutoInfracaoPor(Long numeroProtocolo) {
		AutoInfracaoDto infracao = new AutoInfracaoDto();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT fro.numero rf, ");
		sql.append("	 cpf.nome fiscal, ");
		sql.append("	 ramo.codigo || ' - ' || ramo.descricao ramo, ");
		sql.append("	 fg.id codigo_fato_gerador, ");
		sql.append("	 fg.descricao fato_gerador, ");
		sql.append("	 fro.descritivo_fato_gerador descritivo_fg_rf, "); 
		sql.append("     fcf.data_constatacao constatacao_ai, ");
		sql.append("     fa.data_emissao lavratura_ai,  ");
		sql.append("	 fa.valor valor_ai, ");
		sql.append("     cend.codigo end_codigo, ");
		sql.append(" 	 ctl.descricao end_tipo, ");
		sql.append("     ctl.abreviatura end_abrev, ");
		sql.append("	 cend.logradouro logradouro, ");
		sql.append("	 cend.numero numero_end, ");
		sql.append("	 NVL(cend.complemento, 'NAO POSSUI') complemento, ");
		sql.append("     cend.bairro, ");
		sql.append("     NVL(cend.cep, 'NAO INFORMADO')cep, ");
		sql.append("	 loc.descricao localidade, ");
		sql.append("     ufs.uf estado, ");
		sql.append("	 fonte.nome fonte_informacao, ");
		sql.append("	 fonte.qualificacao fonte_qualificacao, ");
		sql.append("     pessoa.codigo registro_pessoa_autuada, ");
		sql.append("     cfunc.matricula matricula_fiscal, ");
		sql.append("	 (CASE ");
		sql.append("	   WHEN pessoa.tipopessoa = 'PESSOAJURIDICA' OR pessoa.tipopessoa = 'LEIGOPJ' OR pessoa.tipopessoa = 'EMPRESA' OR ");
		sql.append("            pessoa.tipopessoa = 'ENTIDADE' OR pessoa.tipopessoa = 'ESCOLA' ");
		sql.append("       THEN (SELECT rs.descricao FROM cad_razoes_sociais rs WHERE rs.fk_codigo_pjs = pessoa.codigo AND rs.ativo = 1) ");
		sql.append("	   ELSE (SELECT pf.nome FROM cad_pessoas_fisicas pf WHERE pf.codigo = pessoa.codigo) ");
		sql.append("      END) pessoa_autuada, ");
		sql.append("      fti.codigo cod_tp_infracao, ");
		sql.append("      fti.descricao tp_infracao, ");
		sql.append("      fti.fundamento letra, ");
		sql.append("      fti.enquadramento enquadramento ");
		sql.append("	  FROM fis_autoinfracao fa, ");
		sql.append("	  fis_rf_fiscalizado frf, ");
		sql.append("	  fis_rf_online fro ");
		sql.append("	  LEFT JOIN cad_enderecos cend ON (fro.fk_endereco = cend.codigo) ");
		sql.append("	  LEFT JOIN cad_tipos_logradouros ctl ON (cend.fk_codigo_tipos_logradouros = ctl.codigo) ");
		sql.append("      LEFT JOIN cad_localidades loc ON (cend.fk_codigo_localidades = loc.codigo) ");
		sql.append("	  LEFT JOIN cad_ufs ufs ON (cend.fk_codigo_ufs = ufs.codigo), ");
		sql.append("	  fis_rf_fato_gerador fg, ");
		sql.append("	  fis_tipo_infracao fti, ");
		sql.append("	  fis_rf_constatacao_fiscal fcf ");
		sql.append("	  LEFT JOIN art_ramo ramo ON fcf.fk_ramo_art = ramo.codigo, ");
		sql.append("	  fis_rf_fonte_informacao fonte, ");
		sql.append("	  cad_funcionarios cfunc, ");
		sql.append("	  cad_pessoas_fisicas cpf, ");
		sql.append("	  cad_pessoas pessoa ");
		sql.append("	  WHERE fa.numero = :numeroProtocolo ");
		sql.append("	  AND frf.fk_pessoa = pessoa.codigo ");
		sql.append("	  AND fti.codigo = fa.fk_codigo_tipo_infracao ");
		sql.append("      AND fa.fk_rf_fiscalizado = frf.id ");
		sql.append("	  AND frf.fk_rf = fro.id ");
		sql.append("      AND fro.fk_fato_gerador = fg.id ");
		sql.append("	  AND fcf.fk_fiscalizado = frf.id ");
		sql.append("	  AND fcf.fk_fonte_informacao = fonte.id ");
		sql.append("	  AND fro.fk_fiscal = cfunc.codigo ");
		sql.append("	  AND cfunc.fk_id_pessoas_fisicas = cpf.codigo ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			
			Iterator<Object> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					
					infracao.setNumeroRf(result[0] == null ? "" : result[0].toString());
					infracao.setNomeFiscal(result[1] == null ? "" : result[1].toString());
					infracao.setRamoFiscalizado(result[2] == null ? "" : result[2].toString());
					infracao.setCodigoFatoGerador(result[3] == null ? "" : result[3].toString());
					infracao.setDescricaoFatoGerador(result[4] == null ? "" : result[4].toString());
					infracao.setDescritivoFatoGeradorRf(result[5] == null ? "" : result[5].toString());
					infracao.setDataConstatacaoFiscal(result[6] == null ? null : (Date)result[6]);
					infracao.setDataConstatacaoFiscalFormatada(result[6] == null ? null : DateUtils.format((Date)result[6], DateUtils.DD_MM_YYYY));
					infracao.setDataLavraturaAuto(result[7] == null ? null : (Date)result[7]);
					infracao.setDataLavraturaAutoFormatada(result[7] == null ? null : DateUtils.format((Date)result[7], DateUtils.DD_MM_YYYY));
					
					BigDecimal valorMulta = (BigDecimal) result[8];
					infracao.setValorMulta(valorMulta == null ? null : valorMulta.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					
					infracao.setFonteInformacao(result[19] == null ? "" : result[19].toString());
					infracao.setQualificacaoFonteInformacao(result[20] == null ? "" : result[20].toString());
					infracao.setRegistroPessoaAutuada(result[21] == null ? null : ((BigDecimal)result[21]).longValue());
					infracao.setMatriculaFiscal(result[22] == null ? "" : result[22].toString()); 
					infracao.setNomePessoaAutuada(result[23] == null ? "" : result[23].toString());
					
					DomainGenericDto tipoInfracao = new DomainGenericDto();
					tipoInfracao.setId(result[24] == null ? null : ((BigDecimal)result[24]).longValue());
					tipoInfracao.setNome(result[25] == null ? "" : result[25].toString());
					infracao.setTipoAuto(tipoInfracao);
					infracao.setDescricaoCapitulacao(result[25] == null ? "" : result[25].toString());
					infracao.setEnquadramentoCapitulacao(result[27] == null ? "" : result[27].toString());
					
					infracao.setLetraFundamento(result[26] == null ? "" : result[26].toString());
					
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
					infracao.setValoresAplicaveis(getValoresAplicaveisInfracaoPor(infracao.getLetraFundamento(), new Long (simpleDateFormat.format(infracao.getDataConstatacaoFiscal()).toUpperCase())));
					
					BigDecimal idEndereco = (BigDecimal) result[9];
					if (idEndereco != null) {
						EnderecoDto enderecoLocalFiscalizado = new EnderecoDto();
						enderecoLocalFiscalizado.setId(idEndereco.setScale(0, BigDecimal.ROUND_UP).longValueExact());
						
						DomainGenericDto tipoEndereco = new DomainGenericDto();
						tipoEndereco.setDescricao(result[10] == null ? "" : result[10].toString());
						
						enderecoLocalFiscalizado.setTipoEndereco(tipoEndereco);
						
						DomainGenericDto tipoLogradouro = new DomainGenericDto();
						tipoLogradouro.setDescricao(result[10] == null ? "" : result[10].toString());
						
						enderecoLocalFiscalizado.setTipoLogradouro(tipoLogradouro);
						enderecoLocalFiscalizado.setLogradouro(result[12] == null ? "" : result[12].toString());
						enderecoLocalFiscalizado.setNumero(result[13] == null ? "" : result[13].toString());
						enderecoLocalFiscalizado.setComplemento(result[14] == null ? "" : result[14].toString());
						enderecoLocalFiscalizado.setBairro(result[15] == null ? "" : result[15].toString());
						enderecoLocalFiscalizado.setCep(result[16] == null ? "" : result[16].toString());
						String localidade = result[17] == null ? "" : result[17].toString();
						String uf = result[18] == null ? "" : result[18].toString();
						String enderecoDescritivo = (enderecoLocalFiscalizado.getTipoLogradouro() != null ? enderecoLocalFiscalizado.getTipoLogradouro().getDescricao() + " - " : "") + enderecoLocalFiscalizado.getLogradouro() + " - " + 
                                                enderecoLocalFiscalizado.getNumero() + " - " + enderecoLocalFiscalizado.getCep()
								+ " - " + localidade + " - " + uf;
						enderecoLocalFiscalizado.setEnderecoCompleto(enderecoDescritivo);
						infracao.setEnderecoLocalFiscalizado(enderecoLocalFiscalizado);
					}
				}
			}
			
			infracao.setEnderecoPessoaAutuada(getEnderecoPessoaAutuadaPor(numeroProtocolo));
			getAtividadeRelatorioVisita(numeroProtocolo, infracao);
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("FiscalizacaoDao || getAutoInfracaoPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		
		return infracao;
	}
	
	@SuppressWarnings("unchecked")
	public EnderecoDto getEnderecoPessoaAutuadaPor(Long numeroProtocolo) {
		EnderecoDto enderecoPessoaAutuada = new EnderecoDto();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT cend.codigo cod_end, ");
		sql.append("   ctl.descricao end_tipo, ");
		sql.append("   ctl.abreviatura tipo_log, ");
		sql.append("   cend.logradouro logradouro, ");
		sql.append("   cend.numero numero_end, ");
		sql.append("   NVL(cend.complemento, 'NAO POSSUI') complemento, ");
		sql.append("   cend.bairro, ");
		sql.append("   NVL(cend.cep, 'NAO INFORMADO') cep, ");
		sql.append("   loc.descricao localidade, ");
		sql.append("   ufs.uf estado ");
		sql.append("   FROM fis_autoinfracao fa, ");
		sql.append("   		fis_rf_fiscalizado frf ");
		sql.append("   LEFT JOIN cad_enderecos cend ON (frf.fk_pessoa = cend.fk_codigo_pessoas) ");
		sql.append("   LEFT JOIN cad_tipos_logradouros ctl ON (cend.fk_codigo_tipos_logradouros = ctl.codigo) ");
		sql.append("   LEFT JOIN cad_localidades loc ON (cend.fk_codigo_localidades = loc.codigo) ");
		sql.append("   LEFT JOIN cad_ufs ufs ON (cend.fk_codigo_ufs = ufs.codigo) ");
		sql.append("   WHERE fa.numero = :numeroProtocolo ");
		sql.append("   AND fa.fk_rf_fiscalizado = frf.id ");
		
		try {
		
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			
			Iterator<Object> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					BigDecimal idEndereco = (BigDecimal) result[0];
					if (idEndereco != null) {
						enderecoPessoaAutuada.setId(idEndereco.setScale(0, BigDecimal.ROUND_UP).longValueExact());
						
						DomainGenericDto tipoEndereco = new DomainGenericDto();
						tipoEndereco.setDescricao(result[1] == null ? "" : result[1].toString());
						enderecoPessoaAutuada.setTipoEndereco(tipoEndereco);
						
						DomainGenericDto tipoDescricao = new DomainGenericDto();
						tipoDescricao.setDescricao(result[1] == null ? "" : result[1].toString());
						
						enderecoPessoaAutuada.setTipoLogradouro(tipoDescricao);
						enderecoPessoaAutuada.setLogradouro(result[3] == null ? "" : result[3].toString());
						enderecoPessoaAutuada.setNumero(result[4] == null ? "" : result[4].toString());
						enderecoPessoaAutuada.setComplemento(result[5] == null ? "" : result[5].toString());
						enderecoPessoaAutuada.setBairro(result[6] == null ? "" : result[6].toString());
						enderecoPessoaAutuada.setCep(result[7] == null ? "" : result[7].toString());
						String localidade = result[8] == null ? "" : result[8].toString();
						String uf = result[9] == null ? "" : result[9].toString();
						String enderecoDescritivo = (enderecoPessoaAutuada.getTipoLogradouro() != null ? enderecoPessoaAutuada.getTipoLogradouro().getDescricao() + " - " : "") + enderecoPessoaAutuada.getLogradouro() + " - " + 
                                                enderecoPessoaAutuada.getNumero() + " - " + enderecoPessoaAutuada.getCep()
								+ " - " + localidade + " - " + uf;
						enderecoPessoaAutuada.setEnderecoCompleto(enderecoDescritivo);
					}
				}
			}
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			
			httpGoApi.geraLog("FiscalizacaoDao || getEnderecoPessoaAutuadaPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return enderecoPessoaAutuada;
	}
	
	@SuppressWarnings("unchecked")
	public List<AutoReincidenciaDto> getListAutoInfracaoMesmaCapitulacaoPor(Long numeroProtocolo) {
		List<AutoReincidenciaDto> listAutosReincidentes = new ArrayList<AutoReincidenciaDto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT resc.numero numero_infracao, ");
		sql.append("	   fti.descricao tipo_infracao, ");
		sql.append("	   fre.deliberacao deliberacao, ");
		sql.append("	   fre.data_decisao data_decisao, ");
		sql.append("	   resc.status_analise status_analise, ");
		sql.append("	   fre.data_inclusao data_inclusao ");
		sql.append("	FROM fis_autoinfracao fa, ");
		sql.append("		 fis_reincidencia fre, ");
		sql.append("		 fis_autoinfracao resc, ");
		sql.append("		 fis_tipo_infracao fti ");
		sql.append("	WHERE fa.numero = :numeroProtocolo ");
		sql.append("	AND fa.fk_codigo_pessoa = fre.fk_id_pessoas ");
		sql.append("	AND fre.fk_id_autoinfracao != fa.codigo ");
		sql.append("	AND resc.codigo = fre.fk_id_autoinfracao ");
		sql.append("	AND fti.CODIGO = resc.FK_CODIGO_TIPO_INFRACAO ");
		sql.append("	AND fa.fk_codigo_tipo_infracao = fre.fk_id_fis_tipo_infracao ");
		sql.append("	AND TO_CHAR(fa.data_cadastro, 'DD/MM/YYYY') >= '01/01/2010' ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			
			Iterator<Object> it = query.getResultList().iterator();
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					
					AutoReincidenciaDto reincidencia = new AutoReincidenciaDto();
					reincidencia.setNumeroAuto   (result[0] == null ? "" : result[0].toString());
					reincidencia.setTipoInfracao (result[1] == null ? "" : result[1].toString());
					reincidencia.setDeliberacao  (result[2] == null ? "" : result[2].toString());
					reincidencia.setDataDecisao  (result[3] == null ? "" : DateUtils.format((Date)result[3], DateUtils.DD_MM_YYYY));
					reincidencia.setStatusAnalise(result[4] == null ? "" : result[4].toString());
					reincidencia.setDataInclusao (result[5] == null ? "" : DateUtils.format((Date)result[5], DateUtils.DD_MM_YYYY));
					listAutosReincidentes.add(reincidencia);
				}
			}
			
		} catch (NoResultException e) {
			return listAutosReincidentes;
		} catch (Throwable e) {
			httpGoApi.geraLog("FiscalizacaoDao || getListAutoInfracaoMesmaCapitulacaoPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		
		return listAutosReincidentes;
	} 
	
	@SuppressWarnings("unchecked")
	public MultaInfracaoDto getValoresAplicaveisInfracaoPor(String letraFundamento, long exercicio) {
		MultaInfracaoDto multa = new MultaInfracaoDto();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT vm.codigo cod_valor, ");
		sql.append("	vm.letra letra_fundamento, ");
		sql.append(" 	vm.valor vr_aplicavel, ");
		sql.append(" 	vm.reincidencia vr_reincidencia, ");
		sql.append(" 	vm.exercicio ano, ");
		sql.append(" 	vm.valor_minimo vr_minimo, ");
		sql.append(" 	vm.valor vr_maximo ");
		sql.append(" 	FROM fis_valores_infracao vm ");
		sql.append(" 	WHERE vm.exercicio = :exercicio ");
		sql.append(" 	AND   vm.letra = :letraFundamento ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("letraFundamento", letraFundamento);
			query.setParameter("exercicio", exercicio);
			query.setMaxResults(1);
	
			Iterator<Object> it = query.getResultList().iterator();
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					multa.setCodigo(result[0] == null ? null : ((BigDecimal)result[0]).longValue());
					multa.setLetraFundamento(result[1] == null ? "" : result[1].toString());
					
					BigDecimal valorAplicavel = (BigDecimal) result[2];
					multa.setValor(valorAplicavel == null ? null : valorAplicavel);
					
					BigDecimal valorReincidencia = (BigDecimal) result[3];
					multa.setValorReincidencia(valorReincidencia == null ? null : valorReincidencia);
					
					multa.setExercicio  (result[4] == null ? "" : ((BigDecimal)result[4]).toString()); 
					
					BigDecimal valorMinimo = (BigDecimal) result[5];
					multa.setValorMinimo(valorMinimo == null ? null : valorMinimo);
					
					BigDecimal valorMaximo = (BigDecimal) result[6];
					multa.setValorMaximo(valorMaximo == null ? null : valorMaximo); 
					
				}
			}
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable  e) {
			httpGoApi.geraLog("FiscalizacaoDao || getValoresAplicaveisInfracao", StringUtil.convertObjectToJson(letraFundamento), e);
		}
		
		return multa;
	}
	
	@SuppressWarnings("unchecked")
	public void getAtividadeRelatorioVisita(Long numeroProtocolo, AutoInfracaoDto infracao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT frativ.descricao tipo_atividade, ");
		sql.append("	  frar.descricao atividade 			");
		sql.append("	  FROM fis_autoinfracao fa, 		");
		sql.append("	       fis_rf_constatacao_fiscal frcf, 			");
		sql.append("		   fis_rf_atividade_realizada frar, 		");
		sql.append("		   fis_rf_tipo_ativ_realizada frativ 		");
		sql.append("	  WHERE fa.numero = :numeroProtocolo 	 		");
		sql.append("	  AND   fa.fk_rf_constatacao_fiscal = frcf.id 	");
		sql.append("	  AND   frcf.fk_atividade_realizada = frar.id 	");
		sql.append("	  AND   frar.fk_tipo_ativ_realizada = frativ.id	");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			
			Iterator<Object> it = query.getResultList().iterator();
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					infracao.setTipoAtividadeFiscalizada(result[0] == null ? "Não informado" : result[0].toString());
					infracao.setAtividadeFiscalizada(result[1] == null ? "Não informado" : result[1].toString());
				}
			}
			
		} catch (NoResultException e) {
			infracao.setTipoAtividadeFiscalizada("Não possui");
			infracao.setAtividadeFiscalizada("Não possui");
		} catch (Throwable e) {
			httpGoApi.geraLog("FiscalizacaoDao || getAtividadeRelatorioVisita", StringUtil.convertObjectToJson(numeroProtocolo), e);
			
		}
		
	}
	
}
