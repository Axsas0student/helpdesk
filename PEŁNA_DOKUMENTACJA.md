# Helpdesk - system zgłoszeń serwisowych

Projekt zaliczeniowy wykonany w technologii Spring Boot + Angular.

Aplikacja służy do obsługi zgłoszeń serwisowych w systemie helpdesk. System umożliwia rejestrację i logowanie użytkowników, tworzenie zgłoszeń, zarządzanie kategoriami oraz zmianę statusów zgłoszeń przez administratora.

## Technologie

### Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- H2 Database
- Validation
- Maven
- JUnit 5
- Mockito
- Spring Events

### Frontend

- Angular
- TypeScript
- HTML
- CSS
- Angular HttpClient
- FormsModule

## Główne funkcjonalności

### Użytkownik niezalogowany

- może się zarejestrować,
- może się zalogować.

### Użytkownik USER

- może przeglądać kategorie zgłoszeń,
- może utworzyć nowe zgłoszenie,
- może wybrać kategorię zgłoszenia,
- może użyć domyślnej kategorii "Brak kategorii",
- widzi tylko swoje zgłoszenia,
- nie może dodawać kategorii,
- nie może widzieć zgłoszeń innych użytkowników,
- nie może zmieniać statusu zgłoszeń.

### Użytkownik ADMIN

- może przeglądać wszystkie zgłoszenia,
- może dodawać kategorie,
- może zarządzać kategoriami,
- może tworzyć zgłoszenia,
- może zmieniać status zgłoszeń,
- widzi autora zgłoszenia,
- widzi opis zgłoszenia.

## Role użytkowników

W aplikacji dostępne są dwie role:

- USER 
- ADMIN

Rola wybierana jest podczas rejestracji.

Podczas logowania użytkownik podaje tylko nazwę użytkownika i hasło. Rola jest pobierana z backendu na podstawie konta użytkownika.

## Statusy zgłoszeń

Zgłoszenie może mieć jeden z następujących statusów:

- OPEN
- IN_PROGRESS
- RESOLVED
- CLOSED


Nowe zgłoszenie otrzymuje automatycznie status OPEN.

## Kategorie

Aplikacja obsługuje kategorie zgłoszeń.

Przykładowe kategorie:

- Logowanie
- Sprzęt
- Sieć
- Oprogramowanie

Przy starcie aplikacji automatycznie tworzona jest domyślna kategoria "Brak kategorii"

Służy ona do zgłoszeń, które nie pasują do żadnej istniejącej kategorii.

## Autor zgłoszenia

Użytkownik nie wpisuje ręcznie adresu e-mail podczas tworzenia zgłoszenia.

Adres e-mail autora jest automatycznie pobierany z konta aktualnie zalogowanego użytkownika.

## Struktura backendu

Główne pakiety backendu:

```text
com.example.helpdesk
├── auth
├── category
├── config
├── exception
├── init
├── ticket
└── user
```

Opis pakietów:

- auth - logowanie, rejestracja, JWT,
- user - encja użytkownika i role,
- ticket - obsługa zgłoszeń,
- category - obsługa kategorii,
- exception - globalna obsługa błędów,
- config - konfiguracja security i CORS,
- init - inicjalizacja danych startowych.

## Struktura frontendu

Frontend znajduje się w folderze frontend

Najważniejsze elementy:

- frontend/src/app/app.ts
- frontend/src/app/app.html
- frontend/src/app/app.css

Frontend komunikuje się z backendem przez REST API pod adresem:

http://localhost:8080/api

## Uruchomienie backendu

W głównym katalogu projektu uruchom:

```powershell
.\mvnw.cmd spring-boot:run
```

Backend działa pod adresem:

http://localhost:8080

## Uruchomienie frontendu

Przejdź do folderu frontendu:

```powershell
cd frontend
```

Zainstaluj zależności:

```powershell
npm install
```

Uruchom aplikację Angular:

```powershell
ng serve
```

Frontend działa pod adresem:

http://localhost:4200

## Testy backendu

Aby uruchomić testy jednostkowe backendu, w głównym katalogu projektu wpisz:

```powershell
.\mvnw.cmd test
```

Aby zbudować projekt backendowy:

```powershell
.\mvnw.cmd clean package
```

## Build frontendu

W folderze frontend uruchom:

```powershell
npm run build
```

## Przykładowy scenariusz działania

1. Uruchomienie backendu.
2. Uruchomienie frontendu.
3. Wejście na stronę:


http://localhost:4200

