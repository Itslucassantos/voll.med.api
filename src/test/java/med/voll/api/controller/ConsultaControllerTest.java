package med.voll.api.controller;

import lombok.var;
import med.voll.api.domain.consulta.AgendaDeConsultasService;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.api.domain.consulta.DadosDetalhamentoConsultaDTO;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//por ser um controller usa a anotação @SpringBootTest
@SpringBootTest
//para conseguir injetar o MockMvc
@AutoConfigureMockMvc
//para injetar o JacksonTester
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsultaDTO> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsultaDTO> dadosDetalhamentoConsultaJson;

    // usando a anotação @MockBean  para o Spring fazer um Mock e nn injetar no Banco de Dados
    // de verdade, nn vai disparar nenhuma requisição pro banco, nn fará mais o select.
    @MockBean
    private AgendaDeConsultasService agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    //para ignorar a segurança para poder testar a requisição, como se já estivesse logado,
    // com essa anotação @WithMockUser o Spring simula um usuário logado, faz um Mock.
    //Testar essa anotação no java 17, no 8 nn está indo.
    //@WithMockUser
    void agendar_cenario1() throws Exception {

        //dispara a requisição pro controler para testar e guarda na variável response.
        // perfoma uma requisição via método post pro endereço /consultas onde tem os métodos.
        //Não leva nenhum corpo da requisição
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();
        //verifica se o status que está no response é 400, pq nesse cenário tem q ser erro 400.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informações estão validas")
    //Testar essa anotação no java 17, no 8 nn está indo.
    //@WithMockUser
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var dadosDetalhamento = new DadosDetalhamentoConsultaDTO(null, 2l, 5l, data);
        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoConsultaJson.write(
                                        new DadosAgendamentoConsultaDTO((2l, 5l, data, especialidade)
                                )).getJson()
                )
                .andReturn().getResponse);

        //para devolver código 200 é ok
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson.write(
                dadosDetalhamento
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}