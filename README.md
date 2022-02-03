# Česká pošta API

## AK7MT - Semestrální projekt: Repository pattern s využitím API české pošty

Tato aplikace byla vytvořena v rámci semestrálního projektu z předmětu AK7MT.

### Funkcionalita

Na hlavní obrazovce je zadáno číslo zásilky doručované Českou poštou. Aplikace tuto zásilku vyhledá pomocí aplikačního rozhraní poskytovaného Českou poštou a zobrazí stav zásilky. Každé vyhledávání je zároveň uloženo do perzistentní paměti zařízení.

### Technické řešení
Zdrojové kódy aplikace jsou psány v jazyce Kotlin.

Aplikace se pokouší implementovat tzv. Repository pattern. Jedná se o abstrakci nad daty, kdy repozitář slouží jako obecný zdroj dat. Uvnitř má pak konkrétní implementaci, jak a odkud se daná data mají získávat. Zásilka se podle jejího čísla hledá nejprve v lokální SQLite databázi, až následně dochází k volání API po síti.

Pro volání API je využita knihovna **Retrofit**, mapování JSONu získané odpovědi na datové struktury Kotlinu zajišťuje knihovna **GSON**. Přístup do lokálního uložiště SQLite obstarává knihovna **Room**.
