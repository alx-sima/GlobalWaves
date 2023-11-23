# Global Waves - etapa 1

autor: Alexandru Sima
grupa: 322 CA

## Descriere

GlobalWaves este un program care simulează un player muzical, care primește de-a lungul duratei de
funcționare diferite comenzi de la mai mulți utilizatori.

## Componente

### Program

`Program`-ul este entitatea care stochează informațiile aplicației. Scopul său este de a încărca
biblioteca de melodii (`Library`) și utilizatorii (`User`), de a le elibera după execuție și de a 
citi și executa comenzile (`Command` și subtipurile aferente) primite.

`Library`-ul este o componentă care stochează fișierele și colecțiile de fișiere audio.

`Searchbar`-ul este o componentă a programului care se ocupă de căutări, stocând rezultatele
acestora și selecțiile care le urmează.

### User

Un `User` reprezintă un utilizator, astfel că reține informațiile personale ale acestuia (nume,
vârsta, oraș etc.), dar și melodiile apreciate, playlist-urile proprii și cele urmărite.

`User`-ul conține metode pentru creerea de noi playlist-uri, căutarea playlist-urilor deținute,
aprecierea și urmărirea de melodii, respectiv playlist-uri.

---

Fiecare utilizator are un `Player` de muzică asociat, care se ocupă cu gestionarea cozilor (`Queue`)
de fișiere audio ascultate de acesta.

Pentru a simula trecerea timpului, playerul reține timpul ultimei actualizări și, la o nouă
actualizare, avansează coada cu durata intervalului.

### Queue

Un `Queue` reprezintă o coadă de fișiere care sunt cântate de player în ordine. Acesta poate fi un
`Podcast` sau un `SongQueue`. Acesta expune metode pentru gestionarea cozii de melodii (de ex. next,
prev, shuffle etc.), dar al căror mod de lucru diferă în funcție de subtipul cozii, deci sunt
reimplementate în acestea[1].

---

Un `SongQueue` este o coadă care conține melodii (fiind generată dintr-o singură melodie sau
dintr-un playlist, adică dintr-un `SongSource`).

Un `SongSource` este o abstractizare peste o colecție de melodii (playlist sau o melodie).

---

O coadă poate conține un `Shuffler` (doar când este creată dintr-un playlist), care, atunci când 
există, transformând indicii melodiilor din playlist, pentru a randomiza ordinea acestora, folosind
seed-ul dat la creare.

### Searchable

Un `Searchable` este un obiect care poate răspunde unor căutări (melodie, playlist, podcast). Acesta
posedă metode pentru verificarea dacă obiectul corespunde cu parametrii unei căutări și pentru
crearea unei cozi din această sursă.

### Colecții audio

- Un `Playlist` este o colecție de melodii.
- Un `Podcast` este o colecție de episoade. În particular, acesta reține unde ajunge rularea
  episoadelor între ascultări diferite.

### Fișiere audio

- Un `AudioFile` reprezintă un fișier abstract.
- Un `Episode` reprezintă un episod dintr-un podcast.
- Un `Song` reprezintă o melodie.

## Comenzi

`Command` reprezintă o comandă, atât intrarea acesteia, cât și ieșirea (vezi `CommandResult`).
Aceasta expune o metodă `execute`, care rulează efectiv comanda și întoarce un rezultat.

Fiecare tip de comandă are corespondent un subtip al lui `Command`, în care suprascrie modul de
funcționare al lui `execute`. Tipul este stabilit la parsarea inputului, întâi cu ajutorul
moștenirii oferite de biblioteca `Jackson` (pentru comenzile care au parametrii în plus), apoi
în funcție de câmpul `command`.

`CommandResult` reprezintă rezultatul execuției unei comenzi. Acesta moștenește `Command` (pentru
a-i afișa câmpurile). Deoarece rezultatele pot conține mai multe informații, există subtipuri ale
lui `CommandResult`, în funcție de ce trebuie afișat (`MessageResult`, `StatusResult` etc.).

---

[1]: Inițial, am optat pentru folosirea **Visitor**-ului (citind laboratorul🤓), însă mi s-a părut
overkill, așa ca am decis să folosesc **Template Method** (pentru funcționalitățile disponibile doar
într-o anumită subclasă, clasa părinte va avea o metodă care eșuează (*null* sau *false*), iar
subclasa care posedă funcționalitatea va suprascrie acea metodă).

