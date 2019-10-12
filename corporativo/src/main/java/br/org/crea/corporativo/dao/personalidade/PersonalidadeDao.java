package br.org.crea.corporativo.dao.personalidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.models.corporativo.personalidade.dto.PersonalidadeDto;
import br.org.crea.commons.models.corporativo.personalidade.entity.AjudaCusto;
import br.org.crea.commons.models.corporativo.personalidade.entity.Personalidade;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.HttpClientGoApi;


@Stateless
public class PersonalidadeDao extends GenericDao<Personalidade, Serializable> {
	
	@Inject
	HttpClientGoApi httpGoApi;
	@Inject
	PessoaDao pessoaDao;
	
	public PersonalidadeDao() {
		super(Personalidade.class);

	}
	
	//Eliminar o metodo apos testes finais ou mantelo por questoes de performance
	//No momento estamos buscando as informações via getALL
	@SuppressWarnings("unchecked")
	public List<PersonalidadeDto> getByNomes() {
		
		StringBuilder 	  			sql 		= new StringBuilder();
		ArrayList<PersonalidadeDto> listDto 	= new ArrayList<PersonalidadeDto>();
//		sql.append( "SELECT DISTINCT CPF.NOME, PER.CODIGO, PER.REMOVIDO" );
//		sql.append(" FROM   CAD_PESSOAS_FISICAS CPF, PER_CONSELHEIRO_REGIONAL PCR, PER_PERSONALIDADES PER " );
//		sql.append(" WHERE  CPF.CODIGO = PCR.FK_CODIGO_PERSONALIDADE ");
//		sql.append(" AND    PER.CODIGO = PCR.FK_CODIGO_PERSONALIDADE ");
//		sql.append(" ORDER BY CPF.NOME ");
		
		sql.append( "SELECT DISTINCT CPF.NOME, PER.CODIGO, PER.REMOVIDO" );
		sql.append(" FROM   CAD_PESSOAS_FISICAS CPF, PER_PERSONALIDADES PER " );
		sql.append(" WHERE  CPF.CODIGO = PER.CODIGO ");
		sql.append(" ORDER BY CPF.NOME ");

		try {
		
			Query query = em.createNativeQuery(sql.toString());
		    List<Object[]> rows = query.getResultList();
		    for(Object[] row : rows){
		    	
		    	 PersonalidadeDto            dto     	= new PersonalidadeDto();
		    
		    	 dto.setNome(String.valueOf(row[0]));
		    	 dto.setId(Long.parseLong(String.valueOf(row[1])));
		    	 if(Long.parseLong(String.valueOf(row[2]))>0) {
			    		dto.setRemovido(true); 
			    	 }
		    	 
//		    	 dto.setTratamento(String.valueOf(row[2]));
//		    	 dto.setTratamentoTexto(String.valueOf(row[3]));
//		    	 dto.setVocativo(String.valueOf(row[4]));
//		    	 dto.setNomeConjuge(String.valueOf(row[5]));
//		    	 dto.setDataNascimentoConjuge(String.valueOf(row[6]));
//		    	 dto.setFilhos(Long.parseLong(String.valueOf(row[7])));
//		    	 
//		    	 dto.setNumeroAgencia(String.valueOf(row[8]));
//		    	 dto.setNumeroConta(String.valueOf(row[9]));
//		    	 dto.setKmDomicilioCrea(String.valueOf(row[10]));
//		    	 dto.setDataCadastro(String.valueOf(row[11]));
//		    	 
//		     	 
//		    	 if(Long.parseLong(String.valueOf(row[12]))>0) {
//		    		dto.setRemovido(true); 
//		    	 }
//		    	 
//		    
//		    	 dto.setNomeConjuge(String.valueOf(row[14]));
//		    	 dto.setNomeConjuge(String.valueOf(row[15]));
		   
				listDto.add(dto);

			}	
		
		} catch (NoResultException e) {
			return listDto;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeDao || getByNomes", "", e);
		}
		
		return listDto;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<PersonalidadeDto> getConselheirosByNomes() {
		
		StringBuilder 	  			sql 		= new StringBuilder();
		ArrayList<PersonalidadeDto> listDto 	= new ArrayList<PersonalidadeDto>();
		sql.append( "SELECT DISTINCT CPF.NOME, PER.CODIGO, PER.REMOVIDO" );
		sql.append(" FROM   CAD_PESSOAS_FISICAS CPF, PER_CONSELHEIRO_REGIONAL PCR, PER_PERSONALIDADES PER " );
		sql.append(" WHERE  CPF.CODIGO = PCR.FK_CODIGO_PERSONALIDADE ");
		sql.append(" AND    PER.CODIGO = PCR.FK_CODIGO_PERSONALIDADE ");
		sql.append(" AND    PCR.REMOVIDO = 0 AND PCR.FINALIZADO = 0 ");
		sql.append(" ORDER BY CPF.NOME ");
	
		try {
		
			Query query = em.createNativeQuery(sql.toString());
		    List<Object[]> rows = query.getResultList();
		    for(Object[] row : rows){
		    	
		    	 PersonalidadeDto            dto     	= new PersonalidadeDto();
		    
		    	 dto.setNome(String.valueOf(row[0]));
		    	 dto.setId(Long.parseLong(String.valueOf(row[1])));
		    	 if(Long.parseLong(String.valueOf(row[2]))>0) {
		    		dto.setRemovido(true); 
		    	 }
		    	 	
				listDto.add(dto);

			}	
		
		} catch (NoResultException e) {
			return listDto;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeDao || getConselheirosByNomes", "", e);
		}
		
		return listDto;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<PersonalidadeDto> getPresidentesByNomes() {
		
		StringBuilder 	  			sql 		= new StringBuilder();
		ArrayList<PersonalidadeDto> listDto 	= new ArrayList<PersonalidadeDto>();
		sql.append( "SELECT DISTINCT CPF.NOME, PP.FK_CODIGO_PERSONALIDADES, PP.REMOVIDO, PP.FINALIZADO" );
		sql.append(" FROM   CAD_PESSOAS_FISICAS CPF, PER_PRESIDENTES PP, PER_PERSONALIDADES PER  " );
		sql.append(" WHERE  CPF.CODIGO = PP.FK_CODIGO_PERSONALIDADES ");
		sql.append(" AND    PER.CODIGO = PP.FK_CODIGO_PERSONALIDADES ");
		sql.append(" AND    PP.REMOVIDO = 0 ");
		sql.append(" ORDER BY CPF.NOME ");

		try {
		
			Query query = em.createNativeQuery(sql.toString());
		    List<Object[]> rows = query.getResultList();
		    for(Object[] row : rows){
		    	
		    	 PersonalidadeDto            dto     	= new PersonalidadeDto();
		    
		    	 dto.setNome(String.valueOf(row[0]));
		    	 dto.setId(Long.parseLong(String.valueOf(row[1])));
		    	 if(Long.parseLong(String.valueOf(row[2]))>0) {
		    		dto.setRemovido(true); 
		    	 }
		    	 if(Long.parseLong(String.valueOf(row[3]))>0) {
			    		dto.setFinalizado(true); 
			    	 }

				listDto.add(dto);

			}	
		
		} catch (NoResultException e) {
			return listDto;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeDao || getPresidentesByNomes", "", e);
		}
		
		return listDto;
		
	}
	

}
