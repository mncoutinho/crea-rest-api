package br.org.crea.commons.service.commons;

import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import br.org.crea.commons.converter.commons.ArquivoConverter;
import br.org.crea.commons.dao.commons.ArquivoDao;
import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.commons.dtos.ArquivoDto;
import br.org.crea.commons.models.commons.dtos.ArquivoFormUploadDto;
import br.org.crea.commons.util.FileUtils;
import br.org.crea.commons.util.StorageUtil;
import br.org.crea.commons.util.StringUtil;

public class ArquivoService {
	

	@Inject private ArquivoDao dao;
	
	@Inject private ArquivoConverter converter;
	
	
	public ArquivoDto upload(ArquivoFormUploadDto formArquivo, Long idPessoa) {
		
		Arquivo arquivo = StorageUtil.populaArquivo(formArquivo.getModulo(), formArquivo.getFileName(), "." + FileUtils.obterExtensaoDoArquivo(formArquivo.getFileName()), formArquivo.isPrivado(), idPessoa, formArquivo.getDescricao());
		String pathStorage = formArquivo.getModulo() + "/" + StringUtil.pathAnoMesDia();
		FileUtils.criarDiretorio(StorageUtil.getRaizStorage() + pathStorage);
		
		try {
			System.out.println(StorageUtil.getCaminhoCompleto());
			IOUtils.copyLarge(formArquivo.getFile(), new FileOutputStream(StorageUtil.getCaminhoCompleto())); 
			
			return converter.toDto(dao.create(arquivo));
			
		} catch (Exception e) {
			FileUtils.delete(StorageUtil.getCaminhoCompleto());    
		}
		return null;
	}
	

	public File download(Long id) {
		Arquivo arquivo = dao.getBy(id);
		
		StorageUtil.setPrivado(arquivo.isPrivado());
		String path = StorageUtil.getRaizStorage() + arquivo.getCaminhoStorage();

		File file = new File(path);
		return file;
	}
	
	public File download(String nome) {
		Arquivo arquivo = dao.getArquivoBy(nome);
				
		StorageUtil.setPrivado(arquivo.isPrivado());
		String path = StorageUtil.getRaizStorage() + arquivo.getCaminhoStorage();

		File file = new File(path);
		return file;
	}
	
	public byte[] getBytesArquivo(Long id) {
		Arquivo arquivo = dao.getBy(id);
		
		StorageUtil.setPrivado(arquivo.isPrivado());
		String path = StorageUtil.getRaizStorage() + arquivo.getCaminhoStorage();
		return FileUtils.converteFileParaBytes(new File(path));
	}

	public void delete(Long id) {
		Arquivo arquivo = dao.getBy(id);
		
		StorageUtil.setPrivado(arquivo.isPrivado());
		FileUtils.delete(StorageUtil.getRaizStorage() + arquivo.getCaminhoStorage());
		dao.deleta(id);
	}
	
	public void deletaSomenteArquivo(Long id) {
		Arquivo arquivo = dao.getBy(id);
		StorageUtil.setPrivado(arquivo.isPrivado());
		FileUtils.delete(StorageUtil.getRaizStorage() + arquivo.getCaminhoStorage());
	}

}
