package med.voll.api.medico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {
	/*Interface JPARepository
Você já chegou a fazer algum repositório (ou DAO) genérico com os métodos buscar, 
salvar (ou atualizar) e remover? Pois é, essa interface é mais ou menos isso.
Ela tem todos os métodos que a gente precisa para fazer um CRUD (criar, ler, atualizar, deletar).*/

}
