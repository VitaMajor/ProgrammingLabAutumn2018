package units.teamOne;

import units.BaseHero;
import units.Hero;

public class Knight implements Hero {
    private BaseHero hero;
    private int hp = 35;
    private int hit = 10;
    private int heal = 2;
    private int healHit = 10;
    private double critical = 0.6;
    private double radiusAttack = 1.5;
    private int moves = 5;
    private String nameHero = "Knight";

    public Knight() {
        this.hero = new BaseHero(hp, hit, heal, healHit, critical, radiusAttack, moves, nameHero);
    }

    public BaseHero getHero() {
        return hero;
    }

    @Override
    public void heal(Hero friend) {
        this.hero.heal(friend);
    }

    @Override
    public void healHit(int value) {
        this.hero.healHit(value);
    }

    @Override
    public void degradeEnemy(Hero enemy) {
        this.hero.degradeEnemy(enemy);
    }

    @Override
    public void damage(int value) {
        this.hero.damage(value);
    }

    @Override
    public boolean isAlive() {
        return this.hero.isAlive();
    }
}
