package main;

public class Item {
    private final String name;
    private int hp;
    private int attack;
    private int specialAttack;
    private int defense;
    private int specialDefense;

    private Item(ItemBuilder builder){
        this.name = builder.name;
        this.hp = builder.hp;
        this.attack = builder.attack;
        this.specialAttack = builder.specialAttack;
        this.defense = builder.defense;
        this.specialDefense = builder.specialDefense;
    }

    @Override
    public String toString() {
        return "main.Pokemon{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", attack=" + attack +
                ", specialAttack=" + specialAttack +
                ", defense=" + defense +
                ", specialDefense=" + specialDefense +
                '}';
    }
    //Design patternul Builder
    public static class ItemBuilder {
        private final String name;
        private int hp;
        private int attack;
        private int specialAttack;
        private int defense;
        private int specialDefense;

        public ItemBuilder(String name){
            this.name = name;
        }
        public ItemBuilder hp(int hp){
            this.hp = hp;
            return this;
        }

        public ItemBuilder attack(int attack){
            this.attack = attack;
            return this;
        }

        public ItemBuilder specialAttack(int specialAttack){
            this.specialAttack = specialAttack;
            return this;
        }

        public ItemBuilder defense(int defense){
            this.defense = defense;
            return this;
        }

        public ItemBuilder specialDefense(int specialDefense){
            this.specialDefense = specialDefense;
            return this;
        }

        public Item build(){
            return new Item(this);
        }
    }

    /**
     * @param item Lista de iteme (String).
     * @return Lista de iteme de tip Item.
     */
    public static Item createItem(String item){
        switch (item){
            case "Scut":
                return new ItemBuilder("Scut")
                        .defense(2)
                        .specialDefense(2)
                        .build();
            case "Vesta":
                return new ItemBuilder("Vesta")
                        .hp(10)
                        .build();
            case "Sabiuta" :
                return new ItemBuilder("Sabiuta")
                        .attack(3)
                        .build();
            case "Bagheta_Magica":
                return new ItemBuilder("Bagheta_Magica")
                        .specialAttack(3)
                        .build();
            case "Vitamine":
                return new ItemBuilder("Vitamine")
                        .hp(2)
                        .attack(2)
                        .specialAttack(2)
                        .build();
            case "Brad_De_Craciun":
                return new ItemBuilder("Brad_De_Craciun")
                        .attack(3)
                        .defense(1)
                        .build();
            case "Pelerina":
                return new ItemBuilder("Pelerina")
                        .specialDefense(3)
                        .build();
        }
        throw new IllegalArgumentException("Tip de item " + item + " necunoscut");
    }


    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

}