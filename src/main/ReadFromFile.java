package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFromFile {

    /**
     *
     * @param filename Numele fisierului.
     * @return Lista de 2 antrenori setati cu pokemonii si itemele citite din fisier.
     */
    public static Coach[] readFile(String filename) {
        Coach[] coaches = new Coach[2];
        Pokemon[] pokemonsPlayer1 = new Pokemon[3];
        Pokemon[] pokemonsPlayer2 = new Pokemon[3];
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            PokemonFactory pf = PokemonFactory.Instance();

            //Citeste datele despre numele si varsta antrenorului
            coaches[0] = new Coach(myReader.nextLine(),myReader.nextInt());
            myReader.nextLine();
            coaches[1] = new Coach(myReader.nextLine(),myReader.nextInt());
            myReader.nextLine();

            int counterPokemonsPlayer1 = 0;
            int counterPokemonsPlayer2 = 0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] line = data.split(" ",4);
                //Daca citeste o lista de iteme
                if(line[0].equals("item")){
                    if(counterPokemonsPlayer1 < 4){
                        //Daca primul argument este nothing atunci pentru acesta batalie acest pokemon nu va folosi nici un item
                        if(!(line[1].equals("nothing"))){
                            //Creaza un String[] care va pastra toate itemele pe care le va folosi pokemonul in batalie
                            String[] aux = new String[line.length - 1];
                            for(int i = 0; i < line.length - 1; i++){
                                aux[i] = line[i + 1];
                            }
                            //Adauga acest vector de stringuri in ArrayList
                            pokemonsPlayer1[counterPokemonsPlayer1 - 1].getAvailableItems().add(aux);
                        }else{
                            pokemonsPlayer1[counterPokemonsPlayer1 - 1].getAvailableItems().add(new String[0]);
                        }
                    }else{
                        //Daca primul argument este nothing atunci pentru acesta batalie acest pokemon nu va folosi nici un item
                        if(!(line[1].equals("nothing"))){
                            //Creaza un String[] care va pastra toate itemele pe care le va folosi pokemonul in batalie
                            String[] aux = new String[line.length - 1];
                            for(int i = 0; i < line.length - 1; i++)
                                aux[i] = line[i + 1];
                            //Adauga acest vector de stringuri in ArrayList
                            pokemonsPlayer2[counterPokemonsPlayer2 - 1].getAvailableItems().add(aux);
                        }else{
                            pokemonsPlayer2[counterPokemonsPlayer2 - 1].getAvailableItems().add(new String[0]);
                        }
                    }
                //Se citeste numele unui pokemon
                }else{
                    //Daca inca nu s-au citit 3 pokemoni inseamna ca inca se citesc pokemonii primului antrenor
                    if(counterPokemonsPlayer1 < 3){
                        pokemonsPlayer1[counterPokemonsPlayer1] = pf.createPokemon(pf.returnType(line[0]));
                        counterPokemonsPlayer1++;
                    //Se citesc pokemonii antrenorului 2
                    }else{
                        pokemonsPlayer2[counterPokemonsPlayer2] = pf.createPokemon(pf.returnType(line[0]));
                        counterPokemonsPlayer1++;
                        counterPokemonsPlayer2++;
                    }

                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        coaches[0].setPokemons(pokemonsPlayer1);
        coaches[1].setPokemons(pokemonsPlayer2);
        return coaches;
    }

}
