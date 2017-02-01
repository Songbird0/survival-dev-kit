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

import fr.songbird.sdk.collectionutils.Entry;
import fr.songbird.sdk.collectionutils.Maps;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

/**
 * Created by anthony on 31/01/17.
 */
public class MapsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void as_map_function_test()
    {
        final Map<String, Integer> foo_map = Maps.asMap(new Entry<>("John", 117), new Entry<>("Emile", 259));
        assert(foo_map.get("John").equals(117));
        assert(foo_map.get("Emile").equals(259));
    }

    @Test
    public void bad_use_of_as_map_function()
    {
        final Map foo_map = Maps.asMap(new Entry("John", 117), new Entry("Emile", "not safe"));
        assert(foo_map.get("John").equals(117));
        assert(foo_map.get("Emile").equals("not safe"));
    }

    @Test
    public void null_varargs_binding()
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("La référence more_entries est nulle.");
        final Map<String, Integer> foo_map = Maps.asMap(new Entry<>("John", 117), null);
        assert(foo_map.get("John").equals(117));
        assert(foo_map.get("Emile").equals(259));
    }
}
