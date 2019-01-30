package units;

public class BaseHero implements Hero {
    private int hp;
    private int hit;
    private int heal;
    private int healHit;
    private double critical;
    private double radiusAttack;
    private int moves;
    private String nameHero;

    public BaseHero(int hp, int hit, int heal, int healHit, double critical, double radiusAttack, int moves, String nameHero) {
        this.hp = hp;
        this.hit = hit;
        this.heal = heal;
        this.healHit = healHit;
        this.critical = critical;
        this.radiusAttack = radiusAttack;
        this.moves = moves;
        this.nameHero = nameHero;
    }

    public BaseHero transmog(BaseHero hero) {
        this.hp = hero.hp;
        this.hit = hero.hit;
        this.heal = hero.heal;
        this.healHit = hero.healHit;
        this.critical = hero.critical;
        this.radiusAttack = hero.radiusAttack;
        this.moves = hero.moves;
        this.nameHero = hero.nameHero;
        return null;
    }

    public BaseHero() {
        this.hp = 0;
        this.hit = 0;
        this.heal = 0;
        this.healHit = 0;
        this.critical = 0;
        this.radiusAttack = 0;
        this.moves = 0;
        this.nameHero = "";
    }

    public String getNameHero() {
        return nameHero;
    }

    public int getHeal() {
        return heal;
    }

    public int getHp() {
        return hp;
    }

    public int getMoves() {
        return this.moves;
    }

    public double getRadiusAttack() {
        return this.radiusAttack;
    }

    @Override
    public void heal(Hero friend) {
        if (this.heal > 0) {
            friend.healHit(this.healHit);
            this.heal--;
        } else {
            System.out.println("Аптечек больше нет.");
        }
    }

    @Override
    public void healHit(int value) {
        this.hp += value;
    }

    @Override
    public void degradeEnemy(Hero enemy) {
        enemy.damage(this.hit);
    }

    @Override
    public void damage(int value) {
        if (Math.random() <= this.critical) {
            this.hp -= value * 3;
        } else {
            this.hp -= value;
        }
    }

    @Override
    public boolean isAlive() {
        return this.hp > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseHero baseHero = (BaseHero) o;

        if (hp != baseHero.hp) return false;
        if (hit != baseHero.hit) return false;
        if (heal != baseHero.heal) return false;
        if (healHit != baseHero.healHit) return false;
        if (Double.compare(baseHero.critical, critical) != 0) return false;
        if (Double.compare(baseHero.radiusAttack, radiusAttack) != 0) return false;
        if (moves != baseHero.moves) return false;
        return nameHero != null ? nameHero.equals(baseHero.nameHero) : baseHero.nameHero == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = hp;
        result = 31 * result + hit;
        result = 31 * result + heal;
        result = 31 * result + healHit;
        temp = Double.doubleToLongBits(critical);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(radiusAttack);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + moves;
        result = 31 * result + (nameHero != null ? nameHero.hashCode() : 0);
        return result;
    }
}
