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
 * (e.g. création d'une map, ajout de contenu) sur des objets des classes filles de Map.
 * @author songbird
 * @since 31 janv., 2017.
 */
public final class Maps {


    public static <K, V> Map<K, V> asMap(Entry<K, V> first_entry, Entry<K, V>... more_entries)
    {
        if(first_entry == null)
            throw new RuntimeException("La référence first_entry est nulle.");
        if(more_entries == null)
            throw new RuntimeException("La référence more_entries est nulle.");
        if(more_entries.length == 0)
            throw new RuntimeException("Le tableau more_entries est vide. Remplissez-le d'au moins un élément pour faire " +
                    "disparaître cette erreur.");

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
