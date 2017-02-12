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

package fr.songbird.sdk.probabuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Représente un objet de manière générique.
 *
 * @author songbird
 * @since 12 janv., 2017
 * @param <T> Le type de l'item. <string>Note</string>: L'item doit être du même type que celui passé à une instance
 * de la classe ProbabilityManager.
 *
 * @see ProbabilityManager
 */
public final class FavorableCase<T> {
    /**
     * Le logger de la classe.
     */
    private static final Logger LOGGER = Logger.getLogger(FavorableCase.class.getSimpleName());
    /**
     * Les chances de l'instance d'être tirée au sort en pourcentage.
     */
    private final int favorable_case_percentage;
    /**
     * Le label de l'item.
     * Ce label permet au système de probabilités de différencier deux ressources
     * techniquement similaires.
     */
    private String item_name;
    /**
     * La ressource "wrappée" par l'instance.
     */
    private T item;

    /**
     * Créé une instance du wrapper pour envelopper votre objet
     * et le rendre éligible au tirage au sort effectué par le système
     * de probabilités dans un second temps.
     * @param item_name Le nom de l'item.
     * Le système pourra délimiter l'espace entre deux instances, même si elles encapsulent le même contenu. Exemple:<br>
     *
     * <pre>
     *   final FavorableCase<String> foo = new FavorableCase("my awesome item", "item content", 10);<br>
     *
     *   final FavorableCase<String> bar = new FavorableCase("another item", "item content", 10);<br>
     *
     *   final equality_success = foo.equals(bar); // return false<br>
     * </pre>
     *
     * Si vous ne souhaitez pas nommer vos items, passez l'argument à {@code null}, les services se débrouilleront.
     *
     * @param item L'instance de l'item lui-même.
     * @param favorable_case_percentage Le nombre de cas favorables à l'obtention
     * de cet item en pourcentage.
     * Le système se chargera d'adapter le pourcentage à l'échelle des cas possibles.
     * @throws Exception Si les paramètres passés au constructeur ne respecte pas les contrats de ce dernier.
     * @see ProbabilityManager
     */
    public FavorableCase(String item_name, T item, int favorable_case_percentage) throws Exception {
        this.item = item;
        LOGGER.log(Level.FINEST, "Construction de l'item.");
        Boolean is_empty = null;
        if(item_name != null)
            is_empty = item_name.isEmpty();

        if(is_empty != null)
        {
            if(is_empty) {
                throw new Exception("Vous avez renommé votre item, mais la chaîne de caractères est vide. " +
                        "\nRéglez le problème pour faire disparaître cette erreur.");
            }
            LOGGER.log(Level.FINEST, "L'item a été renommé " + item_name+ ".");
            this.item_name = item_name;
        }

        if(favorable_case_percentage < 0)
            throw new Exception("Le pourcentage de cas favorables est négatif. " +
                    "\nFixez le problème pour voir l'erreur disparaître.");
        if(favorable_case_percentage == 0)
            throw new Exception("Le pourcentage de cas favorables est nul. " +
                    "\nFixez le problème pour voir l'erreur disparaître.");
        this.favorable_case_percentage = favorable_case_percentage;
        LOGGER.log(Level.FINEST, "Aucun problème détecté lors de la construction de l'item " + item_name +".");
    }

    /**
     * Renvoi le nombre de cas favorables dédiés à cet item
     * sous sa forme entière.
     * La classe {@code FavorableCase} étant un composant du gestionnaire de probabilités,
     * ce service donne la possibilité au système de questionner ses composants
     * pour gérer l'espace alloué.
     * <strong>Note</strong>: Bien que ça soit possible, ce service n'est pas à utiliser en dehors du système de probabilités.
     * La méthode n'existe que pour le servir, ne peut pas être override et l'utiliser en dehors de son champ d'action
     * serait un non-sens total.
     *
     * @return Le nombre de cas favorables sous sa forme entière.
     */
    public final Integer get_favorable_case_to_int(int potential_case) throws Exception{
        if(potential_case == favorable_case_percentage)
            throw new Exception("Le nombre de cas favorables est équivalent au nombre de cas potentiels." +
                    "\nSi le nombre de cas favorables est équivalent au nombre de cas potentiels " +
                    "l'item sera forcément choisi lors du tirage au sort, inutile de passer par ces services donc.");
        if(potential_case < 0)
            throw new Exception("Le nombre de cas potentiels est négatif, cette valeur appartient-elle vraiment au système de probabilités ?");
        if(potential_case == 0)
            throw new Exception("Le nombre de cas potentiels est nul, cette valeur appartient-elle vraiment au système de probabilités ?");
        if(potential_case == 1)
            throw new Exception("Le nombre de cas potentiels offre un tirage au sort certain(égal à 1), cette valeur appartient-elle vraiment au système de probabilités ?");

        return potential_case * this.favorable_case_percentage/100;
    }

