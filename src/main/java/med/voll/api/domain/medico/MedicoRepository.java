package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

/*Interface JPARepository
  Você já chegou a fazer algum repositório (ou DAO) genérico com os métodos buscar,
  salvar (ou atualizar) e remover? Pois é, essa interface é mais ou menos isso.
  Ela tem todos os métodos que a gente precisa para fazer um CRUD (criar, ler, atualizar, deletar).*/
public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {
    Page<MedicoEntity> findAllByAtivoTrue(Pageable paginacao);

    //Pelo nome está em português o JPA não monta a Query para nós, esse foi o caso, então usamos a anotação a baixo,
    //que é para montar a Query, que foi passada nos parênteses.
    //:especialidade é pra dizer que é a msm Especialidade especialidade. como no :data que é do LocalDateTime data.
    // coloquei o nativeQuery = true, pq o JPQL do Spring não tem esse limit, estava dando erro, ai coloquei o
    // nativeQuery, dizendo que é uma Query nativa.
    @Query(value = " select m from Medico m\n" +
            "            where\n" +
            "            m.ativo = 1\n" +
            "            and\n" +
            "            m.especialidade = :especialidade\n" +
            "            and\n" +
            "            m.id not in(\n" +
            "                select c.medico.id from Consulta c\n" +
            "                where\n" +
            "                c.data = :data\n" +
            "        and\n" +
            "                c.motivoCancelamento is null\n" +
            "            )\n" +
            "            order by rand()\n" +
            "            limit 1",
            nativeQuery = true)
    MedicoEntity escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query(" select m.ativo from Medico m where m.id = :id")
    Boolean findAtivoById(Long id);
}
