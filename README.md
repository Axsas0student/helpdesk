# Dokumentacja uruchomienia projektu Helpdesk

## Wymagania

Do uruchomienia projektu potrzebne są:

- Java 21
- Node.js
- npm
- Angular CLI
- przeglądarka internetowa
- opcjonalnie IntelliJ IDEA

Projekt składa się z dwóch części:

- Backend:  Spring Boot
- Frontend: Angular

Backend działa domyślnie pod adresem:

http://localhost:8080

Frontend działa domyślnie pod adresem:

http://localhost:4200

---

## Struktura projektu

Główny katalog projektu zawiera backend oraz frontend:

```text
helpdesk
├── src
├── frontend
├── pom.xml
├── mvnw
├── mvnw.cmd
├── PEŁNA_DOKUMENTACJA.md
└── README.md
```

Folder src zawiera kod backendu Spring Boot.

Folder frontend zawiera aplikację Angular.

---

## Uruchomienie backendu

Przejdź do głównego katalogu projektu i uruchom backend poleceniem:

```powershell
.\mvnw.cmd spring-boot:run
```

Po poprawnym uruchomieniu backend będzie dostępny pod adresem:

http://localhost:8080

API aplikacji znajduje się pod adresem:

http://localhost:8080/api

---

## Uruchomienie frontendu

Otwórz drugi terminal.

Przejdź do folderu frontendu. Przy pierwszym uruchomieniu zainstaluj zależności:

```powershell
npm install
```

Następnie uruchom frontend:

```powershell
ng serve
```

Po poprawnym uruchomieniu frontend będzie dostępny pod adresem:

http://localhost:4200

---

## Kolejność uruchamiania

Zalecana kolejność:

1. Uruchomić backend Spring Boot.
2. Uruchomić frontend Angular.
3. Otworzyć aplikację w przeglądarce:


http://localhost:4200

---

## Uruchomienie testów backendu

W głównym katalogu projektu uruchom:

```powershell
.\mvnw.cmd test
```

Poprawny wynik oznacza, że testy jednostkowe przeszły bez błędów.

---

## Zbudowanie backendu

W głównym katalogu projektu uruchom:

```powershell
.\mvnw.cmd clean package
```

Po poprawnym buildzie w terminalu powinien pojawić się komunikat BUILD SUCCESS

---

## Zbudowanie frontendu

Przejdź do folderu frontendu:

```powershell
cd frontend
```

Uruchom build:

```powershell
npm run build
```

Jeżeli build zakończy się poprawnie, frontend został poprawnie zbudowany.

---

## Podsumowanie uruchomienia

Minimalny zestaw komend:

Backend:

```powershell
.\mvnw.cmd spring-boot:run
```

Frontend:

```powershell
npm install
ng serve
```

Adres aplikacji:

http://localhost:4200