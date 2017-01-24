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

package fr.songbird.sdk.stringparser.listener;

/**
 * Permet, aux instances qui l'implémentent, d'écouter les résultats
 * des tests d'égalité de la classe {@link fr.songbird.sdk.stringparser.StringParser StringParser}.
 */
@FunctionalInterface
public interface EqualityStringListener {
    /**
     * Méthode appelée sur l'écouteur lorsque
     * l'entrée soumise a été retrouvée dans la base
     * de données.
     * @param targeted_input L'entrée soumise à une instance de la classe StringParser.
     * @param input_found La donnée stockée qui a été testée auprès de l'entrée soumise.
     */
    void whenInputIsFound(final String targeted_input, final String input_found);
}
