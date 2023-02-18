

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador,MouseListener{

	
	private Campo campo;
	private final Color BG_PADRAO = new Color(184,184,184);
	private final Color BG_MARCAR = new Color(8,179,247);
	private final Color BG_EXPLOSAO = new Color(189,66,68);
	private final Color TEXTO_VERDE = new Color(0,100,0);
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		
		setBackground(BG_PADRAO);
		setOpaque(true);
		
		setBorder(BorderFactory.createBevelBorder(0));
		addMouseListener(this);
		campo.registrarObservador(this);
		
	}
	
	public void eventoOcorreu(Campo campo, CampoEvento ev) {
		switch(ev) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstiloPadrao();
		
		}
			
	}
	
	private void aplicarEstiloPadrao() {
		setBorder(BorderFactory.createBevelBorder(0));
		setBackground(BG_PADRAO);
		setText("");
		
		
		
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLOSAO);
		setForeground(Color.white);
		
		setText("X");
		
	}
	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCAR);
		setForeground(Color.black);
		setText("M");
		
		
	}
	
	private void aplicarEstiloAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.gray));
		if(campo.isMinado()) {
			setBackground(BG_EXPLOSAO);
			return;
		}
		setBackground(BG_PADRAO);
		
		switch(campo.minasNaVizinhanca()) {
		case  1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.blue);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.red);
			break;
		default:
			setForeground(Color.PINK);
		break;
		}
		
		
		String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
		setText(valor);
		
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1 ) {
			campo.abrir();
		} else if(campo.isAberto()) {
			return;
		} else {
			campo.alternarMarcacao();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
