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
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by anthony on 24/01/17.
 */
public class StringParserTest {


    @Test(expected = RuntimeException.class)
    public void null_list()
    {
        new StringParser(null);
    }

    @Test(expected = RuntimeException.class)
    public void empty_list()
    {
        new StringParser(new ArrayList<>());
    }

    @Test(expected = RuntimeException.class)
    public void file_type_is_null()
    {
        new StringParser(Paths.get("my", "awesome", "path"), null);
    }

    @Test(expected = RuntimeException.class)
    public void path_to_database_is_null()
    {
        new StringParser(null, FileType.JSON);
    }

    @Test
    public void use_a_file_as_database()
    {
        new StringParser(Paths.get(System.getProperty("user.dir")), FileType.JSON);
    }

    @Test
    public void use_a_list_as_database()
    {
        new StringParser(Arrays.asList("Foo", "Bar", "Baz", "Bang"));
    }

    @Test
    public void submit_pattern_with_list()
    {
        StringParser stringParser = new StringParser(Arrays.asList("Foo", "Bar"));
        stringParser.submit_pattern("Foo");
    }

    @Test
    public void equality_string_event_test()
    {
        StringParser stringParser = new StringParser(Arrays.asList("Foo", "Bar"));
        stringParser.addListener((String targeted_input, String input_found) -> {
            System.out.println("Le terme " + targeted_input + " a bien été trouvé.");
            System.out.println("Résultat de la recherche: " + input_found + ".");
        });
        stringParser.submit_pattern("Foo");
    }

}
