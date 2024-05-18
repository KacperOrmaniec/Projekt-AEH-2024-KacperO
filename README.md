# Projekt-AEH-2024-KacperO
# Team Management App

Aplikacja do zarządzania zespołem i zadaniami, zbudowana w JavaFX.

## Opis

Aplikacja Team Management App umożliwia zarządzanie zespołem, przydzielanie zadań członkom zespołu, śledzenie postępu zadań oraz generowanie raportów. Aplikacja posiada intuicyjny interfejs graficzny zbudowany w oparciu o JavaFX.

## Funkcjonalności

**Zarządzanie członkami zespołu:**
    * Dodawanie nowych członków zespołu.
    * Wyświetlanie listy członków zespołu.
**Zarządzanie zadaniami:**
    * Dodawanie nowych zadań.
    * Wyświetlanie listy zadań.
    * Przypisywanie zadań do członków zespołu.
    * Zmiana statusu zadań (nowe, do zrobienia, w trakcie, ukończone).
    * Śledzenie czasu spędzonego na zadaniach (tylko dla zadań ukończonych).
**Generowanie raportów:**
    * Generowanie raportu z informacjami o:
        * Liczbie zadań przypisanych do każdego członka zespołu.
        * Całkowitym czasie spędzonym na wszystkich zadaniach.
        * Liczbie zadań ukończonych przez każdego członka zespołu.

## Instrukcja obsługi

1. Uruchom aplikację.
2. Dodaj członków zespołu, klikając przycisk "Add Member" i wprowadzając ich imiona.
3. Dodaj zadania, klikając przycisk "Add Task" i wprowadzając ich opisy.
4. Przypisz zadania do członków zespołu, zaznaczając zadanie i członka, a następnie klikając przycisk "Assign Task".
5. Zmień status zadania, zaznaczając je i wybierając nowy status z listy rozwijanej.
6. Aby odświeżyć szczegóły wybranego zadania, kliknij przycisk "Refresh Details".
7. Aby wygenerować raport, kliknij przycisk "Generate Report".

## Uruchamianie

1. Upewnij się, że masz zainstalowane środowisko Java Development Kit (JDK) w wersji 8 lub nowszej.
2. Skompiluj kod źródłowy aplikacji za pomocą polecenia