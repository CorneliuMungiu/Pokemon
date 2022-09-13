# Pokemon Adventure
### Introducere
Scopul acestui proiect este realizarea unei aplicații unde antrenorii de pokemoni să poată să își
crească pokemonii și să se poată duela cu alți antrenori. Aplicația ține cont în
permanență de principiile POO și, de asemenea, trebuie folosește conceptele de Design Patterns.

## Descriere
În acest univers Pokemon există antrenorii de pokemoni despre care cunoaștem numele,vârsta și 
pokemonii pe care fiecare dintre ei îi deține.  
Mai există, bineînțeles, și pokemonii efectivi. Aceștia au un nume (care de fapt reprezintă tipul
pokemonului, ex: Pikachu), HP (din engleză “hit points”, care reprezintă câte puncte de viață are
un pokemon în momentul când participă într-o bătălie), Attack (puterea unui atac normal),
Special Attack (puterea unui atac special), Defense (gradul de apărare împotriva unui atac
normal), Special Defense (gradul de apărare împotriva unui atac special). De asemenea,
fiecare pokemon va avea două abilități speciale care product o lovitură (engleză “damage”)
și/sau imobilizează pokemonul cu care se bat (engleză “stun”) și/sau eschivează următoarea
mișcare a inamicului (engleză “dodge”) și un timp de reîncărcare a mișcării (engleză
“cooldown”). Mai multe detalii vor fi prezentate pentru fiecare pokemon în parte și în secțiunile
următoare.
Observație: Un pokemon va avea un singur tip de atac - ori atac normal, ori atac special.
Universul acesta este un pic diferit decât cele Pokemon obișnuite și oferă posibilitatea ca un
pokemon să “poarte” cu el într-o bătălie până la 3 obiecte care îi îmbunătățesc calitățile.
Antrenorii au acces la o mulțime de obiecte și le pot oferi pokemonilor înainte de o bătălie. Nu
există o limitare asupra unui singur obiect să poată fi folosit doar de un pokemon al unui antrenor. 
Adică dacă avem obiectul “scut” și un antrenor cu 3 pokemoni, toți pokemonii pot purta scutul.
Singura limitare o constituie faptul că un pokemon nu poate purta același obiect de mai multe
ori în aceeași bătălie. Adică nu poate avea scut, scut, scut.

## Aventura
Aventura noastră se desfășoară într-o arenă. În arenă vor intra de fiecare dată doi antrenori,
fiecare dintre ei având posibilitatea să își aleagă cu ce pokemon din lista lor să intre în arenă și
ce obiecte să îi ofere acestuia.  
În această arenă se pot întâmpla 3 evenimente decise într-un mod aleator de către arenă:  
1. Fiecare antrenor împreună cu pokemonul său se va lupta separat cu un pokemon neutru
de tipul 1 (Neutrel1).
2. Fiecare antrenor împreună cu pokemonul său se va lupta separat cu un pokemon neutru
de tipul 2 (Neutrel2).
3. Antrenorii împreună cu pokemonul lor se vor duela între ei. (Aceasta va fi ultima luptă
din arenă iar antrenorul care câștigă această luptă va fi declarat câștigătorul aventurii).  

O bătălie se va desfășura în felul următor:  
- La fiecare moment de timp fiecare pokemon aflat în bătălie va executa o comandă
indicată de antrenor.
- Antrenorul este cel care controlează pokemonul și îi dă comenzi acestuia. Comenzile pot
fi:
    - Atac obișnuit - atunci pokemonul va ataca pokemonul advers cu tipul de atac pe
    care acesta îl posedă (normal / special).
    - Abilitate 1
    - Abilitate 2
- În cazul în care se execută o abilitate aceasta va întoarce 4 informații:
    - Damage-ul produs
    - Dacă este stun
    - Dacă se feresc (dodge) prin această abilitate
    - Cooldown, câte momente de timp durează până când abilitatea va fi din nou
    disponibilă
- În momentul în care un pokemon este atacat își va actualiza starea (HP-ul și dacă este
blocat, adică dacă a primit stun) în concordanță cu mișcarea inamicului și mișcarea sa:
    - Dacă el a executat o abilitate care conține dodge atunci starea sa rămâne
    nemodificată indiferent de ce a făcut celălalt pokemon. Practic, în acest caz nu
    mai contează ce a făcut pokemonul advers și detaliile de mai jos pot fi ignorate.
    - Dacă inamicul a executat un atac obișnuit atunci el va pierde HP egal cu
    diferența dintre tipul atacului inamicului și apărarea sa pe acel tip de atac.  
    Exemplu: dacă pokemonul inamic are atac normal 50 și pokemonul curent are
    defense 20, el va pierde 30 de puncte de viață; dacă pokemonul inamic are atac special 
    25 și pokemonul curent are special defense 5, el va pierde 20 de puncte
    de viață.
    - Dacă inamicul a executat o abilitate atunci damage-ul produs de abilitate se va
    scădea din punctele de viață indiferent de defense-ul pokemonului actual. Dacă
    abilitatea inamicului este o abilitate care conține stun atunci pokemonul nu va
    putea executa nicio mișcare la momentul de timp următor.
