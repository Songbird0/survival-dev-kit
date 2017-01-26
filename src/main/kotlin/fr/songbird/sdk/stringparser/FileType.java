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

package fr.songbird.sdk.stringparser;

/**
 * Représente tous les formats de fichiers pouvant
 * servir de base de données aux services de la classe {@link StringParser StringParser}.
 */
public enum FileType {

    /**
     * Représente un fichier texte basique, sans extension particulière.
     * Par souci de portabilité, vérifiez bien que votre fichier dispose de l'extension ".txt",
     * que vous soyez sous Windows, ou non.
     * Une exception sera levée si cette convention n'est pas respectée.
     */
    VANILLA,
    /**
     * Représente un fichier texte avec une structure json.
     * Par souci de portabilité, vérifiez bien que votre fichier dispose de l'extension ".json",
     * que vous soyez sous Windows, ou non.
     * Une exception sera levée si cette convention n'est pas respectée.
     */
    JSON,
    /**
     * Représente un fichier texte avec une structure yml.
     * Par souci de portabilité, vérifiez bien que votre fichier dispose de l'extension ".yml",
     * que vous soyez sous Windows, ou non.
     * Une exception sera levée si cette convention n'est pas respectée.
     */
    YAML
}