4. Rejestracja użytkownika z rolą ADMIN.
5. Po rejestracji użytkownik zostanie automatycznie zalogowany.
6. Administrator widzi domyślną kategorię Brak kategorii.
7. Administrator dodaje nową kategorię, np. Logowanie.
8. Administrator tworzy zgłoszenie.
9. Administrator widzi listę wszystkich zgłoszeń.
10. Administrator zmienia status zgłoszenia, np. z OPEN na IN_PROGRESS.
11. Administrator wylogowuje się.
12. Rejestracja użytkownika z rolą USER.
13. Użytkownik tworzy zgłoszenie.
14. Użytkownik widzi tylko swoje zgłoszenia.
15. Administrator po ponownym zalogowaniu widzi zgłoszenia wszystkich użytkowników.

## Najważniejsze endpointy API

### Auth

- POST /api/auth/register
- POST /api/auth/login

### Kategorie

- GET    /api/categories
- GET    /api/categories/{id}
- POST   /api/categories
- PUT    /api/categories/{id}
- DELETE /api/categories/{id}

### Zgłoszenia

- GET    /api/tickets
- GET    /api/tickets/my
- GET    /api/tickets/{id}
- POST   /api/tickets
- PUT    /api/tickets/{id}
- PATCH  /api/tickets/{id}/status
- DELETE /api/tickets/{id}

## Zabezpieczenia endpointów

- /api/auth/** - publiczne

- GET /api/categories/** - zalogowany użytkownik
- POST /api/categories/** - ADMIN
- PUT /api/categories/** - ADMIN
- DELETE /api/categories/** - ADMIN

- GET /api/tickets - ADMIN
- GET /api/tickets/my - zalogowany użytkownik
- POST /api/tickets - zalogowany użytkownik
- PATCH /api/tickets/{id}/status - ADMIN
- PUT /api/tickets/{id} - ADMIN
- DELETE /api/tickets/{id} - ADMIN


## Obsługa błędów

Projekt posiada globalną obsługę błędów przez GlobalExceptionHandler.

Obsługiwane są między innymi:

- błędy walidacji danych,
- brak zgłoszenia,
- brak kategorii,
- duplikat kategorii,
- duplikat użytkownika,
- błędne dane logowania,
- błędy integralności danych.

Przykładowa odpowiedź błędu walidacji:

```json
{
  "timestamp": "2026-05-24T20:00:00",
  "status": 400,
  "message": "Błąd walidacji danych",
  "errors": {
    "description": "Opis musi mieć od 10 do 2000 znaków"
  }
}
```

## Mechanizm zdarzeń

Projekt wykorzystuje Spring Events. Po utworzeniu zgłoszenia publikowane jest zdarzenie TicketCreatedEvent. Zdarzenie jest obsługiwane przez TicketEventListener. Listener wypisuje informację o utworzonym zgłoszeniu w konsoli aplikacji.

## Testy jednostkowe

W projekcie przygotowano testy jednostkowe dla warstwy serwisowej:

- CategoryServiceTest
- TicketServiceTest

Testy sprawdzają między innymi:

- tworzenie kategorii,
- obsługę duplikatu kategorii,
- pobieranie kategorii,
- brak kategorii,
- tworzenie zgłoszenia,
- pobieranie zgłoszeń,
- aktualizację zgłoszenia,
- usuwanie zgłoszenia,
- publikację eventu po utworzeniu zgłoszenia.

## Baza danych

Projekt używa bazy H2 w pamięci.

Oznacza to, że dane są usuwane po restarcie backendu. Po każdym restarcie aplikacji należy ponownie zarejestrować użytkowników.

Przy starcie aplikacji automatycznie tworzona jest domyślna kategoria Brak kategorii.

## CORS

Frontend działa na porcie 4200, a backend na porcie 8080.

W projekcie skonfigurowano CORS tak, aby aplikacja Angular mogła komunikować się z API Spring Boot.

- Frontend: http://localhost:4200
- Backend:  http://localhost:8080

## Repozytorium

Projekt znajduje się w repozytorium GitHub:

https://github.com/Axsas0student/helpdesk

## Uwagi końcowe

Projekt realizuje elementy aplikacji webowej:

- REST API,
- połączenie z bazą danych,
- warstwową strukturę backendu,
- DTO i walidację,
- globalną obsługę błędów,
- logowanie i rejestrację z JWT,
- role użytkowników,
- testy jednostkowe,
- mechanizm zdarzeń,
- frontend Angular konsumujący API.