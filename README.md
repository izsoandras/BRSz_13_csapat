# Beágyazott Rendszerek Szoftvertechnológiája házifeladat
## Kígyós játék
### Alap játék
- egy kígyóval kell élelmet gyűjtenünk és ezzel pontot szereznünk
- ha a kígyó saját magába, vagy falba ütközik, akkor meghal és vége a játéknak
- a pálya mezőkre van osztva és ezek között a fejét követve halad a kígyó
- a játékos a kígyóval eldöntheti, hogy valamelyik irányba elfordul, vagy halad egyenesen tovább
- hátrafele nem fordulhat
#### Nehezség
- megevett normál (nem bónusz) élelmenként nő a sebesség egy kicsivel
- minden megevett étel után nő a kígyó
- nehézségi szint: van-e fal, hol van fal
- ha a pályán nincs fal a pálya szélén a kígyó a pálya másik oldalán visszatér a játékmezőre
#### Bónuszok és veszélyek
- mindig van egy normál élelem ami egyhelyben van
- néhány kajánkénk megjelenhet egy bónusz ami több pontot ad ha megeszik és véletlenszerűen mozog
- néhány kajánkénk megjelenhet egy veszély ami pontot von le ha megeszik és véletlenszerűen mozog
- a veszély és a bónusz lassabban mozog a kígyónál
#### Mentés
- ESC billentyű megnyomására a játék megáll
- megjelenik egy menü, itt van lehetőség mentés és kilépésre
- főmenüből van lehetőseg az előző játék folytatására
#### Toplista
- külön fájlba menti a program
- főmenüből top 10-et lehet megtekinteni
- a program a pontszám mellett a játékos nevét is elmenti
- a nehezebb pályán a pontszámokon szorzó van

#### Többjátékos mód
- a két játék függetlenül fut
- egymás mellett látszódik a saját és ellenfelünk táblája
- ha az egyik játékos meghal a másik még tovább játszhat amég ő is meghal