- Aventura se va termina când unul sau amândoi pokemonii rămân fără puncte de viață.
Acest lucru se poate întâmpla când pokemonii se bat cu pokemoni neutrii sau când
antrenorii se bat între ei. Arena va trebui să returneze numele antrenorului câștigător sau
“Draw” în caz de egalitate.
- După ce o luptă (eveniment) este terminată, pokemonii revin la starea inițială din punctul
de vedere al HP-ului. De asemenea, pokemonul unui antrenor care a câștigat o bătălie
(indiferent de tipul bătăliei (vs un pokemon neutru sau vs un pokemon al altui antrenor))
va primi 1 punct în plus la toate caracteristicile sale: hp, atac (normal sau special),
defense și special defense.  

Observație: Bătălia dintre cei doi antrenori se va realiza folosind **threaduri**.

## Pokemoni
Tabelul de mai jos oferă detalii despre fiecare pokemon din proiect:  
Prescurtări: Dmg - Damage, Cd - Cooldown, Def - Defense  
Name|HP|Normal Attack|Special Attack|Def|Special Def|Ability 1|Ability 2|
|--|--|--|--|--|--|--|--|
|Neutrel1|10|3|N/A|1|1|N/A|N/A|
|Neutrel2|20|4|N/A|1|1|N/A|N/A|
|Pikachu|35|N/A|4|2|3|Dmg: 6<br>Stun: No<br> Dodge: No<br>Cd: 4|Dmg: 4<br>Stun: Yes<br> Dodge: Yes<br>Cd: 5|
|Bulbasaur|42|N/A|5|3|1|Dmg: 6<br>Stun: No<br> Dodge: No<br>Cd: 4|Dmg: 5<br>Stun: No<br> Dodge: No<br>Cd: 3|
|Charmander|50|4|N/A|3|2|Dmg: 4<br>Stun: Yes<br> Dodge: No<br>Cd: 4|Dmg: 7<br>Stun: No<br> Dodge: No<br>Cd: 6|
Squirtle|60|N/A|3|5|5|Dmg: 4<br>Stun: No<br> Dodge: No<br>Cd: 3|Dmg: 2<br>Stun: Yes<br> Dodge: No<br>Cd: 2|
|Snorlax|62|3|N/A|6|4|Dmg: 4<br>Stun: Yes<br> Dodge: No<br>Cd: 5|Dmg: 0<br>Stun: No<br> Dodge: Yes<br>Cd: 5|
|Vulpix|36|5|N/A|2|4|Dmg: 8<br>Stun: Yes<br> Dodge: No<br>Cd: 6|Dmg: 2<br>Stun: No<br> Dodge: Yes<br>Cd: 7|
|Eevee|39|N/A|4|3|3|Dmg: 5<br>Stun: No<br> Dodge: No<br>Cd: 3|Dmg: 3<br>Stun: Yes<br> Dodge: No<br>Cd: 3|
|Jigglypuff|34|4|N/A|2|3|Dmg: 4<br>Stun: Yes<br> Dodge: No<br>Cd: 4|Dmg: 3<br>Stun: Yes<br> Dodge: No<br>Cd: 4
|Meowth|41|3|N/A|4|2|Dmg: 5<br>Stun: No<br> Dodge: Yes<br>Cd: 4|Dmg: 1<br>Stun: No<br> Dodge: Yes<br>Cd: 3|
|Psyduck|43|3|N/A|3|3|Dmg: 2<br>Stun: No<br> Dodge: No<br>Cd: 4|Dmg: 2<br>Stun: Yes<br> Dodge: No<br>Cd: 5|

## Items (Obiecte)
Tabelul de mai jos oferă detalii despre obiectele care pot fi oferite de către antrenori
pokemonilor lor pentru a le crește calitățile:

Name|HP|Attack|Special Attack|Defense|Special Defense|
|--|--|--|--|--|--|
|Scut|-|-|-|+2|+2|
|Vestă|+10|-|-|-|-|
|Săbiuță|-|+3|-|-|-|
|Baghetă Magică|-|-|+3|-|-|
|Vitamine|+2|+2|+2|-|-|
|Brad de Crăciun|-|+3|-|+1|-|
|Pelerină|-|-|-|-|+3|

