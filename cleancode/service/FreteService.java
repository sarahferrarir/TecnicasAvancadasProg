package cleancode.service;

public class FreteService {

    public double calcularFrete(double total) {

        if (total < 100) return 25;
        if (total < 300) return 15;

        return 0;
    }

}