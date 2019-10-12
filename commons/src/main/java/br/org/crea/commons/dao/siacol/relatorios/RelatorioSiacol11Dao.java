package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol11Dto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol11Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	
	public RelatorioSiacol11Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}

	public List<RelSiacol11Dto> vidaDoProtocolo(PesquisaRelatorioSiacolDto pesquisa) {

		List<RelSiacol11Dto> listaRelatorio = new ArrayList<RelSiacol11Dto>();
					
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.DT_CREATE, A.NUMERO, A.TEXTO_AUDITORIA   ");
		sql.append("   FROM CAD_AUDITORIA A                            ");
		sql.append("  WHERE A.NUMERO = :idProtocolo                    ");
		sql.append("    AND A.MODULO = :modulo                         ");
		if (pesquisa.ehSemDocumento()) {
			sql.append("    AND A.EVENTO NOT IN (7, 8, 9, 41)          ");
		}
		sql.append("  ORDER BY A.DT_CREATE                             ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProtocolo", pesquisa.getNumeroProtocolo());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();

					RelSiacol11Dto dto = new RelSiacol11Dto();
					dto.setData((Date) result[0]);
					dto.setDataFormatada(DateUtils.format(dto.getData(), DateUtils.DD_MM_YYYY_HH_MM_SS));
					dto.setNumeroProtocolo(result[1] == null ? "" : result[1].toString());
					dto.setTextoAuditoria(result[2] == null ? "" : result[2].toString());
					
					listaRelatorio.add(dto);
				}
			}
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol11Dao || vidaDoProtocolo", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return listaRelatorio;
	}
}
