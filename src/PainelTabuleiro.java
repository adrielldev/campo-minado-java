

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PainelTabuleiro extends JPanel{
	 
	public PainelTabuleiro(Tabuleiro tab) {
		setLayout(new GridLayout(tab.getLinhas(),tab.getColunas()));
	
		tab.paraCada(c->add(new BotaoCampo(c)));
	
		tab.registrarObservadores(e -> {
			
			SwingUtilities.invokeLater(()->{
				if(e.isGanhou()) {
					
					JOptionPane.showMessageDialog(this, "Ganhou :)");
				}else {
					JOptionPane.showMessageDialog(this, "Perdeu :(");
				}
				tab.reiniciar();	
			});
			
			
		
		});
	}
}
