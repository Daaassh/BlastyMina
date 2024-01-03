package org.me.blastymina.utils.porcentage;

import java.util.Random;

public class PorcentageEnchantsManager {
    private final Double percentage;

    public PorcentageEnchantsManager(Double percentage) {
        this.percentage = percentage;
        setup();
    }

    public boolean setup() {
        return verificarPorcentagem(gerarValorAleatorio());
    }

    private boolean verificarPorcentagem(double valor) {
        return valor > percentage;
    }

    private double gerarValorAleatorio() {
        Random random = new Random();
        return random.nextDouble() * (100 - 0.00001) + 0.00001;
    }
}

