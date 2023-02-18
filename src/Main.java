

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	public Main() {
		
		Tabuleiro tab = new Tabuleiro(25,25,50);
		PainelTabuleiro painelTab = new PainelTabuleiro(tab);
		add(painelTab);
		setTitle("Campo Minado");
		setSize(480,438);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}
