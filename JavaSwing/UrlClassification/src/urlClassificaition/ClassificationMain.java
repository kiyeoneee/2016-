package urlClassificaition;

import java.awt.EventQueue;

public class ClassificationMain {

	public static void main(String[] args) {
		ClassificationMain CM = new ClassificationMain();
	}

	public ClassificationMain() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassificationFrame window = new ClassificationFrame();
					window.frame.setVisible(true);;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
