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
public class Paciente {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	@Column
    private String nome;
	@Column
    private String cpf;
	@Column
    private String telefone;

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;

    public Paciente() {}

    public Paciente(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

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

    public String getCpf() {
    	return cpf;
    	}
    public void setCpf(String cpf) {
    	this.cpf = cpf;
    	}

    public String getTelefone() {
    	return telefone;
    	}
    public void setTelefone(String telefone) {
    	this.telefone = telefone;
    	}

    public List<Consulta> getConsultas() {
    	return consultas;
    	}
    
    public static void cadastrarPaciente(EntityManager em, Scanner sc) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        Paciente paciente = new Paciente(nome, cpf, telefone);

        em.getTransaction().begin();
        em.persist(paciente);
        em.getTransaction().commit();

        System.out.println("Paciente cadastrado com sucesso!");
    }
    
    public static void atualizarPaciente(EntityManager em, Scanner sc) {

    	List<Paciente> pacientes = em.createQuery("FROM Paciente", Paciente.class).getResultList();
    	
    	System.out.println("\n===== PACIENTES DISPONÍVEIS =====");
	    for (Paciente p : pacientes) {
	            System.out.println("ID: " + p.getId() + " | Paciente: " + p.getNome() + " | CPF: " + p.getCpf());
	    }
    	
    	System.out.print("\nDigite o ID do Paciente que deseja atualizar: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        Paciente paciente = em.find(Paciente.class, id);

        if (paciente == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        System.out.println("\n=== Atualização de Paciente ===");
        System.out.println("Deixe em branco para manter o valor atual.\n");

        System.out.println("Nome atual: " + paciente.getNome());
        System.out.print("Novo nome: ");
        String novoNome = sc.nextLine();

        System.out.println("CPF atual: " + paciente.getCpf());
        System.out.print("Novo CPF: ");
        String novoCpf = sc.nextLine();

        System.out.println("Telefone atual: " + paciente.getTelefone());
        System.out.print("Novo telefone: ");
        String novoTelefone = sc.nextLine();
        

        paciente.setNome(novoNome);
        paciente.setCpf(novoCpf);
        paciente.setTelefone(novoTelefone);


        em.getTransaction().begin();
        em.merge(paciente);
        em.getTransaction().commit();

        System.out.println("Paciente atualizado com sucesso!");
    }
    
    public static void removerPaciente(EntityManager em, Scanner sc) {

    	List<Paciente> pacientes = em.createQuery("FROM Paciente", Paciente.class).getResultList();
    	
    	System.out.println("\n===== PACIENTES DISPONÍVEIS =====");
	    for (Paciente p : pacientes) {
	            System.out.println("ID: " + p.getId() + " | Paciente: " + p.getNome() + " | CPF: " + p.getCpf());
	    }
    	
    	System.out.print("\nDigite o ID do Paciente que deseja remover: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        Paciente paciente = em.find(Paciente.class, id);

        if (paciente == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        Long qtdConsultas = em.createQuery("SELECT COUNT(c) FROM Consulta c WHERE c.paciente.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();

        if (qtdConsultas > 0) {
            System.out.println("Não é possível remover o paciente, pois ele está vinculado a uma ou mais consultas.");
            return;
        }

        em.getTransaction().begin();
        em.remove(paciente);
        em.getTransaction().commit();

        System.out.println("Paciente removido com sucesso!");
    }
}