## Studiu de caz: Aventura
Vom lua în continuare un exemplu de Aventură.  
În arenă intră Antrenorul1 cu Psyduck și Antrenorul2 cu Bulbasaur. Psyduck va avea obiectele Vitamine si Brad de Craciun
iar Bulbasaur va avea obiectele Vitamine, Brad de Craciun si Sabiuta.

Psyduck has these items[(Vitamine),(Brad_De_Craciun)]  
+2 hp; +5 normalAttack; +1 defense; +0 specialDefense  
Bulbasaur has these items[(Vitamine),(Brad_De_Craciun),(Sabiuta)]  
+2 hp; +2 specialAttack; +1 defense; +0 specialDefense  
----------------------Step1----------------------  
**Psyduck ability2 / Bulbasaur specialAttack /**  
	Psyduck HP 41 (45 - 4), abilitate 2 cooldown 5  
	Bulbasaur HP 42 (44 - 2)  
----------------------Step2----------------------  
**Psyduck normalAttack / Bulbasaur specialAttack /**  
**Bulbasaur is stunned this round**  
	Psyduck HP 41 (41 - 0), abilitate 2 cooldown 4  
	Bulbasaur HP 38 (42 - 4)   
----------------------Step3----------------------  
**Psyduck normalAttack / Bulbasaur ability2 /**  
	Psyduck HP 36 (41 - 5), abilitate 2 cooldown 3  
	Bulbasaur HP 34 (38 - 4), abilitate 2 cooldown 3  
----------------------Step4----------------------  
**Psyduck normalAttack / Bulbasaur ability1 /**  
	Psyduck HP 30 (36 - 6), abilitate 2 cooldown 2  
	Bulbasaur HP 30 (34 - 4), abilitate 1 cooldown 4 abilitate 2 cooldown 2  
----------------------Step5----------------------  
**Psyduck normalAttack / Bulbasaur specialAttack /**  
	Psyduck HP 26 (30 - 4), abilitate 2 cooldown 1  
	Bulbasaur HP 26 (30 - 4), abilitate 1 cooldown 3 abilitate 2 cooldown 1  
----------------------Step6----------------------  
**Psyduck ability1 / Bulbasaur specialAttack /**  
	Psyduck HP 22 (26 - 4), abilitate 1 cooldown 4  
	Bulbasaur HP 24 (26 - 2), abilitate 1 cooldown 2  
----------------------Step7----------------------  
**Psyduck normalAttack / Bulbasaur ability2 /**  
	Psyduck HP 17 (22 - 5), abilitate 1 cooldown 3  
	Bulbasaur HP 20 (24 - 4), abilitate 1 cooldown 1 abilitate 2 cooldown 3  
----------------------Step8----------------------  
**Psyduck normalAttack / Bulbasaur specialAttack /**  
	Psyduck HP 13 (17 - 4), abilitate 1 cooldown 2  
	Bulbasaur HP 16 (20 - 4), abilitate 2 cooldown 2  
----------------------Step9----------------------  
**Psyduck normalAttack / Bulbasaur ability1 /**  
	Psyduck HP 7 (13 - 6), abilitate 1 cooldown 1  
	Bulbasaur HP 12 (16 - 4), abilitate 1 cooldown 4 abilitate 2 cooldown 1  
----------------------Step10----------------------  
**Psyduck normalAttack / Bulbasaur specialAttack /**  
	Psyduck HP 3 (7 - 4)  
	Bulbasaur HP 8 (12 - 4), abilitate 1 cooldown 3  
----------------------Step11----------------------  
**Psyduck ability1 / Bulbasaur specialAttack /**  
	Psyduck HP -1 (3 - 4), abilitate 1 cooldown 4  
	Bulbasaur HP 6 (8 - 2), abilitate 1 cooldown 2  
**Antrenorul2 Wins**  

## Implementare
**Ability.java**  
Clasa care pastreaza informatia despre o abilitate: Damage, Stun, Dodge, Cooldown.  

**Coach.java**  
Clasa care pastreaza informatia despre numele antrenorului , varsta si lista de 3 pokemoni care vor pleca in lupta.  

**Item.java**  
Clasa care pastreaza informatia despre un item: hp, attack, specialAttack, defense, specialDefense.  
Obiectele se creeaza cu ajutorul Design Patern-ului Builder care seteaza doar variabilele care ofera buff.
- *Metoda toString*  
    Afiseaza la consola informatia despre un Item.  
- *Metoda createItem*  
    Primeste un item (String) si intoarce item-ul creat deja.  

**PokemonFactory.java**  
Clasa PokemonFactory foloseste Design Patern-ul Singleton pentru a crea o singura instanta a unui obiect creat.  
- *enum PokemonType*  
    Lista de Pokemoni de tip enum.  
- *returnType*  
    Primeste numele pokemonului (String) si intoarce un pokemon de tip PokemonType.  
- *createPokemon*  
    Primeste un PokemonType si intoarce un pokemon de tip Pokemon deja initializat.  

**ReadFromFile.java**  
Fisierul de input pe care il primeste are pe prima linie numele antrenorului 1, pe urmatoarea linie are varsta acestuia,
respectiv pe linia 3 numele antrenorului 2 iar pe linia urmatoare varsta acestuia. Intrucat un pokemon va lupta maxim de 2 ori 
(in runda initiala si in runda finala daca ajunge acolo), pe urmatoarele linii este scris numele pokemonului urmat de 2 linii care
vor incepe cu cuvantul item si inca maxim 3 iteme care nu se repeta. In caz ca nu se aplica nici un item pentru o lupta, se va scrie
cuvantul nothing.  
*Exemplu:*  
Player1  
20  
Player2  
34  
Pikachu  
item Scut  
item Vesta Vitamine Brad_De_Craciun  
Snorlax  
item nothing  
item Vesta Sabiuta Scut  
Meowth  
item nothing  
item nothing  
Vulpix  
item Vesta  
item Scut  
Squirtle  
item Scut  
item nothing  
Eevee  
item nothing  
item Scut  

Player1 are 20 ani si are Pokemonii: Pikachu, Snorlax si Meowth cu itemele respective.  
Player2 are 34 ani si are Pokemonii: Vulpix, Squirtle si Eevee cu itemele respective.

- *readFile*  
  Primeste ca parametru numele fisierului din care trebuie sa citeasca si returneaza o lista de 2 antrenori, unde 
  fiecare antrenor va fi initializat cu numele sau, varsta si lista de pokemoni pe care ii are , respectiv itemele
  pe care le are fiecare pokemon pentru lupta initiala si lupta finala.  

Observație: Atunci cand un pokemon se va lupta cu un neutrel, acesta nu va folosi nici un item.

**Administration.java**  
Clasa pentru testarea proiectului.  
- *main*  
    Citeste 10 fisiere de test din folderul "test/in" cu numele [1.in, 2.in .. 10.in] iar pentru fiecare fisier ".in" 
    trimite antrenorii in batalie. De asemenea creeaza fisierele de output in care se vor scrie rezultatele luptei.

**BestPokemon.java**  
Clasa BestPokemon va salva informatiile despre cel mai bun pokemon: name, score, index (indexul pokemonului in lista de 
pokemoni a antrenorului).  
- *calculateScore*  
    Scorul se calculeaza adunand toate skill-urile pokemonului (fara abilitati) dupa lupta , iar pentru ca pokemonul care 
    a pierdut lupta sa nu aiba scorul prea mic , hp se va reseta la 0. In caz ca scorul pokemonului care vrea sa isi calculeze 
    scorul este mai mare decat scorul setat ca best (initial scorul se seteaza ca 0), atunci noul pokemon devine best si se 
    updateaza campurile name, score si index. In caz ca se obtine un scor egal cu scorul deja best, se va alege ca best pokemonul
    aflat mai sus lexicografic.  

**Pokemon.java**  
Clasa Pokemon pastreaza informatia despre un pokemon: name, hp, normalAttack, specialAttack, defense, specialDefense, ability1, 
ability2, ability1CD (cooldown-ul abilitatii 1), ability2 (cooldown-ul abilitatii2), availableItems (primul element din lista va 
pastra lista de iteme pentru lupta initiala iar al 2-lea element va pastra lista de iteme pentru lupta finala (Lista de iteme este
reprezentata ca String)), isStunned (True daca pokemonul are stun), usedItems (Obiectele de tip Item care le foloseste in acea lupta
pokemonul) , Alive (True daca hp pokemonului este mai mare decat 0).

- *increseStatsPokemonBy1*  
    Incrementeaza fiecare skill al pokemonului cu 1 (cu exceptia abilitatilor si a skill-urilor care nu le are intrucat un pokemon 
    poate avea doar normalAttack sau doar specialAttack). Aceasta metoda este folosita atunci cand un pokemon castiga o batalie.  
- *increseStatsPokemonByItem*  
    Parcurge lista de iteme din usedItems si aplica buff-urile pe skill-urile pe care le are.  
- *getTheWinner*  
    Returneaza numele antrenorului castigator intrucat intr-o batalie se pot lupta 2 pokemoni de acelasi tip. In caz ca nu exista un 
    castigator va returna "" String gol.
- *enum AttackTypes*  
    Tipurile de atac a pokemonilor. Intrucat unii pokemoni au doar normalAttack iar altii doar specialAttack , vom considera 
    normalAttack atat ca normalAttack cat si ca specialAttack.  
- *getRandomAttackType*  
    Returneaza un AttackType ales random care respecta conditia ca AttackType-ul ales nu are cooldown si ca pokemonul nu are 
    stun (daca pokemonul are stun se alege defaut normalAttack).  
- *PokemonAttack*  
    Pokemonul this isi calculeaza schimbarile sale in urma atacului pokemonului adversar (si le decrementeaza in caz ca este necesar). 
    De asemenea se iau in consideratie si abilitatile pokemonului this intrucat acestea pot avea dodge. Se decrementeza cooldown-ul
    fiecarei abilitati daca acesta este si se afiseaza la consola (in fiesierul de output) informatii despre cooldown-ul ramas si 
    despre efectul uneia din abilitati (dodge sau stun). Daca pokemonul this a folosit o abilitate se updateaza cooldown-ul acesteia.  
- *pokemonInformation*  
    Afiseaza la consola(fisier) cat hp a avut pokemonul / a pierdut pokemonul si cat timp mai are cooldown-ul pentru fiecare abilitate.
- *pokemonItemsBuffInformation*  
    Afiseaza toate itemele pokemonului si buff-ul oferit de iteme pentru fiecare skill.
- *Metoda abstracta copy*  
    Intrucat un pokemon poate participa in mai multe batalii, la inceputul fiecarei lupte se creeaza o copie a pokemonului pentru a 
    putea fi resetat ulterior (nu se poate crea un alt pokemon de acel tip intrucat dupa fiecare lupta castigata , skill-urile 
    pokemonului sunt incrementate).  
    
**Battle.java**  
Clasa Battle pastreaza informatia despre cei 2 pokemoni care se lupta si numele antrenorilor.  

- *run*  
  Lupta dintre 2 pokemoni. Aceasta se termina atunci cand unul (sau ambii) dintre pokemoni nu mai are hp si se considera castigor 
  pokemonul cu hp pozitiv (sau draw daca dupa ultima lovitura amandoi au mai putin decat 1 hp). La fiecare pas se alege aleator 
  tipul de atack disponibil (nu se pot alege albilitatile care au cooldown) al fiecarui pokemon, desigur pokemonii nu stiu ce tip 
  de atac va alege adversarul. Daca pokemonul a primit stun din pasul trecut acesta nu va mai alege abilitate pentru a nu primi 
  cooldown nefolosind abilitatea. La inceputul fiecarei runde se afiseaza (la consola si in fisierul de output) informatia generala 
  despre pokemon + itemele le care le are iar la fiecare moment din lupta se afiseaza informatia despre starea pokemonului 
  (hp, daca este in stun sau daca are dodge) si informatia despre cooldown-ul abilitatilor iar la final se afiseaza numele antrenorului 
  castigator (antrenorii pot avea pokemonii identici).  

**Arena.java**  
Arena propriuzisa unde se desfasoara toate luptele dintre pokemoni.  

- *enum Opponents*  
  Tipuri de adversari ale unui antrenor. Acestea pot fi Neutral1 , Neutral2 si Opponent(celalalt antrenor).  
- *generateRandomOpponent*  
  Returneaza un element random din enum Opponents.  
- *getItems*  
  Transforma o lista de Stringuri cu numele obiectelor intr-o lista de obiecte te tip Item.  
- *arenaBattle*  
    Se extrage random tipul de oponent (Neutral1, Neutral2, Opponent) , iar atat timp cat tipul de oponent nu a fost extras "Opponent" 
    atunci fiecare pokemoni "i" ai antrenorilor se lupta cu neutrei si in caz de castig se incrementeaza toate skill-urile cu 1. Odata 
    ce tipul de oponent a fost ales Opponent se incepe lupta dintre cei 3 pokemoni ai antrenorilor, pokemonul "i" al antrenorului 1 se 
    va lupta cu pokemonul "i" al antrenorului 2, castigatorului i se vor incrementa skilurile si la finalul celor 3 lupte, va trebui 
    fi ales cel mai bun pokemon al fiecarui antrenor pentru a pleca in lupta finala. Nu conteaza cate castiguri a avut fiecare antrenor 
    pana la lupta finala, castiga jocul cel care castiga runda finala unde se lupta cei mai buni pokemoni al celor 2 antrenori.
