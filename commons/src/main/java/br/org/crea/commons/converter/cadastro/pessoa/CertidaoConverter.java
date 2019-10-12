package br.org.crea.commons.converter.cadastro.pessoa;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.Certidao;
import br.org.crea.commons.models.cadastro.dtos.CertidaoDto;
import br.org.crea.commons.util.DateUtils;

public class CertidaoConverter {
	
	
	public CertidaoDto toDto(Certidao model) {
		
		CertidaoDto dto = new CertidaoDto();
		
        dto.setAno(model.getAnoCertidao());
        dto.setNumero(model.getNumero());
        dto.setTipo(model.getTipo().getDescricao());
        dto.setDataCertidao(DateUtils.format(model.getDataEmissao(), DateUtils.DD_MM_YYYY));
        dto.setFormaEmissao(model.getMotivoRemissao());

		return dto;
		
    }
	
	public List<CertidaoDto> toListDto(List<Certidao> listModel) {
		
		List<CertidaoDto> listDto = new ArrayList<CertidaoDto>();
		
		listModel.forEach(certidao -> {
			listDto.add(toDto(certidao));
		});

		return listDto;

    }
	
}
