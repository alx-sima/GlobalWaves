# Global Waves

autor: Alexandru Sima
grupa: 322 CA

## Descriere

GlobalWaves este un program care simulează un player muzical, ce primește de-a lungul duratei de
funcționare diferite comenzi de la mai mulți utilizatori.

----------------------------------------------------------------------------------------------------

## Design Pattern-uri folosite

- `Singleton` pentru `Library` și `UserDatabase`.
- `Factory` pentru crearea de `Command`-uri în funcție de câmpul `type`.
- `Visitor` pentru a lucra diferit cu `Queue`-urile și `SongSource`-urile în funcție de tipul
  colecției ascultate.
- `Builder` pentru a construi rezultatele comenzilor, care pot fi modificate de alte comenzi din
  lanțul de moștenire.
- `Observer` pentru a implementa sistemul de notificări.
- `Command` pentru a implementa istoricul de pagini (`ChangePage`).
- `Strategy` pentru a schimba modul in care se schimba repeat-ul într-un `Queue`.

----------------------------------------------------------------------------------------------------

## Etapa 1

### Componente

#### Program

`Program`-ul este entitatea care stochează informațiile aplicației. Scopul său este de a încărca
biblioteca de melodii (`Library`) și utilizatorii (`User`), de a le elibera după execuție și de a
citi și executa comenzile (`Command` și subtipurile aferente) primite.

`Library`-ul este o componentă care stochează fișierele și colecțiile de fișiere audio.

`Searchbar`-ul este o componentă a programului care se ocupă de căutări, stocând rezultatele
acestora și selecțiile care le urmează.

#### User

Un `User` reprezintă un utilizator, astfel că reține informațiile personale ale acestuia (nume,
vârsta, oraș etc.), dar și melodiile apreciate, playlist-urile proprii și cele urmărite.

`User`-ul conține metode pentru creerea de noi playlist-uri, căutarea playlist-urilor deținute,
aprecierea și urmărirea de melodii, respectiv playlist-uri.

----------------------------------------------------------------------------------------------------

Fiecare utilizator are un `Player` de muzică asociat, care se ocupă cu gestionarea cozilor (`Queue`)
de fișiere audio ascultate de acesta.

Pentru a simula trecerea timpului, playerul reține timpul ultimei actualizări și, la o nouă
actualizare, avansează coada cu durata intervalului.

#### Queue

Un `Queue` reprezintă o coadă de fișiere care sunt cântate de player în ordine. Acesta poate fi un
`Podcast` sau un `SongQueue`. Acesta expune metode pentru gestionarea cozii de melodii (de ex. next,
prev, shuffle etc.), dar al căror mod de lucru diferă în funcție de subtipul cozii, deci sunt
reimplementate în acestea.

----------------------------------------------------------------------------------------------------

Un `SongQueue` este o coadă care conține melodii (fiind generată dintr-o singură melodie sau
dintr-un playlist, adică dintr-un `SongSource`).

Un `SongSource` este o abstractizare peste o colecție de melodii (playlist sau o melodie).

----------------------------------------------------------------------------------------------------

O coadă poate conține un `Shuffler` (doar când este creată dintr-un playlist), care, atunci când
există, transformând indicii melodiilor din playlist, pentru a randomiza ordinea acestora, folosind
seed-ul dat la creare.

#### Searchable

Un `Searchable` este un obiect care poate răspunde unor căutări (melodie, playlist, podcast). Acesta
posedă metode pentru verificarea dacă obiectul corespunde cu parametrii unei căutări și pentru
crearea unei cozi din această sursă.

#### Colecții audio

- Un `Playlist` este o colecție de melodii.
- Un `Podcast` este o colecție de episoade. În particular, acesta reține unde ajunge rularea
  episoadelor între ascultări diferite.

#### Fișiere audio

- Un `AudioFile` reprezintă un fișier abstract.
- Un `Episode` reprezintă un episod dintr-un podcast.
- Un `Song` reprezintă o melodie.

### Comenzi

`Command` reprezintă o comandă, atât intrarea acesteia, cât și ieșirea (vezi `CommandResult`).
Aceasta expune o metodă `execute`, care rulează efectiv comanda și întoarce un rezultat.

Fiecare tip de comandă are corespondent un subtip al lui `Command`, în care suprascrie modul de
funcționare al lui `execute`. Tipul este stabilit la parsarea inputului, întâi cu ajutorul
moștenirii oferite de biblioteca `Jackson` (pentru comenzile care au parametrii în plus), apoi
în funcție de câmpul `command`.

`CommandResult` reprezintă rezultatul execuției unei comenzi. Acesta moștenește `Command` (pentru
a-i afișa câmpurile). Deoarece rezultatele pot conține mai multe informații, există subtipuri ale
lui `CommandResult`, în funcție de ce trebuie afișat (`MessageResult`, `StatusResult` etc.).

