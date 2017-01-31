/*
 *    SurvivalDevKit, descendante de la bibliothèque utilitaire TheBareMinimum, mais en moins crade. :)
 *     Copyright (C) 2017  Defranceschi Anthony
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *     If you need more information, feel free to contact me at chaacygg[at]gmail[dot]com.
 */

package fr.songbird.sdk;

import fr.songbird.sdk.stringparser.FileType;
import fr.songbird.sdk.stringparser.StringParser;
import fr.songbird.sdk.stringparser.listener.StringParserListener;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe dédiée aux tests de la classe {@link StringParser}.
 */
public class StringParserTest {


    /**
     * On attend une erreur car aucune instance de la
     * classe {@link java.util.List List} ou de ses filles
     *  n'a été passée en paramètre.
     */
    @Test(expected = RuntimeException.class)
    public void null_list()
    {
        new StringParser(null);
    }

    /**
     * On attend une erreur car la liste passée en
     * paramètre ne contient aucun élément.
     */
    @Test(expected = RuntimeException.class)
    public void empty_list()
    {
        new StringParser(new ArrayList<>());
    }

    /**
     * On attend une erreur car aucune instance de
     * l'enum {@link FileType} n'a été passée en paramètre.
     */
    @Test(expected = RuntimeException.class)
    public void file_type_is_null()
    {
        new StringParser(Paths.get("my", "awesome", "path"), null);
    }

    /**
     * On attend une erreur car aucune instance de
     * la classe {@link java.nio.file.Path Path} n'a été passée en paramètre.
     */
    @Test(expected = RuntimeException.class)
    public void path_to_database_is_null()
    {
        new StringParser(null, FileType.JSON);
    }

    /**
     * Procédure de test simple. Aucune assertion n'est nécessaire
     * et il ne devrait rien se passer d'anormal (pas d'exceptions).
     * On lance donc le constructeur qui permet d'utiliser un fichier en tant
     * que base de données.
     * @see StringParser#StringParser(Path, FileType)
     */
    @Test
    public void use_a_file_as_database()
    {
        new StringParser(Paths.get(System.getProperty("user.dir")), FileType.JSON);
    }

    /**
     * Procédure de test simple. Aucune assertion n'est nécessaire
     * et il ne devrait rien se passer d'anormal (pas d'exceptions).
     * On lance le constructeur qui permet d'utiliser une liste en tant
     * que base de données.
     * @see StringParser#StringParser(List)
     */
    @Test
    public void use_a_list_as_database()
    {
        new StringParser(Arrays.asList("Foo", "Bar", "Baz", "Bang"));
    }

    /**
     * Procédure de test simple. Aucune assertion n'est nécessaire
     * et il ne devrait rien se passer d'anormal (pas d'exceptions).
     * On lance le constructeur qui permet d'utiliser une liste en tant
     * que base de données, puis on utilise la version surchargée de la méthode {@link StringParser#submit_pattern(String)}.
     */
    @Test
    public void submit_pattern_with_list()
    {
        StringParser stringParser = new StringParser(Arrays.asList("Foo", "Bar"));
        stringParser.submit_pattern("Foo");
    }

    /**
     * Procédure de test simple. Aucune assertion n'est nécessaire
     * et il ne devrait rien se passer d'anormal (pas d'exceptions).
     * On lance le constructeur qui permet d'utiliser une liste en tant
     * que base de données, on inscrit un listener pour tester l'égalité de l'entrée,
     * puis on utilise la version surchargée de la méthode {@link StringParser#submit_pattern(String)}.
     */
    @Test
    public void equality_string_event_test()
    {
        StringParser stringParser = new StringParser(Arrays.asList("Foo", "Bar"));
        stringParser.addListener(new StringParserListener() {
            @Override
            public void whenInputIsFound(String targeted_input, String input_found) {
                System.out.println("Le terme " + targeted_input + " a bien été trouvé.");
            }

            @Override
            public void whenInputIsNotFound(String targeted_input) {

            }
        });
        stringParser.submit_pattern("Foo");
    }

    /**
     * On attend une erreur car le constructeur utilisé n'est pas le bon.
     */
    @Test(expected = RuntimeException.class)
    public void submit_pattern_with_list_and_file_reading_to_true()
    {
        StringParser stringParser = new StringParser(Arrays.asList("Foo", "Bar"));
        stringParser.submit_pattern("Foo", true);
    }

    /**
     * Procédure de test simple permettant de vérifier le bon
     * fonctionnement des de l'envoi des événements lors d'une égalité
     * ou non. Aucune exception n'est attendue. Si il y en a une, alors il y a forcément une
     * régression d'une version à l'autre.
     */
    @Test
    public void submit_pattern_with_list_and_file_reading_to_false()
    {
        StringParser stringParser = new StringParser(Arrays.asList("foo", "bar", "baz", "bang"));
        stringParser.addListener(new StringParserListener() {
            @Override
            public void whenInputIsFound(String targeted_input, String input_found) {
                System.out.println("La chaine de caractères '" + targeted_input + "' a été trouvée.");
            }

            @Override
            public void whenInputIsNotFound(String targeted_input) {
                System.out.println("La chaine de caractères '" + targeted_input + "' n'a pas été trouvée.");
            }
        });
        stringParser.submit_pattern("foO");
        stringParser.submit_pattern("bAr");
        stringParser.submit_pattern("baZ");
        stringParser.submit_pattern("baNG");
        stringParser.submit_pattern("bazzzzzzz");
    }

    /**
     * Procédure de test simple permettant de vérifier
     * rapidement si le service fonctionne correctement.
     * Si ce test lève une exception, alors y il y a forcément une régression
     * quelque part.
     */
    @Test
    public void submit_pattern_with_vanilla_file_and_file_reading_to_true()
    {
        final String USER_DIRECTORY = System.getProperty("user.dir");
        StringParser stringParser = new StringParser(Paths.get(USER_DIRECTORY, "src", "test", "vanilla_file.txt"), FileType.VANILLA);
        stringParser.addListener(new StringParserListener() {
            @Override
            public void whenInputIsFound(String targeted_input, String input_found) {
                System.out.println("La chaîne de caractères '"+ targeted_input + "' a été trouvée.");
            }

            @Override
            public void whenInputIsNotFound(String targeted_input) {
                System.out.println("L'entrée '" + targeted_input + "' n'a pas été trouvée.");
            }
        });
        stringParser.submit_pattern("fOo", true);
        stringParser.submit_pattern("baR", true);
        stringParser.submit_pattern("zzzzzz", true); //ne sera pas trouvé
        stringParser.submit_pattern("bAnG", true);
    }

}
