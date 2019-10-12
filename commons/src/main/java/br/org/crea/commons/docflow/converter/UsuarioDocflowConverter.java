package br.org.crea.commons.docflow.converter;

import java.util.List;

import br.org.crea.commons.docflow.dto.UsuarioDocflowDto;
import br.org.crea.commons.docflow.helper.ConstantDocflow;
import br.org.crea.commons.docflow.model.usuario.MetadadoUsuarioDocflow;

public class UsuarioDocflowConverter {
	
	public UsuarioDocflowDto toDto(List<MetadadoUsuarioDocflow> listDataUsuario){
		
		UsuarioDocflowDto dto = populaDadosUsuario(listDataUsuario);
		return dto;
	}
	
	public UsuarioDocflowDto populaDadosUsuario(List<MetadadoUsuarioDocflow> listDataUsuario) {
		UsuarioDocflowDto dto = new UsuarioDocflowDto ();
		
		for (MetadadoUsuarioDocflow metadadoUsuario : listDataUsuario) {
			
			switch (metadadoUsuario.getName()) {
			case ConstantDocflow.CODIGO_USUARIO:
				dto.setCodigoUsuario(metadadoUsuario.getValue());
				break;
			case ConstantDocflow.NOME_USUARIO:
				dto.setNome(metadadoUsuario.getValue());
				break;
			case ConstantDocflow.ASSINANTE:
				dto.setAssinante(metadadoUsuario.getValue());
				break;
			case ConstantDocflow.CODIGO_UND_PADRAO_USER:
				dto.setCodigoUnidadePadrao(metadadoUsuario.getValue());
				break;
			case ConstantDocflow.NOME_UND_PADRAO_USER:
				dto.setNomeUnidadePadrao(metadadoUsuario.getValue());
				break;
			case ConstantDocflow.CODIGO_UND_LOGADA_USER:
				dto.setCodigoUnidadeLogada(metadadoUsuario.getValue());
				break;
			case ConstantDocflow.NOME_UND_LOGADA_USER:		
				dto.setNomeUnidadeLogada(metadadoUsuario.getValue());
				break;
			default:
				break;
			}
		}
		return dto;
	}
}
