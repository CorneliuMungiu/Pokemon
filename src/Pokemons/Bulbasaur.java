package Pokemons;

import main.Ability;
import main.Pokemon;

public class Bulbasaur extends Pokemon {
    public Bulbasaur(String name, int hp, int normalAttack, int specialAttack, int defense, int specialDefense,
                     Ability ability1, Ability ability2) {
        super(name, hp, normalAttack, specialAttack, defense, specialDefense, ability1, ability2);
    }

    @Override
    public Pokemon copy() {
        return new Bulbasaur(this.getName(), this.getHp(), this.getNormalAttack(),
                this.getSpecialAttack(), this.getDefense(), this.getSpecialDefense(), this.getAbility1(), this.getAbility2());
    }
}
