package package1;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Consulta {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private LocalDate data;
	@Column
	private String observacoes;
	
	@ManyToOne
    @JoinColumn(name = "medico_id")
	private Medico medico;
	
	@ManyToOne
    @JoinColumn(name = "paciente_id")
	private Paciente paciente;
	
	public Consulta() {}
	
	public Consulta (LocalDate data, String observacoes, Medico medico, Paciente paciente) {
		this.data = data;
		this.observacoes = observacoes;
		this.medico = medico;
		this.paciente = paciente;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}

	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	 public static void agendarConsulta(EntityManager em, Scanner sc) {

		 	List<Paciente> pacientes = em.createQuery("FROM Paciente", Paciente.class).getResultList();
	    	
	    	System.out.println("\n===== PACIENTES DISPONÍVEIS =====");
		    for (Paciente p : pacientes) {
		            System.out.println("[ ID: " + p.getId() + " | Paciente: " + p.getNome() + " | CPF: " + p.getCpf() + " ]");
		    }
		    
		    List<Medico> medicos = em.createQuery("FROM Medico", Medico.class).getResultList();
	    	
	    	System.out.println("\n===== MEDICOS DISPONÍVEIS =====");
		    for (Medico m : medicos) {
		            System.out.println("[ ID: " + m.getId() + " | Medico: " + m.getNome() + " | CRM: " + m.getCrm() + " ]");
		    }
		    
	        System.out.print("\nDigite o ID do Paciente que irá realizar a consulta: ");
	        Integer idPaciente = sc.nextInt();

	        System.out.print("Digite o ID do Médico que irá realizar a consulta: ");
	        Integer idMedico = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Observações: ");
	        String obs = sc.nextLine();

	        Paciente paciente = em.find(Paciente.class, idPaciente);
	        Medico medico = em.find(Medico.class, idMedico);

	        if (paciente == null || medico == null) {
	            System.out.println("Paciente ou Médico não encontrado!");
	            return;
	        }

	        Consulta consulta = new Consulta(LocalDate.now(),obs, medico, paciente);
	        em.getTransaction().begin();
	        em.persist(consulta);
	        em.getTransaction().commit();

	        System.out.println("Consulta agendada com sucesso!");
	    }
	 
	 public static void listarConsultas(EntityManager em) {

	        List<Consulta> consultas = em.createQuery("FROM Consulta", Consulta.class).getResultList();

	        System.out.println("\n===== LISTA DE CONSULTAS =====");

	        for (Consulta c : consultas) {
	            System.out.println("ID: " + c.getId());
	            System.out.println("Paciente: " + c.getPaciente().getNome());
	            System.out.println("Médico: " + c.getMedico().getNome());
	            System.out.println("Data: " + c.getData());
	            System.out.println("Observações: " + c.getObservacoes());
	            System.out.println("--------------------------------");
	        }
	    }
	 
	 public static void removerConsulta(EntityManager em, Scanner sc) {

		 List<Consulta> consultas = em.createQuery("FROM Consulta", Consulta.class).getResultList();

	        System.out.println("\n===== LISTA DE CONSULTAS =====");

	        for (Consulta c : consultas) {
	            System.out.println("ID: " + c.getId());
	            System.out.println("Paciente: " + c.getPaciente().getNome());
	            System.out.println("Médico: " + c.getMedico().getNome());
	            System.out.println("Data: " + c.getData());
	            System.out.println("Observações: " + c.getObservacoes());
	            System.out.println("--------------------------------");
	        }
	    
	        System.out.print("\nDigite o ID da Consulta que deseja remover: ");
	        Integer id = sc.nextInt();
	        sc.nextLine();

	        Consulta consulta = em.find(Consulta.class, id);

	        if (consulta == null) {
	            System.out.println("Consulta não encontrada!");
	            return;
	        }

	        em.getTransaction().begin();
	        em.remove(consulta);
	        em.getTransaction().commit();

	        System.out.println("Consulta removida com sucesso!");
	    }
	
	
	
}
