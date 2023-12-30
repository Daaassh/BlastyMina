package org.me.blastymina.utils.porcentage;

import java.util.Random;

public class PorcentageEnchantsManager {
    private double percentage;

    public PorcentageEnchantsManager(double percentage) {
        this.percentage = percentage;
    }


    public boolean setup() {
        Random random = new Random();
        double chance = random.nextDouble() * 10000.0;

        return chance <= percentage;
    }
}