    /**
     * @return Une nouvelle instance de la classe
     * FavorableCase qui possède les mêmes
     * caractéristiques que l'instance courante.
     */
    public FavorableCase<T> copy() throws Exception{
        return new FavorableCase<T>(this.item_name, this.item, this.favorable_case_percentage);
    }

    /**
     * Permet de comparer l'instance courante
     * avec un objet du même type.
     * Cette implémentation de la méthode {@code equals()}
     * fait bien évidemment abstraction de l'état (est-ce une référence de l'objet courant ? Est-ce un nouvel objet ?) de chaque objet
     * pour s'assurer que le contenu est identique (ou non).
     * Autrement dit, si l'objet passé en paramètre n'est pas une référence de l'objet courant
     * son contenu sera analysé.
     * @param an_object L'objet à comparer avec l'instance courante.
     * @return {@code true} si l'objet est une référence de l'instance courante ou dispose
     * des même caractéristiques. (titre de l'item et l'item lui-même), sinon {@code false}.
     */
    public Boolean equals(FavorableCase<T> an_object) {
        return this == an_object || (this.item_name.equals(an_object.item_name)) && (this.item == an_object.item);
    }

    /**
     * Cet accesseur vous permet de récupérer
     * une référence vers l'objet encapsulé.
     * Cela peut être utile de pouvoir opérer de nouveau sur cet objet
     * une fois le tirage au sort terminé, par exemple.
     * @return une référence vers l'objet encapsulé.
     */
    public T getItemRef() {
        return this.item;
    }

    /**
     * Récupère vos objets pour les wrapper dans des instances
     * de la classe {@link FavorableCase}, les préparant ainsi à
     * un tirage au sort iminent. Exemple d'utilisation:
     * <pre>
     *     final List<FavorableCase<String>> favorableCases = as_fav_case_list("foo", 20, "bar", "baz", "bang");
     *     assert(favorableCases.size() == 4);
     * </pre>
     * @param your_first_objet La référence de votre objet. (obligatoire)
     * @param percentage Les chances d'apparition de vos objets.
     *                   <strong>Notez</strong> que toutes vos instances auront
     *                   les mêmes chances d'apparition. (Si vous mettez 20% avec 4 instances
     *                   vous aurez 1 chance sur 4 de tomber sur l'une de vos instances)
     * @param others Un tableau de référence pour d'autres objets. (optionnel)
     * @param <V> Le type de vos objets.
     * @return Une liste contenant vos objets wrappés.
     * @throws RuntimeException Lorsque l'un des paramètres passés est une référence {@code null}.
     */
    @SafeVarargs
    public static <V> List<FavorableCase<V>> as_fav_case_list(V your_first_objet, final int percentage, V... others)
    {
        if(your_first_objet == null)
            throw new RuntimeException("Votre premier objet est nul.");
        if(others == null)
            throw new RuntimeException("La liste optionnelle d'objets supplémentaires est nulle.");

        List<FavorableCase<V>> wrapped_object_list = new ArrayList<>(others.length+1);
//        +1 car on compte aussi le premier objet passé en paramètre
        try
        {
            wrapped_object_list.add(new FavorableCase<>(" ", your_first_objet, percentage));
            if(others.length+1 > 2 && percentage > 50)
                throw new RuntimeException("Vous ne pouvez pas assigner plus de deux instances " +
                        "pouvant occuper 50% des cas potentiels, c'est illogique.");
            if(others.length+1 > 3 && percentage > 30)
                throw new RuntimeException("Vous ne pouvez pas assigner plus de trois instances " +
                        "pouvant occuper 30% des cas potentiels, c'est illogique.");
            if(others.length > 0)
            {
                for (int i = 0; i < others.length; i++) {
                    final V current_element = others[i];
                    if(current_element == null)
                        throw new RuntimeException("L'élément N°" + i + " est nul.");
                    wrapped_object_list.add(new FavorableCase<>(" ", others[i], percentage));
                }
            }
        }catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return wrapped_object_list;
    }
}
