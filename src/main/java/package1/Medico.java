package package1;

import java.util.List;
import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Medico {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String nome;
	@Column
	private String especialidade;
	@Column
	private String crm;
	
	public Medico() {}
	
	public Medico (String nome, String especialidade, String crm) {
		this.nome = nome;
		this.especialidade = especialidade;
		this.crm = crm;
	}
	
	@OneToMany(mappedBy = "medico")
	private List<Consulta> consultas;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	
	public List<Consulta> getConsultas() {
		return consultas;
		}
	
	public static void cadastrarMedico(EntityManager em, Scanner sc) {
	        System.out.print("Nome: ");
	        String nome = sc.nextLine();

	        System.out.print("Especialidade: ");
	        String especialidade = sc.nextLine();

	        System.out.print("CRM: ");
	        String crm = sc.nextLine();

	        Medico medico = new Medico(nome, especialidade, crm);

	        em.getTransaction().begin();
	        em.persist(medico);
	        em.getTransaction().commit();

	        System.out.println("Médico cadastrado com sucesso!");
	    }
	
	public static void atualizarMedico(EntityManager em, Scanner sc) {

		List<Medico> medicos = em.createQuery("FROM Medico", Medico.class).getResultList();
    	
    	System.out.println("\n===== MEDICOS DISPONÍVEIS =====");
	    for (Medico m : medicos) {
	            System.out.println("ID: " + m.getId() + " | Medico: " + m.getNome() + " | CRM: " + m.getCrm());
	    }
		
		System.out.print("\nDigite o ID do Médico que deseja atualizar: ");
	    Integer id = sc.nextInt();
	    sc.nextLine();

	    Medico medico = em.find(Medico.class, id);

	    if (medico == null) {
	        System.out.println("Médico não encontrado!");
	        return;
	    }

	    System.out.print("Novo CRM: ");
	    String novoCrm = sc.nextLine();

	    System.out.print("Nova Especialidade: ");
	    String novaEspecialidade = sc.nextLine();

	    em.getTransaction().begin();
	    medico.setCrm(novoCrm);
	    medico.setEspecialidade(novaEspecialidade);
	    em.merge(medico);
	    em.getTransaction().commit();

	    System.out.println("Médico atualizado com sucesso!");
	}
	
	public static void removerMedico(EntityManager em, Scanner sc) {

		List<Medico> medicos = em.createQuery("FROM Medico", Medico.class).getResultList();
    	
    	System.out.println("\n===== MEDICOS DISPONÍVEIS =====");
	    for (Medico m : medicos) {
	            System.out.println("ID: " + m.getId() + " | Medico: " + m.getNome() + " | CRM: " + m.getCrm());
	    }
		
		System.out.print("\nDigite o ID do Médico que deseja remover: ");
	    Integer id = sc.nextInt();
	    sc.nextLine();

	    Medico medico = em.find(Medico.class, id);

	    if (medico == null) {
	        System.out.println("Médico não encontrado!");
	        return;
	    }

	    Long qtdConsultas = em.createQuery("SELECT COUNT(c) FROM Consulta c WHERE c.medico.id = :id", Long.class)
	    		.setParameter("id", id)
                .getSingleResult();

        if (qtdConsultas > 0) {
            System.out.println("Não é possível remover o médico, pois ele está vinculado a uma ou mais consultas.");
            return;
        }

        em.getTransaction().begin();
        em.remove(medico);
        em.getTransaction().commit();

        System.out.println("Médico removido com sucesso!");
	}

	
}