----------------------------------------------------------------------------------------------------

## Etapa 2

Pentru a acomoda noile tipuri de utilizatori și comenzile aferente acestora, aplicația a suferit
câteva modificări.

### Componente

#### Library

`Library`-ul de melodii este un singleton, pentru a ușura accesul la acesta. Acesta se populează
la fiecare instanțiere a `Program`-ului.

Pentru a lucra diferit cu `Queue`-urile și `SongSource`-urile (reprezentând ce ascultă un utilizator
la un moment de timp) în funcție de tipul colecției ascultate, au fost adăugați **Visitori** pentru
acestea.

#### Users

- `User`-ii au acum status (online și offline), setat printr-o comandă. Când un utilizator este
offline, acesta nu mai ascultă fișiere și nici nu poate executa comenzi.

- `User`-ii rețin `Podcasturile` în timp ce le ascultă, pentru a putea continua de unde au rămas,
indiferent de cât ascultă ceilalți utilizatori.

Au fost adăugate 2 noi tipuri de utilizatori, `Artist` și `Host`, care extind clasa `User`. Toți
utilizatorii sunt stocați într-un `UserDatabase`, care este un singleton.

- `User`-ii au și o pagină (`Page`) pe care se află la un anumit moment de timp. Paginile pot fi
personale (`HomePage`, `LikedContentPage`) sau ale unor artiști sau hosturi.

- `User`-ii pot fi acum șterși printr-o comandă, doar dacă niciun alt utilizator nu interacționează
  în acel moment cu aceștia (ascultă fișiere/colecții de-ale sale sau se uita la pagina sa),
  ștergându-se și toate creațiile lor.

#### Commands

Pentru că au fost introduse multe comenzi care au dependințe (de ex. comenzile artiștilor au nevoie
ca utilizatorul care le execută să fie un artist, acest fapt necesitând ca, la rândul său,
utilizatorul să existe), au fost introduse comenzi abstracte care fac aceste verificări
(`OnlineUserCommand`, `ArtistCommand` etc.).

#### FileOutput

Deoarece comenzile pot eșua, caz în care trebuie afișat un mesaj de eroare (dar restul câmpurilor de
output trebuie să se potrivească formatului comenzii), rezultatele comenzilor (`CommandResult` și
derivatele) sunt acum construite folosind **Builderi**, creați de comanda efectivă, care pot fi
modificați de orice altă comanda din lanțul de moștenire.

----------------------------------------------------------------------------------------------------

## Etapa 3

Aplicația a fost din nou modificată, pentru a suporta noile comenzi și statistici.

### Componente

#### User

- Utilizatorii pot fi acum premium/non-premium.

- Utilizatorii pot deține și vizualiza `Merch`.

- Utilizatorii rețin melodiile ascultate, pentru a se putea monetiza melodiile.

#### Command Requirements

Pentru a deduplica verificările inițiale pentru anumite comenzi (de ex. dacă userul există/este 
online), acestea au fost mutate în clase speciale (`Requirement`), care verifică acea condiție și 
întorc rezultatul - dacă este pozitiv, sau aruncă o eroare în caz contrar.

#### Pages

Se reține un istoric de pagini (`PageHistory` care reține intrări de `SwitchPageCommand`) pentru 
fiecare utilizator, acum existând capabilități de undo/redo.

#### Notifications

Utilizatorii pot subscrie (`Subscriber`) la diferite entități (`Notifier`) care emit notificări
(`Notification`).

#### Wrapped

Utilizatorii pot cere un wrapped, caz în care se afișează statistici din aplicație, în funcție de
tipul utilizatorului.

#### Recommendations

Utilizatorii pot primi recomandări pe baza a ce ascultă în acel moment sau au ascultat înainte.
Acestea pot fi încărcate în coada de redare.

#### Excepții

Pentru a semnala că o comandă este invalidă, în loc de a returna o valoare de succes, se aruncă
excepția `InvalidOperation` în caz de eșec.

#### RepeatChangeStrategy

Pentru simplificare, modul în care se schimbă intre tipurile de `RepeatMode` este reținut într-un
`RepeatChangeStrategy`.

----------------------------------------------------------------------------------------------------

## Remarci finale

- Tema a fost interesantă, dar unele cerințe, deși aveau scopul de a simplifica treaba, mai mult
  m-au încurcat și, părerea mea, nu prea aveau sens. De exemplu:
  - De ce topurile melodiilor se făceau grupându-le *doar* după nume?
  - De ce se puteau adăuga ca hosts ownerii podcasturilor din bibliotecă?

- vim is the best (e bun și IntelliJ la refactor, dar operațiunile mai complicate (de ex. schimbarea
unor metode) s-au făcut mult mai rapid cu macro-uri din vim).