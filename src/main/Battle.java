package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Battle implements Runnable{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private String nameCoach1;
    private String nameCoach2;
    private FileWriter myWriter;

    public Battle(Pokemon pokemon1, Pokemon pokemon2,String nameCoach1, String nameCoach2,FileWriter myWriter) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.nameCoach1 = nameCoach1;
        this.nameCoach2 = nameCoach2;
        this.myWriter = myWriter;
    }

    /**
     * Lupta dintre 2 pokemoni. Aceasta se termina atunci cand unul (sau ambii) dintre pokemoni nu mai are hp si se
     * considera castigor pokemonul cu hp pozitiv (sau draw daca dupa ultima lovitura amandoi au mai putin decat 1 hp).
     * La fiecare pas se alege aleator tipul de atack disponibil (nu se pot alege albilitatile care au cooldown) al
     * fiecarui pokemon, desigur pokemonii nu stiu ce tip de atac va alege adversarul. Daca pokemonul a primit stun
     * din pasul trecut acesta nu va mai alege abilitate pentru a nu primi cooldown nefolosind abilitatea.
     * La inceputul fiecarei runde se afiseaza (la consola si in fisierul de output) informatia generala despre pokemon
     * + itemele le care le are iar la fiecare moment din lupta se afiseaza informatia despre starea pokemonului
     * (hp, daca este in stun sau daca are dodge) si informatia despre cooldown-ul abilitatilor iar la final se afiseaza
     * numele antrenorului castigator (antrenorii pot avea pokemonii identici).
     */
    @Override
    public void run() {
        int roundCounter = 1;
        int initialPokemon1HP;
        int initialPokemon2HP;

        //Atat timp cat ambii pokemoni sunt vii
        while(pokemon1.getHp() > 0 && pokemon2.getHp() > 0){
            //Salveaza datele daca pokemonii sunt in stun in acesta runda
            boolean pokemon1Stun = pokemon1.isStunned();
            boolean pokemon2Stun = pokemon2.isStunned();
            //Salveaza hp initial pentru a putea calcula cat hp a pierdut un pokemon dupa lovitura
            initialPokemon1HP = pokemon1.getHp();
            initialPokemon2HP = pokemon2.getHp();

            System.out.println("----------------------Step" + roundCounter + "----------------------");
            try {
                myWriter.write("----------------------Step" + roundCounter + "----------------------\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Creaza 2 thread-uri
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Pokemon.AttackTypes pokemon1AttackType;
            Pokemon.AttackTypes pokemon2AttackType;
            /* In cazul in care un pokemon are stun pe aceasta runda, nu ar avea sens sa foloseasca o abilitate si sa aiba
            cooldown pe ea, de aceea mereu cand va avea stun acesta va ataca cu normalAttack(sau specialAttack) */
            if(pokemon1.isStunned()){
                pokemon1AttackType = Pokemon.AttackTypes.normalAttack;
            }else{
                pokemon1AttackType = pokemon1.getRandomAttackType();
            }
            if(pokemon2.isStunned()){
                pokemon2AttackType = Pokemon.AttackTypes.normalAttack;
            }else{
                pokemon2AttackType = pokemon2.getRandomAttackType();
            }


            /*Intrucat attackType poate fi doar normalAttack(care reprezinta atat normal attack cat si special attack)
            ability1 sau ability2, daca pokemonul nu are normal attack, atunci vom afisa ca acesta ataca cu specialAttack */
            if(pokemon1.getNormalAttack() == 0 && pokemon1AttackType.name().equals("normalAttack")){
                System.out.print(ANSI_GREEN + pokemon1.getName() + " specialAttack / " + ANSI_RESET);
                try {
                    myWriter.write( pokemon1.getName() + " specialAttack / ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.print(ANSI_GREEN + pokemon1.getName() + " " + pokemon1AttackType + " / " + ANSI_RESET);
                try {
                    myWriter.write(pokemon1.getName() + " " + pokemon1AttackType + " / ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /*Intrucat attackType poate fi doar normalAttack(care reprezinta atat normal attack cat si special attack)
            ability1 sau ability2, daca pokemonul nu are normal attack, atunci vom afisa ca acesta ataca cu specialAttack */
            if(pokemon2.getNormalAttack() == 0 && pokemon2AttackType.name().equals("normalAttack")){
                System.out.print(ANSI_GREEN + pokemon2.getName() + " specialAttack / \n" + ANSI_RESET);
                try {
                    myWriter.write( pokemon2.getName() + " specialAttack / \n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.print(ANSI_GREEN + pokemon2.getName() + " " + pokemon2AttackType + " / \n" + ANSI_RESET);
                try {
                    myWriter.write(pokemon2.getName() + " " + pokemon2AttackType + " / \n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Ruleaza pe unul din thread-uri metoda pokemonAttack al pokemonului 1
            executorService.execute(() -> {
                try {
                    pokemon1.pokemonAttack(pokemon2,pokemon1AttackType,pokemon2AttackType,pokemon2Stun,myWriter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            //Ruleaza pe celalalt thread metoda pokemonAttack al pokemonului 2
            executorService.execute(() -> {
                try {
                    pokemon2.pokemonAttack(pokemon1,pokemon2AttackType,pokemon1AttackType,pokemon1Stun,myWriter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            executorService.shutdown();
            try{
                executorService.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Incrementeaza numarul rundelor
            roundCounter++;
            //Afiseaza informatiile despre cat hp au pierdut fiecare pokemon si ce abilitate au in cooldown
            try {
                pokemon1.pokemonInformation(initialPokemon1HP,myWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                pokemon2.pokemonInformation(initialPokemon2HP,myWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Daca la ultimul pas ambii pokemoni au murit, runda se termina cu draw
        if(!pokemon1.isAlive() && !pokemon2.isAlive()){
            System.out.println(ANSI_BLUE + "Draw" + ANSI_RESET);
            try {
                myWriter.write("Draw\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        /*Daca pokemonul antrenorului 1 a castgat runda se afiseaza numele antrenorului 1, in caz contrar
        se afiseaza numele antrenorului 2 */
        if(pokemon1.isAlive() && !pokemon2.isAlive()){
            System.out.println(ANSI_BLUE + this.getNameCoach1() + " Wins" + ANSI_RESET);
            try {
                myWriter.write(this.getNameCoach1() + " Wins\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(ANSI_BLUE + this.getNameCoach2() + " Wins" + ANSI_RESET);
            try {
                myWriter.write(this.getNameCoach2() + " Wins\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Pokemon getPokemon1() {
        return pokemon1;
    }

    public void setPokemon1(Pokemon pokemon1) {
        this.pokemon1 = pokemon1;
    }

    public Pokemon getPokemon2() {
        return pokemon2;
    }

    public void setPokemon2(Pokemon pokemon2) {
        this.pokemon2 = pokemon2;
    }

    public String getNameCoach1() {
        return nameCoach1;
    }

    public void setNameCoach1(String nameCoach1) {
        this.nameCoach1 = nameCoach1;
    }

    public String getNameCoach2() {
        return nameCoach2;
    }

    public void setNameCoach2(String nameCoach2) {
        this.nameCoach2 = nameCoach2;
    }
}
