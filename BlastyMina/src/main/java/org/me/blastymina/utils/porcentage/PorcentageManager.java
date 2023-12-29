package org.me.blastymina.utils.porcentage;


import java.util.Random;

import java.util.Random;

public class PorcentageManager {
    private double percentage;

    public PorcentageManager(double percentage) {
        if (percentage < 0.0 || percentage > 100.0) {
            throw new IllegalArgumentException("A porcentagem deve estar entre 0.0 e 100.0");
        }
        this.percentage = percentage;

    }

    public boolean setup() {
        Random random = new Random();
        double chance = random.nextDouble() * 100.0;

        return chance <= percentage;
    }
}


