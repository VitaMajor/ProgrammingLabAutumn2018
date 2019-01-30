package units.teamTwo;

import units.BaseHero;
import units.Hero;

public class Sectoid implements Hero {

    private BaseHero hero;
    private int hp = 45;
    private int hit = 7;
    private int heal = 2;
    private int healHit = 7;
    private double critical = 0.2;
    private double radiusAttack = 1.5;
    private int moves = 6;
    private String nameHero = "Sectoid";

    public Sectoid() {
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
