package main;

import Pokemons.*;

public class PokemonFactory {

    private static PokemonFactory singleInstance;

    /**
     * Tipurile de pokemoni.
     */
    public enum PokemonType{
        Bulbasaur,Charmander,Eevee,Jigglypuff,Meowth,Pikachu,Psyduck,Snorlax,Squirtle,Vulpix,Neutrel1,Neutrel2
    }

    public static PokemonFactory Instance(){
        if(singleInstance == null)
            singleInstance = new PokemonFactory();
        return singleInstance;
    }

    /**
     * @param pokemon Numele pokemonului.
     * @return Returneaza PokemonType in dependenta de pokemon.
     */
    public PokemonType returnType(String pokemon){
        switch (pokemon){
            case "Pikachu":
                return PokemonType.Pikachu;
            case "Bulbasaur":
                return PokemonType.Bulbasaur;
            case "Charmander":
                return PokemonType.Charmander;
            case "Squirtle":
                return PokemonType.Squirtle;
            case "Snorlax":
                return PokemonType.Snorlax;
            case "Vulpix":
                return PokemonType.Vulpix;
            case "Eevee":
                return PokemonType.Eevee;
            case "Jigglypuff":
                return PokemonType.Jigglypuff;
            case "Meowth":
                return PokemonType.Meowth;
            case "Psyduck":
                return PokemonType.Psyduck;
        }throw new IllegalArgumentException("Tipul de pokemon " + pokemon + " necunoscut");
    }

    /**
     * @param pokemonType Tipul de pokemon.
     * @return Returneaza pokemonul specificat initializat.
     */
    public Pokemon createPokemon(PokemonType pokemonType){
        switch (pokemonType){
            case Pikachu :
                return new Pikachu("Pikachu",35,0,4,2,3,
                        new Ability(6,false,false,4),new Ability(4,true,true,5));
            case Bulbasaur:
                return new Bulbasaur("Bulbasaur",42,0,5,3,1,
                        new Ability(6,false,false,4),new Ability(5,false,false,3));
            case Charmander:
                return new Charmander("Charmander",50,4,0,3,2,
                        new Ability(4,true,false,4),new Ability(7,false,false,6));
            case Squirtle:
                return new Squirtle("Squirtle",60,0,3,5,5,
                        new Ability(4,false,false,3),new Ability(2,true,false,2));
            case Snorlax:
                return new Snorlax("Snorlax",62,3,0,6,4,
                        new Ability(4,true,false,5),new Ability(0,false,true,5));
            case Vulpix:
                return new Vulpix("Vulpix",36,5,0,2,4,
                        new Ability(8,true,false,6),new Ability(2,false,true,7));
            case Eevee:
                return new Eevee("Eevee",39,0,4,3,3,
                        new Ability(5,false,false,3),new Ability(3,true,false,3));
            case Jigglypuff:
                return new Jigglypuff("Jigglypuff",34,4,0,2,3,
                        new Ability(4,true,false,4),new Ability(3,true,false,4));
            case Meowth:
                return new Meowth("Meowth",41,3,0,4,2,
                        new Ability(5,false,true,4),new Ability(1,false,true,3));
            case Psyduck:
                return new Psyduck("Psyduck",43,3,0,3,3,
                        new Ability(2,false,false,4),new Ability(2,true,false,5));
            case Neutrel1:
                return new Neutrel1("Neutrel1",10,3,0,1,1,
                        new Ability(),new Ability());
            case Neutrel2:
                return new Neutrel2("Neutrel2",20,4,0,1,1,
                        new Ability(),new Ability());
        }
        throw new IllegalArgumentException("Tipul de pokemon " + pokemonType + " necunoscut");
    }
}
