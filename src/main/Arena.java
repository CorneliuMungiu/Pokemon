package main;

import main.PokemonFactory.PokemonType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Arena {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public  Arena(){}

    /**
     * Tipurile de oponenti pe care le poate avea un antrenor.
     */
    enum Opponents{
        Neutral1, Neutral2, Opponent
    }

    /**
     * @return Returneaza un element random din enum Opponents.
     */
    public static Opponents generateRandomOpponent(){
        Opponents[] values = Opponents.values();
        return values[new Random().nextInt(values.length)];
    }

    /**
     * @param list Lista de string cu nume de iteme.
     * @return Lista de iteme in dependenta de array-ul de String list.
     */
    public Item[] getItems(String[] list){
        Item[] items = new Item[list.length];
        for(int i = 0; i < list.length; i++){
              items[i] = Item.createItem(list[i]);
        }
        return items;
    }

    /**
     * Se extrage random tipul de oponent (Neutral1, Neutral2, Opponent) , iar atat timp cat tipul de oponent nu a fost
     * extras "Opponent" atunci fiecare pokemoni "i" ai antrenorilor se lupta cu neutrei si in caz de castig se
     * incrementeaza toate skill-urile cu 1. Odata ce tipul de oponent a fost ales Opponent se incepe lupta dintre cei
     * 3 pokemoni ai antrenorilor, pokemonul "i" al antrenorului 1 se va lupta cu pokemonul "i" al antrenorului 2,
     * castigatorului i se vor incrementa skilurile si la finalul celor 3 lupte, va trebui fi ales cel mai bun pokemon
     * al fiecarui antrenor pentru a pleca in lupta finala. Nu conteaza cate castiguri a avut fiecare antrenor pana la
     * lupta finala, castiga jocul cel care castiga runda finala unde se lupta cei mai buni pokemoni al celor 2 antrenori.
     * @param coaches Lista de 2 antrenori.
     * @param myWriter fileWriter.
     * @throws IOException
     */

    public void arenaBattle(Coach[] coaches, FileWriter myWriter) throws IOException {
        //Creeaza un factory de pokemoni
        PokemonFactory pf = PokemonFactory.Instance();
        //Extrag lista de pokemoni(3) al fiecarui antrenor
        Pokemon[] pokemonsCoach1 = coaches[0].getPokemons();
        Pokemon[] pokemonsCoach2 = coaches[1].getPokemons();
        //Bestpokemon va pastra numele, scorul si indexul pokemonului cel mai puternic
        BestPokemon bestPokemon1 = new BestPokemon();
        BestPokemon bestPokemon2 = new BestPokemon();
        //opponents va fi tipul de oponent ales aleator de arena
        Opponents opponents;
        /* counter va avea valori cuprinse intre 0 si 3
        Daca arena va alege de mai multe ori oponent de tip neutrel atunci prima data antrenorii vor lupta cu pokenonii 1
        apoi cu pokemonii 2 si apoi cu pokemonii 3. Daca in continuare arena va alege neutrel , antrenorii vor incepe din
        nou sa lupe cu pokemonul 1,2,3 pana cand arena nu va alege ca oponent celalalt antrenor. */
        int counter = 0;
        do{
            if(counter == 3)
                counter = 0;

            //Genereaza aleator tipul de oponent pentru aceasta runda
            opponents = generateRandomOpponent();
            //Daca tipul de oponent este Neutrel1
            if(opponents == Opponents.Neutral1) {
                //Creaza un pokemon de tip Neutrel pentru batalia dintre pokemonul antrenorului 1 si neutrel, folosind Factory
                Pokemon neutral = pf.createPokemon(PokemonType.Neutrel1);
                //Creaza copia pokemonului counter din lista de pokemoni al antrenorului 1
                Pokemon pokemon1 = pokemonsCoach1[counter].copy();
                //Apeleaza batalia cu parametri pokemonii si numele antrenorilor pentru a putea afisa castigatorul
                Battle battle = new Battle(pokemon1,neutral, coaches[0].getName(), coaches[1].getName(),myWriter);
                battle.run();
                //Pune intr-un string numele castigatorului acestei lupte
                String winner = pokemon1.getTheWinner(neutral, coaches[0].getName(), coaches[1].getName());
                //Daca castigatorul este pokemonul antrenorului atunci acesta va primi buff
                if(pokemon1.getName().equals(winner))
                    pokemonsCoach1[counter].increseStatsPokemonBy1();

                //Creaza un pokemon de tip Neutrel pentru batalia dintre pokemonul antrenorului 2 si neutrel, folosind Factory
                neutral = pf.createPokemon(PokemonType.Neutrel1);
                //Creaza copia pokemonului counter din lista de pokemoni al antrenorului 2
                Pokemon pokemon2 = pokemonsCoach2[counter].copy();
                //Apeleaza batalia cu parametri pokemonii si numele antrenorilor pentru a putea afisa castigatorul
                battle = new Battle(pokemon2,neutral, coaches[1].getName(), coaches[0].getName(),myWriter);
                battle.run();
                //Pune intr-un string numele castigatorului acestei lupte
                winner = pokemon2.getTheWinner(neutral, coaches[1].getName(), coaches[0].getName());
                //Daca castigatorul este pokemonul antrenorului atunci acesta va primi buff
                if(pokemon2.getName().equals(winner))
                    pokemonsCoach1[counter].increseStatsPokemonBy1();
            }
            if(opponents == Opponents.Neutral2) {
                //Creaza un pokemon de tip Neutrel pentru batalia dintre pokemonul antrenorului 1 si neutrel, folosind Factory
                Pokemon neutral = pf.createPokemon(PokemonType.Neutrel2);
                //Creaza copia pokemonului counter din lista de pokemoni al antrenorului 1
                Pokemon pokemon1 = pokemonsCoach1[counter].copy();
                //Apeleaza batalia cu parametri pokemonii si numele antrenorilor pentru a putea afisa castigatorul
                Battle battle = new Battle(pokemon1,neutral, coaches[0].getName(), coaches[1].getName(),myWriter);
                battle.run();
                //Pune intr-un string numele castigatorului acestei lupte
                String winner = pokemon1.getTheWinner(neutral, coaches[0].getName(), coaches[1].getName());
                //Daca castigatorul este pokemonul antrenorului atunci acesta va primi buff
                if(pokemon1.getName().equals(winner))
                    pokemonsCoach1[counter].increseStatsPokemonBy1();

                //Creaza un pokemon de tip Neutrel pentru batalia dintre pokemonul antrenorului 2 si neutrel, folosind Factory
                neutral = pf.createPokemon(PokemonType.Neutrel2);
                //Creaza copia pokemonului counter din lista de pokemoni al antrenorului 2
                Pokemon pokemon2 = pokemonsCoach2[counter].copy();
                //Apeleaza batalia cu parametri pokemonii si numele antrenorilor pentru a putea afisa castigatorul
                battle = new Battle(pokemon2,neutral, coaches[1].getName(), coaches[0].getName(),myWriter);
                battle.run();
                //Pune intr-un string numele castigatorului acestei lupte
                winner = pokemon2.getTheWinner(neutral, coaches[1].getName(), coaches[0].getName());
                //Daca castigatorul este pokemonul antrenorului atunci acesta va primi buff
                if(pokemon2.getName().equals(winner))
                    pokemonsCoach1[counter].increseStatsPokemonBy1();
            }

            //Arena a ales ca este timpul celor 2 antrenori de a se lupta intre ei
            if(opponents == Opponents.Opponent){
                //Vor fi 3 runde  unde pokemonul i al primului antrenor va lupta cu pokemonul i al celui de al 2 antrenor
                for(int i = 0; i < 3; i++){
                    System.out.println(ANSI_RED + "[][][][][][][][][][][][][] ROUND" + (i + 1) + " [][][][][][][][][][][][][]" + ANSI_RESET);
                    myWriter.write("[][][][][][][][][][][][][] ROUND" + (i + 1) + " [][][][][][][][][][][][][]\n");
                    //Creaza copia pokemonului i al antrenoului 1
                    Pokemon pokemon1 = pokemonsCoach1[i].copy();
                    //Seteaza itemele pe care le are setate pentru aceasta lupta
                    pokemon1.setUsedItems(getItems(pokemonsCoach1[i].getAvailableItems().get(0)));
                    //Aplica itemele pe pokemon
                    pokemon1.increseStatsPokemonByItem();
                    //Afiseaza la consola(fisier) informatia despre buff-ul oferit de iteme
                    pokemon1.pokemonItemsBuffInformation(myWriter);

                    //Creaza copia pokemonului i al antrenoului 2
                    Pokemon pokemon2 = pokemonsCoach2[i].copy();
                    //Seteaza itemele pe care le are setate pentru aceasta lupta
                    pokemon2.setUsedItems(getItems(pokemonsCoach2[i].getAvailableItems().get(0)));
                    //Aplica itemele pe pokemon
                    pokemon2.increseStatsPokemonByItem();
                    //Afiseaza la consola(fisier) informatia despre buff-ul oferit de iteme
                    pokemon2.pokemonItemsBuffInformation(myWriter);

                    //Lupta dintre cei 2 pokemoni
                    Battle battle = new Battle(pokemon1,pokemon2, coaches[0].getName(), coaches[1].getName(),myWriter);
                    battle.run();
                    //Pune intr-un string numele castigatorului acestei lupte
                    String winner = pokemon1.getTheWinner(pokemon2, coaches[0].getName(), coaches[1].getName());
                    //Daca castigatorul este pokemonul antrenorului 1
                    if(pokemon1.getName().equals(winner))
                        //Afiseaza la consola(fisier) numele antrenorului 1
                        pokemonsCoach1[i].increseStatsPokemonBy1();
                    //Daca castigatorul este pokemonul antrenorului 2
                    if(pokemon2.getName().equals(winner))
                        //Afiseaza la consola(fisier) numele antrenorului 2
                        pokemonsCoach2[i].increseStatsPokemonBy1();
                    /*Calculeaza scorul pokemonilor si le pun in bestPokemon in caz ca scorul curent este mai mare decat
                    scorul pokemonului precedent(daca exista) iar in caz ca scorul este egal, bestPokemon va fi cel
                    mai mic lexicografic. */
                    bestPokemon1.calculateScore(pokemon1,i);
                    bestPokemon2.calculateScore(pokemon2,i);
                }
                //Runda finala dintre cei mai buni pokemoni al celor 2 antrenori
                System.out.println(ANSI_RED + "[][][][][][][][][][][][][] FINAL ROUND [][][][][][][][][][][][][]" + ANSI_RESET);
                myWriter.write("[][][][][][][][][][][][][] FINAL ROUND [][][][][][][][][][][][][]\n");
                //Creaza copia celui mai bun pokemon al antrenorului 1
                Pokemon pokemon1 = pokemonsCoach1[bestPokemon1.getIndex()].copy();
                //Seteaza itemele pe care le are setate pentru aceasta lupta
                pokemon1.setUsedItems(getItems(pokemonsCoach1[bestPokemon1.getIndex()].getAvailableItems().get(1)));
                //Aplica itemele pe pokemon
                pokemon1.increseStatsPokemonByItem();
                //Afiseaza la consola(fisier) informatia despre buff-ul oferit de iteme
                pokemon1.pokemonItemsBuffInformation(myWriter);

                //Creaza copia celui mai bun pokemon al antrenorului 2
                Pokemon pokemon2 = pokemonsCoach2[bestPokemon2.getIndex()].copy();
                //Seteaza itemele pe care le are setate pentru aceasta lupta
                pokemon2.setUsedItems(getItems(pokemonsCoach2[bestPokemon2.getIndex()].getAvailableItems().get(1)));
                //Aplica itemele pe pokemon
                pokemon2.increseStatsPokemonByItem();
                //Afiseaza la consola(fisier) informatia despre buff-ul oferit de iteme
                pokemon2.pokemonItemsBuffInformation(myWriter);

                //Lupta dintre cei 2 pokemoni
                Battle battle = new Battle(pokemon1,pokemon2, coaches[0].getName(), coaches[1].getName(),myWriter);
                battle.run();
                //Pune intr-un string numele castigatorului acestei lupte
                String winner = pokemon1.getTheWinner(pokemon2, coaches[0].getName(), coaches[1].getName());
                //Daca exista castigator se va afisa numele antrenorului castigator, in caz contrar va afisa DRAW
                if(!winner.equals("")){
                    System.out.println("The battle was won by " + winner);
                    myWriter.write("The battle was won by " + winner + "\n");
                }
                else{
                    System.out.println("DRAW");
                    myWriter.write("DRAW\n");
                }
            }
        counter++;
        }while(opponents != Opponents.Opponent);
        System.out.println();
        myWriter.write("\n");
    }


}
