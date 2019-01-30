package units.teamTwo;

import units.BaseHero;
import units.Hero;

public class Witch implements Hero {
    private BaseHero hero;
    private int hp = 30;
    private int hit = 8;
    private int heal = 3;
    private int healHit = 6;
    private double critical = 0.3;
    private int radiusAttack = 4;
    private int moves = 7;
    private String nameHero = "Witch";

    public Witch() {
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
