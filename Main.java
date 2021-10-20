class Filosofo extends Thread {
	private Object hashiEsquerdo;
	private Object hashiDireito;

	public Filosofo(Object hashiEsquerdo, Object hashiDireito) {
		this.hashiEsquerdo = hashiEsquerdo;
		this.hashiDireito = hashiDireito;
	}

	private void doAction(String action) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + action);
		Thread.sleep(((int) (Math.random() * 100)));
    }

	@Override
	public void run() {
		try {
			while (true) {
				// pensando
				doAction(System.nanoTime() + ": Pensando");
				synchronized (hashiEsquerdo) {
					doAction(System.nanoTime() + ": Pegou hashi esquerdo");
					synchronized (hashiDireito) {
						// Comendo
						doAction(System.nanoTime() + ": Pegou hashi direito - comeu");
						doAction(System.nanoTime() + ": Soltou o hashi direito");
					}
						// Pensando
						doAction( System.nanoTime() + ": Soltou o hashi direito. Voltou a pensar");
					}
			}
		} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
    }
}

public class JantarFilosofos {
	public static void main(String[] args) throws Exception {
		final Filosofo[] Filosofos = new Filosofo[5];
		Object[] hashis = new Object[Filosofos.length];
		for (int i = 0; i < hashis.length; i++) {
			hashis[i] = new Object();
		}
		
		
		for (int i = 0; i < Filosofos.length; i++) {
			Object hashiEsquerdo = hashis[i];
			Object hashiDireito = hashis[(i + 1) % hashis.length];
			if (i == Filosofos.length - 1) {
		
				Filosofos[i] = new Filosofo(hashiDireito, hashiEsquerdo);
				} else {
						Filosofos[i] = new Filosofo(hashiEsquerdo, hashiDireito);
					}
					Thread t = new Thread(Filosofos[i], "Filosofo " + (i + 1));
					t.start();
        }
    }
}