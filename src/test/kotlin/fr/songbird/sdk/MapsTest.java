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
import org.junit.Test;

import java.util.Map;

/**
 * Created by anthony on 31/01/17.
 */
public class MapsTest {

    @Test
    public void as_map_function_test()
    {
        final Map<String, Integer> foo_map = Maps.<String, Integer>asMap(new Entry<>("John", 117), new Entry<>("Emile", 259));
        assert(foo_map.get("John").equals(117));
        assert(foo_map.get("Emile").equals(259));
    }
}
