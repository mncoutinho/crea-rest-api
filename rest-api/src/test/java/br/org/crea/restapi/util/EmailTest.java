package br.org.crea.restapi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.dtos.ArquivoFormUploadDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.util.EmailEvent;

public class EmailTest {
	
	private EmailEnvioDto email;
	
	private EmailEvent evento;
	
	
	@Before
	public void setup() throws FileNotFoundException{
		email = new EmailEnvioDto();
		email.setAssunto("TESTE E-MAIL CREA");
		email.setEmissor("ricardo.goncalves@crea-rj.org.br");
		List<DestinatarioEmailDto> destinatarios = new ArrayList<DestinatarioEmailDto>();
		DestinatarioEmailDto destino = new DestinatarioEmailDto();
		destino.setNome("Jeferson Moreira");
		destino.setEmail("rodrigo.fonseca@crea-rj.org.br");
		destinatarios.add(destino);
		
		

		
		email.setDestinatarios(destinatarios);
		email.setMensagem("<p align='Justify'>Um texto literário é uma construção textual de acordo com as normas da literatura, com objetivos e características próprias, como linguagem elaborada de forma a causar emoções no leitor.</p>" +
                          "<p align='Justify'>De acordo com a classificação de textos, existe a divisão entre duas categorias: textos literários e textos não literários.</p>" +
                          "<p align='Justify'>Alguns exemplos de textos literários são: peças teatrais, romances, crônicas, contos fábulas, poemas, etc. Uma das características distintivas dos textos literário é a sua função poética, onde é possível constatar ritmo e musicalidade, organização específica das palavras e um elevado nível de criatividade.</p>" +
                          "<p align='Justify'>Já era 2ª feira e todos haviam saído para o trabalho ou para a faculdade. Quase às 11 horas da manhã fui à cozinha pensando em tomar um bom brecfest mais só pude pensar num gole de café e em alguns cigarros e já estava saindo para ir ao antigo bairro onde morara. Já fazia algum tempo que não voltava lá e a saudade era tanta que saí apressado. Desci do ônibus e olhei o outro lado da avenida. Percebi que ali se encontrava um enorme muro e uma ponte. Para atravessar para o outro lado era preciso ultrapassar àquele muro bem parecido com o muro de Berlim e atravessar a ponte. Precisava atravessar mais não era tarefa fácil. Era como mudar de universo de realidade. Sempre achei que não sabemos ao certo aonde vamos. Na travessia comecei a imaginar que no caminho somos apenas passageiros. Somos guiados pelos sentimentos, pelo impulso e só percorremos o caminho, mas não sabemos aonde vai dar. Seguimos o destino até chegarmos ao objetivo que sempre esteve lá. Fui despertado pelo barulho ensurdecedor de uma buzina e percebi que havia chegado a Moçambique. No campo de refugiados da guerra. Enfermeiros e voluntários da ONU corriam de um lado para outras aonde crianças e homens lutavam contra a desnutrição e contra a morte e não tinham se quer esperança nem perspectivas para suas vidas excerto o apoio daquelas pessoas Tinha uma forma peculiar de ser que minha pobre realidade nunca pode imaginar. Eram de uma cor azulada e magros a ponto de parecer ficção. Do outro lado pessoas estendiam a mão pedindo uma esmola nas portas das igrejas onde católicas e protestantes iam dar seu testemunho de fé e não prestavam atenção àquelas pessoas que tinham as pernas enroladas com um pedaço de pano encobrindo as feridas. E que podiam estar em Recife ou na festa de Santa Rita de Cássia ou em Moçambique ou em qualquer lugar do mundo. Onde pessoas preocupadas com se mesmas, não prestam atenção aquele lugar que sempre esteve ali esperando por elas. Ate ouvir Lia gritar Charlot até que fim apareceu. Estava pensando que havia nos esquecidos. Vejo que você estar bem. Volitou parar sua esposa para sua família. É aí se esquece o passado. Disse que não. Que apesar de estar tranqüilo não tinha esquecido de nada. Fui à barraca de Aurindo, onde a cantoria dos passarinhos que e lê criava enchia o ambiente de músiocabilidade. Pedi um vinho e comecei a viajar no pensamento, pois pensar com música e um vinho é bem mais fácil. Lembrei das palavras de Lia e comecei a recordar o tempo que ali passei. Quando tinha pedido tudo mais conseguira a solidariedade daquele povo simples e humildes que mentas vezes não tinham nem para si e gostavam de dividir o que tinham com os outros. Lembrei que apesar das adversidades que ali passei uma coisa eu não perdi, mas ao contrario antes não tinha e hoje eras que era de mais valioso a uma pessoa. A LIBERDADE</p>"+
                          "</p></p><p align='Center'> Atenciosamente</p>");
		
		email.setAnexos(populaAnexos());		
		
		evento = new EmailEvent();
		
	}
	
	
	private List<ArquivoFormUploadDto> populaAnexos() throws FileNotFoundException {
		List<ArquivoFormUploadDto> anexos = new ArrayList<ArquivoFormUploadDto>();
		ArquivoFormUploadDto anexo1 = new ArquivoFormUploadDto();
		InputStream myInputStream = null;
		anexo1.setDescricao("Teste de arquivo");
		anexo1.setFileName("Teste.jpg");
		File initialFile = new File("/home/rodrigo/Documentos/brasao.png");
		myInputStream = new FileInputStream(initialFile);
		anexo1.setFile(myInputStream);
		anexo1.setPath(initialFile.getPath());
		
		
		anexos.add(anexo1);
		
		return anexos;
	}


	@Test
	public void deveTestarEmailSimplse() throws InterruptedException{
		evento.consumeEvent(email);
	}
	

}
