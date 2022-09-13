package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public abstract class Pokemon {
    private String name;
    private int hp;
    private int normalAttack;
    private int specialAttack;
    private int defense;
    private int specialDefense;
    private Ability ability1;
    private int ability1CD;
    private Ability ability2;
    private int abitily2CD;
    ArrayList<String[]> availableItems;
    private boolean  isStunned;
    private Item[] usedItems;
    private boolean Alive;

    //Constructor
    public Pokemon(String name, int hp, int normalAttack, int specialAttack, int defense, int specialDefense,
                   Ability ability1, Ability ability2) {
        this.name = name;
        this.hp = hp;
        this.normalAttack = normalAttack;
        this.specialAttack = specialAttack;
        this.defense = defense;
        this.specialDefense = specialDefense;
        this.ability1 = ability1;
        this.ability1CD = 0;
        this.ability2 = ability2;
        this.abitily2CD = 0;
        this.availableItems = new ArrayList<>();
        this.isStunned = false;
        this.usedItems = null;
        this.Alive = true;
    }

    /**
     * Incrementeaza toate skill-urile (fara abilitati) a pokemonului cu 1 ( +1 ).
     */
    public void increseStatsPokemonBy1(){
        this.setHp(this.getHp() + 1);
        if(this.getNormalAttack() != 0)
            this.setNormalAttack(this.getNormalAttack() + 1);
        if(this.getSpecialAttack() != 0)
            this.setSpecialAttack(this.getSpecialAttack() + 1);
        this.setDefense(this.getDefense() + 1);
        this.setSpecialDefense(this.getSpecialDefense() + 1);
    }

    /**
     * Incrementeaza skill-urile pokemonului cu itemele pe care le are.
     */
    public void increseStatsPokemonByItem(){
        for(int i = 0; i < usedItems.length; i++){
            this.hp += this.usedItems[i].getHp();
            if(this.normalAttack != 0)
                this.normalAttack += this.usedItems[i].getAttack();
            if(this.specialAttack != 0)
                this.specialAttack += this.usedItems[i].getSpecialAttack();
            this.defense += this.usedItems[i].getDefense();
            this.specialDefense += this.usedItems[i].getSpecialDefense();
        }
    }

    /**
     * @param pokemon2 Pokemonul adversar.
     * @param coach1 Antrenorul pokemonului 1.
     * @param coach2 Antrenorul pokemonului 2.
     * @return Returneaza numele antrenorului cu pokemonul castigator sau "" in caz de draw.
     */
    public String getTheWinner(Pokemon pokemon2,String coach1, String coach2){
        if(this.getHp() <= 0 && pokemon2.getHp() <= 0)
            return "";
        if(this.getHp() > 0)
            return coach1;
        else
            return coach2;
    }

    /**
     * Tipurile de atac a pokemonilor. Intrucat unii pokemoni au doar normalAttack iar altii doar specialAttack , vom
     * considera normalAttack atat ca normalAttack cat si ca specialAttack.
     */
    enum AttackTypes{
        normalAttack,ability1,ability2
    }

    /**
     * @return Returneaza un AttackType ales random care respecta conditia ca AttackType-ul ales nu are cooldown si ca
     * pokemonul nu are stun (daca pokemonul are stun se alege defaut normalAttack).
     */
    public AttackTypes getRandomAttackType(){
        AttackTypes[] values = AttackTypes.values();
        int lenght = values.length;
        while(true){
            int randIndex = new Random().nextInt(lenght);
            AttackTypes attackType = values[randIndex];
            switch (attackType){
                case normalAttack -> {
                    return attackType;
                }
                case ability1 -> {
                    if(this.ability1CD == 0 && !this.isStunned)
                        return attackType;
                }
                case ability2 -> {
                    if(this.abitily2CD == 0 && !this.isStunned())
                        return attackType;
                }
            }
        }
    }

    /**
     * Pokemonul this isi calculeaza schimbarile sale in urma atacului pokemonului adversar. De asemenea se iau in
     * consideratie si abilitatile pokemonului this intrucat acestea pot avea dodge. Se decrementeza cooldown-ul
     * fiecarei abilitati daca acesta este si se afiseaza la consola (in fiesierul de output) informatii despre
     * cooldown-ul ramas si despre efectul uneia din abilitati (dodge sau stun).
     * @param pokemon Pokemonul adversar pokemonului this.
     * @param thisPokemonAttackType Tipul de atac al pokemonului this.
     * @param pokemonAttackType1 Tipul de atac al pokemonului adversar/
     * @param isStuned Starea pokemonului adversar (stun sau nu).
     * @param myWriter fileWriter.
     * @throws IOException
     */
    public void pokemonAttack(Pokemon pokemon , AttackTypes thisPokemonAttackType, AttackTypes pokemonAttackType1,
                              boolean isStuned, FileWriter myWriter) throws IOException {
        //Daca a fost in stun de runda trecuta, scoate stun-ul pentru aceasta runda.
        if(this.isStunned())
            this.setStunned(false);

        //Decrementeaza cooldown-ul pentru abilitati
        if(this.ability1CD != 0)
            this.ability1CD--;
        if(this.abitily2CD != 0)
            this.abitily2CD--;

        //Daca pokemonul adversar este in stun, atunci inseamna ca acesta nu poate aplica vre-un tip de attack pe pokemonul this
        if(isStuned){
            System.out.println(pokemon.getName() + " is stunned this round");
            myWriter.write(pokemon.getName() + " is stunned this round\n");
            this.isStunned = false;
            return;
        }
        //Daca pokemonul this a folosit vre-o abilitate, verific daca aceasta nu are dodge pentru aceasta runda
        switch (thisPokemonAttackType){
            case ability1 -> {
                //Seteaza cooldown-ul pentru acesta abilitate
                this.setAbility1CD(this.ability1.getCooldown());
                if(this.getAbility1().isDodge()){
                    System.out.println(this.getName() + " dodge is active this round");
                    myWriter.write(this.getName() + " dodge is active this round\n");
                    return;
                }
            }
            case ability2 -> {
                //Seteaza cooldownl-ul pentru aceasta abilitate
                this.setAbitily2CD(this.ability2.getCooldown());
                if(this.getAbility2().isDodge()){
                    System.out.println(this.getName() + " dodge is active this round");
                    myWriter.write(this.getName() + " dodge is active this round\n");
                    return;
                }
            }
        }

        //Scade din hp pokemonului this in dependenta de tipul de atac aplicat pe el
        switch (pokemonAttackType1){
            case normalAttack -> {
                //Verifica daca pokemonul adversar are normalAttack sau specialAttack
                if(pokemon.getNormalAttack() == 0){
                    //Verifica daca atacul lui este mai mare decat defensul pokemonului this pentru acest tip de atac
                    int aux = pokemon.getSpecialAttack() - this.getSpecialDefense();
                    if(aux < 0)
                        aux = 0;
                    this.hp -= aux;
                }else{
                    //Verific daca atacul lui este mai mare decat defensul pokemonului this pentru acest tip de atac
                    int aux = pokemon.getNormalAttack() - this.getDefense();
                    if (aux < 0)
                        aux = 0;
                    this.hp -= aux;

                }
            }
            case ability1 -> {
                this.hp -= pokemon.getAbility1().getDamage();
                this.setStunned(pokemon.getAbility1().isStun());
            }
            case ability2 -> {
                this.hp -= pokemon.getAbility2().getDamage();
                this.setStunned(pokemon.getAbility2().isStun());
            }
        }
        //Daca nu mai are hp acest pokemon a pierdut lupta(sau va fi draw)
        if(this.getHp() <= 0)
            this.setAlive(false);


    }

    /**
     * Afiseaza la consola(fisier) cat hp a avut pokemonul / a pierdut pokemonul si cat timp mai are cooldown-ul
     * pentru fiecare abilitate.
     * @param initialHP Hp pokemonului inainte de lovitura.
     * @param myWriter fileWriter.
     * @throws IOException
     */
    public void pokemonInformation(int initialHP,FileWriter myWriter) throws IOException {
        System.out.print("\t" + this.getName() + " HP " + this.getHp() + " (" + initialHP + " - " + (initialHP-this.getHp()) + "), ");
        myWriter.write("\t" + this.getName() + " HP " + this.getHp() + " (" + initialHP + " - " + (initialHP-this.getHp()) + "), ");
        if(this.getAbility1CD() != 0){
            System.out.print("abilitate 1 cooldown " + this.getAbility1CD() + " ");
            myWriter.write("abilitate 1 cooldown " + this.getAbility1CD() + " ");
        }
        if(this.getAbitily2CD() != 0){
            System.out.print("abilitate 2 cooldown " + this.getAbitily2CD() + " ");
            myWriter.write("abilitate 2 cooldown " + this.getAbitily2CD() + " ");
        }
        System.out.println();
        myWriter.write("\n");
    }

    /**
     * Afiseaza toate itemele pokemonului si buff-ul oferit de iteme pentru fiecare skill.
     * @param myWriter fileWriter.
     * @throws IOException
     */
    public void pokemonItemsBuffInformation(FileWriter myWriter) throws IOException {
        int hp = 0;
        int attack = 0;
        int specialAttack = 0;
        int defense = 0;
        int specialDefence = 0;
        System.out.print(this.getName() +" has these items[");
        myWriter.write(this.getName() +" has these items[");
        //Parcurge fiecare item din lista de iteme
        for(int i = 0; i < this.usedItems.length; i++){
            //Afiseaza itemul
            System.out.print("(" + this.usedItems[i].getName() + ")");
            myWriter.write("(" + this.usedItems[i].getName() + ")");
            hp += this.usedItems[i].getHp();
            attack += this.usedItems[i].getAttack();
            specialAttack += this.usedItems[i].getSpecialAttack();
            defense += this.usedItems[i].getDefense();
            specialDefence += this.usedItems[i].getSpecialDefense();
            if(i != this.usedItems.length - 1){
                System.out.print(",");
                myWriter.write(",");
            }
        }
        System.out.println("]");
        myWriter.write("]\n");
        //Afiseaza ce buff-uri ii da pokemonului itemele pe care le are
        if(this.getNormalAttack() == 0){
            System.out.println("+" + hp + " hp; +" + specialAttack + " specialAttack; +" + defense +" defense; +" + specialDefence + " specialDefense");
            myWriter.write("+" + hp + " hp; +" + specialAttack + " specialAttack; +" + defense +" defense; +" + specialDefence + " specialDefense\n");
        }
        else{
            System.out.println("+" + hp + " hp; +" + attack + " normalAttack; +" + defense +" defense; +" + specialDefence + " specialDefense");
            myWriter.write("+" + hp + " hp; +" + attack + " normalAttack; +" + defense +" defense; +" + specialDefence + " specialDefense\n");
        }
    }

    public abstract Pokemon copy();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getNormalAttack() {
        return normalAttack;
    }

    public void setNormalAttack(int normalAttack) {
        this.normalAttack = normalAttack;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public Ability getAbility1() {
        return ability1;
    }

    public void setAbility1(Ability ability1) {
        this.ability1 = ability1;
    }

    public Ability getAbility2() {
        return ability2;
    }

    public void setAbility2(Ability ability2) {
        this.ability2 = ability2;
    }

    public ArrayList<String[]> getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(ArrayList<String[]> availableItems) {
        this.availableItems = availableItems;
    }

    public boolean isStunned() {
        return isStunned;
    }

    public void setStunned(boolean stunned) {
        isStunned = stunned;
    }

    public int getAbility1CD() {
        return ability1CD;
    }

    public void setAbility1CD(int ability1CD) {
        this.ability1CD = ability1CD;
    }

    public int getAbitily2CD() {
        return abitily2CD;
    }

    public void setAbitily2CD(int abitily2CD) {
        this.abitily2CD = abitily2CD;
    }

    public Item[] getUsedItems() {
        return usedItems;
    }

    public void setUsedItems(Item[] usedItems) {
        this.usedItems = usedItems;
    }

    public boolean isAlive() {
        return Alive;
    }

    public void setAlive(boolean alive) {
        Alive = alive;
    }
}
