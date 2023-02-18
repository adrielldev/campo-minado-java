
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class Tabuleiro implements CampoObservador{
	
	private final int linhas;
	private final int colunas;
	private final int minas;
	
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();
	
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
		
	}
	
	public void paraCada(Consumer<Campo> funcao) {
		campos.forEach(funcao);
		
	}
	
	public void registrarObservadores(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
		}
	private void notificarObservadores(boolean res) {
		observadores.stream().forEach(ob -> ob.accept(new ResultadoEvento(res)));
		
	}

	private void sortearMinas() {
		

		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado(); 
		do {
			int aleatorio = (int) (Math.random()*campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count(); 
		} while(minasArmadas < minas);
		
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c->c.reiniciar());
		sortearMinas();
		
	}

	private void associarVizinhos() {
		
		for(Campo c1: campos) {
			for(Campo c2:campos) {
				c1.adicionarVizinho(c2);
			}
		}
		
	}

	private void gerarCampos() {
		for(int i =0; i< linhas;i++) {
			for(int j =0; j< colunas;j++) {
				Campo campo = new Campo(i,j);
				campo.registrarObservador(this);
				campos.add(campo);
			}
		}
		
		
		
	}
	
	public void abrir(int linha,int coluna) {
		try {
			campos.parallelStream().filter(c->c.getLinha()==linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c->c.abrir());
			
		} catch(Exception e) {
			
			campos.forEach(c -> c.setAberto(true));
			throw e;
		} 
		
	}
	
	private void mostrarMinas() {
		campos.stream().filter(c->c.isMinado()).
		filter(c->!c.isMarcado()).
		forEach(c -> c.setAberto(true));
	}
	
	
	public void alterarMarcacao(int linha,int coluna) {
		campos.parallelStream().filter(c->c.getLinha()==linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c->c.alternarMarcacao());
		
	}
	
	public void eventoOcorreu(Campo campo, CampoEvento ev) {
		
		if(ev == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		} else if (objetivoAlcancado()){
			System.out.println("ganhou");
			notificarObservadores(true);
		
		}
	
	}
	
}
