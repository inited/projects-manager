package cz.inited.utils;

/**
 * Created by ondre on 08.07.2016.
 */
public final class Strings {

	public static final String
		HELP = "HELP",
		COMMAND_NOT_fOUND = "Takový příkaz neznám\n" +
				"vyberte si příkazy pomocí naší nápovědy:\n" + HELP,
		INITED_FILE_NOT_FOUND = "Soubor .inited neexistuje",
		INITED_FILE_SAVE_ERROR = "Vyskytla se chyba při ukládání";

	public static final String
		CREATE_NO_APP_TYPE = "Zadejte typ aplikace který chcete vytvořit",
		CREATE_PROJECT_CREATED = "Projekt vytvořen",
		CREATE_FILE_CREATED = "Soubor vytvořen",
		CREATE_CLONE_ERROR = "Chyba při klonování repozitáře",
		CREATE_TYPE_NOT_SUPPORTED = "Tvorba tohoto typu zatím není podporována",
		CREATE_NO_APP_NAME = "Zadejte prosím název aplikace",
		CREATE_NO_FILE_NAME = "Zadejte prosím název souboru",
		CREATE_CREATING_FOLDER_ERROR = "Při vytváření složky došlo k chybě",
		CREATE_NOT_FOLDER_ERROR = "Zadaná cesta nevede ke složce",
		CREATE_FOLDER_NOT_EMPTY = "Složka není prázdná",
		CREATE_GIT_FOLDER_NOT_REMOVED = "Nepodařilo se odstranit složku .git, prosím smažte ji ručně",
		CREATE_REPLACING_ERROR = "Chyba při zapisování názvu nové aplikace, projekt není korektně vytvořen",
		CREATE_FILL_ALL_FIELDS = "Musíte vyplnit všechna pole",
		CREATE_FILE_EXISTS = "Soubor již existuje, nevytvářím",
		CREATE_TEMPLATE_MISSING = "Šablona pro vytvoření souboru nebyla nalezena",
		CREATE_CREATING_FILE_ERROR = "Při vytváření souboru došlo k chybě",

		CREATE_LOG_CLONNING = "Stahuji šablonu",
		CREATE_LOG_CREATING_PROJECT = "Vytvářím projekt";

	private Strings() {

	}
}
