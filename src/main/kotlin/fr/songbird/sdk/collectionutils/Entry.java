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

/**
 * La classe Entry permet de récupérer le couple clé/valeur soumis par l'utilisateur
 * pour ensuite le fournir aux services de la classe {@link Maps} qui peuvent en avoir besoin lors de leurs opérations.
 * @param <K> Le type de la clé fournie par l'utilisateur.
 * @param <V> Le type de la valeur fournie par l'utilisateur.
 * @author songbird
 * @since 31 janv., 2017.
 */
public class Entry<K, V> {

    /**
     * La clé.
     */
    private final K key;
    /**
     * La valeur contenue par la clé.
     */
    private final V value;

    /**
     * Récupère la référence du couple clé/valeur.
     * @param key La clé de l'entrée.
     * @param value La valeur de l'entrée.
     */
    public Entry(K key, V value)
    {
        if(key == null)
            throw new RuntimeException("La référence key est nulle.");
        if(value == null)
            throw new RuntimeException("La référence value est nulle.");
        this.key = key;
        this.value = value;
    }

    /**
     * Fournit une référence de la clé.
     * @return La clé.
     */
    public K getKey()
    {
        return key;
    }

    /**
     * Fournit une référence de la valeur que contient la clé.
     * @return La valeur que contient la clé.
     */
    public V getValue()
    {
        return value;
    }
}
