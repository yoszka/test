


public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello World");
		
		StatClass.KlasaWew a = (new StatClass()).new KlasaWew();	// Utworzenie instancji niestatycznej klasy wewn�trznej
		StatClass.KlasaWewSt b = new StatClass.KlasaWewSt();		// Utworzenie instancji    statycznej klasy wewn�trznej
	}

}
