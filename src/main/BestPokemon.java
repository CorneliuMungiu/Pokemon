package main;

public class BestPokemon {
    private String name;
    private int score;
    int index;

    public BestPokemon() {
        this.name = "";
        this.score = 0;
        this.index = -1;
    }

    /**
     * Scorul se calculeaza adunand toate skill-urile pokemonului (fara abilitati) dupa lupta , iar pentru ca pokemonul
     * care a pierdut lupta scorul sa nu fie prea mic , hp se va reseta la 0.
     * @param pokemon Pokemonul caruia i se calculeaza scorul.
     * @param index Indexul pokemonului in lista de pokemoni al antrenorului.
     */
    public void calculateScore(Pokemon pokemon,int index){
        //Scorul care va fi returnat
        int score = 0;
        //Daca hp pokemonului care a pierdut este negativ vom considera +0 la scor pentru categoria hp
        if(pokemon.getHp() < 0)
            pokemon.setHp(0);
        //Calculeaza scorul pokemonului
        score += pokemon.getHp() + pokemon.getNormalAttack() + pokemon.getSpecialAttack() + pokemon.getDefense() + pokemon.getSpecialDefense();
        //Daca pokemonul are un scor mai mare decat scorul pokemonului precedent(daca exista) acesta va deveni cel mai bun pokemon
        if(score > this.score){
            this.setName(pokemon.getName());
            this.setScore(score);
            this.index = index;
        }
        //In caz ca scorul este egal , cel mai bun pokemon va deveni cel mai mic lexicografic
        if(score == this.score){
            int aux = this.getName().compareTo(pokemon.getName());
            if (aux > 0){
                this.setName(pokemon.getName());
                this.setScore(score);
                this.index = index;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
