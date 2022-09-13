package Pokemons;

import main.Pokemon;
import main.Ability;

public class Jigglypuff extends Pokemon {
    public Jigglypuff(String name, int hp, int normalAttack, int specialAttack, int defense, int specialDefense,
                      Ability ability1, Ability ability2) {
        super(name, hp, normalAttack, specialAttack, defense, specialDefense, ability1, ability2);
    }

    @Override
    public Pokemon copy() {
        return new Jigglypuff(this.getName(), this.getHp(), this.getNormalAttack(),
                this.getSpecialAttack(), this.getDefense(), this.getSpecialDefense(), this.getAbility1(), this.getAbility2());
    }
}
