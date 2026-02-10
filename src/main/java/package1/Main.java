package package1;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

		public static void main(String[] args) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
			EntityManager em = emf.createEntityManager();
		    Scanner sc = new Scanner(System.in);
		    
		        int opcao = 0;
		        do {
		        	System.out.println("\n===== SISTEMA DE CLÍNICA MÉDICA =====");
		            System.out.println("1 - Cadastrar Paciente");
		            System.out.println("2 - Atualizar Paciente");
		            System.out.println("3 - Remover Paciente");
		            System.out.println("4 - Cadastrar Médico");
		            System.out.println("5 - Atualizar Médico");
		            System.out.println("6 - Remover Médico");
		            System.out.println("7 - Agendar Consulta");
		            System.out.println("8 - Listar Consultas");
		            System.out.println("9 - Remover Consulta");		            
		            System.out.println("0 - Sair");
		            System.out.print("Escolha uma opção: ");

		            opcao = sc.nextInt();
		            sc.nextLine();

		            switch (opcao) {

		                case 1:
		                    Paciente.cadastrarPaciente(em, sc);
		                    break;
		                    
		                case 2:
		                    Paciente.atualizarPaciente(em, sc);
		                    break;
		                
		                case 3:
		                	Paciente.removerPaciente(em, sc);
		                	break;
		                
		                case 4:
		                    Medico.cadastrarMedico(em, sc);
		                    break;
		                
		                case 5:
		                	Medico.atualizarMedico(em, sc);
		                	break;
		                
		                case 6:
		                	Medico.removerMedico(em, sc);
		                	break;

		                case 7:
		                    Consulta.agendarConsulta(em, sc);
		                    break;

		                case 8:
		                    Consulta.listarConsultas(em);
		                    break;

		                case 9:
		                    Consulta.removerConsulta(em, sc);
		                    break;             
		                
		                case 0:
		                    System.out.println("Obrigado por usar nosso sistema!");
		                    break;

		                default:
		                    System.out.println("Opção inválida!");
		            }

		        } while (opcao != 0);

		        em.close();
		        emf.close();
		        sc.close();
		    }
	}
