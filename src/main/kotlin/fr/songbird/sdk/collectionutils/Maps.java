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

package fr.songbird.sdk.collectionutils;

import java.util.HashMap;
import java.util.Map;

/**
 * La classe Maps est destinée à fournir des services visant à raccourir
 * le temps d'écriture des opérations à effectuer
 * (e.g. création d'une map, ajout de contenu) sur les objets des classes filles de {@link Map}.
 * @author songbird
 * @since 31 janv., 2017.
 */
public final class Maps {


    private Maps(){}


    /**
     * Permet de soumettre tous les couples clés/valeurs à entrer dans une
     * Map en une seule ligne. Exemple:
     * <pre>
     *     final Map{@literal <}String, Integer{@literal >} foo_map = Maps.asMap(new Entry{@literal <}{@literal >}("John", 117), new Entry{@literal <}{@literal >}("Emile", 259));
     *     assert(foo_map.get("John").equals(117));
     *     assert(foo_map.get("Emile").equals(259));
     * </pre>
     *
     * Au lieu de:
     * <pre>
     *     final Map{@literal <}String, Integer{@literal >} foo_map = new HashMap{@literal <}{@literal >}();
     *     foo_map.put("John", 117);
     *     foo_map.put("Emile", 259);
     *     assert(foo_map.get("John").equals(117));
     *     assert(foo_map.get("Emile").equals(259));
     * </pre>
     * @param first_entry Le premier couple clé/valeur (obligatoire).
     * @param more_entries D'autres couples clé/valeur (optionnels).
     * @param <K> Le type de la clé.
     * @param <V> Le type de la valeur contenue par la clé.
     * @return Une instance de la classe {@link HashMap} avec les entrées soumises.
     */
    public static <K, V> Map<K, V> asMap(Entry<K, V> first_entry, Entry<K, V>... more_entries)
    {
        if(first_entry == null)
            throw new RuntimeException("La référence first_entry est nulle.");
        if(more_entries == null)
            throw new RuntimeException("La référence more_entries est nulle.");

        // la première clé de la première
        // entrée
        final K first_key = first_entry.getKey();
        // la première valeur de la première
        // entrée
        final V first_value = first_entry.getValue();

        // la map qui va recevoir les entrées
        // et sera renvoyée en fin de programme.
        final Map<K, V> map = new HashMap<>();
        map.put(first_key, first_value);

        for(Entry<K, V> entry: more_entries)
        {
            final K current_key = entry.getKey();
            final V current_value = entry.getValue();
            map.put(current_key, current_value);
        }
        return map;
    }
}
